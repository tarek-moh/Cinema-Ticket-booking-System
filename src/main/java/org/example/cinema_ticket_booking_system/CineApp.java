package org.example.cinema_ticket_booking_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CineApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CineApp.class.getResource("/org/example/cinema_ticket_booking_system/Views/admin-dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 780);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}