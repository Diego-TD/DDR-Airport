package com.ddr.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddr.R;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<PlaneModel> planeModels;
    public RecycleViewAdapter(Context context, ArrayList<PlaneModel> planeModels) {
        this.context = context;
        this.planeModels = planeModels;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row,parent,false);
        return new RecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.cities.setText(planeModels.get(position).getFromtxt());
        holder.imageView.setImageResource(planeModels.get(position).getImage());





    }

    @Override
    public int getItemCount() {

        return planeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
ImageView imageView;
TextView cities;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagePlane);
            cities = itemView.findViewById(R.id.cities);

        }
    }
}
