package org.example.cinema_ticket_booking_system;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        System.out.println("Login attempt with username: " + username);
        // Add authentication logic here
        // If admin, load admin dashboard. If customer, load movie browser.
    }
}