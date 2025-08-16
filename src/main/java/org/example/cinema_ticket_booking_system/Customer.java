package org.example.cinema_ticket_booking_system;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @Column(name = "CustomerID", length = 50, unique = true, nullable = false)
    private String customerId;

    protected Customer() {}

    public Customer(String phone, String fullName, String email, String pwdHash, String customerId) {
        super(phone, fullName, email, pwdHash);
        setCustomerId(customerId);
    }

    public String getCustomerId() { return customerId; }

    public void setCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank())
            throw new IllegalArgumentException("customerId is required");
        if (customerId.length() > 50)
            throw new IllegalArgumentException("customerId max length is 50");
        this.customerId = customerId;
    }
}
