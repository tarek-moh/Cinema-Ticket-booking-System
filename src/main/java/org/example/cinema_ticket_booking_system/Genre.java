package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
@Entity
@Table(name="Genre", uniqueConstraints = @UniqueConstraint(name = "uk_genre_name", columnNames = "GenreName"))
public class Genre {
    @Id
    @Column(name="GenreID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int GenreID;
    @Column(name="GenreName")
    private String GenreName;
    public Genre() {
        this.GenreID = GenreID;
        this.GenreName = GenreName;
    }
    public int getGenreID() {
        return GenreID;
    }
    public void setGenreName(String GenreName) {
        this.GenreName = GenreName;
    }
    public String getGenreName() {
        return GenreName;
    }
}
