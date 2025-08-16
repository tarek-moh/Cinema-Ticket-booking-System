package org.example.cinema_ticket_booking_system;

import java.io.Serializable;
import java.util.Objects;

public class ReservationId implements Serializable {
    private Integer screening;
    private Integer ticket;
    private Integer seatNumber;
    private Integer hallId;

    public ReservationId() {}
    public ReservationId(Integer screening, Integer ticket, Integer seatNumber, Integer hallId) {
        this.screening = screening; this.ticket = ticket; this.seatNumber = seatNumber; this.hallId = hallId;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationId)) return false;
        ReservationId that = (ReservationId) o;
        return Objects.equals(screening, that.screening) &&
               Objects.equals(ticket, that.ticket) &&
               Objects.equals(seatNumber, that.seatNumber) &&
               Objects.equals(hallId, that.hallId);
    }
    @Override public int hashCode() { return Objects.hash(screening, ticket, seatNumber, hallId); }
}
