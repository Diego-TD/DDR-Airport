package com.ddr.logic;

import java.time.LocalDateTime;

public class ReservationRoundTrip extends Reservation {

    private Flight returnFlight;
    public ReservationRoundTrip(Long id, Flight flight, Flight returnFlight, User user, LocalDateTime createdAt, Integer luggage, Double total) {
        super(id, flight, user, createdAt, luggage, total);
        this.returnFlight = returnFlight;
    }
    public ReservationRoundTrip() {

    }
    public Flight getReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(Flight returnFlight) {
        this.returnFlight = returnFlight;
    }

}
