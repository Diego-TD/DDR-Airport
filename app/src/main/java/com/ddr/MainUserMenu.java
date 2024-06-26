package com.ddr;


import android.os.Bundle;

import com.ddr.logic.Calls;
import com.ddr.logic.DDRS;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ddr.databinding.ActivityMainMenuBinding;

public class MainUserMenu extends AppCompatActivity {

    private ActivityMainMenuBinding binding;
    private DDRS ddrSINGLETON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_flights, R.id.navigation_reservations, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        this.ddrSINGLETON = DDRS.getDDRSINGLETON(getApplicationContext());

        Calls.getAirportCityCountries(this);
    }

}