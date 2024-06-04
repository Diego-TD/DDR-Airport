package com.ddr.logic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DDRAPI {
    @GET("/airports/{id}")
    Call<Airport> getAirport(@Path("id") long id);

    @GET("/airports/")
    Call<Airport> getAirports();
    @GET("/airportCityCountries")
    Call<List<AirportCityCountries>> getAirportCityCountries();
    @GET("/cities")
    Call<City> getCities();
    @GET("/cities/{id}")
    Call<City> getCity(@Path("id") long id);
}
