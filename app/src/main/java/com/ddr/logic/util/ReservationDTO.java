package com.ddr.logic.util;

    public class ReservationDTO {


        private Long flightId;
        private Long userId;
        private String luggage;

        public ReservationDTO( Long flightId,  Long userId,  String luggage) {
            this.flightId = flightId;
            this.userId = userId;
            this.luggage = luggage;
        }
        public ReservationDTO() {

        }


        public Long getFlightId() {
            return flightId;
        }

        public void setFlightId(Long flightId) {
            this.flightId = flightId;
        }


        public Long getUserId() {
            return userId;
        }

        public void setUserId( Long userId) {
            this.userId = userId;
        }

        public String getLuggage() {
            return luggage;
        }

        public void setLuggage( String luggage) {
            this.luggage = luggage;
        }
    }


