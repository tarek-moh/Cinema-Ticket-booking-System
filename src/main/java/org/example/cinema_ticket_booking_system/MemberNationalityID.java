package org.example.cinema_ticket_booking_system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MemberNationalityID implements Serializable {
    private Integer nationalityId;
    private Integer memberId;

    public MemberNationalityID() {}

    public MemberNationalityID(Integer nationalityId, Integer memberId) {
        this.nationalityId = nationalityId;
        this.memberId = memberId;
    }

    public Integer getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Integer nationalityId) {
        this.nationalityId = nationalityId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberNationalityID)) return false;
        MemberNationalityID that = (MemberNationalityID) o;
        return Objects.equals(nationalityId, that.nationalityId) &&
                Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalityId, memberId);
    }
}
