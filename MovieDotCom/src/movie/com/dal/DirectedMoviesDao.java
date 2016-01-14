package movie.com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import movie.com.model.*;

public class DirectedMoviesDao {
	protected ConnectionManager connectionManager;
	
	private static DirectedMoviesDao instance = null;
	protected DirectedMoviesDao() {
		connectionManager = new ConnectionManager();
	}
	public static DirectedMoviesDao getInstance() {
		if(instance == null) {
			instance = new DirectedMoviesDao();
		}
		return instance;
	}
	
	public DirectedMovies create(DirectedMovies directedMovie) throws SQLException {
		String insertDirectedMovie =
				"INSERT INTO DirectedMovies(DirectorId, MovieId) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertDirectedMovie,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, directedMovie.getDirector().getDirectorId());
			insertStmt.setLong(2, directedMovie.getMovie().getMovieId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int directedMovieId = -1;
			if(resultKey.next()) {
				directedMovieId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			directedMovie.setDirectedMovieId(directedMovieId);;
			return directedMovie;
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
	
	public DirectedMovies getDirectedMovieById(int directedMovieId) throws SQLException {
		String selectDirectedMovie =
			"SELECT DirectedMovieId, DirectorId, MovieId " +
			"FROM DirectedMovies " +
			"WHERE DirectedMovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDirectedMovie);
			selectStmt.setInt(1, directedMovieId);
			results = selectStmt.executeQuery();
			DirectorsDao directorDao = DirectorsDao.getInstance();
			MoviesDao movieDao = MoviesDao.getInstance();
			if(results.next()) {
				int resultDirectedMovieId = results.getInt("DirectedMovieId");
				int directorId = results.getInt("DirectorId");
				int movieId = results.getInt("MovieId");
				Movies movie = movieDao.getMovieById(movieId);
				Directors director = directorDao.getDirectorById(directorId);
				DirectedMovies directedMovie = new DirectedMovies(resultDirectedMovieId,
						director, movie);
				return directedMovie;
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
	
	public List<DirectedMovies> getMoviesByDirectorId(int directorId) throws SQLException {
		List<DirectedMovies> directedMovies = new ArrayList<DirectedMovies>();
		String selectDirectedMovies =
				"SELECT DirectedMovieId, DirectorId, MovieId " +
				"FROM DirectedMovies " +
				"WHERE DirectorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDirectedMovies);
			selectStmt.setInt(1, directorId);
			results = selectStmt.executeQuery();
			DirectorsDao directorDao = DirectorsDao.getInstance();
			MoviesDao movieDao = MoviesDao.getInstance();
			while (results.next()) {
				int directedMovieId = results.getInt("DirectedMovieId");
				int movieId = results.getInt("MovieId");
				Movies movie = movieDao.getMovieById(movieId);
				Directors director = directorDao.getDirectorById(directorId);
				DirectedMovies directedMovie = new DirectedMovies(directedMovieId, director, movie);
				directedMovies.add(directedMovie);
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
		return directedMovies;
	}
	
	public List<DirectedMovies> getMoviesByMovieId(int movieId) throws SQLException {
		List<DirectedMovies> directedMovies = new ArrayList<DirectedMovies>();
		String selectDirectedMovies =
				"SELECT DirectedMovieId, DirectorId, MovieId " +
				"FROM DirectedMovies " +
				"WHERE MovieId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDirectedMovies);
			selectStmt.setInt(1, movieId);
			results = selectStmt.executeQuery();
			DirectorsDao directorDao = DirectorsDao.getInstance();
			MoviesDao movieDao = MoviesDao.getInstance();
			while (results.next()) {
				int directedMovieId = results.getInt("DirectedMovieId");
				int directorId = results.getInt("DirectorId");
				Movies movie = movieDao.getMovieById(movieId);
				Directors director = directorDao.getDirectorById(directorId);
				DirectedMovies directedMovie = new DirectedMovies(directedMovieId, director, movie);
				directedMovies.add(directedMovie);
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
		return directedMovies;
	}
	
	public DirectedMovies delete(DirectedMovies directedMovie) throws SQLException {
		String deleteDirectedMovie = "DELETE FROM DirectedMovies WHERE DirectedMovieId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDirectedMovie);
			deleteStmt.setInt(1, directedMovie.getDirectedMovieId());
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
