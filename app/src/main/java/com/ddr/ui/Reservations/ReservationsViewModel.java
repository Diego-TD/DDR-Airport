package com.ddr.ui.Reservations;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.ArrayList;

public class ReservationsViewModel extends ViewModel {

    private String destiny;
    private String origin;
    private String departureTime;
    private String arrivalTime;
    private String flightNumber;
    private String dateDay;
    private int vuelo;
    private String lugagge;

    public String getLugagge() {
        return lugagge;
    }

    public void setLuggage(String lugagge) {
        this.lugagge = lugagge;
    }

    public int getVuelo() {
        return vuelo;
    }

    public void setVuelo(int vuelo) {
        this.vuelo = vuelo;
    }

    ArrayList<ReservationsViewModel> reservationsViewModels = new ArrayList<>();

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public ArrayList<ReservationsViewModel> getReservationsViewModels() {
        return reservationsViewModels;
    }

    public void setReservationsViewModels(ArrayList<ReservationsViewModel> reservationsViewModels) {
        this.reservationsViewModels = reservationsViewModels;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public ReservationsViewModel(String destiny, String origin, String departureTime, String arrivalTime, String flightNumber, String dateDay, int vuelo, String luggage) {
        this.destiny = destiny;
        this.origin = origin;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightNumber = flightNumber;
        this.dateDay = dateDay;
        this.vuelo = vuelo;
        this.lugagge = luggage;
    }


    /*private final MutableLiveData<String> mText;

    public ReservationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reservations fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }*/


}