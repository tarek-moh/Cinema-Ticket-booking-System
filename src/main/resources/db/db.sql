
CREATE TABLE User
(
PhoneNumber NVARCHAR(50) Primary Key,
Email NVARCHAR(100) UNIQUE NOT NULL,
PasswordHash NVARCHAR(255) NOT NULL,
FullName NVARCHAR(100),
CustomerID NVARCHAR(50) UNIQUE NULL,
AdminID NVARCHAR(50) UNIQUE NULL,
  CONSTRAINT ck_userlinks_exactly_one
  CHECK (
    (AdminID   IS NOT NULL AND CustomerID IS NULL) OR
    (AdminID   IS NULL     AND CustomerID IS NOT NULL)

);

CREATE TABLE MovieCrew
(
MemberID INT IDENTITY(1,1) PRIMARY KEY,
FullName NVARCHAR(100),
Gender NVARCHAR(50)  CHECK (Gender IN ('Male', 'Female') OR Gender IS NULL),
PhotoURL NVARCHAR(255),
Biography NVARCHAR(1000),
DirectorID NVARCHAR(50) UNIQUE NULL,
ActorID NCARCHAR(50) UNIQUE NULL
NationalityID INT IDENTITY(1,1) PRIMARY KEY,
  CONSTRAINT ck_userlinks_exactly_one
  CHECK (
    (DirectorID   IS NOT NULL AND ActorID IS NULL) OR
    (DirectorID   IS NULL     AND ActorID IS NOT NULL)
CONSTRAINT fk_nationality
   FOREIGN KEY (NationalityID)
   REFERENCES  Nationality(NationalityID)
   ON DELETE CASCADE
   ON UPDATE CASCADE
);


CREATE TABLE Nationality
(
NationalityID INT IDENTITY(1,1) PRIMARY KEY,
Country NVARCHAR(80),
);


CREATE TABLE Movie
(
MovieID INT IDENTITY(1,1) PRIMARY KEY, --<----start at 1 and inceremnt by 1
MovieLanguage VARCHAR(100),
DateOfRelease DATE,
MovieTitle NVARCHAR(150) NOT NULL,
MovieDescription NVARCHAR(600),
Duration INT NOT NULL CHECK (Duration > 0),           --  <--- in MINUTESSSSSSS!!!!
PosterURL NVARCHAR(255),
TrailerURL NVARCHAR(255),
AdminID NVARCHAR(50),
DirectorID INT,
CONSTRAINT fk_admin
  FOREIGN KEY (AdminID) REFERENCES Administrator(AdminID),
   ON DELETE CASCADE
   ON UPDATE CASCADE
CONSTRAINT fk_director
   FOREIGN KEY (DirectorID) REFERENCES Director(DirectorID)
   ON DELETE CASCADE
   ON UPDATE CASCADE
);



CREATE TABLE CastedFor
(
movieID INT,
actorID INT ,
PRIMARY KEY(movieID,actorID),
RolePlayed NVARCHAR(80),
CONSTRAINT fk_movie
   FOREIGN KEY (movieID) REFERENCES Movie(MovieID),
    ON DELETE CASCADE
    ON UPDATE CASCADE
CONSTRAINT fk_actor
   FOREIGN KEY (actorID) REFERENCES Actor(ActorID)
    on delete cascade
    on update cascade
);


CREATE TABLE Genre
(
GenreID INT IDENTITY(1,1) PRIMARY KEY,
GenreName NVARCHAR(80) UNIQUE
);

CREATE TABLE MovieGenre
(
genreID INT,
movieID INT,
PRIMARY KEY(genreID,movieID),
CONSTRAINT fk_genre
   FOREIGN KEY (genreID) REFERENCES Genre(GenreID),
    ON DELETE CASCADE
    ON UPDATE CASCADE
CONSTRAINT fk_movie
   FOREIGN KEY (movieID) REFERENCES Movie(MovieID)
    on delete cascade
    on update cascade
);


CREATE TABLE Ticket
(
TicketID INT IDENTITY (1,1) PRIMARY KEY,
BookTime DATE,
customerID NVARCHAR(50),
TicketPrice INT NOT NULL CHECK(TicketPrice>0),
HallID INT IDENTITY (1,1),
SeatNumber INT IDENTITY (1,1),
CONSTRAINT fk_hall
     FOREIGN KEY (HallID) REFERENCES Hall (HallID)
         ON DELETE CASCADE
         ON UPDATE CASCADE
CONSTRAINT fk_costumer
   FOREIGN KEY (customerID) REFERENCES User (CustomerID)
constraint fk_seat
    FOREIGN KEY (SeatNumber) REFERENCES Seat (SeatNumber)
         ON DELETE CASCADE
         ON UPDATE CASCADE
);

CREATE TABLE Hall
(
HallID INT IDENTITY (1,1) PRIMARY KEY,
Capacity INT NOT NULL
);

CREATE TABLE Seat
(
SeatNumber INT IDENTITY (1,1) ,
hallID INT NOT NULL,
PRIMARY KEY (SeatNumber,hallID),
CONSTRAINT fk_hall
   FOREIGN KEY (hallID) REFERENCES Hall(HallID)
       ON DELETE CASCADE
       ON UPDATE CASCADE
);


CREATE TABLE Screening
(
ScreeningID INT IDENTITY(1,1) PRIMARY KEY,
StartTime DATETIME NOT NULL,
movieID INT NOT NULL ,
hallID INT NOT NULL,
CONSTRAINT fk_movie
   FOREIGN KEY (movieID) REFERENCES Movie(MovieID),
CONSTRAINT fk_hall
   FOREIGN KEY (hallID) REFERENCES Hall(HallID)
       ON DELETE CASCADE
       ON UPDATE CASCADE
);


CREATE TABLE Reservation
(
screeningID INT NOT NULL,
ticketID INT NOT NULL,
SeatNumber INT NOT NULL,
hallID INT NOT NULL,
PRIMARY KEY (screeningID,ticketID,SeatNumber,hallID),
CONSTRAINT fk_screening
  FOREIGN KEY (screeningID) REFERENCES Screening(ScreeningID),
ON DELETE CASCADE
         ON UPDATE CASCADE
CONSTRAINT fk_ticket
  FOREIGN KEY (ticketID) REFERENCES Ticket(TicketID),
ON DELETE CASCADE
         ON UPDATE CASCADE
CONSTRAINT fk_seat
  FOREIGN KEY (SeatNumber, hallID) REFERENCES Seat(SeatNumber,HallID)
ON DELETE CASCADE
         ON UPDATE CASCADE
);
