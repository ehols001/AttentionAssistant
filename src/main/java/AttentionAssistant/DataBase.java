	package AttentionAssistant;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

public class DataBase {
	
	private SQLiteDataSource ds;
	SQLiteConfig sqlCon;

	
	
	/**
	 * Instantiating empty Task object
	 * @author jmitchel2
	 */
	public DataBase() {
		this.ds= new SQLiteDataSource();
		this.sqlCon = new SQLiteConfig();
	}

	
	/**
	  * Initial Database Setup
	  * 
      * @author jmitchel2
      */
    public void DatabaseSetUp() {
        String url = "jdbc:sqlite:bin/Attention_Assistant.db" ;
        ds.setUrl(url);
        try (Connection conn1 = ds.getConnection()) {
            if (conn1 != null) {
                DatabaseMetaData meta = conn1.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
          }
        
        /**
         * Set up for Table Task
         */
    	String queryTask = "CREATE TABLE IF NOT EXISTS task ( " +
   			 "taskID INTEGER PRIMARY KEY, " +
   			 "fk_userID INTEGER, " +
   			 "description TEXT, " +
   			 "observable BOOLEAN, " +
   			 "status TEXT, " + 
   			 "name TEXT, " +
   			 "dueDate DATETIME, " +
   			 "completedDate DATETIME, " +
   			 "addedDate DATETIME, " +
   			 "priority BOOLEAN, " +
    		 "CONSTRAINT fk_UserID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE)";
    	
    	/**
    	 * Set up for Table HappyThoughtButton
    	 */
    	String queryMedia = "CREATE TABLE IF NOT EXISTS media ( " +
   			 "media_ID INTEGER PRIMARY KEY, " +
   			 "fk_userID INTEGER, " +
  			 "media_ID_Tag TEXT, " +
   			 "flagged BOOLEAN, " +
  			 "rating INTEGER, " +
//   			 "dT_Executed DATE, " + -old code -jmitchel
			 "CONSTRAINT fk_userID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE)";

    	/**
    	 * Set up for Table Observer
    	 */
    	String queryObserver = "CREATE TABLE IF NOT EXISTS observer ( " +
   			 "observerID INTEGER PRIMARY KEY, " +
   			 "fk_taskID INTEGER, " +
   			 "observerScore INTEGER, " +
   			 "defaultEyeScore INTEGER, " +
   			 "threshold INTEGER, " +
   			 "dT_Gathered DATETIME, " +
   			 "CONSTRAINT fk_taskID FOREIGN KEY (\"fk_taskID\") REFERENCES \"task\"(\"taskID\") ON DELETE CASCADE)";

        /**
         * Set up for Table Settings
         */
    	String querySettings = "CREATE TABLE IF NOT EXISTS settings ( " +
   			 "settingsID INTEGER PRIMARY KEY, " +
   			 "fk_userID INTEGER, " +
   			 "iconCircles INTEGER, " +
   			 "icons INTEGER, " +
   			 "opacityCircles INTEGER, " + 
   			 "opacityIcons INTEGER, " +
   			 "isCollapsed BOOLEAN, " +
   			 "xCoord INTEGER, " +
   			 "yCoord INTEGER, " +
   			 "isVertical BOOLEAN, " +
   			 "iconSize INTEGER, " +
   			 "timerIsVisible BOOLEAN, " +
   			 "pmIsVisible BOOLEAN, " +
   			 "ftsIsVisible BOOLEAN, " +
   			 "htbIsVisible BOOLEAN, " +
   			 "ntbIsVisible BOOLEAN, " +
   			 "progReportIsVisible BOOLEAN, " +
   			 "avatarIsActive BOOLEAN, " +
   			 "textIsActive BOOLEAN, " +
   			 "audioIsActive BOOLEAN, " +
   			 "avatarFilePath TEXT, " +
//   			 "audioFilePath TEXT, " + -old code -jmitchel
   			 "alwaysOnScreen BOOLEAN, " +
   			 "avatarSize INTEGER, " +
   			 "pomodoroIsActive BOOLEAN, " +
   			 "workPeriod INTEGER, " +
   			 "breakPeriod INTEGER, " +
   			 "timeShowing BOOLEAN, " +
   			 "ftsIsActive BOOLEAN, " +
   			 "ntbIsActive BOOLEAN, " +
   			 "isAutoLinked BOOLEAN, " +
   			 "htbIsActive BOOLEAN, " +
   			 "timerVisibilityIsLocked BOOLEAN, " +
   			 "pmVisibilityIsLocked BOOLEAN, " +
   			 "htbVisibilityIsLocked BOOLEAN, " +
   			 "ftsVisibilityIsLocked BOOLEAN, " +
   			 "ntbVisibilityIsLocked BOOLEAN, " +
   			 "progReportVisibilityIsLocked BOOLEAN, " +
   			 "textToSpeech BOOLEAN, " +
   			 "pomodoroIsLocked BOOLEAN, " +
   			 "workPeriodIsLocked BOOLEAN, " +
   			 "breakPeriodIsLocked BOOLEAN, " +
   			 "ftsIsLocked BOOLEAN, " +
   			 "ntbIsLocked BOOLEAN, " +
   			 "htbIsLocked BOOLEAN, " +
			 "CONSTRAINT fk_UserID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE)";
    	
    	/**
    	 * Set up for Table user
    	 */
    	String queryUser = "CREATE TABLE IF NOT EXISTS user ( " +
      		 "userID INTEGER PRIMARY KEY, " +
   			 "username TEXT, " +
   			 "password TEXT, " +
   			 "nickname TEXT, " +
   			 "SQ_Key INTEGER, " + 
   			 "SQ_Answer TEXT, " +
   			 "SQ_Key2 INTEGER, " + 
  			 "SQ_Answer2 TEXT)";

    	/**
    	 * Set up for Table parent
    	 */
    	String queryParent = "CREATE TABLE IF NOT EXISTS parent ( " +
      		 "parentID INTEGER PRIMARY KEY, " +
   			 "username TEXT, " +
   			 "password TEXT, " +
   			 "SQ_Key INTEGER, " + 
   			 "SQ_Answer TEXT, " +
   			 "SQ_Key2 INTEGER, " + 
  			 "SQ_Answer2 TEXT)";
        
        /**
         * Set up for Table LinkedAccounts
         */
    	String queryLinkedAccounts = "CREATE TABLE IF NOT EXISTS linked_Accounts ( " +
   			 "linkedAccountID INTEGER PRIMARY KEY, " +
   			 "fk_parentID INTEGER, " +
   			 "fk_userID INTEGER, " +
			 "CONSTRAINT fk_ParentID FOREIGN KEY (\"fk_parentID\") REFERENCES \"parent\"(\"parentID\") ON DELETE CASCADE, " +
   			 "CONSTRAINT fk_UserID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE)";
    	
        /**
         * Set up for Table Notification_System
         */
    	String queryNotification_System = "CREATE TABLE IF NOT EXISTS notification ( " +
      			 "notificationID INTEGER PRIMARY KEY, " +
      			 "fk_userID INTEGER, " +
      			 "type TEXT, " +
      			 "ignored BOOLEAN, " +
      			 "dT_Notification DATETIME, " +
      			 "CONSTRAINT fk_userID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE)";

    	/**
         * Set up for Table Event_Type
         */
    	String queryEventType= "CREATE TABLE IF NOT EXISTS event_Type ( " +
    			"event_TypeID INTEGER PRIMARY KEY, " +
    			"name TEXT) ";
    	
        /**
         * Set up for Table Event
         */
    	String queryEvent= "CREATE TABLE IF NOT EXISTS event ( " +
    			"eventID INTEGER PRIMARY KEY, " +
    			"fk_userID INTEGER, " +
    			"fk_event_TypeID INTEGER, " +
    			"dT_Executed DATETIME, "+
    			"CONSTRAINT fk_userID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE, " +
    			"CONSTRAINT fk_event_TypeID FOREIGN KEY (\"fk_event_TypeID\") REFERENCES \"event_Type\"(\"Event_TypeID\") ON DELETE CASCADE)";
    			
        /**
         * Set up for Table FTS_Color
         */
    	String queryFTS_Color= "CREATE TABLE IF NOT EXISTS FTS_Color ( " +
    			"ftsColorID INTEGER PRIMARY KEY, " +
    			"fk_userID INTEGER, " +
    			"color INTEGER, " +
    			"CONSTRAINT fk_userID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE) "; 

    	/**
         * Set up for Table BWList
         */
    	String queryWBList= "CREATE TABLE IF NOT EXISTS wbList ( " +
    			"bwListID INTEGER PRIMARY KEY, " +
    			"fk_userID INTEGER, " +
    			"processName TEXT, " +
     			 "white_Or_Black BOOLEAN, " +    			
    			"CONSTRAINT fk_userID FOREIGN KEY (\"fk_userID\") REFERENCES \"user\"(\"userID\") ON DELETE CASCADE) "; 
    	
    	try (Connection conn = this.ds.getConnection();
   			Statement stmt = conn.createStatement(); ){
   		int rv1 = stmt.executeUpdate(queryUser);
   		System.out.println( "CreateUserTable() returned " + rv1 );
   		int rv2 = stmt.executeUpdate(queryParent);
   		System.out.println( "CreateParentTable() returned " + rv2 );
		int rv3 = stmt.executeUpdate(queryTask);
   		System.out.println( "CreateTaskTable() returned " + rv3 );
   		int rv4 = stmt.executeUpdate(queryMedia);
   		System.out.println( "CreateMediaTable() returned " + rv4 );
   		int rv5 = stmt.executeUpdate(queryObserver);
   		System.out.println( "CreateObserverTable() returned " + rv5 );
   		int rv6 = stmt.executeUpdate(querySettings);
   		System.out.println( "CreateSettingsTable() returned " + rv6 );
   		int rv7 = stmt.executeUpdate(queryLinkedAccounts);
   		System.out.println( "CreateLinkedAccountsTable() returned " + rv7 );
   		int rv8 = stmt.executeUpdate(queryNotification_System);
   		System.out.println( "CreateNotificationTable() returned " + rv8 );
   		int rv9 = stmt.executeUpdate(queryEventType);
   		System.out.println( "CreateEventTypeTable() returned " + rv9 );
   		int rv10 = stmt.executeUpdate(queryEvent);
   		System.out.println( "CreateEventTable() returned " + rv10 );
   		int rv11 = stmt.executeUpdate(queryFTS_Color);
   		System.out.println( "CreateFTS_ColorTable() returned " + rv11 );
   		int rv12 = stmt.executeUpdate(queryWBList);
   		System.out.println( "CreateWB_ListTable() returned " + rv12 );
       } catch ( SQLException e ) {
           e.printStackTrace();
       }



    }
    /**
    ******* START OF USER_ACCOUNT CRUD *******
    * @author jmitchel2
    */
    /**
     * Add a new User to the database.
     * @param User_Account
     */
    public void AddUser_Account(User_Account user) {
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
    	String query1 = "INSERT INTO user " +
    			"( username, password, nickname ) Values ( '" +
    			user.getUsername().replaceAll("'", "''") + "', '" +
    			user.getPassword().replaceAll("'", "''") + "', '" +
    			user.getName().replaceAll("'", "''") + "')";
    	try ( Connection conn = ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
    		    int rv = stmt.executeUpdate( query1 );
    		    System.out.println( "AddUser_Account() returned " + rv );
    		} catch ( SQLException e ) {
    		    e.printStackTrace();
    		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
    }

    /**
     * Update a User_Account within the Database
     * @param User_Account
     */
	public void UpdateUser_Account(User_Account user) {
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
		String query1 = "UPDATE user " +
				"SET username = '" + user.getUsername().replaceAll("'", "''") +
        		"', password = '" + user.getPassword().replaceAll("'", "''") +
        		"', nickname = '" + user.getName().replaceAll("'", "''") +
        		"', SQ_Key = '" + user.getSQ_Key() +
        		"', SQ_Answer = '" + user.getSQ_Answer().replaceAll("'", "''") +
        		"', SQ_Key2 = '" + user.getSQ_Key2() +
        		"', SQ_Answer2 = '" + user.getSQ_Answer2().replaceAll("'", "''") +
        		"' WHERE userID = '" + user.getUserID() + "'";
        try ( Connection conn = ds.getConnection();
        	Statement stmt = conn.createStatement(); ) {
        	int rv = stmt.executeUpdate( query1 );
        	System.out.println( "UpdateUser_Account() returned " + rv );
        	} catch ( SQLException e ) {
        	e.printStackTrace();
        	}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);

	}

        /**
         * Delete a User_Account within the Database
         * @param int
         */
	public void DeleteUser_Account(int userID) {
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
		String query1 = "DELETE FROM user WHERE userID = '" + userID + "'";
		try ( Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement(); ) {
			int rv = stmt.executeUpdate( query1 );
			System.out.println( "DeleteUser_Account() returned " + rv );
        	} catch ( SQLException e ) {
            e.printStackTrace();
        }
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
    }
            
       	/**
       	 * Select a user within the database using the userID
         * @param int
         * @return User_Account
         */
	public User_Account SelectUser_Account(int userID) {
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
		User_Account user1 = new User_Account();
        String query1 = "SELECT * FROM user WHERE userID = '" + userID + "'";
        try ( Connection conn = ds.getConnection();
        	Statement stmt = conn.createStatement(); ) {
        	ResultSet rs = stmt.executeQuery( query1 );
            user1.setUserID(rs.getInt("userID"));
            user1.setUsername(rs.getString("username"));
            user1.setPassword(rs.getString("password"));
            user1.setName(rs.getString("nickname"));
            user1.setSQ_Key(rs.getInt("SQ_Key"));
            user1.setSQ_Answer(rs.getString("SQ_Answer"));
            user1.setSQ_Key2(rs.getInt("SQ_Key2"));
            user1.setSQ_Answer2(rs.getString("SQ_Answer2"));
            System.out.println( "SelectUser_Account() returned " + rs );
        	} catch ( SQLException e ) {
            e.printStackTrace();
        }
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
          return user1;
        }
	
	
    /**
     * Search for a User_Account in the database.
     * @param String, String
     * @return User_Account
     */
    public User_Account SearchUser_Account(String username, String password) {
    	User_Account tempuser= new User_Account();
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
    	String query1 = "SELECT * FROM user WHERE username = '"+ username.replaceAll("'", "''") + "' AND password = '" + password.replaceAll("'", "''") + "'";
    	try ( Connection conn = ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
    		    ResultSet rs = stmt.executeQuery( query1 );
    		    if (rs.isClosed()) {
    		    	
    		    	return tempuser;
    		    }
    		    tempuser.setUserID(rs.getInt("userID"));
    		    tempuser.setUsername(rs.getString("username"));
    		    tempuser.setPassword(rs.getString("password"));
    		    tempuser.setName(rs.getString("nickname"));
    		    tempuser.setSQ_Key(rs.getInt("SQ_Key"));
    		    tempuser.setSQ_Answer(rs.getString("SQ_Answer"));
    		    tempuser.setSQ_Key2(rs.getInt("SQ_Key2"));
    		    tempuser.setSQ_Answer2(rs.getString("SQ_Answer2"));
    		    System.out.println( "SearchUser_Account() returned " + rs );
    		} catch ( SQLException e ) {
    		    e.printStackTrace();
    		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);        
        return tempuser;
    }
    
	
    /**
     * Search for a Username in the database.
     * @param String
     * @return User_Account
     */
    public User_Account UsernameUser_Account(String username) {
    	User_Account tempuser= new User_Account();
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
    	String query1 = "SELECT * FROM user WHERE username = '"+ username.replaceAll("'", "''") + "'";
    	try ( Connection conn = ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
    		    ResultSet rs = stmt.executeQuery( query1 );
    		    if (rs.isClosed()) {
    		    	return tempuser;
    		    }
    		    tempuser.setUserID(rs.getInt("userID"));
    		    tempuser.setUsername(rs.getString("username"));
    		    tempuser.setPassword(rs.getString("password"));
    		    tempuser.setName(rs.getString("nickname"));
    		    tempuser.setSQ_Key(rs.getInt("SQ_Key"));
    		    tempuser.setSQ_Answer(rs.getString("SQ_Answer"));
    		    tempuser.setSQ_Key2(rs.getInt("SQ_Key2"));
    		    tempuser.setSQ_Answer2(rs.getString("SQ_Answer2"));
    		    System.out.println( "SearchUser_Account() returned " + rs );
    		} catch ( SQLException e ) {
    		    e.printStackTrace();
    		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);        
        return tempuser;
    }

    /**
     * Mainly used for JUNIT testing, deletes the user table at the beginning of testing to remove all test data.
     * 
     */
    public void DeleteAllUser_Accounts(){
    	String query1 = "DROP TABLE IF EXISTS 'user'";
    	try ( Connection conn = this.ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
		    int rv = stmt.executeUpdate( query1 );
		    System.out.println( "DeleteAllUser_Accounts() returned " + rv );
    	} catch ( SQLException e ) {
			e.printStackTrace();
    	}
    }
       /**
        ******* END OF USER_ACCOUNT CRUD *******
        */
            
       /**
        ******* START OF PARENT_ACCOUNT CRUD *******
        * @author jmitchel2
        */
            
       /**
        * Add a new Parent_Account to the database.
        * @param Parent_Account
        */
	public void AddParent_Account(Parent_Account parent) {
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
		String query1 = "INSERT INTO parent " +
             "( username, password ) Values ( '" +
             parent.getUsername().replaceAll("'", "''") + "', '" +
             parent.getPassword().replaceAll("'", "''") + "')";
        try ( Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement(); ) {
             int rv = stmt.executeUpdate( query1 );
             System.out.println( "AddParent_Account() returned " + rv );
        	} catch ( SQLException e ) {
             e.printStackTrace();
             }
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
        }

        /**
         * Update a Parent_Account within the Database
         * @param User_Account
         */
	public void UpdateParent_Account(Parent_Account parent) {
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
		String query1 = "UPDATE parent " +
				"SET username = '" + parent.getUsername().replaceAll("'", "''") +
                "', password = '" + parent.getPassword().replaceAll("'", "''") +
                "', SQ_Key = '" + parent.getSQ_Key() +
        		"', SQ_Answer = '" + parent.getSQ_Answer().replaceAll("'", "''") +
        		"', SQ_Key2 = '" + parent.getSQ_Key2() +
          		"', SQ_Answer2 = '" + parent.getSQ_Answer2().replaceAll("'", "''") +
                "' WHERE parentID = '" + parent.getParentID() + "'";
        try ( Connection conn = ds.getConnection();
        		Statement stmt = conn.createStatement(); ) {
                int rv = stmt.executeUpdate( query1 );
                System.out.println( "UpdateParent_Account() returned " + rv );
        	} catch ( SQLException e ) {
                e.printStackTrace();
        	}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);

        }

