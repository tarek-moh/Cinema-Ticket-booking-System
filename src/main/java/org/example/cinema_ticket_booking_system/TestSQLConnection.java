package org.example.cinema_ticket_booking_system;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestSQLConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost;databaseName=test;user=test;password=password;trustServerCertificate=true";

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("✅ Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
