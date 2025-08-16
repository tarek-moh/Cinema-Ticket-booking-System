package org.example.cinema_ticket_booking_system;


import jakarta.persistence.*;

@Entity
public class Reservation { /// Use @MapsId to map foreign keys to the embedded ID.

    @EmbeddedId
    private ReservationID resID;

    @ManyToOne
    @MapsId("screeningID")       // <- maps the embedded id field 
    @JoinColumn(name = "screeningID" , nullable = false)
    private Screening screening;

    @OneToOne
    @MapsId("ticketID")
    @JoinColumn(name = "ticketID")
    private Ticket ticket;

    @ManyToOne
    @MapsId("seatNumber")  // maps seatNumber and hallID
    @JoinColumns({
            @JoinColumn(name = "seatNumber", referencedColumnName = "SeatNumber"),
            @JoinColumn(name = "hallID", referencedColumnName = "hallID")
    })
    private Seat seat;






}