		/**
         * Delete a Parent_Account within the Database
         * @param int
         */
	public void DeleteParent_Account(int parentID) {
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
       	String query1 = "DELETE FROM parent WHERE parentID = '" + parentID + "'";
        try ( Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement(); ) {
             int rv = stmt.executeUpdate( query1 );
             System.out.println( "DeleteParent_Account() returned " + rv );
             } catch ( SQLException e ) {
             e.printStackTrace();
             }
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
        }
                     
        /**
         * Select a Parent_Account within the database using the parentID
         * @param int
         * @return Parent_Account
         */
	public Parent_Account SelectParent_Account(int parentID) {
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
		Parent_Account parent1 = new Parent_Account();
        String query1 = "SELECT * FROM parent WHERE parentID = '" + parentID + "'";
        try ( Connection conn = ds.getConnection();
        		Statement stmt = conn.createStatement(); ) {
                ResultSet rs = stmt.executeQuery( query1 );
                parent1.setParentID(rs.getInt("parentID"));
                parent1.setUsername(rs.getString("username"));
                parent1.setPassword(rs.getString("password"));
                parent1.setSQ_Key(rs.getInt("SQ_Key"));
                parent1.setSQ_Answer(rs.getString("SQ_Answer"));
                parent1.setSQ_Key2(rs.getInt("SQ_Key2"));
                parent1.setSQ_Answer2(rs.getString("SQ_Answer2"));
                System.out.println( "SelectParent_Account() returned " + rs );
                } catch ( SQLException e ) {
                     e.printStackTrace();
                }
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
         return parent1;
         }
   
