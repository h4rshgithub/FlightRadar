
package com.flights.flightRadar.dto;

import java.util.List;

public class OpenSkyResponse {

    private Long time;

    private List<List<Object>> states;

    public OpenSkyResponse() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<List<Object>> getStates() {
        return states;
    }

    public void setStates(List<List<Object>> states) {
        this.states = states;
    }
}
