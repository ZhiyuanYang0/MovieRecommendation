package movie.com.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import movie.com.model.Actors;
import movie.com.model.FavoriteActors;
import movie.com.model.Users;


public class FavoriteActorsDao {
    protected ConnectionManager connectionManager;	
	// Single pattern: instantiation is limited to one object.
	private static FavoriteActorsDao instance = null;
	protected FavoriteActorsDao() {
		connectionManager = new ConnectionManager();
	}
	public static FavoriteActorsDao getInstance() {
		if(instance == null) {
			instance = new FavoriteActorsDao();
		}
		return instance;
	}
	
	// create new FavoriteActor
	public FavoriteActors create(FavoriteActors favoriteActor) throws SQLException {
		String insertFavoriteActor =
			"INSERT INTO FavoriteActors(UserId,ActorId) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFavoriteActor,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, favoriteActor.getUser().getUserId());
			insertStmt.setInt(2, favoriteActor.getActor().getActorId());				
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteActorId = -1;
			if(resultKey.next()) {
				favoriteActorId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favoriteActor.setFavoriteActorId(favoriteActorId);
			return favoriteActor;
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
	
	// delete actor
	public FavoriteActors delete(FavoriteActors favoriteActor) throws SQLException {
		String deleteFavoriteActor = "DELETE FROM FavoriteActors WHERE FavoriteActorId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavoriteActor);
			deleteStmt.setInt(1, favoriteActor.getFavoriteActorId());
			deleteStmt.executeUpdate();
			// Return null so the caller can no longer operate on the Persons instance.
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
	
	// get FavoriteActor by FavoriteActorId
	public FavoriteActors getFavoriteActorById(int favoriteActorId) throws SQLException {
		String selectFavoriteActor = "SELECT FavoriteActorId,UserId,ActorId "
				+ " FROM FavoriteActors WHERE FavoriteActorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavoriteActor);
			selectStmt.setInt(1, favoriteActorId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ActorsDao actorsDao = ActorsDao.getInstance();
			if(results.next()) {
				int userId = results.getInt("UserId");
				int actorId = results.getInt("ActorId");
				Users user = usersDao.getUserByUserId(userId);
				Actors actor = actorsDao.getActorById(actorId);
				FavoriteActors favoriteActor = new FavoriteActors(favoriteActorId,user,actor);
				return favoriteActor;
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
	
	// get FavoriteActor by FavoriteActorByUserId
	public List<FavoriteActors> getFavoriteActorByUserId(int userId) throws SQLException {
		List<FavoriteActors> favoriteActors = new ArrayList<FavoriteActors>();
		String selectFavoriteActor = "SELECT FavoriteActorId,UserId,ActorId "
				+ " FROM FavoriteActors WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavoriteActor);
			selectStmt.setInt(1, userId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ActorsDao actorsDao = ActorsDao.getInstance();
			while(results.next()) {
				int favoriteActorId = results.getInt("FavoriteActorId");
				int actorId = results.getInt("ActorId");
				Users user = usersDao.getUserByUserId(userId);
				Actors actor = actorsDao.getActorById(actorId);
				FavoriteActors favoriteActor = new FavoriteActors(favoriteActorId,user,actor);
				favoriteActors.add(favoriteActor);
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
		return favoriteActors;
	}
	
	// get FavoriteActor by FavoriteActorByActorId
	public List<FavoriteActors> getFavoriteActorByActorId(int actorId) throws SQLException {
		List<FavoriteActors> favoriteActors = new ArrayList<FavoriteActors>();
		String selectFavoriteActor = "SELECT FavoriteActorId,UserId,ActorId "
				+ " FROM FavoriteActors WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavoriteActor);
			selectStmt.setInt(1, actorId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ActorsDao actorsDao = ActorsDao.getInstance();
			while(results.next()) {
				int favoriteActorId = results.getInt("FavoriteActorId");
				int userId = results.getInt("UserId");
				Users user = usersDao.getUserByUserId(userId);
				Actors actor = actorsDao.getActorById(actorId);
				FavoriteActors favoriteActor = new FavoriteActors(favoriteActorId,user,actor);
				favoriteActors.add(favoriteActor);
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
		return favoriteActors;
	}
}
