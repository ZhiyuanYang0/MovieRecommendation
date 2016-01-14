
package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.com.model.FavoriteGenres;
import movie.com.model.Genres;
import movie.com.model.Users;

public class FavoriteGenresDao {
    protected ConnectionManager connectionManager;
    private static FavoriteGenresDao instance = null;

    protected FavoriteGenresDao() {
        connectionManager = new ConnectionManager();
    }

    public static FavoriteGenresDao getInstance() {
        if (instance == null) {
            instance = new FavoriteGenresDao();
        }
        return instance;
    }

    public FavoriteGenres create(FavoriteGenres favoriteGenres) throws SQLException {
        String insertFavoriteGenre = "INSERT INTO FavoriteGenres(UserId,GenreId) VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertFavoriteGenre);
            insertStmt.setInt(1, favoriteGenres.getUser().getUserId());
            insertStmt.setInt(2, favoriteGenres.getGenre().getGenreId());
            insertStmt.executeUpdate();
            return favoriteGenres;
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

    public FavoriteGenres getMovieGenresById(int favoriteGenresId) throws SQLException {
        String selectFavoriteGenre = "SELECT FavoriteGenreId,UserId,GenreId"
                + " FROM FavoriteGenres WHERE FavoriteGenreId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFavoriteGenre);
            selectStmt.setInt(1, favoriteGenresId);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            GenresDao genreDao = GenresDao.getInstance();
            if (results.next()) {
                int resultFavoriteGenresId = results.getInt("FavoriteGenreId");
                int userId = results.getInt("UserId");
                int genreId = results.getInt("GenreId");
                Users user = usersDao.getUserByUserId(userId);
                Genres genre = genreDao.getGenreById(genreId);
                FavoriteGenres favoriteGenres = new FavoriteGenres(resultFavoriteGenresId, user,
                        genre);
                return favoriteGenres;
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

    public FavoriteGenres delete(FavoriteGenres favoriteGenres) throws SQLException {
        String deleteFavoriteGenre = "DELETE FROM FavoriteGenres WHERE FavoriteGenreId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteFavoriteGenre);
            deleteStmt.setInt(1, favoriteGenres.getFavoriteGenreId());
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

	public List<FavoriteGenres> getFavoriteGenresByUserId(int userId) throws SQLException {
		List<FavoriteGenres> favoriteGenres = new ArrayList<FavoriteGenres>();
		String selectFavoriteGenre = "SELECT FavoriteGenreId,UserId,GenreId"
                + " FROM FavoriteGenres WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFavoriteGenre);
            selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            GenresDao genreDao = GenresDao.getInstance();
            while (results.next()) {
                int favoriteGenresId = results.getInt("FavoriteGenreId");
                int resultUserId = results.getInt("UserId");
                int genreId = results.getInt("GenreId");
                Users user = usersDao.getUserByUserId(resultUserId);
                Genres genre = genreDao.getGenreById(genreId);
                FavoriteGenres favoriteGenre = new FavoriteGenres(favoriteGenresId, user,
                        genre);
                favoriteGenres.add(favoriteGenre);
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
        return favoriteGenres;
	}
}
