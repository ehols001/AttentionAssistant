package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test_Priority_Manager {
	
	/**
	 * Objects used in test
	 */
	
	Priority_Manager defaultPM;
	Priority_Manager nondefaultPM;
	Priority_Manager copyPM;
	
	int testUserID = 999;
	
	DataBase testDB; 
	
	@BeforeEach
	void setup() throws IOException {
	
		Color aa_grey = new Color(51,51,51);
		Color aa_purple = new Color(137,31,191);
		
		int testSelectedTask;
		int testHeight = 800;
		int testWidth = 650;
		int testRow;
		
		ArrayList<Task> testTask_List = null;
		
		defaultPM = new Priority_Manager(testUserID, testDB);
		
		nondefaultPM = new Priority_Manager(testUserID, testDB);
		
		//copyPM = new Priority_Manager(nondefaultPM);

	}
	
	@Test
    @DisplayName("<Priority_Manager> Default Constructor")
    void PriorityManagerDefaultConstructor() {
    	
    	/**
         *  Make sure the PM default height is 700 for the default constructor
         */
        assertEquals(700, defaultPM.getHeight(), 
        "Default constructor Priority_Manager.height should be 700. Returned: "
        + Integer.toString(defaultPM.getHeight()));
        
        /**
         *  Make sure the PM default width is 550 for the default constructor
         */
        assertEquals(550, defaultPM.getWidth(), 
        "Default constructor Priority_Manager.width should be 550. Returned: "
        + Integer.toString(defaultPM.getWidth()));
        
        /**
         *  Grab Active Default Active Task
         */
        Task testActiveTask1 = defaultPM.getActiveTask();
        
        /**
         *  Make sure the PM activeTask taskID is 0 for the default constructor
         */
        assertEquals(0, testActiveTask1.getTaskID(), 
        "Default constructor testActiveTask1.taskID should be 0. Returned: "
        + Integer.toString(testActiveTask1.getTaskID()));
        
        /**
         *  Make sure the activeTask description is empty for the default constructor
         */
        assertEquals("" , testActiveTask1.getDescription(), 
        "Default constructor testActiveTask1.description should be empty. Returned: "
        + testActiveTask1.getDescription());
        
        /**
         *  Make sure the activeTask observable is set to false for the default constructor
         */
        assertEquals(false , testActiveTask1.getObservable(), 
        "Default constructor testActiveTask1.observable should be false. Returned: "
        + String.valueOf(testActiveTask1.getObservable()));

        /**
         *  Make sure the activeTask status is set to CLOSED for the default constructor
         */
        assertEquals(TaskStatus.CLOSED , testActiveTask1.getStatus(), 
        "Default constructor testActiveTask1.status should be CLOSED. Returned: "
        + testActiveTask1.getStatus());
        
        /**
         *  Make sure the activeTask name is set to "" for the default constructor
         */
        assertEquals("" , testActiveTask1.getTaskName(), 
        "Parameter constructor task.name should be empty. Returned: "
        + testActiveTask1.getTaskName());
        
        /**
         *  Grab Active Default Task_List
         */
        ArrayList<Task> testTask_List1 = defaultPM.getTask_List();
        
        /**
         *  Make sure the Task_List is of size 0 for the default constructor
         */
        assertEquals(0, testTask_List1.size(), 
        "Default constructor testTask_List1 should be empty. Returned: "
        + Integer.toString(testTask_List1.size()));
        
    }
	
	@Test
    @DisplayName("<Priority_Manager> Parameter Constructor")
    void PriorityManagerParameterConstructor() {
    	
    	/**
         *  Make sure the PM default height is 700 for the parameter constructor
         */
        assertEquals(800, nondefaultPM.getHeight(), 
        "Parameter constructor Priority_Manager.height should be 800. Returned: "
        + Integer.toString(nondefaultPM.getHeight()));
        
        /**
         *  Make sure the PM default width is 550 for the parameter constructor
         */
        assertEquals(650, nondefaultPM.getWidth(), 
        "Paraemeter constructor Priority_Manager.width should be 650. Returned: "
        + Integer.toString(nondefaultPM.getWidth()));
        
        /**
         *  Grab Parameter Active Task
         */
        
        //nondefaultPM.setActiveTask(new Task(999, "This is a test description", true, TaskStatus.OPEN, "This is a test Name", new Date(1220227200L * 1000), true));
        Task testActiveTask2 = nondefaultPM.getActiveTask();
        
        /**
         *  Make sure the PM activeTask taskID is 0 for the parameter constructor
         */
        assertEquals(0, testActiveTask2.getTaskID(), 
        "Parameter constructor testActiveTask2.taskID should be 999. Returned: "
        + Integer.toString(testActiveTask2.getTaskID()));
        
        /**
         *  Make sure the activeTask description is empty for the parameter constructor
         */
        assertEquals("This is a test description" , testActiveTask2.getDescription(), 
        "Parameter constructor testActiveTask2.description should be empty. Returned: "
        + testActiveTask2.getDescription());
        
        /**
         *  Make sure the activeTask observable is set to false for the parameter constructor
         */
        assertEquals(true , testActiveTask2.getObservable(), 
        "Parameter constructor testActiveTask2.observable should be true. Returned: "
        + String.valueOf(testActiveTask2.getObservable()));

        /**
         *  Make sure the activeTask status is set to CLOSED for the parameter constructor
         */
        assertEquals(TaskStatus.OPEN , testActiveTask2.getStatus(), 
        "Parameter constructor testActiveTask2.status should be OPEN. Returned: "
        + testActiveTask2.getStatus());
        
        /**
         *  Make sure the activeTask dueDate is set to Date(1220227200L * 1000) for the Parameter constructor
         */
        assertEquals(new Date(1220227200L * 1000) , testActiveTask2.getDueDate(), 
        "Parameter constructor testActiveTask2.date should be Sun Aug 31 20:00:00 EDT 2008 Returned: "
        + testActiveTask2.getDueDate());

        /**
         *  Make sure the Task Priority is set to true for the Parameter constructor
         */
        assertEquals(true , testActiveTask2.getPriority(), 
        "Parameter constructor testActiveTask2.priority should be true Returned: "
        + testActiveTask2.getPriority());
        
        /**
         *  Grab Parameter Task_List
         */
        ArrayList<Task> testTask_List2 = nondefaultPM.getTask_List();
        testTask_List2.add(testActiveTask2);
        
        /**
         *  Make sure the Task_List is of size 1 for the parameter constructor
         */
        assertEquals(0, testTask_List2.size(), 
        "Default constructor testTask_List2 should have 1 entry. Returned: "
        + Integer.toString(testTask_List2.size()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*
    @Test
    @DisplayName("<Priority_Manager> Copy Constructor")
    void PriorityManagerCopyConstructor() {
    }
*/
	
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Test
    @DisplayName("<Priority_Manager> populateTaskList")
    void testPopulateTaskList() {
    	
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Priority_Manager> taskToObserve")
    void testTaskToObserve() {
    /**
     * place holder
     */
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*
    @Test
    @DisplayName("<Priority_Manager> open_PM")
    void testOpenPM() {
    
    //TODO Interface Testing... Research in progress
    
    }
*/
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Priority_Manager> deleteTask")
    void testDeleteTask() throws IOException {
    	
    	JTable testTable = null;
		ArrayList<Task> testTask_List = null;
		DefaultTableModel testModel = new DefaultTableModel(testTask_List.size(),0);
		
		defaultPM = new Priority_Manager(testUserID, testDB);
    	
    	//defaultPM.addTask(testUserID, testDB, testModel, testTable);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Priority_Manager> editTask")
    void testEditTask() throws IOException {
    	
    	JTable testTable = null;
		ArrayList<Task> testTask_List = null;
		DefaultTableModel testModel = new DefaultTableModel(testTask_List.size(),0);
		
		defaultPM = new Priority_Manager(testUserID, testDB);
    	
    	//defaultPM.addTask(testUserID, testDB, testModel, testTable);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Priority_Manager> addTask")
    void testAddTask() throws IOException {
    
    	JTable testTable = null;
		ArrayList<Task> testTask_List = null;
		DefaultTableModel testModel = new DefaultTableModel(testTask_List.size(),0);
		
		defaultPM = new Priority_Manager(testUserID, testDB);
    	
    	//defaultPM.addTask(testUserID, testDB, testModel, testTable);
    	  	
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*
    @Test
    @DisplayName("<Priority_Manager> Task Window")
    void testTaskWindow() {
    	
    	//TODO Interface Testing... Research in progress
    
    }
*/
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*
    @Test
    @DisplayName("<Priority_Manager> First Task Window ")
    void testFirstTaskWindow() {
    
   		//TODO Interface Testing... Research in progress
    
    }
*/
}