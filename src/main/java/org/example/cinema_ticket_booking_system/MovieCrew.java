package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // one table for all subclasses
@DiscriminatorColumn(name = "Job", discriminatorType = DiscriminatorType.STRING)
@Table(name= "MovieCrew")
@Check(constraints = "(DirectorID IS NOT NULL AND ActorID IS NULL) OR (DirectorID IS NULL AND ActorID IS NOT NULL)")

public class MovieCrew {
    @Id
    @Column(name="Identity")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Identity;
    @Column(name ="FullName")
    private String FullName;
    @Column(name="Gender")
    private String Gender;
    @Column(name="PhotoURL")
    private String PhotoURL;
    @Column(name="Biography")
    private String Biography;
    @Column(name="ActorID")
    private String ActorID;
    @Column(name="DirectorID")
    private String DirectorID;
    protected MovieCrew() {}
    public int getIdentity() {
        return Identity;
    }
    public void setIdentity(int identity) {
        Identity = identity;
    }
    public String getFullName() {
        return FullName;
    }
    public void setFullName(String fullName) {
        FullName = fullName;
    }
    public String getGender() {
        return Gender;
    }
    public void setGender(String gender) {
        Gender = gender;
    }
    public String getPhotoURL() {
        return PhotoURL;
    }
    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }
    public String getBiography() {
        return Biography;
    }
    public void setBiography(String biography) {
        Biography = biography;
    }
    public String getActorID() {
        return ActorID;
    }
    public void setActorID(String actorID) {
        ActorID = actorID;
    }
    public String getDirectorID() {
        return DirectorID;
    }
    public void setDirectorID(String directorID) {
        DirectorID = directorID;
    }
}
