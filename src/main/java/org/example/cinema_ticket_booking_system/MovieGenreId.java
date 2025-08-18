package org.example.cinema_ticket_booking_system;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class MovieGenreId implements Serializable {
    private Integer genre;
    private Integer movie;

    public MovieGenreId() {}
    public MovieGenreId(Integer genre, Integer movie) { this.genre = genre; this.movie = movie; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieGenreId)) return false;
        MovieGenreId that = (MovieGenreId) o;
        return Objects.equals(genre, that.genre) && Objects.equals(movie, that.movie);
    }
    @Override public int hashCode() { return Objects.hash(genre, movie); }
}
