/**
 * Class that contains information on Notification System created by 
 * events throughout the Attention Assistant
 */
package AttentionAssistant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
 
public class Notification_System {
	//observe runs monitoring every 5 minutes, allows for time tracking 
	private int userID;
	int timeDistracted=0; 
	int timeFocused=0;
	Settings settings;
	private boolean isAudioActive;
	private boolean ttsActive;
	private boolean isAvatarActive;
	private String avatarPath;
	private int avatarSize;
	private boolean avatarAlwaysOn;
	DataBase db;
	AudioHandler audio;
	Priority_Manager pm;
	private String userName;
	User_Account user;
	Pomodoro_Timer pomo;
	 
	public Notification_System(int userID,DataBase db) throws IOException{
		this.settings = new Settings(db,userID);
		this.timeDistracted = 0;
		this.timeFocused = 0;
		this.userID = userID;
		this.isAudioActive = settings.getAudioIsActive();
		this.ttsActive = settings.getTextToSpeech();
		this.isAvatarActive = settings.getAvatarIsActive();
		this.avatarPath = settings.getAvatarFilePath();
		this.avatarSize = 100;
		this.avatarAlwaysOn = false;
		this.db = db;
		user = db.SelectUser_Account(userID);
		userName = user.getName();
		this.pm = new Priority_Manager(userID,db,false);
	}
	
	public void setPomo(Pomodoro_Timer pomo) {
		this.pomo = pomo;
	}
	
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	int height = (int) screen.getHeight();
	
