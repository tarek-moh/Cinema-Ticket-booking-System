package org.example.cinema_ticket_booking_system;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieBrowserController implements Initializable {

    @FXML
    private TilePane movieTilePane;

    // In a real app, you would fetch this movie data from a database
    private final String[] moviePosters = {
            "/images/movie1.jpg", "/images/movie2.jpg", "/images/movie3.jpg",
            "/images/movie4.jpg", "/images/movie5.jpg", "/images/movie6.jpg"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMovies();
    }

    private void loadMovies() {
        for (String posterPath : moviePosters) {
            VBox movieCard = createMovieCard(posterPath);
            movieTilePane.getChildren().add(movieCard);
        }
    }

    private VBox createMovieCard(String imagePath) {
        VBox card = new VBox(5);
        card.getStyleClass().add("movie-card");

        // Poster Image
        Image posterImage = new Image(getClass().getResourceAsStream(imagePath));
        ImageView posterView = new ImageView(posterImage);
        posterView.setFitWidth(200);
        posterView.setPreserveRatio(true);
        posterView.getStyleClass().add("movie-poster");

        // Movie Title (example)
        String title = imagePath.substring(imagePath.lastIndexOf('/') + 1)
                .replace(".jpg", "")
                .replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2") // Add space before caps
                .toUpperCase();
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("movie-title-label");

        card.getChildren().addAll(posterView, titleLabel);

        // Add event handler to open movie details/booking screen
        card.setOnMouseClicked(event -> System.out.println("Clicked on " + title));

        return card;
    }
}