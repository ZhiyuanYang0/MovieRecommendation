
package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import movie.com.model.Genres;
import movie.com.model.MovieGenres;
import movie.com.model.Movies;

public class MovieGenresDao {

    protected ConnectionManager connectionManager;
    private static MovieGenresDao instance = null;

    protected MovieGenresDao() {
        connectionManager = new ConnectionManager();
    }

    public static MovieGenresDao getInstance() {
        if (instance == null) {
            instance = new MovieGenresDao();
        }
        return instance;
    }

    public MovieGenres create(MovieGenres movieGenre) throws SQLException {
        String insertMovieGenre = "INSERT INTO MovieGenres(MovieId,GenreId) VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMovieGenre);
            insertStmt.setInt(1, movieGenre.getMovie().getMovieId());
            insertStmt.setInt(2, movieGenre.getGenre().getGenreId());
            insertStmt.executeUpdate();
            return movieGenre;
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
        }
    }

    public MovieGenres getMovieGenresById(int movieGenresId) throws SQLException {
        String selectMovieGenre = "SELECT MovieGenreId,MovieId,GenreId"
                + " FROM MovieGenres WHERE MovieGenreId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieGenre);
            selectStmt.setInt(1, movieGenresId);
            results = selectStmt.executeQuery();
            MoviesDao moviesDao = MoviesDao.getInstance();
            GenresDao genresDao = GenresDao.getInstance();
            if (results.next()) {
                int resultMovieGenresId = results.getInt("MovieGenreId");
                int movieId = results.getInt("MovieId");
                int genreId = results.getInt("GenreId");
                Movies movie = moviesDao.getMovieById(movieId);
                Genres genre = genresDao.getGenreById(genreId);
                MovieGenres movieGenre = new MovieGenres(resultMovieGenresId, movie, genre);
                return movieGenre;
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
    
    public MovieGenres delete(MovieGenres movieGenres) throws SQLException {
        String deleteMovieGenre = "DELETE FROM MovieGenres WHERE MovieGenreId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMovieGenre);
            deleteStmt.setInt(1, movieGenres.getMovieGenreId());
            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}
