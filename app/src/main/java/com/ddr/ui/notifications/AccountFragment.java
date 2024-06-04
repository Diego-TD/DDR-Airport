package com.ddr.ui.notifications;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ddr.R;
import com.ddr.databinding.FragmentAccountBinding;
import com.ddr.logic.Airport;
import com.ddr.logic.DDRAPI;
import com.ddr.logic.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    private EditText user;
    private EditText pass;
    private EditText first;
    private EditText last;
    private EditText sex;
    private EditText age;
    private EditText phone;
    private EditText email;
    private EditText nation;

    private Button salvar;
    private FragmentAccountBinding binding;

    /*public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);
        View rootView = inflater.inflate(R.layout.activity_account, container, false);
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        //salvar.findViewById(R.id.editTextLogInEmail);

        //final TextView textView = binding.textNotifications;
        //accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return rootView;
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        salvar = rootView.findViewById(R.id.salvar);
        user = rootView.findViewById(R.id.user);
        pass = rootView.findViewById(R.id.pass);
        first = rootView.findViewById(R.id.first);
        last = rootView.findViewById(R.id.last);
        sex = rootView.findViewById(R.id.sex);
        age = rootView.findViewById(R.id.age);
        phone = rootView.findViewById(R.id.phone);
        email = rootView.findViewById(R.id.email);
        nation = rootView.findViewById(R.id.nation);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String firstName = first.getText().toString().trim();
                String lastName = last.getText().toString().trim();
                String sexo = sex.getText().toString().trim();
                String edad = age.getText().toString().trim();
                String phoneNumber = phone.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String nacion = nation.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                        sexo.isEmpty() || edad.isEmpty() || phoneNumber.isEmpty() || mail.isEmpty() || nacion.isEmpty()) {
                    Toast.makeText(getContext(), "One field is empty, all must be filled in", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return rootView;
    }

    /*private void setupSpinner() {
        Spinner spinner = binding.spinner;

        // Crea un ArrayAdapter usando un array de strings y un layout por defecto para el spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.spinner_items, // Asegúrate de tener este array en res/values/strings.xml
                android.R.layout.simple_spinner_item
        );


        // Especifica el layout a usar cuando la lista de opciones aparece
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplica el adaptador al spinner
        spinner.setAdapter(adapter);
    }

     */

//    public void getAirportCall(TextView textView) {
//        Retrofit retrofit = RetrofitClient.getClient();
//
//        DDRAPI api = retrofit.create(DDRAPI.class);
//
//        Call<Airport> call = api.getAirport(1);
//        call.enqueue(new Callback<Airport>() {
//            @Override
//            public void onResponse(@NonNull Call<Airport> call, @NonNull Response<Airport> response) {
//                if (!response.isSuccessful()) {
//                    // Handle error
//                    Log.d("AirportFragment", "onResponseError");
//                    return;
//                }
//
//                Airport airport = response.body();
//                assert airport != null;
//                Log.d("AirportFragment", "Airport data: " + airport.toString(airport));
//                textView.post(() -> {
//                    textView.setText(airport.toString(airport));
//                });
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Airport> call, @NonNull Throwable t) {
//                // Handle failure
//                Log.d("AirportFragment", "onFailure: " + t.getMessage());
//            }
//        });
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}