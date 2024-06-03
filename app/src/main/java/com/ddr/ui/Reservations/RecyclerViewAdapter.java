package com.ddr.ui.Reservations;

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

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static Context context;
    private RecyclerViewInterface recyclerViewInterface;
    private ArrayList<ReservationsViewModel> reservationsViewModels;

    public RecyclerViewAdapter(Context context, ArrayList<ReservationsViewModel> reservationsViewModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.reservationsViewModels = reservationsViewModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.coso, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
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
