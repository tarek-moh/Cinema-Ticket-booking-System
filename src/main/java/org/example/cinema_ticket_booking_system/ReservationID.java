package org.example.cinema_ticket_booking_system;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
 //  force Hibernate to use getters
public class ReservationID implements Serializable {

    @Column(nullable = false)
    private int screeningID;

    @Column(nullable = false)
    private int SeatNumber;

    @Column(nullable = false)
    private int hallID;

    @Column(nullable = false)
    private int ticketID;

    public ReservationID(int screeningID, int ticketID, SeatID seatID, int hallID) {
        this.screeningID = screeningID;
        this.ticketID = ticketID;
        this.SeatNumber= seatID.getSeatNumber();
        this.hallID = hallID;
    }


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
        if (!(o instanceof ReservationID)) return false;
        ReservationID that = (ReservationID) o;
        return screeningID == that.screeningID &&
                SeatNumber == that.SeatNumber &&
                hallID == that.hallID &&
                ticketID == that.ticketID;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(screeningID);
        result = 31 * result + Integer.hashCode(SeatNumber);
        result = 31 * result + Integer.hashCode(hallID);
        result = 31 * result + Integer.hashCode(ticketID);
        return result;
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
