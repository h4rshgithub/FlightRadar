
package com.flights.flightRadar.client;

import com.flights.flightRadar.service.OpenSkyAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenSkyClient {

    private final RestClient restClient;
    private final OpenSkyAuthService authService;

    public OpenSkyClient(
            RestClient.Builder builder,
            OpenSkyAuthService authService) {

        this.restClient = builder.build();
        this.authService = authService;
    }

    public String getFlights() {

        String accessToken = authService.getAccessToken();

        return restClient.get()
                .uri("https://opensky-network.org/api/states/all")
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + accessToken
                )
                .retrieve()
                .body(String.class);
    }
}
