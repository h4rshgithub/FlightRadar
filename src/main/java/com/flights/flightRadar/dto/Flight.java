
package com.flights.flightRadar.dto;

public class Flight {

    private String icao24;
    private String callsign;
    private String originCountry;

    private Double longitude;
    private Double latitude;

    private Double altitude;
    private Boolean onGround;

    private Double velocity;
    private Double heading;
    private Double verticalRate;

    public Flight(String icao24, String callsign, String originCountry, Double longitude, Double latitude, Double altitude, Boolean onGround, Double velocity, Double heading, Double verticalRate) {
        this.icao24 = icao24;
        this.callsign = callsign;
        this.originCountry = originCountry;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.onGround = onGround;
        this.velocity = velocity;
        this.heading = heading;
        this.verticalRate = verticalRate;
    }

    public Flight() {

    }

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Boolean getOnGround() {
        return onGround;
    }

    public void setOnGround(Boolean onGround) {
        this.onGround = onGround;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getVerticalRate() {
        return verticalRate;
    }

    public void setVerticalRate(Double verticalRate) {
        this.verticalRate = verticalRate;
    }
}
