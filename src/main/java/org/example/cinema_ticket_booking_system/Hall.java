package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Table(name = "Hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HallID")
    private Integer id;

    @Column(name = "Capacity", nullable = false)
    private Integer capacity;

    protected Hall() {}
    public Hall(Integer capacity) { setCapacity(capacity); }

    public Integer getId() { return id; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) {
        if (capacity == null || capacity <= 0)
            throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
    }
}
