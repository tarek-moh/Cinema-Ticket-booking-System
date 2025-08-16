package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
@Entity
@Table(name = "Nationality")
public class Nationality {
    @Id
    @Column(name="NationalityID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int NationalityID;
    @Column(name="Country")
    private String Country;

}

