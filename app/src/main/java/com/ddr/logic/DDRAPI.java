package com.ddr.logic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DDRAPI {
    @GET("/airports/{id}")
    Call<Airport> getAirport(@Path("id") long id);

    @GET("/airports/")
    Call<Airport> getAirports();

}
