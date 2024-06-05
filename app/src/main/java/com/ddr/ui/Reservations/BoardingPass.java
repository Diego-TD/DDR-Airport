package com.ddr.ui.Reservations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ddr.R;

public class BoardingPass extends AppCompatActivity {

    private TextView departureTime, arrivalTime, origin, destiny, date, numberFlight,luggageType, price;
    private Button personalize;
    private Context contexto;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.coso_on_click);

        contexto = this;
        departureTime = findViewById(R.id.departureTimePass);
        arrivalTime = findViewById(R.id.arrivalTimePass);
        origin = findViewById(R.id.originPass);
        destiny = findViewById(R.id.destinyPass);
        date = findViewById(R.id.dateDayPass);
        numberFlight = findViewById(R.id.numberFlightPass);
        personalize = findViewById(R.id.personalizeButton);
        luggageType = findViewById(R.id.typeLuggage);
        price = findViewById(R.id.priceTicket);

        String destino = getIntent().getStringExtra("destiny");
        String origen = getIntent().getStringExtra("origin");
        String fecha = getIntent().getStringExtra("date");
        String llegada = getIntent().getStringExtra("arrive");
        String salida = getIntent().getStringExtra("departure");
        String numero = getIntent().getStringExtra("number");

        String maleta = getIntent().getStringExtra("luggage");

        departureTime.setText(salida);
        arrivalTime.setText(llegada);
        origin.setText(origen);
        destiny.setText(destino);
        date.setText(fecha);
        numberFlight.setText(numero);
        luggageType.setText(maleta);

        price.setText("$"+getIntent().getStringExtra("price"));
        personalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, luggage.class);
                intent.putExtra("maleta", luggageType.getText());
                startActivityForResult(intent,1);
            }
        });

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String newLuggage = data.getStringExtra("luggage");
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newLuggage", newLuggage);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == AppCompatActivity.RESULT_OK){
                if (data != null){
                    String luggage = data.getStringExtra("luggage");
                    luggageType.setText(luggage);
                }
            }
        }
    }




}
