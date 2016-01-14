package movie.com.model;

public class FavoriteActors {
	protected int favoriteActorId;
	protected Users user;
	protected Actors actor;
	
	public FavoriteActors(int favoriteActorId, Users user, Actors actor) {
		this.favoriteActorId = favoriteActorId;
		this.user = user;
		this.actor = actor;
	}
	
	public FavoriteActors(int favoriteActorId) {
		this.favoriteActorId = favoriteActorId;
	}
	
	public FavoriteActors(Users user, Actors actor) {
		this.user = user;
		this.actor = actor;
	}
	
	public int getFavoriteActorId() {
		return favoriteActorId;
	}
	
	public void setFavoriteActorId(int favoriteActorId) {
		this.favoriteActorId = favoriteActorId;
	}
	
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	public Actors getActor() {
		return actor;
	}
	
	public void setActor(Actors actor) {
		this.actor = actor;
	}

}
