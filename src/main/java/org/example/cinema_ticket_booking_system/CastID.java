package org.example.cinema_ticket_booking_system;
import java.io.Serializable;
import java.util.Objects;

public class CastID implements Serializable {
    private Integer movie;   // refers to MovieID
    private Integer actor;   // refers to MovieCrew.MemberID

    public CastID() {}
    public CastID(Integer movie, Integer actor) {
        this.movie = movie;
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CastID)) return false;
        CastID that = (CastID) o;
        return Objects.equals(movie, that.movie) &&
                Objects.equals(actor, that.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, actor);
    }
}
