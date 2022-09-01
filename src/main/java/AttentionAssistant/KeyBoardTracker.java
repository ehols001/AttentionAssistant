package AttentionAssistant;

//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.atteo.evo.inflector.English;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyBoardTracker implements NativeKeyListener {

	int keyBoardScore; //Final keyboard score
	ArrayList<String> keyWords;
	int currentKeyPressScore = 1; //Internal Scores.  Means nothing outside this function
	int lastKeyPressScore = 1; //Internal Scores.  Means nothing outside this function
	int keysPressedScore = 1; //Internal Score for amount of keys pressed.
	int keyWordsFoundScore = 1; //Internal Score for amount of keys pressed.
	ArrayList<String> allWordsInputed = new ArrayList<String>(); //List that holds all the key words that were typed in
	StringBuilder inputWord = new StringBuilder(); //Internal stringbuilder for creating a list of words
	int toldWordsInputted = 0;
	int keyWordOccurrences = 0; //Number of keywords found.
	
	/**
 	 * Instantiating empty KeyBoardTracker object
 	 * @author jmitchel2
 	 */
	public KeyBoardTracker(ArrayList<String> keywords){
		this.keyBoardScore= 0;
		this.keyWords = keywords;
	}
	
	/**
	 * Create a class KeyBoardTracker with a specified
	 * KeyBoardScore
	 * @param int 
	 */
	public KeyBoardTracker(ArrayList<String> keyWords, int keyBoardScore) {
		this.keyBoardScore= keyBoardScore;
		this.keyWords = keyWords;
	}
	
	public int getKeystrokes(){
		return this.keysPressedScore;
	}
	
	/**
	 * Return the total amount of keywords the user inputed
	 * @return int
	 */
	public int getKeywordCount() {
		return this.keyWordOccurrences; 
	}
	
	/**
	 * Returns the final keyboard score
	 * @return int
	 */
	public int getFinalKBScore() {
		return keyBoardScore;
		
	}

	
	/**
	 * Start of Encapsulation
	 * 
	 * Get KeyBoardScore
	 * @return int
	 */
	public int getKeyBoardScore() {
		
		/**
		 * First Half of the score
		 * Based on key presses
		 */
		//Calculating a tempt 50 point scale
		int temptKeyPressedScore = 15 * (currentKeyPressScore - lastKeyPressScore)/lastKeyPressScore;
		
		//System.out.println("temptKeyPressedScore = " + temptKeyPressedScore);
				
		//Ensuring that the tempt key pressed score is not above 50 or below 1
		if(temptKeyPressedScore < 15 && temptKeyPressedScore > 0) {
			keysPressedScore = temptKeyPressedScore;
		} else if (temptKeyPressedScore >= 15) {
			keysPressedScore = 15;
		} else {
			keysPressedScore = 1;
		}
		
		//System.out.println("keysPressedScore = " + keysPressedScore);
				
		//Updating the lastKeyPressScore to the currentKeyPressScore
		//Reseting the lastKeyPressScore and currentKeyPressScore before they get to close to the int limit size
		if(currentKeyPressScore < 1147483647) {
			lastKeyPressScore = currentKeyPressScore;
		} else {
			lastKeyPressScore = 1;
			currentKeyPressScore = 1;
		}
		
		/**
		 * Second Half of the score
		 * Based on key words
		 */
		
		//Finding out how many times the keywords occurred in the list
		for(String keyword : keyWords) {
			keyword = keyword.toUpperCase();
			//System.out.println("curent keyword " + keyword);
			keyWordOccurrences += Collections.frequency(allWordsInputed, keyword);
			keyWordOccurrences += Collections.frequency(allWordsInputed, English.plural(keyword).toUpperCase()); //Check for the plural version
			//System.out.println("Current keyword occurrences " + keyWordOccurrences);
		}
		
		//System.out.println("How Many Key Words Were Found " + keyWordOccurrences);
		
		//Updating the keyBoardScoreKeyWords based on the occurrences
		/*for(int i = 0; i < keyWordOccurrences; i++) {
			keyWordsFoundScore += 10;
			if(keyWordsFoundScore > 50) {
				break;
			}
		}*/
		
		
		//Making sure that the keyBoardScoreKeyWords is not above 50
		//if(keyWordsFoundScore > 50) {
		if(keyWordOccurrences >= 2) {
			keyWordsFoundScore = 85;
		}else if(keyWordOccurrences == 1) {
			keyWordsFoundScore = 67;
		}else {
			keyWordsFoundScore = 0;
		}
		
		//System.out.println("keyBoardScoreKeyWords = " + keyWordsFoundScore);
		//System.out.println("keyBoardScoreKeyPressed = " + currentKeyPressScore);

		//Adding the two keyboard scores
		keyBoardScore = keysPressedScore + keyWordsFoundScore;
		
		//System.out.println("keyBoardScore = " + keyBoardScore);
		//System.out.println(allWordsInputed.toString());
		
		return this.keyBoardScore;
	}
	
	/**
	 * Gets all the words typed by the user
	 * @return String
	 */
	public String getWordsTyped() {
		return this.allWordsInputed.toString();
	}

	/**
	 * Set keyBoardScore
	 * @param int
	 */
	public void setKeyBoardScore(int keyBoardScore) {
		
		
		this.keyBoardScore = keyBoardScore;
	}

	
	/**
	 * Start Tracking Keyboard
	 * @param ArrayList<String>
	 */
	protected void startTracking() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ey) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ey.getMessage());
			System.exit(1);
		}
		
		GlobalScreen.addNativeKeyListener(this);
	}

	/**
	 * method that captures all keys pressed
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		//System.out.println("Same Something!");
		//System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		currentKeyPressScore++; //Updating key pressed count
		
		if(NativeKeyEvent.getKeyText(e.getKeyCode()).length() == 1) {
			inputWord.append(NativeKeyEvent.getKeyText(e.getKeyCode()));
		} else {
			allWordsInputed.add(inputWord.toString()); //Adding the inputWord to the list
			inputWord.setLength(0); //Resetting the inputWord variable
			toldWordsInputted++; //Keeping track of the all the words inputed.
		}
		
		//Use this code only if you need to store the key words in a text file
		/**
		try {
			File outPutFile = new File("KeyBoardPresses.txt");
			if(!outPutFile.exists()) {
				outPutFile.createNewFile();
			}
			FileWriter keyBoardPresses = new FileWriter(outPutFile.getName(),true);
			BufferedWriter bufferFile = new BufferedWriter(keyBoardPresses);
			
			if(NativeKeyEvent.getKeyText(e.getKeyCode()).length() > 1) {
				bufferFile.append("\n");
			} else {
				bufferFile.append(NativeKeyEvent.getKeyText(e.getKeyCode()));
			}
			bufferFile.close();
		} catch (IOException e1) {
			System.out.println("An error occurred.");
		    e1.printStackTrace();
		}
		*/
	}

	//Ignored for the most part
	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub	
	}


	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
	}
}
