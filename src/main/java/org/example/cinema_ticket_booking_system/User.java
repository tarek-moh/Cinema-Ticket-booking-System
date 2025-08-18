package org.example.cinema_ticket_booking_system;

import javax.persistence.*;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // One table for all subclasses
@DiscriminatorColumn(name = "UserType", discriminatorType = DiscriminatorType.STRING, columnDefinition = "nvarchar(10)")
@Table(
        name = "[User]", // matches SQL table
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "Email")
        }
)
public class User {

    @Id
    @Column(name = "PhoneNumber", length = 50, columnDefinition = "NVARCHAR(50)")
    @Length(min = 11, max = 11)
    private String phoneNumber;

    @Column(name="FullName", length = 100, columnDefinition = "NVARCHAR(100)")
    private String fullName;

    @Column(name = "Email", length = 100, nullable = false, unique = true, columnDefinition = "NVARCHAR(100)")
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|ymail\\.com|outlook\\.com|hotmail\\.com|live\\.com|msn\\.com|icloud\\.com|me\\.com|proton\\.me|protonmail\\.com)$",
            message = "Only common providers (Gmail, Yahoo, Outlook, Hotmail, Live, MSN, iCloud, Me, Proton) are allowed"
    )
    private String email;

    @Column(name="PasswordHash", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String passwordHash;

    @Column(name="UserType", nullable = false, length = 10, insertable = false, updatable = false)
    private String userType; // handled by @DiscriminatorColumn

    // A user can book many tickets
    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    // Default constructor
    protected User() {}

    // Parameterized constructor
    protected User(String phoneNumber, String fullName, String email, String passwordHash) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters and Setters
    public String getPhoneNumber() { return phoneNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getUserType() { return userType; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    // Phone validation
    boolean isValidPhoneNo(String phoneNumber) {
        if (phoneNumber.length() != 11) return false;
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) return false;
        }
        return true;
    }
}