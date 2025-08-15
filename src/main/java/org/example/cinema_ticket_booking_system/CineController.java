package org.example.cinema_ticket_booking_system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CineController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}