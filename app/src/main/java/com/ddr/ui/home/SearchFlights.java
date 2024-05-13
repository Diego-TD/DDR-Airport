package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kotlin.random.Random;

public class SearchFlights extends AppCompatActivity implements RecycleViewInterface{

private     SearchFlightsRecycleViewAdapter searchFlightsRecycleViewAdapter;
    private RecyclerView recycleView;
    ArrayList<PlaneModel> planeModels;
    String cityFromText,cityToText,timeToGo,timeToArrive;
    private CircularLinkedList<String> time;
    TextView departingFlights;
    private  int planeImage =R.drawable.tbgca201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_flights);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            recycleView = findViewById(R.id.flightsRecyclerView);
            departingFlights = findViewById(R.id.departingText);
            recycleView.setLayoutManager(new LinearLayoutManager(this));
            time = new CircularLinkedList<>();
generateRandomTimes(time);
            setUpPlanes();
searchFlightsRecycleViewAdapter = new SearchFlightsRecycleViewAdapter(this,planeModels,this);
            recycleView.setAdapter(searchFlightsRecycleViewAdapter);
            Intent in  = getIntent();
            if (in != null){
                String cityFromTextPrev = in.getStringExtra("fromTxt");
                String cityToTextPrev = in.getStringExtra("toTxt");
            cityFromText =cityFromTextPrev;
            cityToText =cityToTextPrev;

            }
            String departingFlightsText = getString(R.string.departing_flights_text, cityFromText, cityToText);

            departingFlights.setText(departingFlightsText);
            return insets;
        });
    }

    @Override
    public void OnClickItem(String text) {
        Intent in = new Intent(this,ChoseHowToTravel.class);
        in.putExtra("cityFromText",cityFromText);
        in.putExtra("cityToText",cityToText);
        in.putExtra("timeToGo",timeToGo);
        in.putExtra("timeToArrive",timeToArrive);

    }
    private void setUpPlanes() {
        planeModels = new ArrayList<>();

        // Obtener los recursos de los aeropuertos de salida y llegada
        String fromAirports = cityFromText;
        String toAirports = cityToText;


        // Iterar sobre los arreglos y crear los objetos PlaneModel
        for (int i = 0; i < 27; i++) {
            planeModels.add(new PlaneModel(fromAirports, toAirports,time.getCurrentData(), time.getCurrentData(),  planeImage));
            // Avanzar al siguiente tiempo
        }
    }

    private static void generateRandomTimes(CircularLinkedList<String> timeList) {
        // Crear un objeto Random
        Random random = new Random() {
            @Override
            public int nextBits(int i) {
                return 0;
            }
        };

        // Generar 5 horas aleatorias
        for (int i = 0; i < 5; i++) {
            // Generar una hora aleatoria entre 0 y 23 para la hora
            int hour = random.nextInt(24);

            // Generar una hora aleatoria entre 0 y 59 para los minutos
            int minute = random.nextInt(60);

            // Formatear la hora y los minutos en un string
            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", hour, minute);

            // Añadir la hora al ArrayList
            timeList.add(time);
        }
    }
}