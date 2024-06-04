package com.ddr.ui.Reservations;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

import java.security.PublicKey;
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
    private int vueloId = R.drawable.vuelo;
    private RecyclerView recyclerView;
    private TextView reservationFragmentTextViewFeedback;

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }


    public ArrayList<Reservation> getReservationsViewModels() {
        return reservationsViewModels;
    }

    public void setReservationsViewModels(ArrayList<Reservation> reservationsViewModels) {
        this.reservationsViewModels = reservationsViewModels;
    }

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


    /*public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReservationsViewModel reservationsViewModel =
                new ViewModelProvider(this).get(ReservationsViewModel.class);

        binding = FragmentReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        reservationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
        RecyclerView recyclerView = findViewById(R.id.destiny);
        setUpReservationsModel();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, reservationsViewModels);

        return null;
    }*/
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

//    public void setUpReservationsModel(){
//        String[] origenes = {"Culiacan", "Mazatlan", "Mochis", "Guasave", "Ensenada", "Tijuana", "Mexicali", "CDMX"};
//        String[] destinos = {"Paris", "Berlin", "Amsterdam", "Dubai", "Dortmund", "Munich", "Praga", "Dublin"};
//        String[] horaSalida = {"10:00 AM", "6:00 AM", "3:00 PM", "8:00 PM", "12:00 AM", "11:00 AM", "6:00 PM", "10:00 PM"};
//        String[] horaLlegada = {"12:00 AM", "11:00 AM", "6:00 PM", "10:00 PM", "10:00 AM", "6:00 AM", "3:00 PM", "8:00 PM"};
//        String[] fecha = {"Sab, 08 Jun 2024", "Mie, 15 Sep 2024", "Dom, 01 Agosto 2024", "Lun, 20 Jul 2024", "Mar, 11 May 2024", "Jue, 30 Nov 2024", "Sab, 25 Dic 2024", "Vie, 13 Oct 2024"};
//        String[] numVuelo = {"78adn43", "65nj24d", "ka72n43", "mlf09f7g", "3m2ns6s", "cbgs342", "cnjddg7", "xcs532"};
//        String[] maleta = {"Zero", "Zero", "Zero", "Zero", "Zero", "Zero", "Zero", "Zero"};
//        for (int i = 0; i < origenes.length; i++) {
//            reservationsViewModels.add(new ReservationsViewModel(destinos[i], origenes[i], horaSalida[i], horaLlegada[i], numVuelo[i], fecha[i], vueloId, maleta[i]));
//        }
//
//    }
//
//    public void setUpReservationsModel(){
//        String[] origenes = {"Culiacan", "Mazatlan", "Mochis", "Guasave", "Ensenada", "Tijuana", "Mexicali", "CDMX"};
//        String[] destinos = {"Paris", "Berlin", "Amsterdam", "Dubai", "Dortmund", "Munich", "Praga", "Dublin"};
//        String[] horaSalida = {"10:00 AM", "6:00 AM", "3:00 PM", "8:00 PM", "12:00 AM", "11:00 AM", "6:00 PM", "10:00 PM"};
//        String[] horaLlegada = {"12:00 AM", "11:00 AM", "6:00 PM", "10:00 PM", "10:00 AM", "6:00 AM", "3:00 PM", "8:00 PM"};
//        String[] fecha = {"Sab, 08 Jun 2024", "Mie, 15 Sep 2024", "Dom, 01 Agosto 2024", "Lun, 20 Jul 2024", "Mar, 11 May 2024", "Jue, 30 Nov 2024", "Sab, 25 Dic 2024", "Vie, 13 Oct 2024"};
//        String[] numVuelo = {"78adn43", "65nj24d", "ka72n43", "mlf09f7g", "3m2ns6s", "cbgs342", "cnjddg7", "xcs532"};
//
//        for (int i = 0; i < 2; i++) {
//            reservationsViewModels.add(new ReservationsViewModel(destinos[i], origenes[i], horaSalida[i], horaLlegada[i], numVuelo[i], fecha[i], vueloId));
//        }
//
//        //adapter.notifyDataSetChanged();
//    }

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
        intent.putExtra("arrive", reservationsViewModels.get(position).getFlight().getTime());
        intent.putExtra("departure", reservationsViewModels.get(position).getFlight().getTime());
        intent.putExtra("number", reservationsViewModels.get(position).getFlight().getId());
        intent.putExtra("maleta", reservationsViewModels.get(position).getLuggage());

        //Log.d("ReservationsFragment", "Starting BoardingPass activity");
        startActivity(intent);
    }




    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            String newLuggage = data.getStringExtra("newLuggage");
            if (newLuggage != null) {
                reservationsViewModels.get(requestCode).setLuggage(newLuggage);
                adapter.notifyItemChanged(requestCode);
            }
        }
    }/*



    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/
}