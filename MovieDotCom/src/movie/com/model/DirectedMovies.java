package movie.com.model;

public class DirectedMovies {
	protected int directedMovieId;
	protected Directors director;
	protected Movies movie;
	
	public DirectedMovies(int directedMovieId, Directors director, Movies movie) {
		this.directedMovieId = directedMovieId;
		this.director = director;
		this.movie = movie;
	}
	
	public DirectedMovies(int directedMovieId) {
		this.directedMovieId = directedMovieId;
	}
	
	public DirectedMovies(Directors director, Movies movie) {
		this.director = director;
		this.movie = movie;
	}
	
	public int getDirectedMovieId() {
		return this.directedMovieId;
	}
	
	public void setDirectedMovieId(int directedMovieId) {
		this.directedMovieId = directedMovieId;
	}
	
	public Directors getDirector() {
		return this.director;
	}
	
	public void setDirector(Directors director) {
		this.director = director;
	}
	
	public Movies getMovie() {
		return this.movie;
	}
	
	public void setMovie(Movies movie) {
		this.movie = movie;
	}
}
