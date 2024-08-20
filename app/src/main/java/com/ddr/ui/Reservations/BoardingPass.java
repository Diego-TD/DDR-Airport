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

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardingPass extends AppCompatActivity {

    private TextView departureTime, arrivalTime, origin, destiny, date, numberFlight, luggageType, price;
    private Button personalize,accept;
    private Context contexto;
    private DDRS ddrSINGLETON;
    private Long reservationId;

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
        accept = findViewById(R.id.acceptButton);
        ddrSINGLETON = DDRS.getDDRSINGLETON(this);

        String destino = getIntent().getStringExtra("destiny");
        String origen = getIntent().getStringExtra("origin");
        String fecha = getIntent().getStringExtra("date");
        String llegada = getIntent().getStringExtra("arrive");
        String salida = getIntent().getStringExtra("departure");
        long numero = getIntent().getLongExtra("number", -1);
        String maleta = getIntent().getStringExtra("maleta");

        departureTime.setText(salida);
        arrivalTime.setText(llegada);
        origin.setText(origen);
        destiny.setText(destino);
        date.setText(fecha);
        numberFlight.setText(String.valueOf(numero));
        luggageType.setText(maleta);
        price.setText("$" + getIntent().getStringExtra("price"));

        reservationId = getIntent().getLongExtra("reservationId", -1);

        personalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Luggage.class);
                intent.putExtra("maleta", luggageType.getText().toString());
                startActivityForResult(intent, 1);
            }
        });
accept.setOnClickListener(v -> {
    finish();
});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (data != null) {
                    String luggage = data.getStringExtra("luggage");
                    luggageType.setText(luggage);
                    setLuggage(luggage);
                    accept.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void setLuggage(String luggage) {
        Retrofit retrofit = RetrofitClient.getClient(); // Obtener instancia de Retrofit
        DDRAPI api = retrofit.create(DDRAPI.class); // Crear instancia de la interfaz DDRAPI

        // Verificar si el ID de reserva es válido
        if (reservationId != -1) {
            // Paso 1: Obtener la reserva existente desde el servidor
            Call<Reservation> getCall = api.getReservation(reservationId);
            getCall.enqueue(new Callback<Reservation>() {
                @Override
                public void onResponse(@NonNull Call<Reservation> call, @NonNull Response<Reservation> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Paso 2: Actualizar la información de equipaje en la reserva obtenida
                        Reservation reservation = response.body();
                        reservation.setLuggage(luggage);

                        // Paso 3: Hacer una llamada PUT para actualizar la reserva con la nueva información de equipaje
                        Call<Reservation> updateCall = api.updateReservation(reservation.getId(), reservation);
                        updateCall.enqueue(new Callback<Reservation>() {
                            @Override
                            public void onResponse(@NonNull Call<Reservation> call, @NonNull Response<Reservation> response) {
                                if (response.isSuccessful()) {
                                    // Actualización exitosa
                                    Log.d("setLuggage", "¡La información de equipaje se ha actualizado correctamente!");
                                    Log.d("Reservation id", String.valueOf(reservationId));
                                } else {
                                    // Error al actualizar la reserva
                                    Log.e("setLuggage", "Error al actualizar la reserva: " + response.message());
                                    try {
                                        if (response.errorBody() != null) {
                                            Log.e("setLuggage", "Error body: " + response.errorBody().string());
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Reservation> call, @NonNull Throwable t) {
                                // Error en la llamada (red, conversión de datos, etc.)
                                Log.e("setLuggage", "Error en la llamada de actualización: " + t.getMessage());
                            }
                        });
                    } else {
                        // Error al obtener la reserva
                        Log.e("setLuggage", "Error al obtener la reserva: " + response.message());
                        try {
                            if (response.errorBody() != null) {
                                Log.e("setLuggage", "Error body: " + response.errorBody().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Reservation> call, @NonNull Throwable t) {
                    // Error en la llamada (red, conversión de datos, etc.)
                    Log.e("setLuggage", "Error en la llamada de obtención de reserva: " + t.getMessage());
                }
            });
        } else {
            // ID de reserva inválido
            Log.e("setLuggage", "ID de reserva inválido");
        }
    }

}
