package AttentionAssistant;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.jdatepicker.impl.*;


/**
 * Class that encompasses Progress Report whenever Progress Report is opened 
 * from the Navigation Bar, Settings Menu, or Parent Portal. 
 * @author krchr
 *
 */

public class Progress_Report {
	
	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	Color medium_grey = new Color(153,153,153);
	Color darkRed = new Color(204,0,0);
	LineBorder line = new LineBorder(aa_purple, 2, true);
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = 700; 
	private int width = 625; 
	private int mouseX;
	private int mouseY;
	JPanel summaryPanel;
	JFrame pr_frame; 
	public JFileChooser fileChooser = null;
	final static boolean shouldFill = true; 
	final static boolean shouldWeightX = true; 
	final static boolean RIGHT_TO_LEFT = false; 
	long DAY_IN_MS = 1000 * 60 * 60 * 24;
	Date dt_End = new Date();
	Date dt_Start = new Date(dt_End.getTime() - (7 * DAY_IN_MS));
	int monitorInterval = 5; 
	int loggedInInterval = 5;
	String currentCard = "summary";
	
	public void rebuildPanel(CardLayout cardLayout, JPanel reportViews, int userID, DataBase db, JPanel oldSummaryPanel, JPanel oldTasksAddedPanel, JPanel oldTasksCompletedPanel) {
			
		reportViews.remove(oldSummaryPanel);
		reportViews.remove(oldTasksAddedPanel);
		reportViews.remove(oldTasksCompletedPanel);
		
		//rebuild summary panel
		JPanel summaryPanel = createSummaryPanel(userID, db);
		reportViews.add("summary", summaryPanel);
		
		//rebuild tasksAddedPanel
		JPanel tasksAddedPanel = createTasksAddedPanel(userID, db); 
		reportViews.add("tasksAdded", tasksAddedPanel);
		
		//rebuild tasksCompletedPanel
		JPanel tasksCompletedPanel = createTasksCompletedPanel(userID, db);
		reportViews.add("tasksCompleted", tasksCompletedPanel);
		
		cardLayout.show(reportViews, currentCard); 
		reportViews.revalidate();
		reportViews.repaint();
	}
	
	/*
	 * adjusts color and/or opacity of specified icon image to specified color/opacity
	 */
	public BufferedImage colorFeatureIcon(DataBase db, int userID, BufferedImage image, String feature) {
		
		int red = 0; 
		int green = 0;
		int blue = 0;

		Color greenThreshold = new Color(0, 153, 0);
		Color yellowThreshold = new Color(255, 204, 0);
		Color redThreshold = new Color(204, 0, 0);
		
		//long diffInMilliseconds = dt_End.getTime() - dt_Start.getTime();
		//long hours = TimeUnit.HOURS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
		
		double loggedInTotal = db.CountEvents(userID, dt_Start, dt_End, "loggedIn") * loggedInInterval;
		System.out.println(loggedInTotal+" minutes");
		double loggedInHours = loggedInTotal / 60; 
		System.out.println(loggedInHours+" hours");
		
		double avg = (db.CountEvents(userID, dt_Start, dt_End, feature)) / (loggedInHours);
		if(loggedInHours < 1) {
			red = yellowThreshold.getRed();
			green = yellowThreshold.getGreen();
			blue = yellowThreshold.getBlue();
		}else {
			if(avg <= 1) {
				red = greenThreshold.getRed();
				green = greenThreshold.getGreen();
				blue = greenThreshold.getBlue();
			}else if (avg > 1 && avg <= 2) {
				red = yellowThreshold.getRed();
				green = yellowThreshold.getGreen();
				blue = yellowThreshold.getBlue();
			}else if(avg > 2) {
				red = redThreshold.getRed();
				green = redThreshold.getGreen();
				blue = redThreshold.getBlue();
			}
		}
		
		
		//get height and width of image to be altered
	    int width = image.getWidth();
	    int height = image.getHeight();
	    WritableRaster raster = image.getRaster();

	    //recolors image to new rgb values
	    for (int xx = 0; xx < width; xx++) {
	      for (int yy = 0; yy < height; yy++) {
	        int[] pixels = raster.getPixel(xx, yy, (int[]) null);
	        pixels[0] = red;
	        pixels[1] = green;
	        pixels[2] = blue;
	        raster.setPixel(xx, yy, pixels);
	      }
	    }
	    return image;
	  }
	
