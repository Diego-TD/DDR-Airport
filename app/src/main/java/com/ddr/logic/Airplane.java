package com.ddr.logic;

public class Airplane {
    private Long id;
    private String name;
    private Integer totalCapacity;
    private Integer totalStorage;
    private Boolean fullCapacity;
    private Boolean fullStorage;
    public Airplane() {

    }
    public Airplane(Long id, String name, Integer totalCapacity, Integer totalStorage, Boolean fullCapacity, Boolean fullStorage) {
        this.id = id;
        this.name = name;
        this.totalCapacity = totalCapacity;
        this.totalStorage = totalStorage;
        this.fullCapacity = fullCapacity;
        this.fullStorage = fullStorage;
    }
}
