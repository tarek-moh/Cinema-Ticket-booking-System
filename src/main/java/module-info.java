module org.example.cinema_ticket_booking_system {
    requires javafx.fxml;
    requires java.sql;
    requires com.calendarfx.view;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.desktop;
    requires org.hibernate.validator;
    requires java.validation; // Corrected module name

    // Add this line to resolve the javax.naming error
    requires java.naming;
    requires jbcrypt;


    opens org.example.cinema_ticket_booking_system to javafx.fxml, org.hibernate.orm.core;
    exports org.example.cinema_ticket_booking_system;
}