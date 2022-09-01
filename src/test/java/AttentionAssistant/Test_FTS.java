package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test_FTS {
	Free_Thought_Space defaultFTS;
	Free_Thought_Space nonDefaultFTS;
	Free_Thought_Space copyFTS;
	
	
	@BeforeEach
	void setup() {
	int testFTSID= 999;
	Date testFTSDT_Executed= new Date(1220227200L * 1000);

	defaultFTS= new Free_Thought_Space();
	nonDefaultFTS= new Free_Thought_Space();
	copyFTS= new Free_Thought_Space();
	}
	/*
    @Test
    @DisplayName("<Free_Thought_Space> Default Constructor")
//    void FreeThoughtSpaceDefaultConstructor() {
        /**
         *  Make sure the Free Thought Space FTSID is 0 for the default constructor
         */
/*        assertEquals(0, defaultFTS.getFTSID(), 
        "Default constructor FTS.FTSID should be 0. Returned: " + Integer.toString(defaultFTS.getFTSID()));
    }*/

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

//    @Test
//    @DisplayName("<Free_Thought_Space> Parameter Constructor")
//    void FreeThoughtSpaceParameterConstructor() {
        /**
         *  Make sure the Free Thought Space FTSID is 999 for the nondefault constructor
         */
 /*       assertEquals(999, nonDefaultFTS.getFTSID(), 
        "nonDefault constructor FTS.FTSID should be 999. Returned: " + Integer.toString(nonDefaultFTS.getFTSID()));
*/   
        /**
         *  Make sure the Free Thought Space dT_Executed is Date(1220227200L * 1000) for the nondefault constructor
         */
 /*       assertEquals(new Date(1220227200L * 1000), nonDefaultFTS.getDT_Executed(), 
        "nonDefault constructor FTS.dT_Executed should be Sun Aug 31 20:00:00 EDT 2008 . Returned: " + nonDefaultFTS.getDT_Executed());
    
    }*/

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

 //   @Test
 //   @DisplayName("<Free_Thought_Space> Copy Constructor")
//    void FreeThoughtSpaceCopyConstructor() {
        /**
         *  Make sure the Free Thought Space FTSID is 999 for the Copy constructor
         */
//        assertEquals(999, copyFTS.getFTSID(), 
//        "CopyFTS constructor FTS.FTSID should be 999. Returned: " + Integer.toString(copyFTS.getFTSID()));
   
        /**
         *  Make sure the Free Thought Space dT_Executed is Date(1220227200L * 1000) for the copy constructor
         */
//        assertEquals(new Date(1220227200L * 1000), copyFTS.getDT_Executed(), 
//        "copyFTS constructor FTS.dT_Executed should be Sun Aug 31 20:00:00 EDT 2008 . Returned: " + copyFTS.getDT_Executed());
//        }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*    
    @Test
    @DisplayName("<Free_thought_Space> SetFTSID")
    void FTSSetFTSID() {
    	
    	copyFTS.setFTSID(998);
    	assertEquals(998, copyFTS.getFTSID(), "copyFTS FTSID should be set to 998 but instead returned: " + Integer.toString(copyFTS.getFTSID()));
    }
*/
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*
    @Test
    @DisplayName("<Free_thought_Space> SetFTSdT_Executed")
    void FTSSetDT_Executed() {
    	
    	copyFTS.setDT_Executed(new Date(1220227202L * 1000));
    	assertEquals(new Date(1220227200L * 1000), copyFTS.getDT_Executed(), "copyFTS FTSDT_Executed should be set to Sun Aug 31 20:00:02 EDT 2008 but instead returned: " + copyFTS.getDT_Executed());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

//    @Test
//    @DisplayName("<Free_thought_Space> SetFTSdT_Executed")
    void FTSToString() {
    String fTSString= new String();
 	fTSString = "Free Thought Space ID= 999 Date Time Executed= Sun Aug 31 20:00:00 EDT 2008";
	assertEquals(fTSString, nonDefaultFTS.toString(), "nonDefaultFTS toString should be set to Free Thought Space ID= 999 Date Time Executed= Sun Aug 31 20:00:00 EDT 2008 but instead returned: " + nonDefaultFTS.toString());
    }

}