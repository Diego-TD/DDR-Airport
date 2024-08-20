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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ddr.R;
import com.ddr.logic.Airport;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.Flight;
import com.ddr.logic.RetrofitClient;
import com.ddr.logic.util.FlightDTO;

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
    private static final String FLIGHT_TYPE = "ONE_WAY";

    private String mParam1;
    private String mParam2;
    private TextView departure;
    private TextView fromTextView;
    private TextView toTextView;
    private Button searchButton;
    private String dateToSearch;

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
        String[] randomHoursArray = getResources().getStringArray(R.array.random_hours_array);




        if (getArguments() != null) {
            String cityName = getArguments().getString("cityName");
            fromTextView.setText(cityName);
        }
        fromTextView.setOnClickListener(v -> {
            Intent in = new Intent(requireContext(), SearchAirports.class);
            startActivityForResult(in, 1);
        });

        toTextView.setOnClickListener(v -> {
            Intent in = new Intent(getContext(), SearchAirports.class);
            startActivityForResult(in, 2);
        });
        departure.setOnClickListener(v -> {
            showCalendarPopup();
        });

        searchButton.setOnClickListener(v -> {
            if (!fromTextView.getText().toString().isEmpty() && !toTextView.getText().toString().isEmpty() && !departure.getText().toString().isEmpty()) {


                Retrofit retrofit = RetrofitClient.getClient();
                DDRAPI api = retrofit.create(DDRAPI.class);
                DDRS ddrSINGLETON = DDRS.getDDRSINGLETON(getContext());
                List<Airport> airports = new ArrayList<>();
                Airport departureAirport = null;
                Airport arrivalAirport = null;

                for (AirportCityCountries airportCityCountries :
                        ddrSINGLETON.getAirportCityCountriesList()) {
                    airports.add(airportCityCountries.getAirport());
                }

                for (Airport airport : airports) {
                    if (airport.getName().equals(fromTextView.getText().toString())) {
                        departureAirport = airport;
                    }

                    if (airport.getName().equals(toTextView.getText().toString())) {
                        arrivalAirport = airport;
                    }
                }

                for (int i = 0; i <   2; i++) {
                    FlightDTO flightDTO = new FlightDTO();
                    flightDTO.setDate(dateToSearch);
                    assert departureAirport != null;
                    flightDTO.setDepartureAirportId(departureAirport.getId());
                    assert arrivalAirport != null;
                    flightDTO.setArrivalAirportId(arrivalAirport.getId());
                    flightDTO.setDepartureTime(randomHoursArray[i]);
                    flightDTO.setArrivalTime(randomHoursArray[i + 1]);
                    flightDTO.setAirplaneId(1L);

                    Call<Void> call = api.addFlight(flightDTO);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            Log.d("searchFlights", "Flight added successfully");
                            Intent in = new Intent(getContext(), SearchFlights.class);
                            in.putExtra("date", dateToSearch);
                            in.putExtra("departureAirport", fromTextView.getText().toString());
                            in.putExtra("arrivalAirport", toTextView.getText().toString());
                            in.putExtra("FLIGHT_TYPE", FLIGHT_TYPE);
                            startActivity(in);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable throwable) {
                            Log.d("searchFlights", "Failed to search flights");
                        }
                    });
                }

            }
            else {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();

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
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateToSearch = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
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

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String cityName = data.getStringExtra("cityName");
                fromTextView.setText(cityName);
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String cityName = data.getStringExtra("cityName");
                toTextView.setText(cityName);
            }
        }
    }
}
