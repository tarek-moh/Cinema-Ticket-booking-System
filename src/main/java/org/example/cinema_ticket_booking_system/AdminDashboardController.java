package org.example.cinema_ticket_booking_system;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.popover.EntryPopOverContentPane;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.example.cinema_ticket_booking_system.DAOs.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdminDashboardController {

    // ----------------------- Movie Form Fields -----------------------
    @FXML private StackPane mainContent;
    @FXML private VBox addMovieForm;
    @FXML private TextField movieTitleField, languageField, trailerUrlField, durationField, posterUrlField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker releaseDatePicker;

    // ----------------------- Director Section -----------------------
    @FXML private ComboBox<MovieCrew> directorComboBox;
    @FXML private VBox newDirectorForm;
    @FXML private TextField directorNameField, directorGenderField, directorPhotoField, directorMoviesField;
    @FXML private TextArea directorBioField;

    // ----------------------- Actor Section -----------------------
    @FXML private ListView<CheckBox> actorListView;
    @FXML private VBox newActorForm;
    @FXML private TextField actorNameField, actorGenderField, actorPhotoField, actorRoleField;
    @FXML private TextArea actorBioField;

    // ----------------------- Calendar -----------------------
    @FXML private CalendarView calendarView;
    @FXML private AnchorPane calendarPane;
    private Calendar defaultCalendar;

    // ----------------------- Data Collections -----------------------
    private ObservableList<MovieCrew> allDirectors = FXCollections.observableArrayList();
    private ObservableList<MovieCrew> allActors = FXCollections.observableArrayList();
    private ObservableList<Movie> allMovies = FXCollections.observableArrayList();
    private ObservableList<Hall> allHalls = FXCollections.observableArrayList();
    private ScreeningDAO screeningDAO;
    private MovieCrewDAO crewDAO;
    private MovieDAO movieDAO;
    private UserDAO userDAO;
    private HallDAO hallDAO;

    // ----------------------- Initialization -----------------------
    @FXML
    public void initialize() {
        crewDAO = new MovieCrewDAO();
        screeningDAO = new ScreeningDAO();
        movieDAO = new MovieDAO();
        userDAO = new UserDAO();
        hallDAO = new HallDAO();

        setupDirectorSearch();
        setupCalendar();
        loadHalls();
        loadMovies();

    }

    // ----------------------- Load Data -----------------------
    private void loadDirectors() {
        List<MovieCrew> directors = crewDAO.findByType("DIRECTOR");
        allDirectors = FXCollections.observableArrayList(directors);
        directorComboBox.setItems(allDirectors);
    }

    private void loadActors() {
        List<MovieCrew> actors = crewDAO.findByType("ACTOR");
        allActors = FXCollections.observableArrayList(actors);
        updateActorList("");
    }

    private void loadHalls()
    {
        List<Hall> halls = hallDAO.findAll();
        allHalls = FXCollections.observableArrayList(halls);
    }

    private void loadMovies()
    {
        List<Movie> movies = movieDAO.findAll();
        allMovies = FXCollections.observableArrayList(movies);
    }

    // ----------------------- Search/Filter -----------------------
    // TODO: add a key type even listner and filter accordingly
    private void setupDirectorSearch() {
        directorComboBox.setEditable(false);
        directorComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) ->
                directorComboBox.setItems(allDirectors.filtered(d ->
                        d.getFullName().toLowerCase().contains(newVal.toLowerCase())))
        );
    }

    private void updateActorList(String filter) {
        actorListView.getItems().clear();
        for (MovieCrew actor : allActors) {
            if (actor.getFullName().toLowerCase().contains(filter.toLowerCase())) {
                CheckBox cb = new CheckBox(actor.getFullName());
                cb.setUserData(actor);
                actorListView.getItems().add(cb);
            }
        }
    }


    // ----------------------- Calendar -----------------------
    private void setupCalendar() {
        defaultCalendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);
        defaultCalendar.setName("Screenings");
        loadScreeningEntry(screeningDAO, defaultCalendar);

        calendarView.setEntryDetailsPopOverContentCallback(param -> {
            Entry<Screening> entry = (Entry<Screening>) param.getEntry();
            return new ScreeningPopOver(param.getPopOver(), calendarView, entry, allMovies, allHalls, screeningDAO);
        });


    }

    public static void loadScreeningEntry(ScreeningDAO screeningDAO, Calendar defaultCalendar) {
        List<Screening> screenings = screeningDAO.findAll();

        for (Screening s : screenings) {
            Entry<Screening> entry = new Entry<>();

            entry.setUserObject(s);
            entry.changeStartDate(s.getStartTime().toLocalDate());
            entry.changeEndDate(s.getStartTime().toLocalDate());
            entry.changeStartTime(s.getStartTime().toLocalTime());
            entry.changeEndTime(s.getStartTime().toLocalTime().plusMinutes(s.getMovie().getDurationMinutes()));
            entry.setTitle(s.getMovie().getTitle());
            entry.setLocation("Hall: " + s.getHall().getHallID() + ", Price: " + s.getTicketPrice() + " EGP");

            // --- ATTACH THE DELETE HANDLER HERE ---
            // This ensures the handler is attached to *both* new and existing entries
            // the moment the popover is created.
            entry.calendarProperty().addListener((obs, oldCal, newCal) -> {
                // Entry was removed from its calendar (e.g., via DEL key)
                if (oldCal != null && newCal == null) {
                    Screening scr = entry.getUserObject();
                    if (scr != null && scr.getScreeningID() > 0) { // Ensure it's a saved screening
                        try {
                            screeningDAO.delete(scr);
                            System.out.println("Successfully deleted screening " + scr.getScreeningID() + " from DB.");
                            // showAlert is good, but System.out is fine for debugging
                        } catch (Exception e) {
                            e.printStackTrace();
                            showAlert("Database Error", "Failed to delete screening: " + e.getMessage());
                        }
                    }
                }
            });

            defaultCalendar.addEntry(entry);
        }
    }

    // ----------------------- Movie Form Actions -----------------------
    @FXML private void toggleAddMovieForm() {
        if (!addMovieForm.isVisible()) {
            loadDirectors();
            loadActors();
            addMovieForm.setVisible(true);
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), addMovieForm);
            slideIn.setFromX(addMovieForm.getWidth());
            slideIn.setToX(0);
            slideIn.setInterpolator(Interpolator.EASE_BOTH);
            slideIn.play();
        } else {
            hideAddMovieForm();
        }
    }

    @FXML private void hideAddMovieForm() {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(400), addMovieForm);
        slideOut.setFromX(0);
        slideOut.setToX(addMovieForm.getWidth());
        slideOut.setInterpolator(Interpolator.EASE_BOTH);
        slideOut.setOnFinished(e -> addMovieForm.setVisible(false));
        slideOut.play();
    }


    @FXML
    private void saveMovie() {
        try {
            // Required fields
            String title = movieTitleField.getText();
            if (title == null || title.isBlank()) {
                showAlert("Validation Error", "Movie title is required.");
                return;
            }

            String durationStr = durationField.getText();
            if (durationStr == null || durationStr.isBlank()) {
                showAlert("Validation Error", "Duration is required.");
                return;
            }

            Integer duration;
            try {
                duration = Integer.parseInt(durationStr.trim());
                if (duration <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                showAlert("Validation Error", "Duration must be a positive number.");
                return;
            }

            // Optional fields
            String language = languageField.getText();
            String trailerURL = trailerUrlField.getText();
            String posterURL = posterUrlField.getText();
            String description = descriptionField.getText();
            LocalDate releaseDate = releaseDatePicker.getValue();
            MovieCrew director = directorComboBox.getValue();

            // Build movie entity
            Movie movie = new Movie(title.trim(), duration);
            movie.setLanguage(language);
            movie.setTrailerUrl(trailerURL);
            movie.setPosterUrl(posterURL);
            movie.setDescription(description);
            movie.setDateOfRelease(releaseDate);
            movie.setDirector(director);

            // TODO: set admin when available
            User currentAdminUser = userDAO.findByPhone("01110000002"); // Dummy Admin
            movie.setAdmin(currentAdminUser);

            for (CheckBox cb : actorListView.getItems()) {
                if (cb.isSelected()) {
                    MovieCrew actor = (MovieCrew) cb.getUserData();
                    String role = actorRoleField.getText();
                    // TODO take the actor's role played in the movie as input
                    movie.addActor(actor, null);
                }
            }

            if(movie.getCast().isEmpty())
            {
                showAlert("Validation ALert", "Movie must have at least 1 actor");
                return;
            }

            // Save to DB
            try {
                movieDAO.saveOrUpdate(movie);
                showAlert("Success", "Movie saved successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to save movie: " + e.getMessage());
                return;
            }

            hideAddMovieForm();
            loadMovies(); // refresh movie list/table

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Unexpected Error", "Something went wrong: " + e.getMessage());
        }
    }


    // ----------------------- Director Form Actions -----------------------
    @FXML private void showNewDirectorForm() { toggleForm(newDirectorForm, true); }
    @FXML private void hideNewDirectorForm() { clearDirectorForm(); toggleForm(newDirectorForm, false); }

    @FXML
    private void saveNewDirector() {
        String name = directorNameField.getText();
        String gender = directorGenderField.getText();
        String photoUrl = directorPhotoField.getText();
        String biography = directorBioField.getText();

        if (name == null || name.trim().isEmpty()) {
            showAlert("Validation Error", "Director name is required");
            return;
        }
        if(gender != "Male" && gender != "Female")
        {
            showAlert("Validation Error", "Gender is either \"Male\" or \"Female\"");
        }

        // Create director entity
        MovieCrew director = new MovieCrew();
        director.setFullName(name.trim());
        director.setMemberType("DIRECTOR");
        director.setGender(gender != null ? gender.trim() : null);
        director.setPhotoUrl(photoUrl != null ? photoUrl.trim() : null);
        director.setBiography(biography != null ? biography.trim() : null);

        // Save to DB using DAO
        try {
            crewDAO.saveOrUpdate(director);  // Assuming you have a generic DAO for MovieCrew
            showAlert("Success", "New director saved successfully!");
        } catch (Exception e) {
            showAlert("Database Error", "Failed to save director: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        hideNewDirectorForm();
        loadDirectors(); // refresh ComboBox with updated list
    }


    private void clearDirectorForm() {
        directorNameField.clear(); directorBioField.clear();
        directorGenderField.clear(); directorPhotoField.clear();
        directorMoviesField.clear();
    }

    // ----------------------- Actor Form Actions -----------------------
    @FXML private void showNewActorForm() { toggleForm(newActorForm, true); }
    @FXML private void hideNewActorForm() { clearActorForm(); toggleForm(newActorForm, false); }

    @FXML private void saveNewActor() {
        String name = actorNameField.getText();
        String gender = actorGenderField.getText();
        String role = actorRoleField.getText();
        String  bio = actorBioField.getText();
        String photoURL = actorPhotoField.getText();

        if (name.isEmpty()) { showAlert("Validation Error", "Actor name is required"); return; }
        if (!gender.trim().equals("Male") && !gender.trim().equals("Female"))
        {
            showAlert("Validation Error", "Gender must be 'Female' or 'Male' ");
        }

        MovieCrew actor = new MovieCrew();
        actor.setFullName(name.trim());
        actor.setGender(gender.trim());
        actor.setMemberType("ACTOR");
        actor.setGender(gender != null ? gender.trim() : null);
        actor.setPhotoUrl(photoURL != null ? photoURL.trim() : null);
        actor.setBiography(bio != null ? bio.trim() : null);

        try
        {
            crewDAO.saveOrUpdate(actor);
            showAlert("Success", "New director saved successfully!");
        }catch (Exception e)
        {
            showAlert("Database Error", "Failed to save actor: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        hideNewActorForm();
        loadActors(); // refresh ListView
    }

    private void clearActorForm() {
        actorNameField.clear(); actorBioField.clear();
        actorGenderField.clear(); actorPhotoField.clear();
        actorRoleField.clear();
    }

    // ----------------------- Utility -----------------------
    private void toggleForm(VBox form, boolean visible) {
        form.setVisible(visible);
        form.setManaged(visible);
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title); alert.setHeaderText(null);
        alert.setContentText(message); alert.showAndWait();
    }
}

// ----------------------- PopOver -----------------------
class ScreeningPopOver extends EntryPopOverContentPane {
    private Screening screening;
    private final ComboBox<Movie> movieComboBox;
    private final ComboBox<Hall> hallComboBox;
    private final TextField priceField;

    private final ScreeningDAO screeningDAO;
    private final Entry<Screening> entry;

    public ScreeningPopOver(PopOver popOver, CalendarView calendarView, Entry<Screening> entry,
                            ObservableList<Movie> allMovies, ObservableList<Hall> allHalls, ScreeningDAO screeningDAO) {
        super(popOver, calendarView, entry);
        this.entry = entry;
        this.screening = entry.getUserObject();
        this.screeningDAO = screeningDAO; // Use the DAO passed from the controller

        // --- ATTACH THE DELETE HANDLER HERE ---
        // This ensures the handler is attached to *both* new and existing entries
        // the moment the popover is created.
        entry.calendarProperty().addListener((obs, oldCal, newCal) -> {
            // Entry was removed from its calendar (e.g., via DEL key)
            if (oldCal != null && newCal == null) {
                Screening scr = entry.getUserObject();
                if (scr != null && scr.getScreeningID() > 0) { // Ensure it's a saved screening
                    try {
                        screeningDAO.delete(scr);
                        System.out.println("Successfully deleted screening " + scr.getScreeningID() + " from DB.");
                        // showAlert is good, but System.out is fine for debugging
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Database Error", "Failed to delete screening: " + e.getMessage());
                    }
                }
            }
        });


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        movieComboBox = new ComboBox<>(allMovies);
        hallComboBox = new ComboBox<>(allHalls);
        priceField = new TextField();

        if (screening != null) {
            movieComboBox.setValue(screening.getMovie());
            hallComboBox.setValue(screening.getHall());
            priceField.setText(String.valueOf(screening.getTicketPrice()));
        }

        grid.add(new Label("Movie:"), 0, 0);
        grid.add(movieComboBox, 1, 0);
        grid.add(new Label("Hall:"), 0, 1);
        grid.add(hallComboBox, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceField, 1, 2);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(evt -> {
            saveScreening();
            getPopOver().hide();
        });

        grid.add(saveButton, 1, 3);
        setCenter(grid);
    }

    private void saveScreening() {
        if (screening == null) {
            screening = new Screening();
            // IMPORTANT: Associate the new screening object with the entry
            entry.setUserObject(screening);
        }

        screening.setMovie(movieComboBox.getValue());
        screening.setHall(hallComboBox.getValue());
        screening.setTicketPrice(Integer.parseInt(priceField.getText()));

        LocalDateTime startTime = LocalDateTime.of(
                entry.getStartDate(),
                entry.getStartTime()
        );
        screening.setStartTime(startTime);

        // This will either INSERT (new) or UPDATE (existing)
        screeningDAO.saveOrUpdate(screening);

        // Update the entry's visual representation on the calendar
        getEntry().setTitle(screening.getMovie().getTitle());
        getEntry().setLocation("Hall: " + screening.getHall().getHallID() +
                ", Price: " + screening.getTicketPrice() + " EGP");
        getEntry().changeEndTime(startTime.plusMinutes(screening.getMovie().getDurationMinutes()).toLocalTime());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION or WARNING as needed
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}