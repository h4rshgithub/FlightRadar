
package com.flights.flightRadar.service;

import com.flights.flightRadar.client.OpenSkyClient;
import com.flights.flightRadar.dto.Flight;
import com.flights.flightRadar.dto.OpenSkyResponse;
import com.flights.flightRadar.mapper.FlightMapper;

import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Service
public class FlightService {

    private final OpenSkyClient openSkyClient;
    private final FlightMapper flightMapper;
    private final JsonMapper jsonMapper;

    public FlightService(
            OpenSkyClient openSkyClient,
            FlightMapper flightMapper,
            JsonMapper jsonMapper) {

        this.openSkyClient = openSkyClient;
        this.flightMapper = flightMapper;
        this.jsonMapper = jsonMapper;
    }

    public List<Flight> getAllFlights() {

        try {

            // 1. Fetch JSON response from OpenSky
            String jsonResponse = openSkyClient.getFlights();

            // 2. Convert JSON into Java object
            OpenSkyResponse response =
                    jsonMapper.readValue(
                            jsonResponse,
                            OpenSkyResponse.class
                    );

            // 3. Convert aircraft arrays into Flight objects
            return flightMapper.mapFlights(response.getStates());

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to fetch flight data: " + e.getMessage(), e
            );
        }
    }
}
