package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Screening")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScreeningID")
    private Integer id;

    @Column(name = "StartTime", nullable = false)
    private LocalDateTime startTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movieID", foreignKey = @ForeignKey(name = "fk_movie"))
    private Movie movie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hallID", foreignKey = @ForeignKey(name = "fk_hall"))
    private Hall hall;

    protected Screening() {}

    public Screening(LocalDateTime startTime, Movie movie, Hall hall) {
        setStartTime(startTime);
        setMovie(movie);
        setHall(hall);
    }

    public Integer getId() { return id; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) {
        if (startTime == null) throw new IllegalArgumentException("startTime is required");
        this.startTime = startTime;
    }
    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) {
        if (movie == null) throw new IllegalArgumentException("movie is required");
        this.movie = movie;
    }
    public Hall getHall() { return hall; }
    public void setHall(Hall hall) {
        if (hall == null) throw new IllegalArgumentException("hall is required");
        this.hall = hall;
    }
}
