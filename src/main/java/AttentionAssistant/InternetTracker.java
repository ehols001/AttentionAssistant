package AttentionAssistant;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sqlite.SQLiteDataSource;

/**
 * Tracks the internet Usage to determine if user is on topic while searching the internet 
 */
public class InternetTracker {
	
	private int internetScore;
	private ArrayList<String> latestUrls;
	private ArrayList<Integer> keywordCounts;
	private ArrayList<Integer> wordCounts;
	private ArrayList<Integer> urlScores;
	File tempHistory;
	
	/**
	 * Instantiating empty InternetTracker object
	 * @author jmitchel2, ehols001
	 */
	public InternetTracker() {
		this.internetScore = 100;
		this.latestUrls = new ArrayList<String>();
		this.keywordCounts = new ArrayList<Integer>();
		this.wordCounts = new ArrayList<Integer>();
		this.urlScores = new ArrayList<Integer>();
		this.tempHistory = new File(System.getProperty("user.home") + "\\AppData\\Local\\Temp\\tempHistory");
	}
	
	/**
	 * Create a class InternetTracker with a specified
	 * internetScore, latestUrls, keywordCounts, wordCounts, urlScores, tempHistory
	 * @param int, ArrayList<String>, ArrayList<Integer>, ArrayList<Integer>, ArrayList<Integer>, File
	 */
	public InternetTracker(int internetScore, ArrayList<String> latestUrls, ArrayList<Integer> keywordCounts,
			ArrayList<Integer> wordCounts, ArrayList<Integer> urlScores, File tempHistory) {
		this.internetScore = internetScore;
		this.latestUrls = latestUrls;
		this.keywordCounts = keywordCounts;
		this.wordCounts = wordCounts;
		this.urlScores = urlScores;
		this.tempHistory = tempHistory;
	}
	
	/**
	 * Copy constructor for InternetTracker
	 * @param InternetTracker object
	 */
	public InternetTracker(InternetTracker it) {
		this.internetScore = it.internetScore;
		this.latestUrls = it.latestUrls;
		this.keywordCounts = it.keywordCounts;
		this.wordCounts = it.wordCounts;
		this.urlScores = it.urlScores;
		this.tempHistory = it.tempHistory;
	}
	
	/**
	 * Get internetScore
	 * @return int
	 */
	public int getInternetScore() {
		return this.internetScore;
	}

	/**
	 * Set internetScore
	 * @param int
	 */
	public void setInternetScore(int internetScore) {
		this.internetScore = internetScore;
	}
	
	/**
	 * Get the latest URLs
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getLatestUrls() {
		return this.latestUrls;
	}
	
	/**
	 * Set the latest URLs
	 * @param urls
	 */
	public void setLatestUrls(ArrayList<String> urls) {
		this.latestUrls = urls;
	}
	
	/**
	 * Get the number of keywords found for each URL
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getKeywordCounts() {
		return this.keywordCounts;
	}
	
	/**
	 * Set the number of keywords found for each URL
	 * @param kwCounts
	 */
	public void setKeywordCounts(ArrayList<Integer> kwCounts) {
		this.keywordCounts = kwCounts;
	}
	
	/**
	 * Get the total number of words found for each URL
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getWordCounts() {
		return this.wordCounts;
	}
	
	/**
	 * Set the total number of words found for each URL
	 * @param wCounts
	 */
	public void setWordCounts(ArrayList<Integer> wCounts) {
		this.wordCounts = wCounts;
	}
	
	/**
	 * Get the score for each URL
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getUrlScores() {
		return this.urlScores;
	}
	
	/**
	 * Set the score for each URL
	 * @param scores
	 */
	public void setUrlScores(ArrayList<Integer> scores) {
		this.urlScores = scores;
	}
	
	/**
	 * Get the absolute path of the temp history
	 * @return File
	 */
	public File getTempHistory() {
		return this.tempHistory;
	}
	
