package org.example.cinema_ticket_booking_system;


import org.mindrot.jbcrypt.BCrypt;

// This class to create dummy accounts in the DB
public class GenerateHashedPW {
    public static void main(String[] args)
    {
        String password = "alice";
        System.out.print(BCrypt.hashpw(password, BCrypt.gensalt()));
    }
}
