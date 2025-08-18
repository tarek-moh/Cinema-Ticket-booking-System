package org.example.cinema_ticket_booking_system;

import javax.persistence.*;


import java.io.Serializable;


@Embeddable
@Access(AccessType.PROPERTY)  //  force Hibernate to use getters
public class SeatID implements Serializable {

    private int seatNumber;
    private int hallID;

    public SeatID(int seatNumber, int hallID) {
    }

    public SeatID() {
    }


    @Column(nullable = false)
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Column(nullable = false)
    public int getHallID() {
        return hallID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatID)) return false;
        SeatID seatID = (SeatID) o;
        return seatNumber == seatID.seatNumber &&
                hallID == seatID.hallID;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(seatNumber);
        result = 31 * result + Integer.hashCode(hallID);
        return result;
    }

    @Override
    public String toString() {
        return "SeatID{" +
                "seatNumber=" + seatNumber +
                ", hallID=" + hallID +
                '}';
    }
}
