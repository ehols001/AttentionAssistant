package AttentionAssistant;

import java.util.Date;
 
public class Notification {
	/** Variables */
	private int notificationID;
	private String type;
	private boolean ignored;
	private Date dT_Notification;
	
	/**
	 * Instantiating empty Notification System object
	 */
	public Notification() {
	this.notificationID = 0;
	this.type = "";
	this.ignored = false;
	this.dT_Notification = null;
	}
 
	/**
	 * Create a class Notification System with a specified
	 * NotificationID, type, ignored, dT_Notificaiton
	 * @param int, String, boolean, Date
	 */
	public Notification(int notificationID, String type, boolean ignored, Date dT_Notification) {
		this.notificationID= notificationID;
		this.type = type;
		this.ignored = ignored;
		this.dT_Notification= dT_Notification;
	}
	
	/**
	 * Instantiating copy constructor for Notification System object
	 */
	public Notification(Notification note) {
		this.notificationID= note.notificationID;
		this.type = note.type;
		this.ignored = note.ignored;
		this.dT_Notification= note.dT_Notification;
	}
	
	/**
	 * Start of Encapsulation
	 * 
	 * Get notificationID
	 * @return int
	 */
	public int getNotificationID() {
		return this.notificationID;
	}
	
	/**
	 * Set notificationID
	 * @param int
	 */
	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}
	
	/**
	 * Get type
	 * @return String
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Set type
	 * @param String
	 */
	public void setType(String type) {
		this.type= type;
	}
	
	/**
	 * Get ignored
	 * @return boolean
	 */
	public boolean getIgnored() {
		return this.ignored;
	}
	
	/**
	 * Set ignored
	 * @param boolean
	 */
	public void setIgnored(boolean ignored) {
		this.ignored= ignored;
	}
	
	/**
	 * Get dT_Notification
	 * @return Date
	 */
	public Date getDT_Notification() {
		return this.dT_Notification;
	}
	
	/**
	 * Set dT_Notification
	 * @param Date
	 */
	public void setDT_Notification(Date dT_Notification) {
		this.dT_Notification = dT_Notification;
	}
	
	 /** 
	   * Display Notification System
	   * @return String
	   */
	@Override
	public String toString() {
	 	String notifyString= new String();
	 	notifyString = "Notification ID= " + this.notificationID +
	 			" Type= " + this.type +
	 			" Ignored= " + Boolean.toString(this.ignored) +
	 			" Date and Time of Notification= " + this.dT_Notification.toString();
	 			
	 	return notifyString;
	 	
	 }
	
}