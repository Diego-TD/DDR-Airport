package com.ddr.ui.Reservations;

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

    public ReservationsViewModel(String destiny, String origin, String departureTime, String arrivalTime, String flightNumber, String dateDay) {
        this.destiny = destiny;
        this.origin = origin;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightNumber = flightNumber;
        this.dateDay = dateDay;
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