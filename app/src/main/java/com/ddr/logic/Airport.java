package com.ddr.logic;

public class Airport {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Airport(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString(Airport airport){
        return "ID: " + airport.getId() + " Name: " + airport.getName();
    }
}
