package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User{
    @Column(name="AdminID")
    private String adminId;
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    protected Admin() {}
    public Admin(String phoneNumber, String email, String passwordHash, String fullName, String customerId) {
        super(phoneNumber, email, passwordHash, fullName);
        this.adminId = adminId;
    }
}

