package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

import java.io.Serializable;

@Embeddable
public class ReservationID implements Serializable {

    @Column(nullable = false)
    private int screeningID;

    @Column(nullable = false)
    private int SeatNumber;

    @Column(nullable = false)
    private int hallID;

    @Column(nullable = false)
    private int ticketID;


    public int getHallID() {
        return hallID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    //getters and setters
    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getSeatNumber() {
        return SeatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        SeatNumber = seatNumber;
    }

    public int getScreeningID() {
        return screeningID;
    }

    public void setScreeningID(int screeningID) {
        this.screeningID = screeningID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatID)) return false;
        SeatID seatID = (SeatID) o;
        return SeatNumber == seatID.getSeatNumber() &&
                hallID == seatID.getHallID();
    }

    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(SeatNumber) + Integer.hashCode(hallID);
    }


    @Override
    public String toString() {
        return "ReservationID{" +
                "screeningID=" + screeningID +
                ", SeatNumber=" + SeatNumber +
                ", ticketID=" + ticketID +
                ", hallID=" + hallID +
                '}';
    }
}
