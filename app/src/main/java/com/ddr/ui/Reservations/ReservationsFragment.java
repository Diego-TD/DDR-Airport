package com.ddr.ui.Reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;
import com.ddr.databinding.FragmentReservationsBinding;

import java.util.ArrayList;

public class ReservationsFragment extends Fragment {

    private FragmentReservationsBinding binding;
    private ArrayList<ReservationsViewModel> reservationsViewModels = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewAdapter(getContext(), reservationsViewModels);
        setUpReservationsModel();
        recyclerView.setAdapter(adapter);


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

    public void setUpReservationsModel(){
        String[] origenes = {"Culiacan", "Mazatlan", "Mochis", "Guasave", "Ensenada", "Tijuana", "Mexicali", "CDMX"};
        String[] destinos = {"Paris", "Berlin", "Amsterdam", "Dubai", "Dortmund", "Munich", "Praga", "Dublin"};
        String[] horaSalida = {"10:00 AM", "6:00 AM", "3:00 PM", "8:00 PM", "12:00 AM", "11:00 AM", "6:00 PM", "10:00 PM"};
        String[] horaLlegada = {"12:00 AM", "11:00 AM", "6:00 PM", "10:00 PM", "10:00 AM", "6:00 AM", "3:00 PM", "8:00 PM"};
        String[] fecha = {"Sab, 08 Jun 2024", "Mie, 15 Sep 2024", "Dom, 01 Agosto 2024", "Lun, 20 Jul 2024", "Mar, 11 May 2024", "Jue, 30 Nov 2024", "Sab, 25 Dic 2024", "Vie, 13 Oct 2024"};
        String[] numVuelo = {"78adn43", "65nj24d", "ka72n43", "mlf09f7g", "3m2ns6s", "cbgs342", "cnjddg7", "xcs532"};

        for (int i = 0; i < origenes.length; i++) {
            reservationsViewModels.add(new ReservationsViewModel(destinos[i], origenes[i], horaSalida[i], horaLlegada[i], numVuelo[i], fecha[i]));
        }

        //adapter.notifyDataSetChanged();
    }

    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/
}