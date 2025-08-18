package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MovieCrew")
public class MovieCrew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MemberID")
    private Integer memberId;

    @Column(name = "FullName", length = 100, nullable = false, columnDefinition = "NVARCHAR(100)")
    private String fullName;

    @Column(name = "Gender", length = 50,columnDefinition = "NVARCHAR(50)")
    private String gender; // Male/Female or null

    @Column(name = "PhotoURL", length = 255, columnDefinition = "NVARCHAR(255)")
    private String photoUrl;

    @Column(name = "Biography", length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String biography;

    @Column(name = "MemberType", length = 10, nullable = false, columnDefinition = "NVARCHAR(10)")
    private String memberType;  // "ACTOR" or "DIRECTOR"

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberNationality> nationalities = new HashSet<>();

    protected MovieCrew() {
    }

    public MovieCrew(String fullName, String memberType) {
        setFullName(fullName);
        setMemberType(memberType);
    }

    // Getters and Setters
    public Integer getMemberId() {
        return memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName != null && fullName.trim().length() > 100)
            throw new IllegalArgumentException("fullName max length is 100");
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null) {
            this.gender = null;
            return;
        }
        String g = gender.trim();
        if (!(g.equalsIgnoreCase("male") || g.equalsIgnoreCase("female")))
            throw new IllegalArgumentException("gender must be Male or Female");
        this.gender = Character.toUpperCase(g.charAt(0)) + g.substring(1).toLowerCase();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        if (photoUrl != null && photoUrl.length() > 255)
            throw new IllegalArgumentException("photoUrl max length 255");
        this.photoUrl = photoUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        if (biography != null && biography.length() > 1000)
            throw new IllegalArgumentException("biography max length 1000");
        this.biography = biography;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        if (memberType == null ||
                !(memberType.equalsIgnoreCase("ACTOR") || memberType.equalsIgnoreCase("DIRECTOR")))
            throw new IllegalArgumentException("memberType must be 'ACTOR' or 'DIRECTOR'");
        this.memberType = memberType.toUpperCase();
    }

    public Set<MemberNationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(Set<MemberNationality> nationalities) {
        this.nationalities = nationalities;
    }

}