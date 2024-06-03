package com.ddr;

import android.content.Intent;
import android.os.Bundle;

import com.ddr.ui.Reservations.ReservationsFragment;
import com.ddr.ui.Reservations.ReservationsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ddr.databinding.ActivityMainMenuBinding;
import com.ddr.ui.Reservations.ReservationsFragment;

import java.util.ArrayList;

public class MainUserMenu extends AppCompatActivity {

    private ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent flightsEditor = getIntent();
//        try {
//        String fromTextPrev = flightsEditor.getStringExtra("fromTxt");
//        String toTextPrev = flightsEditor.getStringExtra("toTxt");
//        String toTxt = flightsEditor.getStringExtra("toTxt");
//        String fromTxt = flightsEditor.getStringExtra("fromTxt");
//
//        String isRoundTrip = flightsEditor.getStringExtra("isRoundTrip");
//        String isOneWay = flightsEditor.getStringExtra("isOneWay");
//            if ( isRoundTrip != null&&isRoundTrip.equals("true")) {
//            ReservationsFragment reservationsFragment = new ReservationsFragment();
//            reservationsFragment.onCreate(savedInstanceState);
//            ArrayList<ReservationsViewModel> reservationsViewModels = new ArrayList<>();
//            reservationsViewModels.add(new ReservationsViewModel(toTxt, fromTxt,"12:30", "13:30","1","",23));
//            reservationsFragment.setReservationsViewModels(reservationsViewModels);
//
//
//        }     else {
//
//                if (isRoundTrip != null &&isOneWay.equals("true")){
//                ReservationsFragment reservationsFragment = new ReservationsFragment();
//                ArrayList<ReservationsViewModel> reservationsViewModels = new ArrayList<>();
//                reservationsViewModels.add(new ReservationsViewModel(toTxt, fromTxt,"12:30", "13:30","1","",23));
//                reservationsFragment.setReservationsViewModels(reservationsViewModels);
//
//
//                }
//            }
//
//        }catch (Exception e){
//
//        }



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_flights, R.id.navigation_reservations, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}