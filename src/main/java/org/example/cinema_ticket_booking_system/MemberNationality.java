package org.example.cinema_ticket_booking_system;

import javax.persistence.*;


@Entity
@Table(name = "MemberNationality")
public class MemberNationality {

    @EmbeddedId
    private MemberNationalityID id;

    @ManyToOne
    @MapsId("nationalityId")  // links part of composite key
    @JoinColumn(name = "NationalityID", nullable = false)
    private Nationality nationality;

    @ManyToOne
    @MapsId("memberId")  // links part of composite key
    @JoinColumn(name = "MemberID", nullable = false)
    private MovieCrew member;

    public MemberNationality() {}

    public MemberNationality(MovieCrew member, Nationality nationality) {
        this.member = member;
        this.nationality = nationality;
        this.id = new MemberNationalityID(nationality.getId(), member.getMemberId());
    }

    public MemberNationalityID getId() {
        return id;
    }

    public void setId(MemberNationalityID id) {
        this.id = id;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public MovieCrew getMember() {
        return member;
    }

    public void setMember(MovieCrew member) {
        this.member = member;
    }
}
