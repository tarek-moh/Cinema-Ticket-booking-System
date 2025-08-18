//package org.example.cinema_ticket_booking_system;
//
//import com.calendarfx.model.Calendar;
//import com.calendarfx.model.CalendarEvent;
//import com.calendarfx.model.CalendarSource;
//import com.calendarfx.model.Entry;
//import com.calendarfx.view.CalendarView;
//import javafx.animation.Interpolator;
//import javafx.animation.TranslateTransition;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.geometry.Insets;
//import javafx.scene.control.*;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.util.Duration;
//import org.controlsfx.control.textfield.TextFields;
//
//import java.util.Optional;
//
///**
// * Controller for the Admin Dashboard.
// * Handles calendar display, movie creation, and adding new directors/actors.
// */
//public class AdminDashboardController {
//
//    // ----------------------- Movie Form Fields -----------------------
//    @FXML private StackPane mainContent;
//
//    @FXML private VBox addMovieForm; // Main "Add Movie" form panel
//    @FXML private TextField movieTitleField;
//    @FXML private TextField languageField;
//    @FXML private TextField trailerUrlField;
//    @FXML private DatePicker releaseDatePicker;
//    @FXML private TextArea descriptionField;
//    @FXML private TextField durationField;
//    @FXML private TextField posterUrlField;
//
//    // ----------------------- Director Section -----------------------
//    @FXML private ComboBox<String> directorComboBox; // Dropdown for existing directors
//
//    // New Director form
//    @FXML private VBox newDirectorForm;
//    @FXML private TextField directorNameField;
//    @FXML private TextArea directorBioField;
//    @FXML private TextField directorGenderField;
//    @FXML private TextField directorPhotoField;
//    @FXML private TextField directorMoviesField;
//
//    // ----------------------- Actor Section -----------------------
//    @FXML private TextField actorSearchField; // Search/filter actors
//    @FXML private ListView<CheckBox> actorListView; // Multi-select actors
//
//    // New Actor form
//    @FXML private VBox newActorForm;
//    @FXML private TextField actorNameField;
//    @FXML private TextArea actorBioField;
//    @FXML private TextField actorGenderField;
//    @FXML private TextField actorPhotoField;
//    @FXML private TextField actorRoleField;
//
//    // ----------------------- Calendar -----------------------
//    @FXML private CalendarView calendarView;
//    @FXML private AnchorPane calendarPane;
//
//    // ----------------------- Data Collections -----------------------
//   // private ObservableList<String> allDirectors = FXCollections.observableArrayList();
//    //private ObservableList<Actor> allActors = FXCollections.observableArrayList();
//
//    // ----------------------- Initialization -----------------------
//    @FXML
//    public void initialize() {
//        // Populate directors from DB (TODO: replace with actual DB call)
//        // allDirectors.addAll(fetchDirectorsFromDB());
//        directorComboBox.setItems(allDirectors);
//
//        // Enable search in director combo box
//        directorComboBox.setEditable(true);
//        directorComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) ->
//                directorComboBox.setItems(allDirectors.filtered(d ->
//                        d.toLowerCase().contains(newVal.toLowerCase())))
//        );
//
//        // Populate actors from DB (TODO: replace with actual DB call)
//        // allActors.addAll(fetchActorsFromDB());
//        updateActorList("");
//
//        // Enable live filtering for actors
//        actorSearchField.textProperty().addListener((obs, oldVal, newVal) -> updateActorList(newVal));
//
////        calendarView.setEntryDetailsPopOverContentCallback(e -> {
////            Entry<?> entry = e.getEntry();
////            VBox customContent = new VBox(10);
////            customContent.setPadding(new Insets(10));
////
////            // ====== Searchable ComboBox for Movie Name ======
////            ComboBox<String> movieComboBox = new ComboBox<>();
////            movieComboBox.setEditable(true); // allows searching
////            movieComboBox.getItems().addAll(
////                    "Inception", "Interstellar", "Oppenheimer", "The Dark Knight"
////            ); // TODO: load dynamically from DB
////            movieComboBox.setPromptText("Select Movie");
////
////            // Pre-fill movie if already set
////            if (entry.getTitle() != null && !entry.getTitle().isEmpty()) {
////                movieComboBox.setValue(entry.getTitle());
////            }
////
////            // ====== Searchable ComboBox for Hall ID ======
////            ComboBox<String> hallComboBox = new ComboBox<>();
////            hallComboBox.setEditable(true); // allows searching
////            hallComboBox.getItems().addAll(
////                    "Hall1", "Hall2", "Hall3"
////            ); // TODO: load dynamically from DB
////            hallComboBox.setPromptText("Select Hall");
////
////            // Pre-fill hall if already set
////            if (entry.getLocation() != null && !entry.getLocation().isEmpty()) {
////                hallComboBox.setValue(entry.getLocation());
////            }
////
////            // ====== Ticket Price Field ======
////            TextField priceField = new TextField();
////            priceField.setPromptText("Ticket Price");
////
////            // Pre-fill price if already stored in userObject
////            if (entry.getUserObject() instanceof Double) {
////                priceField.setText(String.valueOf(entry.getUserObject()));
////            }
////
////            // ====== Save Button ======
////            Button saveButton = new Button("Save");
////            saveButton.setOnAction(i -> {
////                String movieName = movieComboBox.getValue();
////                String hallID = hallComboBox.getValue();
////                String priceText = priceField.getText();
////
////                // Validation
////                if (movieName == null || hallID == null || priceText.isEmpty()) {
////                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields.");
////                    alert.showAndWait();
////                    return;
////                }
////
////                try {
////                    double price = Double.parseDouble(priceText);
////                    // Save values to entry
////                    entry.setTitle(movieName);
////                    entry.setLocation(hallID);
////                    entry.setUserObject(price); // Store price in userObject for retrieval later
////                } catch (NumberFormatException ex) {
////                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price.");
////                    alert.showAndWait();
////                }
////            });
////
////            customContent.getChildren().addAll(
////                    new Label("Movie Name"), movieComboBox,
////                    new Label("Hall ID"), hallComboBox,
////                    new Label("Ticket Price"), priceField,
////                    saveButton
////            );
////
////            return customContent;
////        });
//
//
//    }
//
//    // ----------------------- Show/Hide Movie Form -----------------------
//    @FXML
//    private void toggleAddMovieForm() {
//        if (!addMovieForm.isVisible()) {
//            addMovieForm.setVisible(true);
//
//            TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), addMovieForm);
//            slideIn.setFromX(addMovieForm.getWidth()); // use actual width instead of hard-coded 400
//            slideIn.setToX(0);
//            slideIn.setInterpolator(Interpolator.EASE_BOTH); // smooth start & end
//            slideIn.play();
//        } else {
//            hideAddMovieForm();
//        }
//    }
//
//    @FXML
//    private void hideAddMovieForm() {
//        TranslateTransition slideOut = new TranslateTransition(Duration.millis(400), addMovieForm);
//        slideOut.setFromX(0);
//        slideOut.setToX(addMovieForm.getWidth());
//        slideOut.setInterpolator(Interpolator.EASE_BOTH);
//        slideOut.setOnFinished(e -> addMovieForm.setVisible(false));
//        slideOut.play();
//    }
//
//    @FXML
//    private void saveMovie() {
//        // TODO: Implement DB save logic
//        hideAddMovieForm();
//    }
//
//    // ----------------------- Actor List Update -----------------------
//    private void updateActorList(String filter) {
//        // TODO: Uncomment and connect with DB
//        /*
//        actorListView.getItems().clear();
//        for (Actor actor : allActors) {
//            if (actor.getName().toLowerCase().contains(filter.toLowerCase())) {
//                CheckBox checkBox = new CheckBox(actor.getName());
//                checkBox.setUserData(actor); // Store full actor object
//                actorListView.getItems().add(checkBox);
//            }
//        }
//        */
//    }
//
//    // ----------------------- Director Form Actions -----------------------
//    @FXML
//    private void showNewDirectorForm() {
//        newDirectorForm.setVisible(true);
//        newDirectorForm.setManaged(true);
//    }
//
//    @FXML
//    private void hideNewDirectorForm() {
//        clearDirectorForm();
//        newDirectorForm.setVisible(false);
//        newDirectorForm.setManaged(false);
//    }
//
//    @FXML
//    private void saveNewDirector() {
//        String name = directorNameField.getText();
//        String bio = directorBioField.getText();
//        String gender = directorGenderField.getText();
//        String photo = directorPhotoField.getText();
//        String moviesDirectedStr = directorMoviesField.getText();
//
//        if (name.isEmpty()) {
//            showAlert("Validation Error", "Director name is required");
//            return;
//        }
//
//        int moviesDirected;
//        try {
//            moviesDirected = Integer.parseInt(moviesDirectedStr);
//        } catch (NumberFormatException e) {
//            showAlert("Validation Error", "Number of movies must be numeric");
//            return;
//        }
//
//        // TODO: Save to DB and update combo box
//        hideNewDirectorForm();
//    }
//
//    private void clearDirectorForm() {
//        directorNameField.clear();
//        directorBioField.clear();
//        directorGenderField.clear();
//        directorPhotoField.clear();
//        directorMoviesField.clear();
//    }
//
//    // ----------------------- Actor Form Actions -----------------------
//    @FXML
//    private void showNewActorForm() {
//        newActorForm.setVisible(true);
//        newActorForm.setManaged(true);
//    }
//
//    @FXML
//    private void hideNewActorForm() {
//        clearActorForm();
//        newActorForm.setVisible(false);
//        newActorForm.setManaged(false);
//    }
//
//    @FXML
//    private void saveNewActor() {
//        String name = actorNameField.getText();
//        String bio = actorBioField.getText();
//        String gender = actorGenderField.getText();
//        String photo = actorPhotoField.getText();
//        String role = actorRoleField.getText();
//
//        if (name.isEmpty()) {
//            showAlert("Validation Error", "Actor name is required");
//            return;
//        }
//
//        // TODO: Save to DB and update actor list
//        hideNewActorForm();
//    }
//
//    private void clearActorForm() {
//        actorNameField.clear();
//        actorBioField.clear();
//        actorGenderField.clear();
//        actorPhotoField.clear();
//        actorRoleField.clear();
//    }
//
//    // ----------------------- Utility -----------------------
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}
