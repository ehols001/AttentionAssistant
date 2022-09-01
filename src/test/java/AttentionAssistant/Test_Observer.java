package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;

//import net.didion.jwnl.dictionary.Dictionary;

public class Test_Observer {
	/**
	 * Objects used in test
	 */
	Observer defaultObserver;
	Observer nonDefaultObserver;
	Observer copyObserver;
	Task testActiveTask;
	DataBase db;
	Pomodoro_Timer pomodoro;
	Notification_System notificationSystem;
	
	@BeforeEach
	void setup() throws IOException {
		db= new DataBase();
		db.DatabaseSetUp();
		
		//Observer object creation
		int testObserver_ID= 999;
		int testObserverScore= 100;
		int testThreshold= 100;
		Date testDT_Gathered= new Date(1220227200L * 1000);
		int testEyeScore= 100;
	
		defaultObserver= new Observer();
		nonDefaultObserver= new Observer(testObserver_ID, testObserverScore, testThreshold, testDT_Gathered, testEyeScore);
		copyObserver= new Observer(nonDefaultObserver);

		//Task object creation
		int testTaskID = 1;
		String testDescription = "bear migration";
		boolean testObservable = true;
		TaskStatus testStatus = TaskStatus.OPEN;
		String testName = "This is a test Name";
		Date testDate = new Date(1220227200L * 1000);
		boolean testPriority = true;

		testActiveTask = new Task(testTaskID, testDescription, testObservable, testStatus, testName, testDate, testPriority);
	
		pomodoro = new Pomodoro_Timer();
	
		notificationSystem= new Notification_System(1, db);
	
	}
	
