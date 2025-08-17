package org.example.cinema_ticket_booking_system;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.popover.EntryPopOverContentPane;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.TextFields;
import org.example.cinema_ticket_booking_system.DAOs.ScreeningDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the Admin Dashboard.
 * Handles calendar display, movie creation, and adding new directors/actors.
 */
public class AdminDashboardController {

    // ----------------------- Movie Form Fields -----------------------
    @FXML private StackPane mainContent;

    @FXML private VBox addMovieForm; // Main "Add Movie" form panel
    @FXML private TextField movieTitleField;
    @FXML private TextField languageField;
    @FXML private TextField trailerUrlField;
    @FXML private DatePicker releaseDatePicker;
    @FXML private TextArea descriptionField;
    @FXML private TextField durationField;
    @FXML private TextField posterUrlField;

    // ----------------------- Director Section -----------------------
    @FXML private ComboBox<String> directorComboBox; // Dropdown for existing directors

    // New Director form
    @FXML private VBox newDirectorForm;
    @FXML private TextField directorNameField;
    @FXML private TextArea directorBioField;
    @FXML private TextField directorGenderField;
    @FXML private TextField directorPhotoField;
    @FXML private TextField directorMoviesField;

    // ----------------------- Actor Section -----------------------
    @FXML private TextField actorSearchField; // Search/filter actors
    @FXML private ListView<CheckBox> actorListView; // Multi-select actors

    // New Actor form
    @FXML private VBox newActorForm;
    @FXML private TextField actorNameField;
    @FXML private TextArea actorBioField;
    @FXML private TextField actorGenderField;
    @FXML private TextField actorPhotoField;
    @FXML private TextField actorRoleField;

    // ----------------------- Calendar -----------------------
    @FXML private CalendarView calendarView;
    @FXML private AnchorPane calendarPane;
    private Calendar defaultCalendar;

    // ----------------------- Data Collections -----------------------
    private SessionFactory sessionFactory = SessionFactoryProvider.provideSessionFactory();
    private ObservableList<String> allDirectors = FXCollections.observableArrayList();
    private ObservableList<MovieCrew> allActors = FXCollections.observableArrayList();
    private ScreeningDAO screeningDAO;

