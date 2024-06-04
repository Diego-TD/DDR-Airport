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
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.Flight;
import com.ddr.logic.Reservation;
import com.ddr.logic.RetrofitClient;
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
    private int vueloId = R.drawable.vuelo;
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
            getFlights();

           Intent in = getIntent();
            String cityFromText = "";
            String cityToText = "";
            if (in != null) {
               String cityFromTextPrev = in.getStringExtra("fromTxt");
               String cityToTextPrev = in.getStringExtra("toTxt");
                cityFromText = cityFromTextPrev;
                cityToText = cityToTextPrev;

            }
            String departingFlightsText = getString(R.string.departing_flights_text, cityFromText, cityToText);

            departingFlights.setText(departingFlightsText);
            return insets;
        });
    }

    public void getFlights(){
        Retrofit retrofit = RetrofitClient.getClient();
        DDRAPI api = retrofit.create(DDRAPI.class);

        Call<List<Flight>> call = api.getFlights();
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
            public void onFailure(Call<List<Flight>> call, Throwable throwable) {
                //ReservationFragmentTextViewFeedback.setText("Error fetching reservations: " + throwable.getMessage());
                Log.e("flights", "Error fetching flights: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {



        finish();
    }
}