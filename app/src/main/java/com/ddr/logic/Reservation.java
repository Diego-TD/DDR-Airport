package com.ddr.logic;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private Flight flight;
    private User user;
    private String createdAt;
    private Integer luggage;
    private Double total;


    public Reservation(){

    }

    public Reservation(Long id, Flight flight, User user, String createdAt, Integer luggage, Double total) {
        this.id = id;
        this.flight = flight;
        this.user = user;
        this.createdAt = createdAt;
        this.luggage = luggage;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLuggage() {
        return luggage;
    }

    public void setLuggage(Integer luggage) {
        this.luggage = luggage;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
