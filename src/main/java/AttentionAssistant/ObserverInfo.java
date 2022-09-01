package AttentionAssistant;

import java.util.ArrayList;

public class ObserverInfo {

	/*
	 * Monitor Overview variables
	 */
	private String task;
	private String taskDescription;
	private ArrayList<String> taskKeywords;
	private int observerScore;
	
	/*
	 * Facial Recognition variables
	 */
	private int groupsOfFrames;
	private int numFaceDetected;
	private int defaultEyeScore;
	private int thresholdGathered;
	private int weightedEyeScore;
	
	/*
	 * Internet Tracking variables
	 */
	private ArrayList<String> urls;
	private ArrayList<Integer> numKeywordsPerURL;
	private ArrayList<Integer> numTotalWordsURL;
	private ArrayList<Integer> scorePerURL;
	private int internetScore;
	
	/*
	 * OS Events Tracking variables
	 */
	private ArrayList<String> wlApps;
	private ArrayList<String> wlAppsOpen;
	private ArrayList<String> blApps;
	private ArrayList<String> blAppsOpen;
	private int osEventsScore;
	
	/*
	 * Keyboard Tracking variables
	 */
	private String wordsTyped;
	private int numKeywordsTyped;
	private int keyboardScore;
	
	/*
	 * Mouse Tracking variables
	 */
	private int currentScore;
	private int lastScore;
	private int mouseScore;
	
	/*
	 * Initialize all Observer Monitor Details to be displayed
	 */
	public ObserverInfo() {
		this.task = "<No active task>";
		this.taskDescription = "<No task description>";
		this.taskKeywords = new ArrayList<String>();
		this.observerScore = 0;
		this.groupsOfFrames = 0;
		this.numFaceDetected = 0;
		this.defaultEyeScore = 0;
		this.thresholdGathered = 0;
		this.weightedEyeScore = 0;
		this.urls = new ArrayList<String>();
		this.numKeywordsPerURL = new ArrayList<Integer>();
		this.numTotalWordsURL = new ArrayList<Integer>();
		this.scorePerURL = new ArrayList<Integer>();
		this.internetScore = 0;
		this.wlApps = new ArrayList<String>();
		this.wlAppsOpen = new ArrayList<String>();
		this.blApps = new ArrayList<String>();
		this.blAppsOpen = new ArrayList<String>();
		this.osEventsScore = 0;
		this.wordsTyped = "<No keyboard input received>";
		this.numKeywordsTyped = 0;
		this.keyboardScore = 0;
		this.currentScore = 0;
		this.lastScore = 0;
		this.mouseScore = 0;
	}
	
	
	/***************************************************************************************
	
							Gets and Sets for all ObserverInfo variables
	
	****************************************************************************************/
	
	
	/***************************** Observer Overview Gets/Sets *****************************/
	
	/**
	 * Set the task that's being monitored
	 * @param activeTask
	 */
	public void setTask(Task activeTask) {
		this.task = activeTask.getTaskName();
	}
	
	/**
	 * Get the task that's being monitored
	 * @return String task name
	 */
	public String getTask() {
		return this.task;
	}
	
	/**
	 * Set the task description to be displayed
	 * @param activeTask
	 */
	public void setTaskDescription(Task activeTask) {
		this.taskDescription = activeTask.getDescription();
	}
	
	/**
	 * Get the task description to be displayed
	 * @return String task description
	 */
	public String getTaskDescription() {
		return this.taskDescription;
	}
	
	/**
	 * Set the keywords for the task being monitored
	 * @param ArrayList<String> keywords
	 */
	public void setTaskKeywords(ArrayList<String> keywords) {
		this.taskKeywords = keywords;
	}
	
	/**
	 * Get the keywords for the task being monitored
	 * @return ArrayList<String> keywords for the task
	 */
	public ArrayList<String> getTaskKeywords() {
		return this.taskKeywords;
	}
	
	/**
	 * Set the overall Observer score for this monitor period
	 * @param int score
	 */
	public void setObserverScore(int score) {
		this.observerScore = score;
	}
	
