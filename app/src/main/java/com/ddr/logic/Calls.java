package com.ddr.logic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Calls {
    public static void getAirportCityCountries(Context context) {
        Retrofit retrofit = RetrofitClient.getClient();

        DDRAPI api = retrofit.create(DDRAPI.class);
        Call<List<AirportCityCountries>> call = api.getAirportCityCountries();

        call.enqueue(new Callback<List<AirportCityCountries>>() {

            @Override
            public void onResponse(@NonNull Call<List<AirportCityCountries>> call, @NonNull Response<List<AirportCityCountries>> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.d("AirportCityCountries", "onResponseError");
                    return;
                }

                List<AirportCityCountries> airportCityCountries = response.body();
                if (airportCityCountries != null) {
                    DDRS.getDDRSINGLETON(context).setAirportCityCountriesList(airportCityCountries);
                    Log.d("AirportCityCountries", "Tabien 👍");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AirportCityCountries>> call, @NonNull Throwable throwable) {
                Log.d("AirportCityCountries", "onFailure: 🤨🤨🤨" + throwable.getMessage());
            }
        });
    }
}
