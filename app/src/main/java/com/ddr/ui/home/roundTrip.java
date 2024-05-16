package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.net.ParseException;
import androidx.fragment.app.Fragment;

import com.ddr.Login;
import com.ddr.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private Button searchButton;
    private TextView fromTextViewRoundTrip,toTextViewRoundTrip,departure,retur_n;

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
//                in.putExtra("fromTextViewRoundTrip",fromTextViewRoundTrip.getText().toString());
//                in.putExtra("toTextViewRoundTrip",toTextViewRoundTrip.getText().toString());
                startActivityForResult(in,2);
//                if (toTextViewRoundTrip.getText().toString().equals(fromTextViewRoundTrip.getText().toString())){
//                    new AlertDialog.Builder(requireContext())
//                            .setTitle("???")
//                            .setMessage("Ciudad repetida, Favor de ingresar otra")
//                            .setPositiveButton("Ok",null)
//                            .show();
//                            toTextViewRoundTrip.setText("");
//                }

            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromTextViewRoundTrip.getText().toString().isEmpty()
                        || toTextViewRoundTrip.getText().toString().isEmpty()
                        || departure.getText().toString().isEmpty()
                        || retur_n.getText().toString().isEmpty()){

                    new AlertDialog.Builder(requireContext())
                            .setTitle("Campos Vacíos")
                            .setMessage("Favor de llenar todos los campos")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                if (fromTextViewRoundTrip.getText().toString().equals(toTextViewRoundTrip.getText().toString())){
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Ciudades iguales")
                            .setMessage("Favor de ingresar ciudades diferentes")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                boolean isRoundTrip = true;
                Intent in = new Intent(requireContext(),SearchFlights.class);
                in.putExtra("isRoundTrip",isRoundTrip);
                in.putExtra("fromTxt",fromTextViewRoundTrip.getText().toString());
                in.putExtra("toTxt",toTextViewRoundTrip.getText().toString());
                startActivity(in);
        }});
        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarPopup(departure, retur_n);
            }
        });

        retur_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!departure.getText().toString().isEmpty()){
                // Show calendar popup for return with departure date + 2 days
//                showCalendarPopup(retur_n, retur_n, -2);
                showReturnCalendarPopup(departure,retur_n);}

            else{
                    Toast.makeText(requireContext(),"Selecciona primero la fecha de salida", Toast.LENGTH_SHORT).show();
            }
        }});


        return rootView;
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
        }else {
            if (resultCode == Activity.RESULT_OK){
                if (data != null){
                    String cityName = data.getStringExtra("cityName");
                   toTextViewRoundTrip.setText(cityName);
                }
            }
        }
    }
    private void showCalendarPopup(TextView departure, TextView returnTextView) {
        // Obtenemos la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Creamos el DatePickerDialog para la salida
        DatePickerDialog departureDatePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                departure.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        // Mostramos el DatePickerDialog para la salida
        departureDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        departureDatePickerDialog.show();
    }

    private void showReturnCalendarPopup(TextView departure, TextView returnTextView) {
        // Obtenemos la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Creamos el DatePickerDialog para el retorno, limitando la fecha mínima a dos días después de la fecha seleccionada en salida
        DatePickerDialog returnDatePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String returnDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                returnTextView.setText(returnDate);
            }
        }, year, month, dayOfMonth);

        // Limitamos la fecha mínima a dos días después de la fecha seleccionada en salida
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