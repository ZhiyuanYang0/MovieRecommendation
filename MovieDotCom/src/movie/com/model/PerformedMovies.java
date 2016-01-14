package movie.com.model;

public class PerformedMovies {
	protected int performedMovieId;
	protected Actors actor;
	protected Movies movie;

	public PerformedMovies(int performedMovieId, Actors actor, Movies movie) {
		super();
		this.performedMovieId = performedMovieId;
		this.actor = actor;
		this.movie = movie;
	}
	
	public int getPerformedMovieId() {
		return performedMovieId;
	}

	public void setPerformedMovieId(int performedMovieId) {
		this.performedMovieId = performedMovieId;
	}

	public Actors getActor() {
		return actor;
	}
	public void setActor(Actors actor) {
		this.actor = actor;
	}
	public Movies getMovie() {
		return movie;
	}
	public void setMovie(Movies movie) {
		this.movie = movie;
	}
}
