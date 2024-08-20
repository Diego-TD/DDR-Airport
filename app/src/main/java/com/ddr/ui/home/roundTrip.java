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
import androidx.core.net.ParseException;
import androidx.fragment.app.Fragment;

import com.ddr.R;
import com.ddr.logic.Airport;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.DDRS;
import com.ddr.logic.RetrofitClient;
import com.ddr.logic.util.FlightDTO;
import com.ddr.ui.Reservations.Luggage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link roundTrip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class roundTrip extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String FLIGHT_TYPE = "ROUND_TRIP";
    private long departureFlightId;
    private long returnFlightId;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private Button searchButton;
    boolean isRoundTrip = false;
    private String dateToSearch;
    private String dateToSearchReturn;
    private TextView fromTextViewRoundTrip,toTextViewRoundTrip,departure,retur_n;
    private  List<Airport> airports = new ArrayList<>();
    private final DDRS ddrsSINGLETON = DDRS.getDDRSINGLETON(getContext());
    private final Retrofit retrofit = RetrofitClient.getClient();


    public roundTrip() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment roundTrip.
     */
    // TODO: Rename and change types and number of parameters
    public static roundTrip newInstance(String param1, String param2) {
        roundTrip fragment = new roundTrip();
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
        View rootView = inflater.inflate(R.layout.fragment_round_trip, container, false);
        toTextViewRoundTrip = rootView.findViewById(R.id.toTextViewRoundTrip);
        fromTextViewRoundTrip = rootView.findViewById(R.id.fromTextViewRoundTrip);
        departure = rootView.findViewById(R.id.departureRounTrip);
        retur_n = rootView.findViewById(R.id.returnRoundTrip);
        searchButton = rootView.findViewById(R.id.search_button_round_trip);

        fromTextViewRoundTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(requireContext(), SearchAirports.class);
                startActivityForResult(in,1);
            }
        });
        toTextViewRoundTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(requireContext(), SearchAirports.class);

                startActivityForResult(in,2);

            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!fromTextViewRoundTrip.getText().toString().isEmpty()
                        && !toTextViewRoundTrip.getText().toString().isEmpty()
                        && !departure.getText().toString().isEmpty()
                        && !retur_n.getText().toString().isEmpty()){


                    DDRAPI api = retrofit.create(DDRAPI.class);

                    Airport departureAirport = null;
                    Airport arrivalAirport = null;
                        for (AirportCityCountries airport : ddrsSINGLETON.getAirportCityCountriesList()){
                            airports.add(airport.getAirport());
                            }
                        for (Airport airport : airports){
                            if (airport.getName().equals(fromTextViewRoundTrip.getText().toString())){
                                departureAirport = airport;
                            }
                            if (airport.getName().equals(toTextViewRoundTrip.getText().toString())){
                                arrivalAirport = airport;
                        }

                        }
                        if (departureAirport != null && arrivalAirport != null) {
                            callFlights(departureAirport, arrivalAirport, api, 9, 4, dateToSearch);
                        }
                        else {
                            Log.e("RoundTrip", "Failed to find matching airports.");
                        }
        }
            }
        });

        departure.setOnClickListener(v -> showCalendarPopup(departure, retur_n));

        retur_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!departure.getText().toString().isEmpty()){
                // Show calendar popup for return with departure date + 2 days
//                showCalendarPopup(retur_n, retur_n, -2);
                    isRoundTrip = true;
                showReturnCalendarPopup(departure,retur_n);}

            else{
                    Toast.makeText(requireContext(),"Selecciona primero la fecha de salida", Toast.LENGTH_SHORT).show();
            }
        }});


        return rootView;
    }

    private void callFlights( Airport departureAirport, Airport arrivalAirport, DDRAPI api, int requestCode, int flightsCalled, String dateToSearch) {
        String[] randomHoursArray = getResources().getStringArray(R.array.random_hours_array);
        final int[] succesfulCall = {0};
        for (int i = 0; i < flightsCalled; i++) {


            FlightDTO flightDTO = new FlightDTO();
            flightDTO.setDate(dateToSearch);
            flightDTO.setDepartureAirportId(departureAirport.getId());
            flightDTO.setArrivalAirportId(arrivalAirport.getId());
            flightDTO.setDepartureTime(randomHoursArray[i]);
            flightDTO.setArrivalTime(randomHoursArray[i + 1]);
            flightDTO.setAirplaneId(1L);

            Call<Void> call = api.addFlight(flightDTO);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        succesfulCall[0]++;
                        if (flightsCalled == succesfulCall[0]) {
                            Intent intent = new Intent(requireContext(), SearchFlights.class);
                            new android.os.Handler().postDelayed(() -> {
                                intent.putExtra("date", dateToSearch);
                                intent.putExtra("departureAirport", fromTextViewRoundTrip.getText().toString());
                                intent.putExtra("arrivalAirport", toTextViewRoundTrip.getText().toString());
                                intent.putExtra("FLIGHT_TYPE", FLIGHT_TYPE);
                                startActivityForResult(intent, requestCode);

                            }, 2000);
                        }

                        Log.d("RoundTrip success", response.toString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable throwable) {
                Log.d("RoundTrip failure",throwable.toString());
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 ) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String cityName = data.getStringExtra("cityName");
                    fromTextViewRoundTrip.setText(cityName);
                }
            }
        }else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK){
                if (data != null){
                    String cityName = data.getStringExtra("cityName");
                   toTextViewRoundTrip.setText(cityName);
                }
            }
        } else if (requestCode == 9) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null){
                   departureFlightId = data.getLongExtra("flightId",-1);
                    Log.d("RoundTrip", "Before swap: From: " + fromTextViewRoundTrip.getText() + ", To: " + toTextViewRoundTrip.getText());
                    String temp = fromTextViewRoundTrip.getText().toString();
                    fromTextViewRoundTrip.setText(toTextViewRoundTrip.getText().toString());
                    toTextViewRoundTrip.setText(temp);
                    Log.d("RoundTrip", "After swap: From: " + fromTextViewRoundTrip.getText() + ", To: " + toTextViewRoundTrip.getText());
                    toTextViewRoundTrip.setText(temp);
                   DDRAPI api = retrofit.create(DDRAPI.class);
                    Airport departureAirport = null;
                    Airport arrivalAirport = null;
                    for (AirportCityCountries airport : ddrsSINGLETON.getAirportCityCountriesList()){
                        airports.add(airport.getAirport());
                    }
                    for (Airport airport : airports){
                        if (airport.getName().equals(fromTextViewRoundTrip.getText().toString())){
                            departureAirport = airport;
                        }
                        if (airport.getName().equals(toTextViewRoundTrip.getText().toString())){
                            arrivalAirport = airport;
                        }


                    }
                    if (departureAirport != null && arrivalAirport != null) {
                        callFlights(departureAirport, arrivalAirport, api, 8, 4, dateToSearchReturn);
                    }



                }
            }

        }
        else if (requestCode == 8) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null){
                    Log.d("departureFlightId",String.valueOf(departureFlightId));
                    Log.d("returnFlightId",String.valueOf(returnFlightId));
                    returnFlightId = data.getLongExtra("flightId",-1);
                    Intent inten = new Intent(requireContext(), Luggage.class);
                    startActivityForResult(inten,7);



                }
            }
        } else if (requestCode == 7) {
            Intent intent = new Intent(requireContext(), ItineraryRoundTrip.class);
            String luggage = data.getStringExtra("luggage");
            intent.putExtra("luggage",luggage);
            intent.putExtra("departureFlightId",departureFlightId);
            intent.putExtra("returnFlightId",returnFlightId);
            startActivity(intent);
        }
    }
    private void showCalendarPopup(TextView departure, TextView returnTextView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog departureDatePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateToSearch = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                departure.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        departureDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        departureDatePickerDialog.show();
    }



    private void showReturnCalendarPopup(TextView departure, TextView returnTextView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog returnDatePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateToSearchReturn = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                String returnDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                returnTextView.setText(returnDate);
            }
        }, year, month, dayOfMonth);

        if (!departure.getText().toString().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date departureDate = sdf.parse(departure.getText().toString());
                calendar.setTime(departureDate);
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                returnDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            } catch (ParseException | java.text.ParseException e) {
                e.printStackTrace();
            }
        }

        // Mostramos el DatePickerDialog para el retorno
        returnDatePickerDialog.show();
    }

    }