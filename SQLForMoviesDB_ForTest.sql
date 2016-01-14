
#CREATE SCHEMA IF NOT EXISTS MovieDB_ForTest;
USE MovieDB_ForTest;

DROP TABLE IF EXISTS FavoriteMovies;
DROP TABLE IF EXISTS FavoriteDirectors;
DROP TABLE IF EXISTS FavoriteActors;
DROP TABLE IF EXISTS FavoriteGenres;
DROP TABLE IF EXISTS PerformedMovies;
DROP TABLE IF EXISTS DirectedMovies;
DROP TABLE IF EXISTS MovieGenres;
DROP TABLE IF EXISTS Genres;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Actors;
DROP TABLE IF EXISTS Directors;
DROP TABLE IF EXISTS Movies;

-- For Testing UsersDao & MoviesDao-hx
-- SELECT * FROM Movies;
-- SELECT * FROM Users;
-- SELECT * FROM Genres;


CREATE TABLE Movies (
  MovieId Int AUTO_INCREMENT,
  Title VARCHAR(255),
  Year INT,
  ImageUrl VARCHAR(255),
  Rating DECIMAL(2,1),
  Description VARCHAR(255),
  CONSTRAINT pk_Movies_MovieId PRIMARY KEY (MovieId)
);

CREATE TABLE Directors (
  DirectorId Int AUTO_INCREMENT,
  FirstName VARCHAR(255),
  LastName VARCHAR(255),
  DoB DATE, 
  Profile VARCHAR(255),
  Gender VARCHAR(255),
  CONSTRAINT pk_Directors_DirectorId PRIMARY KEY (DirectorId)
);

CREATE TABLE Actors (
  ActorId Int AUTO_INCREMENT,
  Height Int,
  Weight Int,
  FirstName VARCHAR(255),
  LastName VARCHAR(255),
  DoB DATE NOT NULL, 
  Profile VARCHAR(255),
  Gender VARCHAR(255),
  CONSTRAINT pk_Actors_ActorId PRIMARY KEY (ActorId)
);

CREATE TABLE Users (
  UserId Int AUTO_INCREMENT,
  UserName VARCHAR(255) UNIQUE,
  Password VARCHAR(255),
  Email VARCHAR(255),
  FirstName VARCHAR(255),
  LastName VARCHAR(255),
  DoB DATE NOT NULL, 
  Profile VARCHAR(255),
  Gender VARCHAR(255),
  CONSTRAINT pk_Users_UserId PRIMARY KEY (UserId)
);

CREATE TABLE Genres (
  GenreId INT AUTO_INCREMENT,
  GenreType ENUM('Action', 'Adult', 'Adventure', 'Animation',
  'Comedy', 'Crime', 'Documentary', 'Drama', 'Family', 'Fantasy',
  'Film-Noir', 'Horror', 'Music', 'Musical', 'Mystery', 
  'Romance', 'Sci-Fi', 'Short', 'Thriller', 'War', 'Western', 
  'Biography', 'History') UNIQUE,
  CONSTRAINT pk_Genres_GenreId PRIMARY KEY (GenreId)
);

-- INSERT INTO GenreTypes(GenreType)
-- VALUES('Action'), ('Adult'), ('Adventure'), ('Animation'),
--   ('Comedy'), ('Crime'), ('Documentary'), ('Drama'), ('Family'), ('Fantasy'),
--   ('Film-Noir'), ('Horror'), ('Music'), ('Musical'), ('Mystery'), 
--   ('Romance'), ('Sci-Fi'), ('Short'), ('Thriller'), ('War'), ('Western'), 
--   ('Biography'), ('History');

CREATE TABLE MovieGenres (
  MovieGenreId Int AUTO_INCREMENT,
  MovieId INT,
  GenreId INT,
  CONSTRAINT pk_MovieGenres_MovieGenreId PRIMARY KEY (MovieGenreId),
  CONSTRAINT fk_MovieGenres_MovieId
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_MovieGenres_GenreId
    FOREIGN KEY (GenreId)
    REFERENCES Genres(GenreId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FavoriteDirectors (
  FavoriteDirectorId INT AUTO_INCREMENT,
  UserId INT,
  DirectorId INT,
  CONSTRAINT pk_FavoriteDirectors_FavoriteDirectorId
  PRIMARY KEY (FavoriteDirectorId),
  CONSTRAINT fk_FavoriteDirectors_UserId
    FOREIGN KEY (UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_FavoriteDirectors_DirectorId
    FOREIGN KEY (DirectorId)
    REFERENCES Directors(DirectorId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE DirectedMovies (
  DirectedMovieId INT AUTO_INCREMENT,
  DirectorId INT,
  MovieId INT,
  CONSTRAINT pk_DirectedMovies_DirectedMovieId PRIMARY KEY (DirectedMovieId),
  CONSTRAINT fk_DirectedMovies_DirectorId
    FOREIGN KEY (DirectorId)
    REFERENCES Directors(DirectorId)
    ON UPDATE CASCADE ON DELETE CASCADE,
 CONSTRAINT fk_DirectedMovies_MovieId 
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE PerformedMovies (
  PerformedMovieId INT AUTO_INCREMENT,
  ActorId INT,
  MovieId INT,
  CONSTRAINT pk_PerformedMovies_PerformedMovieId PRIMARY KEY (PerformedMovieId),
  CONSTRAINT fk_PerformedMovies_ActorId
    FOREIGN KEY (ActorId)
    REFERENCES Actors(ActorId)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_PerformedMovies_MovieId
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FavoriteMovies (
  FavoriteMovieId INT AUTO_INCREMENT,
  UserId INT,
  MovieId INT,
  CONSTRAINT pk_FavoriteMovies_FavoriteMovieId
  PRIMARY KEY (FavoriteMovieId),
  CONSTRAINT fk_FavoriteMovies_UserId
    FOREIGN KEY (UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_FavoriteMovies_MovieId
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FavoriteActors (
  FavoriteActorId INT AUTO_INCREMENT,
  UserId INT,
  ActorId INT,
  CONSTRAINT pk_FavoriteActors_FavoriteActorId PRIMARY KEY (FavoriteActorId),
  CONSTRAINT fk_FavoriteActors_UserId
    FOREIGN KEY (UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_FavoriteActors_ActorId
    FOREIGN KEY (ActorId)
    REFERENCES Actors(ActorId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FavoriteGenres (
  FavoriteGenreId INT AUTO_INCREMENT,
  UserId INT,
  GenreId INT,
  CONSTRAINT pk_FavoriteGenres_FavoriteGenreId PRIMARY KEY (FavoriteGenreId),
  CONSTRAINT fk_FavoriteGenres_UserId
    FOREIGN KEY (UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_FavoriteGenres_GenreId
    FOREIGN KEY (GenreId)
    REFERENCES Genres(GenreId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

