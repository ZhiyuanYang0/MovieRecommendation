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

public class ActorsDao {
    protected ConnectionManager connectionManager;	
	// Single pattern: instantiation is limited to one object.
	private static ActorsDao instance = null;
	protected ActorsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ActorsDao getInstance() {
		if(instance == null) {
			instance = new ActorsDao();
		}
		return instance;
	}

	// create new actor
	public Actors create(Actors actor) throws SQLException {
		String insertActor =
			"INSERT INTO Actors(Height, Weight, FirstName, LastName, DoB, Profile, Gender) " +
			"VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertActor,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, actor.getHeight());
			insertStmt.setInt(2, actor.getWeight());			
			insertStmt.setString(3, actor.getFirstName());
			insertStmt.setString(4, actor.getLastName());
			insertStmt.setDate(5, actor.getDoB());	
			insertStmt.setString(6, actor.getProfile());
			insertStmt.setString(7, actor.getGender());		
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int actorId = -1;
			if(resultKey.next()) {
				actorId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			actor.setActorId(actorId);
			return actor;
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
	public Actors delete(Actors actor) throws SQLException {
		String deleteActor = "DELETE FROM Actors WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteActor);
			deleteStmt.setInt(1, actor.getActorId());
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
	
	// get Actor by ActorId
	public Actors getActorById(int actorId) throws SQLException {
		String selectActor = "SELECT ActorId, Height, Weight, FirstName, LastName, DoB, Profile, Gender"
				+ " FROM Actors WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectActor);
			selectStmt.setInt(1, actorId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultActorId = results.getInt("ActorId");
				int height = results.getInt("Height");
				int weight = results.getInt("Weight");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				Date doB = results.getDate("DoB");
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");
				Actors actor = new Actors(resultActorId,height,weight,firstName,lastName,doB,profile,gender);
				return actor;
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
	
	// get Actor by FirstName
	public List<Actors> getActorByFirstName (String firstName) throws SQLException {
		List<Actors> actors = new ArrayList<Actors>();
		String selectActor = "SELECT ActorId, Height, Weight, FirstName, LastName, DoB, Profile, Gender"
				+ " FROM Actors WHERE FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectActor);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultActorId = results.getInt("ActorId");
				int height = results.getInt("Height");
				int weight = results.getInt("Weight");
				String lastName = results.getString("LastName");
				Date doB = results.getDate("DoB");
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");
				Actors actor = new Actors(resultActorId,height,weight,firstName,lastName,doB,profile,gender);
				actors.add(actor);
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
		return actors;
	}
	
	// get Actor by LastName
	public List<Actors> getActorByLastName (String lastName) throws SQLException {
		List<Actors> actors = new ArrayList<Actors>();
		String selectActor = "SELECT ActorId, Height, Weight, FirstName, LastName, DoB, Profile, Gender"
				+ " FROM Actors WHERE LastName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectActor);
			selectStmt.setString(1, lastName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultActorId = results.getInt("ActorId");
				int height = results.getInt("Height");
				int weight = results.getInt("Weight");
				String firstName = results.getString("FirstName");
				Date doB = results.getDate("DoB");
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");
				Actors actor = new Actors(resultActorId,height,weight,firstName,lastName,doB,profile,gender);
				actors.add(actor);
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
		return actors;
	}
	
	public Actors getActorByName(String firstName, String lastName) throws SQLException {
		String selectActor = "SELECT ActorId, Height, Weight, FirstName, LastName, DoB, Profile, Gender"
				+ " FROM Actors WHERE FirstName=? AND LastName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectActor);
			selectStmt.setString(1, firstName);
			selectStmt.setString(2, lastName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int actorId = results.getInt("ActorId");
				int height = results.getInt("Height");
				int weight = results.getInt("Weight");
				String resultFirstName = results.getString("FirstName");
				String resultLastName = results.getString("LastName");
				Date doB = results.getDate("DoB");
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");
				Actors actor = new Actors(actorId,height,weight,resultFirstName,resultLastName,doB,profile,gender);
				return actor;
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
}
