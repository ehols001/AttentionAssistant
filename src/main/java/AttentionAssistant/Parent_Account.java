/**
 * Class that contains information on User_Account created by 
 * the user through Account Creation
 */
package AttentionAssistant;

public class Parent_Account{

	// Primary Key for Parent Account
	private int parentID;
	// username for Parent Account
	private String username;
	// password for Parent Account
	private String password;
	// Security key for question user selected
	private int SQ_Key;
	// users answer to specific security question
	private String SQ_Answer;
	// Security key for question user selected
	private int SQ_Key2;
	// users answer to specific security question
	private String SQ_Answer2;
		
	//Instantiating empty Parent_Account Object
	public Parent_Account() {
		this.parentID= 0;
		this.username= "";
		this.password= "";
		this.SQ_Key = 0;
		this.SQ_Answer = "";
		this.SQ_Key2 = 0;
		this.SQ_Answer2 = "";
	}
	
	/**
	 * Create a class Parent_Account with a specified
	 * parentID, username, password
	 * @param int, String, String
	 */
	public Parent_Account(int parentID, String username, String password,int SQ_Key,String SQ_Answer,int SQ_Key2 ,String SQ_Answer2) {
		this.parentID= parentID;
		this.username= username;
		this.password= password;
		this.SQ_Key = SQ_Key;
		this.SQ_Answer = SQ_Answer;
		this.SQ_Key2 = SQ_Key2;
		this.SQ_Answer2 = SQ_Answer2;
	}
	
	//Copy constructor for Parent_Account
	public Parent_Account(Parent_Account parent) {
		this.parentID= parent.parentID;
		this.username= parent.username;
		this.password= parent.password;
		this.SQ_Key= parent.SQ_Key;
		this.SQ_Answer = parent.SQ_Answer;
		this.SQ_Key2= parent.SQ_Key2;
		this.SQ_Answer2 = parent.SQ_Answer2;
	}
	
	/**
	 * Start of Encapsulation
	 * 
	 * Get Parent
	 * @return intID
	 */
	public int getParentID() {
		return this.parentID;
	}
	
	/**
	 * set ParentID
	 * @param int
	 */
	public void setParentID(int parentID) {
		this.parentID = parentID;
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
	 * Get security question key
	 * @return int
	 */
	public int getSQ_Key2() {
		return this.SQ_Key2;
	}
	
	/**
	 * set security question key
	 * @param int
	 */
	public void setSQ_Key2(int SQ_Key2) {
		this.SQ_Key2 = SQ_Key2;
	}
	
	/**
	 * Get Security question answer
	 * @return String
	 */
	public String getSQ_Answer2() {
		return this.SQ_Answer2;
	}
	
	/**
	 * set security question answer
	 * @param String
	 */
	public void setSQ_Answer2(String SQ_Answer2) {
		this.SQ_Answer2 = SQ_Answer2;
	}
	 /** 
	   * Display Parent_Account
	   * @return String
	   */
	@Override
	public String toString() {
	 	String ParentString= new String();
	 	ParentString = "Parent ID= " + this.parentID +
	 			" Username= " + this.username +
	 			" Password= " + this.password + 
	 			" SQ_Key= " + this.SQ_Key +
	 			" SQ_Answer= " + this.SQ_Answer +
	 			" SQ_Key2= " + this.SQ_Key2 +
	 			" SQ_Answer2= " + this.SQ_Answer2;
	 	return ParentString;	 	
	 }

	
	
	
	
	
}