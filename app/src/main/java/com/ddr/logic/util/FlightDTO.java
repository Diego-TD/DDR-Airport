package com.ddr.logic.util;




public class FlightDTO {

    private Long airplaneId;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private String date;
    private String departureTime;
    private String arrivalTime;
    public FlightDTO() {
    }

    public FlightDTO(Long airplaneId, Long departureAirportId, Long arrivalAirportId, String date, String departureTime, String arrivalTime) {
        this.airplaneId = airplaneId;
        this.departureAirportId = departureAirportId;
        this.arrivalAirportId = arrivalAirportId;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    public Long getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(Long departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public Long getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(Long arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
