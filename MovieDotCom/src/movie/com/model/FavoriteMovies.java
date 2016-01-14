
package movie.com.model;

public class FavoriteMovies {
    private int favoriteMovieId;
    private Users user;
    private Movies movie;
    private int rating;

    public FavoriteMovies(int favoriteMovieId, Users user, Movies movie, int rating) {
        this.favoriteMovieId = favoriteMovieId;
        this.user = user;
        this.movie = movie;
        this.rating = rating;
    }

    public FavoriteMovies(int favoriteMovieId) {
        this.favoriteMovieId = favoriteMovieId;
    }

    public int getFavoriteMovieId() {
        return favoriteMovieId;
    }

    public void setFavoriteMovieId(int favoriteMovieId) {
        this.favoriteMovieId = favoriteMovieId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
