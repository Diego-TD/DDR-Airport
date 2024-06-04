package com.ddr.logic;

import com.google.gson.annotations.SerializedName;

public class AirportCityCountries {
    @SerializedName("id")
    private Long id;
    @SerializedName("airport")
    private Airport airport;
    @SerializedName("city")
    private City city;
    @SerializedName("country")
    private Country country;
    public AirportCityCountries() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }



}