	/**
     * Search for a Parent_Account in the database.
     * @param String, String
     * @return User_Account
     */
    public Parent_Account SearchParent_Account(String username, String password) {
    	Parent_Account tempparent= new Parent_Account();
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
    	String query1 = "SELECT * FROM parent WHERE username = '"+ username.replaceAll("'", "''") + "' AND password = '" + password.replaceAll("'", "''") + "'";
    	try ( Connection conn = ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
    		    ResultSet rs = stmt.executeQuery( query1 );
    		    if (rs.isClosed()) {
    		    	return tempparent;
    		    }
    		    tempparent.setParentID(rs.getInt("parentID"));
    		    tempparent.setUsername(rs.getString("username"));
    		    tempparent.setPassword(rs.getString("password"));
    		    tempparent.setSQ_Key(rs.getInt("SQ_Key"));
    		    tempparent.setSQ_Answer(rs.getString("SQ_Answer"));
    		    tempparent.setSQ_Key2(rs.getInt("SQ_Key2"));
    		    tempparent.setSQ_Answer2(rs.getString("SQ_Answer2"));
    		    System.out.println( "SearchParent_Account() returned " + rs );
    		} catch ( SQLException e ) {
    		    e.printStackTrace();
    		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
        return tempparent;
    }

    /**
     * Search for a parent username in the database.
     * @param String, String
     * @return User_Account
     */
    public Parent_Account UsernameParent_Account(String username) {
    	Parent_Account tempparent= new Parent_Account();
    	sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
    	String query1 = "SELECT * FROM parent WHERE username = '"+ username.replaceAll("'", "''") + "'";
    	try ( Connection conn = ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
    		    ResultSet rs = stmt.executeQuery( query1 );
    		    if (rs.isClosed()) {
    		    	return tempparent;
    		    }
    		    tempparent.setParentID(rs.getInt("parentID"));
    		    tempparent.setUsername(rs.getString("username"));
    		    tempparent.setPassword(rs.getString("password"));
    		    tempparent.setSQ_Key(rs.getInt("SQ_Key"));
    		    tempparent.setSQ_Answer(rs.getString("SQ_Answer"));
    		    tempparent.setSQ_Key2(rs.getInt("SQ_Key2"));
    		    tempparent.setSQ_Answer2(rs.getString("SQ_Answer2"));
    		    System.out.println( "SearchParent_Account() returned " + rs );
    		} catch ( SQLException e ) {
    		    e.printStackTrace();
    		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
        return tempparent;
    }
	/**
     * Mainly used for JUNIT testing, deletes the parent table at the beginning of testing to remove all test data.
     * 
     */
    public void DeleteAllParent_Accounts(){
    	String query1 = "DROP TABLE IF EXISTS 'parent'";
    	try ( Connection conn = this.ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
		    int rv = stmt.executeUpdate( query1 );
		    System.out.println( "DeleteAllParent_Accounts() returned " + rv );
    	} catch ( SQLException e ) {
			e.printStackTrace();
    	}
    }


   /**
   ******* END OF PARENT_ACCOUNT CRUD *******
   */
        
    /**
    ******* START OF TASK CRUD *******
    */
    /**
     * OLD CODE 3-24-2022-Jmitchel
     * Add a new task to the database.
     * @param task
     */
//    public void AddTask(Task task) {
//		sqlCon.enforceForeignKeys(true);
//        ds.setConfig(sqlCon);
//    	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(task.getDueDate());
//    	String query1 = "INSERT INTO task " +
//    			"( description, observable, status, name, dueDate, priority ) Values ( '" +
//    			task.getDescription().replaceAll("'", "''") + "', '" +
//    			task.getObservable() + "', '" +
//    			task.getStatus().toString().replaceAll("'", "''") + "', '" +
//    			task.getTaskName().replaceAll("'", "''") + "', '" +
//    			DateTime + "', '" +
//    			task.getPriority() +"')";
//    	try ( Connection conn = ds.getConnection();
//    		    Statement stmt = conn.createStatement(); ) {
//    		    int rv = stmt.executeUpdate( query1 );
//    		    System.out.println( "AddTask() returned " + rv );
//    		} catch ( SQLException e ) {
//    		    //gets called when a user is passed in that isn't in the user table.
//    			e.printStackTrace();
//    		}
//		sqlCon.enforceForeignKeys(false);
//        ds.setConfig(sqlCon);
//    }

    /**
     * Add a new task to the database.
     * @param task
     */
    public void AddTask(Task task, int userID) {
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
        Date dateAdded= new Date(System.currentTimeMillis());
    	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(task.getDueDate());
    	String DateTimeAdded = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dateAdded);
    	String query1 = "INSERT INTO task " +
    			"(fk_userID, description, observable, status, name, dueDate, addedDate, priority ) Values ( '" +
    			userID + "', '" +
    			task.getDescription().replaceAll("'", "''") + "', '" +
    			task.getObservable() + "', '" +
    			task.getStatus().toString().replaceAll("'", "''") + "', '" +
    			task.getTaskName().replaceAll("'", "''") + "', '" +
    			DateTime.replaceAll("'", "''") + "', '" +
    			DateTimeAdded.replaceAll("'", "''") + "', '" +
    			task.getPriority() +"')";
    	try ( Connection conn = ds.getConnection();
    		    Statement stmt = conn.createStatement(); ) {
    		    int rv = stmt.executeUpdate( query1 );
    		    System.out.println( "AddTask() returned " + rv );
    		} catch ( SQLException e ) {
    		    //gets called when a user is passed in that isn't in the user table.
    			e.printStackTrace();
    		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
    }
    
    /**
     * Update a task within the Database
     * @param task
     */
        public void UpdateTask(Task task) {
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
        	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(task.getDueDate());
        	String query1 = "UPDATE task " +
        			"SET description = '" + task.getDescription().replaceAll("'", "''") + 
        			"', observable = '" + task.getObservable() + 
        			"', status = '" + task.getStatus().toString().replaceAll("'", "''") +
        			"', name = '" + task.getTaskName().replaceAll("'", "''") +
        			"', dueDate = '" + DateTime +
        			"', priority = '" + task.getPriority() +
        			"' WHERE taskID = '" + task.getTaskID() + "'";
            //if the task is completed
        	if (task.getStatus() == TaskStatus.CLOSED) {
                Task previousTask = this.SelectTask(task.getTaskID());
            //if the previousTask in the database was not completed
                if (previousTask.getStatus() != TaskStatus.CLOSED) {
                	Date dateCompleted= new Date(System.currentTimeMillis());
                	String DateTimeCompleted= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dateCompleted);
                	query1 = "UPDATE task " +
                			"SET description = '" + task.getDescription().replaceAll("'", "''") + 
                			"', observable = '" + task.getObservable() + 
                			"', status = '" + task.getStatus().toString().replaceAll("'", "''") +
                			"', name = '" + task.getTaskName().replaceAll("'", "''") +
                			"', dueDate = '" + DateTime +
                			"', completedDate = '" + DateTimeCompleted +
                			"', priority = '" + task.getPriority() +
                			"' WHERE taskID = '" + task.getTaskID() + "'";                	
                }
            }
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    int rv = stmt.executeUpdate( query1 );
        		    System.out.println( "UpdateTask() returned " + rv );
        		} catch ( SQLException e ) {
        		    e.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
        	
        }
        
        /**
         * Delete a task within the Database
         * @param int
         */
        public void DeleteTask(int taskid) {
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
        	String query1 = "DELETE FROM task WHERE taskID = '" + taskid + "'";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    int rv = stmt.executeUpdate( query1 );
        		    System.out.println( "DeleteTask() returned " + rv );
        		} catch ( SQLException e ) {
        		    e.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
        	
        }    	
        
        /**
         * Select a task within the database using the taskid
         * @param int
         * @return Task
         */
        public Task SelectTask(int taskid) {
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
        	Task task1 = new Task();
        	String query1 = "SELECT * FROM task WHERE taskID = '" + taskid + "'";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    ResultSet rs = stmt.executeQuery( query1 );
        		    task1.setTaskID(rs.getInt("taskID"));
        		    task1.setDescription(rs.getString("description"));
        		    task1.setObservable(Boolean.valueOf(rs.getString("observable")));
        		    task1.setStatus(TaskStatus.valueOf(rs.getString("status")));
        		    task1.setTaskName(rs.getString("name"));
        		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dueDate"));
        		    task1.setDueDate(date1);
        		    task1.setPriority(Boolean.valueOf(rs.getString("priority")));
        		    System.out.println( "SelectTask() returned " + rs );
        		} catch ( SQLException e ) {
        			e.printStackTrace();
        		}
        		  catch ( ParseException p ) {
        			p.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
        	return task1;
        }
        
        /**
         * OLD CODE 3-24-2022-jmitchel
         * Grab all tasks within the Database
         * 
         * This will eventually have a parameter int to grab by user id
         * @return ArrayList<Task>
         */
//        public ArrayList<Task> SelectAllTasks(){
//    		sqlCon.enforceForeignKeys(true);
//            ds.setConfig(sqlCon);
//        	ArrayList<Task> tasksOnList = new ArrayList<Task>();
//        	Task blankTask = new Task();
//        	String query1 = "SELECT * FROM task ORDER BY observable DESC, priority DESC, dueDate ASC";
//        	try ( Connection conn = ds.getConnection();
//        		    Statement stmt = conn.createStatement(); ) {
//        		    ResultSet rs = stmt.executeQuery( query1 );
//        		    while (rs.next()){
//        		    blankTask = new Task();
//        		    blankTask.setTaskID(rs.getInt("taskID"));
//        		    blankTask.setDescription(rs.getString("description"));
//        		    blankTask.setObservable(Boolean.valueOf(rs.getString("observable")));
//        		    blankTask.setStatus(TaskStatus.valueOf(rs.getString("status")));
//        		    blankTask.setTaskName(rs.getString("name"));
//        		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dueDate"));
//        		    blankTask.setDueDate(date1);
//        		    blankTask.setPriority(Boolean.valueOf(rs.getString("priority")));
//        		    tasksOnList.add(blankTask);
//        		    }
//        		    System.out.println( "SelectAllTasks() returned " + rs );
//        		} catch ( SQLException e ) {
//        			e.printStackTrace();
//        		}
//        		  catch ( ParseException p ) {
//        			p.printStackTrace();
//        		}
//    		sqlCon.enforceForeignKeys(false);
//            ds.setConfig(sqlCon);
//        	return tasksOnList;
//        }

        /**
         * Grab all tasks within the Database
         * 
         * This will eventually have a parameter int to grab by user id
         * @return ArrayList<Task>
         */
        public ArrayList<Task> SelectAllTasks(int userID){
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
        	ArrayList<Task> tasksOnList = new ArrayList<Task>();
        	ArrayList<Task> overDueTasks = new ArrayList<Task>();
        	Task blankTask = new Task();
        	String query1 = "SELECT * FROM task WHERE fk_userID = '"+ userID+ "' ORDER BY observable DESC, priority DESC, dueDate ASC";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    ResultSet rs = stmt.executeQuery( query1 );
        		    while (rs.next()){
        		    blankTask = new Task();
        		    blankTask.setTaskID(rs.getInt("taskID"));
        		    blankTask.setDescription(rs.getString("description"));
        		    blankTask.setObservable(Boolean.valueOf(rs.getString("observable")));
        		    blankTask.setStatus(TaskStatus.valueOf(rs.getString("status")));
        		    blankTask.setTaskName(rs.getString("name"));
        		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dueDate"));
        		    blankTask.setDueDate(date1);
        		    blankTask.setPriority(Boolean.valueOf(rs.getString("priority")));
        		    Date datenow = new Date(System.currentTimeMillis());
        		    //Sets the Tasks Status to OverDue
        		    if (new Date(blankTask.getDueDate().getTime() + 86400000).compareTo(datenow) < 0 && blankTask.getStatus() == TaskStatus.OPEN) {
        		    	blankTask.setStatus(TaskStatus.OVERDUE);
        		    	overDueTasks.add(blankTask);
        		    }
        		    tasksOnList.add(blankTask);
        		    }
        		    System.out.println( "SelectAllTasks() returned " + rs );
        		} catch ( SQLException e ) {
        			e.printStackTrace();
        		}
        		  catch ( ParseException p ) {
        			p.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
            for (int i=0; i < overDueTasks.size(); i++) {
            	this.UpdateTask(overDueTasks.get(i));
            }
            
        	return tasksOnList;
        }
        
        /**
         * Mainly used for JUNIT testing, deletes the task table at the beginning of testing to remove all test data.
         * 
         */
        public void DeleteAllTasks(){
        	String query1 = "DROP TABLE IF EXISTS 'task'";
        	try ( Connection conn = this.ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
    		    int rv = stmt.executeUpdate( query1 );
    		    System.out.println( "DeleteAllTasks() returned " + rv );
        	} catch ( SQLException e ) {
    			e.printStackTrace();
        }

        }
        
        /**
         * Returns the userID from a specific task
         * 
         * @param task
         * @return int
         */
        public int GetUserIDFromTask(Task task)
        {
        	int userID= 0;
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
        	String query1 = "SELECT * FROM task WHERE taskID = '" + task.getTaskID() + "'";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    ResultSet rs = stmt.executeQuery( query1 );
        		    userID= rs.getInt("fk_userID");
        		    System.out.println( "GetUserIDforTask() returned " + rs );
        		} catch ( SQLException e ) {
        			e.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
            return userID;
        }
        
        /**
         * Returns an array List of added tasks between 2 dates
         * 
         * @param Int, Date, Date
         * @return ArrayList<Task>
         */
        public ArrayList<Task> SelectAllAddedTasks(int userID, Date dT_From, Date dT_Till){
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
        	ArrayList<Task> tasksOnList = new ArrayList<Task>();
        	Task blankTask = new Task();
            String DateFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dT_From);
            String DateTill = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dT_Till);
        	String query1 = "SELECT * FROM task WHERE fk_userID = '"+ userID+ 
        			"' AND addedDate BETWEEN '" + DateFrom +
        			"' AND '" + DateTill + "' ORDER BY addedDate ASC";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    ResultSet rs = stmt.executeQuery( query1 );
        		    while (rs.next()){
        		    blankTask = new Task();
        		    blankTask.setTaskID(rs.getInt("taskID"));
        		    blankTask.setDescription(rs.getString("description"));
        		    blankTask.setObservable(Boolean.valueOf(rs.getString("observable")));
        		    blankTask.setStatus(TaskStatus.valueOf(rs.getString("status")));
        		    blankTask.setTaskName(rs.getString("name"));
        		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dueDate"));
        		    blankTask.setDueDate(date1);
        		    blankTask.setPriority(Boolean.valueOf(rs.getString("priority")));
        		    tasksOnList.add(blankTask);
        		    }
        		    System.out.println( "SelectAllAddedTasks() returned " + rs );
        		} catch ( SQLException e ) {
        			e.printStackTrace();
        		}
        		  catch ( ParseException p ) {
        			p.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);            
        	return tasksOnList;

        }
        
        /**
         * Returns an array List of Completed tasks between 2 dates
         * 
         * @param Int, Date, Date
         * @return ArrayList<Task>
         */
        public ArrayList<Task> SelectAllCompletedTasks(int userID, Date dT_From, Date dT_Till){
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
        	ArrayList<Task> tasksOnList = new ArrayList<Task>();
        	Task blankTask = new Task();
            String DateFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dT_From);
            String DateTill = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dT_Till);
        	String query1 = "SELECT * FROM task WHERE fk_userID = '"+ userID+ 
        			"' AND completedDate BETWEEN '" + DateFrom +
        			"' AND '" + DateTill + "' ORDER BY completedDate ASC";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    ResultSet rs = stmt.executeQuery( query1 );
        		    while (rs.next()){
        		    blankTask = new Task();
        		    blankTask.setTaskID(rs.getInt("taskID"));
        		    blankTask.setDescription(rs.getString("description"));
        		    blankTask.setObservable(Boolean.valueOf(rs.getString("observable")));
        		    blankTask.setStatus(TaskStatus.valueOf(rs.getString("status")));
        		    blankTask.setTaskName(rs.getString("name"));
        		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dueDate"));
        		    blankTask.setDueDate(date1);
        		    blankTask.setPriority(Boolean.valueOf(rs.getString("priority")));
        		    tasksOnList.add(blankTask);
        		    }
        		    System.out.println( "SelectAllCompletedTasks() returned " + rs );
        		} catch ( SQLException e ) {
        			e.printStackTrace();
        		}
        		  catch ( ParseException p ) {
        			p.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);            
        	return tasksOnList;
        }
        
        /**
        ******* END OF TASK CRUD *******
        */

        /**
        ******* START OF MEDIA CRUD *******
        * @author jmitchel2
        */
        /**
         * Add a new media to the database.
         * @param Media
         */
        public void AddMedia(Media media, int userID) {
    		sqlCon.enforceForeignKeys(true);
            ds.setConfig(sqlCon);
//        	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(hTB.getDT_Executed());
        	String query1 = "INSERT INTO media " +
        			"(fk_userID, media_ID_Tag, flagged, rating) Values ( '" +
        			userID + "', '" +
        			media.getMedia_ID_Tag().replaceAll("'", "''") + "', '" +
        			media.getFlagged() + "', '" +
        			media.getRating() + "')";
//        			DateTime + "')";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    int rv = stmt.executeUpdate( query1 );
        		    System.out.println( "AddMedia() returned " + rv );
        		} catch ( SQLException e ) {
        		    //gets called when a user is passed in that isn't in the user table.
        			e.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
        }
        /**
         * Update a Media within the Database
         * 
         * @param Media
         */
            public void UpdateMedia(Media media) {
        		sqlCon.enforceForeignKeys(true);
                ds.setConfig(sqlCon);
//            	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(hTB.getDT_Executed());
            	String query1 = "UPDATE media " +
            			"SET media_ID_Tag = '" + media.getMedia_ID_Tag().replaceAll("'", "''") + 
            			"', flagged = '" + media.getFlagged() + 
            			"', rating = '" + media.getRating() +
//            			"', dT_Executed = '" + DateTime + 
            			"' WHERE media_ID = '" + media.getMediaID() + "'";
            	try ( Connection conn = ds.getConnection();
            		    Statement stmt = conn.createStatement(); ) {
            		    int rv = stmt.executeUpdate( query1 );
            		    System.out.println( "UpdateMedia() returned " + rv );
            		} catch ( SQLException e ) {
            		    //I have no idea how to get inside of this statement without the DB not connecting.
            			e.printStackTrace();
            		}
        		sqlCon.enforceForeignKeys(false);
                ds.setConfig(sqlCon);
            	
            }   
            /**
             * Delete a Media within the Database
             *              * 
             * @param int
             */
            public void DeleteMedia(int mediaID) {
        		sqlCon.enforceForeignKeys(true);
                ds.setConfig(sqlCon);
            	String query1 = "DELETE FROM media WHERE media_ID = '" + mediaID + "'";
            	try ( Connection conn = ds.getConnection();
            		    Statement stmt = conn.createStatement(); ) {
            		    int rv = stmt.executeUpdate( query1 );
            		    System.out.println( "DeleteMedia() returned " + rv );
            		} catch ( SQLException e ) {
            		    e.printStackTrace();
            		}
        		sqlCon.enforceForeignKeys(false);
                ds.setConfig(sqlCon);
            	
            }    	
            /**
             * Select a Media within the database using the mediaID
             *              * 
             * @param int
             * @return Media
             */
            public Media SelectMedia(int mediaID) {
        		sqlCon.enforceForeignKeys(true);
                ds.setConfig(sqlCon);
            	Media media1 = new Media();
            	String query1 = "SELECT * FROM media WHERE media_ID = '" + mediaID + "'";
            	try ( Connection conn = ds.getConnection();
            		    Statement stmt = conn.createStatement(); ) {
            		    ResultSet rs = stmt.executeQuery( query1 );
            		    media1.setMediaID(rs.getInt("media_ID"));
            		    media1.setMedia_ID_Tag(rs.getString("media_ID_Tag"));
            		    media1.setFlagged(Boolean.valueOf(rs.getString("flagged")));
            		    media1.setRating(rs.getInt("rating"));
//            		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dT_Executed"));
//            		    hTB1.setDT_Executed(date1);
            		    System.out.println( "SelectMedia() returned " + rs );
            		} catch ( SQLException e ) {
            			e.printStackTrace();
            		}
//      			 catch ( ParseException p ) {
//      				 p.printStackTrace();
//      			 }
        		sqlCon.enforceForeignKeys(false);
                ds.setConfig(sqlCon);
            	return media1;
            }
            
            /**
             * Mainly used for JUNIT testing, deletes the Media table & happy_thought_button Table at the beginning of testing to remove all test data.
             * 
             */
            public void DeleteAllMedia(){
            	String query1 = "DROP TABLE IF EXISTS 'media'";
            	try ( Connection conn = this.ds.getConnection();
            		    Statement stmt = conn.createStatement(); ) {
        		    int rv = stmt.executeUpdate( query1 );
        		    System.out.println( "DeleteAllMedias() returned " + rv );
            	} catch ( SQLException e ) {
        			e.printStackTrace();
            }
            	//This can be commented out once everyone's database is updated
            	String query2 = "DROP TABLE IF EXISTS 'happy_thought_button'";
            	try ( Connection conn = this.ds.getConnection();
            		    Statement stmt = conn.createStatement(); ) {
        		    int rv = stmt.executeUpdate( query2 );
        		    System.out.println( "DeleteAllHappyThoughtButtons() returned " + rv );
            	} catch ( SQLException e ) {
        			e.printStackTrace();
            }
            	
            }
            
            /**
             * Grab all Media within the Database
             * 
             * @param int
             * @return ArrayList<Media>
             */
            public ArrayList<Media> SelectAllMedias(int userID){
        		sqlCon.enforceForeignKeys(true);
                ds.setConfig(sqlCon);
            	ArrayList<Media> MediasOnList = new ArrayList<Media>();
            	Media blankmedia = new Media();
            	String query1 = "SELECT * FROM media WHERE fk_userID = '" + userID + "'";
            	try ( Connection conn = ds.getConnection();
            		    Statement stmt = conn.createStatement(); ) {
            		    ResultSet rs = stmt.executeQuery( query1 );
            		    while (rs.next()){
            		    	blankmedia = new Media();
            		    	blankmedia.setMediaID(rs.getInt("media_ID"));
            		    	blankmedia.setMedia_ID_Tag(rs.getString("media_ID_Tag"));
            		    	blankmedia.setFlagged(Boolean.valueOf(rs.getString("flagged")));
            		    	blankmedia.setRating(rs.getInt("rating"));
//            		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dT_Executed"));
//            		    blankHTB.setDT_Executed(date1);
            		    	MediasOnList.add(blankmedia);
            		    }
            		    System.out.println( "SelectAllMedias() returned " + rs );
            		} catch ( SQLException e ) {
            			e.printStackTrace();
            		}
//            		  catch ( ParseException p ) {
//            			p.printStackTrace();
//            		}
        		sqlCon.enforceForeignKeys(false);
                ds.setConfig(sqlCon);
            	return MediasOnList;
            }

            /**
             ******* END OF MEDIA CRUD *******
             */

             /**
             ******* START OF OBSERVER CRUD *******
             * @author jmitchel2, ehols001
             */
 
            
            /**
             * Add a new Observer to the database.
             * @param Observer, Task
             */
            public void AddObserver(Observer observer, int taskID) {
            	sqlCon.enforceForeignKeys(true);
                ds.setConfig(sqlCon);
            	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(observer.getDTGathered());
            	String query1 = "INSERT INTO observer " +
            			"( fk_taskID, observerScore, threshold, defaultEyeScore, dT_Gathered) Values ( '" +
            			taskID + "', '" +
            			observer.getObserverScore() + "', '" +
            			observer.getThreshold() + "', '" +
            			observer.getDefaultEyeScore() + "', '" +
            			DateTime + "')"; 
            	try ( Connection conn = ds.getConnection();
            		    Statement stmt = conn.createStatement(); ) {
            		    int rv = stmt.executeUpdate( query1 );
            		    System.out.println( "AddObserver() returned " + rv );
            		} catch ( SQLException e ) {
            		    //gets called when a task is passed in that isn't in the task table.
            			e.printStackTrace();
            		}
        		sqlCon.enforceForeignKeys(false);
                ds.setConfig(sqlCon);
            }

            /**
             * Update a Observer within the Database
             * 
             * @param Observer
             */
                public void UpdateObserver(Observer observer) {
            		sqlCon.enforceForeignKeys(true);
                    ds.setConfig(sqlCon);
                	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(observer.getDTGathered());
                	String query1 = "UPDATE observer " +
                			"SET observerScore = '" + observer.getObserverScore() + 
                			"', threshold = '" + observer.getThreshold() + 
                			"', dT_Gathered = '" + DateTime + 
                			"', defaultEyeScore= '" + observer.getDefaultEyeScore() +
                			"' WHERE observerID = '" + observer.getObserverID() + "'";
                	try ( Connection conn = ds.getConnection();
                		    Statement stmt = conn.createStatement(); ) {
                		    int rv = stmt.executeUpdate( query1 );
                		    System.out.println( "UpdateObserver() returned " + rv );
                		} catch ( SQLException e ) {
                		    e.printStackTrace();
                		}
            		sqlCon.enforceForeignKeys(false);
                    ds.setConfig(sqlCon);
                	
                }
                
                /**
                 * Delete a Observer within the Database
                 * @param int
                 */
                public void DeleteObserver(int observerID) {
            		sqlCon.enforceForeignKeys(true);
                    ds.setConfig(sqlCon);
                	String query1 = "DELETE FROM observer WHERE observerID = '" + observerID + "'";
                	try ( Connection conn = ds.getConnection();
                		    Statement stmt = conn.createStatement(); ) {
                		    int rv = stmt.executeUpdate( query1 );
                		    System.out.println( "DeleteObserver() returned " + rv );
                		} catch ( SQLException e ) {
                		    e.printStackTrace();
                		}
            		sqlCon.enforceForeignKeys(false);
                    ds.setConfig(sqlCon);
                	
                }    	
                
                /**
                 * Select a Observer within the database using the observerID
                 * @param int
                 * @return Observer
                 */
                public Observer SelectObserver(int observerID) {
            		sqlCon.enforceForeignKeys(true);
                    ds.setConfig(sqlCon);
                	Observer observer1 = new Observer();
                	String query1 = "SELECT * FROM observer WHERE observerID = '" + observerID + "'";
                	try ( Connection conn = ds.getConnection();
                		    Statement stmt = conn.createStatement(); ) {
                		    ResultSet rs = stmt.executeQuery( query1 );
                		    observer1.setObserverID(rs.getInt("observerID"));
                		    observer1.setObserverScore(rs.getInt("observerScore"));
                		    observer1.setThreshold(rs.getInt("threshold"));
                		    observer1.setDefaultEyeScore(rs.getInt("defaultEyeScore"));
                		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(rs.getString("dT_Gathered"));
                		    observer1.setDTGathered(date1);
                		    System.out.println( "SelectObserver() returned " + rs );
                		} catch ( SQLException e ) {
                			e.printStackTrace();
                		}
                		  catch ( ParseException p ) {
                			p.printStackTrace();
                			System.exit( 0 );
                		}
            		sqlCon.enforceForeignKeys(false);
                    ds.setConfig(sqlCon);
                	return observer1;
                }
            	
                /**
                 * Mainly used for JUNIT testing, deletes the Observer table at the beginning of testing to remove all test data.
                 * 
                 */
                public void DeleteAllObservers(){
            		sqlCon.enforceForeignKeys(true);
                    ds.setConfig(sqlCon);
                	String query1 = "DROP TABLE IF EXISTS 'observer'";
                	try ( Connection conn = this.ds.getConnection();
                		    Statement stmt = conn.createStatement(); ) {
            		    int rv = stmt.executeUpdate( query1 );
            		    System.out.println( "DeleteAllObservers() returned " + rv );
                	} catch ( SQLException e ) {
            			e.printStackTrace();
                	}
            		sqlCon.enforceForeignKeys(false);
                    ds.setConfig(sqlCon);
                	
                }

                /**
                 * Grab all observers within the Database
                 * 
                 * @return ArrayList<Observers>
                 */
                public ArrayList<Observer> SelectAllObservers(int task_ID){
            		sqlCon.enforceForeignKeys(true);
                    ds.setConfig(sqlCon);
                	ArrayList<Observer> ObserversOnList = new ArrayList<Observer>();
                	Observer blankObserver = new Observer();
                	String query1 = "SELECT * FROM observer WHERE fk_taskID='" + task_ID + "' ORDER BY dT_Gathered ASC";
                	try ( Connection conn = ds.getConnection();
                		    Statement stmt = conn.createStatement(); ) {
                		    ResultSet rs = stmt.executeQuery( query1 );
                		    while (rs.next()){
                		    blankObserver = new Observer();
                		    blankObserver.setObserverID(rs.getInt("observerID"));
                		    blankObserver.setObserverScore(rs.getInt("observerScore"));
                		    blankObserver.setThreshold(rs.getInt("threshold"));
                		    blankObserver.setDefaultEyeScore(rs.getInt("defaultEyeScore"));
                		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dT_Gathered"));
                		    blankObserver.setDTGathered(date1);
                		    ObserversOnList.add(blankObserver);
                		    }
                		    System.out.println( "SelectAllObservers() returned " + rs );
                		} catch ( SQLException e ) {
                			e.printStackTrace();
                		}
                		  catch ( ParseException p ) {
                			p.printStackTrace();
                			System.exit( 0 );
                		}
            		sqlCon.enforceForeignKeys(false);
                    ds.setConfig(sqlCon);
                	return ObserversOnList;
                }

                /**
                 ******* END OF OBSERVER CRUD *******
                 */

                 /**
                 ******* START OF SETTINGS CRUD *******
                 * @author jmitchel2
                 */

