
package com.flights.flightRadar.mapper;

import com.flights.flightRadar.dto.Flight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightMapper {

    public List<Flight> mapFlights(List<List<Object>> states) {

        List<Flight> flights = new ArrayList<>();

        if (states == null) {
            return flights;
        }

        for (List<Object> state : states) {

            if (state == null || state.size() < 12) {
                continue;
            }

            Flight flight = new Flight();

            flight.setIcao24((String) state.get(0));
            flight.setCallsign((String) state.get(1));
            flight.setOriginCountry((String) state.get(2));

            flight.setLongitude(toDouble(state.get(5)));
            flight.setLatitude(toDouble(state.get(6)));
            flight.setAltitude(toDouble(state.get(7)));

            flight.setOnGround((Boolean) state.get(8));

            flight.setVelocity(toDouble(state.get(9)));
            flight.setHeading(toDouble(state.get(10)));
            flight.setVerticalRate(toDouble(state.get(11)));

            flights.add(flight);
        }

        return flights;
    }

    private Double toDouble(Object value) {

        if (value instanceof Number number) {
            return number.doubleValue();
        }

        return null;
    }
}
