package movie.com.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


import movie.com.model.Users;


public class UsersDao {

	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	//1 connection per dao, singleton pattern-hx for reuse
	private static UsersDao instance = null;
	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}
// every class has 4 basic methods, read, create, insert, update, delete??(not sure) -hx
	/**
	 * Save the Persons instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Users create(Users user) throws SQLException {
		String insertUser = 
				"INSERT INTO Users(UserName,Password, Email, FirstName,LastName, DoB, Profile, Gender) " +
		" VALUES(?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			
			insertStmt = connection.prepareStatement(insertUser,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, user.getUserName()); 
			insertStmt.setString(2, user.getPassword());
			insertStmt.setString(3, user.getEmail());
			insertStmt.setString(4, user.getFirstName());
			insertStmt.setString(5, user.getLastName());
			insertStmt.setTimestamp(6, new Timestamp(user.getDob().getTime()));
			insertStmt.setString(7, user.getProfile());
			insertStmt.setString(8, user.getGender());
			insertStmt.executeUpdate();
			

			resultKey = insertStmt.getGeneratedKeys();
			int userId = -1;
			if(resultKey.next()) {
				userId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			user.setUserId(userId);
			return user;
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
	
	/**
	 * Update the first Name of the Users instance.
	 * This runs a UPDATE statement.
	 */
	public Users updateFirstName(Users user, String newFirstName) throws SQLException {
		String updateUser = "UPDATE Users SET newFirstName=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newFirstName);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			
			// Update the user param before returning to the caller.
			user.setPassword(newFirstName);
			return user;
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
	
	/**
	 * Update the first Name of the Users instance.
	 * This runs a UPDATE statement.
	 */
	public Users updateLastName(Users user, String newLastName) throws SQLException {
		String updateUser = "UPDATE Users SET newLastName=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newLastName);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			
			// Update the user param before returning to the caller.
			user.setPassword(newLastName);
			return user;
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

	/**
	 * Update the Email of the Users instance.
	 * This runs a UPDATE statement.
	 */
	public Users updateEmail(Users user, String newEmail) throws SQLException {
		String updateUser = "UPDATE Users SET Email=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newEmail);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			
			// Update the user param before returning to the caller.
			user.setEmail(newEmail);
			return user;
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
	
	
	
	/**
	 * Update the Password of the Users instance.
	 * This runs a UPDATE statement.
	 */
	public Users updatePassword(Users user, String newPassword) throws SQLException {
		String updateUser = "UPDATE Users SET Password=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newPassword);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			
			// Update the user param before returning to the caller.
			user.setPassword(newPassword);
			return user;
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
	
	/**
	 * Update the Profile of the Users instance.
	 * This runs a UPDATE statement.
	 */
	public Users updateProfile(Users user, String newProfile) throws SQLException {
		String updateUser = "UPDATE Users SET Profile=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newProfile);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			
			// Update the user param before returning to the caller.
			user.setProfile(newProfile);
			return user;
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

	/**
	 * Delete the Users instance.
	 * This runs a DELETE statement.
	 */
	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setInt(1, user.getUserId());
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
	
	
	/**
	 * Get the Users record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Users instance.
	 */
	public Users getUserByUserId(int userId) throws SQLException {
		String selectUser = 
				"SELECT UserId,UserName,Password,Email,FirstName,LastName,DoB,Profile,Gender "+
				" FROM Users" +
				" WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setInt(1, userId);
			results = selectStmt.executeQuery(); 
	
			if(results.next()) {
				int resultUserId = results.getInt("UserId");
				String userName = results.getString("UserName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				Date dob = new Date(results.getTimestamp("DoB").getTime());
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");			
				Users user = 
						new Users(resultUserId, userName, password, email, firstName, lastName, dob, profile, gender);
				return user;
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

	
	
	

	/**
	 * Get the Users record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Users instance.
	 */
	public Users getUserByUserName(String userName) throws SQLException {
		String selectUser = 
				"SELECT UserId,UserName,Password,Email,FirstName,LastName,DoB,Profile,Gender "+
				" FROM Users" +
				" WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery(); 
			if(results.next()) {
				int userId = results.getInt("UserId");
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				Date dob = new Date(results.getTimestamp("DoB").getTime());
				String profile = results.getString("Profile");
				String gender = results.getString("Gender");	
				Users user = new Users(userId,resultUserName,password,email, firstName, lastName, dob, profile, gender);
				return user;
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