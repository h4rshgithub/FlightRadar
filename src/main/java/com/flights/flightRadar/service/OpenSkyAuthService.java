
package com.flights.flightRadar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class OpenSkyAuthService {

    private final RestClient restClient;

    @Value("${opensky.client-id}")
    private String clientId;

    @Value("${opensky.client-secret}")
    private String clientSecret;

    @Value("${opensky.token-url}")
    private String tokenUrl;

    public OpenSkyAuthService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public String getAccessToken() {

        MultiValueMap<String, String> formData =
                new LinkedMultiValueMap<>();

        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        Map response = restClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(Map.class);

        return (String) response.get("access_token");
    }
}
