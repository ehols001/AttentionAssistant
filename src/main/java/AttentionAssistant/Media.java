package AttentionAssistant;

import java.util.Date;

/**
 * Class that contains information for each media item displayed in HTB
 * @author jmitchel2
 * @author krchr
 *
 */

public class Media {

	/**
	 * Primary Key for Media
	 */
	private int media_ID;

	/**
	 * Media ID for the media displayed from the Happy_Thought_Button
	 */
	private String media_ID_Tag;
	
	/**
	 * If the media is flagged by the user or not
	 */
	private boolean flagged;
	
	/**
	 * Records rating of media by the user
	 * 0 = thumbs down
	 * 1 = unrated
	 * 2 = thumbs up
	 */
	
	private int rating;
	
	/**
	 * The Date and Time the Happy_Thought_Button was executed 
	 */
	
	private Date dT_Executed;
	
	/**
	 * Instantiating empty Media object
	 */
	public Media() {
		this.media_ID= 0;
		this.media_ID_Tag = "";
		this.flagged = false;
		this.rating = 1;
		this.dT_Executed= null;
	}
	
	/**
	 * Instantiating a Media object with a passed in filename
	 */
	public Media(String filename) {
		this.media_ID= 0;
		this.media_ID_Tag = filename;
		this.flagged = false;
		this.rating = 1;
		this.dT_Executed= null;
	}

	/**
	 * Create a class Media with a specified
	 * media_ID, media_ID_Tage, flagged, rating
	 * @author jmitchel2
	 * @author krchr
	 * @param int, String, boolean, boolean
	 */
	public Media(int media_ID, String media_ID_Tag, boolean flagged, int rating, Date dT_Executed) {
		this.media_ID= media_ID;
		this.media_ID_Tag= media_ID_Tag;
		this.flagged = flagged;
		this.rating = rating;
		this.dT_Executed= dT_Executed;
	}
	
	/**
	 * Instantiating copy constructor for Media object
	 */
	public Media(Media media) {
		this.media_ID= media.media_ID;
		this.media_ID_Tag = media.media_ID_Tag;
		this.flagged = media.flagged;
		this.rating = media.rating;
		this.dT_Executed= media.dT_Executed;
	}
	
	/**
	 * Start of Encapsulation
	 * 
	 * Get hTB_ID
	 * @author jmitchel2
	 * @author krchr
	 * @return int
	 */
	public int getMediaID() {
		return this.media_ID;
	}
	
	/**
	 * User should not be able to set the media_ID this should be done automatically through the database
	 * comment out once database is working.
	 * 
	 * Set media_ID
	 * @param int
	 */
	public void setMediaID(int media_ID) {
		this.media_ID= media_ID;
	}

	/**
	 * Get media_ID_Tag
	 * 
	 * @return String
	 */
	public String getMedia_ID_Tag() {
		return this.media_ID_Tag;
	}
	
	/**
	 * Set media_ID_Tag
	 * 
	 * @param String
	 */
	public void setMedia_ID_Tag(String media_ID_Tag) {
		this.media_ID_Tag= media_ID_Tag;
	}

	/**
	 * Get flagged
	 * @return boolean
	 */
	public boolean getFlagged() {
		return this.flagged;
	}
	
	/**
	 * Set flagged
	 * @param boolean
	 */
	public void setFlagged(boolean flagged) {
		this.flagged= flagged;
	}
	
	/**
	 * Get rating
	 * @return int
	 */
	public int getRating() {
		return this.rating;
	}
	
	/**
	 * Set rating
	 * @param int
	 */
	public void setRating(int rating) {
		this.rating= rating;
	}

	/**
	 * Get dT_Executed
	 * 
	 * @return Date
	 */
	public Date getDT_Executed() {
		return this.dT_Executed;
	}
	
	/**
	 * Set dT_Executed
	 * 
	 * @param Date
	 */
	public void setDT_Executed(Date dT_Executed) {
		this.dT_Executed= dT_Executed;
	}

	  /** 
	   * Display HTB
	   * @return String
	   */
	@Override
	public String toString() {
	 	String mediaString= new String();
	 	mediaString = "Media ID= " + this.media_ID +
	 			" Media_ID_Tag= " + this.media_ID_Tag +
	 			" Flagged= " + Boolean.toString(this.flagged) +
	 			" Rating= " + this.rating +
	 			" Date Time Executed= " + this.dT_Executed;
	 			
	 	return mediaString;
	 	
	 }
}
