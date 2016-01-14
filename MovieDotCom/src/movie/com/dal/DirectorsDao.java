package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import movie.com.model.*;

public class DirectorsDao {
	protected ConnectionManager connectionManager;
	private static DirectorsDao instance = null;
	protected DirectorsDao() {
		connectionManager = new ConnectionManager();
	}
	public static DirectorsDao getInstance() {
		if(instance == null) {
			instance = new DirectorsDao();
		}
		return instance;
	} 
	
	public Directors create(Directors director) throws SQLException {
		String insertDirector = 
				"INSERT INTO Directors(FirstName,LastName,DoB,Profile,Gender) "
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertDirector);
			
			insertStmt = connection.prepareStatement(insertDirector,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, director.getFirstName());
			insertStmt.setString(2, director.getLastName());
			insertStmt.setTimestamp(3, new Timestamp(director.getDoB().getTime()));
			insertStmt.setString(4, director.getProfile());
			insertStmt.setString(5, director.getGender());
			insertStmt.executeUpdate();
			

			resultKey = insertStmt.getGeneratedKeys();
			int directorId = -1;
			if(resultKey.next()) {
				directorId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			director.setDirectorId(directorId);
			return director;
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
		}
	}
	
	public Directors updateProfile(Directors director, String profile) throws SQLException {
		String updateActor = "UPDATE Directors SET Profile=? WHERE DirectorId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateActor);
			updateStmt.setString(1, profile);
			updateStmt.setInt(2, director.getDirectorId());
			updateStmt.executeUpdate();
			
			// Update the user param before returning to the caller.
			director.setProfile(profile);
			return director;
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
	
	public Directors getDirectorById(int directorId) throws SQLException {
		String selectDirector = 
				"SELECT DirectorId,FirstName,LastName,DoB,Profile,Gender "+
				" FROM Directors" +
				" WHERE DirectorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDirector);
			selectStmt.setInt(1, directorId);
			results = selectStmt.executeQuery(); 
	
			if(results.next()) {
				int resultDirectorId = results.getInt("DirectorId");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				Date dob = new Date();
				if (results.getString("DoB") != null) {
					dob = new Date(results.getTimestamp("DoB").getTime());
				}
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");			
				Directors director = 
						new Directors(resultDirectorId, firstName, lastName, dob, profile, gender);
				return director;
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
	
	public Directors getDirectorByName(String firstName, String lastName) throws SQLException {
		String selectDirector = 
				"SELECT DirectorId,FirstName,LastName,DoB,Profile,Gender "+
				" FROM Directors" +
				" WHERE FirstName=? AND LastName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDirector);
			selectStmt.setString(1, firstName);
			selectStmt.setString(2, lastName);
			results = selectStmt.executeQuery(); 
	
			if(results.next()) {
				int directorId = results.getInt("DirectorId");
				String resultFirstName = results.getString("FirstName");
				String resultLastName = results.getString("LastName");
				Date dob = new Date();
				if (results.getString("DoB") != null) {
					dob = new Date(results.getTimestamp("DoB").getTime());
				}
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");			
				Directors director = 
						new Directors(directorId, resultFirstName, resultLastName, dob, profile, gender);
				return director;
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
	
	public Directors delete(Directors director) throws SQLException {
		String deleteDirector = "DELETE FROM Directors WHERE DirectorId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDirector);
			deleteStmt.setInt(1, director.getDirectorId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Users instance.
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
