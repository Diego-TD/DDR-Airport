package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.Flight;
import com.ddr.logic.RetrofitClient;
import com.ddr.logic.util.FlightDTO;
import com.ddr.logic.util.ReservationDTO;
import com.ddr.ui.Reservations.RecyclerViewInterface;
import com.ddr.ui.Reservations.luggage;

import java.util.ArrayList;
import java.util.List;

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
    private Dialog loadingDialog;
    private int planeImage = R.drawable.tbgca201;
    private DDRS ddrSINGLETON;
    private ReservationDTO reservationDTO;

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

    public void getFlights(String departureAirport, String arrivalAirport, String date) {
        Retrofit retrofit = RetrofitClient.getClient();
        DDRAPI api = retrofit.create(DDRAPI.class);
        List<AirportCityCountries> airportCityCountriesList = ddrSINGLETON.getAirportCityCountriesList();
        Long departureAirportId = null, arrivalAirportId = null;

        for (AirportCityCountries airportCityCountries : airportCityCountriesList) {
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
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Flight>> call, @NonNull Response<List<Flight>> response) {
                List<Flight> flightsResponse = response.body();
                Log.d("Flights", "Received flights");
                assert flightsResponse != null;
                if (flightsResponse.isEmpty()) {
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
    public void onItemClick(int position) throws InterruptedException {
        long flightId = flights.get(position).getId();
        reservationDTO = new ReservationDTO();
        reservationDTO.setFlightId(flightId);
        reservationDTO.setUserId(ddrSINGLETON.getUserId());


                    Intent intent = new Intent(getApplicationContext(), luggage.class);
                    startActivityForResult(intent, 6);



    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6 && resultCode == Activity.RESULT_OK && data != null) {

            String luggage = data.getStringExtra("luggage");
            long flightId = reservationDTO.getFlightId();

            reservationDTO.setLuggage(luggage);
            Retrofit retrofit = RetrofitClient.getClient();
            DDRAPI ddrapi = retrofit.create(DDRAPI.class);
            Call<Void> call = ddrapi.addReservation(reservationDTO);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    Log.d("searchFlights", "Flight added successfully🎉");
                            Handler handler = new Handler();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(getApplicationContext(), Itinerary.class);
                                    i.putExtra("flightId", flightId);
                                    i.putExtra("luggage", luggage);
                                    startActivity(i);
                                    finish();
                                }},4000);







                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                    Log.d("searchFlights", "Reservation failed🤨");

                }

            });

        }
    }

}

