package movie.com.tools;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import movie.com.dal.*;
import movie.com.model.*;

public class Inserter {
	public static void main(String[] args) throws SQLException {
		// DAO instances.
		Date date = new Date();
		UsersDao usersDao = UsersDao.getInstance();
		MoviesDao moviesDao = MoviesDao.getInstance();
	//	GenresDao genresDao = GenresDao.getInstance();
		DirectorsDao directorsDao = DirectorsDao.getInstance();
		DirectedMoviesDao directedMoviesDao = DirectedMoviesDao.getInstance();
		FavoriteDirectorsDao favoriteDirectorsDao = FavoriteDirectorsDao.getInstance();
		
		
		// INSERT objects from our model.
		Users user1 = new Users("hx-test1", "test1", "test-email", "h", "x", date, "profile", "f");
		user1 = usersDao.create(user1);
		Users user2 = new Users("hx-test2", "test2", "test-email", "h", "x", date, "profile", "f");
		user2 = usersDao.create(user2);
		
		Movies movie1 = new Movies("movie-test1", 2015, "url", 3.0, "description");
		movie1 = moviesDao.create(movie1);
		Movies movie2 = new Movies("movie-test2", 2014, "url", 4.0, "description");
		movie2 = moviesDao.create(movie2);
		Movies movie3 = new Movies("movie-test3", 2013, "url", 5.0, "description");
		movie3 = moviesDao.create(movie3);
		Movies movie4 = new Movies("movie-test3", 1998, "url", 4.0, "a different movie");
		movie4 = moviesDao.create(movie4);
		
//		
//		Genres genre1 = new Genres(Genres.GenreType.Action);
//		genre1 = genresDao.create(genre1);
		
		Directors director1 = new Directors("FirstName1", "LastName1", date, "profile", "M");
		director1 = directorsDao.create(director1);
		Directors director2 = new Directors("FirstName2", "LastName2", date, "profile", "M");
		director2 = directorsDao.create(director2);
		DirectedMovies directedMovie1 = new DirectedMovies(director1, movie1);
		directedMovie1 = directedMoviesDao.create(directedMovie1);
		DirectedMovies directedMovie2 = new DirectedMovies(director1, movie2);
		directedMovie2 = directedMoviesDao.create(directedMovie2);
		FavoriteDirectors favoriteDirector1 = new FavoriteDirectors(user1, director1);
		favoriteDirector1 = favoriteDirectorsDao.create(favoriteDirector1);
		FavoriteDirectors favoriteDirector2 = new FavoriteDirectors(user1, director2);
		favoriteDirector2 = favoriteDirectorsDao.create(favoriteDirector2);
		FavoriteDirectors favoriteDirector3 = new FavoriteDirectors(user2, director1);
		favoriteDirector3 = favoriteDirectorsDao.create(favoriteDirector3);
		
		
		
		//read
		Users u1 = usersDao.getUserByUserId(1);
		System.out.format("Reading user: u:%s f:%s l:%s e:%s \n",
				u1.getUserName(), u1.getFirstName(), u1.getLastName(), u1.getEmail());
		
		Users u2 = usersDao.getUserByUserName("hx-test2");
		System.out.format("Reading user: u:%s f:%s l:%s e:%s \n",
				u2.getUserName(), u2.getFirstName(), u2.getLastName(), u2.getEmail());
		
		usersDao.updateEmail(user1, "newEmail");
		System.out.format("Reading user: u:%s f:%s l:%s e:%s \n",
				u1.getUserName(), u1.getFirstName(), u1.getLastName(), u1.getEmail());
		
		
		
		Movies m1 = moviesDao.getMovieById(1);
		System.out.format("Reading movie: m:%s r:%f \n",
				m1.getTitle(), m1.getRating());
		List<Movies> mList1 = moviesDao.getMoviesByTitle("movie-test3");
		for(Movies m : mList1) {
			System.out.format("Looping movies: t:%s y:%d r:%f \n",
				m.getTitle(), m.getYear(), m.getRating());
		}
		
		Directors d1 = directorsDao.getDirectorById(1);
		System.out.println("Reading director by id: " + d1.getFirstName() + " " + d1.getLastName() +
				" " + d1.getDoB() + " " + d1.getProfile() + " " + d1.getGender());
		
		Directors d2 = directorsDao.getDirectorByName("FirstName2", "LastName2");
		System.out.println("Reading director by name: " + d2.getFirstName() + " " + d2.getLastName() +
				" " + d2.getDoB() + " " + d2.getProfile() + " " + d2.getGender());
		
		d1 = directorsDao.updateProfile(d1, "newProfile");
		System.out.println("Reading director by id: " + d1.getFirstName() + " " + d1.getLastName() +
				" " + d1.getDoB() + " " + d1.getProfile() + " " + d1.getGender());
		
		DirectedMovies dm1 = directedMoviesDao.getDirectedMovieById(1);
		System.out.println("Reading directed movies by id: " + dm1.getDirectedMovieId() + " " 
				+ " " + dm1.getDirector().getDirectorId() + " " + dm1.getMovie().getMovieId());
		
		List<DirectedMovies> dmList1 = directedMoviesDao.getMoviesByDirectorId(d1.getDirectorId());
		for (DirectedMovies dm: dmList1) {
			System.out.println("Reading directed movies by director id: " + dm.getDirectedMovieId() + " " 
					+ " " + dm.getDirector().getDirectorId() + " " + dm.getMovie().getMovieId());
		}
		
		List<DirectedMovies> dmList2 = directedMoviesDao.getMoviesByMovieId(m1.getMovieId());
		for (DirectedMovies dm: dmList2) {
			System.out.println("Reading directed movies by movie id: " + dm.getDirectedMovieId() + " " 
					+ " " + dm.getDirector().getDirectorId() + " " + dm.getMovie().getMovieId());
		}
		
		FavoriteDirectors fd1 = favoriteDirectorsDao.getFavoriteDirectorById(1);
		System.out.println("Reading favorite director by id: " + fd1.getFavoriteDirectorId() + " "
				+ fd1.getUser().getUserName() + " " + fd1.getDirector().getDirectorId());
		
		List<FavoriteDirectors> fdList1 = favoriteDirectorsDao.getFavoriteDirectorsByUserId(u1.getUserId());
		for (FavoriteDirectors fd: fdList1) {
			System.out.println("Reading favorite director by user id: " + fd.getFavoriteDirectorId() + " "
					+ fd.getUser().getUserName() + " " + fd.getDirector().getDirectorId());
		}
		
		List<FavoriteDirectors> fdList2 = favoriteDirectorsDao.getFavoriteDirectorsByDirectorId(d1.getDirectorId());
		for (FavoriteDirectors fd: fdList2) {
			System.out.println("Reading favorite director by director id: " + fd.getFavoriteDirectorId() + " "
					+ fd.getUser().getUserName() + " " + fd.getDirector().getDirectorId());
		}
		
		//Delete
		directorsDao.delete(director1);
		directorsDao.delete(director2);
		directedMoviesDao.delete(directedMovie1);
		directedMoviesDao.delete(directedMovie2);
		favoriteDirectorsDao.delete(favoriteDirector1);
		favoriteDirectorsDao.delete(favoriteDirector2);
		favoriteDirectorsDao.delete(favoriteDirector3);
	}

}
