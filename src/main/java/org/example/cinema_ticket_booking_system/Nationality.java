package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Table(name = "Nationality")
public class Nationality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NationalityID")
    private Integer id;

    @Column(name = "Country", length = 80)
    private String country;

    protected Nationality() {}

    public Nationality(String country) { setCountry(country); }

    public Integer getId() { return id; }
    public String getCountry() { return country; }

    public void setCountry(String country) {
        if (country.length() > 80)
            throw new IllegalArgumentException("Invalid Input");
        if( country == null)
        {
            this.country="Not disclosed yet";
        }
        this.country = country.trim();
    }
}
