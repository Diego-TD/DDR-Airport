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

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private final RecycleViewInterface recycleViewInterface;
    Context context;
    static ArrayList<Airport> planeModels;
    public RecycleViewAdapter(Context context, ArrayList<Airport> planeModels,
    RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.planeModels = planeModels;
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
        holder.cities.setText(planeModels.get(position).getName());
//        holder.imageView.setImageResource(planeModels.get(position).getImage());
        holder.setPosition(position);




    }

    @Override
    public int getItemCount() {

        return planeModels.size();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface != null && cities != null) {
                        if (position != RecyclerView.NO_POSITION) {

                            recycleViewInterface.OnClickItem(planeModels.get(position).getName());
                        }
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
this.planeModels = filteredList;
notifyDataSetChanged();

    }
}