	/**
	 * Get the overall Observer score for this monitor period
	 * @return int
	 */
	public int getObserverScore() {
		return this.observerScore;
	}
	
	
	/***************************** Eye Tracking Gets/Sets *****************************/
	
	/**
	 * Set the number of groups of frames
	 * @param int numGroups
	 */
	public void setGroupsOfFrames(int numGroups) {
		this.groupsOfFrames = numGroups;
	}
	
	/**
	 * Get the number of groups of frames
	 * @return int
	 */
	public int getGroupsOfFrames() {
		return this.groupsOfFrames;
	}
	
	/**
	 * Set the number of times a face was detected
	 * @param int numFaces
	 */
	public void setNumFaceDetected(int numFaces) {
		this.numFaceDetected = numFaces;
	}
	
	/**
	 * Get the number of times a face was detected
	 * @return int
	 */
	public int getNumFaceDetected() {
		return this.numFaceDetected;
	}
	
	/**
	 * Set the default eye score
	 * @param int defaultScore
	 */
	public void setDefaultEyeScore(int defaultScore) {
		this.defaultEyeScore = defaultScore;
	}
	
	/**
	 * Get the default eye score
	 * @return int
	 */
	public int getDefaultEyeScore() {
		return this.defaultEyeScore;
	}
	
	/**
	 * Set the threshold gathered
	 * @param int threshold gathered
	 */
	public void setThresholdGathered(int threshold) {
		this.thresholdGathered = threshold;
	}
	
	/**
	 * Get the threshold gathered
	 * @return int
	 */
	public int getThresholdGathered() {
		return this.thresholdGathered;
	}
	
	/**
	 * Set the weighted eye score
	 * @param int weighted eye score
	 */
	public void setWeightedEyeScore(int weightedScore) {
		this.weightedEyeScore = weightedScore;
	}
	
	/**
	 * Get the weighted eye score
	 * @return int
	 */
	public int getWeightedEyeScore() {
		return this.weightedEyeScore;
	}
	
	
	/***************************** Internet Tracking Gets/Sets *****************************/
	
	/**
	 * Set the list of urls visited during this monitor period
	 * @param ArrayList<String> urlList
	 */
	public void setUrls(ArrayList<String> urlList) {
		this.urls = urlList;
	}
	
	/**
	 * Get the list of urls visited during this monitor period
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getUrls() {
		return this.urls;
	}
	
	/**
	 * Set the number of keywords found for each URL
	 * @param ArrayList<Integer> keywordCounts
	 */
	public void setNumKeywordsPerURL(ArrayList<Integer> keywordCounts) {
		this.numKeywordsPerURL = keywordCounts;
	}
	
	/**
	 * Get the number of keywords found for each URL
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getNumKeywordsPerURL() {
		return this.numKeywordsPerURL;
	}
	
	/**
	 * Set the total number of words on the page for each URL
	 * @param ArrayList<Integer> wordCounts
	 */
	public void setNumTotalWordsURL(ArrayList<Integer> wordCounts) {
		this.numTotalWordsURL = wordCounts;
	}
	
	/**
	 * Get the total number of words on the page for each URL
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getNumTotalWordsURL() {
		return this.numTotalWordsURL;
	}
	
	/**
	 * Set the Internet score for each URL
	 * @param ArrayList<Integer> urlScores
	 */
	public void setScorePerURL(ArrayList<Integer> urlScores) {
		this.scorePerURL = urlScores;
	}
	
	/**
	 * Get the Internet score for each URL
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getScorePerURL() {
		return this.scorePerURL;
	}
	
	/**
	 * Set the Internet score for this monitor period
	 * @param int score
	 */
	public void setInternetScore(int score) {
		this.internetScore = score;
	}
	
	/**
	 * Get the Internet score for this monitor period
	 * @return int
	 */
	public int getInternetScore() {
		return this.internetScore;
	}
	
	
	/***************************** OS Events Tracking Gets/Sets *****************************/
	
