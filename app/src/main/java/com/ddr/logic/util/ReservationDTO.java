package com.ddr.logic.util;

    public class ReservationDTO {


        private Long flightId;
        private Long userId;
        private Integer luggage;

        public ReservationDTO( Long flightId,  Long userId,  Integer luggage) {
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

        public Integer getLuggage() {
            return luggage;
        }

        public void setLuggage( Integer luggage) {
            this.luggage = luggage;
        }
    }