	/**
	 * creates title bar
	 */
	private JMenuBar createTitlePanel(Frame pr_frame) {
		
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
				pr_frame.setLocation(pr_frame.getX() + e.getX() - mouseX, pr_frame.getY() + e.getY() - mouseY);
			}
		});
		
		title_panel.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});

		JLabel title = new JLabel("Progress Report");
		title.setForeground(Color.white);
		title.setFont(new Font("Serif", Font.BOLD, 20));
		
		/*
		 * create icons to use as buttons for title bar
		 */
		BufferedImage ci = null;
		BufferedImage gi = null;
		
		try {
			ci = ImageIO.read(new File("images/exit_circle.png"));
			gi = ImageIO.read(new File("images/guide.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
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
        		pr_frame.dispose();
        	
        }});
		
		Image g_img = gi.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		Icon guideIcon = new ImageIcon(g_img);
		
		JButton guide = new JButton(guideIcon);
		guide.setBorderPainted(false);
		guide.setContentAreaFilled(false);
		guide.setFocusPainted(false);
		guide.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//opens guide
        		Guide guide = new Guide();
				guide.open_Guide("Progress Report");
        }});
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(335, 0)));
		title_panel.add(guide);
		title_panel.add(close_window);
		
		return title_panel;
		
	}
	
	private JPanel createDatePickers(CardLayout cardLayout, JPanel reportViews, int userID, DataBase db, JPanel summaryPanel, JPanel tasksAddedPanel, JPanel tasksCompletedPanel){
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
		datePanel.setBackground(Color.black);
		datePanel.setMaximumSize(new Dimension(600, 80));
		
		JPanel dateIntervalPanel = new JPanel();
		dateIntervalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		dateIntervalPanel.setMaximumSize(new Dimension(600, 35));
		
		JLabel startDate = new JLabel("Start Date: ");
		startDate.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 17));
		startDate.setBackground(Color.black);
		
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year"); 
		
		JDatePanelImpl beginDatePanel = new JDatePanelImpl(model, properties);
		JDatePickerImpl beginDatePicker = new JDatePickerImpl(beginDatePanel, new DateLabelFormatter());
		
		JLabel endDate = new JLabel("End Date:  ");
		endDate.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 17));
		endDate.setBackground(Color.black);
		
		UtilDateModel model2 = new UtilDateModel();
		Properties properties2 = new Properties();
		properties2.put("text.today", "Today");
		properties2.put("text.month", "Month");
		properties2.put("text.year", "Year"); 
		
		JDatePanelImpl finishDatePanel = new JDatePanelImpl(model2, properties2);
		JDatePickerImpl finishDatePicker = new JDatePickerImpl(finishDatePanel, new DateLabelFormatter());
		
		dateIntervalPanel.add(startDate);
		dateIntervalPanel.add(beginDatePicker);
		dateIntervalPanel.add(endDate);
		dateIntervalPanel.add(finishDatePicker);
		
		JPanel applyPanel = new JPanel();
		applyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		applyPanel.setMaximumSize(new Dimension(600, 35));
		
		JButton apply = new JButton(" apply ");
		apply.setForeground(Color.white);
		apply.setFont(new Font("Serif", Font.BOLD, 16));
		apply.setContentAreaFilled(true);
		apply.setBorderPainted(true);
		apply.setBorder(new LineBorder(aa_grey));
		apply.setFocusPainted(false);
		apply.setBackground(aa_purple);
		apply.setMaximumSize(new Dimension(75,30));
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//refresh frame to reflect selected dates
				
				dt_Start = (Date) beginDatePicker.getModel().getValue();
				dt_End = (Date) finishDatePicker.getModel().getValue();
				
				rebuildPanel(cardLayout, reportViews, userID, db, summaryPanel, tasksAddedPanel, tasksCompletedPanel);
			}
		});
		
		applyPanel.add(Box.createRigidArea(new Dimension(530, 0)));
		applyPanel.add(apply);
		
		datePanel.add(dateIntervalPanel);	
		datePanel.add(applyPanel);
		
		return datePanel;
	}
	
	private JPanel createSummaryPanel(int userID, DataBase db) {
		
		summaryPanel = new JPanel();
		summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
		summaryPanel.setMaximumSize(new Dimension(600, 550));
		
		JPanel datesPanel = new JPanel();
		datesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		datesPanel.setMaximumSize(new Dimension(550, 50));
		datesPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, aa_purple));
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(dt_Start);
		startCalendar.add(Calendar.MONTH, 1);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(dt_End); 
		endCalendar.add(Calendar.MONTH, 1);
		
		JLabel dates = new JLabel (startCalendar.get(Calendar.MONTH) + "/" + startCalendar.get(Calendar.DAY_OF_MONTH) + "/" + startCalendar.get(Calendar.YEAR) + " - " + endCalendar.get(Calendar.MONTH) + "/" + endCalendar.get(Calendar.DAY_OF_MONTH) + "/" + endCalendar.get(Calendar.YEAR)+" "); 
		dates.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 35));
		dates.setBackground(Color.black);
		dates.setForeground(aa_grey);
		
		datesPanel.add(Box.createRigidArea(new Dimension(110, 0)));
		datesPanel.add(dates);
		
		JPanel tasks = new JPanel();
		tasks.setLayout(new BoxLayout(tasks, BoxLayout.Y_AXIS));
		tasks.setMaximumSize(new Dimension(550, 500));
		
		JPanel taskHeaderPanel = new JPanel();
		taskHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		taskHeaderPanel.setMaximumSize(new Dimension(550, 50));
		
		JLabel tasksHeader = new JLabel("Tasks:"); 
		tasksHeader.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
		tasksHeader.setBackground(Color.black);
		
		JLabel taskOverdue = new JLabel("overdue tasks: ");
		taskOverdue.setFont(new Font("Serif", Font.BOLD, 16));
		taskOverdue.setBackground(Color.black);
		
		ArrayList<Task> Task_List = db.SelectAllAddedTasks(userID, dt_Start, dt_End); 
		int overDueTasks = 0; 
		
		for(Task task : Task_List) {
			if(task.getStatus() == TaskStatus.OVERDUE) {
				overDueTasks++;
			}
		}
		
		JLabel totalOverdue = new JLabel(Integer.toString(overDueTasks));
		totalOverdue.setFont(new Font("Serif", Font.BOLD, 22));
		totalOverdue.setBackground(Color.black);
		totalOverdue.setForeground(darkRed);
		
		taskHeaderPanel.add(tasksHeader);
		taskHeaderPanel.add(Box.createRigidArea(new Dimension(320, 0)));
		taskHeaderPanel.add(taskOverdue);
		taskHeaderPanel.add(totalOverdue);
		
		JPanel taskTotals = new JPanel();
		taskTotals.setLayout(new FlowLayout(FlowLayout.LEFT));
		taskTotals.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, aa_purple));
		taskTotals.setMaximumSize(new Dimension(600, 140));
		
		BufferedImage star = null;
		try {
			star = ImageIO.read(new File("images/star.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Image star_img = star.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		Icon starMedal = new ImageIcon(star_img);
		JLabel displayMedal = new JLabel(starMedal);
		
		JPanel taskColumn1 = new JPanel();
		taskColumn1.setLayout(new BoxLayout(taskColumn1, BoxLayout.Y_AXIS));
		taskColumn1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		JPanel taskRow1 = new JPanel();
		taskRow1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel totalCompleted = new JLabel(Integer.toString(db.CountEvents(userID, dt_Start, dt_End, "complete")));
		totalCompleted.setFont(new Font("Serif", Font.BOLD, 25));
		totalCompleted.setBackground(Color.black);
		
		JLabel taskCompleted = new JLabel("completed");
		taskCompleted.setFont(new Font("Serif", Font.BOLD, 19));
		taskCompleted.setBackground(Color.black);
		
		taskRow1.add(totalCompleted);
		taskRow1.add(taskCompleted);
		
		JPanel taskRow2 = new JPanel();
		taskRow2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel totalAdded = new JLabel(Integer.toString(db.CountEvents(userID, dt_Start, dt_End, "add")));
		totalAdded.setFont(new Font("Serif", Font.BOLD, 25));
		totalAdded.setBackground(Color.black);
		
		JLabel taskAdded = new JLabel("added");
		taskAdded.setFont(new Font("Serif", Font.BOLD, 19));
		taskAdded.setBackground(Color.black);
		
		taskRow2.add(totalAdded);
		taskRow2.add(taskAdded);
		
		taskColumn1.add(taskRow1);
		taskColumn1.add(taskRow2);
		
		JPanel taskColumn2 = new JPanel();
		taskColumn2.setLayout(new BoxLayout(taskColumn2, BoxLayout.Y_AXIS));
		taskColumn2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		JPanel taskRow3 = new JPanel();
		taskRow3.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel totalStarted = new JLabel(Integer.toString(db.CountEvents(userID, dt_Start, dt_End, "started")));
		totalStarted.setFont(new Font("Serif", Font.BOLD, 25));
		totalStarted.setBackground(Color.black);
		
		JLabel taskStarted = new JLabel("started");
		taskStarted.setFont(new Font("Serif", Font.BOLD, 19));
		taskStarted.setBackground(Color.black);
		
		taskRow3.add(totalStarted);
		taskRow3.add(taskStarted);
		
		JPanel taskRow4 = new JPanel();
		taskRow4.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		int inProgressTotal = db.CountEvents(userID, dt_Start, dt_End, "started") - db.CountEvents(userID, dt_Start, dt_End, "complete"); 
		
		JLabel totalInProgress = new JLabel(Integer.toString(inProgressTotal));
		totalInProgress.setFont(new Font("Serif", Font.BOLD, 25));
		totalInProgress.setBackground(Color.black);
		
		JLabel taskProgress = new JLabel("in progress");
		taskProgress.setFont(new Font("Serif", Font.BOLD, 19));
		taskProgress.setBackground(Color.black);
		
		taskRow4.add(totalInProgress);
		taskRow4.add(taskProgress);
		
		taskColumn2.add(taskRow3);
		taskColumn2.add(taskRow4);
		
		JPanel taskColumn3 = new JPanel();
		taskColumn3.setLayout(new BoxLayout(taskColumn3, BoxLayout.Y_AXIS));
		
		JPanel taskRow5 = new JPanel();
		taskRow5.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel timeOnTask = new JLabel("on task: ");
		timeOnTask.setFont(new Font("Serif", Font.BOLD, 19));
		timeOnTask.setBackground(Color.black);
		
		int onTaskTotal = db.CountEvents(userID, dt_Start, dt_End, "focus") * monitorInterval;
		int onTaskHours = onTaskTotal / 60; 
		int onTaskMinutes = onTaskTotal - (60 * onTaskHours); 
		
		JLabel totalOnTask = new JLabel(Integer.toString(onTaskHours) + "hr " + Integer.toString(onTaskMinutes) + "min");
		totalOnTask.setFont(new Font("Serif", Font.BOLD, 20));
		totalOnTask.setBackground(Color.black);
		
		taskRow5.add(timeOnTask);
		taskRow5.add(totalOnTask);
		
		JPanel taskRow6 = new JPanel();
		taskRow6.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel timeOffTask = new JLabel("off task: ");
		timeOffTask.setFont(new Font("Serif", Font.BOLD, 19));
		timeOffTask.setBackground(Color.black);
		
		int offTaskTotal = db.CountEvents(userID, dt_Start, dt_End, "distract") * monitorInterval;
		int offTaskHours = offTaskTotal / 60; 
		int offTaskMinutes = offTaskTotal - (60 * offTaskHours); 
		
		JLabel totalOffTask = new JLabel(Integer.toString(offTaskHours) + "hr " + Integer.toString(offTaskMinutes) + "min");
		totalOffTask.setFont(new Font("Serif", Font.BOLD, 20));
		totalOffTask.setBackground(Color.black);
		
		taskRow6.add(timeOffTask);
		taskRow6.add(totalOffTask);
		
		taskColumn3.add(taskRow5);
		taskColumn3.add(taskRow6);
		
		taskTotals.add(displayMedal);
		taskTotals.add(taskColumn1);
		taskTotals.add(taskColumn2);
		taskTotals.add(taskColumn3);
		
		tasks.add(taskHeaderPanel);
		tasks.add(taskTotals);
		
		JPanel features = new JPanel();
		features.setLayout(new BoxLayout(features, BoxLayout.Y_AXIS));
		features.setMaximumSize(new Dimension(550, 500));
		
		JPanel featuresHeaderPanel = new JPanel();
		featuresHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		featuresHeaderPanel.setMaximumSize(new Dimension(550, 50));
		
		JLabel featuresHeader = new JLabel("Features:"); 
		featuresHeader.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
		featuresHeader.setBackground(Color.black);
		
		featuresHeaderPanel.add(featuresHeader);
		
		JPanel featureTotals = new JPanel();
		featureTotals.setLayout(new BoxLayout(featureTotals, BoxLayout.Y_AXIS));
		featureTotals.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, aa_purple));
		featureTotals.setMaximumSize(new Dimension(550, 140));
		
		JPanel featureRow1 = new JPanel();
		featureRow1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		BufferedImage flames = null;
		try {
			flames = ImageIO.read(new File("images/burner.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		colorFeatureIcon(db, userID, flames, "ntb");
		Image flames_img = flames.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		Icon ntbIcon = new ImageIcon(flames_img);
		JLabel ntb_Label = new JLabel(ntbIcon);
		
		JLabel totalNTB = new JLabel(Integer.toString(db.CountEvents(userID, dt_Start, dt_End, "ntb")) + " times");
		totalNTB.setFont(new Font("Serif", Font.BOLD, 25));
		totalNTB.setBackground(Color.black);
		
		BufferedImage happyFace = null;
		try {
			happyFace = ImageIO.read(new File("images/happy_button.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		colorFeatureIcon(db, userID, happyFace, "htb");
		Image happyFace_img = happyFace.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		Icon htbIcon = new ImageIcon(happyFace_img);
		JLabel htb_Label = new JLabel(htbIcon);
		
		JLabel totalHTB = new JLabel(Integer.toString(db.CountEvents(userID, dt_Start, dt_End, "htb")) + " times");
		totalHTB.setFont(new Font("Serif", Font.BOLD, 25));
		totalHTB.setBackground(Color.black);

		featureRow1.add(Box.createRigidArea(new Dimension(50, 0)));
		featureRow1.add(ntb_Label);
		featureRow1.add(Box.createRigidArea(new Dimension(10, 0)));
		featureRow1.add(totalNTB);
		featureRow1.add(Box.createRigidArea(new Dimension(80, 0)));
		featureRow1.add(htb_Label);
		featureRow1.add(Box.createRigidArea(new Dimension(10, 0)));
		featureRow1.add(totalHTB);
		
		JPanel featureRow2 = new JPanel();
		featureRow2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		BufferedImage paint = null;
		try {
			paint = ImageIO.read(new File("images/thought_space.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		colorFeatureIcon(db, userID, paint, "fts");
		Image thought_img = paint.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		Icon ftsIcon = new ImageIcon(thought_img);
		JLabel fts_Label = new JLabel(ftsIcon);
		
		JLabel totalFTS = new JLabel(Integer.toString(db.CountEvents(userID, dt_Start, dt_End, "fts")) + " times");
		totalFTS.setFont(new Font("Serif", Font.BOLD, 25));
		totalFTS.setBackground(Color.black);
		
		featureRow2.add(Box.createRigidArea(new Dimension(200, 0)));
		featureRow2.add(fts_Label);
		featureRow2.add(Box.createRigidArea(new Dimension(10, 0)));
		featureRow2.add(totalFTS);
		
		featureTotals.add(featureRow1);
		featureTotals.add(featureRow2);
		
		features.add(featuresHeaderPanel);
		features.add(featureTotals);
		
		JPanel hyperfocus = new JPanel();
		hyperfocus.setLayout(new BoxLayout(hyperfocus, BoxLayout.Y_AXIS));
		hyperfocus.setMaximumSize(new Dimension(550, 500));
		
		JPanel hF_headerPanel = new JPanel();
		hF_headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		hF_headerPanel.setMaximumSize(new Dimension(550, 50));
		
		JLabel hf_Header = new JLabel("Hyper-focusing:"); 
		hf_Header.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
		hf_Header.setBackground(Color.black);
		
		hF_headerPanel.add(hf_Header);
		
		JPanel hF_Totals = new JPanel();
		hF_Totals.setLayout(new FlowLayout(FlowLayout.LEFT));
		hF_Totals.setMaximumSize(new Dimension(550, 140));
		
		BufferedImage magnifyingGlass = null;
		try {
			magnifyingGlass = ImageIO.read(new File("images/hf_magnifying.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Image mag_img = magnifyingGlass.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		Icon hfGlass = new ImageIcon(mag_img);
		JLabel displayMG = new JLabel(hfGlass);
		
		JPanel hF_Row1 = new JPanel();
		hF_Row1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ArrayList<Notification> selfCareNotifications = db.SelectAllNotificationsType(userID, "selfCare", dt_Start, dt_End);
		int notificationCount = selfCareNotifications.size(); 		
		
		JLabel totalHF = new JLabel(Integer.toString(notificationCount));
		totalHF.setFont(new Font("Serif", Font.BOLD, 35));
		totalHF.setBackground(Color.black);
		totalHF.setForeground(aa_purple);
		
		JLabel hF_notifications = new JLabel("self-care notifications");
		hF_notifications.setFont(new Font("Serif", Font.BOLD, 20));
		hF_notifications.setBackground(Color.black);
		
		hF_Row1.add(totalHF);
		hF_Row1.add(hF_notifications);
		
		hF_Totals.add(Box.createRigidArea(new Dimension(80, 0)));
		hF_Totals.add(displayMG);
		hF_Totals.add(Box.createRigidArea(new Dimension(5, 0)));
		hF_Totals.add(hF_Row1);
		
		hyperfocus.add(hF_headerPanel);
		hyperfocus.add(hF_Totals);
		
		summaryPanel.add(datesPanel);
		summaryPanel.add(tasks);
		summaryPanel.add(features);
		summaryPanel.add(hyperfocus);
		
		return summaryPanel;
	}
	
	private JPanel createTasksAddedPanel(int userID, DataBase db) {
		
		ArrayList<Task> Task_List = db.SelectAllAddedTasks(userID, dt_Start, dt_End); 
		
		JPanel tasksAdded = new JPanel();
		tasksAdded.setLayout(new BoxLayout(tasksAdded, BoxLayout.Y_AXIS));
		tasksAdded.setMaximumSize(new Dimension(600, 500));
		
		JPanel datesPanel = new JPanel();
		datesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		datesPanel.setMaximumSize(new Dimension(600, 50));
		datesPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, aa_purple));
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(dt_Start);
		startCalendar.add(Calendar.MONTH, 1);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(dt_End); 
		endCalendar.add(Calendar.MONTH, 1);
		
		JLabel dates = new JLabel (startCalendar.get(Calendar.MONTH) + "/" + startCalendar.get(Calendar.DAY_OF_MONTH) + "/" + startCalendar.get(Calendar.YEAR) + " - " + endCalendar.get(Calendar.MONTH) + "/" + endCalendar.get(Calendar.DAY_OF_MONTH) + "/" + endCalendar.get(Calendar.YEAR)); 
		dates.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 34));
		dates.setBackground(Color.black);
		dates.setForeground(aa_grey);
		
		datesPanel.add(Box.createRigidArea(new Dimension(135, 0)));
		datesPanel.add(dates);
		
		DefaultTableModel model = new DefaultTableModel(Task_List.size(), 0);
		JTable addedTable = new JTable(model);
		model.addColumn("Tasks Added");
		model.addColumn("ID");
		
		addedTable.setFillsViewportHeight(true);
		addedTable.setBorder(BorderFactory.createEmptyBorder());
		addedTable.getTableHeader().setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		addedTable.getTableHeader().setBackground(aa_purple);
		addedTable.getTableHeader().setForeground(Color.white);
		addedTable.setGridColor(aa_purple);
		addedTable.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		
		for(int i = 0; i < Task_List.size(); i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					addedTable.setValueAt(Task_List.get(i).getTaskName(), i, j);
				}else if (j == 1) {
					addedTable.setValueAt(Task_List.get(i).getTaskID(), i, j);
				}
			}
		}
		
		addedTable.removeColumn(addedTable.getColumnModel().getColumn(1));
		addedTable.setBackground(Color.white);
		addedTable.setForeground(Color.black);
		addedTable.setBorder(null);
		
		JScrollPane table_pane = new JScrollPane(addedTable);
		table_pane.setBackground(Color.GRAY);
		
		Border empty = new EmptyBorder(0,0,0,0);
		table_pane.setBorder(empty);
		//sets dimensions for table panel
		table_pane.setPreferredSize(new Dimension(550,400));
		
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		graphPanel.setMaximumSize(new Dimension(600, 35));
		graphPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, aa_purple));
		
		JButton graph = new JButton(" graph task ");
		graph.setForeground(Color.white);
		graph.setFont(new Font("Serif", Font.BOLD, 16));
		graph.setContentAreaFilled(true);
		graph.setBorderPainted(true);
		graph.setBorder(new LineBorder(aa_grey));
		graph.setFocusPainted(false);
		graph.setBackground(aa_purple);
		graph.setMaximumSize(new Dimension(75,30));
		graph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//graph selected task
				
				int row = addedTable.getSelectedRow();
				int id = (int) addedTable.getModel().getValueAt(row, 1);
				
				for(Task task : Task_List) {
					if(task.getTaskID() == id) {
						Progress_Report_TaskGraph graph = new Progress_Report_TaskGraph(task.getTaskName());
						graph.Make_TaskGraph(db, task);
					}
				}
			}
		});
		
		graphPanel.add(Box.createRigidArea(new Dimension(500, 0)));
		graphPanel.add(graph);
		
		tasksAdded.add(datesPanel);
		tasksAdded.add(table_pane);
		tasksAdded.add(graphPanel);
		
		return tasksAdded; 
	}
	
	private JPanel createTasksCompletedPanel(int userID, DataBase db) {
		
		ArrayList<Task> Task_List = db.SelectAllCompletedTasks(userID, dt_Start, dt_End); 
		
		JPanel tasksCompleted = new JPanel();
		tasksCompleted.setLayout(new BoxLayout(tasksCompleted, BoxLayout.Y_AXIS));
		tasksCompleted.setMaximumSize(new Dimension(600, 500));
		
		JPanel datesPanel = new JPanel();
		datesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		datesPanel.setMaximumSize(new Dimension(600, 50));
		datesPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, aa_purple));
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(dt_Start);
		startCalendar.add(Calendar.MONTH, 1);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(dt_End); 
		endCalendar.add(Calendar.MONTH, 1);
		
		JLabel dates = new JLabel (startCalendar.get(Calendar.MONTH) + "/" + startCalendar.get(Calendar.DAY_OF_MONTH) + "/" + startCalendar.get(Calendar.YEAR) + " - " + endCalendar.get(Calendar.MONTH) + "/" + endCalendar.get(Calendar.DAY_OF_MONTH) + "/" + endCalendar.get(Calendar.YEAR)); 
		dates.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 34));
		dates.setBackground(Color.black);
		dates.setForeground(aa_grey);
		
		datesPanel.add(Box.createRigidArea(new Dimension(135, 0)));
		datesPanel.add(dates);
		
		DefaultTableModel model = new DefaultTableModel(Task_List.size(), 0);
		JTable completedTable = new JTable(model);
		model.addColumn("Tasks Completed");
		model.addColumn("ID");
		
		completedTable.setFillsViewportHeight(true);
		completedTable.setBorder(BorderFactory.createEmptyBorder());
		completedTable.getTableHeader().setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		completedTable.getTableHeader().setBackground(aa_purple);
		completedTable.getTableHeader().setForeground(Color.white);
		completedTable.setGridColor(aa_purple);
		completedTable.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		
		for(int i = 0; i < Task_List.size(); i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					completedTable.setValueAt(Task_List.get(i).getTaskName(), i, j);
				}else if (j == 1) {
					completedTable.setValueAt(Task_List.get(i).getTaskID(), i, j);
				}
			}
		}
		
		completedTable.removeColumn(completedTable.getColumnModel().getColumn(1));
		completedTable.setBackground(Color.white);
		completedTable.setForeground(Color.black);
		completedTable.setBorder(null);
		
		JScrollPane table_pane = new JScrollPane(completedTable);
		table_pane.setBackground(Color.GRAY);
		
		Border empty = new EmptyBorder(0,0,0,0);
		table_pane.setBorder(empty);
		//sets dimensions for table panel
		table_pane.setPreferredSize(new Dimension(550,400));
		
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		graphPanel.setMaximumSize(new Dimension(600, 35));
		graphPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, aa_purple));
		
		JButton graph = new JButton(" graph task ");
		graph.setForeground(Color.white);
		graph.setFont(new Font("Serif", Font.BOLD, 16));
		graph.setContentAreaFilled(true);
		graph.setBorderPainted(true);
		graph.setBorder(new LineBorder(aa_grey));
		graph.setFocusPainted(false);
		graph.setBackground(aa_purple);
		graph.setMaximumSize(new Dimension(75,30));
		graph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//graph selected task
				
				int row = completedTable.getSelectedRow();
				int id = (int) completedTable.getModel().getValueAt(row, 1);
				
				for(Task task : Task_List) {
					if(task.getTaskID() == id) {
						Progress_Report_TaskGraph graph = new Progress_Report_TaskGraph(task.getTaskName());
						graph.Make_TaskGraph(db, task);
					}
				}
			}
		});
		
		graphPanel.add(Box.createRigidArea(new Dimension(500, 0)));
		graphPanel.add(graph);
		
		tasksCompleted.add(datesPanel);
		tasksCompleted.add(table_pane);
		tasksCompleted.add(graphPanel);
		
		return tasksCompleted; 
	}
	
	private JPanel createCenterPanel(CardLayout cardLayout, JPanel reportViews, int userID, DataBase db) {
		
		JPanel center_panel = new JPanel();
		center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.Y_AXIS));
		center_panel.setBackground(Color.black);
		
		//add card layout that will toggle between summary and two tables
		reportViews.setLayout(cardLayout);
		reportViews.setMaximumSize(new Dimension(600, 600));
		
		//add summary panel to have all the summary stuff
		JPanel summaryPanel = createSummaryPanel(userID, db);
		reportViews.add("summary", summaryPanel);
		
		//add tasksAdded panel that will have table for all tasks added
		JPanel tasksAddedPanel = createTasksAddedPanel(userID, db); 
		reportViews.add("tasksAdded", tasksAddedPanel);
		
		//add tasksCompleted panel that will have table for all tasked completed
		JPanel tasksCompletedPanel = createTasksCompletedPanel(userID, db);
		reportViews.add("tasksCompleted", tasksCompletedPanel);
		
		JPanel datePanel = createDatePickers(cardLayout, reportViews, userID, db, summaryPanel, tasksAddedPanel, tasksCompletedPanel);
		
		center_panel.add(datePanel);
		center_panel.add(reportViews);
		return center_panel;		
	}
	
	private JPanel createButtonPanel(CardLayout cardLayout, JPanel reportViews) {
		
		JPanel bottomButtons = new JPanel();
		bottomButtons.setLayout(new BoxLayout(bottomButtons, BoxLayout.X_AXIS));
		bottomButtons.setBackground(aa_grey);
		
		JLabel view = new JLabel("Select View: ");
		view.setForeground(Color.white);
		view.setFont(new Font("Serif", Font.BOLD, 18));
		
		JButton summary = new JButton("summary");
		summary.setForeground(Color.white);
		summary.setFont(new Font("Serif", Font.BOLD, 16));
		summary.setContentAreaFilled(true);
		summary.setBorderPainted(true);
		summary.setBorder(new LineBorder(Color.GRAY, 2));
		summary.setFocusPainted(false);
		summary.setBackground(aa_purple);
		summary.setMaximumSize(new Dimension(125,50));
		summary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open cardlayout to summary panel
				currentCard = "summary";
				cardLayout.show(reportViews, "summary");
			}
		});
		
		JButton addedTasks = new JButton("tasks added");
		addedTasks.setForeground(Color.white);
		addedTasks.setFont(new Font("Serif", Font.BOLD, 16));
		addedTasks.setContentAreaFilled(true);
		addedTasks.setBorderPainted(true);
		addedTasks.setBorder(new LineBorder(Color.GRAY, 2));
		addedTasks.setFocusPainted(false);
		addedTasks.setBackground(aa_purple);
		addedTasks.setMaximumSize(new Dimension(125,50));
		addedTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open cardLayout to tasks added table
				currentCard = "tasksAdded";
				cardLayout.show(reportViews, "tasksAdded");
			}
		});
		
		JButton completedTasks = new JButton("tasks completed");
		completedTasks.setForeground(Color.white);
		completedTasks.setFont(new Font("Serif", Font.BOLD, 16));
		completedTasks.setContentAreaFilled(true);
		completedTasks.setBorderPainted(true);
		completedTasks.setBorder(new LineBorder(Color.GRAY, 2));
		completedTasks.setFocusPainted(false);
		completedTasks.setBackground(aa_purple);
		completedTasks.setMaximumSize(new Dimension(125,50));
		completedTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open cardLayout to tasks completed table
				currentCard = "tasksCompleted";
				cardLayout.show(reportViews, "tasksCompleted");
			}
		});
		
		bottomButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		bottomButtons.add(view);
		bottomButtons.add(Box.createRigidArea(new Dimension(40, 0)));
		bottomButtons.add(summary);
		bottomButtons.add(Box.createRigidArea(new Dimension(20, 0)));
		bottomButtons.add(addedTasks);
		bottomButtons.add(Box.createRigidArea(new Dimension(20, 0)));
		bottomButtons.add(completedTasks);
		
		return bottomButtons;
	}
	/**
	 * creates/displays Progress Report GUI
	 * @param db, userID
	 */
	public void open_progressReport(int userID, DataBase db) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				pr_frame = new JFrame("Progress Report");
				
				pr_frame.setUndecorated(true);
				pr_frame.setPreferredSize(new Dimension(width, height));
				
				JPanel masterPanel = new JPanel(new BorderLayout());
				masterPanel.setBackground(Color.black);
				masterPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aa_purple));
				
				CardLayout cardLayout = new CardLayout();
				JPanel reportViews = new JPanel();
					
				JMenuBar title_panel = createTitlePanel(pr_frame);
				JPanel center_panel = createCenterPanel(cardLayout, reportViews, userID, db);
				JPanel bottomButtons = createButtonPanel(cardLayout, reportViews);
				
				/*
				 * populates master panel 
				 */
				masterPanel.add(title_panel, BorderLayout.PAGE_START); 
				masterPanel.add(center_panel, BorderLayout.CENTER);
				masterPanel.add(bottomButtons, BorderLayout.PAGE_END);
			
				/*
				 * adds master panel to frame
				 */
				pr_frame.getContentPane().add(masterPanel); 
				pr_frame.getContentPane().setBackground(Color.black);
				pr_frame.pack();
				pr_frame.setAlwaysOnTop(false);
				pr_frame.setVisible(true);
				pr_frame.setResizable(true);
				pr_frame.setLocationRelativeTo(null);
			
			}
		});
	}
	
	public JFileChooser getFileChooser()
    {
        if (fileChooser ==null)
        {
            fileChooser = new JFileChooser();  //create file chooser
            
            fileChooser.setFileFilter(new PNGFileFilter());  //set file extension to .png
        }
        return fileChooser;
    }
	
	public static BufferedImage getScreenShot(Component component)    //used to get the current progress report displayed on the screen
    {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());   // paints into image's Graphics
        return image;
    }
	
	private static class PNGFileFilter extends FileFilter
    {
        public boolean accept(File file) //filter files to display
        {
            return file.getName().toLowerCase().endsWith(".png") || file.isDirectory();
        }

        public String getDescription()
        {
            return "PNG image  (*.png) ";
        }
    }
	
	private void downloadDateSelectionWindow(int userID, DataBase db) {
		//create window for user to select progress report dates
		JFrame dates_window = new JFrame("Select Dates for Download");
		dates_window.setAlwaysOnTop(true);
		dates_window.setBackground(Color.black);
		dates_window.setUndecorated(true);
		dates_window.setVisible(true);
		
		JPanel title_panel = new JPanel();
		title_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		title_panel.setBackground(Color.black);
		title_panel.setBorder(BorderFactory.createLineBorder(aa_purple));
		JLabel title = new JLabel("Select Dates for Progress Report Download");
		title.setForeground(Color.white);
		title.setFont(new Font("Serif", Font.BOLD, 18));
		
		/*
		 * allows drag and drop of frame
		 */
		title_panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dates_window.setLocation(dates_window.getX() + e.getX() - mouseX, dates_window.getY() + e.getY() - mouseY);
			}
		});
		
		title_panel.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		
		//reads in image for the close button
		BufferedImage ci = null;
		
		try {
			ci = ImageIO.read(new File("images/exit_circle.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//creates close button with close icon and no background
		Image c_img = ci.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		Icon close = new ImageIcon(c_img);
		JButton close_window = new JButton(close);
		close_window.setBorderPainted(false);
		close_window.setContentAreaFilled(false);
		close_window.setFocusPainted(false);
		close_window.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//close window without saving info
        		dates_window.dispose();
        	}
        });
		
		//adds title JLabel, empty space, then guide button and close button
		title_panel.add(title);
		//title_panel.add(Box.createRigidArea(new Dimension(200, 0)));
		title_panel.add(close_window);
		
		JPanel dateChoices = new JPanel(); 
		dateChoices.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aa_purple));
		dateChoices.setBackground(Color.black);
		dateChoices.setLayout(new BoxLayout(dateChoices, BoxLayout.Y_AXIS));
		dateChoices.setMaximumSize(new Dimension(300, 300));
		dateChoices.setBackground(Color.black); 
		
		JPanel startDateIntervalPanel = new JPanel();
		startDateIntervalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		startDateIntervalPanel.setMaximumSize(new Dimension(300, 45));
		startDateIntervalPanel.setBackground(Color.black); 
		
		JLabel startDate = new JLabel("Start Date: ");
		startDate.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 17));
		startDate.setBackground(Color.black);
		startDate.setForeground(Color.white);
		
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year"); 
		
		JDatePanelImpl beginDatePanel = new JDatePanelImpl(model, properties);
		JDatePickerImpl beginDatePicker = new JDatePickerImpl(beginDatePanel, new DateLabelFormatter());
		
		startDateIntervalPanel.add(startDate);
		startDateIntervalPanel.add(beginDatePicker);
		
		JPanel endDateIntervalPanel = new JPanel();
		endDateIntervalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		endDateIntervalPanel.setMaximumSize(new Dimension(300, 45));
		endDateIntervalPanel.setBackground(Color.black); 
		
		JLabel endDate = new JLabel("End Date:  ");
		endDate.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 17));
		endDate.setBackground(Color.black);
		endDate.setForeground(Color.white);
		
		UtilDateModel model2 = new UtilDateModel();
		Properties properties2 = new Properties();
		properties2.put("text.today", "Today");
		properties2.put("text.month", "Month");
		properties2.put("text.year", "Year"); 
		
		JDatePanelImpl finishDatePanel = new JDatePanelImpl(model2, properties2);
		JDatePickerImpl finishDatePicker = new JDatePickerImpl(finishDatePanel, new DateLabelFormatter());
		
		endDateIntervalPanel.add(endDate);
		endDateIntervalPanel.add(finishDatePicker);
		
		dateChoices.add(startDateIntervalPanel);
		dateChoices.add(endDateIntervalPanel); 
		
		JPanel applyPanel = new JPanel();
		applyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		applyPanel.setMaximumSize(new Dimension(300, 35));
		applyPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, aa_purple));
		applyPanel.setBackground(aa_grey);
		
		JButton apply = new JButton(" select ");
		apply.setForeground(Color.white);
		apply.setFont(new Font("Serif", Font.BOLD, 16));
		apply.setContentAreaFilled(true);
		apply.setBorderPainted(true);
		apply.setBorder(new LineBorder(aa_grey));
		apply.setFocusPainted(false);
		apply.setBackground(aa_purple);
		apply.setMaximumSize(new Dimension(75,30));
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dt_Start = (Date) beginDatePicker.getModel().getValue();
				dt_End = (Date) finishDatePicker.getModel().getValue();
				
				dates_window.dispose();
				
				open_progressReport(userID, db);
				
				//save as png 
				JFileChooser jFileChooser = getFileChooser();
				int result = jFileChooser.showSaveDialog(summaryPanel); 
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						File selectedFile = jFileChooser.getSelectedFile();
						selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
						BufferedImage img = getScreenShot(summaryPanel);
						ImageIO.write(img, "png", selectedFile);
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(null, "Could not save file"); 
					}
				}
				pr_frame.dispose();
			}
		});
		
		applyPanel.add(Box.createRigidArea(new Dimension(340, 0)));
		applyPanel.add(apply);
		
		//sets location and dimensions of task window
		int x = (int) ((screen.getWidth() - dates_window.getWidth()) /2);
		int y = (int) ((screen.getHeight() - dates_window.getHeight()) /2);
		dates_window.setLocation(x, y);
		
		dates_window.add(title_panel, BorderLayout.PAGE_START); 
		dates_window.add(dateChoices, BorderLayout.CENTER);
		dates_window.add(applyPanel, BorderLayout.PAGE_END);
		dates_window.pack();
		
	}
	
	/**
	 * downloads a screenshot of the progress report
	 */
	public void downloadProgressReport(int userID, DataBase db) {
		
		downloadDateSelectionWindow(userID, db);
	}
}

