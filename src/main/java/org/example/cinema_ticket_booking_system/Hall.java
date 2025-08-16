package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Table(name = "Hall")
public class Hall {
    //no setters for the hallid as its auto incremented

    public Hall(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // for IDENTITY auto-increment
    private int HallID;
    @Column( nullable = false)
    private int Capacity;


    public int getHallID() {
        return HallID;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }


//    public void setHallID(int hallID) {
//        this.HallID = hallID;
//    }

    @Override
    public String toString() {
        return "Hall{" +
                "HallID=" + HallID +
                ", Capacity=" + Capacity +
                '}';
    }
}
