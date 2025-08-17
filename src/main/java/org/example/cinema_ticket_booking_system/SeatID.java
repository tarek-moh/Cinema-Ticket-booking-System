package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

import java.io.Serializable;

@Embeddable
public class SeatID implements Serializable {

    // TODO Custom Generation strategy
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int SeatNumber;

    @Column(nullable = false)
    private int hallID;



    public int getHallID() {
        return hallID;
    }

    public int getSeatNumber() {
        return SeatNumber;
    }

    public void setseatNumber(int seatNumber) {
        this.SeatNumber = seatNumber;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatID)) return false;
        SeatID seatID = (SeatID) o;
        return SeatNumber == seatID.SeatNumber &&
                hallID == seatID.hallID;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(SeatNumber);
        result = 31 * result + Integer.hashCode(hallID);
        return result;
    }


    @Override
    public String toString() {
        return "SeatID{" +
                "SeatNumber=" + SeatNumber +
                ", hallID=" + hallID +
                '}';
    }
}