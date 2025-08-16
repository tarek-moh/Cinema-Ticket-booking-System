package org.example.cinema_ticket_booking_system;
import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "Movie",uniqueConstraints = {
        @UniqueConstraint(name = "uk_mc_director", columnNames = "DirectorID"),
        @UniqueConstraint(name = "uk_mc_actor",    columnNames = "ActorID")
})
public class Movie {
    @Id
    @Column(name="MovieID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="MovieLanguage")
    private String MovieLanguage;
    @Column(name="DateOfRelease")
    private Date DateOfRelease;
    @Column(name="MovieTitle")
    private String MovieTitle;
    @Column(name="MovieDescription")
    private String MovieDescription;
    @Column(name="Duration")
    private int Duration;
    @Column(name="PosterURL")
    private String PosterURL;
    @Column(name="TrailerURL")
    private String TrailerURL;
    public Movie(){
        this.MovieLanguage = MovieLanguage;
        this.DateOfRelease = DateOfRelease;
        this.MovieTitle = MovieTitle;
        this.MovieDescription = MovieDescription;
        this.Duration = Duration;
        this.PosterURL = PosterURL;
        this.TrailerURL = TrailerURL;
    }
    public int getId() {
        return id;
    }
    public String getMovieLanguage() {
        return MovieLanguage;
    }
    public void setMovieLanguage(String movieLanguage) {
        MovieLanguage = movieLanguage;
    }
    public Date getDateOfRelease() {
        return DateOfRelease;
    }
    public void setDateOfRelease(Date dateOfRelease) {
        DateOfRelease = dateOfRelease;
    }
    public String getMovieTitle() {
        return MovieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        MovieTitle = movieTitle;
    }
    public String getMovieDescription() {
        return MovieDescription;
    }
    public void setMovieDescription(String movieDescription) {
        MovieDescription = movieDescription;
    }
    public int getDuration() {
        return Duration;
    }
    public void setDuration(int duration) {
        Duration = duration;
    }
    public String getPosterURL() {
        return PosterURL;
    }
    public void setPosterURL(String posterURL) {
        PosterURL = posterURL;
    }
    public String getTrailerURL() {
        return TrailerURL;
    }
    public void setTrailerURL(String trailerURL) {
        TrailerURL = trailerURL;
    }
}
