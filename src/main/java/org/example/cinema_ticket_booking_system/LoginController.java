package org.example.cinema_ticket_booking_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.cinema_ticket_booking_system.DAOs.UserDAO;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;


public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    // DAO
    SessionFactory sessionFactory;
    UserDAO userDAO;

    @FXML
    public void initialize()
    {
        sessionFactory = SessionFactoryProvider.provideSessionFactory();
        userDAO = new UserDAO();
    }

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "email and password cannot be empty.");
            return;
        }

        User user = userDAO.findByEmail(email);

        if(user == null)
        {
            showAlert("Login Failed", "User not found.");
            return;
        }

        // Compare hash
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            if ("ADMIN".equalsIgnoreCase(user.getUserType())) {
                loadScene("Views/admin-dashboard-view.fxml", "Admin Dashboard");
            } else {
                loadScene("Views/movie-browser-view.fxml", "Movie Browser");
            }
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }

    @FXML
    protected void handleSignUpButtonAction(ActionEvent event) {
        // Example: load SignUp.fxml if you implement sign-up
        loadScene("/org/example/cinema_ticket_booking_system/SignUp.fxml", "Sign Up");
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load " + title + " scene.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
