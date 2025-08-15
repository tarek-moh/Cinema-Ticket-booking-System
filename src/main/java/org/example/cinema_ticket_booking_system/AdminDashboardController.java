package org.example.cinema_ticket_booking_system;

import com.calendarfx.view.CalendarView;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
/// TODO modeling add movieCrew/actors in GUI and logic in general
public class AdminDashboardController {

    public TextField languageField;
    public TextField trailerUrlField;
    public DatePicker releaseDatePicker;
    public TextArea descriptionField;
    public TextField durationField;
    public TextField posterUrlField;
    public ComboBox directorComboBox;
    public StackPane mainContent;
    @FXML private CalendarView calendarView;
    @FXML private VBox formPanel;
    @FXML private TextField movieTitleField, startTimeField, endTimeField;
    @FXML private DatePicker movieDatePicker;
    @FXML
    private VBox addMovieForm;

    @FXML
    private AnchorPane calendarPane;

    @FXML
    private void showAddForm() {
        formPanel.setVisible(true);
        formPanel.setManaged(true);
        clearForm();
    }

    @FXML
    private void showModifyForm() {
        formPanel.setVisible(true);
        formPanel.setManaged(true);
        // Load selected movie details into form
    }

    @FXML
    private void hideForm() {
        formPanel.setVisible(false);
        formPanel.setManaged(false);
    }

    @FXML
    private void saveMovie() {
        // Save movie to calendar
        hideForm();
    }

    private void clearForm() {
        movieTitleField.clear();
        startTimeField.clear();
        endTimeField.clear();
        movieDatePicker.setValue(null);
    }

    @FXML
    private void showAddMovieForm() {
        addMovieForm.setVisible(true);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), addMovieForm);
        slideIn.setFromX(400);
        slideIn.setToX(0);
        slideIn.play();
    }

    @FXML
    private void hideAddMovieForm() {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), addMovieForm);
        slideOut.setFromX(0);
        slideOut.setToX(400);
        slideOut.setOnFinished(e -> addMovieForm.setVisible(false));
        slideOut.play();
    }
}
