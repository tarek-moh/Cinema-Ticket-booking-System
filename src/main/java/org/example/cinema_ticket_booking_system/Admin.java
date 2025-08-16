package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @Column(name = "AdminID", length = 50, unique = true, nullable = false)
    private String adminId;

    protected Admin() {}

    public Admin(String phone, String fullName, String email, String pwdHash, String adminId) {
        super(phone, fullName, email, pwdHash);
        setAdminId(adminId);
    }

    public String getAdminId() { return adminId; }

    public void setAdminId(String adminId) {
        if (adminId == null || adminId.isBlank())
            throw new IllegalArgumentException("adminId is required");
        if (adminId.length() > 50)
            throw new IllegalArgumentException("adminId max length is 50");
        this.adminId = adminId;
    }
}
