package com.ddr.logic.util;



import java.sql.Date;
import java.sql.Time;

public class FlightDTO {

    private Long airplaneId;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private Date date;
    private Time time;

    public FlightDTO() {
    }

    public FlightDTO(Long airplaneId, Long departureAirportId, Long arrivalAirportId, Date date, Time time) {
        this.airplaneId = airplaneId;
        this.departureAirportId = departureAirportId;
        this.arrivalAirportId = arrivalAirportId;
        this.date = date;
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
