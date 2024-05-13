package com.ddr.ui.Reservations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ReservationsViewModel> reservationsViewModels;

    public RecyclerViewAdapter(Context context, ArrayList<ReservationsViewModel> reservationsViewModels){
        this.context = context;
        this.reservationsViewModels = reservationsViewModels;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.coso, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.origen.setText(reservationsViewModels.get(position).getOrigin());
        holder.destino.setText(reservationsViewModels.get(position).getDestiny());
        holder.horaSalida.setText(reservationsViewModels.get(position).getDepartureTime());
        holder.horaLlegada.setText(reservationsViewModels.get(position).getArrivalTime());
        holder.numeroVuelo.setText(reservationsViewModels.get(position).getFlightNumber());
        holder.fechaSalida.setText(reservationsViewModels.get(position).getDateDay());
    }


    @Override
    public int getItemCount() {
        return reservationsViewModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView destino, origen, horaSalida, horaLlegada, numeroVuelo, fechaSalida;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            destino = itemView.findViewById(R.id.destiny);
            origen = itemView.findViewById(R.id.origin);
            horaLlegada = itemView.findViewById(R.id.departureTime);
            horaSalida = itemView.findViewById(R.id.arrivalTime);
            numeroVuelo = itemView.findViewById(R.id.numberFlight);
            fechaSalida = itemView.findViewById(R.id.dateDay);
        }
    }
}
