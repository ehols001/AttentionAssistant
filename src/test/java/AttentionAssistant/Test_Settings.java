package AttentionAssistant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Color;

/**
 * Test File for the Settings functions
 * @author krchr
 */

public class Test_Settings {

	Color aa_grey = new Color(51,51,51);
	int userID = 0; 
	
	/**
	 * Objects used in test
	 */
	Settings defaultSettings;
	Settings nonDefaultSettings;
	Settings copySettings;
	
	@BeforeEach
	void setup() {
		int testSettingsID = 999;
		int testUserID = 998; 
		Color testIconCircles = Color.RED; 
		Color testIcons = Color.YELLOW;
		int testOpacityCircles = 75; 
		int testOpacityIcons = 75; 
		boolean testIsCollapsed = true; 
		int testXCoord = 15;
		int testYCoord = 15; 
		boolean testIsVertical = false; 
		int testIconSize = 30; 
		boolean testTimerIsVisible = false; 
		boolean testPmIsVisible = false; 
		boolean testFtsIsVisible = false; 
		boolean testHtbIsVisible = false; 
		boolean testNtbIsVisible = false; 
		boolean testProgReportIsVisible = false; 
		boolean testTimerVisibilityIsLocked = true;
		boolean testPmVisibilityIsLocked = true;
		boolean testFtsVisibilityIsLocked = true;
		boolean testHtbVisibilityIsLocked = true;
		boolean testNtbVisibilityIsLocked = true;
		boolean testProgReportVisibilityIsLocked = true;
		boolean testAvatarIsActive = true; 
		boolean testTextIsActive = false; 
		boolean testAudioIsActive = true;
		boolean testTextToSpeech = true; 
		String testAvatarFilePath = "images/avatar_cat1.png"; 
		boolean testAlwaysOnScreen = true; 
		int testAvatarSize = 75; 
		boolean testPomodoroIsActive = false; 
		boolean testPomodoroIsLocked = true;
		int testWorkPeriod = 33; 
		boolean testWorkPeriodIsLocked = true;
		int testBreakPeriod = 23; 
		boolean testBreakPeriodIsLocked = true;
		boolean testTimeShowing = false; 
		boolean testFtsIsActive = false; 
		boolean testNtbIsActive = false; 
		boolean testIsAutoLinked = false; 
		boolean testHtbIsActive = false; 
		boolean testFtsIsLocked = true;
		boolean testNtbIsLocked = true;
		boolean testHtbIsLocked = true;
		
		defaultSettings = new Settings(userID);
		
		nonDefaultSettings = new Settings(testSettingsID, testUserID, testIconCircles, testIcons, testOpacityCircles, testOpacityIcons, testIsCollapsed, 
										  testXCoord, testYCoord, testIsVertical, testIconSize, testTimerIsVisible, testPmIsVisible, testFtsIsVisible, 
										  testHtbIsVisible, testNtbIsVisible, testProgReportIsVisible, testTimerVisibilityIsLocked, testPmVisibilityIsLocked, 
										  testFtsVisibilityIsLocked, testHtbVisibilityIsLocked, testNtbVisibilityIsLocked, testProgReportVisibilityIsLocked, 
										  testAvatarIsActive, testTextIsActive, testAudioIsActive, testTextToSpeech, testAvatarFilePath, testAlwaysOnScreen, 
										  testAvatarSize, testPomodoroIsActive, testPomodoroIsLocked, testWorkPeriod, testWorkPeriodIsLocked, testBreakPeriod, 
										  testBreakPeriodIsLocked, testTimeShowing, testFtsIsActive, testNtbIsActive, testIsAutoLinked, testHtbIsActive, 
										  testFtsIsLocked, testNtbIsLocked, testHtbIsLocked);
		
		copySettings = new Settings(nonDefaultSettings);
		
	}
	
