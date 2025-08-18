package org.example.cinema_ticket_booking_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class CineApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CineApp.class.getResource("Views/movie-browser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 780);
        stage.setTitle("CineApp");
        stage.setScene(scene);
        stage.show();

//        Configuration cfg = new Configuration();
//        try {
//            cfg.configure();
//        } catch (HibernateException e) {
//            System.out.println(e.getMessage());
//        }

    }

    public static void main(String[] args) {
        launch();
    }
}