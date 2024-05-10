package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;

import java.util.ArrayList;

public class SearchFlights extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PlaneModel> planeModels;
    private int planeImage = R.drawable.baseline_airplanemode_active_24;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);
        EdgeToEdge.enable(this);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar modelos de aviones
        setUpPlanes();

        // Crear y configurar adaptador para el RecyclerView
        RecycleViewAdapter adapter = new RecycleViewAdapter(this, planeModels);
        recyclerView.setAdapter(adapter);

        // Aplicar padding para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.searchFlights), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setUpPlanes() {
        planeModels = new ArrayList<>();
        String[] fromTxt = getResources().getStringArray(R.array.info);
        for (String cityName : fromTxt) {
            Log.d("PlaneModel", "City name: " + cityName);
            planeModels.add(new PlaneModel(cityName, planeImage));
        }
    }
}
