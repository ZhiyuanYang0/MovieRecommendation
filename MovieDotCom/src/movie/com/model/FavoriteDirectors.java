package movie.com.model;

public class FavoriteDirectors {
	protected int favoriteDirectorId;
	protected Users user;
	protected Directors director;
	
	public FavoriteDirectors(int favoriteDirectorId, Users user, Directors director) {
		this.favoriteDirectorId = favoriteDirectorId;
		this.user = user;
		this.director = director;
	}
	
	public FavoriteDirectors(int favoriteDirectorId) {
		this.favoriteDirectorId = favoriteDirectorId;
	}
	
	public FavoriteDirectors(Users user, Directors director) {
		this.user = user;
		this.director = director;
	} 
	
	public int getFavoriteDirectorId() {
		return this.favoriteDirectorId;
	}
	
	public void setFavoriteDirectorId(int favoriteDirectorId) {
		this.favoriteDirectorId = favoriteDirectorId;
	}
	
	public Users getUser() {
		return this.user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	public Directors getDirector() {
		return this.director;
	}
	
	public void setDirector(Directors director) {
		this.director = director;
	}
}
