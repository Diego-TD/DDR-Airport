package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAirports extends AppCompatActivity implements RecycleViewInterface {

    private RecyclerView recyclerView;
    private ArrayList<PlaneModel> planeModels;
    TextView fromTextView;
    private List<PlaneModel> item;
    private SearchView searchView;
    private int planeImage = R.drawable.baseline_airplanemode_active_24;
    RecycleViewAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_airports);
        EdgeToEdge.enable(this);

//
        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar modelos de aviones
        setUpPlanes();

        // Crear y configurar adaptador para el RecyclerView
        adapter = new RecycleViewAdapter(this, planeModels,this);
        recyclerView.setAdapter(adapter);
//        Intent in = getIntent();
//String fromTextViewPrev =in.getStringExtra("fromTextViewRoundTrip");
//String toTextViewPrev = in.getStringExtra("toTextViewRoundTrip");
//        assert fromTextViewPrev != null;
//        if (fromTextViewPrev.equals(fromTextView.getText().toString())){
//    new AlertDialog.Builder(this)
//                            .setTitle("???")
//                            .setMessage("Ciudad repetida, Favor de ingresar otra")
//                            .setPositiveButton("Ok",null)
//                            .show();
////                            toTextViewRoundTrip.setText("");
//}

        searchView = findViewById(R.id.searchViewFlight);
        fromTextView = findViewById(R.id.fromTextViewRoundTrip);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);

                return false;
            }
        });
        recyclerView.setHasFixedSize(true);
        item = new ArrayList<>();


        // Aplicar padding para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.searchFlights), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void filterList(String newText) {
        ArrayList<PlaneModel> filteredList = new ArrayList<>();

        for (PlaneModel planeModel : planeModels) {
            if (planeModel.getFromtxt().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(planeModel);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }

        adapter.setFilteredList(filteredList);
    }

    private void setUpPlanes() {
        planeModels = new ArrayList<>();
        String[] fromTxt = getResources().getStringArray(R.array.fromAirports1);
        for (String cityName : fromTxt) {
            Log.d("PlaneModel", "City name: " + cityName);
            planeModels.add(new PlaneModel(cityName, planeImage));
        }
    }

    @Override
    public void OnClickItem(String text) {
        Intent intent = new Intent();
        intent.putExtra("cityName", text); // cityName es el dato que deseas devolver
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    public TextView getFromTextView() {

        return fromTextView;
    }
}


