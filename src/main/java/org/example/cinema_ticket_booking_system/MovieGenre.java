package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Table(name = "MovieGenre")
@IdClass(MovieGenreId.class)
public class MovieGenre {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "genreID", foreignKey = @ForeignKey(name = "fk_genre"))
    private Genre genre;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "movieID", foreignKey = @ForeignKey(name = "fk_movie"))
    private Movie movie;

    protected MovieGenre() {}
    public MovieGenre(Genre genre, Movie movie) {
        setGenre(genre);
        setMovie(movie);
    }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) {
        if (genre == null) throw new IllegalArgumentException("genre is required");
        this.genre = genre;
    }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) {
        if (movie == null) throw new IllegalArgumentException("movie is required");
        this.movie = movie;
    }
}
