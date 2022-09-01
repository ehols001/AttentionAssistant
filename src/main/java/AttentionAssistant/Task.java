/**
 * Class that contains information on task created by 
 * the user through the priority manager
 */
package AttentionAssistant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Options are 'OPEN', 'CLOSED' and 'OVERDUE'
 * OPEN tasks were not yet completed by the User
 * CLOSED tasks were completed by the User
 * OVERDUE tasks are past the dueDate
 */

enum TaskStatus {
	OPEN, CLOSED, OVERDUE;
}


public class Task{
	/** Variables */
	private int taskID;
	private boolean priority;
	private String name;
	private String description;
	private Date dueDate;
	private TaskStatus status;
	
	
	/**
	 * False if not observable, True if observable
	 * Observable tasks will be monitored by the Observer
	 * Non-observable tasks will not be monitored by the Observer
	 */
	private boolean observable;
	
	
	/**
	 * Instantiating empty Task object
	 */
	public Task() {
		this.taskID = 1;
		this.description = "";
		this.observable = false;
		this.status = TaskStatus.CLOSED;
		this.name = null; 
		this.dueDate = null;
		this.priority = false;
	}
	
	/**
	 * Create a class Task with a specified
	 * taskID, description, whether observable, status
	 * @param int, String, boolean, Status
	 */
	public Task(int taskID, String description, boolean observable, TaskStatus status, String name, Date dueDate, boolean priority) {
		this.taskID = taskID;
		this.description = description;
		this.observable = observable;
		this.status = status;
		this.name = name; 
		this.dueDate = dueDate;
		this.priority = priority;
	}
	
	/**
	 * Instantiating copy constructor for Task object
	 */
	public Task(Task task) {
		this.taskID= task.taskID;
		this.description = task.description;
		this.observable = task.observable;
		this.status = task.status;
		this.name = task.name; 
		this.dueDate = task.dueDate;
		this.priority = task.priority;
	}
	
	
	/**
	 * Start of Encapsulation
	 * 
	 * Get taskID
	 * @return int
	 */
	public int getTaskID() {
		return this.taskID;
	}
	
	/**
	 * User should not be able to set the taskID this should be done automatically through the database
	 * comment out once database is working.
	 * 
	 * Set taskID
	 * @param int
	 */
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	/**
	 * Get Name
	 * @return name
	 */
	public String getTaskName() {
		return this.name;
	}
	
	/**
	 * Set Name
	 * @param String
	 */
	public void setTaskName(String n) {
		this.name = n;
	}
	
	/**
	 * Get Priority
	 * @return priority
	 */
	public boolean getPriority() {
		return this.priority;
	}
	
	/**
	 * Set Priority
	 * 
	 * @param boolean
	 */
	public void setPriority(boolean isPriority) {
		this.priority = isPriority;
	}
	
	/**
	 * Get DueDate
	 * @return dueDate
	 */
	public Date getDueDate() {
		return this.dueDate;
	}
	
	public String getStringDate(){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    String date = formatter.format(dueDate);
		return date;
	}
	
	/**
	 * Set DueDate
	 * @param Date
	 */
	public void setDueDate(Date due) {
		this.dueDate = due;
	}
	
	/**
	 * Get description
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Set description
	 * @param String
	 */
	public void setDescription(String description) {
		this.description= description;
	}
	
	/**
	 * Get Observable
	 * @return boolean
	 */
	public boolean getObservable() {
		return this.observable;
	}
	
	/**
	 * Set Observable
	 * @param boolean
	 */
	public void setObservable(boolean observable) {
		this.observable = observable;
	}
	
	/**
	 * Get Status
	 * @return TaskStatus
	 */
	public TaskStatus getStatus() {
		return this.status;
	}
	
	/**
	 * Set Status
	 * @param TaskStatus
	 */
	public void setStatus(TaskStatus s) {
		this.status = s;
	}
	
	 /** 
	   * Display Task
	   * @return String
	   */
	@Override
	public String toString() {
	 	String taskString= new String();
	 	taskString = "Task ID= " + this.taskID +
	 			" Priority= " + Boolean.toString(this.priority) +
	 			" Name= " + this.name +
	 			" Description= " + this.description +
	 			" Due Date= " + this.dueDate.toString() +
	 			" Observable= " + Boolean.toString(this.observable) +
	 			" Status= " + this.status.toString();
	 			
	 	return taskString;
	 	
	 }
}
