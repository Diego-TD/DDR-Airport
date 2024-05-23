package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ddr.R;

import java.util.Calendar;

public class oneWay extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView departure;
    private TextView fromTextView;
    private TextView toTextView;
    private Button searchButton;

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


                startActivityForResult(in,1);
            }
        });
    toTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent in = new Intent(getContext(), SearchAirports.class);
            startActivityForResult(in,2);
        }
    });
        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarPopup();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), SearchFlights.class);
                in.putExtra("isOneWay", true);
                startActivity(in);
            }
        });

        return rootView;
    }

    private void showCalendarPopup() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        DatePicker datePicker = new DatePicker();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

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
        }else {
            if (resultCode == Activity.RESULT_OK){
                if (data != null){
                    String cityName = data.getStringExtra("cityName");
                    toTextView.setText(cityName);
                }
            }
        }
    }

}
