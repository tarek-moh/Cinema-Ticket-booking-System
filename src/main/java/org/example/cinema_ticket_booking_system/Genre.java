package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

@Entity
@Table(name = "Genre",
       uniqueConstraints = @UniqueConstraint(name = "uk_genre_name", columnNames = "GenreName"))
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreID")
    private Integer id;

    @Column(name = "GenreName", length = 80, unique = true)
    private String name;

    protected Genre() {}
    public Genre(String name) { setName(name); }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("genre name is required");
        if (name.length() > 80)
            throw new IllegalArgumentException("genre name max length is 80");
        this.name = name.trim();
    }
}
