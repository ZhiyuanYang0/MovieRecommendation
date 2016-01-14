
package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.com.model.FavoriteMovies;
import movie.com.model.Movies;
import movie.com.model.Users;

public class FavoriteMoviesDao {

    protected ConnectionManager connectionManager;
    private static FavoriteMoviesDao instance = null;

    protected FavoriteMoviesDao() {
        connectionManager = new ConnectionManager();
    }

    public static FavoriteMoviesDao getInstance() {
        if (instance == null) {
            instance = new FavoriteMoviesDao();
        }
        return instance;
    }

    public FavoriteMovies create(FavoriteMovies favoriteMovies) throws SQLException {
        String insertFavoriteMovie = "INSERT INTO FavoriteMovies(UserId,MovieId,Rating) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertFavoriteMovie);
            insertStmt.setInt(1, favoriteMovies.getUser().getUserId());
            insertStmt.setInt(2, favoriteMovies.getMovie().getMovieId());
            insertStmt.setInt(3, favoriteMovies.getRating());
            insertStmt.executeUpdate();
            return favoriteMovies;
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

    public FavoriteMovies getFavoriteMoviesById(int favoriteMoviesId) throws SQLException {
        String selectFavoriteMovies = "SELECT FavoriteMovieId,UserId,MovieId,Rating"
                + " FROM FavoriteMovies WHERE FavoriteMovieId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFavoriteMovies);
            selectStmt.setInt(1, favoriteMoviesId);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            MoviesDao moviesDao = MoviesDao.getInstance();
            if (results.next()) {
                int resultFavoriteMoviesId = results.getInt("FavoriteMovieId");
                int userId = results.getInt("UserId");
                int movieId = results.getInt("MovieId");
                int rating = results.getInt("Rating");
                Users user = usersDao.getUserByUserId(userId);
                Movies movie = moviesDao.getMovieById(movieId);
                FavoriteMovies favoriteMovies = new FavoriteMovies(resultFavoriteMoviesId, user,
                        movie, rating);
                return favoriteMovies;
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
    
    public List<FavoriteMovies> getFavoriteMoviesByUserId(int userId) throws SQLException {
    	List<FavoriteMovies> favoriteMovies = new ArrayList<FavoriteMovies>();
        String selectFavoriteMovies = "SELECT FavoriteMovieId,UserId,MovieId,Rating"
                + " FROM FavoriteMovies WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFavoriteMovies);
            selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            MoviesDao moviesDao = MoviesDao.getInstance();
            while (results.next()) {
                int resultFavoriteMoviesId = results.getInt("FavoriteMovieId");
                int resultUserId = results.getInt("UserId");
                int resultMovieId = results.getInt("MovieId");
                int rating = results.getInt("Rating");
                Users user = usersDao.getUserByUserId(resultUserId);
                Movies movie = moviesDao.getRecomMovieById(resultMovieId);
                FavoriteMovies favoriteMovie = new FavoriteMovies(resultFavoriteMoviesId, user,
                        movie, rating);
                favoriteMovies.add(favoriteMovie);
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
        return favoriteMovies;
    }

    public FavoriteMovies getFavoriteMoviesByUserIdAndMovieId(int userId, int movieId)
            throws SQLException {
        String selectFavoriteMovies = "SELECT FavoriteMovieId,UserId,MovieId,Rating"
                + " FROM FavoriteMovies WHERE UserId=? AND MovieId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFavoriteMovies);
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, movieId);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            MoviesDao moviesDao = MoviesDao.getInstance();
            if (results.next()) {
                int resultFavoriteMoviesId = results.getInt("FavoriteMovieId");
                int resultUserId = results.getInt("UserId");
                int resultMovieId = results.getInt("MovieId");
                int rating = results.getInt("Rating");
                Users user = usersDao.getUserByUserId(userId);
                Movies movie = moviesDao.getMovieById(movieId);
                FavoriteMovies favoriteMovies = new FavoriteMovies(resultFavoriteMoviesId, user,
                        movie, rating);
                return favoriteMovies;
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
    
    public boolean updateRating(Users user, Movies movie, int rating) throws SQLException {
        String updateRating = "UPDATE FavoriteMovies SET Rating=? WHERE UserId=? AND MovieId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateRating);
            updateStmt.setInt(1, rating);
            updateStmt.setInt(2, user.getUserId());
            updateStmt.setInt(3, movie.getMovieId());
            updateStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    public FavoriteMovies delete(FavoriteMovies favoriteMovies) throws SQLException {
        String deleteFavoriteMovie = "DELETE FROM FavoriteMovies WHERE FavoriteMovieId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteFavoriteMovie);
            deleteStmt.setInt(1, favoriteMovies.getFavoriteMovieId());
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
