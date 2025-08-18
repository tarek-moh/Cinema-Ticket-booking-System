CREATE TABLE [User] (
    PhoneNumber NVARCHAR(50) PRIMARY KEY,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(255) NOT NULL,
    FullName NVARCHAR(100),
    UserType NVARCHAR(10) NOT NULL
);

CREATE TABLE Nationality (
    NationalityID INT IDENTITY(1,1) PRIMARY KEY,
    Country NVARCHAR(80) NOT NULL
);

CREATE TABLE MovieCrew (
    MemberID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(100) NOT NULL,
    Gender NVARCHAR(50) CHECK (Gender IN ('Male', 'Female') OR Gender IS NULL),
    PhotoURL NVARCHAR(255),
    Biography NVARCHAR(1000),
    MemberType NVARCHAR(10) NOT NULL  -- 'ACTOR' or 'DIRECTOR'
);

CREATE TABLE Movie (
    MovieID INT IDENTITY(1,1) PRIMARY KEY,
    MovieLanguage VARCHAR(100),
    DateOfRelease DATE,
    MovieTitle NVARCHAR(150) NOT NULL,
    MovieDescription NVARCHAR(600),
    Duration INT NOT NULL CHECK (Duration > 0),
    PosterURL NVARCHAR(255),
    TrailerURL NVARCHAR(255),
    AdminID NVARCHAR(50) NOT NULL,
    DirectorID INT NOT NULL,
    CONSTRAINT fk_admin FOREIGN KEY (AdminID)
        REFERENCES [User](PhoneNumber)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT fk_director FOREIGN KEY (DirectorID)
        REFERENCES MovieCrew(MemberID)
        ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE CastedFor (
    MovieID INT NOT NULL,
    ActorID INT NOT NULL,
    RolePlayed NVARCHAR(100),
    PRIMARY KEY (MovieID, ActorID),
    CONSTRAINT fk_cast_movie FOREIGN KEY (MovieID)
        REFERENCES Movie(MovieID)
        ON DELETE CASCADE ON UPDATE CASCADE, -- This is fine: if the movie is deleted, the cast record is irrelevant.
    CONSTRAINT fk_cast_actor FOREIGN KEY (ActorID)
        REFERENCES MovieCrew(MemberID)
        ON DELETE NO ACTION ON UPDATE NO ACTION -- This prevents deleting an actor who is still in a movie's cast.
);

CREATE TABLE Genre (
    GenreID INT IDENTITY(1,1) PRIMARY KEY,
    GenreName NVARCHAR(80) UNIQUE NOT NULL
);

CREATE TABLE MovieGenre (
    GenreID INT NOT NULL,
    MovieID INT NOT NULL,
    PRIMARY KEY (GenreID, MovieID),
    CONSTRAINT fk_moviegenre_genre FOREIGN KEY (GenreID)
        REFERENCES Genre(GenreID)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT fk_moviegenre_movie FOREIGN KEY (MovieID)
        REFERENCES Movie(MovieID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Hall (
    HallID INT IDENTITY(1,1) PRIMARY KEY,
    Capacity INT NOT NULL CHECK (Capacity > 0)
);

CREATE TABLE Seat (
    SeatNumber INT NOT NULL,
    HallID INT NOT NULL,
    PRIMARY KEY (SeatNumber, HallID),
    CONSTRAINT fk_seat_hall FOREIGN KEY (HallID)
        REFERENCES Hall(HallID)
        ON DELETE CASCADE ON UPDATE CASCADE
        -- If a hall is deleted, delete all its seats
);

CREATE TABLE Ticket (
    TicketID INT IDENTITY (1,1) PRIMARY KEY,
    BookTime DATE NOT NULL,
    CustomerID NVARCHAR(50) NOT NULL,
    TicketPrice INT NOT NULL CHECK(TicketPrice > 0),
    HallID INT NOT NULL,
    SeatNumber INT NOT NULL,
    CONSTRAINT fk_ticket_hall FOREIGN KEY (HallID)
        REFERENCES Hall(HallID)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT fk_ticket_customer FOREIGN KEY (CustomerID)
        REFERENCES [User](PhoneNumber)
        ON DELETE NO ACTION,
    CONSTRAINT fk_ticket_seat FOREIGN KEY (SeatNumber, HallID)
        REFERENCES Seat(SeatNumber, HallID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
        -- Tickets should not vanish automatically when seats/users are deleted
);

CREATE TABLE Screening (
    ScreeningID INT IDENTITY(1,1) PRIMARY KEY,
    StartTime DATETIME NOT NULL,
    MovieID INT NOT NULL,
    HallID INT NOT NULL,
    TicketPrice INT NOT NULL,
    CONSTRAINT fk_screening_movie FOREIGN KEY (MovieID)
        REFERENCES Movie(MovieID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_screening_hall FOREIGN KEY (HallID)
        REFERENCES Hall(HallID)
        ON DELETE NO ACTION ON UPDATE CASCADE
        -- If a movie is deleted, remove its screenings
        -- If a hall is deleted, block unless screenings are reassigned
);

CREATE TABLE Reservation (
    ScreeningID INT NOT NULL,
    TicketID INT NOT NULL,
    SeatNumber INT NOT NULL,
    HallID INT NOT NULL,
    PRIMARY KEY (ScreeningID, TicketID, SeatNumber, HallID),
    CONSTRAINT fk_reservation_screening FOREIGN KEY (ScreeningID)
        REFERENCES Screening(ScreeningID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_reservation_ticket FOREIGN KEY (TicketID)
        REFERENCES Ticket(TicketID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_reservation_seat FOREIGN KEY (SeatNumber, HallID)
        REFERENCES Seat(SeatNumber, HallID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE MemberNationality (
    NationalityID INT NOT NULL,
    MemberID INT NOT NULL,
    PRIMARY KEY (NationalityID, MemberID),
    FOREIGN KEY (NationalityID) REFERENCES Nationality(NationalityID)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (MemberID) REFERENCES MovieCrew(MemberID)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);