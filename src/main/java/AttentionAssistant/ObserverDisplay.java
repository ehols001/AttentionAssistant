package AttentionAssistant;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ObserverDisplay {
	
	Color aa_grey = new Color(120,120,120);
	Color aa_purple = new Color(0,0,255);
	LineBorder line = new LineBorder(aa_purple, 2, true);
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = 650; 
	private int width = 550; 
	private int mouseX;
	private int mouseY;
	final static boolean shouldFill = true; 
	final static boolean shouldWeightX = true; 
	final static boolean RIGHT_TO_LEFT = false;
	
	/**
	 * RHS display for Monitor Overview sub menu
	 */
	private void monitorOverviewPanel(JPanel card_panel, ObserverInfo monitorInfo) {
		
		JPanel overview_panel = new JPanel();
		overview_panel.setLayout(new BoxLayout(overview_panel, BoxLayout.Y_AXIS));
		overview_panel.setBackground(aa_grey);
		
		
		/*
		 * everything within the Active Task section
		 */
		JPanel task_panel = new JPanel();
		task_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		task_panel.setBackground(aa_grey);
		task_panel.setMaximumSize(new Dimension(425, 110));
		
		JLabel activeTask = new JLabel("<html><u>Active Task:</u></html>");
		activeTask.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		activeTask.setForeground(Color.white);
		
		JLabel taskName = new JLabel(" " + monitorInfo.getTask());
		taskName.setFont(new Font("Serif", Font.BOLD, 20));
		taskName.setForeground(Color.white);
		
		task_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		task_panel.add(activeTask);
		task_panel.add(taskName);
		
		
		/*
		 * everything within the Task Description section
		 */
		JPanel descriptionLabel_panel = new JPanel();
		descriptionLabel_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		descriptionLabel_panel.setBackground(aa_grey);
		descriptionLabel_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel descriptionLabel = new JLabel("<html><u>Task Description:</u></html>");
		descriptionLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		descriptionLabel.setForeground(Color.white);
		
		descriptionLabel_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		descriptionLabel_panel.add(descriptionLabel);
		
		JPanel description_panel = new JPanel();
		description_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		description_panel.setBackground(aa_grey);
		description_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel description = new JLabel(monitorInfo.getTaskDescription());
		description.setFont(new Font("Serif", Font.BOLD, 17));
		description.setForeground(Color.white);
		
		description_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		description_panel.add(description);
		
		
		/*
		 * everything within the Task Keywords section
		 */
		JPanel keywordsLabel_panel = new JPanel();
		keywordsLabel_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		keywordsLabel_panel.setBackground(aa_grey);
		keywordsLabel_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel keywordsLabel = new JLabel("<html><u>Task Keywords:</u></html>");
		keywordsLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		keywordsLabel.setForeground(Color.white);
		
		keywordsLabel_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		keywordsLabel_panel.add(keywordsLabel);
		
		JPanel keywords_panel = new JPanel();
		keywords_panel.setLayout(new GridLayout(0, 3));
		keywords_panel.setBackground(aa_grey);
		keywords_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel keyword;
		ArrayList<String> taskKeywords = monitorInfo.getTaskKeywords();
		for(int i = 0; i < taskKeywords.size(); i++) {
			keyword = new JLabel("       " + taskKeywords.get(i));
			keyword.setFont(new Font("Serif", Font.BOLD, 17));
			keyword.setForeground(Color.white);

			keywords_panel.add(keyword);	
		}
		
		
		/*
		 * everything within the Overall Observer Score section
		 */
		JPanel score_panel = new JPanel();
		score_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		score_panel.setBackground(aa_grey);
		score_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel score = new JLabel("Observer Score: " + monitorInfo.getObserverScore());
		score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		score.setForeground(Color.white);
		
		score_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		score_panel.add(score);
		
		JPanel threshold_panel = new JPanel();
		threshold_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		threshold_panel.setBackground(aa_grey);
		
		JLabel threshold = new JLabel("* A score of 50 or above is considered on task");
		threshold.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		threshold.setForeground(Color.white);
		
		threshold_panel.add(Box.createRigidArea(new Dimension(30, 0)));
		threshold_panel.add(threshold);
		
		
		/*
		 * add everything to general_panel
		 */
		overview_panel.add(task_panel);
		overview_panel.add(descriptionLabel_panel);
		overview_panel.add(description_panel);
		overview_panel.add(keywordsLabel_panel);
		overview_panel.add(keywords_panel);
		overview_panel.add(Box.createRigidArea(new Dimension(0, 25)));
		overview_panel.add(score_panel);
		overview_panel.add(threshold_panel);
		
		//add to general_panel to card_panel
		card_panel.add("overview", overview_panel);
	}
	
	/**
	 * RHS display for Facial Recognition Tracking sub menu
	 */
	private void facialRecognitionPanel(JPanel card_panel, ObserverInfo monitorInfo) {
		
		JPanel face_panel = new JPanel();
		face_panel.setLayout(new BoxLayout(face_panel, BoxLayout.Y_AXIS));
		face_panel.setBackground(aa_grey);
		
		
		/*
		 * everything within the Total number of groups of frames section
		 */
		JPanel numGroups_panel = new JPanel();
		numGroups_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		numGroups_panel.setBackground(aa_grey);
		numGroups_panel.setMaximumSize(new Dimension(425, 75));
		
		JLabel numGroups = new JLabel("Total number of groups of frames: " + monitorInfo.getGroupsOfFrames());
		numGroups.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		numGroups.setForeground(Color.white);
		
		numGroups_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		numGroups_panel.add(numGroups);
		
		
		/*
		 * everything within the Number of times a face was detected section
		 */
		JPanel numFaces_panel = new JPanel();
		numFaces_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		numFaces_panel.setBackground(aa_grey);
		numFaces_panel.setMaximumSize(new Dimension(425, 75));
		
		JLabel numFaces = new JLabel("Number of times a face was detected: " + monitorInfo.getNumFaceDetected());
		numFaces.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		numFaces.setForeground(Color.white);
		
		numFaces_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		numFaces_panel.add(numFaces);
		
		
		/*
		 * everything within the Facial recognition score (default) section
		 */
		JPanel dscore_panel = new JPanel();
		dscore_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		dscore_panel.setBackground(aa_grey);
		dscore_panel.setMaximumSize(new Dimension(425, 75));
		
		JLabel dscore = new JLabel("Facial recognition score (default): " + monitorInfo.getDefaultEyeScore());
		dscore.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		dscore.setForeground(Color.white);
		
		dscore_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		dscore_panel.add(dscore);
		
		
		/*
		 * everything within the Threshold gathered section
		 */
		JPanel threshold_panel = new JPanel();
		threshold_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		threshold_panel.setBackground(aa_grey);
		threshold_panel.setMaximumSize(new Dimension(425, 75));
		
		JLabel threshold = new JLabel("Threshold gathered: " + monitorInfo.getThresholdGathered());
		threshold.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		threshold.setForeground(Color.white);
		
		threshold_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		threshold_panel.add(threshold);
		
		
		/*
		 * everything within the facial recognition score (weighted) section
		 */
		JPanel wscore_panel = new JPanel();
		wscore_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		wscore_panel.setBackground(aa_grey);
		wscore_panel.setMaximumSize(new Dimension(425, 75));
		
		JLabel wscore = new JLabel("Facial recognition score (weighted): " + monitorInfo.getWeightedEyeScore());
		wscore.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		wscore.setForeground(Color.white);
		
		wscore_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		wscore_panel.add(wscore);
		
		
		/*
		 * add everything to face_panel
		 */
		face_panel.add(numGroups_panel);
		face_panel.add(numFaces_panel);
		face_panel.add(dscore_panel);
		face_panel.add(threshold_panel);
		face_panel.add(wscore_panel);
		
		//add to face_panel to card_panel
		card_panel.add("face", face_panel);
	}
	
	/**
	 * RHS display for Internet Tracking sub menu
	 */
	private void internetPanel(JPanel card_panel, ObserverInfo monitorInfo) {
		
		JPanel internet_panel = new JPanel();
		internet_panel.setLayout(new BoxLayout(internet_panel, BoxLayout.Y_AXIS));
		internet_panel.setBackground(aa_grey);
		
		
		/*
		 * everything within the Most Recently Visited URLs section
		 */
		JPanel urlsLabel_panel = new JPanel();
		urlsLabel_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		urlsLabel_panel.setBackground(aa_grey);
		urlsLabel_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel urlsLabel = new JLabel("<html><u>Most Recently Visited URLs:</u></html>");
		urlsLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		urlsLabel.setForeground(Color.white);
		
		urlsLabel_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		urlsLabel_panel.add(urlsLabel);
		
		JPanel urls_panel = new JPanel();
		urls_panel.setLayout(new GridLayout(0, 1));
		urls_panel.setBackground(aa_grey);
		urls_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel url;
		int nullUrls = 0;
		ArrayList<String> urls = monitorInfo.getUrls();
		for(int i = 0; i < urls.size(); i++) {
			if(monitorInfo.getNumTotalWordsURL().get(i) == 0) {nullUrls += 1;}
			else {
				url = new JLabel("       " + (i + 1 - nullUrls) + ".)  " + urls.get(i));
				url.setFont(new Font("Serif", Font.BOLD, 15));
				url.setForeground(Color.white);

				urls_panel.add(url);
			}
		}
		
		
		/*
		 * everything within the Information per URL section
		 */
		JPanel urlInfo_panel = new JPanel();
		urlInfo_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		urlInfo_panel.setBackground(aa_grey);
		urlInfo_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel urlColumn = new JLabel("<html><u>URL</u></html>");
		urlColumn.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		urlColumn.setForeground(Color.white);
		
		JLabel numKeywords = new JLabel("<html><u># of keywords</u></html>");
		numKeywords.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		numKeywords.setForeground(Color.white);
		
		JLabel numWords = new JLabel("<html><u># of words</u></html>");
		numWords.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		numWords.setForeground(Color.white);
		
		JLabel urlScore = new JLabel("<html><u>URL score</u></html>");
		urlScore.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		urlScore.setForeground(Color.white);
		
		urlInfo_panel.add(Box.createRigidArea(new Dimension(5, 0)));
		urlInfo_panel.add(urlColumn);
		urlInfo_panel.add(numKeywords);
		urlInfo_panel.add(numWords);
		urlInfo_panel.add(urlScore);
		
		JPanel urlData_panel = new JPanel();
		urlData_panel.setLayout(new GridLayout(0, 4));
		urlData_panel.setBackground(aa_grey);
		urlData_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel keywordsPer;
		JLabel wordsPer;
		JLabel scorePer;
		nullUrls = 0;
		ArrayList<Integer> keywordsPerURL = monitorInfo.getNumKeywordsPerURL();
		ArrayList<Integer> wordsPerURL = monitorInfo.getNumTotalWordsURL();
		ArrayList<Integer> scorePerURL = monitorInfo.getScorePerURL();
		for(int i = 0; i < urls.size(); i++) {
			if(wordsPerURL.get(i) == 0) {nullUrls += 1;}
			else {
				url = new JLabel("         " + (i + 1 - nullUrls));
				url.setFont(new Font("Serif", Font.BOLD, 15));
				url.setForeground(Color.white);
			
				keywordsPer = new JLabel("     " + keywordsPerURL.get(i));
				keywordsPer.setFont(new Font("Serif", Font.BOLD, 15));
				keywordsPer.setForeground(Color.white);
			
				wordsPer = new JLabel("    " + wordsPerURL.get(i));
				wordsPer.setFont(new Font("Serif", Font.BOLD, 15));
				wordsPer.setForeground(Color.white);
			
				scorePer = new JLabel("   " + scorePerURL.get(i));
				scorePer.setFont(new Font("Serif", Font.BOLD, 15));
				scorePer.setForeground(Color.white);

				urlData_panel.add(url);	
				urlData_panel.add(keywordsPer);
				urlData_panel.add(wordsPer);
				urlData_panel.add(scorePer);
			}
		}
		
		
		/*
		 * everything within the Internet Score section
		 */
		JPanel score_panel = new JPanel();
		score_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		score_panel.setBackground(aa_grey);
		
		JLabel score;
		if(monitorInfo.getInternetScore() == -1) {
			score = new JLabel("Internet Score: <>");
			score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
			score.setForeground(Color.white);
			
			score_panel.add(Box.createRigidArea(new Dimension(15, 0)));
			score_panel.add(score);
		}
		else {
			score = new JLabel("Internet Score: " + monitorInfo.getInternetScore());
			score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
			score.setForeground(Color.white);
			
			score_panel.add(Box.createRigidArea(new Dimension(15, 0)));
			score_panel.add(score);
		}
		
		
		/*
		 * add everything to internet_panel
		 */
		internet_panel.add(urlsLabel_panel);
		internet_panel.add(urls_panel);
		internet_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		internet_panel.add(urlInfo_panel);
		internet_panel.add(urlData_panel);
		internet_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		internet_panel.add(score_panel);
		
		//add to internet_panel to card_panel
		card_panel.add("internet", internet_panel);
	}
	
	/**
	 * RHS display for OS Events Tracking sub menu
	 */
	private void osEventsPanel(JPanel card_panel, ObserverInfo monitorInfo) {
		
		JPanel os_panel = new JPanel();
		os_panel.setLayout(new BoxLayout(os_panel, BoxLayout.Y_AXIS));
		os_panel.setBackground(aa_grey);
		
		
		/*
		 * everything within the Whitelisted Applications section
		 */
		JPanel wl_panel = new JPanel();
		wl_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		wl_panel.setBackground(aa_grey);
		wl_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel wl = new JLabel("<html><u>Whitelisted Applications:</u></html>");
		wl.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		wl.setForeground(Color.white);
		
		wl_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		wl_panel.add(wl);
		
		JPanel wlapps_panel = new JPanel();
		wlapps_panel.setLayout(new GridLayout(0, 1));
		wlapps_panel.setBackground(aa_grey);
		wlapps_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel wlapp;
		ArrayList<String> wlapps = monitorInfo.getWlApps();
		for(int i = 0; i < wlapps.size(); i++) {
			wlapp = new JLabel("       " + wlapps.get(i));
			wlapp.setFont(new Font("Serif", Font.BOLD, 17));
			wlapp.setForeground(Color.white);

			wlapps_panel.add(wlapp);	
		}
		
		
		/*
		 * everything within the Whitelisted Applications Open section
		 */
		JPanel wlopen_panel = new JPanel();
		wlopen_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		wlopen_panel.setBackground(aa_grey);
		wlopen_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel wlopen = new JLabel("<html><u>Whitelisted Applications Open:</u></html>");
		wlopen.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		wlopen.setForeground(Color.white);
		
		wlopen_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		wlopen_panel.add(wlopen);
		
		JPanel wlappsOpen_panel = new JPanel();
		wlappsOpen_panel.setLayout(new GridLayout(0, 1));
		wlappsOpen_panel.setBackground(aa_grey);
		wlappsOpen_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel wlappOpen;
		ArrayList<String> wlappsOpen = monitorInfo.getWlAppsOpen();
		for(int i = 0; i < wlappsOpen.size(); i++) {
			wlappOpen = new JLabel("       " + wlappsOpen.get(i));
			wlappOpen.setFont(new Font("Serif", Font.BOLD, 17));
			wlappOpen.setForeground(Color.white);

			wlappsOpen_panel.add(wlappOpen);	
		}
		
		
		/*
		 * everything within the Blacklisted Applications section
		 */
		JPanel bl_panel = new JPanel();
		bl_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		bl_panel.setBackground(aa_grey);
		bl_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel bl = new JLabel("<html><u>Blacklisted Applications:</u></html>");
		bl.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		bl.setForeground(Color.white);
		
		bl_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		bl_panel.add(bl);
		
		JPanel blapps_panel = new JPanel();
		blapps_panel.setLayout(new GridLayout(0, 1));
		blapps_panel.setBackground(aa_grey);
		blapps_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel blapp;
		ArrayList<String> blapps = monitorInfo.getBlApps();
		for(int i = 0; i < blapps.size(); i++) {
			blapp = new JLabel("       " + blapps.get(i));
			blapp.setFont(new Font("Serif", Font.BOLD, 17));
			blapp.setForeground(Color.white);

			blapps_panel.add(blapp);	
		}
		
		
		/*
		 * everything within the Blacklisted Applications Open section
		 */
		JPanel blopen_panel = new JPanel();
		blopen_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		blopen_panel.setBackground(aa_grey);
		blopen_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel blopen = new JLabel("<html><u>Blacklisted Applications Open:</u></html>");
		blopen.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		blopen.setForeground(Color.white);
		
		blopen_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		blopen_panel.add(blopen);
		
		JPanel blappsOpen_panel = new JPanel();
		blappsOpen_panel.setLayout(new GridLayout(0, 1));
		blappsOpen_panel.setBackground(aa_grey);
		blappsOpen_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel blappOpen;
		ArrayList<String> blappsOpen = monitorInfo.getBlAppsOpen();
		for(int i = 0; i < blappsOpen.size(); i++) {
			blappOpen = new JLabel("       " + blappsOpen.get(i));
			blappOpen.setFont(new Font("Serif", Font.BOLD, 17));
			blappOpen.setForeground(Color.white);

			blappsOpen_panel.add(blappOpen);	
		}
		
		
		/*
		 * everything within the OS Events Score section
		 */
		JPanel score_panel = new JPanel();
		score_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		score_panel.setBackground(aa_grey);
		
		JLabel score = new JLabel("OS Events Score: " + monitorInfo.getOsEventsScore());
		score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		score.setForeground(Color.white);
		
		score_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		score_panel.add(score);
		
		
		/*
		 * add everything to os_panel
		 */
		os_panel.add(wl_panel);
		os_panel.add(wlapps_panel);
		os_panel.add(Box.createRigidArea(new Dimension(0, 25)));
		os_panel.add(wlopen_panel);
		os_panel.add(wlappsOpen_panel);
		os_panel.add(Box.createRigidArea(new Dimension(0, 25)));
		os_panel.add(bl_panel);
		os_panel.add(blapps_panel);
		os_panel.add(Box.createRigidArea(new Dimension(0, 25)));
		os_panel.add(blopen_panel);
		os_panel.add(blappsOpen_panel);
		os_panel.add(Box.createRigidArea(new Dimension(0, 25)));
		os_panel.add(score_panel);
		
		JScrollPane scroll_os = new JScrollPane(os_panel); 
		scroll_os.setBackground(aa_grey); 
		//scroll_os.setMaximumSize(new Dimension(105, 500)); 
		
		//add to os_panel to card_panel
		card_panel.add("osEvents", scroll_os);
	}
	
	/**
	 * RHS display for Keyboard Tracking sub menu
	 */
	private void keyboardPanel(JPanel card_panel, ObserverInfo monitorInfo) {
		
		JPanel keyboard_panel = new JPanel();
		keyboard_panel.setLayout(new BoxLayout(keyboard_panel, BoxLayout.Y_AXIS));
		keyboard_panel.setBackground(aa_grey);
		
		
		/*
		 * everything within the Words Typed section
		 */
		JPanel words_panel = new JPanel();
		words_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		words_panel.setBackground(aa_grey);
		words_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel words = new JLabel("<html><u>Words Typed:</u></html>");
		words.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		words.setForeground(Color.white);
		
		words_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		words_panel.add(words);
		
		JPanel wordsTyped_panel = new JPanel();
		wordsTyped_panel.setLayout(new GridLayout(0, 3));
		wordsTyped_panel.setBackground(aa_grey);
		wordsTyped_panel.setMaximumSize(new Dimension(425, 425));
		
		JLabel wordsTyped;
		String[] input = monitorInfo.getWordsTyped().split("\\s+");;
		for(int i = 0; i < input.length; i++) {
			wordsTyped = new JLabel("       " + input[i]);
			wordsTyped.setFont(new Font("Serif", Font.BOLD, 15));
			wordsTyped.setForeground(Color.white);

			wordsTyped_panel.add(wordsTyped);	
		}
		
		
		/*
		 * everything within the Number of Keywords section
		 */
		JPanel keywords_panel = new JPanel();
		keywords_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		keywords_panel.setBackground(aa_grey);
		keywords_panel.setMaximumSize(new Dimension(425, 25));
		
		JLabel keywords = new JLabel("Number of Keywords: " + monitorInfo.getNumKeywordsTyped());
		keywords.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		keywords.setForeground(Color.white);
		
		keywords_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		keywords_panel.add(keywords);
		
		
		/*
		 * everything within the Keyboard Score section
		 */
		JPanel score_panel = new JPanel();
		score_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		score_panel.setBackground(aa_grey);
		
		JLabel score = new JLabel("Keyboard Score: " + monitorInfo.getKeyboardScore());
		score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		score.setForeground(Color.white);
		
		score_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		score_panel.add(score);
		
		
		/*
		 * add everything to keyboard_panel
		 */
		keyboard_panel.add(words_panel);
		keyboard_panel.add(wordsTyped_panel);
		keyboard_panel.add(Box.createRigidArea(new Dimension(0, 25)));
		keyboard_panel.add(keywords_panel);
		keyboard_panel.add(Box.createRigidArea(new Dimension(0, 25)));
		keyboard_panel.add(score_panel);
		
		//add to keyboard_panel to card_panel
		card_panel.add("keyboard", keyboard_panel);
	}
	
	/**
	 * RHS display for Mouse Tracking sub menu
	 */
	private void mousePanel(JPanel card_panel, ObserverInfo monitorInfo) {
		
		JPanel mouse_panel = new JPanel();
		mouse_panel.setLayout(new BoxLayout(mouse_panel, BoxLayout.Y_AXIS));
		mouse_panel.setBackground(aa_grey);
		
		
		/*
		 * everything within the Current Mouse Score section
		 */
		JPanel currentscore_panel = new JPanel();
		currentscore_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		currentscore_panel.setBackground(aa_grey);
		currentscore_panel.setMaximumSize(new Dimension(425, 50));
		
		JLabel currentscore = new JLabel("Current Mouse Score: " + monitorInfo.getCurrentMouseScore());
		currentscore.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		currentscore.setForeground(Color.white);
		
		currentscore_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		currentscore_panel.add(currentscore);
		
		
		/*
		 * everything within the Last Mouse Score section
		 */
		/*JPanel lastscore_panel = new JPanel();
		lastscore_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lastscore_panel.setBackground(aa_grey);
		lastscore_panel.setMaximumSize(new Dimension(425, 50));
		
		JLabel lastscore = new JLabel("Last Mouse Score: " + monitorInfo.getLastMouseScore());
		lastscore.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		lastscore.setForeground(Color.white);
		
		lastscore_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		lastscore_panel.add(lastscore);
		
		*/
		/*
		 * everything within the Mouse Score section
		 */
		JPanel score_panel = new JPanel();
		score_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		score_panel.setBackground(aa_grey);
		score_panel.setMaximumSize(new Dimension(425, 50));
		
		JLabel score = new JLabel("Mouse Score: " + monitorInfo.getMouseScore());
		score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		score.setForeground(Color.white);
		
		score_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		score_panel.add(score);
		
		
		/*
		 * add everything to mouse_panel
		 */
		mouse_panel.add(currentscore_panel);
		//mouse_panel.add(lastscore_panel);
		mouse_panel.add(score_panel);
		
		//add to mouse_panel to card_panel
		card_panel.add("mouse", mouse_panel);
	}
	
	/**
	 * Title menu containing Title of the observer monitor info window
	 * as well as the guide and exit buttons
	 * 
	 * @param JFrame frame for title menu bar
	 * @return JMenuBar
	 */
	private JMenuBar titlePanel(JFrame frame) {
		JMenuBar title_panel = new JMenuBar();
		title_panel.setBorder(line);
		title_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));	
		title_panel.setBackground(aa_grey);
		title_panel.setBorder(BorderFactory.createLineBorder(aa_purple));
		
		/*
		 * allows drag and drop of frame
		 */
		title_panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				frame.setLocation(frame.getX() + e.getX() - mouseX, frame.getY() + e.getY() - mouseY);
			}
		});
		
		title_panel.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});

		JLabel title = new JLabel("Observer Monitor Details");
		title.setForeground(Color.white);
		title.setBounds(0,0,100,100);
		title.setFont(new Font("Serif", Font.BOLD, 20));
		
		/*
		 * create icons to use as buttons for title bar
		 */
		BufferedImage ci = null;
		
		try {
			ci = ImageIO.read(new File("images/exit_circle.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Image c_img = ci.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		Icon close = new ImageIcon(c_img);
		
		JButton close_window = new JButton(close);
		close_window.setBorderPainted(false);
		close_window.setContentAreaFilled(false);
		close_window.setFocusPainted(false);
		close_window.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//close window without saving 
        		frame.dispose();
        	
        }});
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(250, 0)));
		title_panel.add(close_window);
		
		return title_panel;
	}
	
	/**
	 * General window for the observer monitor display details
	 */
	public void monitorDetails(ObserverInfo monitorInfo) {
		EventQueue.invokeLater(new Runnable() {
			@Override 
			public void run() {
		
				JFrame observer_frame = new JFrame();

				int screenWidth = (int)screen.getWidth();
				int screenHeight = (int)screen.getHeight();
				observer_frame.setLocation(screenWidth - width, (screenHeight - height) / 2);
				
				observer_frame.setUndecorated(true);
				observer_frame.setPreferredSize(new Dimension(width, height)); 

				JPanel masterPanel = new JPanel(new BorderLayout());
				masterPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, aa_purple));
				masterPanel.setBackground(new Color(51, 51, 51));
				
				JMenuBar titlePanel = titlePanel(observer_frame);
				titlePanel.setBorder(line);

				//card layout and panel for RHS of settings that displays sub-menu with each button option 
				JPanel card_panel = new JPanel();
				//creates layout for sub-menus and panels for each sub-menu
				CardLayout card_layout = new CardLayout();
				card_panel.setLayout(card_layout);
				
				monitorOverviewPanel(card_panel, monitorInfo);
				facialRecognitionPanel(card_panel, monitorInfo);
				internetPanel(card_panel, monitorInfo);
				osEventsPanel(card_panel, monitorInfo);
				keyboardPanel(card_panel, monitorInfo);
				mousePanel(card_panel, monitorInfo);
				
				/*
				 * buttons for sidebar
				 */
				JButton general = new JButton("<html><center>Monitor" + "<br/>Overview</center></html>");
				JButton face = new JButton("<html><center>Facial Recognition" + "<br/>Tracking</center></html>");
				JButton internet = new JButton("<html><center>Internet" + "<br/>Tracking</center></html>");
				JButton osEvents = new JButton("<html><center>OS Events" + "<br/>Tracking</center></html>");
				JButton keyboard = new JButton("<html><center>Keyboard" + "<br/>Tracking</center></html>");
				JButton mouse = new JButton("<html><center>Mouse" + "<br/>Tracking</center></html>");
				
				/*
				 * specifications for Monitor Overview button
				 */
				general.setForeground(Color.white);
				general.setFont(new Font("Serif", Font.BOLD, 16));
				general.setContentAreaFilled(true);
				general.setBorderPainted(true);
				general.setBorder(new LineBorder(aa_purple));
				general.setFocusPainted(false);
				general.setBackground(aa_grey);
				general.setMaximumSize(new Dimension(175, 47));
				general.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open monitor overview sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						face.setBorderPainted(false);
						internet.setBorderPainted(false);
						osEvents.setBorderPainted(false);
						keyboard.setBorderPainted(false);
						mouse.setBorderPainted(false);
						general.setBorderPainted(true);
						general.setBorder(new LineBorder(aa_purple));
						card_layout.show(card_panel, "overview");						
					}
				});
				
				/*
				 * specifications for Facial Recognition Tracking button
				 */
				face.setForeground(Color.white);
				face.setFont(new Font("Serif", Font.BOLD, 15));
				face.setContentAreaFilled(true);
				face.setBorderPainted(false);
				face.setFocusPainted(false);
				face.setBackground(aa_grey);
				face.setMaximumSize(new Dimension(175, 47));
				face.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open facial recognition tracking sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						general.setBorderPainted(false);
						internet.setBorderPainted(false);
						osEvents.setBorderPainted(false);
						keyboard.setBorderPainted(false);
						mouse.setBorderPainted(false);
						face.setBorderPainted(true);
						face.setBorder(new LineBorder(aa_purple));
						card_layout.show(card_panel, "face");
					}
				});
				
				/*
				 * specifications for Internet Tracking button 
				 */
				internet.setForeground(Color.white);
				internet.setFont(new Font("Serif", Font.BOLD, 16));
				internet.setContentAreaFilled(true);
				internet.setBorderPainted(false);
				internet.setFocusPainted(false);
				internet.setBackground(aa_grey);
				internet.setMaximumSize(new Dimension(175, 47));
				internet.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open internet tracking sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						general.setBorderPainted(false);
						face.setBorderPainted(false);
						osEvents.setBorderPainted(false);
						keyboard.setBorderPainted(false);
						mouse.setBorderPainted(false);
						internet.setBorderPainted(true);
						internet.setBorder(new LineBorder(aa_purple));
						card_layout.show(card_panel, "internet");
					}
				});
				
				/*
				 * specifications for OS Events Tracking button
				 */
				osEvents.setForeground(Color.white);
				osEvents.setFont(new Font("Serif", Font.BOLD, 16));
				osEvents.setContentAreaFilled(true);
				osEvents.setBorderPainted(false);
				osEvents.setFocusPainted(false);
				osEvents.setBackground(aa_grey);
				osEvents.setMaximumSize(new Dimension(175, 47));
				osEvents.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open os events tracking sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						general.setBorderPainted(false);
						face.setBorderPainted(false);
						internet.setBorderPainted(false);
						keyboard.setBorderPainted(false);
						mouse.setBorderPainted(false);
						osEvents.setBorderPainted(true);
						osEvents.setBorder(new LineBorder(aa_purple));
						card_layout.show(card_panel, "osEvents");
					}
				});
				
				/*
				 * specifications for Keyboard Tracking button 
				 */
				keyboard.setForeground(Color.white);
				keyboard.setFont(new Font("Serif", Font.BOLD, 16));
				keyboard.setContentAreaFilled(true);
				keyboard.setBorderPainted(false);
				keyboard.setFocusPainted(false);
				keyboard.setBackground(aa_grey);
				keyboard.setMaximumSize(new Dimension(175, 47));
				keyboard.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open keyboard tracking sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						general.setBorderPainted(false);
						face.setBorderPainted(false);
						internet.setBorderPainted(false);
						osEvents.setBorderPainted(false);
						mouse.setBorderPainted(false);
						keyboard.setBorderPainted(true);
						keyboard.setBorder(new LineBorder(aa_purple));
						
						card_layout.show(card_panel, "keyboard");
					}
				});
				
				/*
				 * specifications for Mouse Tracking button
				 */
				mouse.setForeground(Color.white);
				mouse.setFont(new Font("Serif", Font.BOLD, 16));
				mouse.setContentAreaFilled(true);
				mouse.setBorderPainted(false);
				mouse.setFocusPainted(false);
				mouse.setBackground(aa_grey);
				mouse.setMaximumSize(new Dimension(175, 47));
				mouse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open mouse tracking sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						general.setBorderPainted(false);
						face.setBorderPainted(false);
						internet.setBorderPainted(false);
						osEvents.setBorderPainted(false);
						keyboard.setBorderPainted(false);
						mouse.setBorderPainted(true);
						mouse.setBorder(new LineBorder(aa_purple));
						
						card_layout.show(card_panel, "mouse");
					}
				});
				
				/*
				 * creates layout for left side panel and buttons for side-bar sub-menus 
				 */ 
				JPanel sideMenu = new JPanel();
				sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
				sideMenu.setPreferredSize(new Dimension(160, 675));
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(general);
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(face);
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(internet);
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(osEvents);
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(keyboard);
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(mouse);
				sideMenu.setBackground(new Color(51, 51, 51));
				sideMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, aa_purple));
				
				/*
				 * creates split center panel
				 */
				JPanel center_panel = new JPanel(new BorderLayout());
				center_panel.setBackground(new Color(51, 51, 51));
				center_panel.add(card_panel, BorderLayout.CENTER);
				center_panel.add(sideMenu, BorderLayout.WEST);
				
				/*
				 * populates master panel 
				 */
				masterPanel.add(titlePanel, BorderLayout.PAGE_START); 
				masterPanel.add(center_panel, BorderLayout.CENTER);
			
				/*
				 * adds master panel to frame
				 */
				observer_frame.getContentPane().add(masterPanel); 
				observer_frame.getContentPane().setBackground(new Color(51, 51, 51));
				observer_frame.pack();
				observer_frame.setAlwaysOnTop(false);
				observer_frame.setVisible(true);
				observer_frame.setResizable(true);
			}
		});
	}
}
