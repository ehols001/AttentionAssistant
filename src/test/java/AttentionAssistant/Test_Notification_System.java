package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test_Notification_System {
	
	/**
	 * Objects used in test
	 */
	Notification defaultNotify;
	Notification nonDefaultNotify;
	Notification copyNotify;
	
	@BeforeEach
	void setup() {
		int testNotificationID= 999;
		String testType = "This is a test type";
		boolean testIgnored= true;
		Date testDT_Notification = new Date(1220227200L * 1000);

		defaultNotify= new Notification();
		
		nonDefaultNotify= new Notification(testNotificationID, testType, testIgnored, testDT_Notification);
		
		copyNotify= new Notification(nonDefaultNotify);
		
	}
		
    @Test
    @DisplayName("<Notification_System> Default Constructor")
    void Notification_SystemDefaultConstructor() {
    	
        /**
         *  Make sure the Notification_System notificationID is 0 for the default constructor
         */
        assertEquals(0, defaultNotify.getNotificationID(), 
        "Default constructor Notification_System.notificationID should be 0. Returned: " + Integer.toString(defaultNotify.getNotificationID()));
    	
        /**
         *  Make sure the Notification_System type is \"\" for the default constructor
         */
        assertEquals("", defaultNotify.getType(), 
        "Default constructor Notification_System.type should be \"\". Returned: " + defaultNotify.getType());
    	
        /**
         *  Make sure the Notification_System ignored is false for the default constructor
         */
        assertEquals(false, defaultNotify.getIgnored(), 
        "Default constructor Notification_System.ignored should be false. Returned: " + Boolean.toString(defaultNotify.getIgnored()));	
    }

    @Test
    @DisplayName("<Notification_System> Parameter Constructor")
    void Notification_SystemNonDefaultConstructor() {
    	
        /**
         *  Make sure the Notification_System notificationID is 999 for the nondefault constructor
         */
        assertEquals(999, nonDefaultNotify.getNotificationID(), 
        "NonDefault constructor Notification_System.notificationID should be 999. Returned: " + Integer.toString(nonDefaultNotify.getNotificationID()));
    	
        /**
         *  Make sure the Notification_System type is \"This is a test type\" for the nondefault constructor
         */
        assertEquals("This is a test type", nonDefaultNotify.getType(), 
        "NonDefault constructor Notification_System.type should be \"This is a test type\". Returned: " + nonDefaultNotify.getType());
    	
        /**
         *  Make sure the Notification_System ignored is true for the nondefault constructor
         */
        assertEquals(true, nonDefaultNotify.getIgnored(), 
        "NonDefault constructor Notification_System.ignored should be true. Returned: " + Boolean.toString(nonDefaultNotify.getIgnored()));
    	
        /**
         *  Make sure the Notification_System dT_Notification is new Date(1220227200L * 1000) for the nondefault constructor
         */
        assertEquals(new Date(1220227200L * 1000), nonDefaultNotify.getDT_Notification(), 
        "NonDefault constructor Notification_System.dT_Notification should be Sun Aug 31 20:00:00 EDT 2008. Returned: " + nonDefaultNotify.getDT_Notification().toString());
    }

    @Test
    @DisplayName("<Notification_System> Copy Constructor")
    void Notification_SystemCopyConstructor() {
    	
        /**
         *  Make sure the Notification_System notificationID is 999 for the copy constructor
         */
        assertEquals(999, copyNotify.getNotificationID(), 
        "Copy constructor Notification_System.notificationID should be 999. Returned: " + Integer.toString(copyNotify.getNotificationID()));
    	
        /**
         *  Make sure the Notification_System type is \"This is a test type\" for the copy constructor
         */
        assertEquals("This is a test type", copyNotify.getType(), 
        "Copy constructor Notification_System.type should be \"This is a test type\". Returned: " + copyNotify.getType());
    	
        /**
         *  Make sure the Notification_System ignored is true for the copy constructor
         */
        assertEquals(true, copyNotify.getIgnored(), 
        "Copy constructor Notification_System.ignored should be true. Returned: " + Boolean.toString(copyNotify.getIgnored()));
    	
        /**
         *  Make sure the Notification_System dT_Notification is new Date(1220227200L * 1000) for the copy constructor
         */
        assertEquals(new Date(1220227200L * 1000), copyNotify.getDT_Notification(), 
        "Copy constructor Notification_System.dT_Notification should be Sun Aug 31 20:00:00 EDT 2008. Returned: " + copyNotify.getDT_Notification().toString());
    }
    
    @Test
    @DisplayName("<Notification_System> SetNotificationID")
    void Notification_SystemSetNotificationID(){
    	copyNotify.setNotificationID(998);
    	assertEquals(998, copyNotify.getNotificationID(), "copyNotify notificationID should be set to 998 but instead returned: " + Integer.toString(copyNotify.getNotificationID()));
    }
    
    @Test
    @DisplayName("<Notification_System> SetType")
    void Notification_SystemSetType(){
    	copyNotify.setType("I am still a test type");
    	assertEquals("I am still a test type", copyNotify.getType(), "copyNotify type should be set to \"I am still a test type\" but instead returned: " + copyNotify.getType());
    }
    
    @Test
    @DisplayName("<Notification_System> SetIgnored")
    void Notification_SystemSetIgnored(){
    	copyNotify.setIgnored(false);
    	assertEquals(false, copyNotify.getIgnored(), "copyNotify ignored should be set to false but instead returned: " + Boolean.toString(copyNotify.getIgnored()));
    }
    
    @Test
    @DisplayName("<Notification_System> SetType")
    void Notification_SystemSetDT_Notification(){
    	copyNotify.setDT_Notification(new Date(1220227202L * 1000));
    	assertEquals(new Date(1220227202L * 1000), copyNotify.getDT_Notification(), "copyNotify dT_Notification should be set to Sun Aug 31 20:00:02 EDT 2008 but instead returned: " + copyNotify.getDT_Notification().toString());
    }

    @Test
    @DisplayName("<Notification_System> toString")
    void Notification_SystemToString(){
    	String String1 = "Notification ID= 999 Type= This is a test type Ignored= true Date and Time of Notification= Sun Aug 31 20:00:00 EDT 2008";
    	assertEquals(String1, nonDefaultNotify.toString(), "nonDefaultNotify toString should be set to \"Notification ID= 999 Type= This is a test type Ignored= true Date and Time of Notification= Sun Aug 31 20:00:00 EDT 2008\" but instead returned: " + copyNotify.toString());
    }

}