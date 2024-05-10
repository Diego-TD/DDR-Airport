package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.Login;
import com.ddr.MainUserMenu;
import com.ddr.R;
import com.ddr.databinding.FragmentOneWayBinding;

import java.util.Calendar;

public class oneWay extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView departure;
    private TextView fromTextView;

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
        fromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(requireContext(), SearchFlights.class);
                startActivity(in);
            }
        });

        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarPopup();
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




//    private void openSearchPage(String query) {
//        // Crea una nueva instancia del fragmento SearchFragment
//        SearchFragment fragment = new SearchFragment();
//
//        // Crear un Bundle para pasar datos al nuevo fragmento si es necesario
//        Bundle args = new Bundle();
//        args.putString("query", query);
//        fragment.setArguments(args);
//
//        // Obtener el FragmentManager y comenzar una transacción
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        // Reemplazar el contenido actual del contenedor con el nuevo fragmento
//        transaction.replace(R.id.fragment_container, fragment);
//
//        // Agregar la transacción a la pila de retroceso
//        transaction.addToBackStack(null);
//
//        // Confirmar la transacción
//        transaction.commit();
//    }


}
