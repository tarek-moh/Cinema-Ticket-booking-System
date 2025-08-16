package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Table(name = "Reservation")
@IdClass(ReservationId.class)
public class Reservation {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "screeningID",
            foreignKey = @ForeignKey(name = "fk_screening"))
    private Screening screening;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "ticketID",
            foreignKey = @ForeignKey(name = "fk_ticket"))
    private Ticket ticket;

    @Id
    @Column(name = "SeatNumber", nullable = false)
    private Integer seatNumber;

    @Id
    @Column(name = "hallID", nullable = false)
    private Integer hallId;

    protected Reservation() {}
    public Reservation(Screening s, Ticket t, Integer seatNumber, Integer hallId) {
        this.screening = s; this.ticket = t; this.seatNumber = seatNumber; this.hallId = hallId;
    }


    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
    public Integer getHallId() { return hallId; }
    public void setHallId(Integer hallId) { this.hallId = hallId; }
}
