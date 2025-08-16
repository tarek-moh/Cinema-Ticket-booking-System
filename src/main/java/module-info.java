module org.example.cinema_ticket_booking_system {
    requires javafx.fxml;
    requires java.sql;
    requires com.calendarfx.view;
    requires org.hibernate.orm.core;


    opens org.example.cinema_ticket_booking_system to javafx.fxml;
    exports org.example.cinema_ticket_booking_system;
}