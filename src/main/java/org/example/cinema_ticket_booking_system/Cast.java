package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
import org.example.cinema_ticket_booking_system.Movie;

@Entity
@Table(name="CastedFor")
@IdClass(CastID.class)
public class Cast {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "movieID",
            foreignKey = @ForeignKey(name = "fk_movie"))
    private Movie movie;

    @Id
    @Column(name = "actorID", nullable = false)
    private Integer actorId;

    @Column(name = "RolePlayed", length = 80)
    private String rolePlayed;

    protected Cast() {}
    public Cast(Movie movie, Integer actorId, String rolePlayed) {
        this.movie = movie; this.actorId = actorId; this.rolePlayed = rolePlayed;
    }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) {        
        if (movie == null) throw new IllegalArgumentException("movie is required");

        this.movie = movie; }
    public Integer getActorId() { return actorId; }
    public void setActorId(Integer actorId) {
        if (actorId == null || actorId <= 0)
            throw new IllegalArgumentException("actorId must be a positive integer");
        this.actorId = actorId;
    }

    public String getRolePlayed() { return rolePlayed; }
    public void setRolePlayed(String rolePlayed) {
        if (rolePlayed != null && rolePlayed.length() > 80)
            throw new IllegalArgumentException("rolePlayed max length is 80");
        this.rolePlayed = rolePlayed;
    }
}

