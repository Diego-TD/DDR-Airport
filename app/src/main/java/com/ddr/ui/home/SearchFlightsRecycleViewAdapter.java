package com.ddr.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.City;
import com.ddr.logic.DDRS;
import com.ddr.logic.Flight;
import com.ddr.ui.Reservations.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFlightsRecycleViewAdapter extends RecyclerView.Adapter<com.ddr.ui.home.SearchFlightsRecycleViewAdapter.MyViewHolder> {
    private DDRS ddrSINGLETON;
    private static Context context;
    private RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Flight> flights;

    public SearchFlightsRecycleViewAdapter(Context context, ArrayList<Flight> flights, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.flights = flights;
        this.recyclerViewInterface = recyclerViewInterface;
        ddrSINGLETON = DDRS.getDDRSINGLETON(context);
    }

    @NonNull
    @Override
    public com.ddr.ui.home.SearchFlightsRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.coso, parent, false);
        return new SearchFlightsRecycleViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List<AirportCityCountries> airportCityCountriesList = ddrSINGLETON.getAirportCityCountriesList();
        City departureCity = new City();
        City arrivalCity = new City();

        for (AirportCityCountries airportCityCountries : airportCityCountriesList) {
            if (Objects.equals(airportCityCountries.getAirport().getName(), flights.get(position).getDepartureAirport().getName())){
                departureCity = airportCityCountries.getCity();
            }
            if (Objects.equals(airportCityCountries.getAirport().getName(), flights.get(position).getArrivalAirport().getName())){
                arrivalCity = airportCityCountries.getCity();
            }
        }

        holder.origen.setText(departureCity.getName());
        holder.destino.setText(arrivalCity.getName());
        holder.horaSalida.setText(flights.get(position).getDepartureTime());
        holder.horaLlegada.setText(flights.get(position).getArrivalTime());
        holder.numeroVuelo.setText(flights.get(position).getId().toString());
        holder.fechaSalida.setText(flights.get(position).getDate());
        holder.price.setText("$"+flights.get(position).getPrice().toString());;

    }



    @Override
    public int getItemCount() {
        return flights.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView destino, origen, horaSalida, horaLlegada, numeroVuelo, fechaSalida, maleta, price;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            destino = itemView.findViewById(R.id.destiny);
            origen = itemView.findViewById(R.id.origin);
            horaLlegada = itemView.findViewById(R.id.arrivalTime);
            horaSalida = itemView.findViewById(R.id.departureTime);
            numeroVuelo = itemView.findViewById(R.id.numberFlight);
            fechaSalida = itemView.findViewById(R.id.dateDay);
            maleta = itemView.findViewById(R.id.typeLuggage);
            price = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        try {
                            recyclerViewInterface.onItemClick(position);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }
}
