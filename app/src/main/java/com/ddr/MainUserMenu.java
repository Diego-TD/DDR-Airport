package com.ddr;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.ddr.logic.Calls;
import com.ddr.logic.DDRS;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ddr.databinding.ActivityMainMenuBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainUserMenu extends AppCompatActivity {

    private ActivityMainMenuBinding binding;
    private DDRS ddrSINGLETON;
    private SimpleDateFormat timeFormat;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        time = findViewById(R.id.timeWatch);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_flights, R.id.navigation_reservations, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        this.ddrSINGLETON = DDRS.getDDRSINGLETON(getApplicationContext());

        Calls.getAirportCityCountries(this);
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateTime());
            }
        };
        timer.schedule(timerTask, 0, 1000);

    }

    @SuppressLint("SimpleDateFormat")
    private void updateTime() {
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = timeFormat.format(new Date());

        time.setText(currentTime);

    }

}