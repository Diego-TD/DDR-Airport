package com.ddr.ui.Reservations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ddr.R;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.Reservation;
import com.ddr.logic.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardingPass extends AppCompatActivity {

    private TextView departureTime, arrivalTime, origin, destiny, date, numberFlight,luggageType, price;
    private Button personalize;
    private Context contexto;
    private DDRS ddrSINGLETON;

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
        ddrSINGLETON = DDRS.getDDRSINGLETON(this);
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
                Intent intent = new Intent(getApplicationContext(), luggage.class);
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
                    setLuggage(luggage);
                }
            }
        }
    }
    public void setLuggage(String luggage) {
        Retrofit retrofit = RetrofitClient.getClient();
        DDRAPI api = retrofit.create(DDRAPI.class);

        // Verificar si la lista de reservaciones no es null
        if (ddrSINGLETON.getReservationsList() != null && !ddrSINGLETON.getReservationsList().isEmpty()) {
            long reservationId = ddrSINGLETON.getReservationsList().get(0).getId(); // Ajusta esto según tus necesidades

            // Obtener la reserva existente
            Call<Reservation> getCall = api.getReservation(reservationId);
            getCall.enqueue(new Callback<Reservation>() {
                @Override
                public void onResponse(@NonNull Call<Reservation> call, @NonNull Response<Reservation> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Reservation reservation = response.body();
                        // Actualizar el campo luggage
                        reservation.setLuggage(luggage);

                        // Enviar la reserva actualizada al servidor
                        Call<Reservation> updateCall = api.updateReservation(reservationId, reservation);
                        updateCall.enqueue(new Callback<Reservation>() {
                            @Override
                            public void onResponse(@NonNull Call<Reservation> call, @NonNull Response<Reservation> response) {
                                if (response.isSuccessful()) {
                                    Log.d("setLuggage", "Luggage updated successfully");
                                } else {
                                    Log.e("setLuggage", "Failed to update luggage: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Reservation> call, @NonNull Throwable t) {
                                Log.e("setLuggage", "Error updating luggage: " + t.getMessage());
                            }
                        });
                    } else {
                        Log.e("setLuggage", "Failed to get reservation: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Reservation> call, @NonNull Throwable t) {
                    Log.e("setLuggage", "Error getting reservation: " + t.getMessage());
                }
            });
        }   else {
            Log.e("setLuggage", "Reservations list is null or empty");
        }
    }




}
