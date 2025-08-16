package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Table(name = "Reservation")
@IdClass(ReservationId.class)
public class Reservation {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "screeningID", foreignKey = @ForeignKey(name = "fk_screening"))
    private Screening screening;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "ticketID", foreignKey = @ForeignKey(name = "fk_ticket"))
    private Ticket ticket;

    @Id
    @Column(name = "SeatNumber", nullable = false)
    private Integer seatNumber;

    @Id
    @Column(name = "hallID", nullable = false)
    private Integer hallId;

    protected Reservation() {}

    public Reservation(Screening screening, Ticket ticket, Integer seatNumber, Integer hallId) {
        setScreening(screening);
        setTicket(ticket);
        setSeatNumber(seatNumber);
        setHallId(hallId);
    }

    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) {
        if (screening == null) throw new IllegalArgumentException("screening is required");
        this.screening = screening;
    }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) {
        if (ticket == null) throw new IllegalArgumentException("ticket is required");
        this.ticket = ticket;
    }

    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) {
        if (seatNumber == null || seatNumber <= 0)
            throw new IllegalArgumentException("seatNumber must be positive");
        this.seatNumber = seatNumber;
    }

    public Integer getHallId() { return hallId; }
    public void setHallId(Integer hallId) {
        if (hallId == null || hallId <= 0)
            throw new IllegalArgumentException("hallId must be positive");
        this.hallId = hallId;
    }
}