//                /**
//                 * OLD CODE 3/16/2022 -Jmitchel2
//                 * Add a new Settings to the database.
//                 * @param Settings
//                 */
//                public void AddSettings(Settings settings) {
//            		sqlCon.enforceForeignKeys(true);
//                    ds.setConfig(sqlCon);
//                	String query1 = "INSERT INTO settings " +
//                			"( iconCircles, icons, opacityCircles, opacityIcons, isCollapsed, xCoord, yCoord, isVertical, iconSize, timerIsVisible, pmIsVisible, ftsIsVisible, htbIsVisible, ntbIsVisible, progReportIsVisible, avatarIsActive, textIsActive, audioIsActive, avatarFilePath, audioFilePath, alwaysOnScreen, avatarSize, pomodoroIsActive, workPeriod, breakPeriod, timeShowing, ftsIsActive, ntbIsActive, isAutoLinked, htbIsActive) Values ( '" +
//                			settings.getIconCircles().getRGB() + "', '" +
//                			settings.getIcons().getRGB() + "', '" +
//                			settings.getOpacityCircles() + "', '" +
//                			settings.getOpacityIcons() + "', '" +
//                			settings.getIsCollapsed() + "', '" +
//                			settings.getXCoord() + "', '" +
//                			settings.getYCoord() + "', '" +
//                			settings.getIsVertical() + "', '" +
//                			settings.getIconSize() + "', '" +
//                			settings.getTimerIsVisible() + "', '" +
//                			settings.getPmIsVisible() + "', '" +
//                			settings.getFtsIsVisible() + "', '" +
//                			settings.getHtbIsVisible() + "', '" +
//                			settings.getNtbIsVisible() + "', '" +
//                			settings.getProgReportIsVisible() + "', '" +
//                			settings.getAvatarIsActive() + "', '" +
//                			settings.getTextIsActive() + "', '" +
//                			settings.getAudioIsActive() + "', '" +
//                			settings.getAvatarFilePath().replaceAll("'", "''") + "', '" +
//                			settings.getAudioFilePath().replaceAll("'", "''") + "', '" +
//                			settings.getAlwaysOnScreen() + "', '" +
//                			settings.getAvatarSize() + "', '" +
//                			settings.getPomodoroIsActive() + "', '" +
//                			settings.getWorkPeriod() + "', '" +
//                			settings.getBreakPeriod() + "', '" +
//                			settings.getTimeShowing() + "', '" +
//                			settings.getFtsIsActive() + "', '" +
//                			settings.getNtbIsActive() + "', '" +
//                			settings.getIsAutoLinked() + "', '" +
//                			settings.getHtbIsActive() +"')";
//                	try ( Connection conn = ds.getConnection();
//                		    Statement stmt = conn.createStatement(); ) {
//                		    int rv = stmt.executeUpdate( query1 );
//                		    System.out.println( "AddSettings() returned " + rv );
//                		} catch ( SQLException e ) {
//                		    e.printStackTrace();
//                		}
//            		sqlCon.enforceForeignKeys(false);
//                    ds.setConfig(sqlCon);
//                }

                
                /**
                 * Add a new Settings to the database.
                 * @param Settings, int
                 */
                public void AddSettings(Settings settings, int userID) {
            		sqlCon.enforceForeignKeys(true);
                    ds.setConfig(sqlCon);
                	String query1 = "INSERT INTO settings " +
                			"(fk_userID, iconCircles, icons, opacityCircles, opacityIcons, isCollapsed, xCoord, yCoord, isVertical, iconSize, timerIsVisible, pmIsVisible, ftsIsVisible, htbIsVisible, ntbIsVisible, progReportIsVisible, avatarIsActive, textIsActive, audioIsActive, avatarFilePath, alwaysOnScreen, avatarSize, pomodoroIsActive, workPeriod, breakPeriod, timeShowing, ftsIsActive, ntbIsActive, isAutoLinked, htbIsActive, timerVisibilityIsLocked, pmVisibilityIsLocked, htbVisibilityIsLocked, ftsVisibilityIsLocked, ntbVisibilityIsLocked, progReportVisibilityIsLocked, textToSpeech, pomodoroIsLocked, workPeriodIsLocked, breakPeriodIsLocked, ftsIsLocked, ntbIsLocked, htbIsLocked) Values ( '" +
                			userID + "', '" +
                			settings.getIconCircles().getRGB() + "', '" +
                			settings.getIcons().getRGB() + "', '" +
                			settings.getOpacityCircles() + "', '" +
                			settings.getOpacityIcons() + "', '" +
                			settings.getIsCollapsed() + "', '" +
                			settings.getXCoord() + "', '" +
                			settings.getYCoord() + "', '" +
                			settings.getIsVertical() + "', '" +
                			settings.getIconSize() + "', '" +
                			settings.getTimerIsVisible() + "', '" +
                			settings.getPmIsVisible() + "', '" +
                			settings.getFtsIsVisible() + "', '" +
                			settings.getHtbIsVisible() + "', '" +
                			settings.getNtbIsVisible() + "', '" +
                			settings.getProgReportIsVisible() + "', '" +
                			settings.getAvatarIsActive() + "', '" +
                			settings.getTextIsActive() + "', '" +
                			settings.getAudioIsActive() + "', '" +
                			settings.getAvatarFilePath().replaceAll("'", "''") + "', '" +
//                			settings.getAudioFilePath().replaceAll("'", "''") + "', '" +
                			settings.getAlwaysOnScreen() + "', '" +
                			settings.getAvatarSize() + "', '" +
                			settings.getPomodoroIsActive() + "', '" +
                			settings.getWorkPeriod() + "', '" +
                			settings.getBreakPeriod() + "', '" +
                			settings.getTimeShowing() + "', '" +
                			settings.getFtsIsActive() + "', '" +
                			settings.getNtbIsActive() + "', '" +
                			settings.getIsAutoLinked() + "', '" +
                			settings.getHtbIsActive() +"', '" +
                			settings.getTimerVisibilityIsLocked() + "', '" +
                			settings.getPmVisibilityIsLocked() + "', '" +
                			settings.getHtbVisibilityIsLocked() + "', '" +
                			settings.getFtsVisibilityIsLocked() + "', '" +
                			settings.getNtbVisibilityIsLocked() + "', '" +
                			settings.getProgReportVisibilityIsLocked() + "', '" +
                			settings.getTextToSpeech() + "', '" +
                			settings.getPomodoroIsLocked() + "', '" +
                			settings.getWorkPeriodIsLocked() + "', '" +
                			settings.getBreakPeriodIsLocked() + "', '" +
                			settings.getFtsIsLocked() + "', '" +
                			settings.getNtbIsLocked() + "', '" +
                			settings.getHtbIsLocked() + "')"; 
                	try ( Connection conn = ds.getConnection();
                		    Statement stmt = conn.createStatement(); ) {
                		    int rv = stmt.executeUpdate( query1 );
                		    System.out.println( "AddSettings() returned " + rv );
                		} catch ( SQLException e ) {
                		    e.printStackTrace();
                		}
            		sqlCon.enforceForeignKeys(false);
                    ds.setConfig(sqlCon);
                }

                /**
                 * Update a Settings within the Database
                 * 
                 * @param Settings
                 */
                    public void UpdateSettings(Settings settings) {
                		sqlCon.enforceForeignKeys(true);
                        ds.setConfig(sqlCon);
                    	String query1 = "UPDATE settings " +
                    			"SET iconCircles = '" + settings.getIconCircles().getRGB() + 
                    			"', icons = '" + settings.getIcons().getRGB() + 
                    			"', opacityCircles = '" + settings.getOpacityCircles() + 
                    			"', opacityIcons = '" + settings.getOpacityIcons() + 
                    			"', isCollapsed = '" + settings.getIsCollapsed() + 
                    			"', xCoord = '" + settings.getXCoord() + 
                    			"', yCoord = '" + settings.getYCoord() + 
                    			"', isVertical = '" + settings.getIsVertical() + 
                    			"', iconSize = '" + settings.getIconSize() + 
                    			"', timerIsVisible = '" + settings.getTimerIsVisible() + 
                    			"', pmIsVisible = '" + settings.getPmIsVisible() + 
                    			"', ftsIsVisible = '" + settings.getFtsIsVisible() + 
                    			"', htbIsVisible = '" + settings.getHtbIsVisible() + 
                    			"', ntbIsVisible = '" + settings.getNtbIsVisible() + 
                    			"', progReportIsVisible = '" + settings.getProgReportIsVisible() + 
                    			"', avatarIsActive = '" + settings.getAvatarIsActive() + 
                    			"', textIsActive = '" + settings.getTextIsActive() + 
                    			"', audioIsActive = '" + settings.getAudioIsActive() + 
                    			"', avatarFilePath = '" + settings.getAvatarFilePath().replaceAll("'", "''") + 
//                    			"', audioFilePath = '" + settings.getAudioFilePath().replaceAll("'", "''") + 
                    			"', alwaysOnScreen = '" + settings.getAlwaysOnScreen() + 
                    			"', avatarSize = '" + settings.getAvatarSize() + 
                    			"', pomodoroIsActive = '" + settings.getPomodoroIsActive() + 
                    			"', workPeriod = '" + settings.getWorkPeriod() + 
                    			"', breakPeriod = '" + settings.getBreakPeriod() + 
                    			"', timeShowing = '" + settings.getTimeShowing() + 
                    			"', ftsIsActive = '" + settings.getFtsIsActive() + 
                    			"', ntbIsActive = '" + settings.getNtbIsActive() + 
                    			"', isAutoLinked = '" + settings.getIsAutoLinked() + 
                    			"', htbIsActive = '" + settings.getHtbIsActive() + 
                    			"', timerVisibilityIsLocked = '" + settings.getTimerVisibilityIsLocked() + 
                    			"', pmVisibilityIsLocked = '" + settings.getPmVisibilityIsLocked() + 
                    			"', htbVisibilityIsLocked = '" + settings.getHtbVisibilityIsLocked() +
                    			"', ftsVisibilityIsLocked = '" + settings.getFtsVisibilityIsLocked() +
                    			"', ntbVisibilityIsLocked = '" + settings.getNtbVisibilityIsLocked() + 
                    			"', progReportVisibilityIsLocked = '" + settings.getProgReportVisibilityIsLocked() + 
                    			"', textToSpeech = '" + settings.getTextToSpeech() + 
                    			"', pomodoroIsLocked = '" + settings.getPomodoroIsLocked() + 
                    			"', workPeriodIsLocked = '" + settings.getWorkPeriodIsLocked() + 
                    			"', breakPeriodIsLocked = '" + settings.getBreakPeriodIsLocked() + 
                    			"', ftsIsLocked = '" + settings.getFtsIsLocked() + 
                    			"', ntbIsLocked = '" + settings.getNtbIsLocked() + 
                    			"', htbIsLocked = '" + settings.getHtbIsLocked() + 
                    			"' WHERE settingsID = '" + settings.getSettingsID() + "'";
                    	try ( Connection conn = ds.getConnection();
                    		    Statement stmt = conn.createStatement(); ) {
                    		    int rv = stmt.executeUpdate( query1 );
                    		    System.out.println( "UpdateSettings() returned " + rv );
                    		} catch ( SQLException e ) {
                    		    e.printStackTrace();
                    		}
                		sqlCon.enforceForeignKeys(false);
                        ds.setConfig(sqlCon);
                    	
                    }
                    
                    /**
                     * Delete a Settings within the Database
                     * @param int
                     */
                    public void DeleteSettings(int settingsID) {
                		sqlCon.enforceForeignKeys(true);
                        ds.setConfig(sqlCon);
                    	String query1 = "DELETE FROM settings WHERE settingsID = '" + settingsID + "'";
                    	try ( Connection conn = ds.getConnection();
                    		    Statement stmt = conn.createStatement(); ) {
                    		    int rv = stmt.executeUpdate( query1 );
                    		    System.out.println( "DeleteSettings() returned " + rv );
                    		} catch ( SQLException e ) {
                    		    e.printStackTrace();
                    		}
                		sqlCon.enforceForeignKeys(false);
                        ds.setConfig(sqlCon);
                    	
                    }    	

                
                    /**
                     * Select a Settings within the database using the settingsID
                     * @param int
                     * @return Settings
                     */
                    public Settings SelectSettings(int userID) {
                		sqlCon.enforceForeignKeys(true);
                        ds.setConfig(sqlCon);
                    	Settings settings1 = new Settings(userID);
                    	String query1 = "SELECT * FROM settings WHERE fk_userID = '" + userID + "'";
                    	try ( Connection conn = ds.getConnection();
                    		    Statement stmt = conn.createStatement(); ) {
                    		    ResultSet rs = stmt.executeQuery( query1 );
                    		    settings1.setSettingsID(rs.getInt("settingsID"));
                    		    settings1.setIconCircles(new Color(rs.getInt("iconCircles")));
                    		    settings1.setIcons(new Color(rs.getInt("icons")));
                    		    settings1.setOpacityCircles(rs.getInt("opacityCircles"));
                    		    settings1.setOpacityIcons(rs.getInt("opacityIcons"));
                    		    settings1.setIsCollapsed(Boolean.valueOf(rs.getString("isCollapsed")));
                    		    settings1.setXCoord(rs.getInt("xCoord"));
                    		    settings1.setYCoord(rs.getInt("yCoord"));
                    		    settings1.setIsVertical(Boolean.valueOf(rs.getString("isVertical")));
                    		    settings1.setIconSize(rs.getInt("iconSize"));
                    		    settings1.setTimerIsVisible(Boolean.valueOf(rs.getString("timerIsVisible")));
                    		    settings1.setPmIsVisible(Boolean.valueOf(rs.getString("pmIsVisible")));
                    		    settings1.setFtsIsVisible(Boolean.valueOf(rs.getString("ftsIsVisible")));
                    		    settings1.setHtbIsVisible(Boolean.valueOf(rs.getString("htbIsVisible")));
                    		    settings1.setNtbIsVisible(Boolean.valueOf(rs.getString("ntbIsVisible")));
                    		    settings1.setProgReportIsVisible(Boolean.valueOf(rs.getString("progReportIsVisible")));
                    		    settings1.setAvatarIsActive(Boolean.valueOf(rs.getString("avatarIsActive")));
                    		    settings1.setTextIsActive(Boolean.valueOf(rs.getString("textIsActive")));
                    		    settings1.setAudioIsActive(Boolean.valueOf(rs.getString("audioIsActive")));
                    		    settings1.setAvatarFilePath(rs.getString("avatarFilePath"));
//                    		    settings1.setAudioFilePath(rs.getString("audioFilePath"));
                    		    settings1.setAlwaysOnScreen(Boolean.valueOf(rs.getString("alwaysOnScreen")));
                    		    settings1.setAvatarSize(rs.getInt("avatarSize"));
                    		    settings1.setPomodoroIsActive(Boolean.valueOf(rs.getString("pomodoroIsActive")));
                    		    settings1.setWorkPeriod(rs.getInt("workPeriod"));
                    		    settings1.setBreakPeriod(rs.getInt("breakPeriod"));
                    		    settings1.setTimeShowing(Boolean.valueOf(rs.getString("timeShowing")));
                    		    settings1.setFtsIsActive(Boolean.valueOf(rs.getString("ftsIsActive")));
                    		    settings1.setNtbIsActive(Boolean.valueOf(rs.getString("ntbIsActive")));
                    		    settings1.setIsAutoLinked(Boolean.valueOf(rs.getString("isAutoLinked")));
                    		    settings1.setHtbIsActive(Boolean.valueOf(rs.getString("htbIsActive")));
                    		    settings1.setTimerVisibilityIsLocked(Boolean.valueOf(rs.getString("timerVisibilityIsLocked")));
                    		    settings1.setPmVisibilityIsLocked(Boolean.valueOf(rs.getString("pmVisibilityIsLocked")));
                    		    settings1.setHtbVisibilityIsLocked(Boolean.valueOf(rs.getString("htbVisibilityIsLocked")));
                    		    settings1.setFtsVisibilityIsLocked(Boolean.valueOf(rs.getString("ftsVisibilityIsLocked")));
                    		    settings1.setNtbVisibilityIsLocked(Boolean.valueOf(rs.getString("ntbVisibilityIsLocked")));
                    		    settings1.setProgReportVisibilityIsLocked(Boolean.valueOf(rs.getString("progReportVisibilityIsLocked")));
                    		    settings1.setTextToSpeech(Boolean.valueOf(rs.getString("textToSpeech")));
                    		    settings1.setPomodoroIsLocked(Boolean.valueOf(rs.getString("pomodoroIsLocked")));
                    		    settings1.setWorkPeriodIsLocked(Boolean.valueOf(rs.getString("workPeriodIsLocked")));
                    		    settings1.setBreakPeriodIsLocked(Boolean.valueOf(rs.getString("breakPeriodIsLocked")));
                    		    settings1.setFtsIsLocked(Boolean.valueOf(rs.getString("ftsIsLocked")));
                    		    settings1.setNtbIsLocked(Boolean.valueOf(rs.getString("ntbIsLocked")));
                    		    settings1.setHtbIsLocked(Boolean.valueOf(rs.getString("htbIsLocked")));
                    		    System.out.println( "SelectSettings() returned " + rs );
                    		} catch ( SQLException e ) {
                    			e.printStackTrace();
                    		}
                		sqlCon.enforceForeignKeys(false);
                        ds.setConfig(sqlCon);
                    	return settings1;
                    }

                
                /**
                 * Mainly used for JUNIT testing, deletes the Settings table at the beginning of testing to remove all test data.
                 */
                public void DeleteAllSettings(){
                	String query1 = "DROP TABLE IF EXISTS 'settings'";
                	try ( Connection conn = this.ds.getConnection();
                		    Statement stmt = conn.createStatement(); ) {
            		    int rv = stmt.executeUpdate( query1 );
            		    System.out.println( "DeleteAllSettings() returned " + rv );
                	} catch ( SQLException e ) {
            			e.printStackTrace();
                	}
                	
                }
                
     /**
      ******* END OF SETTINGS CRUD *******
      */

     /**
      ******* START OF LINKING ACCOUNTS *******
      * @author jmitchel2
      */
     /**
      * Add a new Linked Account to the database.
      * @param Parent_Account, User_Account
      */
    public void AddLinked_Account(Parent_Account parent, User_Account user) {
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
         	String query1 = "INSERT INTO linked_Accounts " +
      			"( fk_parentID, fk_userID) Values ( '" +
       			parent.getParentID() + "', '" +
       			user.getUserID() + "')";
           	try ( Connection conn = ds.getConnection();
       		    Statement stmt = conn.createStatement(); ) {
       		    int rv = stmt.executeUpdate( query1 );
       		    System.out.println( "AddLinked_Account() returned " + rv );
           		} catch ( SQLException e ) {
        		    //gets called when a user or parent is passed in that isn't in the user table or parent table.
           		    e.printStackTrace();
          		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
            }
    /**
     * Delete a new Account the database.
     * @param Parent_Account, User_Account
     */
   public void DeleteLinked_Account(Parent_Account parent, User_Account user) {
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
        	String query1 = "DELETE FROM linked_Accounts WHERE fk_parentID= '" + parent.getParentID() + "' AND fk_userID= '" + user.getUserID() +"'";
        	try ( Connection conn = ds.getConnection();
        		    Statement stmt = conn.createStatement(); ) {
        		    int rv = stmt.executeUpdate( query1 );
        		    System.out.println( "DeleteLinked_Account() returned " + rv );
        		} catch ( SQLException e ) {
        		    e.printStackTrace();
        		}
    		sqlCon.enforceForeignKeys(false);
            ds.setConfig(sqlCon);
           }
   
   /**
    * Returns all user accounts that are related to a parent account.
    * 
    * @param Parent_Account
    * @return ArrayList<User_Account>
    */
   
   public ArrayList<User_Account> Select_All_Users_Linked_Account(Parent_Account parent){
		sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
	   ArrayList<User_Account> userAccountList = new ArrayList<User_Account>();
	   User_Account blankUser= new User_Account();
	   String query1 = "SELECT fk_userID FROM linked_Accounts WHERE fk_parentID = '" + parent.getParentID() + "'";
   		try ( Connection conn = ds.getConnection();
		    Statement stmt = conn.createStatement(); ) {
		    ResultSet rs = stmt.executeQuery( query1 );
		    if (rs.isClosed()) {
		    	return userAccountList;
		    }
		    while (rs.next()){
    		blankUser = new User_Account();
    		blankUser = this.SelectUser_Account(rs.getInt("fk_userID"));
    		userAccountList.add(blankUser);
		    }		    
   		}catch ( SQLException e ) {
			e.printStackTrace();
		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
	   return userAccountList;
   }

   /**
    * Mainly used for JUNIT testing, deletes the LinkedAccounts table at the beginning of testing to remove all test data.
    */ 
   public void DeleteAllLinkedAccounts() {
   	String query1 = "DROP TABLE IF EXISTS 'linked_Accounts'";
   	try ( Connection conn = this.ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
		    int rv = stmt.executeUpdate( query1 );
		    System.out.println( "DeleteAllLinkedAccounts() returned " + rv );
   	} catch ( SQLException e ) {
			e.printStackTrace();
   	}   
   }
   
   /**
    ******* END OF LINKING ACCOUNTS CRUD *******
    */

   /**
    ******* START OF NOTIFICATION SYSTEM CRUD *******
    * @author jmitchel2
    */
   
   /**
    * Add a new Notification_System to the database.
    * @param Notification_System, User_Account
    */
   public void AddNotification(Notification notification, int userID) {
   	sqlCon.enforceForeignKeys(true);
       ds.setConfig(sqlCon);
   	String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(notification.getDT_Notification());
   	String query1 = "INSERT INTO notification " +
   			"( fk_userID, type, ignored, dT_Notification) Values ( '" +
   			userID + "', '" +
   			notification.getType().replaceAll("'", "''") + "', '" +
   			notification.getIgnored() + "', '" +
   			DateTime + "')"; 
   	try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    int rv = stmt.executeUpdate( query1 );
   		    System.out.println( "AddNotification() returned " + rv );
   		} catch ( SQLException e ) {
   		    //gets called when a task is passed in that isn't in the task table.
   			e.printStackTrace();
   		}
		sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   }

   /**
    * Update a Notification_System within the Database
    * 
    * @param Notification_System
    */
   public void UpdateNotification(Notification notification) {
	    sqlCon.enforceForeignKeys(true);
        ds.setConfig(sqlCon);
        String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(notification.getDT_Notification());
        String query1 = "UPDATE notification " +
        "SET type = '" + notification.getType().replaceAll("'", "''") + 
        "', ignored = '" + notification.getIgnored() + 
        "', dT_Notification = '" + DateTime + 
        "' WHERE notificationID = '" + notification.getNotificationID() + "'";
        try ( Connection conn = ds.getConnection();
    	    Statement stmt = conn.createStatement(); ) {
    	    int rv = stmt.executeUpdate( query1 );
       	    System.out.println( "UpdateNotification() returned " + rv );
        } catch ( SQLException e ) {
       		e.printStackTrace();
        }
        sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);
   }

   /**
    * Delete a Notification_System within the Database
    *              * 
    * @param int
    */
   public void DeleteNotification(int notificationID) {
	   sqlCon.enforceForeignKeys(true);
	   ds.setConfig(sqlCon);
   	   String query1 = "DELETE FROM notification WHERE notificationID = '" + notificationID + "'";
   	   try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    int rv = stmt.executeUpdate( query1 );
   		    System.out.println( "DeleteNotification() returned " + rv );
   	   } catch ( SQLException e ) {
   		    e.printStackTrace();
   	   }
   	   sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   } 	

   /**
    * Select a Notification_System within the database using the notificationID
    *              * 
    * @param int
    * @return Notification_System
    */
   public Notification SelectNotification(int notificationID) {
		sqlCon.enforceForeignKeys(true);
		ds.setConfig(sqlCon);
		Notification notification1 = new Notification();
		String query1 = "SELECT * FROM notification WHERE notificationID = '" + notificationID + "'";
		try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    ResultSet rs = stmt.executeQuery( query1 );
   		    notification1.setNotificationID(rs.getInt("notificationID"));
   		    notification1.setType(rs.getString("type"));
   		    notification1.setIgnored(Boolean.valueOf(rs.getString("ignored")));
   		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dT_Notification"));
   		    notification1.setDT_Notification(date1);
   		    System.out.println( "SelectNotification() returned " + rs );
   			} catch ( SQLException e ) {
   				e.printStackTrace();
   			}
			 catch ( ParseException p ) {
				 p.printStackTrace();
			 }
		sqlCon.enforceForeignKeys(false);
		ds.setConfig(sqlCon);
   		return notification1;
   }
   /**
    * Mainly used for JUNIT testing, deletes the notification table at the beginning of testing to remove all test data.
    * 
    */
   public void DeleteAllNotifications(){
   	String query1 = "DROP TABLE IF EXISTS 'notification'";
   	try ( Connection conn = this.ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
		    int rv = stmt.executeUpdate( query1 );
		    System.out.println( "DeleteAllNotifications() returned " + rv );
   	} catch ( SQLException e ) {
			e.printStackTrace();
   }

   }

   /**
    * Grab all Notifications within the Database
    * 
    * @param int
    * @return ArrayList<Notification_System>
    */
   public ArrayList<Notification> SelectAllNotifications(int userID){
		sqlCon.enforceForeignKeys(true);
       ds.setConfig(sqlCon);
   	ArrayList<Notification> notificationsOnList = new ArrayList<Notification>();
   	Notification blankNotification = new Notification();
   	String query1 = "SELECT * FROM notification WHERE fk_userID = '" + userID + "'";
   	try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    ResultSet rs = stmt.executeQuery( query1 );
   		    while (rs.next()){
   		    blankNotification = new Notification();
   		    blankNotification.setNotificationID(rs.getInt("notificationID"));
   		    blankNotification.setType(rs.getString("type"));
   		    blankNotification.setIgnored(Boolean.valueOf(rs.getString("ignored")));
   		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dT_Notification"));
   		    blankNotification.setDT_Notification(date1);
   		    notificationsOnList.add(blankNotification);
   		    }
   		    System.out.println( "SelectAllNotifications() returned " + rs );
   		} catch ( SQLException e ) {
   			e.printStackTrace();
   		}
   		  catch ( ParseException p ) {
   			p.printStackTrace();
   		}
		sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   	return notificationsOnList;
   }
   
   
   /**
    * Grab all Notifications within the Database by type
    * 
    * @param int
    * @return ArrayList<Notification_System>
    */
   public ArrayList<Notification> SelectAllNotificationsType(int userID, String notificationType, Date dT_From, Date dT_Till){
	   sqlCon.enforceForeignKeys(true);
       ds.setConfig(sqlCon);
       String DateFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dT_From);
       String DateTill = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dT_Till);
   	ArrayList<Notification> notificationsOnList = new ArrayList<Notification>();
   	Notification blankNotification = new Notification();
   	String query1 = "SELECT * FROM notification WHERE fk_userID = '" + userID +
   	"' AND type = '" + notificationType +
   	"' AND dT_Notification BETWEEN '" + DateFrom +
   	"' AND '" + DateTill + "'";
   	try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    ResultSet rs = stmt.executeQuery( query1 );
   		    while (rs.next()){
   		    blankNotification = new Notification();
   		    blankNotification.setNotificationID(rs.getInt("notificationID"));
   		    blankNotification.setType(rs.getString("type"));
   		    blankNotification.setIgnored(Boolean.valueOf(rs.getString("ignored")));
   		    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("dT_Notification"));
   		    blankNotification.setDT_Notification(date1);
   		    notificationsOnList.add(blankNotification);
   		    }
   		    System.out.println( "SelectAllNotificationsType() returned " + rs );
   		} catch ( SQLException e ) {
   			e.printStackTrace();
   		}
   		  catch ( ParseException p ) {
   			p.printStackTrace();
   		}
		sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   	return notificationsOnList;
   }
   
   /**
    ******* END OF NOTIFICATION SYSTEM CRUD *******
    */
   
   /**
    ******* START OF EVENT TYPE ADD & SEARCH *******
    */

   /**
    * Add a new EVENT_TYPE to the database.
    * @param String
    */
   private void AddEventType(String Event_Name) {
   	sqlCon.enforceForeignKeys(true);
       ds.setConfig(sqlCon);
   	String query1 = "INSERT INTO event_Type " +
   			"(name) Values ( '" + Event_Name.replaceAll("'", "''") + "')";
   	try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    int rv = stmt.executeUpdate( query1 );
   		    System.out.println( "AddEventType() returned " + rv );
   		} catch ( SQLException e ) {
   		    //gets called when a task is passed in that isn't in the task table.
   			e.printStackTrace();
   		}
		sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   }

   /**
    * Search an EVENT_TYPE in the database.
    * @param String
    * @return int
    */
   private int SelectEventType(String Event_Name) {
	int event_TypeID=0; 
   	sqlCon.enforceForeignKeys(true);
       ds.setConfig(sqlCon);
		String query1 = "SELECT * FROM event_Type WHERE name = '" + Event_Name.replaceAll("'", "''") + "'";
   	try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    ResultSet rs = stmt.executeQuery( query1 );
   		    if (rs.isClosed()) {
   		    	return 0;
   		    }
   		    event_TypeID = rs.getInt("event_TypeID");
   		    System.out.println( "SelectEventType() returned " + rs );
   		} catch ( SQLException e ) {
   			e.printStackTrace();
   		}
	sqlCon.enforceForeignKeys(false);
	ds.setConfig(sqlCon);
	return event_TypeID;
   	}
   
   /**
    * Primarily used for JUNIT testing, deletes the event_Type table
    */
   public void DeleteAllEventTypes() {
	   	String query1 = "DROP TABLE IF EXISTS 'event_Type'";
	   	try ( Connection conn = this.ds.getConnection();
	   		    Statement stmt = conn.createStatement(); ) {
			    int rv = stmt.executeUpdate( query1 );
			    System.out.println( "DeleteAllEventTypes() returned " + rv );
	   	} catch ( SQLException e ) {
				e.printStackTrace();
	   	}   
	   }


   /**
    ******* END OF EVENT_TYPE ADD, SEARCH, & DROP *******
    */
   
   /**
    ******* START OF EVENT ADD, COUNT, & DROP *******
    */
   
   /**
    * Adds an event to the database
    * @param User_Account, Date, String
    */
   public void AddEvent(int userID, Date dt_Executed, String Event_Name) {
	   	sqlCon.enforceForeignKeys(true);
	    ds.setConfig(sqlCon);
        String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dt_Executed);
	    int event_ID = this.SelectEventType(Event_Name);
	   	if (event_ID== 0) {
	   		this.AddEventType(Event_Name);
	   		event_ID = this.SelectEventType(Event_Name);
	   		}
	    String query1 = "INSERT INTO event " +
	   			"(fk_userID, fk_event_TypeID, dt_Executed) Values ( '" + 
	    		userID + "', '" +
	   			event_ID + "', '" +
	    		DateTime + "')";
	   	try ( Connection conn = ds.getConnection();
	   		    Statement stmt = conn.createStatement(); ) {
	   		    int rv = stmt.executeUpdate( query1 );
	   		    System.out.println( "AddEvent() returned " + rv );
	   		} catch ( SQLException e ) {
	   		    //gets called when a task is passed in that isn't in the task table.
	   			e.printStackTrace();
	   		}
	   	sqlCon.enforceForeignKeys(false);
	    ds.setConfig(sqlCon);
	   }
   
   /**
    * Counts the number of events that are within the Event Table
    * @param User_Account, Date, Date, String
    * @return int
    */
   public int CountEvents(int userID, Date dt_From, Date dt_Till, String Event_Name ){
	   int count = 0;
	   sqlCon.enforceForeignKeys(true);
	   ds.setConfig(sqlCon);
       String DateFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dt_From);
       String DateTill = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(dt_Till);
       int event_ID = this.SelectEventType(Event_Name);
	   String query1 = "SELECT COUNT('eventID') AS counter FROM event WHERE fk_userID= '" + userID + 
			   "' AND fk_event_TypeID= '" + event_ID +
			   "' AND dt_Executed BETWEEN '" + DateFrom +
			   "' AND '" + DateTill + "'"; 
	   	try ( Connection conn = ds.getConnection();
	   		Statement stmt = conn.createStatement(); ) {
   		    ResultSet rs = stmt.executeQuery( query1 );
   		    count = rs.getInt("counter");
   		    System.out.println( "CountEvents() returned " + rs );
	   	}catch ( SQLException e ) {
   		    //gets called when a task is passed in that isn't in the task table.
   			e.printStackTrace();
   		}
	   return count;
   }

   /**
    * Primarily used for JUNIT testing, deletes the event_Type table
    */
   public void DeleteAllEvents() {
	   	String query1 = "DROP TABLE IF EXISTS 'event'";
	   	try ( Connection conn = this.ds.getConnection();
	   		    Statement stmt = conn.createStatement(); ) {
			    int rv = stmt.executeUpdate( query1 );
			    System.out.println( "DeleteAllEvents() returned " + rv );
	   	} catch ( SQLException e ) {
				e.printStackTrace();
	   	}   
	   }

   /**
    ******* END OF EVENT ADD, COUNT, & DROP *******
    */
   
   /**
    ******* START OF FTS_COLOR Add, Delete, SelectAll, & Drop *******
    */
   
   /**
    * Add a new FTS_Color to the database.
    * @param Color, User_Account
    */
   public void AddFTS_Color(Color color, int userID) {
   	sqlCon.enforceForeignKeys(true);
    ds.setConfig(sqlCon);
   	String query1 = "INSERT INTO fts_Color " +
   			"(fk_userID, color) Values ( '" +
   			userID + "', '" +
   			color.getRGB() + "')";
   	try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    int rv = stmt.executeUpdate( query1 );
   		    System.out.println( "AddFTS_Color() returned " + rv );
   		} catch ( SQLException e ) {
   		    //gets called when a task is passed in that isn't in the task table.
   			e.printStackTrace();
   		}
		sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   }
   
   /**
    * Delete a FTS_Color within the Database
    *              * 
    * @param Color, User_Account
    */
   public void DeleteFTS_Color(Color color, int userID) {
	   sqlCon.enforceForeignKeys(true);
	   ds.setConfig(sqlCon);
   	   String query1 = "DELETE FROM fts_Color WHERE fk_userID = '" + userID + 
   			   "' AND color = '" + color.getRGB() + "'";
   	   try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    int rv = stmt.executeUpdate( query1 );
   		    System.out.println( "DeleteFTS_Color() returned " + rv );
   	   } catch ( SQLException e ) {
   		    e.printStackTrace();
   	   }
   	   sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   }
   
   /**
    * Selects all FTS_Color within the Database
    * 
    * @param User_Account
    * @return ArrayList<Color>
    */
   public ArrayList<Color> SelectAllFTS_Color(int userID){
	   sqlCon.enforceForeignKeys(true);
       ds.setConfig(sqlCon);
	   ArrayList<Color> allColors = new ArrayList<Color>();
	   String query1 = "SELECT * FROM fts_Color WHERE fk_userID = '" + userID + "'";
   		try ( Connection conn = ds.getConnection();
		    Statement stmt = conn.createStatement(); ) {
		    ResultSet rs = stmt.executeQuery( query1 );
		    if (rs.isClosed()) {
		    	return allColors;
		    }
		    while (rs.next()){
    		allColors.add(new Color(rs.getInt("color")));
		    }		    
   		}catch ( SQLException e ) {
			e.printStackTrace();
		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);	   
	   return allColors;
   }

   /**
    * Primarily used for JUNIT testing, deletes the FTS_Colors table
    */
   public void DeleteAllFTS_Colors() {
	   String query1 = "DROP TABLE IF EXISTS 'fts_Color'";
	   try ( Connection conn = this.ds.getConnection();
	   		    Statement stmt = conn.createStatement(); ) {
			    int rv = stmt.executeUpdate( query1 );
			    System.out.println( "DeleteAllFTSColors() returned " + rv );
	   } catch ( SQLException e ) {
				e.printStackTrace();
	   }   
	   }
   
   /**
    ******* END OF FTS_Color Add, Delete, SelectAll, & Drop *******
    */

   /**
    ******* START OF WBLIST Add, Delete, SelectAll, & Drop *******
    */
   
   /**
    * Add a new WBList to the database.
    * @param Color, User_Account
    */
   public void AddWBlist(String ProcessName, boolean white_Or_Black, int userID) {
   	sqlCon.enforceForeignKeys(true);
    ds.setConfig(sqlCon);
   	String query1 = "INSERT INTO wbList " +
   			"(fk_userID, processName, white_Or_Black) Values ( '" +
   			userID + "', '" +
   			ProcessName.replaceAll("'", "''") + "', '" +
   			white_Or_Black+ "')";
   	try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    int rv = stmt.executeUpdate( query1 );
   		    System.out.println( "AddWBList() returned " + rv );
   		} catch ( SQLException e ) {
   		    //gets called when a task is passed in that isn't in the task table.
   			e.printStackTrace();
   		}
		sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   }
   
   /**
    * Delete a WB_List within the Database
    *              * 
    * @param String, boolean, User_Account
    */
   public void DeleteWB_List(String ProcessName, boolean white_Or_Black, int userID) {
	   sqlCon.enforceForeignKeys(true);
	   ds.setConfig(sqlCon);
   	   String query1 = "DELETE FROM wbList WHERE fk_userID = '" + userID + 
   			   "' AND processName = '" + ProcessName +
   	   		   "' AND white_Or_Black = '" + Boolean.toString(white_Or_Black) + "'";
   	   try ( Connection conn = ds.getConnection();
   		    Statement stmt = conn.createStatement(); ) {
   		    int rv = stmt.executeUpdate( query1 );
   		    System.out.println( "DeleteWB_List() returned " + rv );
   	   } catch ( SQLException e ) {
   		    e.printStackTrace();
   	   }
   	   sqlCon.enforceForeignKeys(false);
       ds.setConfig(sqlCon);
   }
   
   /**
    * Selects all WBlist processes within the Database depending upon the boolean
    * 
    * @param User_Account, boolean
    * @return ArrayList<String>
    */
   public ArrayList<String> SelectAllFromWBList(int userID, boolean white_Or_Black){
	   sqlCon.enforceForeignKeys(true);
       ds.setConfig(sqlCon);
	   ArrayList<String> Processes_On_List = new ArrayList<String>();
	   String query1 = "SELECT * FROM wbList WHERE fk_userID = '" + userID + 
			   "' AND white_Or_Black = '" + Boolean.toString(white_Or_Black) +"'";
   		try ( Connection conn = ds.getConnection();
		    Statement stmt = conn.createStatement(); ) {
		    ResultSet rs = stmt.executeQuery( query1 );
		    if (rs.isClosed()) {
		    	return Processes_On_List;
		    }
		    while (rs.next()){
    		Processes_On_List.add(new String(rs.getString("processName")));
		    }		    
   		}catch ( SQLException e ) {
			e.printStackTrace();
		}
		sqlCon.enforceForeignKeys(false);
        ds.setConfig(sqlCon);	   
	   return Processes_On_List;
   }

   /**
    * Primarily used for JUNIT testing, deletes the WbList table
    */
   public void DeleteAllWBList() {
	   String query1 = "DROP TABLE IF EXISTS 'wbList'";
	   try ( Connection conn = this.ds.getConnection();
	   		    Statement stmt = conn.createStatement(); ) {
			    int rv = stmt.executeUpdate( query1 );
			    System.out.println( "DeleteAllWBLists() returned " + rv );
	   } catch ( SQLException e ) {
				e.printStackTrace();
	   }   
	   }
   
   /**
    ******* END OF WBLIST Add, Delete, SelectAll, & Drop *******
    */
   
}
