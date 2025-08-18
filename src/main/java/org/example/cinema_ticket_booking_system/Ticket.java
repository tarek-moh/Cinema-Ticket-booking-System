package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TicketID;

    @Column(name = "BookTime", nullable = false)
    private Date bookTime;

    @Column(name = "TicketPrice", nullable = false)
    private int ticketPrice;

    // Corrected foreign key mapping to User
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private User customer;

    // FIX: This HallID is also part of the Seat's composite key.
    // Tell Hibernate that it's read-only for this mapping.
    @ManyToOne
    @JoinColumn(name = "HallID", insertable = false, updatable = false, nullable = false)
    private Hall hall;

    // This is the correct primary mapping for the HallID and SeatNumber
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "SeatNumber", referencedColumnName = "SeatNumber", nullable = false),
            @JoinColumn(name = "HallID", referencedColumnName = "HallID", nullable = false)
    })
    private Seat seat;

    // Mapping to Screening, added to match the database
    @ManyToOne
    @JoinColumn(name = "ScreeningID", nullable = false)
    private Screening screening;

    // Constructors
    public Ticket() {}

    public Ticket(Date bookTime, int ticketPrice, User customer, Hall hall, Seat seat, Screening screening) {
        this.bookTime = bookTime;
        this.ticketPrice = ticketPrice;
        this.customer = customer;
        this.hall = hall;
        this.seat = seat;
        this.screening = screening;
    }

    // Getters and Setters
    public int getTicketID() {
        return TicketID;
    }

    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}