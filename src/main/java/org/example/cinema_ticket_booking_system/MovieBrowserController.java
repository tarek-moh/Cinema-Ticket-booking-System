package org.example.cinema_ticket_booking_system;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MovieBrowserController implements Initializable {


    @FXML
    private VBox movieBrowserPane;
    @FXML
    private VBox movieDetailsPane;
    @FXML
    private Button backButton;
    @FXML
    private Label movieDetailsTitle;
    @FXML
    private Label movieDetailsDescription;
    @FXML
    private ImageView movieDetailsPoster;
    @FXML
    private Label movieDurationLabel;
    @FXML
    private Button bookButton;
    @FXML
    private Button viewTicketsButton;
    @FXML
    private VBox ticketViewPane;
    @FXML
    private VBox ticketListVBox;
    @FXML
    private Label welcomeLabel;
    @FXML
    private TilePane movieTilePane;
    @FXML
    private VBox bookingPane;
    @FXML
    private VBox showtimeListVBox;
    @FXML
    private VBox showtimeSelectionPane;
    @FXML
    private VBox seatSelectionPane;
    @FXML
    private GridPane seatGridPane;
    private SessionFactory sessionFactory;
    private Screening currentScreening;
    private Hall currentHall;

    private void setUpHibernate() {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error setting up Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ----------------------- session  -----------------------
    private User currentUser;

    public void setLoggedUser(User user)
    {
        this.currentUser = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpHibernate();


        loadMovies();
    }

    private void loadMovies() {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            // Use HQL to fetch all movies
            Query<Movie> query = session.createQuery("from Movie", Movie.class);
            List<Movie> movies = query.list();

            for (Movie movie : movies) {
                VBox movieCard = createMovieCard(movie);
                movieTilePane.getChildren().add(movieCard);
            }
        } catch (Exception e) {
            System.out.println("Error loading movies: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private VBox createMovieCard(Movie movie) {
        VBox movieCard = new VBox(5);
        movieCard.getStyleClass().add("movie-card");

        // Poster Image

        ImageView posterView = null;
        try {
            Image posterImage = new Image(movie.getPosterUrl());
            posterView = new ImageView(posterImage);
        } catch (Exception e) {
           System.out.println("Error Loading poster");
            Image posterImage = new Image(getClass().getResourceAsStream("/MoviePoster.jpg"));
            posterView = new ImageView(posterImage);
        }
        posterView.setFitWidth(200);
        posterView.setPreserveRatio(true);
        posterView.getStyleClass().add("movie-poster");


        Label movieTitle = new Label(movie.getTitle());
        movieCard.getChildren().addAll(posterView, movieTitle);

        // Add event handler to open movie details/booking screen
        // movieCard.setOnMouseClicked(event -> System.out.println("Clicked on " + movieTitle));

        // Add a click handler to the movie card
        movieCard.setOnMouseClicked(event -> {
            // When the card is clicked, call the method that handles showing the details
            showMovieDetails(movie);
        });


        return movieCard;
    }


    private void showMovieDetails(Movie movie) {

        movieBrowserPane.setVisible(false);
        movieBrowserPane.setManaged(false);

        movieDetailsPane.setVisible(true);
        movieDetailsPane.setManaged(true);

        backButton.setVisible(true);
        backButton.setManaged(true);

        // Hide the 'View Tickets' button
        viewTicketsButton.setVisible(false);
        viewTicketsButton.setManaged(false);

        /// populate movie details (these are just some as an example!!)
        movieDetailsTitle.setText(movie.getTitle());
        movieDetailsDescription.setText(movie.getDescription());
        // 1. Display the movie poster
        Image posterImage = null;
        try {
            posterImage = new Image(movie.getPosterUrl(), true);
        } catch (Exception e) {
            System.out.println("Error Loading poster");
            posterImage = new Image(getClass().getResourceAsStream("/MoviePoster.jpg"));
        }
        movieDetailsPoster.setImage(posterImage);

       // String directorName = getDirectorName(movie.getDirector().getMemberId());
        movieDetailsTitle.setText(movie.getTitle() + " by " + movie.getDirector().getFullName());
        movieDurationLabel.setText("Duration: " + movie.getDurationMinutes() + " minutes");


        ///book button logic
        bookButton.setOnAction(event -> {
            // Handle the booking logic here
            showBookingDetails(movie);
            System.out.println("Booking button clicked!!!!! " + movie.getTitle());
        });
    }

    private void showBookingDetails(Movie movie) {

        movieDetailsPane.setVisible(false);
        movieDetailsPane.setManaged(false);

        showtimeSelectionPane.setVisible(true);
        showtimeSelectionPane.setManaged(true);

        // Hide the 'View Tickets' button
        viewTicketsButton.setVisible(false);
        viewTicketsButton.setManaged(false);

        /// populate screening details (these are just some as an example!!)
        loadShowtimes(movie);

    }

    private void loadShowtimes(Movie movie) {

        showtimeListVBox.getChildren().clear();
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Use the named query to call the stored procedure
            // HQL query to find all screenings for a movie with available seats
            Query<Screening> query = session.createQuery(
                    "SELECT s FROM Screening s WHERE s.movie.id = :movieID",
                    Screening.class
            );

            query.setParameter("movieID", movie.getId());

            List<Screening> screenings = query.list();

            for (Screening screening : screenings) {
                // Your logic to create buttons for each screening
                Button screeningButton = new Button(screening.getStartTime().toString());
                screeningButton.setOnAction(event -> showAvailableSeats(screening.getScreeningID()));
                //currentHall = screening.getHall();
                showtimeListVBox.getChildren().add(screeningButton);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void loadTickets(){
        ticketListVBox.getChildren().clear(); // Clear previous tickets
        Session session = null;

        try {
            session = sessionFactory.openSession();
            // Use JOIN FETCH to eagerly load the associated Screening and Seat data
            Query<Ticket> ticketQuery = session.createQuery(
                    "FROM Ticket t JOIN FETCH t.screening JOIN FETCH t.seat WHERE t.customer.phoneNumber = :customerPhoneNumber", Ticket.class);
            ticketQuery.setParameter("customerPhoneNumber", currentUser.getPhoneNumber());
            List<Ticket> userTickets = ticketQuery.list();
            // Add this line to see what the database returns
            System.out.println("Tickets found for user: " + userTickets.size());
            if (userTickets.isEmpty()) {
                Label noTicketsLabel = new Label("You have no booked tickets.");
                noTicketsLabel.getStyleClass().add("no-tickets-label");
                ticketListVBox.getChildren().add(noTicketsLabel);
            } else {
                for (Ticket ticket : userTickets) {
                    VBox ticketCard = createTicketCard(ticket);
                    ticketListVBox.getChildren().add(ticketCard);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while fetching your tickets.");
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
    private VBox createTicketCard(Ticket ticket){
        VBox card = new VBox(10);
        card.getStyleClass().add("ticket-card");

        // Use a descriptive name like the one from the image you attached
        Label movieTitle = new Label(ticket.getScreening().getMovie().getTitle());
        movieTitle.getStyleClass().add("ticket-movie-title");

        Label seatInfo = new Label("Seat: " + ticket.getSeat().getSeatID().getSeatNumber());
        Label screeningInfo = new Label("Time: " + ticket.getScreening().getStartTime());
        Label hallInfo = new Label("Hall: " + ticket.getSeat().getSeatID().getHallID());

        card.getChildren().addAll(movieTitle, seatInfo, screeningInfo, hallInfo);

        return card;
    }

    private void showAvailableSeats(int screeningID) {
        // Step 1: Switch views
        showtimeSelectionPane.setVisible(false);
        showtimeSelectionPane.setManaged(false);

        seatSelectionPane.setVisible(true);
        seatSelectionPane.setManaged(true);

        // Hide the 'View Tickets' button
        viewTicketsButton.setVisible(false);
        viewTicketsButton.setManaged(false);

        // Step 2: Clear previous seats
        seatGridPane.getChildren().clear();

        // Step 3 & 4: Fetch data and dynamically create seats
        Session session = null;
        try {
            session = sessionFactory.openSession();

            // Load the full Screening entity to get the Hall capacity
            Screening screening = session.get(Screening.class, screeningID);
            this.currentScreening = screening;

            if (screening != null && screening.getHall() != null) {
                Hall hall = screening.getHall();

                //  FIX: Correctly fetch all Seat entities for the current Hall
                Query<Seat> seatsQuery = session.createQuery(
                        "FROM Seat s WHERE s.hall.hallID = :hallId", Seat.class);
                seatsQuery.setParameter("hallId", hall.getHallID());
                List<Seat> allSeats = seatsQuery.list();

                // Fetch reserved seats
                Query<Integer> reservedSeatsQuery = session.createQuery(
                        "SELECT t.seat.seatID.seatNumber FROM Ticket t WHERE t.screening.ScreeningID = :screeningId", Integer.class);
                reservedSeatsQuery.setParameter("screeningId", screeningID);
                Set<Integer> reservedSeatNumbers = new HashSet<>(reservedSeatsQuery.list());

                // Loop through the fetched Seat entities
                for (Seat seat : allSeats) {
                    int seatNumber = seat.getSeatID().getSeatNumber();
                    int row = (seatNumber - 1) / 10;
                    int col = (seatNumber - 1) % 10;

                    Button seatButton = new Button(String.valueOf(seatNumber));
                    seatButton.getStyleClass().add("seat-button");
                    seatButton.setUserData(seatNumber);

                    if (reservedSeatNumbers.contains(seatNumber)) {
                        seatButton.setDisable(true);
                        seatButton.getStyleClass().add("reserved");
                    } else {
                        seatButton.getStyleClass().add("available");
                        seatButton.setOnAction(event -> {
                            if (seatButton.getStyleClass().contains("selected")) {
                                seatButton.getStyleClass().remove("selected");
                            } else {
                                seatButton.getStyleClass().add("selected");
                            }
                        });
                    }
                    seatGridPane.add(seatButton, col, row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    @FXML
    private void handleBackButton(ActionEvent event) {
        if (seatSelectionPane.isVisible()) {
            seatSelectionPane.setVisible(false);
            seatSelectionPane.setManaged(false);
            showtimeSelectionPane.setVisible(true);
            showtimeSelectionPane.setManaged(true);
        } else if (showtimeSelectionPane.isVisible()) {
            showtimeSelectionPane.setVisible(false);
            showtimeSelectionPane.setManaged(false);
            movieDetailsPane.setVisible(true);
            movieDetailsPane.setManaged(true);
        } else if (movieDetailsPane.isVisible()) {
            movieDetailsPane.setVisible(false);
            movieDetailsPane.setManaged(false);
            movieBrowserPane.setVisible(true);
            movieBrowserPane.setManaged(true);
            backButton.setVisible(false);
            backButton.setManaged(false);
            viewTicketsButton.setVisible(true);
            viewTicketsButton.setManaged(true);
        } else if (ticketViewPane.isVisible()) {
            //logic to go back from the tickets view to the movie browser
            ticketViewPane.setVisible(false);
            ticketViewPane.setManaged(false);
            movieBrowserPane.setVisible(true);
            movieBrowserPane.setManaged(true);
            backButton.setVisible(false);
            backButton.setManaged(false);
            viewTicketsButton.setVisible(true);
            viewTicketsButton.setManaged(true);
        }

    }

    @FXML
    private void handleViewTicketsButton(ActionEvent event) {
//        if (currentUser == null) {
//            showAlert("Error", "User not logged in. Cannot view tickets.");
//            return;
//        }

        // Hide all other panes and show the ticket view pane
        movieBrowserPane.setVisible(false);
        movieBrowserPane.setManaged(false);
        movieDetailsPane.setVisible(false);
        movieDetailsPane.setManaged(false);
        showtimeSelectionPane.setVisible(false);
        showtimeSelectionPane.setManaged(false);
        seatSelectionPane.setVisible(false);
        seatSelectionPane.setManaged(false);

        ticketViewPane.setVisible(true);
        ticketViewPane.setManaged(true);
        backButton.setVisible(true);
        backButton.setManaged(true);

        // Hide the 'View Tickets' button
        viewTicketsButton.setVisible(false);
        viewTicketsButton.setManaged(false);

        // Load the tickets for the current user
        loadTickets();
    }



    @FXML
    private void handleBookingConfirmation(ActionEvent event) {
        List<Button> selectedSeats = seatGridPane.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .filter(button -> button.getStyleClass().contains("selected"))
                .collect(Collectors.toList());

        if (selectedSeats.isEmpty()) {
            showAlert("No seats selected", "Please select at least one seat to confirm your booking.");
            return;
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            if (currentUser == null) {
                showAlert("Error", "Dummy user not found. Cannot complete booking.");
                transaction.rollback();
                return;
            }

            Screening screening = session.get(Screening.class, currentScreening.getScreeningID());
            Hall hall = screening.getHall();
            if (screening == null || hall == null) {
                showAlert("Error", "Screening or Hall information is missing. Cannot complete booking.");
                transaction.rollback();
                return;
            }

            for (Button seatButton : selectedSeats) {
                int seatNumber = (int) seatButton.getUserData();
                System.out.println("Attempting to book seat number: " + seatNumber + " for Hall ID: " + hall.getHallID());
                Query<Seat> seatQuery = session.createQuery(
                        "FROM Seat s WHERE s.seatID.seatNumber = :seatNum AND s.hall.hallID = :hallId", Seat.class
                );
                seatQuery.setParameter("seatNum", seatNumber);
                seatQuery.setParameter("hallId", hall.getHallID());
                Seat seat = seatQuery.uniqueResult();


                if (seat == null) {
                    showAlert("Booking Failed", "Seat " + seatNumber + " could not be found.");
                    transaction.rollback();
                    return;
                }

                Ticket ticket = new Ticket();
                ticket.setScreening(screening);
                ticket.setSeat(seat);
                ticket.setBookTime(new Date());
                ticket.setTicketPrice(100);
                ticket.setCustomer(currentUser);
                session.save(ticket);
                System.out.println(ticket.getTicketID());


                // FIX: Flush the session to ensure the Ticket is persisted and has a valid ID
                session.flush();

                Reservation reservation = new Reservation();
                reservation.setScreening(screening);
                reservation.setTicket(ticket);
                reservation.setSeat(seat);

                System.out.println("Reservation :" + reservation.getTicket().getTicketID() + reservation.getScreening().getScreeningID());
                ReservationID resID = new ReservationID(screening.getScreeningID(), ticket.getTicketID(), seat.getSeatID(), hall.getHallID());
                reservation.setResID(resID);
//                reservation.setResID(new ReservationID(
//                        screening.getScreeningID(),
//                        ticket.getTicketID(),
//                        seat.getSeatID(),
//                        hall.getHallID()
//                ));

                session.save(reservation);
            }

            transaction.commit();
            showAlert("Booking Successful!", "Your seats have been booked.");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            showAlert("Booking Failed", "An error occurred during your booking. Please try again.");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

//        private void handleViewTicketsButton(ActionEvent event) {
//            // Implement the logic to fetch and display the user's tickets here.
//            // For example, you could switch to a new view that displays a list of tickets.
//            showAlert("Tickets", "This feature is coming soon! Here you would see a list of your booked tickets.");
//
//            // Example: Fetch tickets for the current user and print to console.
//            Session session = null;
//            try {
//                session = sessionFactory.openSession();
//                // Assuming currentUser is set up correctly during login
//                if (currentUser != null) {
//                    Query<Ticket> ticketQuery = session.createQuery(
//                            "FROM Ticket t WHERE t.customer.phoneNumber = :customerPhoneNumber", Ticket.class);
//                    ticketQuery.setParameter("customerPhoneNumber", currentUser.getPhoneNumber());
//                    List<Ticket> userTickets = ticketQuery.list();
//
//                    System.out.println("--- User's Booked Tickets ---");
//                    userTickets.forEach(t -> {
//                        System.out.println("Ticket ID: " + t.getTicketID() +
//                                ", Movie: " + t.getScreening().getMovie().getTitle() +
//                                ", Seat: " + t.getSeat().getSeatID().getSeatNumber() +
//                                ", Price: " + t.getTicketPrice());
//                    });
//                    System.out.println("-----------------------------");
//                } else {
//                    showAlert("Error", "User not logged in. Cannot view tickets.");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                showAlert("Error", "An error occurred while fetching your tickets.");
//            } finally {
//                if (session != null) {
//                    session.close();
//                }
//            }
//        }
//    }




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}