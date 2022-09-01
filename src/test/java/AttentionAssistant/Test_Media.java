package AttentionAssistant;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test File for the Media functions.
 * @author jmitchel2
 * @author krchr
 */

public class Test_Media {
	
	/**
	 * Objects used in test
	 */

	Media defaultMedia;
	Media nonDefaultMedia;
	Media copyMedia;
	
	
	@BeforeEach
	void setup() {
		int testMediaID = 999;
		String testMediaIDTag = "This is a test Media ID Tag";
		boolean testFlagged = true;
		int testRating = 2;
		Date testDateMedia= new Date(1220227200L * 1000);

		defaultMedia= new Media();
		
		nonDefaultMedia = new Media(testMediaID, testMediaIDTag, testFlagged, testRating, testDateMedia);
		
		copyMedia = new Media(nonDefaultMedia);

	}
	
    @Test
    @DisplayName("<Media> Default Constructor")
    void MediaDefaultConstructor() {
        /**
         *  Make sure the defaultMedia media_ID is 0 for the default constructor
         */
        assertEquals(0, defaultMedia.getMediaID(), 
        "Default constructor defaultMedia.media_ID should be 0. Returned: " + Integer.toString(defaultMedia.getMediaID()));

        /**
         *  Make sure the defaultMedia media_ID_Tag is "" for the default constructor
         */
        assertEquals("", defaultMedia.getMedia_ID_Tag(), 
        "Default constructor defaultMedia.media_ID_Tag should be . Returned: " + defaultMedia.getMedia_ID_Tag());

        /**
         *  Make sure the defaultMedia Flagged is false for the default constructor
         */
        assertEquals(false, defaultMedia.getFlagged(), 
        "Default constructor defaultMedia.flagged should be false. Returned: " + defaultMedia.getFlagged());
        
        /**
         *  Make sure the defaultMedia Rating is 1 for the default constructor
         */
        assertEquals(1, defaultMedia.getRating(), 
        "Default constructor defaultMedia.rating should be 1. Returned: " + Integer.toString(defaultMedia.getRating()));
    
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> Parameter Constructor")
    void MediaParameterConstructor() {
        /**
         *  Make sure the nonDefaultMedia MediaID is 999 for the Parameter constructor
         */
        assertEquals(999, nonDefaultMedia.getMediaID(), 
        "nonDefault constructor nonDefaultMeida.media_ID should be 999. Returned: " + Integer.toString(nonDefaultMedia.getMediaID()));

        /**
         *  Make sure the nonDefaultMedia media_ID_Tag is "This is a test Media ID Tag" for the Parameter constructor
         */
        assertEquals("This is a test Media ID Tag", nonDefaultMedia.getMedia_ID_Tag(), 
        "nonDefault constructor nonDefaultMedia.media_ID_Tag should be This is a test Media ID Tag. Returned: " + nonDefaultMedia.getMedia_ID_Tag());

        /**
         *  Make sure the nonDefaultMedia Flagged is true for the Parameter constructor
         */
        assertEquals(true, nonDefaultMedia.getFlagged(), 
        "nonDefault constructor nonDefaultMedia.flagged should be false. Returned: " + nonDefaultMedia.getFlagged());
        
        /**
         *  Make sure the nonDefaultMedia Rating is 2 for the Parameter constructor
         */
        assertEquals(2, nonDefaultMedia.getRating(), 
        "nonDefault constructor nonDefaultMedia.rating should be 2. Returned: " + Integer.toString(nonDefaultMedia.getRating()));
    
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> Copy Constructor")
    void MediaCopyConstructor() {
        /**
         *  Make sure the copyMedia MediaID is 999 for the copy constructor
         */
        assertEquals(999, copyMedia.getMediaID(), 
        "copy constructor copyMedia.media_ID should be 999. Returned: " + Integer.toString(copyMedia.getMediaID()));

        /**
         *  Make sure the copyMedia media_ID_Tag is "This is a test Media ID Tag" for the copy constructor
         */
        assertEquals("This is a test Media ID Tag", copyMedia.getMedia_ID_Tag(), 
        "copy constructor copyMedia.media_ID_Tag should be This is a test Media ID Tag. Returned: " + copyMedia.getMedia_ID_Tag());

        /**
         *  Make sure the copyMedia Flagged is true for the copy constructor
         */
        assertEquals(true, copyMedia.getFlagged(), 
        "copy constructor copyMedia.flagged should be false. Returned: " + copyMedia.getFlagged());

        /**
         *  Make sure the copyMedia Rating is 2 for the copy constructor
         */
        assertEquals(2, copyMedia.getRating(), 
        "copy constructor copyMedia.rating should be 2. Returned: " + Integer.toString(copyMedia.getRating()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> SetMediaID")
    void MediaSetMediaID() {
    	
    	copyMedia.setMediaID(998);
    	assertEquals(998, copyMedia.getMediaID(), "copyMedia MediaID should be set to 998 but instead returned: " + Integer.toString(copyMedia.getMediaID()));
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> SetMediaMedia_ID_Tag")
    void MediaSetMedia_ID_Tag() {
    	
    	copyMedia.setMedia_ID_Tag("I AM A COPY MEDIA_ID_TAG");
    	assertEquals("I AM A COPY MEDIA_ID_TAG", copyMedia.getMedia_ID_Tag(), "copyMedia media_ID_Tag should be set to \"I AM A COPY MEDIA_ID_TAG\" but instead returned: " + copyMedia.getMedia_ID_Tag());
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> SetFlagged")
    void MediaSetFlagged() {
    	
    	copyMedia.setFlagged(false);
    	assertEquals(false, copyMedia.getFlagged(), "copyMedia flagged should be set to false but instead returned: " + Boolean.toString(copyMedia.getFlagged()));
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> SetRating")
    void MediaSetRating() {
    	
    	copyMedia.setRating(0);
    	assertEquals(0, copyMedia.getRating(), "copyMedia rating should be set to 0 but instead returned: " + Integer.toString(copyMedia.getRating()));
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> SetDT_Executed")
    void MediaSetDate() {
    	
    	copyMedia.setDT_Executed(new Date(1220227202L * 1000));
    	assertEquals(new Date(1220227202L * 1000), copyMedia.getDT_Executed(), "copyMedia dt_Executed should be set to Sun Aug 31 20:0s0:02 EDT 2008 but instead returned: " + (copyMedia.getDT_Executed()));
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("<Media> ToString")
    void MediaToString() {
    String String1 = "Media ID= 999 Media_ID_Tag= This is a test Media ID Tag Flagged= true Rating= 2 Date Time Executed= Sun Aug 31 20:00:00 EDT 2008";

    	assertEquals(String1, copyMedia.toString(), "copyMedia toString should be set to \"Media ID= 999 Media_ID_Tag= This is a test Media ID Tag Flagged= true Rating= 2\" but instead returned: " + copyMedia.toString());
    }
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

}
