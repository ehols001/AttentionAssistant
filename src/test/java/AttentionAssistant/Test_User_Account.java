package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test_User_Account {

	User_Account defaultUser;
	User_Account nonDefaultUser;
	User_Account copyUser;
	
	@BeforeEach
	void setup() {
		int testUserID= 999;
		String testUserUsername= "TestUser123";
		String testUserPassword= "TestPass123";
		String testUserName= "TestName123";
		int testUserSQ_Key = 40;
		String testUserSQ_Answer = "TestSecurityQuestionAnswer";
		int testUserSQ_Key2 = 40;
		String testUserSQ_Answer2 = "TestSecurityQuestionAnswer2";
		
		defaultUser= new User_Account();
		nonDefaultUser= new User_Account(testUserID, testUserUsername, testUserPassword, testUserName, testUserSQ_Key, testUserSQ_Answer,testUserSQ_Key2,testUserSQ_Answer2);
		copyUser= new User_Account(nonDefaultUser);
	}

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> Default Constructor")
    void UserDefaultConstructor() {
    
        /**
         *  Make sure the User_Account userID is 0 for the default constructor
         */
        assertEquals(0, defaultUser.getUserID(), 
        "Default constructor User_Account.UserID should be 0. Returned: " + Integer.toString(defaultUser.getUserID()));

        /**
         *  Make sure the User_Account username is \"\" for the default constructor
         */
        assertEquals("", defaultUser.getUsername(), 
        "Default constructor User_Account.username should be \"\". Returned: " + defaultUser.getUsername());

        /**
         *  Make sure the User_Account password is \"\" for the default constructor
         */
        assertEquals("", defaultUser.getPassword(), 
        "Default constructor User_Account.password should be \"\". Returned: " + defaultUser.getPassword());
        /**
         *  Make sure the User_Account SQ_Key is 0 for the default constructor
         */
        assertEquals(0, defaultUser.getSQ_Key(), 
        "Default constructor User_Account.SQ_Key should be 0. Returned: " + Integer.toString(defaultUser.getSQ_Key()));
        /**
         *  Make sure the User_Account SQ_Answer is \"\" for the default constructor
         */
        assertEquals("", defaultUser.getSQ_Answer(), 
        "Default constructor User_Account.SQ_Answer should be \"\". Returned: " + defaultUser.getSQ_Answer());
        /**
         *  Make sure the User_Account SQ_Key2 is 0 for the default constructor
         */
        assertEquals(0, defaultUser.getSQ_Key2(), 
        "Default constructor User_Account.SQ_Key2 should be 0. Returned: " + Integer.toString(defaultUser.getSQ_Key2()));
        /**
         *  Make sure the User_Account SQ_Answer2 is \"\" for the default constructor
         */
        assertEquals("", defaultUser.getSQ_Answer2(), 
        "Default constructor User_Account.SQ_Answer2 should be \"\". Returned: " + defaultUser.getSQ_Answer2());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> Parameter Constructor")
    void UserParameterConstructor() {
    
        /**
         *  Make sure the User_Account userID is 999 for the Parameter constructor
         */
        assertEquals(999, nonDefaultUser.getUserID(), 
        "Parameter constructor User_Account.UserID should be 999. Returned: " + Integer.toString(nonDefaultUser.getUserID()));

        /**
         *  Make sure the User_Account username is \"TestUser123\" for the Parameter constructor
         */
        assertEquals("TestUser123", nonDefaultUser.getUsername(), 
        "Parameter constructor User_Account.username should be \"TestUser123\". Returned: " + nonDefaultUser.getUsername());

        /**
         *  Make sure the User_Account password is \"TestPass123\" for the Parameter constructor
         */
        assertEquals("TestPass123", nonDefaultUser.getPassword(), 
        "Parameter constructor User_Account.password should be \"TestPass123\". Returned: " + nonDefaultUser.getPassword());
        /**
         *  Make sure the User_Account Security Question Key is 40 for the Parameter constructor
         */
        assertEquals(40, nonDefaultUser.getSQ_Key(), 
        "Parameter constructor User_Account.SQ_Key should be 40. Returned: " + nonDefaultUser.getSQ_Key());
        /**
         *  Make sure the User_Account Security Question Answer is \"TestSecurityQuestionAnswer\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer", nonDefaultUser.getSQ_Answer(), 
        "Parameter constructor User_Account.getSQ_Answer should be \"TestSecurityQuestionAnswer\". Returned: " + nonDefaultUser.getSQ_Answer());
        /**
         *  Make sure the User_Account Security Question Key 2 is 40 for the Parameter constructor
         */
        assertEquals(40, nonDefaultUser.getSQ_Key2(), 
        "Parameter constructor User_Account.SQ_Key2 should be 40. Returned: " + nonDefaultUser.getSQ_Key2());
        /**
         *  Make sure the User_Account Security Question Answer 2 is \"TestSecurityQuestionAnswer2\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer2", nonDefaultUser.getSQ_Answer2(), 
        "Parameter constructor User_Account.getSQ_Answer2 should be \"TestSecurityQuestionAnswer2\". Returned: " + nonDefaultUser.getSQ_Answer2());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> Copy Constructor")
    void UserCopyConstructor() {
    
        /**
         *  Make sure the User_Account userID is 999 for the Copy constructor
         */
        assertEquals(999, copyUser.getUserID(), 
        "Copy constructor User_Account.UserID should be 999. Returned: " + Integer.toString(copyUser.getUserID()));

        /**
         *  Make sure the User_Account username is \"TestUser123\" for the Copy constructor
         */
        assertEquals("TestUser123", copyUser.getUsername(), 
        "Copy constructor User_Account.username should be \"TestUser123\". Returned: " + copyUser.getUsername());

        /**
         *  Make sure the User_Account password is \"TestPass123\" for the Copy constructor
         */
        assertEquals("TestPass123", copyUser.getPassword(), 
        "Copy constructor User_Account.password should be \"TestPass123\". Returned: " + copyUser.getPassword());
        /**
         *  Make sure the User_Account Security Question Key is 40 for the Parameter constructor
         */
        assertEquals(40, copyUser.getSQ_Key(), 
        "Parameter constructor User_Account.SQ_Key should be 40. Returned: " + copyUser.getSQ_Key());
        /**
         *  Make sure the User_Account Security Question Answer is \"TestSecurityQuestionAnswer\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer", copyUser.getSQ_Answer(), 
        "Parameter constructor User_Account.getSQ_Answer should be \"TestSecurityQuestionAnswer\". Returned: " + copyUser.getSQ_Answer());
        /**
         *  Make sure the User_Account Security Question Key 2 is 40 for the Parameter constructor
         */
        assertEquals(40, copyUser.getSQ_Key2(), 
        "Parameter constructor User_Account.SQ_Key2 should be 40. Returned: " + copyUser.getSQ_Key2());
        /**
         *  Make sure the User_Account Security Question Answer 2 is \"TestSecurityQuestionAnswer2\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer2", copyUser.getSQ_Answer2(), 
        "Parameter constructor User_Account.getSQ_Answer2 should be \"TestSecurityQuestionAnswer2\". Returned: " + copyUser.getSQ_Answer2());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> SetUserID")
    void UserSetUserID() {
    	
    	copyUser.setUserID(998);
    	assertEquals(998, copyUser.getUserID(), "copyUser userID should be set to 998 but instead returned: " + Integer.toString(copyUser.getUserID()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> SetUsername")
    void UserSetUsername() {
    	
    	copyUser.setUsername("CopyUser123");
    	assertEquals("CopyUser123", copyUser.getUsername(), "copyUser username should be set to \"CopyUser123\" but instead returned: " + copyUser.getUsername());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> SetPassword")
    void UserSetPassword() {
    	
    	copyUser.setPassword("CopyPass123");
    	assertEquals("CopyPass123", copyUser.getPassword(), "copyUser password should be set to \"CopyPass123\" but instead returned: " + copyUser.getPassword());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> SQ_Key")
    void UserSetSQ_Key() {
    	
    	copyUser.setSQ_Key(40);
    	assertEquals(40, copyUser.getSQ_Key(), "copyUser SQ_Key should be set to 40 but instead returned: " +  Integer.toString(copyUser.getSQ_Key()));
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> SQ_Answer")
    void UserSetSQ_Answer() {
    	
    	copyUser.setPassword("TestSecurityQuestionAnswer");
    	assertEquals("TestSecurityQuestionAnswer", copyUser.getSQ_Answer(), "copyUser SQ_Answer should be set to \"TestSecurityQuestionAnswer\" but instead returned: " + copyUser.getSQ_Answer());
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Test
    @DisplayName("<User_Account> SQ_Answer2")
    void UserSetSQ_Answer2() {
    	
    	copyUser.setPassword("TestSecurityQuestionAnswer2");
    	assertEquals("TestSecurityQuestionAnswer2", copyUser.getSQ_Answer2(), "copyUser SQ_Answer2 should be set to \"TestSecurityQuestionAnswer2\" but instead returned: " + copyUser.getSQ_Answer2());
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<User_Account> SQ_Key2")
    void UserSetSQ_Key2() {
    	
    	copyUser.setSQ_Key2(40);
    	assertEquals(40, copyUser.getSQ_Key2(), "copyUser SQ_Key2 should be set to 40 but instead returned: " +  Integer.toString(copyUser.getSQ_Key2()));
    }
    

    @Test
    @DisplayName("<User_Account> toString")
    void UserToString() {
        String String1 = "User ID= 999 Username= TestUser123 Password= TestPass123 Name= TestName123 SQ_Key= 40 SQ_Answer= TestSecurityQuestionAnswer SQ_Key2= 40 SQ_Answer2= TestSecurityQuestionAnswer2";
        
        assertEquals(String1, nonDefaultUser.toString(), "String1 should be set to \"User ID= 999 Username= TestUser123 Password= TestPass123 Name= TestName123 SQ_Key= 40 SQ_Answer= TestSecurityQuestionAnswer SQ_Key2= 40 SQ_Answer2= TestSecurityQuestionAnswer2\" but instead returned: " + nonDefaultUser.toString());
    } 
    

}