package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("Actor")
public class Actor {
    @Column(name="ActorID")
    private String ActorID;
    protected Actor() {}
    public Actor(String ActorID,int Identity,String FullName,String Gender, String PhotoURL, String Biography) {
        super();
        this.ActorID = ActorID;
    }
    public String getDirectorID() {
        return ActorID;
    }
    public void setDirectorID(String DirectorID) {
        this.ActorID = ActorID;
    }

}

