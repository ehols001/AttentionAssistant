package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test_Parent_Account {

	Parent_Account defaultParent;
	Parent_Account nonDefaultParent;
	Parent_Account copyParent;
	
	@BeforeEach
	void setup() {
		int testParentID= 999;
		String testParentUsername= "TestParentUser123";
		String testParentPassword= "TestParentPass123";
		int testParentSQ_Key = 40;
		String testParentSQ_Answer = "TestSecurityQuestionAnswer";
		int testParentSQ_Key2 = 40;
		String testParentSQ_Answer2 = "TestSecurityQuestionAnswer2";

		defaultParent= new Parent_Account();
		nonDefaultParent= new Parent_Account(testParentID, testParentUsername, testParentPassword, testParentSQ_Key, testParentSQ_Answer,testParentSQ_Key2,testParentSQ_Answer2);
		copyParent= new Parent_Account(nonDefaultParent);
	}

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> Default Constructor")
    void ParentDefaultConstructor() {
    
        /**
         *  Make sure the Parent_Account ParentID is 0 for the default constructor
         */
        assertEquals(0, defaultParent.getParentID(), 
        "Default constructor Parent_Account.ParentID should be 0. Returned: " + Integer.toString(defaultParent.getParentID()));

        /**
         *  Make sure the Parent_Account username is \"\" for the default constructor
         */
        assertEquals("", defaultParent.getUsername(), 
        "Default constructor Parent_Account.username should be \"\". Returned: " + defaultParent.getUsername());

        /**
         *  Make sure the Parent_Account password is \"\" for the default constructor
         */
        assertEquals("", defaultParent.getPassword(), 
        "Default constructor Parent_Account.password should be \"\". Returned: " + defaultParent.getPassword());
        /**
         *  Make sure the Parent_Account SQ_Key is 0 for the default constructor
         */
        assertEquals(0, defaultParent.getSQ_Key(), 
        "Default constructor Parent_Account.SQ_Key should be 0. Returned: " + Integer.toString(defaultParent.getSQ_Key()));
        /**
         *  Make sure the Parent_Account password is \"\" for the default constructor
         */
        assertEquals("", defaultParent.getSQ_Answer(), 
        "Default constructor Parent_Account.SQ_Answer should be \"\". Returned: " + defaultParent.getSQ_Answer());
        /**
         *  Make sure the Parent_Account SQ_Key is 0 for the default constructor
         */
        assertEquals(0, defaultParent.getSQ_Key2(), 
        "Default constructor Parent_Account.SQ_Key2 should be 0. Returned: " + Integer.toString(defaultParent.getSQ_Key2()));
        /**
         *  Make sure the Parent_Account password is \"\" for the default constructor
         */
        assertEquals("", defaultParent.getSQ_Answer2(), 
        "Default constructor Parent_Account.SQ_Answer2 should be \"\". Returned: " + defaultParent.getSQ_Answer2());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> Parameter Constructor")
    void ParentParameterConstructor() {
    
        /**
         *  Make sure the Parent_Account ParentID is 999 for the Parameter constructor
         */
        assertEquals(999, nonDefaultParent.getParentID(), 
        "Parameter constructor Parent_Account.ParentID should be 999. Returned: " + Integer.toString(nonDefaultParent.getParentID()));

        /**
         *  Make sure the Parent_Account username is \"TestParentUser123\" for the Parameter constructor
         */
        assertEquals("TestParentUser123", nonDefaultParent.getUsername(), 
        "Parameter constructor Parent_Account.username should be \"TestParentUser123\". Returned: " + nonDefaultParent.getUsername());

        /**
         *  Make sure the Parent_Account password is \"TestParentPass123\" for the Parameter constructor
         */
        assertEquals("TestParentPass123", nonDefaultParent.getPassword(), 
        "Parameter constructor Parent_Account.password should be \"TestParentPass123\". Returned: " + nonDefaultParent.getPassword());
        /**
         *  Make sure the Parent_Account Security Question Key is 40 for the Parameter constructor
         */
        assertEquals(40, nonDefaultParent.getSQ_Key(), 
        "Parameter constructor Parent_Account.SQ_Key should be 40. Returned: " + nonDefaultParent.getSQ_Key());
        /**
         *  Make sure the Parent_Account Security Question Answer is \"TestSecurityQuestionAnswer\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer", nonDefaultParent.getSQ_Answer(), 
        "Parameter constructor Parent_Account.SQ_Answer should be \"TestSecurityQuestionAnswer\". Returned: " + nonDefaultParent.getSQ_Answer());
        /**
         *  Make sure the Parent_Account Security Question Key is 40 for the Parameter constructor
         */
        assertEquals(40, nonDefaultParent.getSQ_Key2(), 
        "Parameter constructor Parent_Account.SQ_Key2 should be 40. Returned: " + nonDefaultParent.getSQ_Key2());
        /**
         *  Make sure the Parent_Account Security Question Answer is \"TestSecurityQuestionAnswer\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer2", nonDefaultParent.getSQ_Answer2(), 
        "Parameter constructor Parent_Account.SQ_Answer2 should be \"TestSecurityQuestionAnswer2\". Returned: " + nonDefaultParent.getSQ_Answer2());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> Copy Constructor")
    void ParentCopyConstructor() {
    
        /**
         *  Make sure the Parent_Account ParentID is 999 for the Copy constructor
         */
        assertEquals(999, copyParent.getParentID(), 
        "Copy constructor Parent_Account.ParentID should be 999. Returned: " + Integer.toString(copyParent.getParentID()));

        /**
         *  Make sure the Parent_Account username is \"TestParentUser123\" for the Copy constructor
         */
        assertEquals("TestParentUser123", copyParent.getUsername(), 
        "Copy constructor Parent_Account.username should be \"TestParentUser123\". Returned: " + copyParent.getUsername());

        /**
         *  Make sure the Parent_Account password is \"TestPass123\" for the Copy constructor
         */
        assertEquals("TestParentPass123", copyParent.getPassword(), 
        "Copy constructor Parent_Account.password should be \"TestParentPass123\". Returned: " + copyParent.getPassword());
        /**
         *  Make sure the Parent_Account Security Question Key is 40 for the Parameter constructor
         */
        assertEquals(40, copyParent.getSQ_Key(), 
        "Parameter constructor Parent_Account.SQ_Key should be 40. Returned: " + copyParent.getSQ_Key());
        /**
         *  Make sure the Parent_Account Security Question Answer is \"TestSecurityQuestionAnswer\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer", copyParent.getSQ_Answer(), 
        "Parameter constructor Parent_Account.SQ_Answer should be \"TestSecurityQuestionAnswer\". Returned: " + copyParent.getSQ_Answer());
        /**
         *  Make sure the Parent_Account Security Question Key is 40 for the Parameter constructor
         */
        assertEquals(40, copyParent.getSQ_Key2(), 
        "Parameter constructor Parent_Account.SQ_Key2 should be 40. Returned: " + copyParent.getSQ_Key2());
        /**
         *  Make sure the Parent_Account Security Question Answer is \"TestSecurityQuestionAnswer\" for the Parameter constructor
         */
        assertEquals("TestSecurityQuestionAnswer2", copyParent.getSQ_Answer2(), 
        "Parameter constructor Parent_Account.SQ_Answer2 should be \"TestSecurityQuestionAnswer2\". Returned: " + copyParent.getSQ_Answer2());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> SetUserID")
    void ParentSetUserID() {
    	
    	copyParent.setParentID(998);
    	assertEquals(998, copyParent.getParentID(), "copyParent userID should be set to 998 but instead returned: " + Integer.toString(copyParent.getParentID()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> SetUsername")
    void ParentSetUsername() {
    	
    	copyParent.setUsername("CopyParentUser123");
    	assertEquals("CopyParentUser123", copyParent.getUsername(), "copyParent username should be set to \"CopyParentUser123\" but instead returned: " + copyParent.getUsername());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> SetPassword")
    void ParentSetPassword() {
    	
    	copyParent.setPassword("CopyParentPass123");
    	assertEquals("CopyParentPass123", copyParent.getPassword(), "copyParent password should be set to \"CopyParentPass123\" but instead returned: " + copyParent.getPassword());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> SQ_Key")
    void ParentSetSQ_Key() {
    	
    	copyParent.setSQ_Key(40);
    	assertEquals(40, copyParent.getSQ_Key(), "copyParent SQ_Key should be set to 40 but instead returned: " +  Integer.toString(copyParent.getSQ_Key()));
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> SQ_Answer")
    void ParentSetSQ_Answer() {
    	
    	copyParent.setSQ_Answer("TestSecurityQuestionAnswer");
    	assertEquals("TestSecurityQuestionAnswer", copyParent.getSQ_Answer(), "copyParent SQ_Answer should be set to \"TestSecurityQuestionAnswer\" but instead returned: " + copyParent.getSQ_Answer());
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> SQ_Key2")
    void ParentSetSQ_Key2() {
    	
    	copyParent.setSQ_Key(40);
    	assertEquals(40, copyParent.getSQ_Key2(), "copyParent SQ_Key2 should be set to 40 but instead returned: " +  Integer.toString(copyParent.getSQ_Key2()));
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> SQ_Answer2")
    void ParentSetSQ_Answer2() {
    	
    	copyParent.setSQ_Answer2("TestSecurityQuestionAnswer2");
    	assertEquals("TestSecurityQuestionAnswer2", copyParent.getSQ_Answer2(), "copyParent SQ_Answer2 should be set to \"TestSecurityQuestionAnswer2\" but instead returned: " + copyParent.getSQ_Answer2());
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Parent_Account> toString")
    void ParentToString() {
        String String1 = "Parent ID= 999 Username= TestParentUser123 Password= TestParentPass123 SQ_Key= 40 SQ_Answer= TestSecurityQuestionAnswer SQ_Key2= 40 SQ_Answer2= TestSecurityQuestionAnswer2";
        
        assertEquals(String1, nonDefaultParent.toString(), "String1 should be set to \"Parent ID= 999 Username= TestParentUser123 Password= TestParentPass123 SQ_Key= 40 SQ_Answer= TestSecurityQuestionAnswer SQ_Key2= 40 SQ_Answer2= TestSecurityQuestionAnswer2\" but instead returned: " + nonDefaultParent.toString());
    }    	

}