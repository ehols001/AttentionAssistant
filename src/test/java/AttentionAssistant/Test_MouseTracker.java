package AttentionAssistant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class Test_MouseTracker {
	
	MouseTracker testMouse;
	MouseTracker testMouseInt;
	
	@BeforeEach
	void setup() {
		int testMouseScore = 50;
		testMouse = new MouseTracker();
		testMouseInt = new MouseTracker(testMouseScore);
	}

	@Test
	@Order(1)
	@DisplayName("<MouseTracker> defaultConstructor")
	void testMouseTrackerConstructor() {
		assertEquals(1, testMouse.getDefaultMouseScore(),
				"Expected: 1 | Actual: " + testMouse.getMouseScore());
		assertEquals(1, testMouse.getMouseScore(), 
				"Expected: 1 | Actual: " + testMouse.getMouseScore());
	}

	@Test
	@Order(2)
	@DisplayName("<MouseTracker> intConstructor")
	void testMouseTrackerIntConstructor() {
		assertEquals(1, testMouse.getDefaultMouseScore(),
				"Expected: 1 | Actual: " + testMouse.getMouseScore());
		assertEquals(50, testMouseInt.getMouseScore(), 
				"Expected: 50 | Actual: " + testMouseInt.getMouseScore());
	}

	@Test
	@Order(3)
	@DisplayName("<MouseTracker> setMouseScore")
	void testSetMouseScore() {
		testMouse.setMouseScore(75);
		assertEquals(1, testMouse.getDefaultMouseScore(),
				"Expected: 1 | Actual: " + testMouse.getMouseScore());
		assertEquals(75, testMouse.getMouseScore(), 
				"Expected: 75 | Actual: " + testMouse.getMouseScore());
	}

}
