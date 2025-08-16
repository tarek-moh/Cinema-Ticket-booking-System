package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Customer")
public class Customer extends User{
    @Column(name="CostumerID")
    private String customerId;
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    protected Customer() {}
    public void setCostumerId(String costumerId) {
        this.customerId = costumerId;
    }
    public Customer(String phoneNumber, String email, String passwordHash, String fullName, String customerId) {
        super(phoneNumber, email, passwordHash, fullName);
        this.customerId = customerId;
    }
}
