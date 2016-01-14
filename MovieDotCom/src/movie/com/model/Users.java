
package movie.com.model;

import java.util.Date;

public class Users {
    protected int userId;
    protected String userName;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Date dob;
    protected String profile;
    protected String gender;

    public Users(int userId, String userName, String password, String email, String firstName,
            String lastName,
            Date dob, String profile, String gender) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.profile = profile;
        this.gender = gender;
    }

    public Users(String userName, String password, String email, String firstName, String lastName,
            Date dob, String profile, String gender) {
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.profile = profile;
        this.gender = gender;
    }

    public Users(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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
