package org.example.cinema_ticket_booking_system;
import javax.persistence.*;

@Entity
@Table(name = "CastedFor")
@IdClass(CastID.class)
public class Cast {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "movieID",
            foreignKey = @ForeignKey(name = "fk_movie"))
    private Movie movie;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "ActorID", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cast_actor"))
    private MovieCrew actor;

    @Column(name = "RolePlayed", length = 80)
    private String rolePlayed;

    protected Cast() {}

    public Cast(Movie movie, MovieCrew actor, String rolePlayed) {
        setMovie(movie);
        setActor(actor);
        setRolePlayed(rolePlayed);
    }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) {
        if (movie == null) throw new IllegalArgumentException("movie is required");
        this.movie = movie;
    }

    public MovieCrew getActor() { return actor; }
    public void setActor(MovieCrew actor) {
        if (actor == null) throw new IllegalArgumentException("actor is required");
        this.actor = actor;
    }

    public String getRolePlayed() { return rolePlayed; }
    public void setRolePlayed(String rolePlayed) {
        if (rolePlayed != null && rolePlayed.length() > 80)
            throw new IllegalArgumentException("rolePlayed max length is 80");
        this.rolePlayed = rolePlayed;
    }
}

