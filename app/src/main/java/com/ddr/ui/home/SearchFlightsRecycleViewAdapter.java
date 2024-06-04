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
import java.util.List;

public class SearchFlightsRecycleViewAdapter extends RecyclerView.Adapter<SearchFlightsRecycleViewAdapter.SearchFlightsViewHolder> {

    private RecycleViewInterface recycleViewInterface;
private Context context;
private static ArrayList<PlaneModel> planeModelList;


public SearchFlightsRecycleViewAdapter (Context context, ArrayList<PlaneModel> planeModelList,RecycleViewInterface recycleViewInterface){
    this.context = context;
    this.planeModelList = planeModelList;
    this.recycleViewInterface = recycleViewInterface;

}



    @NonNull
    @Override
    public SearchFlightsRecycleViewAdapter.SearchFlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_search_flight, parent, false);
        return new SearchFlightsViewHolder(view, recycleViewInterface) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFlightsRecycleViewAdapter.SearchFlightsViewHolder holder, int position) {
        PlaneModel planeModel = planeModelList.get(position);
        // Configura los datos en el CardView
//        holder.timeToGoTextView.setText(planeModel.getTimeToGo());
//        holder.timeToArriveTextView.setText(planeModel.getTimeToArrive());
//        holder.fromCityCodeTextView.setText(planeModel.getFromtxt());
//        holder.toCityCodeTextView.setText(planeModel.getToTxt());
//        holder.planeImageView.setImageResource(planeModel.getImage());
    }

    @Override
    public int getItemCount() {
        return planeModelList.size();
    }
    public static class SearchFlightsViewHolder extends RecyclerView.ViewHolder {

        TextView timeToGoTextView, timeToArriveTextView, fromCityCodeTextView, toCityCodeTextView;
ImageView planeImageView;
int position;
        public SearchFlightsViewHolder (@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            timeToGoTextView = itemView.findViewById(R.id.timeToGoTextView);
            timeToArriveTextView = itemView.findViewById(R.id.timeToArriveTextView);
            fromCityCodeTextView = itemView.findViewById(R.id.fromCityCode);
            toCityCodeTextView = itemView.findViewById(R.id.toCityCode);
            planeImageView = itemView.findViewById(R.id.planeLine);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewInterface!= null && fromCityCodeTextView!= null && toCityCodeTextView != null){
                        if(position!= RecyclerView.NO_POSITION){
                            recycleViewInterface.OnClickItem(planeModelList.get(position).getFromtxt());
                            recycleViewInterface.OnClickItem(planeModelList.get(position).getToTxt());

                        }
                    }
                }
            });


        }
    }
}
