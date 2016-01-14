
package movie.com.model;

public class MovieGenres {
    private int movieGenreId;
    private Movies movie;
    private Genres genre;

    public MovieGenres(int movieGenreId, Movies movie, Genres genre) {
        this.movieGenreId = movieGenreId;
        this.movie = movie;
        this.genre = genre;
    }

    public MovieGenres(int movieGenreId) {
        this.movieGenreId = movieGenreId;
    }

    public int getMovieGenreId() {
        return movieGenreId;
    }

    public void setMovieGenreId(int movieGenreId) {
        this.movieGenreId = movieGenreId;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

}
