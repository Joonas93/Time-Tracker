package timeTracker;

public class User {
	/*This class holds user info with accessors-methods.
	 *
	 *
	 */
private int userID;
private String firstName, lastName;


	public User (int userID, String firstName, String lastName) {
		
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
	
	}
	
	public int getUserID() {
		return userID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
}

