package org.example.cinema_ticket_booking_system;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
    public static SessionFactory provideSessionFactory() {
        Configuration config = new Configuration();
        // Configures Hibernate with hibernate.cfg.xml
        config.configure();
        return config.buildSessionFactory();
    }
}