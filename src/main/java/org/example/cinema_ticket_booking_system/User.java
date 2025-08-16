package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // one table for all subclasses
@DiscriminatorColumn(name = "UserType", discriminatorType = DiscriminatorType.STRING)
@Table(name = "user",uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_email",    columnNames = "Email"),
        @UniqueConstraint(name = "uk_user_customer", columnNames = "CustomerID"),
        @UniqueConstraint(name = "uk_user_admin",    columnNames = "AdminID")
})
public class User {
    @Id
    @Column(name="PhoneNumber")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String phoneNumber;
    @Column(name="FullName")
    private String name;
    @Column(name="Email")
    private String email;
    @Column(name="PasswordHash")
    private String passwordhash;

    // Default constructor
    protected User() {
    }

    // Parameterized constructor
    protected User(String phoneNumber, String name, String email, String password) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.passwordhash = passwordhash;
    }

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwordhash;
    }

    public void setPassword(String password) {
        this.passwordhash = passwordhash;
    }
    boolean isValidPhoneNo(String phoneNumber) {
        if(phoneNumber.length()!=11)
        {
            return false;
        }
        for(int i=0; i<phoneNumber.length();i++)
        {
            if(!Character.isDigit(phoneNumber.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    boolean isValidEmail(String email) {
        if (email.toLowerCase().endsWith("@gmail.com") ||
                email.toLowerCase().endsWith("@yahoo.com") ||
                email.toLowerCase().endsWith("@ymail.com") ||
                email.toLowerCase().endsWith("@outlook.com") ||
                email.toLowerCase().endsWith("@hotmail.com") ||
                email.toLowerCase().endsWith("@live.com") ||
                email.toLowerCase().endsWith("@msn.com") ||
                email.toLowerCase().endsWith("@icloud.com") ||
                email.toLowerCase().endsWith("@me.com") ||
                email.toLowerCase().endsWith("@proton.me") ||
                email.toLowerCase().endsWith("@protonmail.com")) {

            return true;
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
    boolean isValidPassword(String passwordhash) {
        if (passwordhash.length()<8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters");
        }
        return true;
    }
    }

