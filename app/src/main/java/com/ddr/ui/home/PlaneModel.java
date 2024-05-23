package com.ddr.ui.home;

import android.widget.TextView;

public class PlaneModel {

    private String fromtxt,timeToGo,timeToArrive,toTxt;

    int image;

    public PlaneModel(String fromtxt, int image) {
        this.fromtxt = fromtxt;
        this.image = image;
    }
    public PlaneModel(String fromtxt,String toTxt,String timeToGo,String timeToArrive, int image){
        this.fromtxt = fromtxt;
        this.toTxt = toTxt;
        this.timeToGo = timeToGo;
        this.timeToArrive = timeToArrive;
        this.image = image;
    }

    public String getFromtxt() {
        return fromtxt;
    }

    public int getImage() {
        return image;
    }

    public String getToTxt() {
        return toTxt;
    }

    public String getTimeToGo() {
        return timeToGo;
    }

    public String getTimeToArrive() {
        return timeToArrive;
    }
}
