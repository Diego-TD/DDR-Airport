package com.ddr.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ddr.databinding.FragmentAccountBinding;
import com.ddr.logic.Airport;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        //http request to the API
        getAirportCall(binding.textNotifications);
        accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void getAirportCall(TextView textView) {
        Retrofit retrofit = RetrofitClient.getClient();

        DDRAPI api = retrofit.create(DDRAPI.class);

        Call<Airport> call = api.getAirport(1);
        call.enqueue(new Callback<Airport>() {
            @Override
            public void onResponse(@NonNull Call<Airport> call, @NonNull Response<Airport> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    Log.d("AirportFragment", "onResponseError");
                    return;
                }

                Airport airport = response.body();
                assert airport != null;
//                Log.d("AirportFragment", "Airport data: " + airport.toString(airport));
//                textView.post(() -> {
//                    textView.setText(airport.toString(airport));
//                });
            }

            @Override
            public void onFailure(@NonNull Call<Airport> call, @NonNull Throwable t) {
                // Handle failure
                Log.d("AirportFragment", "onFailure: " + t.getMessage());
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}