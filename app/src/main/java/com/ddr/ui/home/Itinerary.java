package com.ddr.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ddr.R;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.Flight;
import com.ddr.logic.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Itinerary extends AppCompatActivity {
    private Flight flight;
    private long flightId;
    private TextView departureTime, arrivalTime, origin, destiny, numberFlight, dateDay, price,luggageText,luggageInfo;
private  ImageView imageView7;
    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itinerary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            departureTime= findViewById(R.id.departureTime);
            arrivalTime = findViewById(R.id.arrivalTime);
            origin = findViewById(R.id.origin);
            destiny = findViewById(R.id.destiny);
            numberFlight = findViewById(R.id.numberFlight);
            dateDay = findViewById(R.id.dateDay);
            price = findViewById(R.id.price);
            luggageText = findViewById(R.id.luggageText);
            luggageInfo = findViewById(R.id.luggageInfo);
            imageView7 = findViewById(R.id.imageView7);

                setLuggage();
                setFlight();



            return insets;

        });
    }
    public void setFlight() {
        Intent in = getIntent();
        flight = new Flight();
        flightId = in.getLongExtra("flightId", 1);
        fetchFlightDetails(0);

    }
    public void fetchFlightDetails(int attempts) {
        Retrofit retrofit = RetrofitClient.getClient();
        DDRS ddrSINGLETON = DDRS.getDDRSINGLETON(this);

        DDRAPI api = retrofit.create(DDRAPI.class);
        Call<Flight> call = api.getFlight(flightId);
        List<AirportCityCountries> airportCityCountriesList = ddrSINGLETON.getAirportCityCountriesList();

        call.enqueue(new Callback<Flight>() {
            @Override
            public void onResponse(@NonNull Call<Flight> call, @NonNull Response<Flight> response) {
                Log.d("response itinerary", response.toString());
                flight = response.body();
                if (flight != null) {
                    for(AirportCityCountries airport : airportCityCountriesList)
                    {
                        if(airport.getAirport().getName().equals(flight.getDepartureAirport().getName())){
                            origin.setText(airport.getCity().getName());
                        }
                        if(airport.getAirport().getName().equals(flight.getArrivalAirport().getName())){
                            destiny.setText(airport.getCity().getName());
                        }
                    }
                    departureTime.setText(flight.getDepartureTime());
                    arrivalTime.setText(flight.getArrivalTime());
                    numberFlight.setText(String.valueOf(flight.getId()));
                    dateDay.setText(flight.getDate());
                    price.setText(String.valueOf(flight.getPrice()));
                }
                else {
                    Log.d("response itinerary 1 ", "Flight is null");
                    Toast.makeText(Itinerary.this, "Flight is null", Toast.LENGTH_SHORT).show();
                    retryFetchFlightDetails(attempts);
                }


            }

            @Override
            public void onFailure(@NonNull Call<Flight> call, @NonNull Throwable throwable) {
                Log.d("response itinerary 2 ", throwable.toString());
                Toast.makeText(Itinerary.this, "Failed to fetch flight details", Toast.LENGTH_SHORT).show();
                retryFetchFlightDetails(attempts);
            }


        });
    }
    public void setLuggage(){
        Intent in = getIntent();
        String luggage = in.getStringExtra("luggage");
        switch (Objects.requireNonNull(luggage)){
            case "Zero":
                luggageText.setText(luggage);
                luggageInfo.setText(R.string.luggageZero);
                imageView7.setImageResource(R.drawable.maleta_removebg_previewzero);
                break;
            case "Basic":
                luggageText.setText(luggage);
                luggageInfo.setText(R.string.luggageBasic);
                imageView7.setImageResource(R.drawable.__maletas_removebg_preview_basic);
                break;
            case "Plus":
                luggageText.setText(luggage);
                luggageInfo.setText(R.string.luggagePlus);
                imageView7.setImageResource(R.drawable.maletas_removebg_previewplus);
                break;
            default:
                luggageText.setText("null");
                luggageInfo.setText("null");
                imageView7.setImageResource(R.drawable.ojo);


        }
    }

public void retryFetchFlightDetails(int attempts) {
    if (attempts < MAX_RETRIES) {
        new android.os.Handler().postDelayed(() -> {
            fetchFlightDetails(attempts + 1);
        }, RETRY_DELAY);
    } else {
        Log.d("response itinerary", "Failed to fetch flight details and reached max attempts");
        Toast.makeText(this, "Failed to fetch flight details", Toast.LENGTH_SHORT).show();
    }

}}


