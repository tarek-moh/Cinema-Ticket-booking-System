package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

@Entity
@Table(name = "Reservation")
public class Reservation {

    @EmbeddedId
    private ReservationID resID;

    @ManyToOne
    @MapsId("screeningID")
    @JoinColumn(name = "ScreeningID", nullable = false)
    private Screening screening;

    @OneToOne
    @MapsId("ticketID")
    @JoinColumn(name = "TicketID")
    private Ticket ticket;

    @ManyToOne
    @MapsId("SeatID")
    @JoinColumns({
            @JoinColumn(name = "SeatNumber", referencedColumnName = "SeatNumber"),
            @JoinColumn(name = "HallID", referencedColumnName = "HallID")
    })
    private Seat seat;

    // Getters and setters...
    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public ReservationID getResID() {
        return resID;
    }

    public void setResID(ReservationID resID) {
        this.resID = resID;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}