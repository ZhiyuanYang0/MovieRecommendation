#CREATE SCHEMA IF NOT EXISTS MovieDB;
Use MovieDB;

-- DROP TABLE IF EXISTS FavoriteMovies;
-- DROP TABLE IF EXISTS FavoriteDirectors;
-- DROP TABLE IF EXISTS FavoriteActors;
-- DROP TABLE IF EXISTS FavoriteGenres;
-- DROP TABLE IF EXISTS PerformedMovies;
-- DROP TABLE IF EXISTS DirectedMovies;
-- DROP TABLE IF EXISTS MovieGenres;
-- DROP TABLE IF EXISTS GenreTypes;
-- DROP TABLE IF EXISTS Users;
-- DROP TABLE IF EXISTS Actors;
-- DROP TABLE IF EXISTS Directors;
-- DROP TABLE IF EXISTS Movies;

#CHANGES MADE ON 11/09/2015
#1.changed the Movies.Name to Movies.Title
ALTER TABLE Movies CHANGE Name Title VARCHAR(255);
#2.renamaed the GenreTypes to Genres
ALTER TABLE GenreTypes RENAME AS Genres;
#3.changed all the IDs to int
ALTER TABLE FavoriteGenres CHANGE FavoriteGenreId FavoriteGenreId Int;
ALTER TABLE FavoriteActors CHANGE FavoriteActorId FavoriteActorId Int;
ALTER TABLE FavoriteActors CHANGE UserId UserId Int;
ALTER TABLE FavoriteMovies CHANGE FavoriteMovieId FavoriteMovieId Int;
ALTER TABLE FavoriteDirectors CHANGE FavoriteDirectorId FavoriteDirectorId Int;
ALTER TABLE MovieGenres CHANGE MovieGenreId MovieGenreId Int;

ALTER TABLE FavoriteGenres DROP FOREIGN KEY fk_FavoriteGenres_UserId;
ALTER TABLE FavoriteMovies DROP FOREIGN KEY fk_FavoriteMovies_UserId;
ALTER TABLE FavoriteDirectors DROP FOREIGN KEY fk_FavoriteDirectors_UserId;
ALTER TABLE FavoriteActors DROP FOREIGN KEY fk_FavoriteActors_UserId;

ALTER TABLE Users CHANGE UserId UserId Int;
ALTER TABLE FavoriteGenres CHANGE UserId UserId INT;
ALTER TABLE FavoriteActors CHANGE UserId UserId INT;
ALTER TABLE FavoriteDirectors CHANGE UserId UserId INT;
ALTER TABLE FavoriteMovies CHANGE UserId UserId INT;


ALTER TABLE FavoriteGenres
 ADD CONSTRAINT fk_FavoriteGenres_UserId
 FOREIGN KEY Users(UserId)
 REFERENCES Users(UserId) ON UPDATE CASCADE ON DELETE SET NULL;
 
ALTER TABLE FavoriteDirectors
	ADD CONSTRAINT fk_FavoriteDirectors_UserId
    FOREIGN KEY Users(UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL;
    
ALTER TABLE FavoriteActors
 ADD CONSTRAINT fk_FavoriteActors_UserId
    FOREIGN KEY (UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE FavoriteMovies
 ADD CONSTRAINT fk_FavoriteMovies_UserId
    FOREIGN KEY (UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL;



ALTER TABLE FavoriteMovies DROP FOREIGN KEY fk_FavoriteMovies_MovieId;
ALTER TABLE PerformedMovies DROP FOREIGN KEY fk_PerformedMovies_MovieId;
ALTER TABLE DirectedMovies DROP FOREIGN KEY fk_DirectedMovies_MovieId;
ALTER TABLE MovieGenres DROP FOREIGN KEY fk_MovieGenres_MovieId;

ALTER TABLE Movies CHANGE MovieId MovieId Int;
ALTER TABLE MovieGenres CHANGE MovieId MovieId Int;
ALTER TABLE DirectedMovies CHANGE MovieId MovieId Int;
ALTER TABLE PerformedMovies CHANGE MovieId MovieId Int;
ALTER TABLE FavoriteMovies CHANGE MovieId MovieId Int;

ALTER TABLE MovieGenres
 ADD CONSTRAINT fk_MovieGenres_MovieId
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE DirectedMovies
 ADD CONSTRAINT fk_DirectedMovies_MovieId 
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE PerformedMovies
 ADD CONSTRAINT fk_PerformedMovies_MovieId
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE FavoriteMovies
 ADD CONSTRAINT fk_FavoriteMovies_MovieId
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE;




CREATE TABLE Movies (
  MovieId BigInt AUTO_INCREMENT,
  Name VARCHAR(255),
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
  UserId BigInt AUTO_INCREMENT,
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

CREATE TABLE GenreTypes (
  GenreId INT AUTO_INCREMENT,
  GenreType ENUM('Action', 'Adult', 'Adventure', 'Animation',
  'Comedy', 'Crime', 'Documentary', 'Drama', 'Family', 'Fantasy',
  'Film-Noir', 'Horror', 'Music', 'Musical', 'Mystery', 
  'Romance', 'Sci-Fi', 'Short', 'Thriller', 'War', 'Western', 
  'Biography', 'History') UNIQUE,
  CONSTRAINT pk_GenreTypes_GenreId PRIMARY KEY (GenreId)
);

-- INSERT INTO GenreTypes(GenreType)
-- VALUES('Action'), ('Adult'), ('Adventure'), ('Animation'),
--   ('Comedy'), ('Crime'), ('Documentary'), ('Drama'), ('Family'), ('Fantasy'),
--   ('Film-Noir'), ('Horror'), ('Music'), ('Musical'), ('Mystery'), 
--   ('Romance'), ('Sci-Fi'), ('Short'), ('Thriller'), ('War'), ('Western'), 
--   ('Biography'), ('History');

CREATE TABLE MovieGenres (
  MovieGenreId BigInt AUTO_INCREMENT,
  MovieId BIGINT,
  GenreId INT,
  CONSTRAINT pk_MovieGenres_MovieGenreId PRIMARY KEY (MovieGenreId),
  CONSTRAINT fk_MovieGenres_MovieId
    FOREIGN KEY (MovieId)
    REFERENCES Movies(MovieId)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_MovieGenres_GenreId
    FOREIGN KEY (GenreId)
    REFERENCES GenreTypes(GenreId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FavoriteDirectors (
  FavoriteDirectorId BIGINT AUTO_INCREMENT,
  UserId BIGINT,
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
  MovieId BIGINT,
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
  MovieId BIGINT,
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
  FavoriteMovieId BIGINT AUTO_INCREMENT,
  UserId BIGINT,
  MovieId BIGINT,
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
  FavoriteActorId BIGINT AUTO_INCREMENT,
  UserId BIGINT,
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
  FavoriteGenreId BIGINT AUTO_INCREMENT,
  UserId BIGINT,
  GenreId INT,
  CONSTRAINT pk_FavoriteGenres_FavoriteGenreId PRIMARY KEY (FavoriteGenreId),
  CONSTRAINT fk_FavoriteGenres_UserId
    FOREIGN KEY (UserId)
    REFERENCES Users(UserId)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_FavoriteGenres_GenreId
    FOREIGN KEY (GenreId)
    REFERENCES GenreTypes(GenreId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

