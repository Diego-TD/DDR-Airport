package com.ddr.logic;

import com.ddr.logic.util.FlightDTO;
import com.ddr.logic.util.ReservationDTO;

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

    @POST("/flight")
    Call<Void> addFlight(@Body FlightDTO flight);

    @GET("/flights")
    Call<List<Flight>> getFlights();

    @POST("/flights")
    Call<List<Flight>> getFlightsByAirports(@Body FlightDTO flightDTO);

    @GET("/flights/{id}")
    Call<Flight> getFlight(@Path("id") long id);

    @GET("/reservations")
    Call<List<Reservation>> getReservations();

    @GET("/reservations/user/{id}")
    Call<List<Reservation>> getReservationsByUser(@Path("id") long id);

    @GET("/reservation/{id}")
    Call<Reservation> getReservation(@Path("id") long id);

    @POST("/reservation")
    Call<Void> addReservation(@Body ReservationDTO reservation);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") long id);

}
