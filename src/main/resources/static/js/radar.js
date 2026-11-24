
// 1. INITIALIZE MAP

const map = L.map('map').setView([20, 0], 2);

L.tileLayer(
    'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
    {
        attribution: '&copy; OpenStreetMap contributors',
        maxZoom: 19
    }
).addTo(map);


// 2. STORE AIRCRAFT MARKERS

const aircraftMarkers = {};


// 3. FETCH FLIGHT DATA

async function fetchFlights() {

    try {

        const response = await fetch('/api/v1/flights');

        if (!response.ok) {
            throw new Error('Failed to fetch flights');
        }

        const flights = await response.json();

        console.log('Flights received:', flights);

        document.getElementById('flightCount').textContent =
            flights.length;

        displayFlights(flights);

        updateFlightList(flights);

    } catch (error) {

        console.error('Error fetching flights:', error);

    }
}


// 4. CREATE AIRCRAFT ICON

function createPlaneIcon(heading) {

    return L.divIcon({

        html: `
            <div style="
                font-size: 22px;
                color: #38bdf8;
                transform: rotate(${heading ?? 0}deg);
                text-shadow: 0 0 8px #38bdf8;
            ">
                ✈
            </div>
        `,

        className: '',
        iconSize: [25, 25],
        iconAnchor: [12, 12]

    });

}


// 5. DISPLAY AIRCRAFT ON MAP

function displayFlights(flights) {

    const currentAircraft = new Set();

    flights.forEach(flight => {

        if (
            flight.latitude == null ||
            flight.longitude == null
        ) {
            return;
        }

        const id = flight.icao24;

        currentAircraft.add(id);

        if (aircraftMarkers[id]) {

            // Update existing marker position
            aircraftMarkers[id].setLatLng([
                flight.latitude,
                flight.longitude
            ]);

            aircraftMarkers[id].setIcon(
                createPlaneIcon(flight.heading)
            );

        } else {

            // Create new marker
            const marker = L.marker(
                [flight.latitude, flight.longitude],
                {
                    icon: createPlaneIcon(flight.heading)
                }
            ).addTo(map);

            marker.on('click', function () {
                showFlightDetails(flight);
            });

            aircraftMarkers[id] = marker;

        }

        // Keep marker click data updated
        aircraftMarkers[id].off('click');

        aircraftMarkers[id].on('click', function () {
            showFlightDetails(flight);
        });

    });


    // Remove aircraft no longer present in latest response
    Object.keys(aircraftMarkers).forEach(id => {

        if (!currentAircraft.has(id)) {

            map.removeLayer(aircraftMarkers[id]);

            delete aircraftMarkers[id];

        }

    });

}


// 6. UPDATE SIDEBAR FLIGHT LIST

function updateFlightList(flights) {

    const flightList = document.getElementById('flightList');

    flightList.innerHTML = '';

    // Show maximum 50 aircraft in sidebar
    flights.slice(0, 50).forEach(flight => {

        const item = document.createElement('div');

        item.className = 'flight-item';

        const callsign = flight.callsign?.trim() || 'Unknown';

        item.innerHTML = `
            <strong>${escapeHTML(callsign)}</strong>
            <p>${escapeHTML(flight.originCountry || 'Unknown country')}</p>
            <p>ICAO24: ${escapeHTML(flight.icao24 || 'Unknown')}</p>
        `;

        item.addEventListener('click', function () {

            if (
                flight.latitude == null ||
                flight.longitude == null
            ) {
                return;
            }

            map.flyTo(
                [flight.latitude, flight.longitude],
                7
            );

            showFlightDetails(flight);

        });

        flightList.appendChild(item);

    });

}


// 7. SHOW AIRCRAFT DETAILS

function showFlightDetails(flight) {

    const panel = document.getElementById('flightPanel');

    panel.innerHTML = `
        <h3 style="color: #38bdf8; margin-bottom: 15px;">
            ${escapeHTML(flight.callsign?.trim() || 'Unknown Aircraft')}
        </h3>

        <p>ICAO24: ${escapeHTML(flight.icao24 || 'Unknown')}</p>

        <p>Country: ${escapeHTML(flight.originCountry || 'Unknown')}</p>

        <p>Altitude: ${flight.altitude ?? 'Unknown'} m</p>

        <p>Speed: ${flight.velocity != null
        ? (flight.velocity * 3.6).toFixed(1) + ' km/h'
        : 'Unknown'}</p>

        <p>Heading: ${flight.heading ?? 'Unknown'}°</p>

        <p>On Ground: ${flight.onGround ?? 'Unknown'}</p>
    `;

}


// 8. SAFE HTML TEXT

function escapeHTML(value) {

    return String(value).replace(/[&<>"']/g, character => {

        const entities = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#39;'
        };

        return entities[character];

    });

}


// 9. AIRCRAFT SEARCH

document.getElementById('searchInput').addEventListener(
    'input',
    function () {

        const searchValue = this.value.trim().toLowerCase();

        const flightItems = document.querySelectorAll('.flight-item');

        flightItems.forEach(item => {

            const matches = item.textContent
                .toLowerCase()
                .includes(searchValue);

            item.style.display = matches ? 'block' : 'none';

        });

    }
);


// 10. START AND REFRESH

fetchFlights();

setInterval(fetchFlights, 60000);
