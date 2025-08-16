package org.example.cinema_ticket_booking_system;

import java.io.Serializable;
import java.util.Objects;

public class SeatPK implements Serializable {
    private Integer seatNumber;
    private Integer hall;

    public SeatPK() {}
    public SeatPK(Integer seatNumber, Integer hall) { this.seatNumber = seatNumber; this.hall = hall; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatPK)) return false;
        SeatPK that = (SeatPK) o;
        return Objects.equals(seatNumber, that.seatNumber) && Objects.equals(hall, that.hall);
    }
    @Override public int hashCode() { return Objects.hash(seatNumber, hall); }
}
