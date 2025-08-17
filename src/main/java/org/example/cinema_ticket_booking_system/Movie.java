package org.example.cinema_ticket_booking_system;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovieID")
    private Integer id;

    @Column(name = "MovieLanguage", length = 100)
    private String language;

    @Column(name = "DateOfRelease")
    private LocalDate dateOfRelease;

    @Column(name = "MovieTitle", nullable = false, length = 150)
    private String title;

    @Column(name = "MovieDescription", length = 600)
    private String description;

    @Column(name = "Duration", nullable = false)
    private Integer durationMinutes;

    @Column(name = "PosterURL", length = 255)
    private String posterUrl;

    @Column(name = "TrailerURL", length = 255)
    private String trailerUrl;

    @ManyToOne
    @JoinColumn(name = "AdminID", foreignKey = @ForeignKey(name = "fk_admin"))
    private User admin;

    @ManyToOne
    @JoinColumn(name = "DirectorID", foreignKey = @ForeignKey(name = "fk_director"))
    private MovieCrew director;


    protected Movie() {}

    public Movie(String title, Integer durationMinutes) {
        setTitle(title);
        setDurationMinutes(durationMinutes);
    }

    public Integer getId() { return id; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) {
        if (language != null && language.length() > 100)
            throw new IllegalArgumentException("language max length is 100");
        this.language = language;
    }

    public LocalDate getDateOfRelease() { return dateOfRelease; }
    public void setDateOfRelease(LocalDate dateOfRelease) { this.dateOfRelease = dateOfRelease; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title is required");
        if (title.length() > 150)
            throw new IllegalArgumentException("title max length is 150");
        this.title = title.trim();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description != null && description.length() > 600)
            throw new IllegalArgumentException("description max length is 600");
        this.description = description;
    }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) {
        if (durationMinutes == null || durationMinutes <= 0)
            throw new IllegalArgumentException("duration must be > 0 minutes");
        this.durationMinutes = durationMinutes;
    }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) {
        if (posterUrl != null && posterUrl.length() > 255)
            throw new IllegalArgumentException("posterUrl max length is 255");
        this.posterUrl = posterUrl;
    }

    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) {
        if (trailerUrl != null && trailerUrl.length() > 255)
            throw new IllegalArgumentException("trailerUrl max length is 255");
        this.trailerUrl = trailerUrl;
    }

    public User getAdmin() { return admin; }
    public void setAdminId(User admin) {
        this.admin = admin;
    }

    public MovieCrew getDirector() { return director;}
    public void setDirector(MovieCrew director) { this.director = director; }
}