    @Test
    @DisplayName("<Observer> Default Constructor")
    void ObserverDefaultConstructor() {

        /**
         *  Make sure the Observer ObserverID is 0 for the default constructor
         */
        assertEquals(0, defaultObserver.getObserverID(), 
        "Default constructor Observer ObserverID should be 0. Returned: " + Integer.toString(defaultObserver.getObserverID()));
        
        /**
         *  Make sure the Observer ObserverScore is 0 for the default constructor
         */
        assertEquals(0 , defaultObserver.getObserverScore(), 
        "Default constructor task.description should be empty. Returned: " + defaultObserver.getThreshold());
        
        /**
         *  Make sure the Observer Threshold is set to 0 for the default constructor
         */
        assertEquals(0 , defaultObserver.getThreshold(), 
        "Default constructor task.observable should be false. Returned: " + defaultObserver.getThreshold());
        
        assertEquals(0, defaultObserver.getDefaultEyeScore(),
        "Default constructor task.defaultEyeScore should be 0. Returned: " + defaultObserver.getDefaultEyeScore());
 
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Observer> Parameter Constructor")
    void ObserverParameterConstructor() {

        /**
         *  Make sure the Observer ObserverID is 999 for the Parameter constructor
         */
        assertEquals(999, nonDefaultObserver.getObserverID(), 
        "Parameter constructor Observer.ObserverID should be 999. Returned: " + Integer.toString(nonDefaultObserver.getObserverID()));

        /**
         *  Make sure the Observer ObserverScore is 100 for the Parameter constructor
         */
        assertEquals(100 , nonDefaultObserver.getObserverScore(), 
        "Parameter constructor Observer.ObserverScore should be 100. Returned: " + nonDefaultObserver.getObserverScore());
        
        /**
         *  Make sure the Observer Threshold is set to 100 for the Parameter constructor
         */
        assertEquals(100 , nonDefaultObserver.getThreshold(), 
        "Parameter constructor Observer.Threshold should be 100. Returned: " + nonDefaultObserver.getThreshold());

        /**
         *  Make sure the Observer dT_Gathered is set to Date(1220227200L * 1000) for the Parameter constructor
         */
        assertEquals(new Date(1220227200L * 1000) , nonDefaultObserver.getDTGathered(), 
        "Parameter constructor Observer.dt_Gathered should be Sun Aug 31 20:00:00 EDT 2008 Returned: " + nonDefaultObserver.getDTGathered());

        /**
         *  Make sure the Observer DefaultEyeScore is 100 for the Parameter constructor
         */
        assertEquals(100 , nonDefaultObserver.getDefaultEyeScore(), 
        "Parameter constructor Observer.DefaultEyeScore should be 100. Returned: " + nonDefaultObserver.getDefaultEyeScore());
        
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Observer> Copy Constructor")
    void ObserverCopyConstructor() {

        /**
         *  Make sure the Observer ObserverID is 999 for the Copy constructor
         */
        assertEquals(999, copyObserver.getObserverID(), 
        "Copy constructor Observer.ObserverID should be 999. Returned: " + Integer.toString(copyObserver.getObserverID()));

        /**
         *  Make sure the Observer ObserverScore is 100 for the Copy constructor
         */
        assertEquals(100 , copyObserver.getObserverScore(), 
        "Copy constructor Observer.ObserverScore should be \"This is a test description\". Returned: " + copyObserver.getObserverScore());
        
        /**
         *  Make sure the Observer Threshold is set to 100 for the Copy constructor
         */
        assertEquals(100 , copyObserver.getThreshold(), 
        "Copy constructor Observer.Threshold should be 100. Returned: " + copyObserver.getThreshold());

        /**
         *  Make sure the Observer dT_Gathered is set to Date(1220227200L * 1000) for the Copy constructor
         */
        assertEquals(new Date(1220227200L * 1000) , copyObserver.getDTGathered(), 
        "Copy constructor Observer.dt_Gathered should be Sun Aug 31 20:00:00 EDT 2008 Returned: " + copyObserver.getDTGathered());

        /**
         *  Make sure the Observer DefaultEyeScore is 100 for the Copy constructor
         */
        assertEquals(100 , copyObserver.getDefaultEyeScore(), 
        "Parameter constructor Observer.DefaultEyeScore should be 100. Returned: " + copyObserver.getDefaultEyeScore());

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Observer> SetObserver_ID")
    void observerSetObserverID() {
        copyObserver.setObserverID(998);
    	assertEquals(998, copyObserver.getObserverID(), 
    	"Copy constructor Observer.ObserverID should be 998. Returned: " + Integer.toString(copyObserver.getObserverID()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Observer> SetObserverScore")
    void observerSetObserverScore() {
        copyObserver.setObserverScore(99);
    	assertEquals(99, copyObserver.getObserverScore(), 
    	"Copy constructor Observer.ObserverScore should be 99. Returned: " + Integer.toString(copyObserver.getObserverScore()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Observer> SetObserverThreshold")
    void observerSetThreshold() {
        copyObserver.setThreshold(99);
    	assertEquals(99, copyObserver.getThreshold(), 
    	"Copy constructor Observer.Threshold should be 99. Returned: " + Integer.toString(copyObserver.getThreshold()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Observer> SetObserverDTGathered")
    void observerSetDTGathered() {
        copyObserver.setDTGathered(new Date(1220227202L * 1000));
    	assertEquals(new Date(1220227202L * 1000), copyObserver.getDTGathered(), 
    	"Copy constructor Observer.dT_Gathered should be Sun Aug 31 20:00:02 EDT 2008. Returned: " + copyObserver.getDTGathered());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Observer> SetObserverThreshold")
    void observerSetDefaultEyeScore() {
        copyObserver.setDefaultEyeScore(99);
    	assertEquals(99, copyObserver.getDefaultEyeScore(), 
    	"Copy constructor Observer.defaultEyeScore should be 99. Returned: " + Integer.toString(copyObserver.getDefaultEyeScore()));
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Test
    @DisplayName("<Observer> toString")
    void observerToString() {
        String String1 = "Observer ID= 999 Observer Score= 100 Threshold= 100 Date Time Gathered= Sun Aug 31 20:00:00 EDT 2008";
        
        assertEquals(String1, nonDefaultObserver.toString(), "String1 should be set to Observer ID= 999 Observer Score= 100 Threshold= 100 Date Time Gathered= Sun Aug 31 20:00:00 EDT 2008 but instead returned: " + nonDefaultObserver.toString());
    }    	

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * This is not how I want to fully implement this test, but for the time being this will be testing:
     * setUpDict,
     * setDictionary,
     * getDictionary. -jmitchel2
     
    @Test
    @DisplayName("<Observer> setUpDict")
    void observerSetUpDict() {
    	Dictionary tempDict = defaultObserver.setUpDict();

    	defaultObserver.setDictionary(tempDict);

    	assertEquals(tempDict, defaultObserver.getDictionary(), "tempDict should be = to defaultObserver dict");
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Test
    @DisplayName("<Observer> filterTaskDescription")
    void observerFilterTaskDescription() {
    	Date testTaskDate = new Date(1220227200L * 1000);
    	testActiveTask = new Task(999, "This is a test description", true, TaskStatus.OPEN,
											"This is a test Name", testTaskDate, true);
    	ArrayList<String> testTaskWords = defaultObserver.filterTaskDescription(testActiveTask);
    	assertEquals("This", testTaskWords.get(0), "Expected: This | Actual: " + testTaskWords.get(0));
    	assertEquals("test", testTaskWords.get(1), "Expected: test | Actual: " + testTaskWords.get(1));
    	assertEquals("description", testTaskWords.get(2), "Expected: description | Actual: " + testTaskWords.get(2));
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Test
    @DisplayName("<Observer> removeDuplicateKeywords")
    void observerRemoveDuplicateKeywords() {
    	ArrayList<String> testKeywords = new ArrayList<String>();
    	testKeywords.add("test");
    	testKeywords.add("history");
    	testKeywords.add("test");
    	testKeywords.add("history");
    	testKeywords.add("paper");
    	
    	ArrayList<String> newTestKeywords = defaultObserver.removeDuplicateKeywords(testKeywords);
    	assertEquals("test", newTestKeywords.get(0), "Expected: test | Actual: " + newTestKeywords.get(0));
    	assertEquals("history", newTestKeywords.get(1), "Expected: history | Actual: " + newTestKeywords.get(1));
    	assertEquals("paper", newTestKeywords.get(2), "Expected: paper | Actual: " + newTestKeywords.get(2));
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    @Test
    @DisplayName("<Observer> setKeywordSynonyms")
    void observerSetKeywordSynonyms() throws IOException {
    	URL location =  new File("./src/main/resources/dict").toURI().toURL();
    	IDictionary dict = new Dictionary(location);
		dict.open();
		
		ArrayList<String> testKeywords = new ArrayList<String>();
		String[] testSynonyms = {"trial", "test", "tryout", "examination", "exam", "run", "prove", 
				"try", "examine", "essay", "screen", "quiz", "history", "account", "chronicle", 
				"story", "paper", "composition", "report", "theme", "newspaper", "wallpaper"};
		
		ArrayList<String> testTaskWords = new ArrayList<String>();
		testTaskWords.add("test");
		testTaskWords.add("history"); 
		testTaskWords.add("paper");
		
		defaultObserver.setKeywordSynonyms(dict, testTaskWords, testKeywords);
		defaultObserver.removeDuplicateKeywords(testKeywords);
		
		for(int i = 0; i < testSynonyms.length; i++) {
			assertEquals(testSynonyms[i], testKeywords.get(i), "Expected: " + testSynonyms[i] + " | Actual: " + testKeywords.get(i));
		}
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    
    //Tests the Observer's monitor function
    @Test
    @DisplayName("<Observer> monitor function")
    void observerMonitor() throws IOException {
    	notificationSystem.db= db;
    	Observer monitorObserver= new Observer(defaultObserver); 
    	monitorObserver.monitor(testActiveTask, db, notificationSystem, pomodoro);
    	System.out.println(monitorObserver.getObserverScore());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}