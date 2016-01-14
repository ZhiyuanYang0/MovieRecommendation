package movie.com.model;

import java.util.Date;

public class Directors {
	protected int directorId;
	protected String firstName;
	protected String lastName;
	protected Date doB;
	protected String profile;
	protected String gender;
	
	public Directors(int directorId, String firstName, String lastName, Date doB, String profile, String gender) {
		this.directorId = directorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.doB = doB;
		this.profile = profile;
		this.gender = gender;
	}
	
	public Directors(int directorId) {
		this.directorId = directorId;
	}
	
	public Directors(String firstName, String lastName, Date doB, String profile, String gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.doB = doB;
		this.profile = profile;
		this.gender = gender;
	}
	
	public int getDirectorId() {
		return this.directorId;
	}
	
	public void setDirectorId(int directorId) {
		this.directorId = directorId;
	}
		
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getDoB() {
		return this.doB;
	}
	
	public void setDoB(Date doB) {
		this.doB = doB;
	}
	
	public String getProfile() {
		return this.profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
}
