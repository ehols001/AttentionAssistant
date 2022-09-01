/**
 * Class that contains information on User_Account created by 
 * the user through Account Creation
 */
package AttentionAssistant;

public class User_Account{

	// Primary Key for User Account
	private int userID;
	// username for User Account
	private String username;
	// password for User Account
	private String password;
	// name for User Account
	private String name;
	// Security key for question user selected
	private int SQ_Key;
	// users answer to specific security question
	private String SQ_Answer;
	// Security key 2 for question user selected
	private int SQ_Key2;
	// users answer to specific security question 2
	private String SQ_Answer2;
	
	//Instantiating empty User_Account Object
	public User_Account() {
		this.userID= 0;
		this.username= "";
		this.password= "";
		this.name= "";
		this.SQ_Key = 0;
		this.SQ_Answer = "";
		this.SQ_Key2 = 0;
		this.SQ_Answer2 = "";
	}
	
	/**
	 * Create a class User_Account with a specified
	 * userID, username, password
	 * @param int, String, String
	 */
	public User_Account(int userID, String username, String password,String name, int SQ_Key, String SQ_Answer,int SQ_Key2, String SQ_Answer2) {
		this.userID= userID;
		this.username= username;
		this.password= password;
		this.name= name;
		this.SQ_Key = SQ_Key;
		this.SQ_Answer = SQ_Answer;
		this.SQ_Key2 = SQ_Key2;
		this.SQ_Answer2 = SQ_Answer2;
	}
	
	//Copy constructor for User_Account
	public User_Account(User_Account user) {
		this.userID= user.userID;
		this.username= user.username;
		this.password= user.password;
		this.name= user.name;
		this.SQ_Key= user.SQ_Key;
		this.SQ_Answer = user.SQ_Answer;
		this.SQ_Key2= user.SQ_Key2;
		this.SQ_Answer2 = user.SQ_Answer2;
	}
	
	/**
	 * Start of Encapsulation
	 * 
	 * Get UserID
	 * @return int
	 */
	public int getUserID() {
		return this.userID;
	}
	
	/**
	 * set UserID
	 * @param int
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * Get username
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * set username
	 * @param String
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get password
	 * @return String
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * set password
	 * @param String
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Get name
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * set name
	 * @param String
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get security question key
	 * @return int
	 */
	public int getSQ_Key() {
		return this.SQ_Key;
	}
	
	/**
	 * set security question key
	 * @param int
	 */
	public void setSQ_Key(int SQ_Key) {
		this.SQ_Key = SQ_Key;
	}
	
	/**
	 * Get Security question answer
	 * @return String
	 */
	public String getSQ_Answer() {
		return this.SQ_Answer;
	}
	
	/**
	 * set security question answer
	 * @param String
	 */
	public void setSQ_Answer(String SQ_Answer) {
		this.SQ_Answer = SQ_Answer;
	}
	
	
	/**
	 * Get security question key 2
	 * @return int
	 */
	public int getSQ_Key2() {
		return this.SQ_Key2;
	}
	
	/**
	 * set security question key 2
	 * @param int
	 */
	public void setSQ_Key2(int SQ_Key2) {
		this.SQ_Key2 = SQ_Key2;
	}
	
	/**
	 * Get Security question answer 2
	 * @return String
	 */
	public String getSQ_Answer2() {
		return this.SQ_Answer2;
	}
	
	/**
	 * set security question answer 2
	 * @param String
	 */
	public void setSQ_Answer2(String SQ_Answer2) {
		this.SQ_Answer2 = SQ_Answer2;
	}
	
	 /** 
	   * Display User_Account
	   * @return String
	   */
	@Override
	public String toString() {
	 	String UserString= new String();
	 	UserString = "User ID= " + this.userID +
	 			" Username= " + this.username +
	 			" Password= " + this.password +
	 			" Name= " + this.name + 
	 			" SQ_Key= " + this.SQ_Key +
	 			" SQ_Answer= " + this.SQ_Answer +
	 			" SQ_Key2= " + this.SQ_Key2 +
	 			" SQ_Answer2= " + this.SQ_Answer2;
	 	return UserString;	 	
	 }

	
	
	
	
	
}