    // ----------------------- Initialization -----------------------
    @FXML
    public void initialize() {
        // Populate directors from DB (TODO: replace with actual DB call)
        // allDirectors.addAll(fetchDirectorsFromDB());
        directorComboBox.setItems(allDirectors);

        // Enable search in director combo box
        directorComboBox.setEditable(true);
        directorComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) ->
                directorComboBox.setItems(allDirectors.filtered(d ->
                        d.toLowerCase().contains(newVal.toLowerCase())))
        );

        // Populate actors from DB (TODO: replace with actual DB call)
        // allActors.addAll(fetchActorsFromDB());
        updateActorList("");

        defaultCalendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);
        defaultCalendar.setName("Screenings");
        loadScreeningEntry();

        calendarView.setEntryDetailsPopOverContentCallback(param -> {
            Entry<Screening> entry = (Entry<Screening>)param.getEntry();   // now generic Entry<Screening>
            Screening screening = entry.getUserObject(); // safely retrieve your object

            // Pass the PopOver, CalendarView, and Entry to your custom pane
            return new ScreeningPopOver(param.getPopOver(), calendarView, entry);
        });
    }
    private void loadScreeningEntry()
    {
        screeningDAO = new ScreeningDAO(sessionFactory);
        List<Screening> screeningsFromDB = screeningDAO.findAll();
        for (Screening screening : screeningsFromDB)
        {
            Entry entry = new Entry();
            entry.changeStartDate(screening.getStartTime().toLocalDate());
            entry.changeEndDate(screening.getStartTime().toLocalDate());
            entry.changeStartTime(screening.getStartTime().toLocalTime());
            entry.changeEndTime(screening.getStartTime().toLocalTime().plusMinutes(screening.getMovie().getDurationMinutes()));
            entry.setTitle(screening.getMovie().getTitle());
            entry.setLocation("Hall: " + String.valueOf(screening.getHall().getHallID()) + ", Price: " +
                    String.valueOf(screening.getTicketPrice()) + "EGP");
            defaultCalendar.addEntry(entry);
        }
    }

    private void entryHandler(CalendarEvent ev) {
        if (ev.getEventType() == CalendarEvent.ENTRY_TITLE_CHANGED)
        {
            Entry<?> editedEntry = new Entry<>();

            // Show a dialog to get ticket price
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ticket Price");
            dialog.setHeaderText("Enter ticket price for " + editedEntry.getTitle());
            dialog.setContentText("Price:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(price -> {
                try {
                    double ticketPrice = Double.parseDouble(price);
                    // store ticketPrice in your entry or a map
                    editedEntry.setLocation("Price: $" + ticketPrice); // example usage
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price entered!");
                    alert.showAndWait();
                }
            });
        }
    }


    // ----------------------- Show/Hide Movie Form -----------------------
    @FXML
    private void toggleAddMovieForm() {
        if (!addMovieForm.isVisible()) {
            addMovieForm.setVisible(true);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), addMovieForm);
            slideIn.setFromX(addMovieForm.getWidth()); // use actual width instead of hard-coded 400
            slideIn.setToX(0);
            slideIn.setInterpolator(Interpolator.EASE_BOTH); // smooth start & end
            slideIn.play();
        } else {
            hideAddMovieForm();
        }
    }

    @FXML
    private void hideAddMovieForm() {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(400), addMovieForm);
        slideOut.setFromX(0);
        slideOut.setToX(addMovieForm.getWidth());
        slideOut.setInterpolator(Interpolator.EASE_BOTH);
        slideOut.setOnFinished(e -> addMovieForm.setVisible(false));
        slideOut.play();
    }

    @FXML
    private void saveMovie() {
        // TODO: Implement DB save logic
        hideAddMovieForm();
    }

    // ----------------------- Actor List Update -----------------------
    private void updateActorList(String filter) {
        // TODO: Uncomment and connect with DB
        /*
        actorListView.getItems().clear();
        for (Actor actor : allActors) {
            if (actor.getName().toLowerCase().contains(filter.toLowerCase())) {
                CheckBox checkBox = new CheckBox(actor.getName());
                checkBox.setUserData(actor); // Store full actor object
                actorListView.getItems().add(checkBox);
            }
        }
        */
    }

    // ----------------------- Director Form Actions -----------------------
    @FXML
    private void showNewDirectorForm() {
        newDirectorForm.setVisible(true);
        newDirectorForm.setManaged(true);
    }

    @FXML
    private void hideNewDirectorForm() {
        clearDirectorForm();
        newDirectorForm.setVisible(false);
        newDirectorForm.setManaged(false);
    }

    @FXML
    private void saveNewDirector() {
        String name = directorNameField.getText();
        String bio = directorBioField.getText();
        String gender = directorGenderField.getText();
        String photo = directorPhotoField.getText();
        String moviesDirectedStr = directorMoviesField.getText();

        if (name.isEmpty()) {
            showAlert("Validation Error", "Director name is required");
            return;
        }

        int moviesDirected;
        try {
            moviesDirected = Integer.parseInt(moviesDirectedStr);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Number of movies must be numeric");
            return;
        }

        // TODO: Save to DB and update combo box
//
//        // 1️⃣ Create new MovieCrew as Director
//        MovieCrew director = new MovieCrew(name, "D" + System.currentTimeMillis(), true); // unique code
//        director.setBiography(bio);
//        director.setGender(gender);
//        director.setPhotoUrl(photo);
//
//        // 2️⃣ Persist using EntityManager // TODO: should use Hibernate
//        EntityManager em = entityManagerFactory.createEntityManager();
//
//        EntityTransaction tx = em.getTransaction();
//        try {
//            tx.begin();
//            em.persist(director);
//            tx.commit();
//        } catch (Exception e) {
//            if (tx.isActive()) tx.rollback();
//            e.printStackTrace();
//            showAlert("Error", "Failed to save director");
//            return;
//        } finally {
//            em.close();
//        }
//
//        // 3️⃣ Update ComboBox
//        directorComboBox.getItems().add(director);
//        directorComboBox.setValue(director);
//

        hideNewDirectorForm();
    }

    private void clearDirectorForm() {
        directorNameField.clear();
        directorBioField.clear();
        directorGenderField.clear();
        directorPhotoField.clear();
        directorMoviesField.clear();
    }

    // ----------------------- Actor Form Actions -----------------------
    @FXML
    private void showNewActorForm() {
        newActorForm.setVisible(true);
        newActorForm.setManaged(true);
    }

    @FXML
    private void hideNewActorForm() {
        clearActorForm();
        newActorForm.setVisible(false);
        newActorForm.setManaged(false);
    }

    @FXML
    private void saveNewActor() {
        String name = actorNameField.getText();
        String bio = actorBioField.getText();
        String gender = actorGenderField.getText();
        String photo = actorPhotoField.getText();
        String role = actorRoleField.getText();

        if (name.isEmpty()) {
            showAlert("Validation Error", "Actor name is required");
            return;
        }

        // TODO: Save to DB and update actor list
        hideNewActorForm();
    }

    private void clearActorForm() {
        actorNameField.clear();
        actorBioField.clear();
        actorGenderField.clear();
        actorPhotoField.clear();
        actorRoleField.clear();
    }

    // ----------------------- Utility -----------------------
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class ScreeningPopOver extends EntryPopOverContentPane {

    private final Screening screening;

    public ScreeningPopOver(PopOver popOver, CalendarView calendarView, Entry<Screening> entry) {
        super(popOver, calendarView, entry);
        this.screening = entry.getUserObject();
        // --- UI Components ---
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Fields for editing the screening
        ComboBox<Movie> movieComboBox = new ComboBox<>();
        ComboBox<Hall> hallComboBox = new ComboBox<>();
        DatePicker datePicker = new DatePicker();
        TextField timeField = new TextField("HH:MM"); // e.g., "14:30"
        TextField priceField = new TextField();

        // --- Populate Fields with existing data ---
        // In a real app, you'd fetch these from a service/database
        // List<Movie> allMovies = movieService.findAll();
        // List<Hall> allHalls = hallService.findAll();
        // movieComboBox.getItems().addAll(allMovies);
        // hallComboBox.getItems().addAll(allHalls);

        if(screening != null) {
            if (screening.getMovie() != null) {
                movieComboBox.setValue(screening.getMovie());
            }
            if (screening.getHall() != null) {
                hallComboBox.setValue(screening.getHall());
            }
            if (screening.getStartTime() != null) {
                datePicker.setValue(screening.getStartTime().toLocalDate());
                timeField.setText(screening.getStartTime().toLocalTime().toString());
            }
            priceField.setText(String.valueOf(screening.getTicketPrice()));
        }

        // --- Add components to grid ---
        grid.add(new Label("Movie:"), 0, 0);
        grid.add(movieComboBox, 1, 0);
        grid.add(new Label("Hall:"), 0, 1);
        grid.add(hallComboBox, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceField, 1, 2);

        // --- Save Button Logic ---
        Button saveButton = new Button("Save");
        saveButton.setOnAction(evt -> {
            // 1. Update the screening object from UI fields
            screening.setMovie(movieComboBox.getValue());
            screening.setHall(hallComboBox.getValue());
            screening.setTicketPrice(Integer.parseInt(priceField.getText()));
            LocalDateTime startTime = LocalDateTime.of(datePicker.getValue(), java.time.LocalTime.parse(timeField.getText()));
            screening.setStartTime(startTime);

            // 2. Persist to database
            // screeningService.save(screening);
            System.out.println("Saving screening: " + screening);

            // 3. Update the calendar entry's visual properties
           // entry.updateEntryProperties();

            // 4. Close the popover
            getPopOver().hide();
        });

        grid.add(saveButton, 1, 5);

        // Set the final content
        setCenter(grid);
    }
}