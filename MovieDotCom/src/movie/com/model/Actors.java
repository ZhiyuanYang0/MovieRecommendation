package movie.com.model;

import java.sql.Date;

public class Actors {
	protected int actorId;
	protected int height;
	protected int weight;
	protected String firstNmae;
	protected String lastName;
	protected Date doB;
	protected String profile;
	protected String gender;

	public Actors(int actorId, int height, int weight, String firstNmae, String lastName, Date doB, String profile,
			String gender) {
		super();
		this.actorId = actorId;
		this.height = height;
		this.weight = weight;
		this.firstNmae = firstNmae;
		this.lastName = lastName;
		this.doB = doB;
		this.profile = profile;
		this.gender = gender;
	}

	public Actors(int height, int weight, String firstNmae, String lastName, Date doB, String profile, String gender) {
		super();
		this.height = height;
		this.weight = weight;
		this.firstNmae = firstNmae;
		this.lastName = lastName;
		this.doB = doB;
		this.profile = profile;
		this.gender = gender;
	}




	public int getActorId() {
		return actorId;
	}


	public void setActorId(int actorId) {
		this.actorId = actorId;
	}


	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getFirstName() {
		return firstNmae;
	}

	public void setFirstName(String firstNmae) {
		this.firstNmae = firstNmae;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDoB() {
		return doB;
	}

	public void setDoB(Date doB) {
		this.doB = doB;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