	private void displayNotif(String text,String type) {
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		frame.setLocation(10,height-220);
		
		JMenuBar close = new JMenuBar();
		close.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		close.setBorderPainted(false);
		
		BufferedImage exit = null;
		try {
			exit = ImageIO.read(new File("images/exit_circle.png"));
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		Image c_img = exit.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		JButton exitButton = new JButton();
		exitButton.setIcon(new ImageIcon(c_img));
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusable(false);
		exitButton.setBorderPainted(false);
		exitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//close window without saving 
        		frame.dispose();
        	
        }});
		
		close.add(exitButton);
		frame.setJMenuBar(close);
		
		AudioHandler audio = new AudioHandler();
		boolean isIgnored=false;
		Date date = new Date();
		if(isAudioActive == true) {
			audio.playNotificationType(type);
		}
		if(ttsActive == true) {
			/* This will say whatever is in String text */
			audio.notificationTTS(text);
		}
		JPanel notifPanel = new JPanel();
		notifPanel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		notifPanel.setLayout(new BoxLayout(notifPanel,BoxLayout.X_AXIS));
		if(isAvatarActive == true) {
			//display text in speech bubble
			//Display avatar
			//string to place in text bubble
			JLabel avatarLabel = new JLabel();
			JLabel bubbleLabel = new JLabel();
			
			BufferedImage avatar = null;
			BufferedImage bubble = null;
			try {
				//gets image for specific buttons icon
				avatar = ImageIO.read(new File(avatarPath));
				//gets circle image
				bubble = ImageIO.read(new File("images/speechBubble.png"));
			}catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
			
			//add text to bubble			
			Font font = new Font("Arial",Font.BOLD,16);

			Graphics g = bubble.getGraphics();
			g.setFont(font);
			g.setColor(Color.black);
			int x=40,y=25;
			for (String line : text.split("\n")) {
				g.drawString(line,x,y);
				y += 20;
			}
			
			avatarLabel.setIcon(new ImageIcon(avatar));
			bubbleLabel.setIcon(new ImageIcon(bubble));
			
			notifPanel.add(avatarLabel);
			notifPanel.add(bubbleLabel);
			
		}else {
			//display text in text bubble
			JLabel textBox = new JLabel();
			
			BufferedImage textBubble = null;
			try {
				//gets circle image
				textBubble = ImageIO.read(new File("images/textBubble.png"));
			}catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
			
			//add text to bubble			
			Font font = new Font("Arial",Font.BOLD,16);

			Graphics g = textBubble.getGraphics();
			g.setFont(font);
			g.setColor(Color.black);
			int x=40,y=25;
			for (String line : text.split("\n")) {
				g.drawString(line,x,y);
				y += 20;
			}
			textBox.setIcon(new ImageIcon(textBubble));
	
			notifPanel.add(textBox);
		}
		
		//display notification, return isIgnored
		Notification notif = new Notification();
		notif.setDT_Notification(date);
		notif.setType(type);
		notif.setIgnored(isIgnored);
		db.AddNotification(notif,userID);
		
		frame.getContentPane().add(notifPanel);
		frame.pack();
		frame.setVisible(true);
		new Timer(30_000, (e) -> { frame.setVisible(false); frame.dispose(); }).start();
	}
	
	private int min = 1;
	private int max = 4;
	
	public void resumePomo() {
		//on-task & null
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = " You're working but \n your timer is paused!";
	        		break;
	        case 2: text = " Unpause your timer to \n continue your work \n period countdown!";
	                break;
	        case 3:  text = " Your timer is paused \n but you're still \n working";
	                break;
	        case 4: text = " Don't forget to unpause \n your Pomodoro Timer!";
	                break;
            default: text = " Resume pomo - randoNum \n out of range";
            		break;
		}
		
		timeFocused += 5;
		Date timestamp = new Date();
		System.out.println(timestamp);
		db.AddEvent(userID, timestamp, "focus");
		displayNotif(text,"resume");
	}
	
	
	public void isNull() {
		//if off-task & null OR off-task & in break period
		String text = " We are paused and on break";
		displayNotif(text,"paused");
	}
	
	
	public void distracted(Task task) {
		//in work period & off-task
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = " Hey buddy! Let's get \n back to work!";
	                 break;
	        case 2: text = " Brotato-Chip! Why \n don't we get back to \n "+ task.getTaskName()+"?";
	                 break;
	        case 3:  text = " Hey "+userName+"! \n we should get back \n on task!";
	                 break;
	        case 4: text = " "+userName+"! \n We gotta finish \n "+ task.getTaskName()+"!";
	                 break;
	        default: text = " Distracted - randoNum \n out of range";
    				break;
		}
		timeDistracted += 5;
		Date timestamp = new Date();
		System.out.println(timestamp);
		db.AddEvent(userID, timestamp, "distract");
		displayNotif(text, "distracted");
	}
	
	
	public void selfCare(Task task) {
		//on-task for too long with no break
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = "You've been working \n on "+ task.getTaskName()+" \n for a while, what if \n we "+pm.getNonObservableTask().getTaskName()+"?";
	                 break;
	        case 2: text = " You are KILLING it! \n You've definitely \n earned a break!";
	                 break;
	        case 3:  text = " Hey "+userName+", \n all this work is \n making me hungry... \n How about a snack?";
	                 break;
	        case 4: text = " You've been working \n for ages! Why don't \n we stretch out legs?";
	                 break;
	        default: text = " Self Care - randoNum \n out of range";
    				break;
		}
		timeFocused += 5;
		Date timestamp = new Date();
		System.out.println(timestamp);
		db.AddEvent(userID, timestamp, "focus");
		displayNotif(text,"selfCare");
	}
	
	
	public void allGood() {
		//in work period & on-task
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = " Keep up the great \n work "+userName+"!";
	                 break;
	        case 2:  text = " Way to stay on task!";
	                 break;
	        case 3:  text = " Hey "+userName+", \n you're rocking right \n along!";
	                 break;
	        case 4: text = " You'll be done before \n you know it at this \n pace!";
	                 break;
	        default: text = " Resume pomo - randoNum \n out of range";
    				break;
		}
		
		timeFocused += 5;
		Date timestamp = new Date();
		System.out.println(timestamp);
		db.AddEvent(userID, timestamp, "focus");
		displayNotif(text,"encourage");
		//add words of encouragement?
	}
	
	
	public void dueDateApproaching(Task task) {
		//due date happening soon
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = " It's almost time to \n turn in "+task.getTaskName()+"!";
	                 break;
	        case 2: text = " "+task.getTaskName()+" is \n due "+task.getStringDate()+"!";
	                 break;
	        case 3:  text = " The due date for \n "+task.getTaskName()+" is \n approaching!";
	                 break;
	        case 4: text = " Time to work on \n "+task.getTaskName()+"! \n It's due "+task.getStringDate();
	                 break;
	        default: text = " Due Date Approaching - randoNum \n out of range";
    				break;
		}
		
		displayNotif(text,"dueDate");
	}
	
	
	public void taskCompleted(Task task) {
		//task is completed
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = " "+userName+", \n you finished "+pm.getActiveTask().getTaskName()+"! \n Let's pick a new task!";
	                 break;
	        case 2: text = " You rocked "+task.getTaskName()+"! \n I'm so impressed!";
	                 break;
	        case 3:  text = " "+userName+"! All your \n hard work has paid \n off, you're done \n with "+task.getTaskName()+"!";
	                 break;
	        case 4: text = " Yay! You completed \n "+task.getTaskName()+", great job!";
	                 break;
	        default: text = " Task completed - randoNum \n out of range";
    				break;
		}
		displayNotif(text,"complete");
	}
	
	
	public void breakTime() {
		//break period start
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = " It's time to take a \n break, why not \n"+pm.getNonObservableTask().getTaskName()+"?";
	                 break;
	        case 2:  text = " You made it through \n "+settings.getWorkPeriod()+" minutes of work, \n it's time for \n "+settings.getBreakPeriod()+" of play";
	                 break;
	        case 3:  text = " Hey "+userName+", it's \n break time! See you \n back in "+settings.getBreakPeriod()+" minutes!";
	                 break;
	        case 4:  text = " You are KILLING it! \n You've definitely \n earned a break, luckily \n it's break time!";
	                 break;
	        default: text = " Resume pomo - randoNum \n out of range";
    				 break;
		}
		displayNotif(text,"break");
	}
	
	
	public void workTime(Task task) {
		//work period start
		String text = "";
		
		Random randomGenerator=new Random();
		int randoNum = randomGenerator.nextInt(max) + min;
		switch (randoNum) {
	        case 1:  text = " Break time is over, \n let's get back to \n work on "+task.getTaskName()+"!";
	                 break;
	        case 2:  text = " I hope you had a great \n break! Let's get \n back to "+task.getTaskName();
	                 break;
	        case 3:  text = " Hey "+userName+", \n it's work time! \n It's great to have \n you back!";
	                 break;
	        case 4:  text = " It's time to get back \n on task! "+task.getTaskName()+" \n is your current goal.";
	                 break;
	        default: text = " Resume pomo - randoNum \n out of range";
    				 break;
		}
		displayNotif(text,"work");
	}
}