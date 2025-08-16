package org.example.cinema_ticket_booking_system;
import java.util.Objects;
import java.io.serializable;
public class CastID implements Serializable {
    private Integer movie;
    private Integer actorId;

    public CastID() {}
    public CastID(Integer movie, Integer actorId) {
        this.movie = movie;
        this.actorId = actorId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CastID)) return false;
        CastID that = (CastID) o;
        return Objects.equals(movie, that.movie) && Objects.equals(actorId, that.actorId);
    }
    @Override public int hashCode() { return Objects.hash(movie, actorId); }
}

