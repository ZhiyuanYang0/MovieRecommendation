
package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import movie.com.model.Movies;

public class MoviesDao {
    protected ConnectionManager connectionManager;

    private static MoviesDao instance = null;

    protected MoviesDao() {
        connectionManager = new ConnectionManager();
    }

    public static MoviesDao getInstance() {
        if (instance == null) {
            instance = new MoviesDao();
        }
        return instance;
    }

    public Movies create(Movies movie) throws SQLException {
        String insertMovie = "INSERT INTO Movies(Title,Year,ImageURL,Rating,Description) " +
                "VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            // BlogPosts has an auto-generated key. So we want to retrieve that key.
            insertStmt = connection.prepareStatement(insertMovie,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, movie.getTitle());
            insertStmt.setInt(2, movie.getYear());
            insertStmt.setString(3, movie.getImageURL());
            insertStmt.setDouble(4, movie.getRating());
            insertStmt.setString(5, movie.getDescription());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();// retrieve auto generated key-hx
            int movieId = -1;
            if (resultKey.next()) {
                movieId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            movie.setMovieId(movieId);
            return movie;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (resultKey != null) {
                resultKey.close();
            }
        }
    }

    public Movies getMovieById(int movieId) throws SQLException {
        String selectMovie = "SELECT MovieId,Title,Year,ImageURL,Rating,Description " +
                "FROM Movies " +
                "WHERE MovieId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovie);
            selectStmt.setInt(1, movieId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int resultMovieId = results.getInt("MovieId");
                String title = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                imageURL = imageURL.replace("_SX54_CR0,0,54,74_", "_SX432_CR0,0,432,592_");
                Double rating = results.getDouble("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(resultMovieId, title, year, imageURL,
                        rating, description);
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }

    public Movies getRecomMovieById(int movieId) throws SQLException {
        String selectMovie = "SELECT MovieId,Title,Year,ImageURL,Rating,Description,"
                + "Actors,Director,Genre "
                + "FROM MoviesForRec "
                + "WHERE MovieId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovie);
            selectStmt.setInt(1, movieId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int resultMovieId = results.getInt("MovieId");
                String title = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                Double rating = results.getDouble("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(resultMovieId, title, year, imageURL,
                        rating, description);
                String actors = results.getString("Actors");
                String director = results.getString("Director");
                String genre = results.getString("Genre");
                movie.setActors(actors);
                movie.setDirector(director);
                movie.setGenre(genre);
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }

    /**
     * Get the all the movies with the same title
     */
    public List<Movies> getMoviesByTitle(String title) throws SQLException {
        List<Movies> movies = new ArrayList<Movies>();
        String selectMovies = "SELECT MovieId,Title,Year,ImageURL,Rating,Description " +
                "FROM Movies " +
                "WHERE Title=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
            selectStmt.setString(1, title);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int movieId = results.getInt("MovieId");
                String resultTitle = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                int rating = results.getInt("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(movieId, resultTitle, year, imageURL, rating,
                        description);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return movies;
    }

    public List<Movies> getRecommendedMoviesByUserId(int userId) throws SQLException {
        List<Movies> movies = new ArrayList<Movies>();
        String selectMovies = "SELECT MovieId,Title,Year,Rating,ImageUrl,Description "
                + "FROM MoviesForRec INNER JOIN "
                + "(SELECT MovieId2 AS mid, Similarity FROM Similarity "
                + "WHERE MovieId1 = "
                + "(SELECT MovieId FROM MovieDB.FavoriteMovies "
                + "WHERE UserId = " + userId
                + " ORDER BY Rating DESC "
                + "LIMIT 1) "
                + "ORDER BY Similarity DESC "
                + "LIMIT 12) AS sTable ON "
                + "MoviesForRec.MovieId = sTable.mid;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
            // selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int movieId = results.getInt("MovieId");
                String resultTitle = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                int rating = results.getInt("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(movieId, resultTitle, year, imageURL, rating,
                        description);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return movies;
    }

    public List<Movies> getMoviesForRating() throws SQLException {
        List<Movies> movies = new ArrayList<Movies>();
        String selectMovies = "SELECT MovieId,Title,Year,ImageURL,Rating,Description "
                + "FROM MoviesForRec "
                + "WHERE MovieId in (25, 5, 65, 17, 13, 66, 40, 32, 38, 9, 56, 18);";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
            // selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int movieId = results.getInt("MovieId");
                String resultTitle = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                // hack here
                imageURL = imageURL.replace("_SX54_CR0,0,54,74_", "_SX216_CR0,0,216,296_");
                int rating = results.getInt("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(movieId, resultTitle, year, imageURL, rating,
                        description);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return movies;
    }

    public List<Movies> getFourLatestMovies() throws SQLException {
        List<Movies> movies = new ArrayList<Movies>();
        String selectMovies = "SELECT MovieId,Title,Year,ImageURL,Rating,Description "
                + "FROM Movies "
                + "LIMIT 4;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
            // selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int movieId = results.getInt("MovieId");
                String resultTitle = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                // hack here
                imageURL = imageURL.replace("_SX54_CR0,0,54,74_", "_SX216_CR0,0,216,296_");
                int rating = results.getInt("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(movieId, resultTitle, year, imageURL, rating,
                        description);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return movies;
    }

    public List<Movies> getPopularMovies() throws SQLException {
        List<Movies> movies = new ArrayList<Movies>();
        String selectMovies = "SELECT MovieId,Title,Year,ImageURL,Rating,Description "
                + "FROM Movies "
                + "WHERE Year = 2015 AND Rating >= 8 "
                + "LIMIT 20;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
            // selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int movieId = results.getInt("MovieId");
                String resultTitle = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                // hack here
                imageURL = imageURL.replace("_SX54_CR0,0,54,74_", "_SX216_CR0,0,216,296_");
                int rating = results.getInt("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(movieId, resultTitle, year, imageURL, rating,
                        description);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return movies;
    }

    public List<Movies> getSearchMovies(String term) throws SQLException {
        List<Movies> movies = new ArrayList<Movies>();
        String selectMovies = "SELECT MovieId,Title,Year,ImageURL,Rating,Description "
                + "FROM Movies "
                + "WHERE Title like '%" + term + "%'"
                + "LIMIT 20;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovies);
//            selectStmt.setString(0, term);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int movieId = results.getInt("MovieId");
                String resultTitle = results.getString("Title");
                int year = results.getInt("Year");
                String imageURL = results.getString("ImageURL");
                // hack here
                imageURL = imageURL.replace("_SX54_CR0,0,54,74_", "_SX216_CR0,0,216,296_");
                int rating = results.getInt("Rating");
                String description = results.getString("Description");
                Movies movie = new Movies(movieId, resultTitle, year, imageURL, rating,
                        description);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return movies;
    }
}
