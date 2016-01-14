package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import movie.com.model.Actors;
import movie.com.model.Movies;
import movie.com.model.PerformedMovies;

public class PerformedMoviesDao {
    protected ConnectionManager connectionManager;	
	// Single pattern: instantiation is limited to one object.
	private static PerformedMoviesDao instance = null;
	protected PerformedMoviesDao() {
		connectionManager = new ConnectionManager();
	}
	public static PerformedMoviesDao getInstance() {
		if(instance == null) {
			instance = new PerformedMoviesDao();
		}
		return instance;
	}
	
	// create new PerformedMovie
	public PerformedMovies create(PerformedMovies performedMovie) throws SQLException {
		String insertPerformedMovie =
			"INSERT INTO PerformedMovies(ActorId,MovieId) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPerformedMovie,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, performedMovie.getActor().getActorId());
			insertStmt.setInt(2, performedMovie.getMovie().getMovieId());				
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int performedMovieId = -1;
			if(resultKey.next()) {
				performedMovieId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			performedMovie.setPerformedMovieId(performedMovieId);
			return performedMovie;
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
	
	// delete PerformedMovie
	public PerformedMovies delete(PerformedMovies performedMovie) throws SQLException {
		String deleteFavoriteActor = "DELETE FROM PerformedMovies WHERE PerformedMovieId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavoriteActor);
			deleteStmt.setInt(1, performedMovie.getPerformedMovieId());
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
	
	
	// get PerformedMovie by PerformedMovieId
	public PerformedMovies getPerformedMovieById(int performedMovieId) throws SQLException {
		String selectPerformedMovie = "SELECT PerformedMovieId,ActorId,MovieId "
				+ " FROM PerformedMovies WHERE PerformedMovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerformedMovie);
			selectStmt.setInt(1, performedMovieId);
			results = selectStmt.executeQuery();
			ActorsDao actorsDao = ActorsDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();
			if(results.next()) {
				int actorId = results.getInt("ActorId");
				int movieId = results.getInt("MovieId");
				Actors actor = actorsDao.getActorById(actorId);
				Movies movie = moviesDao.getMovieById(movieId);
				PerformedMovies performedMovie = new PerformedMovies(performedMovieId,actor,movie);
				return performedMovie;
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
	
	// get PerformedMovie by ActorId
	public List<PerformedMovies> getPerformedMovieByActorId(int actorId) throws SQLException {
		List<PerformedMovies> performedMovies = new ArrayList<PerformedMovies>();
		String selectPerformedMovie = "SELECT PerformedMovieId,ActorId,MovieId "
				+ " FROM PerformedMovies WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerformedMovie);
			selectStmt.setInt(1, actorId);
			results = selectStmt.executeQuery();
			ActorsDao actorsDao = ActorsDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();
			while(results.next()) {
				int performedMovieId = results.getInt("PerformedMovieId");
				int movieId = results.getInt("MovieId");
				Actors actor = actorsDao.getActorById(actorId);
				Movies movie = moviesDao.getMovieById(movieId);
				PerformedMovies performedMovie = new PerformedMovies(performedMovieId,actor,movie);
				performedMovies.add(performedMovie);
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
		return performedMovies;
	}
	
	// get PerformedMovie by MovieId
	public List<PerformedMovies> getPerformedMovieByMovieId(int movieId) throws SQLException {
		List<PerformedMovies> performedMovies = new ArrayList<PerformedMovies>();
		String selectPerformedMovie = "SELECT PerformedMovieId,ActorId,MovieId "
				+ " FROM PerformedMovies WHERE MovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerformedMovie);
			selectStmt.setInt(1, movieId);
			results = selectStmt.executeQuery();
			ActorsDao actorsDao = ActorsDao.getInstance();
			MoviesDao moviesDao = MoviesDao.getInstance();
			while(results.next()) {
				int performedMovieId = results.getInt("PerformedMovieId");
				int actorId = results.getInt("ActorId");
				Actors actor = actorsDao.getActorById(actorId);
				Movies movie = moviesDao.getMovieById(movieId);
				PerformedMovies performedMovie = new PerformedMovies(performedMovieId,actor,movie);
				performedMovies.add(performedMovie);
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
		return performedMovies;
	}
}
