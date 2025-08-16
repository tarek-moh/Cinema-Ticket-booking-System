package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TicketID")
    private Integer id;

    @Column(name = "BookTime")
    private LocalDate bookTime;

    @ManyToOne
    @JoinColumn(name = "customerID",
                referencedColumnName = "CustomerID",
                foreignKey = @ForeignKey(name = "fk_costumer"))
    private User customer; // optional per DDL

    @Column(name = "TicketPrice", nullable = false)
    private Integer ticketPrice;

    // scalar FKs per your DDL
    @Column(name = "HallID", nullable = false)
    private Integer hallId;

    @Column(name = "SeatNumber", nullable = false)
    private Integer seatNumber;

    protected Ticket() {}

    public Ticket(LocalDate bookTime, Integer ticketPrice, Integer hallId, Integer seatNumber) {
        setBookTime(bookTime);
        setTicketPrice(ticketPrice);
        setHallId(hallId);
        setSeatNumber(seatNumber);
    }

    public Integer getId() { return id; }
    public LocalDate getBookTime() { return bookTime; }
    public void setBookTime(LocalDate bookTime) { this.bookTime = bookTime; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public Integer getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(Integer ticketPrice) {
        if (ticketPrice == null || ticketPrice <= 0)
            throw new IllegalArgumentException("ticket price must be > 0");
        this.ticketPrice = ticketPrice;
    }

    public Integer getHallId() { return hallId; }
    public void setHallId(Integer hallId) {
        if (hallId == null || hallId <= 0)
            throw new IllegalArgumentException("hallId must be positive");
        this.hallId = hallId;
    }

    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) {
        if (seatNumber == null || seatNumber <= 0)
            throw new IllegalArgumentException("seatNumber must be positive");
        this.seatNumber = seatNumber;
    }
}
