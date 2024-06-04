package com.ddr.logic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DDRAPI {
    @GET("/airports/")
    Call<List<Airport>> getAirports();

    @GET("/airport/{id}")
    Call<Airport> getAirport(@Path("id") long id);

    @GET("/airportCityCountries")
    Call<List<AirportCityCountries>> getAirportCityCountries();

    @GET("/cities")
    Call<List<City>> getCities();

    @GET("/cities/{id}")
    Call<City> getCity(@Path("id") long id);

    @POST("/register")
    Call<Void> registerUser(@Body User user);

    @POST("/login")
    Call<Long> loginUser(@Body User user);


}
