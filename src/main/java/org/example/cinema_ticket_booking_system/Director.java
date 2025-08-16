package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("Director")
public class Director {
    @Column(name="DirectorID")
    private String DirectorID;
    protected Director() {}
    public Director(String DirectorID,int Identity,String FullName,String Gender, String PhotoURL, String Biography) {
        super();
        this.DirectorID = DirectorID;
    }
    public String getDirectorID() {
        return DirectorID;
    }
    public void setDirectorID(String DirectorID) {
        this.DirectorID = DirectorID;
    }
}
