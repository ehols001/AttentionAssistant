package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test_Task {
	/**
	 * Objects used in test
	 */
	Task defaultTask;
	Task nonDefaultTask;
	Task copyTask;
	
	@BeforeEach
	void setup() {
	int testTaskID = 999;
	String testDescription = "This is a test description";
	boolean testObservable = true;
	TaskStatus testStatus = TaskStatus.OPEN;
	String testName = "This is a test Name";
	Date testDate = new Date(1220227200L * 1000);
	boolean testPriority = true;
	
	defaultTask= new Task();
	
	nonDefaultTask = new Task(testTaskID, testDescription, testObservable, testStatus, testName, testDate, testPriority);
	
	copyTask = new Task(nonDefaultTask);
	
	}
	
    @Test
    @DisplayName("<Task> Default Constructor")
    void TaskDefaultConstructor() {

        /**
         *  Make sure the Task taskID is 0 for the default constructor
         */
        assertEquals(0, defaultTask.getTaskID(), 
        "Default constructor task.taskID should be 0. Returned: " + Integer.toString(defaultTask.getTaskID()));
        
        /**
         *  Make sure the Task description is empty for the default constructor
         */
        assertEquals("" , defaultTask.getDescription(), 
        "Default constructor task.description should be empty. Returned: " + defaultTask.getDescription());
        
        /**
         *  Make sure the Task observable is set to false for the default constructor
         */
        assertEquals(false , defaultTask.getObservable(), 
        "Default constructor task.observable should be false. Returned: " + String.valueOf(defaultTask.getObservable()));

        /**
         *  Make sure the Task status is set to CLOSED for the default constructor
         */
        assertEquals(TaskStatus.CLOSED , defaultTask.getStatus(), 
        "Default constructor task.status should be CLOSED. Returned: " + defaultTask.getStatus());
 
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> Parameter Constructor")
    void TaskParameterConstructor() {

        /**
         *  Make sure the Task taskID is 999 for the Parameter constructor
         */
        assertEquals(999, nonDefaultTask.getTaskID(), 
        "Parameter constructor task.taskID should be 999. Returned: " + Integer.toString(nonDefaultTask.getTaskID()));
        
        /**
         *  Make sure the Task description is "This is a test description" for the Parameter constructor
         */
        assertEquals("This is a test description" , nonDefaultTask.getDescription(), 
        "Parameter constructor task.description should be \"This is a test description\". Returned: " + nonDefaultTask.getDescription());
        
        /**
         *  Make sure the Task observable is set to true for the Parameter constructor
         */
        assertEquals(true , nonDefaultTask.getObservable(), 
        "Parameter constructor task.observable should be true. Returned: " + String.valueOf(nonDefaultTask.getObservable()));

        /**
         *  Make sure the Task status is set to OPEN for the Parameter constructor
         */
        assertEquals(TaskStatus.OPEN , nonDefaultTask.getStatus(), 
        "Parameter constructor task.status should be OPEN. Returned: " + nonDefaultTask.getStatus());
 
        /**
         *  Make sure the Task name is set to "this is a test name" for the Parameter constructor
         */
        assertEquals("This is a test Name" , copyTask.getTaskName(), 
        "Parameter constructor task.name should be This is a test Name. Returned: " + nonDefaultTask.getTaskName());

        /**
         *  Make sure the Task dueDate is set to Date(1220227200L * 1000) for the Parameter constructor
         */
        assertEquals(new Date(1220227200L * 1000) , copyTask.getDueDate(), 
        "Parameter constructor task.date should be Sun Aug 31 20:00:00 EDT 2008 Returned: " + nonDefaultTask.getDueDate());

        /**
         *  Make sure the Task Priority is set to true for the Parameter constructor
         */
        assertEquals(true , copyTask.getPriority(), 
        "Parameter constructor task.priority should be true Returned: " + nonDefaultTask.getPriority());

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> Parameter Constructor")
    void TaskCopyConstructor() {

        /**
         *  Make sure the Task taskID is 999 for the Copy constructor
         */
        assertEquals(999, copyTask.getTaskID(), 
        "Copy constructor task.taskID should be 999. Returned: " + Integer.toString(copyTask.getTaskID()));
        
        /**
         *  Make sure the Task description is "This is a test description" for the Copy constructor
         */
        assertEquals("This is a test description" , copyTask.getDescription(), 
        "Copy constructor task.description should be \"This is a test description\". Returned: " + copyTask.getDescription());
        
        /**
         *  Make sure the Task observable is set to true for the Copy constructor
         */
        assertEquals(true , copyTask.getObservable(), 
        "Copy constructor task.observable should be true. Returned: " + String.valueOf(copyTask.getObservable()));

        /**
         *  Make sure the Task status is set to OPEN for the Copy constructor
         */
        assertEquals(TaskStatus.OPEN , copyTask.getStatus(), 
        "Copy constructor task.status should be OPEN. Returned: " + copyTask.getStatus());
 
        /**
         *  Make sure the Task name is set to "this is a test name" for the Copy constructor
         */
        assertEquals("This is a test Name" , copyTask.getTaskName(), 
        "Copy constructor task.name should be This is a test Name. Returned: " + copyTask.getTaskName());

        /**
         *  Make sure the Task dueDate is set to Date(1220227200L * 1000) for the Copy constructor
         */
        assertEquals(new Date(1220227200L * 1000) , copyTask.getDueDate(), 
        "Copy constructor task.date should be Sun Aug 31 20:00:00 EDT 2008 Returned: " + copyTask.getDueDate());

        /**
         *  Make sure the Task Priority is set to true for the Copy constructor
         */
        assertEquals(true , copyTask.getPriority(), 
        "Copy constructor task.priority should be true Returned: " + copyTask.getPriority());

    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> SetTaskID")
    void taskSetTaskID() {
    	
    	copyTask.setTaskID(998);
    	assertEquals(998, copyTask.getTaskID(), "copyTask taskID should be set to 998 but instead returned: " + Integer.toString(copyTask.getTaskID()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> SetDescription")
    void taskSetDescription() {
    	
    	copyTask.setDescription("I am still a test Description");
    	assertEquals("I am still a test Description", copyTask.getDescription(), "copyTask description should be set to \"I am still a test\" but instead returned: " + copyTask.getDescription());
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> SetObservable")
    void taskSetObservable() {
        	
        copyTask.setObservable(false);
        assertEquals(false, copyTask.getObservable(), "copyTask observable should be set to false but instead returned: " + String.valueOf(copyTask.getObservable()));
    }    	

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> SetStatus")
    void taskSetStatus() {
        	
        copyTask.setStatus(TaskStatus.CLOSED);
        assertEquals(TaskStatus.CLOSED, copyTask.getStatus(), "copyTask status should be set to CLOSED but instead returned: " + copyTask.getStatus());
    }    	

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> SetName")
    void taskSetName() {
        	
        copyTask.setTaskName("I am still a test Name");
        assertEquals("I am still a test Name", copyTask.getTaskName(), "copyTask name should be set to I am still a test Name but instead returned: " + copyTask.getTaskName());
    }    	

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> SetDueDate")
    void taskSetDueDate() {
        	
        copyTask.setDueDate(new Date(1220227202L * 1000));
        assertEquals(new Date(1220227202L * 1000), copyTask.getDueDate(), "copyTask dueDate should be set to Sun Aug 31 20:00:02 EDT 2008 but instead returned: " + copyTask.getDueDate());
    }    	

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Task> SetPriority")
    void taskSetPriority() {
        	
        copyTask.setPriority(false);
        assertEquals(false, copyTask.getPriority(), "copyTask dueDate should be set to false but instead returned: " + copyTask.getPriority());
    }    	

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

     @Test
     @DisplayName("<Task> toString")
     void taskToString() {
         String String1 = "Task ID= 999 Priority= true Name= This is a test Name Description= This is a test description Due Date= Sun Aug 31 20:00:00 EDT 2008 Observable= true Status= OPEN";
         
         assertEquals(String1, nonDefaultTask.toString(), "String1 should be set to Task ID= 999 Priority= true Name= This is a test Name Description= This is a test description Due Date= Sun Aug 31 20:00:00 EDT 2008 Observable= true Status= OPEN but instead returned: " + nonDefaultTask.toString());
     }    	

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}