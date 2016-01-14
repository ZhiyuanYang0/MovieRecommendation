
package movie.com.model;

public class FavoriteGenres {
    private int favoriteGenreId;
    private Users user;
    private Genres genre;

    public FavoriteGenres(int favoriteGenreId, Users user, Genres genre) {
        this.favoriteGenreId = favoriteGenreId;
        this.user = user;
        this.genre = genre;
    }

    public FavoriteGenres(int favoriteGenreId) {
        this.favoriteGenreId = favoriteGenreId;
    }

    public FavoriteGenres(Users user, Genres genre) {
    	this.user = user;
        this.genre = genre;
	}

	public int getFavoriteGenreId() {
        return favoriteGenreId;
    }

    public void setFavoriteGenreId(int favoriteGenreId) {
        this.favoriteGenreId = favoriteGenreId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

}
