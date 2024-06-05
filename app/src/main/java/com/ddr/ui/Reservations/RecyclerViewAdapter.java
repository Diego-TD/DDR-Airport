package com.ddr.ui.Reservations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;
import com.ddr.logic.AirportCityCountries;
import com.ddr.logic.City;
import com.ddr.logic.DDRS;
import com.ddr.logic.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private DDRS ddrSINGLETON;
    private static Context context;
    private RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Reservation> reservationsViewModels;

    public RecyclerViewAdapter(Context context, ArrayList<Reservation> reservationsViewModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.reservationsViewModels = reservationsViewModels;
        this.recyclerViewInterface = recyclerViewInterface;
        ddrSINGLETON = DDRS.getDDRSINGLETON(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.coso, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List<AirportCityCountries> airportCityCountriesList = ddrSINGLETON.getAirportCityCountriesList();
        City departureCity = new City();
        City arrivalCity = new City();

        for (AirportCityCountries airportCityCountries : airportCityCountriesList) {
            if (Objects.equals(airportCityCountries.getAirport().getName(), reservationsViewModels.get(position).getFlight().getDepartureAirport().getName())){
                departureCity = airportCityCountries.getCity();
            }
            if (Objects.equals(airportCityCountries.getAirport().getName(), reservationsViewModels.get(position).getFlight().getArrivalAirport().getName())){
                arrivalCity = airportCityCountries.getCity();
            }
        }

        holder.origen.setText(departureCity.getName());
        holder.destino.setText(arrivalCity.getName());
        holder.horaSalida.setText(reservationsViewModels.get(position).getFlight().getDepartureTime());
        holder.horaLlegada.setText(reservationsViewModels.get(position).getFlight().getArrivalTime());
        holder.numeroVuelo.setText(reservationsViewModels.get(position).getFlight().getId().toString());
        holder.fechaSalida.setText(reservationsViewModels.get(position).getFlight().getDate());
    }

    @Override
    public int getItemCount() {
        return reservationsViewModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView destino, origen, horaSalida, horaLlegada, numeroVuelo, fechaSalida, maleta;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            destino = itemView.findViewById(R.id.destiny);
            origen = itemView.findViewById(R.id.origin);
            horaLlegada = itemView.findViewById(R.id.arrivalTime);
            horaSalida = itemView.findViewById(R.id.departureTime);
            numeroVuelo = itemView.findViewById(R.id.numberFlight);
            fechaSalida = itemView.findViewById(R.id.dateDay);
            maleta = itemView.findViewById(R.id.typeLuggage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
