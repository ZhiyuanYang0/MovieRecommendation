package movie.com.model;

public class Genres {
	protected int genreId;
	protected GenreType genreType;
	
	public enum GenreType {
		Action, Adult, Adventure, Animation,
		Comedy, Crime, Documentary, Drama, 
		Family, Fantasy, FilmNoir, Horror,  //cannot have dash in between
		Music, Musical, Mystery, Romance, 
		SciFi, Short, Thriller, War, Western	
	}

	public Genres(int genreId, GenreType genreType) {
		this.genreId = genreId;
		this.genreType = genreType;
	}

	public Genres(GenreType genreType) {
		this.genreType = genreType;
	}
	
	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public GenreType getGenreType() {
		return genreType;
	}

	public void setGenreType(GenreType genreType) {
		this.genreType = genreType;
	}
	
	

}
