package org.example.cinema_ticket_booking_system;

import org.hibernate.annotations.*;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Screening")
@Check(constraints = "TicketPrice>0")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ScreeningID;

    @Column(nullable = false)
    private LocalDateTime StartTime;

    @Column(nullable = false)
    private int TicketPrice;

    @ManyToOne    /// ask tottyy is this true ???
    @JoinColumn(name ="movieID",nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "hallID", nullable = false)
    private Hall hall;


    public int getScreeningID() {
        return ScreeningID;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getTicketPrice() {
        return TicketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        TicketPrice = ticketPrice;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }


    public LocalDateTime getStartTime() {
        return StartTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        StartTime = startTime;
    }


    @Override
    public String toString() {
        return "Screening{" +
                "ScreeningID=" + ScreeningID +
                ", StartTime=" + StartTime +
                ", TicketPrice=" + TicketPrice +
                ", movie=" + movie +
                ", hall=" + hall +
                '}';
    }

}
