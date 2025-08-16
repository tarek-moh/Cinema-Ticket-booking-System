package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Ticket")
public class Ticket {
    @Id
    @Column(name="TicketID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String TicketID;
    @Column(name="BookTime")
    private LocalDate BookTime;
    @ManyToOne
    @JoinColumn(name = "customerID",
            referencedColumnName = "CustomerID",
            foreignKey = @ForeignKey(name = "fk_costumer"))
    private User customer;
    @Column(name="TicketPrice")
    private int TicketPrice;
    @Column(name = "HallID")
    private Integer hallId;

    @Column(name = "SeatNumber")
    private Integer seatNumber;
    protected Ticket() {}

    public String getId() { return TicketID; }
    public LocalDate getBookTime() { return BookTime; }
    public void setBookTime(LocalDate bookTime) { this.BookTime = bookTime; }
    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
    public Integer getTicketPrice() { return TicketPrice; }
    public void setTicketPrice(Integer ticketPrice) { this.TicketPrice = ticketPrice; }
    public Integer getHallId() { return hallId; }
    public void setHallId(Integer hallId) { this.hallId = hallId; }
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

}
