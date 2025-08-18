package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

@Entity
@Table(name = "Hall")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hallID;

    @Column(nullable = false)
    private int capacity;


    public int getHallID() {
        return hallID;
    }

    // Hibernate still needs a setter, even if you won’t use it manually
    public void setHallID(int hallID) {
        this.hallID = hallID;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Hall " + hallID;
    }
}