	@Test 
	@DisplayName("<Settings> Default Constructor")
	void SettingsDefaultConstructor() {
		/**
		 * Make sure the Settings settingsID is set to 1 for the default constructor
		 */
		assertEquals(1, defaultSettings.getSettingsID(), "Default constructor Settings.settingsID should be 1. Returned: "
					+ Integer.toString(defaultSettings.getSettingsID()));
		
		/**
		 * Make sure the Settings userID is set to 0 for the default constructor
		 */
		assertEquals(0, defaultSettings.getUserID(), "Default constructor Settings.userID should be 0. Returned: "
					+ Integer.toString(defaultSettings.getUserID())); 
		
		/**
		 * Make sure the Settings iconCircles are set to aa_grey for the default constructor
		 */
		assertEquals(aa_grey, defaultSettings.getIconCircles(), "Default constructor Settings.iconCircles should be aa_grey. Returned: " 
				    + Integer.toString(defaultSettings.getIconCircles().getRGB()));
		
		/**
		 * Make sure Settings icons are set to Color.white for the default constructor
		 */
		assertEquals(Color.white, defaultSettings.getIcons(), "Default constructor Settings.icons should be Color.white. Returned: "
					+ Integer.toString(defaultSettings.getIcons().getRGB()));
		
		/**
		 * Make sure Settings OpacityCircles is set to 100 for the default constructor 
		 */
		assertEquals(100, defaultSettings.getOpacityCircles(), "Default constructor Settings.opacityCircles should be 100. Returned: "
					+ Integer.toString(defaultSettings.getOpacityCircles()));
		
		/**
		 * Make sure Settings OpacityIcons is set to 100 for the default constructor 
		 */
		assertEquals(100, defaultSettings.getOpacityIcons(), "Default constructor Settings.opacityIcons should be 100. Returned: "
					+ Integer.toString(defaultSettings.getOpacityIcons()));
		
		/**
		 * Make sure Settings isCollapsed is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getIsCollapsed(), "Default constructor Settings.isCollapsed should be false. Returned: "
					+ defaultSettings.getIsCollapsed());
		
		/**
		 * Make sure Settings xCoord is set to 0 for default constructor
		 */
		assertEquals(0, defaultSettings.getXCoord(), "Default constructor Settings.xCoord should be 0. Returned: "
				    + Integer.toString(defaultSettings.getXCoord()));
		
		/**
		 * Make sure Settings yCoord is set to 0 for default constructor
		 */
		assertEquals(0, defaultSettings.getYCoord(), "Default constructor Settings.yCoord should be 0. Returned: "
				    + Integer.toString(defaultSettings.getYCoord()));
		
		/**
		 * Make sure Settings isVertical is set to true for default Constructor 
		 */
		assertEquals(true, defaultSettings.getIsVertical(), "Default constructor Settings.isVertical should be true. Returned: "
					+ defaultSettings.getIsVertical());
		
		/**
		 * Make sure Settings iconSize is set to 50 for default constructor
		 */
		assertEquals(50, defaultSettings.getIconSize(), "Default constructor Settings.iconSize should be 50. Returned: "
					+ Integer.toString(defaultSettings.getIconSize()));
		
		/**
		 * Make sure Settings timerIsVisible is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getTimerIsVisible(), "Default constructor Settings.timerIsVisible should be true. Returned: "
					+ defaultSettings.getTimerIsVisible());
		
		/**
		 * Make sure Settings pmIsVisible is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getPmIsVisible(), "Default constructor Settings.pmIsVisible should be true. Returned: "
					+ defaultSettings.getPmIsVisible());
		
		/**
		 * Make sure Settings ftsIsVisible is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getFtsIsVisible(), "Default constructor Settings.ftsIsVisible should be true. Returned: "
					+ defaultSettings.getFtsIsVisible());
		
		/**
		 * Make sure Settings htbIsVisible is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getHtbIsVisible(), "Default constructor Settings.htbIsVisible should be true. Returned: "
					+ defaultSettings.getHtbIsVisible());
		
		/**
		 * Make sure Settings ntbIsVisible is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getNtbIsVisible(), "Default constructor Settings.ntbIsVisible should be true. Returned: "
					+ defaultSettings.getNtbIsVisible());
		
		/**
		 * Make sure Settings progReportIsVisible is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getProgReportIsVisible(), "Default constructor Settings.progReportIsVisible should be true. Returned: "
					+ defaultSettings.getProgReportIsVisible());
		
		/**
		 * Make sure Settings timerVisibilityIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getTimerVisibilityIsLocked(), "Default constructor Settings.timerVisibilityIsLocked should be false. Returned: "
					+ defaultSettings.getTimerVisibilityIsLocked());
		
		/**
		 * Make sure Settings pmVisibilityIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getPmVisibilityIsLocked(), "Default constructor Settings.pmVisibilityIsLocked should be false. Returned: "
					+ defaultSettings.getPmVisibilityIsLocked());
		
		/**
		 * Make sure Settings ftsVisibilityIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getFtsVisibilityIsLocked(), "Default constructor Settings.ftsVisibilityIsLocked should be false. Returned: "
					+ defaultSettings.getFtsVisibilityIsLocked());
		
		/**
		 * Make sure Settings htbVisibilityIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getHtbVisibilityIsLocked(), "Default constructor Settings.htbVisibilityIsLocked should be false. Returned: "
					+ defaultSettings.getHtbVisibilityIsLocked());
		
		/**
		 * Make sure Settings ntbVisibilityIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getNtbVisibilityIsLocked(), "Default constructor Settings.ntbVisibilityIsLocked should be false. Returned: "
					+ defaultSettings.getNtbVisibilityIsLocked());
		
		/**
		 * Make sure Settings progReportVisibilityIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getProgReportVisibilityIsLocked(), "Default constructor Settings.progReportVisibilityIsLocked should be false. Returned: "
					+ defaultSettings.getProgReportVisibilityIsLocked());
		
		/**
		 * Make sure Settings avatarIsActive is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getAvatarIsActive(), "Default constructor Settings.avatarIsActive should be false. Returned: "
					+ defaultSettings.getAvatarIsActive());
		
		/**
		 * Make sure Settings textIsActive is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getTextIsActive(), "Default constructor Settings.textIsActive should be true. Returned: "
					+ defaultSettings.getTextIsActive());
		
		/**
		 * Make sure Settings audioIsActive is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getAudioIsActive(), "Default constructor Settings.audioIsActive should be false. Returned: "
					+ defaultSettings.getAudioIsActive());
		
		/**
		 * Make sure Settings textToSpeech is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getTextToSpeech(), "Default constructor Settings.textToSpeech should be false. Returned: "
					+ defaultSettings.getTextToSpeech());
		
		/**
		 * Make sure Settings avatarFilePath is set to "images/avatar_dino.png" for default constructor
		 */
		assertEquals("avatarSelection/avatar_dino.png", defaultSettings.getAvatarFilePath(), "Default constructor Settings.avatarFilePath should be \"images/avatar_dino.png\". Returned: "
					+ defaultSettings.getAvatarFilePath()); 
		
		/**
		 * Make sure alwaysOnScreen is set to false for default constructor
		 */
		assertEquals(false, defaultSettings.getAlwaysOnScreen(), "Default constructor Settings.alwaysOnScreen should be false. Returned: "
					+ defaultSettings.getAlwaysOnScreen());
		
		/**
		 * Make sure avatarSize is set to 100 for default constructor
		 */
		assertEquals(100, defaultSettings.getAvatarSize(), "Default constructor Settings.avatarSize should be 100. Returned: "
					+ Integer.toString(defaultSettings.getAvatarSize()));
		
		/**
		 * Make sure Settings pomodoroIsActive is set to true for default constructor 
		 */
		assertEquals(true, defaultSettings.getPomodoroIsActive(), "Default constructor Settings.pomodoroIsActive should be true. Returned: "
					+ defaultSettings.getPomodoroIsActive());
		
		/**
		 * Make sure Settings pomodoroIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getPomodoroIsLocked(), "Default constructor Settings.pomodoroIsLocked should be false. Returned: "
					+ defaultSettings.getPomodoroIsLocked());
		
		/**
		 * Make sure Settings workPeriod is set to 45 for default constructor 
		 */
		assertEquals(45, defaultSettings.getWorkPeriod(), "Default constructor Settings.workPeriod should be 45. Returned: "
					+ Integer.toString(defaultSettings.getWorkPeriod()));
		
		/**
		 * Make sure Settings workPeriodIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getWorkPeriodIsLocked(), "Default constructor Settings.workPeriodIsLocked should be false. Returned: "
					+ defaultSettings.getWorkPeriodIsLocked());
		
		/**
		 * Make sure Settings breakPeriod is set to 15 for default constructor 
		 */
		assertEquals(15, defaultSettings.getBreakPeriod(), "Default constructor Settings.breakPeriod should be 15. Returned: "
					+ Integer.toString(defaultSettings.getBreakPeriod()));
		
		/**
		 * Make sure Settings breakPeriodIsLocked is set to false for default constructor 
		 */
		assertEquals(false, defaultSettings.getBreakPeriodIsLocked(), "Default constructor Settings.breakPeriodIsLocked should be false. Returned: "
					+ defaultSettings.getBreakPeriodIsLocked());
		
		/**
		 * Make sure Settings timeShowing is set to true for default constructor
		 */
		assertEquals(true, defaultSettings.getTimeShowing(), "Default constructor Settings.timeShowing should be true. Returned: "
					+ defaultSettings.getTimeShowing());
		
		/**
		 * Make sure Settings ftsIsActive is set to true for default constructor
		 */
		assertEquals(true, defaultSettings.getFtsIsActive(), "Default constructor Settings.ftsIsActive should be true. Returned: "
					+ defaultSettings.getFtsIsActive());
		
		/**
		 * Make sure Settings ntbIsActive is set to true for default constructor
		 */
		assertEquals(true, defaultSettings.getNtbIsActive(), "Default constructor Settings.ntbIsActive should be true. Returned: "
					+ defaultSettings.getNtbIsActive());
		
		/**
		 * Make sure Settings isAutoLinked is set to true for default constructor
		 */
		assertEquals(true, defaultSettings.getIsAutoLinked(), "Default constructor Settings.isAutoLinked should be true. Returned: "
					+ defaultSettings.getIsAutoLinked());
		
		/**
		 * Make sure Settings htbIsActive is set to true for default constructor
		 */
		assertEquals(true, defaultSettings.getHtbIsActive(), "Default constructor Settings.htbIsActive should be true. Returned: "
					+ defaultSettings.getHtbIsActive());
		
		/**
		 * Make sure Settings ftsIsLocked is set to false for default constructor
		 */
		assertEquals(false, defaultSettings.getFtsIsLocked(), "Default constructor Settings.ftsIsLocked should be false. Returned: "
					+ defaultSettings.getFtsIsLocked());
		
		/**
		 * Make sure Settings ntbIsLocked is set to false for default constructor
		 */
		assertEquals(false, defaultSettings.getNtbIsLocked(), "Default constructor Settings.ntbIsLocked should be false. Returned: "
					+ defaultSettings.getNtbIsLocked());
		
		/**
		 * Make sure Settings htbIsLocked is set to false for default constructor
		 */
		assertEquals(false, defaultSettings.getHtbIsLocked(), "Default constructor Settings.htbIsLocked should be false. Returned: "
					+ defaultSettings.getHtbIsLocked());
		
	}
	