	/**
	 * Starts tracking relevance of Internet activity 
	 * 
	 * @param ArrayList<String>
	 * @param long startTime -> timestamp of when the Observer monitor function begins
	 * @author ehols001
	 */
	public void startTracking(ArrayList<String> keywords, long startTime) throws IOException {
		
		createHistoryCopy();
		
		setLatestBrowserHistory(startTime);
		
		if(this.latestUrls.size() != 0) {
			int urlCount = 0;
			int combinedScore = 0;
			String text = "";
			for(int i = 0; i < this.latestUrls.size(); i++) {
				if(parseFromOrigin(this.latestUrls.get(i)) == null) {
					this.keywordCounts.add(0);
					this.wordCounts.add(0);
					this.urlScores.add(0);
				}
				else {
					urlCount += 1;
					text = parseFromOrigin(this.latestUrls.get(i)).toLowerCase();
					combinedScore += calculatePageScore(keywords, text);
				}
			}
			
			//Accounting for the case where all latestUrls throw an Http 404 error
			if(urlCount != 0)
				this.internetScore = combinedScore / urlCount;
			else
				this.internetScore = -1;
		}
		else {
			this.internetScore = -1;
		}
		tempHistory.delete();
	}
	
	/**
	 * Iterates through a list of provided keywords checking if the 
	 * current keyword exists within the webpage, totaling up an average score.
	 * 
	 * @param ArrayList<String> keywords
	 * @param String text - text from a web page
	 * @return int -> internet score for the given text from a single url
	 * @author ehols001, jmitchel2
	 */
	public int calculatePageScore(ArrayList<String> keywords, String text) {
		//declaration
		double keywordsAppear=0;
		double calculatedScore=0;
		
		//Split the text on a page by each space
		String[] wordsOnAPage = text.split("\\s+");
		
		//turn each word into only A-Z characters
		for(int i = 0; i < wordsOnAPage.length; i++) {
			wordsOnAPage[i] = wordsOnAPage[i].replaceAll("[^A-Za-z]", "");
		}
		
		//search keywords for the words on a page.
		for(String keyword : keywords) {
			for (int j=0; j < wordsOnAPage.length; j++) {
				if (wordsOnAPage[j].equals(keyword)) {
					keywordsAppear = keywordsAppear + 1;
				}
			}
		}
		
		this.keywordCounts.add((int)keywordsAppear);
		this.wordCounts.add(wordsOnAPage.length);
		
		calculatedScore = (keywordsAppear/Double.valueOf(wordsOnAPage.length))*5000;
		
		int pageScore = 0;
		if (calculatedScore > 100) {
			pageScore = 100;
		}
		else {
			pageScore = (int)calculatedScore;
		}
		
		this.urlScores.add(pageScore);
		return pageScore;
	}
	
	/**
	 * Parses the contents of a web page from live or local origin
	 * and returns all text within the body.
	 * 
	 * @param String - Can be either a live url or a local file path 
	 * @return String - The parsed contents of the page
	 * @throws IOException
	 * @author ehols001
	 */
	public String parseFromOrigin(String uri) throws IOException {
		String text = "";
		if (uri.startsWith("http")) {
			try {
				Document webpage = Jsoup.connect(uri).get();
				text = webpage.body().text();
				return text;
			} catch (HttpStatusException e) {
				return null;
			}
		}
		else {
			File input = new File(uri);
			Document localpage = Jsoup.parse(input, "UTF-8");
			text = localpage.body().text();
			return text;
		}
	}
	
	/**
	 * Creates a copy of the chrome browser history in a different location.
	 * This is necessary to allow read access while chrome is open.
	 * 
	 * @author ehols001
	 */
	public void createHistoryCopy() {
		File source = new File(System.getProperty("user.home") 
				+ "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
		try {
			FileUtils.copyFile(source, tempHistory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves and sets the latest urls visited from the user's chrome browser history
	 * 
	 * @param long startTime -> timestamp of when the Observer monitor function begins
	 * @author ehols001
	 */
	public void setLatestBrowserHistory(long startTime) {
		SQLiteDataSource ds = new SQLiteDataSource();
		String history = "jdbc:sqlite:" + tempHistory.toString();
		ds.setUrl(history);
		
		HashMap<Long, String> tempUrls = new HashMap<>();
    	String query = "SELECT url, last_visit_time FROM urls WHERE last_visit_time > " + startTime;
    	try (Connection conn = ds.getConnection(); 
    			Statement stmt = conn.createStatement();) {
    		    ResultSet rs = stmt.executeQuery(query);
    		    while (rs.next()) {
    		    	tempUrls.put(rs.getLong("last_visit_time"), rs.getString("url"));
    		    }
    		    
    		    ArrayList<Long> sortedKeys = new ArrayList<Long>(tempUrls.keySet());
    		    Collections.sort(sortedKeys);
    		    for(long key : sortedKeys) {
    		    	this.latestUrls.add(tempUrls.get(key));
    		    }
    		    
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}
	
}
