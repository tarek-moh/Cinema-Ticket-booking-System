package org.example.cinema_ticket_booking_system;

import javax.persistence.*;


@Entity
@Table(name = "Seat")
public class Seat {
    @EmbeddedId
    private SeatID seatID;

    public SeatID getSeatID() {
        return seatID;
    }

    public void setSeatID(SeatID seatID) {
        this.seatID = seatID;
    }

    @ManyToOne
    @MapsId("hallID") // maps hallID in SeatID
    @JoinColumn(name = "hallID")
    private Hall hall;

/// can not increment the seatNumber directly we will need to increment it manuallyyy!!!!
    public Seat(Hall hall, int seatNumber) {
        this.hall = hall;
        this.seatID = new SeatID();
        this.seatID.setHallID(hall.getHallID());
        this.seatID.setseatNumber(seatNumber);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatID=" + seatID +
                ", hall=" + hall +
                '}';
    }

}