	 /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> Parameter Constructor")
	void SettingsParameterConstructor() {
		/**
		 * Make sure the Settings settingsID is set to 999 for the nonDefault constructor
		 */
		assertEquals(999, nonDefaultSettings.getSettingsID(), "nonDefault constructor Settings.settingsID should be 999. Returned: "
					+ Integer.toString(nonDefaultSettings.getSettingsID()));
		
		/**
		 * Make sure the Settings userID is set to 998 for the nonDefault constructor
		 */
		assertEquals(998, nonDefaultSettings.getUserID(), "nonDefault constructor Settings.userID should be 998. Returned: "
					+ Integer.toString(nonDefaultSettings.getUserID()));
		
		/**
		 * Make sure the Settings iconCircles are set to Color.RED for the nonDefault constructor
		 */
		assertEquals(Color.RED, nonDefaultSettings.getIconCircles(), "nonDefault constructor Settings.iconCircles should be Color.Red. Returned: " 
				    + Integer.toString(nonDefaultSettings.getIconCircles().getRGB()));
		
		/**
		 * Make sure Settings icons are set to Color.YELLOW for the nonDefault constructor
		 */
		assertEquals(Color.YELLOW, nonDefaultSettings.getIcons(), "nonDefault constructor Settings.icons should be Color.YELLOW. Returned: "
					+ Integer.toString(nonDefaultSettings.getIcons().getRGB())); 
		
		/**
		 * Make sure Settings OpacityCircles is set to 75 for the nonDefault constructor 
		 */
		assertEquals(75, nonDefaultSettings.getOpacityCircles(), "nonDefault constructor Settings.opacityCircles should be 75. Returned: "
					+ Integer.toString(nonDefaultSettings.getOpacityCircles()));
		
		/**
		 * Make sure Settings OpacityIcons is set to 75 for the nonDefault constructor 
		 */
		assertEquals(75, nonDefaultSettings.getOpacityIcons(), "nonDefault constructor Settings.opacityIcons should be 75. Returned: "
					+ Integer.toString(nonDefaultSettings.getOpacityIcons()));
		
		/**
		 * Make sure Settings isCollapsed is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getIsCollapsed(), "nonDefault constructor Settings.isCollapsed should be true. Returned: "
					+ nonDefaultSettings.getIsCollapsed());
		
		/**
		 * Make sure Settings xCoord is set to 15 for nonDefault constructor
		 */
		assertEquals(15, nonDefaultSettings.getXCoord(), "nonDefault constructor Settings.xCoord should be 15. Returned: "
				    + Integer.toString(nonDefaultSettings.getXCoord()));
		
		/**
		 * Make sure Settings yCoord is set to 15 for nonDefault constructor
		 */
		assertEquals(15, nonDefaultSettings.getYCoord(), "nonDefault constructor Settings.yCoord should be 15. Returned: "
				    + Integer.toString(nonDefaultSettings.getYCoord()));
		
		/**
		 * Make sure Settings isVertical is set to false for nonDefault Constructor 
		 */
		assertEquals(false, nonDefaultSettings.getIsVertical(), "nonDefault constructor Settings.isVertical should be false. Returned: "
					+ nonDefaultSettings.getIsVertical());
		
		/**
		 * Make sure Settings iconSize is set to 30 for nonDefault constructor
		 */
		assertEquals(30, nonDefaultSettings.getIconSize(), "nonDefault constructor Settings.iconSize should be 30. Returned: "
					+ Integer.toString(nonDefaultSettings.getIconSize()));
		
		/**
		 * Make sure Settings timerIsVisible is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getTimerIsVisible(), "nonDefault constructor Settings.timerIsVisible should be false. Returned: "
					+ nonDefaultSettings.getTimerIsVisible());
		
		/**
		 * Make sure Settings pmIsVisible is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getPmIsVisible(), "nonDefault constructor Settings.pmIsVisible should be false. Returned: "
					+ nonDefaultSettings.getPmIsVisible());
		
		/**
		 * Make sure Settings ftsIsVisible is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getFtsIsVisible(), "nonDefault constructor Settings.ftsIsVisible should be false. Returned: "
					+ nonDefaultSettings.getFtsIsVisible());
		
		/**
		 * Make sure Settings htbIsVisible is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getHtbIsVisible(), "nonDefault constructor Settings.htbIsVisible should be false. Returned: "
					+ nonDefaultSettings.getHtbIsVisible());
		
		/**
		 * Make sure Settings ntbIsVisible is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getNtbIsVisible(), "nonDefault constructor Settings.ntbIsVisible should be false. Returned: "
					+ nonDefaultSettings.getNtbIsVisible());
		
		/**
		 * Make sure Settings progReportIsVisible is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getProgReportIsVisible(), "nonDefault constructor Settings.progReportIsVisible should be false. Returned: "
					+ nonDefaultSettings.getProgReportIsVisible());
		
		/**
		 * Make sure Settings timerVisibilityIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getTimerVisibilityIsLocked(), "nonDefault constructor Settings.timerVisibilityIsLocked should be ture. Returned: "
					+ nonDefaultSettings.getTimerVisibilityIsLocked());
		
		/**
		 * Make sure Settings pmVisibilityIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getPmVisibilityIsLocked(), "nonDefault constructor Settings.pmVisibilityIsLocked should be true. Returned: "
					+ nonDefaultSettings.getPmVisibilityIsLocked());
		
		/**
		 * Make sure Settings ftsVisibilityIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getFtsVisibilityIsLocked(), "nonDefault constructor Settings.ftsVisibilityIsLocked should be true. Returned: "
					+ nonDefaultSettings.getFtsVisibilityIsLocked());
		
		/**
		 * Make sure Settings htbVisibilityIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getHtbVisibilityIsLocked(), "nonDefault constructor Settings.htbVisibilityIsLocked should be true. Returned: "
					+ nonDefaultSettings.getHtbVisibilityIsLocked());
		
		/**
		 * Make sure Settings ntbVisibilityIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getNtbVisibilityIsLocked(), "nonDefault constructor Settings.ntbVisibilityIsLocked should be true. Returned: "
					+ nonDefaultSettings.getNtbVisibilityIsLocked());
		
		/**
		 * Make sure Settings progReportVisibilityIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getProgReportVisibilityIsLocked(), "nonDefault constructor Settings.progReportVisibilityIsLocked should be true. Returned: "
					+ nonDefaultSettings.getProgReportVisibilityIsLocked());
		
		/**
		 * Make sure Settings avatarIsActive is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getAvatarIsActive(), "nonDefault constructor Settings.avatarIsActive should be true. Returned: "
					+ nonDefaultSettings.getAvatarIsActive());
		
		/**
		 * Make sure Settings textIsActive is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getTextIsActive(), "nonDefault constructor Settings.textIsActive should be false. Returned: "
					+ nonDefaultSettings.getTextIsActive());
		
		/**
		 * Make sure Settings audioIsActive is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getAudioIsActive(), "nonDefault constructor Settings.audioIsActive should be true. Returned: "
					+ nonDefaultSettings.getAudioIsActive());
		
		/**
		 * Make sure Settings textToSpeech is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getTextToSpeech(), "nonDefault constructor Settings.textToSpeech should be true. Returned: "
					+ nonDefaultSettings.getTextToSpeech());
		
		/**
		 * Make sure Settings avatarFilePath is set to "images/avatar_cat1.png" for nonDefault constructor
		 */
		assertEquals("images/avatar_cat1.png", nonDefaultSettings.getAvatarFilePath(), "nonDefault constructor Settings.avatarFilePath should be \"images/avatar_cat1.png\". Returned: "
					+ nonDefaultSettings.getAvatarFilePath()); 
		
		/**
		 * Make sure alwaysOnScreen is set to true for nonDefault constructor
		 */
		assertEquals(true, nonDefaultSettings.getAlwaysOnScreen(), "nonDefault constructor Settings.alwaysOnScreen should be true. Returned: "
					+ nonDefaultSettings.getAlwaysOnScreen());
		
		/**
		 * Make sure avatarSize is set to 75 for nonDefault constructor
		 */
		assertEquals(75, nonDefaultSettings.getAvatarSize(), "nonDefault constructor Settings.avatarSize should be 75. Returned: "
					+ Integer.toString(nonDefaultSettings.getAvatarSize()));
		
		/**
		 * Make sure Settings pomodoroIsActive is set to false for nonDefault constructor 
		 */
		assertEquals(false, nonDefaultSettings.getPomodoroIsActive(), "nonDefault constructor Settings.pomodoroIsActive should be false. Returned: "
					+ nonDefaultSettings.getPomodoroIsActive());
		
		/**
		 * Make sure Settings pomodoroIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getPomodoroIsLocked(), "nonDefault constructor Settings.pomodoroIsLocked should be true. Returned: "
					+ nonDefaultSettings.getPomodoroIsLocked());
		
		/**
		 * Make sure Settings workPeriod is set to 33 for nonDefault constructor 
		 */
		assertEquals(33, nonDefaultSettings.getWorkPeriod(), "nonDefault constructor Settings.workPeriod should be 33. Returned: "
					+ Integer.toString(nonDefaultSettings.getWorkPeriod()));
		
		/**
		 * Make sure Settings workPeriodIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getWorkPeriodIsLocked(), "nonDefault constructor Settings.workPeriodIsLocked should be true. Returned: "
					+ nonDefaultSettings.getWorkPeriodIsLocked());
		
		/**
		 * Make sure Settings breakPeriod is set to 23 for nonDefault constructor 
		 */
		assertEquals(23, nonDefaultSettings.getBreakPeriod(), "nonDefault constructor Settings.breakPeriod should be 23. Returned: "
					+ Integer.toString(nonDefaultSettings.getBreakPeriod()));
		
		/**
		 * Make sure Settings breakPeriodIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getBreakPeriodIsLocked(), "nonDefault constructor Settings.breakPeriodIsLocked should be true. Returned: "
					+ nonDefaultSettings.getBreakPeriodIsLocked());
		
		/**
		 * Make sure Settings timeShowing is set to false for nonDefault constructor
		 */
		assertEquals(false, nonDefaultSettings.getTimeShowing(), "nonDefault constructor Settings.timeShowing should be trfalseue. Returned: "
					+ nonDefaultSettings.getTimeShowing());
		
		/**
		 * Make sure Settings ftsIsActive is set to false for nonDefault constructor
		 */
		assertEquals(false, nonDefaultSettings.getFtsIsActive(), "nonDefault constructor Settings.ftsIsActive should be false. Returned: "
					+ nonDefaultSettings.getFtsIsActive());
		
		/**
		 * Make sure Settings ntbIsActive is set to false for nonDefault constructor
		 */
		assertEquals(false, nonDefaultSettings.getNtbIsActive(), "nonDefault constructor Settings.ntbIsActive should be false. Returned: "
					+ nonDefaultSettings.getNtbIsActive());
		
		/**
		 * Make sure Settings isAutoLinked is set to false for nonDefault constructor
		 */
		assertEquals(false, nonDefaultSettings.getIsAutoLinked(), "nonDefault constructor Settings.isAutoLinked should be false. Returned: "
					+ nonDefaultSettings.getIsAutoLinked());
		
		/**
		 * Make sure Settings htbIsActive is set to false for nonDefault constructor
		 */
		assertEquals(false, nonDefaultSettings.getHtbIsActive(), "nonDefault constructor Settings.htbIsActive should be false. Returned: "
					+ nonDefaultSettings.getHtbIsActive());
		
		/**
		 * Make sure Settings ftsIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getFtsIsLocked(), "nonDefault constructor Settings.ftsIsLocked should be true. Returned: "
					+ nonDefaultSettings.getFtsIsLocked());
		
		/**
		 * Make sure Settings ntbIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getNtbIsLocked(), "nonDefault constructor Settings.ntbIsLocked should be true. Returned: "
					+ nonDefaultSettings.getNtbIsLocked());
		
		/**
		 * Make sure Settings htbIsLocked is set to true for nonDefault constructor 
		 */
		assertEquals(true, nonDefaultSettings.getHtbIsLocked(), "nonDefault constructor Settings.htbIsLocked should be true. Returned: "
					+ nonDefaultSettings.getHtbIsLocked());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> Copy Constructor")
	void SettingsCopyConstructor() {
		/**
		 * Make sure Settings settingsID is set to 999 for copy constructor
		 */
		assertEquals(999, copySettings.getSettingsID(), "Copy constructor Settings.settingsID should be 999. Returned: " 
					+ Integer.toString(copySettings.getSettingsID()));
		
		/**
		 * Make sure Settings UserID is set to 998 for copy constructor
		 */
		assertEquals(998, copySettings.getUserID(), "Copy constructor Settings.userID should be 998. Returned: " 
					+ Integer.toString(copySettings.getUserID()));
		
		/**
		 * Make sure the Settings iconCircles are set to Color.RED for the copy constructor
		 */
		assertEquals(Color.RED, copySettings.getIconCircles(), "Copy constructor Settings.iconCircles should be Color.Red. Returned: " 
				    + Integer.toString(copySettings.getIconCircles().getRGB()));
		
		/**
		 * Make sure Settings icons are set to Color.YELLOW for the copy constructor
		 */
		assertEquals(Color.YELLOW, copySettings.getIcons(), "Copy constructor Settings.icons should be Color.YELLOW. Returned: "
					+ Integer.toString(copySettings.getIcons().getRGB())); 
		
		/**
		 * Make sure Settings OpacityCircles is set to 75 for the copy constructor 
		 */
		assertEquals(75, copySettings.getOpacityCircles(), "Copy constructor Settings.opacityCircles should be 75. Returned: "
					+ Integer.toString(copySettings.getOpacityCircles()));
		
		/**
		 * Make sure Settings OpacityIcons is set to 75 for the copy constructor 
		 */
		assertEquals(75, copySettings.getOpacityIcons(), "Copy constructor Settings.opacityIcons should be 75. Returned: "
					+ Integer.toString(copySettings.getOpacityIcons()));
		
		/**
		 * Make sure Settings isCollapsed is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getIsCollapsed(), "Copy constructor Settings.isCollapsed should be true. Returned: "
					+ copySettings.getIsCollapsed());
		
		/**
		 * Make sure Settings xCoord is set to 15 for copy constructor
		 */
		assertEquals(15, copySettings.getXCoord(), "Copy constructor Settings.xCoord should be 15. Returned: "
				    + Integer.toString(copySettings.getXCoord()));
		
		/**
		 * Make sure Settings yCoord is set to 15 for copy constructor
		 */
		assertEquals(15, copySettings.getYCoord(), "Copy constructor Settings.yCoord should be 15. Returned: "
				    + Integer.toString(copySettings.getYCoord()));
		
		/**
		 * Make sure Settings isVertical is set to false for copy Constructor 
		 */
		assertEquals(false, copySettings.getIsVertical(), "Copy constructor Settings.isVertical should be false. Returned: "
					+ copySettings.getIsVertical());
		
		/**
		 * Make sure Settings iconSize is set to 30 for copy constructor
		 */
		assertEquals(30, copySettings.getIconSize(), "Copy constructor Settings.iconSize should be 30. Returned: "
					+ Integer.toString(copySettings.getIconSize()));
	
		/**
		 * Make sure Settings timerIsVisible is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getTimerIsVisible(), "Copy constructor Settings.timerIsVisible should be false. Returned: "
					+ copySettings.getTimerIsVisible());
		
		/**
		 * Make sure Settings pmIsVisible is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getPmIsVisible(), "Copy constructor Settings.pmIsVisible should be false. Returned: "
					+ copySettings.getPmIsVisible());
		
		/**
		 * Make sure Settings ftsIsVisible is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getFtsIsVisible(), "Copy constructor Settings.ftsIsVisible should be false. Returned: "
					+ copySettings.getFtsIsVisible());
		
		/**
		 * Make sure Settings htbIsVisible is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getHtbIsVisible(), "Copy constructor Settings.htbIsVisible should be false. Returned: "
					+ copySettings.getHtbIsVisible());
		
		/**
		 * Make sure Settings ntbIsVisible is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getNtbIsVisible(), "Copy constructor Settings.ntbIsVisible should be false. Returned: "
					+ copySettings.getNtbIsVisible());
		
		/**
		 * Make sure Settings progReportIsVisible is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getProgReportIsVisible(), "Copy constructor Settings.progReportIsVisible should be false. Returned: "
					+ copySettings.getProgReportIsVisible());
		
		/**
		 * Make sure Settings timerVisibilityIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getTimerVisibilityIsLocked(), "Copy constructor Settings.timerVisibilityIsLocked should be true. Returned: "
					+ copySettings.getTimerVisibilityIsLocked());
		
		/**
		 * Make sure Settings pmVisibilityIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getPmVisibilityIsLocked(), "Copy constructor Settings.pmVisibilityIsLocked should be true. Returned: "
					+ copySettings.getPmVisibilityIsLocked());
		
		/**
		 * Make sure Settings ftsVisibilityIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getFtsVisibilityIsLocked(), "Copy constructor Settings.ftsVisibilityIsLocked should be true. Returned: "
					+ copySettings.getFtsVisibilityIsLocked());
		
		/**
		 * Make sure Settings htbVisibilityIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getHtbVisibilityIsLocked(), "Copy constructor Settings.htbVisibilityIsLocked should be true. Returned: "
					+ copySettings.getHtbVisibilityIsLocked());
		
		/**
		 * Make sure Settings ntbVisibilityIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getNtbVisibilityIsLocked(), "Copy constructor Settings.ntbVisibilityIsLocked should be true. Returned: "
					+ copySettings.getNtbVisibilityIsLocked());
		
		/**
		 * Make sure Settings progReportVisibilityIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getProgReportVisibilityIsLocked(), "Copy constructor Settings.progReportVisibilityIsLocked should be true. Returned: "
					+ copySettings.getProgReportVisibilityIsLocked());
		
		/**
		 * Make sure Settings avatarIsActive is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getAvatarIsActive(), "Copy constructor Settings.avatarIsActive should be true. Returned: "
					+ copySettings.getAvatarIsActive());
		
		/**
		 * Make sure Settings textIsActive is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getTextIsActive(), "Copy constructor Settings.textIsActive should be false. Returned: "
					+ copySettings.getTextIsActive());
		
		/**
		 * Make sure Settings audioIsActive is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getAudioIsActive(), "Copy constructor Settings.audioIsActive should be true. Returned: "
					+ copySettings.getAudioIsActive());
		
		/**
		 * Make sure Settings textToSpeech is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getTextToSpeech(), "Copy constructor Settings.textToSpeech should be true. Returned: "
					+ copySettings.getTextToSpeech());
		
		/**
		 * Make sure Settings avatarFilePath is set to "images/avatar_cat1.png" for copy constructor
		 */
		assertEquals("images/avatar_cat1.png", copySettings.getAvatarFilePath(), "Copy constructor Settings.avatarFilePath should be \"images/avatar_cat1.png\". Returned: "
					+ copySettings.getAvatarFilePath()); 

		/**
		 * Make sure alwaysOnScreen is set to true for copy constructor
		 */
		assertEquals(true, copySettings.getAlwaysOnScreen(), "Copy constructor Settings.alwaysOnScreen should be true. Returned: "
					+ copySettings.getAlwaysOnScreen());
		
		/**
		 * Make sure avatarSize is set to 75 for copy constructor
		 */
		assertEquals(75, copySettings.getAvatarSize(), "Copy constructor Settings.avatarSize should be 75. Returned: "
					+ Integer.toString(copySettings.getAvatarSize()));
		
		/**
		 * Make sure Settings pomodoroIsActive is set to false for copy constructor 
		 */
		assertEquals(false, copySettings.getPomodoroIsActive(), "Copy constructor Settings.pomodoroIsActive should be false. Returned: "
					+ copySettings.getPomodoroIsActive());
		
		/**
		 * Make sure Settings pomodoroIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getPomodoroIsLocked(), "Copy constructor Settings.pomodoroIsLocked should be true. Returned: "
					+ copySettings.getPomodoroIsLocked());
		
		/**
		 * Make sure Settings workPeriod is set to 33 for copy constructor 
		 */
		assertEquals(33, copySettings.getWorkPeriod(), "Copy constructor Settings.workPeriod should be 33. Returned: "
					+ Integer.toString(copySettings.getWorkPeriod()));
		
		/**
		 * Make sure Settings workPeriodIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getWorkPeriodIsLocked(), "Copy constructor Settings.workPeriodIsLocked should be true. Returned: "
					+ copySettings.getWorkPeriodIsLocked());
		
		/**
		 * Make sure Settings breakPeriod is set to 23 for copy constructor 
		 */
		assertEquals(23, copySettings.getBreakPeriod(), "Copy constructor Settings.breakPeriod should be 23. Returned: "
					+ Integer.toString(copySettings.getBreakPeriod()));
		
		/**
		 * Make sure Settings breakPeriodIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getBreakPeriodIsLocked(), "Copy constructor Settings.breakPeriodIsLocked should be true. Returned: "
					+ copySettings.getBreakPeriodIsLocked());
		
		/**
		 * Make sure Settings timeShowing is set to false for copy constructor
		 */
		assertEquals(false, copySettings.getTimeShowing(), "Copy constructor Settings.timeShowing should be trfalseue. Returned: "
					+ copySettings.getTimeShowing());
		
		/**
		 * Make sure Settings ftsIsActive is set to false for copy constructor
		 */
		assertEquals(false, copySettings.getFtsIsActive(), "Copy constructor Settings.ftsIsActive should be false. Returned: "
					+ copySettings.getFtsIsActive());
		
		/**
		 * Make sure Settings ntbIsActive is set to false for copy constructor
		 */
		assertEquals(false, copySettings.getNtbIsActive(), "Copy constructor Settings.ntbIsActive should be false. Returned: "
					+ copySettings.getNtbIsActive());
		
		/**
		 * Make sure Settings isAutoLinked is set to false for copy constructor
		 */
		assertEquals(false, copySettings.getIsAutoLinked(), "Copy constructor Settings.isAutoLinked should be false. Returned: "
					+ copySettings.getIsAutoLinked());
		
		/**
		 * Make sure Settings htbIsActive is set to false for copy constructor
		 */
		assertEquals(false, copySettings.getHtbIsActive(), "Copy constructor Settings.htbIsActive should be false. Returned: "
					+ copySettings.getHtbIsActive());
		
		/**
		 * Make sure Settings ftsIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getFtsIsLocked(), "Copy constructor Settings.ftsIsLocked should be true. Returned: "
					+ copySettings.getFtsIsLocked());
		
		/**
		 * Make sure Settings ntbIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getNtbIsLocked(), "Copy constructor Settings.ntbIsLocked should be true. Returned: "
					+ copySettings.getNtbIsLocked());
		
		/**
		 * Make sure Settings htbIsLocked is set to true for copy constructor 
		 */
		assertEquals(true, copySettings.getHtbIsLocked(), "Copy constructor Settings.htbIsLocked should be true. Returned: "
					+ copySettings.getHtbIsLocked());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetSettingsID")
	void SettingsSetSettingsID() {
		
		copySettings.setSettingsID(123);
		assertEquals(123, copySettings.getSettingsID(), "copySettings.settingsID should be set to 123, but returned: "
					+ Integer.toString(copySettings.getSettingsID())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetUserID")
	void SettingsSetUserID() {
		
		copySettings.setUserID(456);
		assertEquals(456, copySettings.getUserID(), "copySettings.userID should be set to 456, but returned: "
					+ Integer.toString(copySettings.getUserID())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetIconCircles")
	void SettingsSetIconCircles() {
		
		copySettings.setIconCircles(Color.BLUE);
		assertEquals(Color.BLUE, copySettings.getIconCircles(), "copySettings.iconCircles should be set to Color.Blue, but returned: "
					+ Integer.toString(copySettings.getIconCircles().getRGB())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetIcons")
	void SettingsSetIcons() {
		
		copySettings.setIcons(Color.GREEN);
		assertEquals(Color.GREEN, copySettings.getIcons(), "copySettings.icons should be set to Color.GREEN, but returned: "
					+ Integer.toString(copySettings.getIcons().getRGB())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetOpacityCircles")
	void SettingsSetOpacityCircles() {
		
		copySettings.setOpacityCircles(50);
		assertEquals(50, copySettings.getOpacityCircles(), "copySettings.opacityCircles should be set to 50, but returned: "
					+ Integer.toString(copySettings.getOpacityCircles())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetOpacityIcons")
	void SettingsSetOpacityIcons() {
		
		copySettings.setOpacityIcons(50);
		assertEquals(50, copySettings.getOpacityIcons(), "copySettings.opacityIcons should be set to 50, but returned: "
					+ Integer.toString(copySettings.getOpacityIcons())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetIsCollapsed")
	void SettingsSetIsCollapsed() {
		
		copySettings.setIsCollapsed(false);
		assertEquals(false, copySettings.getIsCollapsed(), "copySettings.isCollapsed should be set to false, but returned: "
					+ copySettings.getIsCollapsed()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetXCoord")
	void SettingsSetXCoord() {
		
		copySettings.setXCoord(25);
		assertEquals(25, copySettings.getXCoord(), "copySettings.xCoord should be set to 25, but returned: "
					+ Integer.toString(copySettings.getXCoord())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetYCoord")
	void SettingsSetYCoord() {
		
		copySettings.setYCoord(25);
		assertEquals(25, copySettings.getYCoord(), "copySettings.yCoord should be set to 25, but returned: "
					+ Integer.toString(copySettings.getYCoord())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetIsVertical")
	void SettingsSetIsVertical() {
		
		copySettings.setIsVertical(true);
		assertEquals(true, copySettings.getIsVertical(), "copySettings.isVertical should be set to true, but returned: "
					+ copySettings.getIsVertical()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetIconSize")
	void SettingsSetIconSize() {
		
		copySettings.setIconSize(45);
		assertEquals(45, copySettings.getIconSize(), "copySettings.iconSize should be set to 45, but returned: "
					+ Integer.toString(copySettings.getIconSize())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetTimerIsVisible")
	void SettingsSetTimerIsVisible() {
		
		copySettings.setTimerIsVisible(true);
		assertEquals(true, copySettings.getTimerIsVisible(), "copySettings.timerIsVisible should be set to true, but returned: "
					+ copySettings.getTimerIsVisible()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetPmIsVisible")
	void SettingsSetPmIsVisible() {
		
		copySettings.setPmIsVisible(true);
		assertEquals(true, copySettings.getPmIsVisible(), "copySettings.pmIsVisible should be set to true, but returned: "
					+ copySettings.getPmIsVisible()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetFtsIsVisible")
	void SettingsSetFtsIsVisible() {
		
		copySettings.setFtsIsVisible(true);
		assertEquals(true, copySettings.getFtsIsVisible(), "copySettings.ftsIsVisible should be set to true, but returned: "
					+ copySettings.getFtsIsVisible()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetHtbIsVisible")
	void SettingsSetHtbIsVisible() {
		
		copySettings.setHtbIsVisible(true);
		assertEquals(true, copySettings.getHtbIsVisible(), "copySettings.htbIsVisible should be set to true, but returned: "
					+ copySettings.getHtbIsVisible()); 
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetNtbIsVisible")
	void SettingsSetNtbIsVisible() {
		
		copySettings.setNtbIsVisible(true);
		assertEquals(true, copySettings.getNtbIsVisible(), "copySettings.ntbIsVisible should be set to true, but returned: "
					+ copySettings.getNtbIsVisible()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetProgReportIsVisible")
	void SettingsSetProgReportIsVisible() {
		
		copySettings.setProgReportIsVisible(true);
		assertEquals(true, copySettings.getProgReportIsVisible(), "copySettings.progReportIsVisible should be set to true, but returned: "
					+ copySettings.getProgReportIsVisible()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetTimerVisibilityIsLocked")
	void SettingsSetTimerVisibilityIsLocked() {
		
		copySettings.setTimerVisibilityIsLocked(false);
		assertEquals(false, copySettings.getTimerVisibilityIsLocked(), "copySettings.timerVisibilityIsLocked should be set to false, but returned: "
					+ copySettings.getTimerVisibilityIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetPmVisibilityIsLocked")
	void SettingsSetPmVisibilityIsLocked() {
		
		copySettings.setPmVisibilityIsLocked(false);
		assertEquals(false, copySettings.getPmVisibilityIsLocked(), "copySettings.pmVisibilityIsLocked should be set to false, but returned: "
					+ copySettings.getPmVisibilityIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetFtsVisibilityIsLocked")
	void SettingsSetFtsVisibilityIsLocked() {
		
		copySettings.setFtsVisibilityIsLocked(false);
		assertEquals(false, copySettings.getFtsVisibilityIsLocked(), "copySettings.ftsVisibilityIsLocked should be set to false, but returned: "
					+ copySettings.getFtsVisibilityIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetHtbVisibilityIsLocked")
	void SettingsSetHtbVisibilityIsLocked() {
		
		copySettings.setHtbVisibilityIsLocked(false);
		assertEquals(false, copySettings.getHtbVisibilityIsLocked(), "copySettings.htbVisibilityIsLocked should be set to false, but returned: "
					+ copySettings.getHtbVisibilityIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetNtbVisibilityIsLocked")
	void SettingsSetNtbVisibilityIsLocked() {
		
		copySettings.setNtbVisibilityIsLocked(false);
		assertEquals(false, copySettings.getNtbVisibilityIsLocked(), "copySettings.ntbVisibilityIsLocked should be set to false, but returned: "
					+ copySettings.getNtbVisibilityIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetProgReportVisibilityIsLocked")
	void SettingsSetProgReportVisibilityIsLocked() {
		
		copySettings.setProgReportVisibilityIsLocked(false);
		assertEquals(false, copySettings.getProgReportVisibilityIsLocked(), "copySettings.progReportVisibilityIsLocked should be set to false, but returned: "
					+ copySettings.getProgReportVisibilityIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetAvatarIsActive")
	void SettingsSetAvatarIsActive() {
		
		copySettings.setAvatarIsActive(false);
		assertEquals(false, copySettings.getAvatarIsActive(), "copySettings.avatarIsActive should be set to false, but returned: "
					+ copySettings.getAvatarIsActive()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetTextIsActive")
	void SettingsSetTextIsActive() {
		
		copySettings.setTextIsActive(true);
		assertEquals(true, copySettings.getTextIsActive(), "copySettings.textIsActive should be set to true, but returned: "
					+ copySettings.getTextIsActive()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetAudioIsActive")
	void SettingsSetAudioIsActive() {
		
		copySettings.setAudioIsActive(false);
		assertEquals(false, copySettings.getAudioIsActive(), "copySettings.audioIsActive should be set to false, but returned: "
					+ copySettings.getAudioIsActive()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetTextToSpeech")
	void SettingsSetTextToSpeech() {
		
		copySettings.setTextToSpeech(false);
		assertEquals(false, copySettings.getTextToSpeech(), "copySettings.textToSpeech should be set to false, but returned: "
					+ copySettings.getTextToSpeech()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetAvatarFilePath")
	void SettingsSetAvatarFilePath() {
		
		copySettings.setAvatarFilePath("images/avatar_duck.png");
		assertEquals("images/avatar_duck.png", copySettings.getAvatarFilePath(), "copySettings.avatarFilePath should be set to \"images/avatar_duck.png\", but returned: "
					+ copySettings.getAvatarFilePath()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetAlwaysOnScreen")
	void SettingsSetAlwaysOnScreen() {
		
		copySettings.setAlwaysOnScreen(false);
		assertEquals(false, copySettings.getAlwaysOnScreen(), "copySettings.alwaysOnScreen should be set to false, but returned: "
					+ copySettings.getAlwaysOnScreen()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetAvatarSize")
	void SettingsSetAvatarSize() {
		
		copySettings.setAvatarSize(200);
		assertEquals(200, copySettings.getAvatarSize(), "copySettings.avatarSize should be set to 200, but returned: "
					+ Integer.toString(copySettings.getAvatarSize())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetPomodoroIsActive")
	void SettingsSetPomodoroIsActive() {
		
		copySettings.setPomodoroIsActive(true);
		assertEquals(true, copySettings.getPomodoroIsActive(), "copySettings.pomodoroIsActive should be set to true, but returned: "
					+ copySettings.getPomodoroIsActive()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetPomodoroIsLocked")
	void SettingsSetPomodoroIsLocked() {
		
		copySettings.setPomodoroIsLocked(false);
		assertEquals(false, copySettings.getPomodoroIsLocked(), "copySettings.pomodoroIsLocked should be set to false, but returned: "
					+ copySettings.getPomodoroIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetWorkPeriod")
	void SettingsSetWorkPeriod() {
		
		copySettings.setWorkPeriod(75);
		assertEquals(75, copySettings.getWorkPeriod(), "copySettings.workPeriod should be set to 75, but returned: "
					+ Integer.toString(copySettings.getWorkPeriod())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetWorkPeriodIsLocked")
	void SettingsSetWorkPeriodIsLocked() {
		
		copySettings.setWorkPeriodIsLocked(false);
		assertEquals(false, copySettings.getWorkPeriodIsLocked(), "copySettings.workPeriodIsLocked should be set to false, but returned: "
					+ copySettings.getWorkPeriodIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@Test
	@DisplayName("<Settings> SetBreakPeriod")
	void SettingsSetBreakPeriod() {
		
		copySettings.setBreakPeriod(35);
		assertEquals(35, copySettings.getBreakPeriod(), "copySettings.breakPeriod should be set to 35, but returned: "
					+ Integer.toString(copySettings.getBreakPeriod())); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetBreakPeriodIsLocked")
	void SettingsSetBreakPeriodIsLocked() {
		
		copySettings.setBreakPeriodIsLocked(false);
		assertEquals(false, copySettings.getBreakPeriodIsLocked(), "copySettings.breakPeriodIsLocked should be set to false, but returned: "
					+ copySettings.getBreakPeriodIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetTimeShowing")
	void SettingsSetTimeShowing() {
		
		copySettings.setTimeShowing(true);
		assertEquals(true, copySettings.getTimeShowing(), "copySettings.timeShowing should be set to true, but returned: "
					+ copySettings.getTimeShowing()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetFtsIsActive")
	void SettingsSetFtsIsActive() {
		
		copySettings.setFtsIsActive(true);
		assertEquals(true, copySettings.getFtsIsActive(), "copySettings.ftsIsActive should be set to true, but returned: "
					+ copySettings.getFtsIsActive()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetNtbIsActive")
	void SettingsSetNtbIsActive() {
		
		copySettings.setNtbIsActive(true);
		assertEquals(true, copySettings.getNtbIsActive(), "copySettings.ntbIsActive should be set to true, but returned: "
					+ copySettings.getNtbIsActive()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetIsAutoLinked")
	void SettingsSetIsAutoLinked() {
		
		copySettings.setIsAutoLinked(true);
		assertEquals(true, copySettings.getIsAutoLinked(), "copySettings.isAutoLinked should be set to true, but returned: "
					+ copySettings.getIsAutoLinked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetHtbIsActive")
	void SettingsSetHtbIsActive() {
		
		copySettings.setHtbIsActive(true);
		assertEquals(true, copySettings.getHtbIsActive(), "copySettings.htbIsActive should be set to true, but returned: "
					+ copySettings.getHtbIsActive()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetFtsIsLocked")
	void SettingsSetFtsIsLocked() {
		
		copySettings.setFtsIsLocked(true);
		assertEquals(true, copySettings.getFtsIsLocked(), "copySettings.ftsIsLocked should be set to true, but returned: "
					+ copySettings.getFtsIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetNtbIsLocked")
	void SettingsSetNtbIsLocked() {
		
		copySettings.setNtbIsLocked(true);
		assertEquals(true, copySettings.getNtbIsLocked(), "copySettings.ntbIsLocked should be set to true, but returned: "
					+ copySettings.getNtbIsLocked()); 
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	@DisplayName("<Settings> SetHtbIsLocked")
	void SettingsSetHtbIsLocked() {
		
		copySettings.setHtbIsLocked(true);
		assertEquals(true, copySettings.getHtbIsLocked(), "copySettings.htbIsLocked should be set to true, but returned: "
					+ copySettings.getHtbIsLocked()); 
	}
}
