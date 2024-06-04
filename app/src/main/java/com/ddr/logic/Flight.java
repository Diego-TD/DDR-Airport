package com.ddr.logic;


import java.sql.Date;
import java.sql.Time;

public class Flight {

    private Long id;
    private Airplane airplane;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private String date;
    private String time;
    private Double price;
    public Flight() {

    }

    public Flight(Long id, Airplane airplane, Airport departureAirport, Airport arrivalAirport, String date, String time, Double price) {
        this.id = id;
        this.airplane = airplane;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
