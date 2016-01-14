package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import movie.com.model.*;

public class FavoriteDirectorsDao {
	protected ConnectionManager connectionManager;
	
	private static FavoriteDirectorsDao instance = null;
	protected FavoriteDirectorsDao() {
		connectionManager = new ConnectionManager();
	}
	public static FavoriteDirectorsDao getInstance() {
		if(instance == null) {
			instance = new FavoriteDirectorsDao();
		}
		return instance;
	}
	
	public FavoriteDirectors create(FavoriteDirectors favoriteDirector) throws SQLException {
		String insertFavoriteDirector =
				"INSERT INTO FavoriteDirectors(UserId, DirectorId) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFavoriteDirector,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, favoriteDirector.getUser().getUserId());
			insertStmt.setInt(2, favoriteDirector.getDirector().getDirectorId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteDirectorId = -1;
			if(resultKey.next()) {
				favoriteDirectorId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favoriteDirector.setFavoriteDirectorId(favoriteDirectorId);
			return favoriteDirector;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public FavoriteDirectors getFavoriteDirectorById(int favoriteDirectorId) throws SQLException {
		String selectFavoriteDirector =
			"SELECT FavoriteDirectorId, UserId, DirectorId " +
			"FROM FavoriteDirectors " +
			"WHERE FavoriteDirectorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavoriteDirector);
			selectStmt.setInt(1, favoriteDirectorId);
			results = selectStmt.executeQuery();
			DirectorsDao directorDao = DirectorsDao.getInstance();
			UsersDao userDao = UsersDao.getInstance();
			if(results.next()) {
				int resultFavoriteDirectorId = results.getInt("FavoriteDirectorId");
				int userId = results.getInt("UserId");
				int directorId = results.getInt("DirectorId");
				Users user = userDao.getUserByUserId(userId);
				Directors director = directorDao.getDirectorById(directorId);
				FavoriteDirectors favoriteDirector = new FavoriteDirectors(
						resultFavoriteDirectorId, user, director);
				return favoriteDirector;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	public List<FavoriteDirectors> getFavoriteDirectorsByUserId(int userId) throws SQLException {
		List<FavoriteDirectors> favoriteDirectors = new ArrayList<FavoriteDirectors>();
		String selectFavoriteDirectors =
				"SELECT FavoriteDirectorId, UserId, DirectorId " +
				"FROM FavoriteDirectors " +
				"WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavoriteDirectors);
			selectStmt.setInt(1, userId);
			results = selectStmt.executeQuery();
			UsersDao userDao = UsersDao.getInstance();
			DirectorsDao directorDao = DirectorsDao.getInstance();
			while (results.next()) {
				int favoriteDirectorId = results.getInt("FavoriteDirectorId");
				int resultUserId = results.getInt("UserId");
				Users user = userDao.getUserByUserId(resultUserId);
				int directorId = results.getInt("DirectorId");
				Directors director = directorDao.getDirectorById(directorId);
				FavoriteDirectors favoriteDirector = new FavoriteDirectors(
						favoriteDirectorId, user, director);
				favoriteDirectors.add(favoriteDirector);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return favoriteDirectors;
	}
	
	public List<FavoriteDirectors> getFavoriteDirectorsByDirectorId(int directorId) throws SQLException {
		List<FavoriteDirectors> favoriteDirectors = new ArrayList<FavoriteDirectors>();
		String selectFavoriteDirectors =
				"SELECT FavoriteDirectorId, UserId, DirectorId " +
				"FROM FavoriteDirectors " +
				"WHERE DirectorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavoriteDirectors);
			selectStmt.setInt(1, directorId);
			results = selectStmt.executeQuery();
			UsersDao userDao = UsersDao.getInstance();
			DirectorsDao directorDao = DirectorsDao.getInstance();
			while (results.next()) {
				int favoriteDirectorId = results.getInt("FavoriteDirectorId");
				int userId = results.getInt("UserId");
				Users user = userDao.getUserByUserId(userId);
				int resultDirectorId = results.getInt("DirectorId");
				Directors director = directorDao.getDirectorById(resultDirectorId);
				FavoriteDirectors favoriteDirector = new FavoriteDirectors(
						favoriteDirectorId, user, director);
				favoriteDirectors.add(favoriteDirector);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return favoriteDirectors;
	}
	
	public FavoriteDirectors delete(FavoriteDirectors favoriteDirector) throws SQLException {
		String deleteDirectedMovie = "DELETE FROM FavoriteDirectors WHERE FavoriteDirectorId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDirectedMovie);
			deleteStmt.setInt(1, favoriteDirector.getFavoriteDirectorId());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
