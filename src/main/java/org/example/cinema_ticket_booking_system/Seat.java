package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Table(name = "Seat")
@IdClass(SeatPK.class)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SeatNumber")
    private Integer seatNumber;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "hallID",
            foreignKey = @ForeignKey(name = "fk_hall"))
    private Hall hall;

    protected Seat() {}
    public Seat(Hall hall) { this.hall = hall; }

    public Integer getSeatNumber() { return seatNumber; }
    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }
}
