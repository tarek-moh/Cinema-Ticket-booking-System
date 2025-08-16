package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
@Entity
@Table(name="Hall")
public class Hall {
    @Id
    @Column(name="HallID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int HallID;
    @Column(name="Capacity")
    private int Capacity;

    public int getHallID() {
        return HallID;
    }
    public int getCapacity() {
        return Capacity;
    }
    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }
}
