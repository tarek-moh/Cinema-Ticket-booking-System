package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Ticket")
public class Ticket { ///  no setters for TicketID nor Customer !!!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TicketID;

    private LocalDate BookTime;

    @ManyToOne
    @JoinColumn(name = "customerID") // this is the foreign key column in Ticket table
    private User customer;

    public LocalDate getBookTime() {
        return BookTime;
    }

    public User getCustomer() {
        return customer;
    }


    public int getTicketID() {
        return TicketID;
    }

    public void setBookTime(LocalDate bookTime) {
        BookTime = bookTime;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "TicketID=" + TicketID +
                ", BookTime=" + BookTime +
                ", customer=" + customer +
                '}';
    }

}

