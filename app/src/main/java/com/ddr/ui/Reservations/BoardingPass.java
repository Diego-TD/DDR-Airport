package com.ddr.ui.Reservations;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ddr.R;

public class BoardingPass extends AppCompatActivity {

    private TextView departureTime;
    private TextView arrivalTime;
    private TextView origin;
    private TextView destiny;
    private TextView date;
    private TextView numberFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.coso_on_click);

        departureTime = findViewById(R.id.departureTimePass);
        arrivalTime = findViewById(R.id.arrivalTimePass);
        origin = findViewById(R.id.originPass);
        destiny = findViewById(R.id.destinyPass);
        date = findViewById(R.id.dateDayPass);
        numberFlight = findViewById(R.id.numberFlightPass);

        String destino = getIntent().getStringExtra("destiny");
        String origen = getIntent().getStringExtra("origin");
        String fecha = getIntent().getStringExtra("date");
        String llegada = getIntent().getStringExtra("arrive");
        String salida = getIntent().getStringExtra("departure");
        String numero = getIntent().getStringExtra("number");

        departureTime.setText(salida);
        arrivalTime.setText(llegada);
        origin.setText(origen);
        destiny.setText(destino);
        date.setText(fecha);
        numberFlight.setText(numero);
    }
}
