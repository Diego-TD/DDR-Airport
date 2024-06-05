package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;
import com.ddr.logic.Airplane;
import com.ddr.logic.Airport;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.Flight;
import com.ddr.logic.Reservation;
import com.ddr.logic.RetrofitClient;
import com.ddr.logic.util.FlightDTO;
import com.ddr.logic.util.ReservationDTO;
import com.ddr.ui.Reservations.RecyclerViewAdapter;
import com.ddr.ui.Reservations.RecyclerViewInterface;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.random.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFlights extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recycleView;
    private SearchFlightsRecycleViewAdapter adapter;
    private int vueloIdImage = R.drawable.vuelo;
    private ArrayList<Flight> flights;
    TextView departingFlights;
    private int planeImage = R.drawable.tbgca201;
    private DDRS ddrSINGLETON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_flights);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            departingFlights = findViewById(R.id.departingText);

            flights = new ArrayList<>();

            recycleView = findViewById(R.id.flightsRecyclerView);
            recycleView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SearchFlightsRecycleViewAdapter(this, flights, this);
            recycleView.setAdapter(adapter);

            ddrSINGLETON = DDRS.getDDRSINGLETON(getApplicationContext());

            Intent in = getIntent();
            String date = "";
            String departureAirport = "";
            String arrivalAirport = "";
            if (in != null) {
                String dateFromIntent = in.getStringExtra("date");
                String departureAirportFromIntent = in.getStringExtra("departureAirport");
                String arrivalAirportFromIntent = in.getStringExtra("arrivalAirport");
                departureAirport = departureAirportFromIntent;
                arrivalAirport = arrivalAirportFromIntent;
                date = dateFromIntent;

            }
            String departingFlightsText = getString(R.string.departing_flights_text, departureAirport, arrivalAirport);
            getFlights(departureAirport, arrivalAirport, date);

            departingFlights.setText(departingFlightsText);
            return insets;
        });
    }

    public void getFlights(String departureAirport, String arrivalAirport, String date){
        Retrofit retrofit = RetrofitClient.getClient();
        DDRAPI api = retrofit.create(DDRAPI.class);
        List<AirportCityCountries> airportCityCountriesList = ddrSINGLETON.getAirportCityCountriesList();
        Long departureAirportId = null, arrivalAirportId = null;

        for (AirportCityCountries airportCityCountries: airportCityCountriesList) {
            if (airportCityCountries.getAirport().getName().equals(departureAirport)) {
                departureAirportId = airportCityCountries.getAirport().getId();
            } else if (airportCityCountries.getAirport().getName().equals(arrivalAirport)) {
                arrivalAirportId = airportCityCountries.getAirport().getId();
            }
        }

        FlightDTO flight = new FlightDTO();
        flight.setDepartureAirportId(departureAirportId);
        flight.setArrivalAirportId(arrivalAirportId);
        flight.setDate(date);

        Call<List<Flight>> call = api.getFlightsByAirports(flight);

        call.enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                List<Flight> flightsResponse = response.body();
                Log.d("Flights", "Received flights" );
                assert flightsResponse != null;
                if (flightsResponse.isEmpty()){
                    //reservationFragmentTextViewFeedback.setText("You don't have reservations");
                    //reservationFragmentTextViewFeedback.setVisibility(View.VISIBLE);
                    return;
                }

                flights.addAll(flightsResponse);
                adapter.notifyDataSetChanged();
                //reservationFragmentTextViewFeedback.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<List<Flight>> call, @NonNull Throwable throwable) {
                //ReservationFragmentTextViewFeedback.setText("Error fetching reservations: " + throwable.getMessage());
                Log.e("flights", "Error fetching flights: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setFlightId(flights.get(position).getId());
        reservationDTO.setUserId(ddrSINGLETON.getUserId());
        reservationDTO.setLuggage(1);
        Retrofit retrofit = RetrofitClient.getClient();
        DDRAPI ddrapi = retrofit.create(DDRAPI.class);
        Call<Void> call = ddrapi.addReservation(reservationDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("searchFlights", "Flight added successfully🎉");
                finish();



            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                Log.d("searchFlights", "Reservation failed🤨");

            }

        });
    }
//        ddrSINGLETON.getReservationsList().add(reservationDTO);




    }
