package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

@Entity
@Table(
    name = "MovieCrew",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_mc_director", columnNames = "DirectorID"),
        @UniqueConstraint(name = "uk_mc_actor",    columnNames = "ActorID")
    }
)
@Check(constraints = "(DirectorID IS NOT NULL AND ActorID IS NULL) OR (DirectorID IS NULL AND ActorID IS NOT NULL)")
public class MovieCrew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MemberID")
    private Integer memberId;

    @Column(name = "FullName", length = 100)
    private String fullName;

    @Column(name = "Gender", length = 50)
    private String gender; // Male/Female or null

    @Column(name = "PhotoURL", length = 255)
    private String photoUrl;

    @Column(name = "Biography", length = 1000)
    private String biography;

    @Column(name = "DirectorID", length = 50, unique = true)
    private String directorId;

    @Column(name = "ActorID", length = 50, unique = true)
    private String actorId;

    @ManyToOne
    @JoinColumn(name = "NationalityID", foreignKey = @ForeignKey(name = "fk_nationality"))
    private Nationality nationality;

    protected MovieCrew() {}

    public MovieCrew(String fullName, String roleCode, boolean isDirector) {
        setFullName(fullName);
        if (isDirector) setDirectorId(roleCode); else setActorId(roleCode);
    }

    public Integer getMemberId() { return memberId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) {
        if (fullName != null && fullName.trim().length() > 100)
            throw new IllegalArgumentException("fullName max length is 100");
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getGender() { return gender; }
    public void setGender(String gender) {
        if (gender == null) { this.gender = null; return; }
        String g = gender.trim();
        if (!(g.equalsIgnoreCase("male") || g.equalsIgnoreCase("female")))
            throw new IllegalArgumentException("gender must be Male or Female");
        this.gender = Character.toUpperCase(g.charAt(0)) + g.substring(1).toLowerCase();
    }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) {
        if (photoUrl != null && photoUrl.length() > 255) throw new IllegalArgumentException("photoUrl max length 255");
        this.photoUrl = photoUrl;
    }

    public String getBiography() { return biography; }
    public void setBiography(String biography) {
        if (biography != null && biography.length() > 1000) throw new IllegalArgumentException("biography max length 1000");
        this.biography = biography;
    }

    public String getDirectorId() { return directorId; }
    public void setDirectorId(String directorId) {
        if (directorId == null || directorId.isBlank())
            throw new IllegalArgumentException("directorId required when role is director");
        if (actorId != null) throw new IllegalArgumentException("cannot set directorId when actorId already set");
        if (directorId.length() > 50) throw new IllegalArgumentException("directorId max length is 50");
        this.directorId = directorId;
    }

    public String getActorId() { return actorId; }
    public void setActorId(String actorId) {
        if (actorId == null || actorId.isBlank())
            throw new IllegalArgumentException("actorId required when role is actor");
        if (directorId != null) throw new IllegalArgumentException("cannot set actorId when directorId already set");
        if (actorId.length() > 50) throw new IllegalArgumentException("actorId max length is 50");
        this.actorId = actorId;
    }

    public Nationality getNationality() { return nationality; }
    public void setNationality(Nationality nationality) { this.nationality = nationality; }
}