	/**
	 * Set the list of whitelisted apps for this user
	 * @param ArrayList<String> apps
	 */
	public void setWlApps(ArrayList<String> apps) {
		this.wlApps = apps;
	}
	
	/**
	 * Get the list of whitelisted apps for this user
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getWlApps() {
		return this.wlApps;
	}
	
	/**
	 * Set the list of whitelisted apps that are currently active
	 * @param ArrayList<String> openApps
	 */
	public void setWlAppsOpen(ArrayList<String> openApps) {
		this.wlAppsOpen = openApps;
	}
	
	/**
	 * Get the list of whitelisted apps that are currently active
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getWlAppsOpen() {
		return this.wlAppsOpen;
	}
	
	/**
	 * Set the list of blacklisted apps for this user
	 * @param ArrayList<String> apps
	 */
	public void setBlApps(ArrayList<String> apps) {
		this.blApps = apps;
	}
	
	/**
	 * Get the list of blacklisted apps for this user
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getBlApps() {
		return this.blApps;
	}
	
	/**
	 * Set the list of blacklisted apps that are currently active
	 * @param ArrayList<String> openApps
	 */
	public void setBlAppsOpen(ArrayList<String> openApps) {
		this.blAppsOpen = openApps;
	}
	
	/**
	 * Get the list of blacklisted apps that are currently active
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getBlAppsOpen() {
		return this.blAppsOpen;
	}
	
	/**
	 * Set the OS Events score for this monitor period
	 * @param int score
	 */
	public void setOsEventsScore(int score) {
		this.osEventsScore = score;
	}
	
	/**
	 * Get the OS Events score for this monitor period
	 * @return int 
	 */
	public int getOsEventsScore() {
		return this.osEventsScore;
	}
	
	
	/***************************** Keyboard Tracking Gets/Sets *****************************/
	
	/**
	 * Set the words typed by the user during this monitor period
	 * @param String words
	 */
	public void setWordsTyped(String words) {
		this.wordsTyped = words;
	}
	
	/**
	 * Get the words typed by the user during this monitor period
	 * @return String
	 */
	public String getWordsTyped() {
		return this.wordsTyped;
	}
	
	/**
	 * Set the number of keywords typed by the user during this monitor period
	 * @param int keywordCount
	 */
	public void setNumKeywordsTyped(int keywordCount) {
		this.numKeywordsTyped = keywordCount;
	}
	
	/**
	 * Get the number of keywords typed by the user during this monitor period
	 * @return int
	 */
	public int getNumKeywordsTyped() {
		return this.numKeywordsTyped;
	}
	
	/**
	 * Set the keyboard score for this monitor period
	 * @param int score
	 */
	public void setKeyboardScore(int score) {
		this.keyboardScore = score;
	}
	
	/**
	 * Get the keyboard score for this monitor period
	 * @return int 
	 */
	public int getKeyboardScore() {
		return this.keyboardScore;
	}
	
	
	/***************************** Mouse Tracking Gets/Sets *****************************/
	
	/**
	 * Set the current mouse score for this monitor period
	 * @param int score
	 */
	public void setCurrentMouseScore(int score) {
		this.currentScore = score;
	}
	
	/**
	 * Get the current mouse score for this monitor period
	 * @return int 
	 */
	public int getCurrentMouseScore() {
		return this.currentScore;
	}
	
	/**
	 * Set the last mouse score for this monitor period
	 * @param int score
	 */
	public void setLastMouseScore(int score) {
		this.lastScore = score;
	}
	
	/**
	 * Get the last mouse score for this monitor period
	 * @return int 
	 */
	public int getLastMouseScore() {
		return this.lastScore;
	}
	
	/**
	 * Set the mouse score for this monitor period
	 * @param int score
	 */
	public void setMouseScore(int score) {
		this.mouseScore = score;
	}
	
	/**
	 * Get the mouse score for this monitor period
	 * @return int 
	 */
	public int getMouseScore() {
		return this.mouseScore;
	}
}
