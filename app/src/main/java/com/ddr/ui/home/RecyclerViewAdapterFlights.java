package com.ddr.ui.home;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;
import com.ddr.logic.Airport;
import com.ddr.logic.Flight;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterFlights extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private final RecycleViewInterface recycleViewInterface;
    Context context;
    static ArrayList<Flight> flights;
    public RecyclerViewAdapterFlights(Context context, ArrayList<Flight> flights,
                              RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.flights = flights;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row,parent,false);
        return new RecycleViewAdapter.MyViewHolder(view,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.cities.setText(flights.get(position).getName());
//        holder.imageView.setImageResource(flights.get(position).getImage());
        holder.setPosition(position);





    }

    @Override
    public int getItemCount() {

        return flights.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //        TextView fromTextView;
        ImageView imageView;
        TextView cities;
        int position;
        TextView fromTxt;


        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagePlane);
            cities = itemView.findViewById(R.id.cities);

            itemView.setOnClickListener(v -> {
                        if (recycleViewInterface != null && cities != null) {
                            if (position != RecyclerView.NO_POSITION) {

                                recycleViewInterface.OnClickItem(flights.get(position).getName());
                            }
                        }
                    }
            );



        }
        public void setPosition(int position) {
            this.position = position;
        }
    }
    public void setFilteredList(ArrayList<Airport> filteredList){
        //this.flights = filteredList;
        notifyDataSetChanged();

    }
}
