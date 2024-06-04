package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ddr.MainUserMenu;
import com.ddr.R;
import com.ddr.logic.Airport;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.RetrofitClient;
import com.ddr.logic.util.FlightDTO;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class oneWay extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView departure;
    private TextView fromTextView;
    private TextView toTextView;
    private Button searchButton;
    private Date dateToSearch;
    protected boolean isOneWay = false;

    public oneWay() {
        // Required empty public constructor
    }

    public static oneWay newInstance(String param1, String param2) {
        oneWay fragment = new oneWay();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one_way, container, false);
        departure = rootView.findViewById(R.id.departure);
        fromTextView = rootView.findViewById(R.id.fromTextView);
        toTextView = rootView.findViewById(R.id.toTextView);
        searchButton = rootView.findViewById(R.id.search_button);

        if (getArguments() != null) {
            String cityName = getArguments().getString("cityName");
            fromTextView.setText(cityName);
        }
        fromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(requireContext(), SearchAirports.class);


                startActivityForResult(in, 1);
            }
        });

        toTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), SearchAirports.class);
                startActivityForResult(in, 2);
            }
        });
        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalendarPopup();
                isOneWay = true;
            }
        });

        searchButton.setOnClickListener(v -> {
            Retrofit retrofit = RetrofitClient.getClient();
            DDRAPI api = retrofit.create(DDRAPI.class);
            DDRS ddrSINGLETON = DDRS.getDDRSINGLETON(getContext());
            List<Airport> airports = new ArrayList<>();
            Airport departureAirport = null;
            Airport arrivalAirport = null;


            for (AirportCityCountries airportCityCountries:
                    ddrSINGLETON.getAirportCityCountriesList()
                ) {
                airports.add(airportCityCountries.getAirport());
            }

            for (Airport airport:airports){
                if (airport.getName().equals(fromTextView.getText().toString())){
                    departureAirport = airport;

                }

                if (airport.getName().equals(toTextView.getText().toString())){
                    arrivalAirport = airport;
                }

            }

            Time[] timeArray = new Time[4];

            // Asignando valores de tiempo específicos
            timeArray[0] = Time.valueOf("09:00:00");  // 09:00 AM
            timeArray[1] = Time.valueOf("12:30:00"); // 12:30 PM
            timeArray[2] = Time.valueOf("15:45:00"); // 03:45 PM
            timeArray[3] = Time.valueOf("18:15:00"); // 06:15 PM


            for (int i = 0; i < 4; i++) {
                FlightDTO flightDTO = new FlightDTO();
                flightDTO.setDate(dateToSearch);
                flightDTO.setDepartureAirportId(departureAirport.getId());
                flightDTO.setArrivalAirportId(arrivalAirport.getId());
                flightDTO.setTime(timeArray[i]);
                flightDTO.setAirplaneId(1L);

                Call<Void> call = api.addFlight(flightDTO); //TODO: doesn't create the things

                call.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent in = new Intent(getContext(), MainUserMenu.class);
                        startActivity(in);
                        in.putExtra("isOneWay", isOneWay);
                        in.putExtra("fromTxt", fromTextView.getText().toString());
                        in.putExtra("toTxt", toTextView.getText().toString());
                        startActivityForResult(in, 3);
                        Log.d("searchFlights", "Flight added successfully");

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Log.d("searchFlights", "Failed to search flights");

                    }
                });
            }

        });
        return rootView;
    }


    private void showCalendarPopup() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateToSearch = new Date(year, month + 1, dayOfMonth);
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                departure.setText(selectedDate);

            }
        }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Manejar el resultado de la actividad secundaria aquí
        if (requestCode == 1 ) {
            if (resultCode == Activity.RESULT_OK) {
                // El resultado fue exitoso, obtener los datos y actualizar la interfaz de usuario
                if (data != null) {
                    String cityName = data.getStringExtra("cityName");
                    // Actualizar la vista con el resultado recibido
                    fromTextView.setText(cityName);
                }
            }
        }else if( requestCode == 2){
            if (resultCode == Activity.RESULT_OK){
                if (data != null){
                    String cityName = data.getStringExtra("cityName");
                    toTextView.setText(cityName);
                }
            }
        }

    }

}
