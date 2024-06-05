package com.ddr.ui.Reservations;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;
import com.ddr.databinding.FragmentReservationsBinding;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.City;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.Reservation;
import com.ddr.logic.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReservationsFragment extends Fragment implements RecyclerViewInterface{
    private DDRS ddrSINGLETON;
    private FragmentReservationsBinding binding;
    protected ArrayList<Reservation> reservationsViewModels = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private TextView reservationFragmentTextViewFeedback;

    public ReservationsFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ddrSINGLETON = DDRS.getDDRSINGLETON(getActivity().getApplicationContext());
        getReservations();

        recyclerView = root.findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewAdapter(getContext(), reservationsViewModels, this);
        recyclerView.setAdapter(adapter);
        reservationFragmentTextViewFeedback = root.findViewById(R.id.reservationFragmentTextViewFeedback);
        reservationFragmentTextViewFeedback.bringToFront();
        //reservationsViewModels.add(new ReservationsViewModel("No Hay Vuelos ", "No Hay Vuelos", "No Hay Vuelos ", "No Hay Vuelos ", "No Hay Vuelos ", "No Hay Vuelos ", vueloId,"3"));
//        setUpReservationsModel();

        return root;
    }

    public void getReservations(){
        Retrofit retrofit = RetrofitClient.getClient();
        DDRAPI api = retrofit.create(DDRAPI.class);
        Call<List<Reservation>> call = api.getReservationsByUser(ddrSINGLETON.getUserId());

        call.enqueue(new Callback<List<Reservation>>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<List<Reservation>> call, @NonNull Response<List<Reservation>> response) {
                List<Reservation> reservations = response.body();
                Log.d("ReservationsFragment", "Received reservations" );
                assert reservations != null;
                if (reservations.isEmpty()){
                    reservationFragmentTextViewFeedback.setText("You don't have reservations");
                    reservationFragmentTextViewFeedback.setVisibility(View.VISIBLE);
                    return;
                }

                reservationsViewModels.addAll(reservations);
                adapter.notifyDataSetChanged();
                //TODO: Implement some UI to provide feedback, like, here we could hidden a textview
                reservationFragmentTextViewFeedback.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NonNull Call<List<Reservation>> call, @NonNull Throwable throwable) {
                reservationFragmentTextViewFeedback.setText("Error fetching reservations: " + throwable.getMessage());
                Log.e("ReservationsFragment", "Error fetching reservations: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        List<AirportCityCountries> airportCityCountriesList = ddrSINGLETON.getAirportCityCountriesList();
        City departureCity = new City();
        City arrivalCity = new City();

        for (AirportCityCountries airportCityCountries : airportCityCountriesList) {
            if (Objects.equals(airportCityCountries.getAirport().getName(), reservationsViewModels.get(position).getFlight().getDepartureAirport().getName())){
                departureCity = airportCityCountries.getCity();
            }
            if (Objects.equals(airportCityCountries.getAirport().getName(), reservationsViewModels.get(position).getFlight().getArrivalAirport().getName())){
                arrivalCity = airportCityCountries.getCity();
            }
        }

        Intent intent = new Intent(requireContext(), BoardingPass.class);
        //startActivityForResult(intent, position);
        intent.putExtra("destiny", departureCity.getName());
        intent.putExtra("origin", arrivalCity.getName());
        intent.putExtra("date", reservationsViewModels.get(position).getFlight().getDate());
        intent.putExtra("arrive", reservationsViewModels.get(position).getFlight().getArrivalTime());
        intent.putExtra("departure", reservationsViewModels.get(position).getFlight().getDepartureTime());
        intent.putExtra("number", reservationsViewModels.get(position).getFlight().getId());
        intent.putExtra("maleta", reservationsViewModels.get(position).getLuggage());
        intent.putExtra("price", reservationsViewModels.get(position).getTotal().toString());

        //Log.d("ReservationsFragment", "Starting BoardingPass activity");
        startActivity(intent);
    }
}