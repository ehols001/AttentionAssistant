package AttentionAssistant;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.net.URL;
import java.net.MalformedURLException;
import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.table.DefaultTableModel;

/**
 * Class that encompasses Parent Portal whenever adolescent is 
 * clicked on from child selection bar
 * @author krchr
 *
 */

public class Parent_Portal {

	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	LineBorder line = new LineBorder(aa_purple, 2, true);
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = 715; 
	private int width = 615; 
	private int mouseX;
	private int mouseY;
	private int row; 
	final static boolean shouldFill = true; 
	final static boolean shouldWeightX = true; 
	final static boolean RIGHT_TO_LEFT = false; 
	JCheckBox ftsVisibleBox = new JCheckBox("<html><center>&nbsp;&nbsp;&nbsp;&nbsp;Free Thought&#8202;&#8202;&nbsp;&nbsp;" + "<br/>Space</center></html>");
	JCheckBox ntbVisibleBox = new JCheckBox("<html><center>&nbsp;&nbsp;Negative Thought" + "<br/>Burner</center></html>");
	JCheckBox htbVisibleBox = new JCheckBox("<html><center>&nbsp;&nbsp;Happy Thought&nbsp;&nbsp;" + "<br/>Button</center></html>");
	JCheckBox timerVisibleBox = new JCheckBox("<html><center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pomodoro&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;" + "<br/>Timer</center></html>");
	JTable tableWL;
	JTable tableBL;
	boolean allowed = true;
	boolean isInstantiated = false;
	
	public void rebuildPanel(CardLayout card_layout, JPanel card_panel, Settings settingsChanges, DataBase db) {
		
		createMonitoringManagementPanel(card_layout, card_panel, settingsChanges, db);
		card_layout.show(card_panel, "monitor"); 
		card_panel.revalidate();
		card_panel.repaint();
	}
	
	private JCheckBox createLockCheckBox(String html) {
		
		JCheckBox lock_feature = new JCheckBox(html);
		lock_feature.setBorderPainted(false);
		lock_feature.setContentAreaFilled(false);
		lock_feature.setFocusPainted(false);
		
		return lock_feature; 
	}
	
	private void createSettingsPanel(JPanel card_panel, Settings settingsChanges, Priority_Manager pm, DataBase db, int userID, Progress_Report pr) throws MalformedURLException {
		JPanel settings_panel = new JPanel();
		settings_panel.setLayout(new BoxLayout(settings_panel, BoxLayout.Y_AXIS));
		settings_panel.setBackground(aa_grey);
		
		JPanel header_panel = new JPanel();
		header_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		header_panel.setBackground(aa_grey);
		
		JLabel overrideOptions = new JLabel("Override Options:");
		overrideOptions.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		overrideOptions.setForeground(Color.white);
		
		header_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		header_panel.add(overrideOptions);
		
		JPanel featuresHeader = new JPanel();
		featuresHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		featuresHeader.setBackground(aa_grey);
		featuresHeader.setMaximumSize(new Dimension(500, 5));
		
		JLabel visibleFeatures = new JLabel("<html><center><u>Features Displayed in Navigation Bar</u>:</center></html>");
		visibleFeatures.setFont(new Font("Serif", Font.BOLD, 18));
		visibleFeatures.setForeground(Color.white); 
		
		featuresHeader.add(Box.createRigidArea(new Dimension(15, 0)));
		featuresHeader.add(visibleFeatures);
		
		File lockFile = new File("images/lock.png");
		URL url = lockFile.toURI().toURL();
		final String html = "<html><body><img src='" + url.toString() +"'width=\"20\" height=\"20\">";
		
		JPanel navBarBoxes = new JPanel();
		navBarBoxes.setLayout(new BoxLayout(navBarBoxes, BoxLayout.Y_AXIS));
		navBarBoxes.setBackground(aa_grey);
		navBarBoxes.setMaximumSize(new Dimension(425, 40));
		
		JPanel firstRowVisibilityChecks = new JPanel();
		firstRowVisibilityChecks.setLayout(new FlowLayout(FlowLayout.LEFT));
		firstRowVisibilityChecks.setBackground(aa_grey);
		firstRowVisibilityChecks.setMaximumSize(new Dimension(415, 55));
		
		timerVisibleBox.setSelected(settingsChanges.getTimerIsVisible()); 
		if(settingsChanges.getPomodoroIsActive() == false) {
			timerVisibleBox.setEnabled(false); 
		}else if(settingsChanges.getPomodoroIsActive() == true) {
			timerVisibleBox.setEnabled(true);
		}
		timerVisibleBox.setFont(new Font("Serif", Font.BOLD, 16));
		timerVisibleBox.setForeground(Color.white);
		timerVisibleBox.setContentAreaFilled(false);
		timerVisibleBox.setBorderPainted(true);
		timerVisibleBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, aa_purple));
		timerVisibleBox.setFocusPainted(false);
		timerVisibleBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setTimerIsVisible(timerVisibleBox.isSelected()); 
			}
		});
		
		JCheckBox lock_timerVisibility = createLockCheckBox(html);
		lock_timerVisibility.setSelected(settingsChanges.getTimerVisibilityIsLocked());
		lock_timerVisibility.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setTimerVisibilityIsLocked(lock_timerVisibility.isSelected());
        }});
		
		JCheckBox pmVisibleBox = new JCheckBox("<html><center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Priority&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<br/>Manager</center></html>", settingsChanges.getPmIsVisible());
		pmVisibleBox.setFont(new Font("Serif", Font.BOLD, 16));
		pmVisibleBox.setForeground(Color.white);
		pmVisibleBox.setContentAreaFilled(false);
		pmVisibleBox.setFocusPainted(false);
		pmVisibleBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setPmIsVisible(pmVisibleBox.isSelected()); 
			}
		});
		
		JCheckBox lock_pmVisibility = createLockCheckBox(html); 
		lock_pmVisibility.setSelected(settingsChanges.getPmVisibilityIsLocked());
		lock_pmVisibility.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setPmVisibilityIsLocked(lock_pmVisibility.isSelected());
        }});
		
		firstRowVisibilityChecks.add(lock_timerVisibility);
		firstRowVisibilityChecks.add(timerVisibleBox);
		firstRowVisibilityChecks.add(lock_pmVisibility);
		firstRowVisibilityChecks.add(pmVisibleBox);
		
		JPanel secondRowVisibilityChecks = new JPanel();
		secondRowVisibilityChecks.setLayout(new FlowLayout(FlowLayout.LEFT));
		secondRowVisibilityChecks.setBackground(aa_grey);
		secondRowVisibilityChecks.setMaximumSize(new Dimension(415, 55));
		
		ftsVisibleBox.setSelected(settingsChanges.getFtsIsVisible());
		if(settingsChanges.getFtsIsActive() == false) {
			ftsVisibleBox.setEnabled(false); 
		}else if(settingsChanges.getFtsIsActive() == true) {
			ftsVisibleBox.setEnabled(true);
		}
		ftsVisibleBox.setFont(new Font("Serif", Font.BOLD, 16));
		ftsVisibleBox.setForeground(Color.white);
		ftsVisibleBox.setBorderPainted(true);
		ftsVisibleBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, aa_purple));
		ftsVisibleBox.setContentAreaFilled(false);
		ftsVisibleBox.setFocusPainted(false);
		ftsVisibleBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setFtsIsVisible(ftsVisibleBox.isSelected()); 
			}
		});
		
		JCheckBox lock_ftsVisibility = createLockCheckBox(html); 
		lock_ftsVisibility.setSelected(settingsChanges.getFtsVisibilityIsLocked());
		lock_ftsVisibility.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setFtsVisibilityIsLocked(lock_ftsVisibility.isSelected());
        }});
		
		ntbVisibleBox.setSelected(settingsChanges.getNtbIsVisible()); 
		if(settingsChanges.getNtbIsActive() == false) {
			ntbVisibleBox.setEnabled(false); 
		}else if(settingsChanges.getNtbIsActive() == true) {
			ntbVisibleBox.setEnabled(true);
		}
		ntbVisibleBox.setFont(new Font("Serif", Font.BOLD, 16));
		ntbVisibleBox.setForeground(Color.white);
		ntbVisibleBox.setContentAreaFilled(false);
		ntbVisibleBox.setFocusPainted(false);
		ntbVisibleBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setNtbIsVisible(ntbVisibleBox.isSelected()); 
			}
		});
		
		JCheckBox lock_ntbVisibility = createLockCheckBox(html);
		lock_ntbVisibility.setSelected(settingsChanges.getNtbVisibilityIsLocked());
		lock_ntbVisibility.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setNtbVisibilityIsLocked(lock_ntbVisibility.isSelected());
        }});
		
		secondRowVisibilityChecks.add(lock_ftsVisibility);
		secondRowVisibilityChecks.add(ftsVisibleBox);
		secondRowVisibilityChecks.add(lock_ntbVisibility);
		secondRowVisibilityChecks.add(ntbVisibleBox);
		
		JPanel thirdRowVisibilityChecks = new JPanel();
		thirdRowVisibilityChecks.setLayout(new FlowLayout(FlowLayout.LEFT));
		thirdRowVisibilityChecks.setBackground(aa_grey);
		thirdRowVisibilityChecks.setMaximumSize(new Dimension(415, 55));
		
		htbVisibleBox.setSelected(settingsChanges.getHtbIsVisible());
		if(settingsChanges.getHtbIsActive() == false) {
			htbVisibleBox.setEnabled(false); 
		}else if(settingsChanges.getHtbIsActive() == true) {
			htbVisibleBox.setEnabled(true);
		}
		htbVisibleBox.setFont(new Font("Serif", Font.BOLD, 16));
		htbVisibleBox.setForeground(Color.white);
		htbVisibleBox.setBorderPainted(true);
		htbVisibleBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, aa_purple));
		htbVisibleBox.setContentAreaFilled(false);
		htbVisibleBox.setFocusPainted(false);
		htbVisibleBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setHtbIsVisible(htbVisibleBox.isSelected()); 
			}
		});
		
		JCheckBox lock_htbVisibility = createLockCheckBox(html); 
		lock_htbVisibility.setSelected(settingsChanges.getHtbVisibilityIsLocked());
		lock_htbVisibility.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setHtbVisibilityIsLocked(lock_htbVisibility.isSelected());
        }});
		
		JCheckBox prVisibleBox = new JCheckBox("<html><center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Progress&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<br/>Report</center></html>", settingsChanges.getProgReportIsVisible());
		prVisibleBox.setFont(new Font("Serif", Font.BOLD, 16));
		prVisibleBox.setForeground(Color.white);
		prVisibleBox.setContentAreaFilled(false);
		prVisibleBox.setFocusPainted(false);
		prVisibleBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setProgReportIsVisible(prVisibleBox.isSelected()); 
			}
		});
		
		JCheckBox lock_prVisibility = createLockCheckBox(html); 
		lock_prVisibility.setSelected(settingsChanges.getProgReportVisibilityIsLocked());
		lock_prVisibility.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setProgReportVisibilityIsLocked(lock_prVisibility.isSelected());
        }});
		
		thirdRowVisibilityChecks.add(lock_htbVisibility);
		thirdRowVisibilityChecks.add(htbVisibleBox);
		thirdRowVisibilityChecks.add(lock_prVisibility);
		thirdRowVisibilityChecks.add(prVisibleBox);
		
		navBarBoxes.add(firstRowVisibilityChecks);
		navBarBoxes.add(secondRowVisibilityChecks);
		navBarBoxes.add(thirdRowVisibilityChecks);
		
		JPanel activefeaturesHeader = new JPanel();
		activefeaturesHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		activefeaturesHeader.setBackground(aa_grey);
		activefeaturesHeader.setMaximumSize(new Dimension(500, 5));
		
		JLabel activeFeatures = new JLabel("<html><center><u>Active Features</u>:</center></html>");
		activeFeatures.setFont(new Font("Serif", Font.BOLD, 18));
		activeFeatures.setForeground(Color.white); 
		
		activefeaturesHeader.add(Box.createRigidArea(new Dimension(15, 0)));
		activefeaturesHeader.add(activeFeatures);
		
		JPanel featureBoxes = new JPanel();
		featureBoxes.setLayout(new BoxLayout(featureBoxes, BoxLayout.Y_AXIS));
		featureBoxes.setBackground(aa_grey);
		featureBoxes.setMaximumSize(new Dimension(420, 5));
		
		JPanel firstRowActiveChecks = new JPanel();
		firstRowActiveChecks.setLayout(new FlowLayout(FlowLayout.LEFT));
		firstRowActiveChecks.setBackground(aa_grey);
		firstRowActiveChecks.setMaximumSize(new Dimension(415, 55));
		
		JCheckBox toggleTimer = new JCheckBox("<html><center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pomodoro&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;" + "<br/>Timer</center></html>", settingsChanges.getPomodoroIsActive());
		toggleTimer.setFont(new Font("Serif", Font.BOLD, 16));
		toggleTimer.setForeground(Color.white);
		toggleTimer.setContentAreaFilled(false);
		toggleTimer.setFocusPainted(false);
		toggleTimer.setBorderPainted(true);
		toggleTimer.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, aa_purple));
		toggleTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setPomodoroIsActive(toggleTimer.isSelected()); 
			}
		});
		
		JCheckBox lock_timerActive = createLockCheckBox(html); 
		lock_timerActive.setSelected(settingsChanges.getPomodoroIsLocked());
		lock_timerActive.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setPomodoroIsLocked(lock_timerActive.isSelected());
        }});
		
		JCheckBox ntbBox = new JCheckBox("<html><center>&nbsp;&nbsp;Negative Thought" + "<br/>Burner</center></html>", settingsChanges.getNtbIsActive());
		ntbBox.setFont(new Font("Serif", Font.BOLD, 16));
		ntbBox.setForeground(Color.white);
		ntbBox.setContentAreaFilled(false);
		ntbBox.setFocusPainted(false);
		ntbBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setNtbIsActive(ntbBox.isSelected()); 
			}
		});
		
		JCheckBox lock_ntbActive = createLockCheckBox(html); 
		lock_ntbActive.setSelected(settingsChanges.getNtbIsLocked());
		lock_ntbActive.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setNtbIsLocked(lock_ntbActive.isSelected());
        }});
		
		
		firstRowActiveChecks.add(lock_timerActive);
		firstRowActiveChecks.add(toggleTimer);
		firstRowActiveChecks.add(lock_ntbActive);
		firstRowActiveChecks.add(ntbBox);
		
		JPanel secondRowActiveChecks = new JPanel();
		secondRowActiveChecks.setLayout(new FlowLayout(FlowLayout.LEFT));
		secondRowActiveChecks.setBackground(aa_grey);
		secondRowActiveChecks.setMaximumSize(new Dimension(415, 55));
		
		JCheckBox ftsBox = new JCheckBox("<html><center>&nbsp;&nbsp;&nbsp;&nbsp;Free Thought&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<br/>Space</center></html>", settingsChanges.getFtsIsActive());
		ftsBox.setFont(new Font("Serif", Font.BOLD, 16));
		ftsBox.setForeground(Color.white);
		ftsBox.setContentAreaFilled(false);
		ftsBox.setFocusPainted(false);
		ftsBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setFtsIsActive(ftsBox.isSelected()); 
			}
		});
		
		JCheckBox lock_ftsActive = createLockCheckBox(html); 
		lock_ftsActive.setSelected(settingsChanges.getFtsIsLocked());
		lock_ftsActive.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setFtsIsLocked(lock_ftsActive.isSelected());
        }});
		
		JCheckBox htbBox = new JCheckBox("<html><center>&nbsp;&nbsp;Happy Thought&nbsp;&nbsp;" + "<br/>Button</center></html>", settingsChanges.getHtbIsActive());
		htbBox.setFont(new Font("Serif", Font.BOLD, 16));
		htbBox.setForeground(Color.white);
		htbBox.setContentAreaFilled(false);
		htbBox.setFocusPainted(false);
		htbBox.setBorderPainted(true);
		htbBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, aa_purple));
		htbBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setHtbIsActive(htbBox.isSelected()); 
			}
		});
		
		JCheckBox lock_htbActive = createLockCheckBox(html); 
		lock_htbActive.setSelected(settingsChanges.getHtbIsLocked());
		lock_htbActive.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setHtbIsLocked(lock_htbActive.isSelected());
        }});
		
		secondRowActiveChecks.add(lock_htbActive);
		secondRowActiveChecks.add(htbBox);
		secondRowActiveChecks.add(lock_ftsActive);
		secondRowActiveChecks.add(ftsBox);
		
		featureBoxes.add(firstRowActiveChecks);
		featureBoxes.add(secondRowActiveChecks);
		
		JPanel timerHeader = new JPanel();
		timerHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		timerHeader.setBackground(aa_grey);
		timerHeader.setMaximumSize(new Dimension(500, 5));
		
		JLabel timerLabel = new JLabel("<html><center><u>Pomodoro Timer Intervals</u>:</center></html>");
		timerLabel.setFont(new Font("Serif", Font.BOLD, 18));
		timerLabel.setForeground(Color.white); 
		
		timerHeader.add(Box.createRigidArea(new Dimension(15, 0)));
		timerHeader.add(timerLabel);
		
		JPanel intervalSettings = new JPanel();
		GridLayout grid3 = new GridLayout(0,4);
		intervalSettings.setLayout(grid3);
		intervalSettings.setBackground(aa_grey);
		intervalSettings.setMaximumSize(new Dimension(400, 100));
		
		JLabel workPeriod = new JLabel("<html><center>Enter Work" + "<br/>Period: </center></html>");
		workPeriod.setFont(new Font("Serif", Font.BOLD, 16));
		workPeriod.setForeground(Color.white);
		
		Integer workMinutes[] = {15, 20, 25, 30, 35, 40, 45, 50, 55, 60};
		Integer breakMinutes[] = {10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60};
		
		JComboBox<Integer> workInterval = new JComboBox<>(workMinutes);
		AccessibleContext accessCont = workInterval.getAccessibleContext();
		BasicComboPopup pop = (BasicComboPopup) accessCont.getAccessibleChild(0);
		JList workList = pop.getList();
		workList.setSelectionForeground(Color.WHITE);
		workList.setSelectionBackground(aa_purple);
		workInterval.setBackground(Color.black);
		workInterval.setForeground(Color.white);
		workInterval.setFont(new Font("Serif", Font.BOLD, 24));
		workInterval.setSelectedIndex((settingsChanges.getWorkPeriod()/5) - 3); 
		((JLabel)workInterval.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		workInterval.setMaximumSize(new Dimension(50,25));
		workInterval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setWorkPeriod((int) workInterval.getSelectedItem());	
			} 
		});
		
		JLabel minutesWork = new JLabel(" minutes");
		minutesWork.setFont(new Font("Serif", Font.BOLD, 16));
		minutesWork.setForeground(Color.white);
		
		JCheckBox lock_workPeriod = createLockCheckBox(html); 
		lock_workPeriod.setSelected(settingsChanges.getWorkPeriodIsLocked());
		lock_workPeriod.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setWorkPeriodIsLocked(lock_workPeriod.isSelected());
        	
        }});
		
		JLabel breakPeriod = new JLabel("<html><center>Enter Break" + "<br/>Period: </center></html>");
		breakPeriod.setFont(new Font("Serif", Font.BOLD, 16));
		breakPeriod.setForeground(Color.white);
		
		JComboBox<Integer> breakInterval = new JComboBox<>(breakMinutes);
		AccessibleContext accessCont2 = breakInterval.getAccessibleContext();
		BasicComboPopup pop2 = (BasicComboPopup) accessCont2.getAccessibleChild(0);
		JList breakList = pop2.getList();
		breakList.setSelectionForeground(Color.WHITE);
		breakList.setSelectionBackground(aa_purple);
		breakInterval.setBackground(Color.black);
		breakInterval.setForeground(Color.white);
		breakInterval.setFont(new Font("Serif", Font.BOLD, 24));
		breakInterval.setSelectedIndex((settingsChanges.getBreakPeriod()/5) - 2); 
		((JLabel)breakInterval.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		breakInterval.setMaximumSize(new Dimension(50,25));
		breakInterval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsChanges.setBreakPeriod((int) breakInterval.getSelectedItem());	
			} 
		});
		
		JLabel minutesBreak = new JLabel(" minutes");
		minutesBreak.setFont(new Font("Serif", Font.BOLD, 16));
		minutesBreak.setForeground(Color.white);
		
		JCheckBox lock_breakPeriod = createLockCheckBox(html);
		lock_breakPeriod.setSelected(settingsChanges.getBreakPeriodIsLocked());
		lock_breakPeriod.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lock feature
        		settingsChanges.setBreakPeriodIsLocked(lock_breakPeriod.isSelected());
        }});
		
		intervalSettings.add(workPeriod);
		intervalSettings.add(workInterval);
		intervalSettings.add(minutesWork);
		intervalSettings.add(lock_workPeriod);
		intervalSettings.add(breakPeriod);
		intervalSettings.add(breakInterval);
		intervalSettings.add(minutesBreak);
		intervalSettings.add(lock_breakPeriod);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.setBackground(aa_grey);
		buttonsPanel.setMaximumSize(new Dimension (500, 140));
		
		JPanel topRowButtons = new JPanel();
		topRowButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		topRowButtons.setBackground(aa_grey);
		topRowButtons.setMaximumSize(new Dimension(500, 35));
		
		JPanel bottomRowButtons = new JPanel();
		bottomRowButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottomRowButtons.setBackground(aa_grey);
		bottomRowButtons.setMaximumSize(new Dimension(500, 35));
		
		JButton openPM = new JButton("Open Priority Manager");
		openPM.setMaximumSize(new Dimension(200, 20));
		openPM.setBackground(Color.GRAY);
		openPM.setForeground(Color.WHITE);
		openPM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.open_pm(settingsChanges.getUserID(),db,pm);
			}
		});
		
		JButton calendar = new JButton("<html><center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Open Calendar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</center></html>");
		calendar.setMaximumSize(new Dimension(200, 20));
		calendar.setBackground(Color.GRAY);
		calendar.setForeground(Color.WHITE);
		calendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call to open calendar
				pm.userCalendar();
			}
		});
		
		JButton calendarInt = new JButton("<html><center>&nbsp;&nbsp;Calendar Integration&nbsp;&nbsp;</center></html>");
		calendarInt.setMaximumSize(new Dimension(200, 20));
		calendarInt.setBackground(Color.GRAY);
		calendarInt.setForeground(Color.WHITE);
		calendarInt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call to integrate calendar
				pm.importCalendar();
			}
		});
		
		JButton progressReport = new JButton("Open Progress Report ");
		progressReport.setMaximumSize(new Dimension(200, 0));
		progressReport.setBackground(Color.GRAY);
		progressReport.setForeground(Color.WHITE);
		progressReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call to open progress report
				pr.open_progressReport(userID, db);
			}
		});
		
		topRowButtons.add(Box.createRigidArea(new Dimension(20, 0)));
		topRowButtons.add(openPM);
		topRowButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		topRowButtons.add(calendar);
		
		bottomRowButtons.add(Box.createRigidArea(new Dimension(20, 0)));
		bottomRowButtons.add(calendarInt);
		bottomRowButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		bottomRowButtons.add(progressReport);
		
		buttonsPanel.add(topRowButtons);
		buttonsPanel.add(bottomRowButtons);
		
		/*
		 * add everything to settings_panel
		 */
		settings_panel.add(header_panel);
		settings_panel.add(featuresHeader);
		settings_panel.add(navBarBoxes);
		settings_panel.add(activefeaturesHeader);
		settings_panel.add(featureBoxes);	
		settings_panel.add(timerHeader);
		settings_panel.add(Box.createRigidArea(new Dimension(10, 0)));
		settings_panel.add(intervalSettings);
		settings_panel.add(Box.createRigidArea(new Dimension(0, 15)));
		settings_panel.add(buttonsPanel);
		settings_panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		//add to settings_panel to  card_panel
		card_panel.add("settings", settings_panel);	
	}
	
	private JPanel generateTablePanels(DataBase db, Settings settingsChanges, CardLayout card_layout, JPanel card_panel, JTable table, 
										ArrayList<String> applicationList, boolean listType, String columnName) {
	
		JPanel panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(aa_grey);
		panel.setMaximumSize(new Dimension(400, 500));
		
		DefaultTableModel model = new DefaultTableModel(applicationList.size(), 0);
		table = new JTable(model); 
		model.addColumn(columnName);
		table.getTableHeader().setFont(new Font("TimesRoman", Font.ITALIC, 18));
		
		table.setFillsViewportHeight(true);
		table.setBorder(BorderFactory.createEmptyBorder());
		table.getTableHeader().setBackground(aa_grey);
		table.getTableHeader().setForeground(Color.white);
		table.setGridColor(aa_purple);
		table.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		
		for(int i = 0; i < applicationList.size(); i++) {
			
			table.setValueAt(applicationList.get(i),i,0); 
		}
			
		table.setBackground(Color.black);
		table.setForeground(Color.white);
		table.setBorder(null);
		
		if (listType) {
			tableWL = table;
		} else {
			tableBL = table;
		}
		
		JScrollPane table_pane = new JScrollPane(listType ? tableWL : tableBL);
		table_pane.setBackground(Color.black);
		
		Border empty = new EmptyBorder(0,0,0,0);
		table_pane.setBorder(empty);
		//sets dimensions for table panel
		table_pane.setPreferredSize(new Dimension(350,500));
		
		JPanel button_pane = new JPanel();
		button_pane.setLayout(new FlowLayout(FlowLayout.LEFT));
		button_pane.setBackground(aa_grey);
		button_pane.setMaximumSize(new Dimension(400, 55));
		
		JPanel add_pane = new JPanel();
		add_pane.setLayout(new FlowLayout(FlowLayout.LEFT));
		add_pane.setBackground(aa_grey);
		add_pane.setMaximumSize(new Dimension(300, 55));
		
		//creates text area for application name input
		JTextArea applicationName = new JTextArea(1, 19);
		applicationName.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		applicationName.setBorder(new LineBorder(aa_purple,5,false));
		
		JButton addApplication = new JButton("Add"); 
		addApplication.setMaximumSize(new Dimension(50, 0));
		addApplication.setBackground(aa_purple);
		addApplication.setForeground(Color.WHITE);
		addApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//add new application to database table
				String name = applicationName.getText(); 
				
				db.AddWBlist(name, listType, settingsChanges.getUserID());
				rebuildPanel(card_layout, card_panel, settingsChanges, db);
			}
		});
		
		add_pane.add(applicationName);
		add_pane.add(addApplication); 
		
		JButton deleteApplication = new JButton("Delete"); 
		deleteApplication.setMaximumSize(new Dimension(40, 0));
		deleteApplication.setBackground(Color.GRAY);
		deleteApplication.setForeground(Color.WHITE);
		deleteApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (allowed) {
					row = tableWL.getSelectedRow();
					String appName = String.valueOf(tableWL.getModel().getValueAt(row, 0));
					//call to delete selected row from database
					db.DeleteWB_List(appName, listType, settingsChanges.getUserID());
					//call to delete selected row from table
					model.removeRow(tableWL.getSelectedRow());
				} else {
					row = tableBL.getSelectedRow();
					String appName = String.valueOf(tableBL.getModel().getValueAt(row, 0));
					//call to delete selected row from database
					db.DeleteWB_List(appName, listType, settingsChanges.getUserID());
					//call to delete selected row from table
					model.removeRow(tableBL.getSelectedRow());
				} 
			}
		});
		
		button_pane.add(add_pane);
		button_pane.add(deleteApplication);
		
		panel.add(table_pane);
		panel.add(button_pane);
		
		return panel;
	}
	
	/**
	 * 
	 * @param card_panel
	 * @param settingsChanges
	 */
	private void createMonitoringManagementPanel(CardLayout card_layout, JPanel card_panel, Settings settingsChanges, DataBase db) {
		JPanel monitor_panel = new JPanel();
		monitor_panel.setLayout(new BoxLayout(monitor_panel, BoxLayout.Y_AXIS));
		monitor_panel.setBackground(aa_grey);
		
		JPanel header_panel = new JPanel();
		header_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		header_panel.setBackground(aa_grey);
		
		JLabel listOptions = new JLabel("Application Lists:");
		listOptions.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 17));
		listOptions.setForeground(Color.white);
		
		header_panel.add(Box.createRigidArea(new Dimension(15, 0)));
		header_panel.add(listOptions);
		
		//panel for whitelist and blacklist cards
		JPanel tables_panel = new JPanel();
		CardLayout cardLayout = new CardLayout();
		tables_panel.setLayout(cardLayout);
		tables_panel.setMaximumSize(new Dimension(400, 500));
		
		JPanel button_panel = new JPanel();
		button_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		button_panel.setBackground(aa_grey);
		
		JButton viewWhiteList = new JButton("On-Task Applications");
		viewWhiteList.setMaximumSize(new Dimension(70,20));
		viewWhiteList.setBackground(Color.GRAY);
		viewWhiteList.setForeground(Color.WHITE);
		viewWhiteList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//card panel for whitelist
				cardLayout.show(tables_panel, "whitelist"); 
				allowed = true;
			}
		});		
		
		JButton viewBlackList = new JButton("Off-Task Applications");
		viewBlackList.setMaximumSize(new Dimension(70,20));
		viewBlackList.setBackground(Color.GRAY);
		viewBlackList.setForeground(Color.WHITE);
		viewBlackList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//card panel for blackList
				cardLayout.show(tables_panel, "blacklist");
				allowed = false;
			}
		});		
		
		button_panel.add(Box.createRigidArea(new Dimension(40, 0)));
		button_panel.add(viewWhiteList);
		button_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		button_panel.add(viewBlackList);
		
		// whitelist == true
		// blacklist == false
		ArrayList<String> whiteList = db.SelectAllFromWBList(settingsChanges.getUserID(), true);
		ArrayList<String> blackList = db.SelectAllFromWBList(settingsChanges.getUserID(), false);
		String whiteListColumn = "On-Task Applications";
		String blackListColumn = "Off-Task Applications";
		
		JPanel whiteListPanel = generateTablePanels(db, settingsChanges, card_layout, card_panel, tableWL, whiteList, true, whiteListColumn);
		JPanel blackListPanel = generateTablePanels(db, settingsChanges, card_layout, card_panel, tableBL, blackList, false, blackListColumn);
		
		if (!isInstantiated) {
			allowed = true;
			isInstantiated = true;
		}
		
		tables_panel.add("whitelist", whiteListPanel);
		tables_panel.add("blacklist", blackListPanel);
		
		if (allowed) {
			cardLayout.show(tables_panel, "whitelist");
		} else {
			cardLayout.show(tables_panel, "blacklist");
		}
		
		monitor_panel.add(header_panel);
		monitor_panel.add(button_panel);
		monitor_panel.add(tables_panel);
		
		card_panel.add("monitor", monitor_panel);
	}
	
	public void open_parentPortal(DataBase db, Settings settings, Priority_Manager pm, Progress_Report pr, int userID) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
		
				Settings settingsChanges = new Settings(db, settings.getUserID());
				
				JFrame pPortal_Frame = new JFrame("Attention Assistant Parent Portal");
				
				pPortal_Frame.setUndecorated(true);
				pPortal_Frame.setPreferredSize(new Dimension(width, height)); 
				
				JPanel masterPanel = new JPanel(new BorderLayout());
				masterPanel.setBackground(Color.black);
				masterPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aa_purple));
				
				
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
						pPortal_Frame.setLocation(pPortal_Frame.getX() + e.getX() - mouseX, pPortal_Frame.getY() + e.getY() - mouseY);
					}
				});
				
				title_panel.addMouseListener(new MouseAdapter(){
					@Override 
					public void mousePressed(MouseEvent e) {
						mouseX = e.getX();
						mouseY = e.getY();
					}
				});
				
				User_Account user = db.SelectUser_Account(userID);
				JLabel title = new JLabel("Parent Portal for "+user.getName());
				title.setForeground(Color.white);
				title.setFont(new Font("Serif", Font.BOLD, 20));
				
				/*
				 * create icons to use as buttons for title bar
				 */
				BufferedImage ci = null;
				BufferedImage gi = null;
				BufferedImage exit = null;
				BufferedImage lo = null;
				
				try {
					ci = ImageIO.read(new File("images/exit_circle.png"));
					gi = ImageIO.read(new File("images/guide.png"));
					exit = ImageIO.read(new File("images/AA_exit.png"));
					lo = ImageIO.read(new File("images/logout.png"));
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
		        		pPortal_Frame.dispose();
		        	
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
						guide.open_Guide("Parent Portal");
		        }});
				
				title_panel.add(title);
				title_panel.add(Box.createRigidArea(new Dimension(250, 0)));
				title_panel.add(guide);
				title_panel.add(close_window);
				
				//card layout and panel for RHS of parent portal that displays sub-menu with each button option 
				JPanel card_panel = new JPanel();
				//creates layout for sub-menus and panels for each sub-menu
				CardLayout card_layout = new CardLayout();
				card_panel.setLayout(card_layout);
				
				try {
					createSettingsPanel(card_panel, settingsChanges, pm, db, userID, pr);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				createMonitoringManagementPanel(card_layout, card_panel, settingsChanges, db);
				
				JButton generalSettings = new JButton("<html><center>General" + "<br/>Settings</center></html>");
				JButton monitoringManagement = new JButton("<html><center>Monitoring" + "<br/>Management</center></html>");
				
				/*
				 * specifications for settings button
				 */
				generalSettings.setForeground(Color.white);
				generalSettings.setFont(new Font("Serif", Font.BOLD, 16));
				generalSettings.setContentAreaFilled(true);
				generalSettings.setBorderPainted(true);
				generalSettings.setBorder(new LineBorder(aa_purple));
				generalSettings.setFocusPainted(false);
				generalSettings.setBackground(aa_grey);
				generalSettings.setMaximumSize(new Dimension(175, 47));
				generalSettings.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open general sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						monitoringManagement.setBorderPainted(false);
						generalSettings.setBorderPainted(true);
						generalSettings.setBorder(new LineBorder(aa_purple));
						card_layout.show(card_panel, "settings");						
					}
				});
				
				monitoringManagement.setForeground(Color.white);
				monitoringManagement.setFont(new Font("Serif", Font.BOLD, 16));
				monitoringManagement.setContentAreaFilled(true);
				monitoringManagement.setBorderPainted(false);
				monitoringManagement.setFocusPainted(false);
				monitoringManagement.setBackground(aa_grey);
				monitoringManagement.setMaximumSize(new Dimension(175, 47));
				monitoringManagement.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*
						 * open notification systems sub-menu
						 * adjusts border of all buttons to put purple border on selected button
						 */
						generalSettings.setBorderPainted(false);
						monitoringManagement.setBorderPainted(true);
						monitoringManagement.setBorder(new LineBorder(aa_purple));
						card_layout.show(card_panel, "monitor");
					}
				});
				
				JButton progressReport = new JButton("<html><center>Download" + "<br/>Progress Report</center></html>");
				progressReport.setForeground(Color.white);
				progressReport.setFont(new Font("Serif", Font.BOLD, 16));
				progressReport.setContentAreaFilled(true);
				progressReport.setBorderPainted(false);
				progressReport.setFocusPainted(false);
				progressReport.setBackground(aa_purple);
				progressReport.setMaximumSize(new Dimension(170, 47));
				progressReport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//download progress report
						pr.downloadProgressReport(userID, db); 
					}
				});
				
				JButton guideButton = new JButton(guideIcon);
				guideButton.setText("<html><center>Guide" + "<br/>       </center></html>");
				guideButton.setForeground(Color.white);
				guideButton.setFont(new Font("Serif", Font.BOLD, 16));
				guideButton.setContentAreaFilled(true);
				guideButton.setBorderPainted(false);
				guideButton.setFocusPainted(false);
				guideButton.setBackground(aa_purple);
				guideButton.setMaximumSize(new Dimension(170, 30));
				guideButton.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		//opens guide
		        		Guide guide = new Guide();
						guide.open_Guide("Child Selection Bar");
		        }});
				
				Image logOut_img = lo.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
				Icon logOutIcon = new ImageIcon(logOut_img);
				
				JButton logOutButton = new JButton(logOutIcon);
				logOutButton.setText("<html><center>Log Out" + "<br/>       </center></html>");
				logOutButton.setForeground(Color.white);
				logOutButton.setFont(new Font("Serif", Font.BOLD, 16));
				logOutButton.setContentAreaFilled(true);
				logOutButton.setBorderPainted(false);
				logOutButton.setFocusPainted(false);
				logOutButton.setBackground(aa_purple);
				logOutButton.setMaximumSize(new Dimension(170, 30));
				logOutButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Logout l = new Logout();
						l.run_logout();
					}
				});
				
				Image ex_img = exit.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
				Icon exitIcon = new ImageIcon(ex_img);
				
				JButton exit_AA = new JButton(exitIcon);
				exit_AA.setText("EXIT");
				exit_AA.setForeground(Color.white);
				exit_AA.setFont(new Font("Serif", Font.BOLD, 16));
				exit_AA.setContentAreaFilled(true);
				exit_AA.setBorderPainted(false);
				exit_AA.setFocusPainted(false);
				exit_AA.setBackground(aa_purple);
				exit_AA.setMaximumSize(new Dimension(170, 30));
				exit_AA.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// exit entire application 
						UIManager.put("Button.foreground", aa_purple);
						UIManager.put("Button.background", aa_grey);
						UIManager.put("OptionPane.background", Color.black);
						UIManager.put("Panel.setOpaque", true);
						UIManager.put("Panel.background", aa_grey);
						UIManager.put("TextField.selectionBackground", Color.WHITE);
						UIManager.put("TextField.selectionForeground", Color.WHITE);
						
						JLabel warning = new JLabel("<html><center>Are you sure you want to EXIT" + "<br/>The Attention Assistant?</center></html>");
						warning.setFont(new Font("Serif", Font.BOLD, 16));
						warning.setForeground(Color.white);
						
						int response = JOptionPane.showConfirmDialog(null, warning, "The Attention Assistant", JOptionPane.OK_CANCEL_OPTION);
						switch (response) {
						case JOptionPane.OK_OPTION:
							System.exit(0); 
						case JOptionPane.CANCEL_OPTION:
							break; 
						}
					}
				});
				
				/*
				 * buttons for bottom border
				 */
				JButton apply = new JButton("apply");
				apply.setForeground(Color.white);
				apply.setFont(new Font("Serif", Font.BOLD, 16));
				apply.setContentAreaFilled(true);
				apply.setBorderPainted(true);
				apply.setBorder(new LineBorder(Color.GRAY));
				apply.setFocusPainted(false);
				apply.setBackground(aa_grey);
				apply.setMaximumSize(new Dimension(75,30));
				/*
				 * When apply is selected all changes made to settingsChanges are applied to settings
				 */
				apply.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						settings.setTimerIsVisible(settingsChanges.getTimerIsVisible()); 
						settings.setPmIsVisible(settingsChanges.getPmIsVisible()); 
						settings.setFtsIsVisible(settingsChanges.getFtsIsVisible()); 
						settings.setHtbIsVisible(settingsChanges.getHtbIsVisible());
						settings.setNtbIsVisible(settingsChanges.getNtbIsVisible()); 
						settings.setProgReportIsVisible(settingsChanges.getProgReportIsVisible()); 
						settings.setTimerVisibilityIsLocked(settingsChanges.getTimerVisibilityIsLocked());
						settings.setPmVisibilityIsLocked(settingsChanges.getPmVisibilityIsLocked());
						settings.setFtsVisibilityIsLocked(settingsChanges.getFtsVisibilityIsLocked());
						settings.setHtbVisibilityIsLocked(settingsChanges.getHtbVisibilityIsLocked());
						settings.setNtbVisibilityIsLocked(settingsChanges.getNtbVisibilityIsLocked());
						settings.setProgReportVisibilityIsLocked(settingsChanges.getProgReportVisibilityIsLocked());
						settings.setPomodoroIsActive(settingsChanges.getPomodoroIsActive()); 
						settings.setPomodoroIsLocked(settingsChanges.getPomodoroIsLocked());
						settings.setWorkPeriod(settingsChanges.getWorkPeriod()); 
						settings.setWorkPeriodIsLocked(settingsChanges.getWorkPeriodIsLocked());
						settings.setBreakPeriod(settingsChanges.getBreakPeriod()); 
						settings.setBreakPeriodIsLocked(settingsChanges.getBreakPeriodIsLocked()); 
						settings.setFtsIsActive(settingsChanges.getFtsIsActive()); 
						settings.setNtbIsActive(settingsChanges.getNtbIsActive());  
						settings.setHtbIsActive(settingsChanges.getHtbIsActive());
						settings.setFtsIsLocked(settingsChanges.getFtsIsLocked());
						settings.setNtbIsLocked(settingsChanges.getNtbIsLocked());
						settings.setHtbIsLocked(settingsChanges.getHtbIsLocked());
						
						if(settings.getFtsIsActive() == false) {
							ftsVisibleBox.setEnabled(false);
						}else if(settings.getFtsIsActive() == true) {
							ftsVisibleBox.setEnabled(true); 
						}

						if(settings.getHtbIsActive() == false) {
							htbVisibleBox.setEnabled(false);
						}else if(settings.getHtbIsActive() == true) {
							htbVisibleBox.setEnabled(true);
						}

						if(settings.getNtbIsActive() == false) {
							ntbVisibleBox.setEnabled(false);
						}else if(settings.getNtbIsActive() == true) {
							ntbVisibleBox.setEnabled(true);
						}

						if(settings.getPomodoroIsActive() == false) {
							timerVisibleBox.setEnabled(false);
						}else if(settings.getPomodoroIsActive() == true) {
							timerVisibleBox.setEnabled(true);
						}
						
						db.UpdateSettings(settings);	
					}
				});
				
				JButton cancel = new JButton("cancel");
				cancel.setForeground(Color.white);
				cancel.setFont(new Font("Serif", Font.BOLD, 16));
				cancel.setContentAreaFilled(true);
				cancel.setBorderPainted(true);
				cancel.setBorder(new LineBorder(Color.GRAY));
				cancel.setFocusPainted(false);
				cancel.setBackground(aa_grey);
				cancel.setMaximumSize(new Dimension(80,30));
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//close window without saving info
						pPortal_Frame.dispose();
					}
				});
				
				
				/*
				 * creates layout for left side panel and buttons for side-bar sub-menus 
				 */ 
				JPanel sideMenu = new JPanel();
				sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
				sideMenu.setPreferredSize(new Dimension(160, 675));
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(generalSettings);
				sideMenu.add(Box.createRigidArea(new Dimension(0,15)));
				sideMenu.add(monitoringManagement);
				sideMenu.add(Box.createRigidArea(new Dimension(0,335)));
				sideMenu.add(progressReport);
				sideMenu.add(Box.createRigidArea(new Dimension(0,10)));
				sideMenu.add(guideButton);
				sideMenu.add(Box.createRigidArea(new Dimension(0,10)));
				sideMenu.add(logOutButton);
				sideMenu.add(Box.createRigidArea(new Dimension(0,10)));
				sideMenu.add(exit_AA);
				sideMenu.setBackground(Color.black);
				sideMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, aa_purple));
				
				/*
				 * creates split center panel
				 */
				JPanel center_panel = new JPanel(new BorderLayout());
				center_panel.setBackground(Color.black);
				center_panel.add(card_panel, BorderLayout.CENTER);
				center_panel.add(sideMenu, BorderLayout.WEST);
				center_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, aa_purple));
			
				/*
				 * creates layout for panel and buttons along bottom of frame
				 */
				JPanel bottomButtons = new JPanel();
				bottomButtons.setLayout(new BoxLayout(bottomButtons, BoxLayout.X_AXIS));
				bottomButtons.setMaximumSize(new Dimension(500,35));
				bottomButtons.add(Box.createRigidArea(new Dimension(415, 0)));
				bottomButtons.add(apply);
				bottomButtons.add(Box.createRigidArea(new Dimension(15, 0)));
				bottomButtons.add(cancel);
				bottomButtons.setBackground(Color.black);
				
				/*
				 * populates master panel 
				 */
				masterPanel.add(title_panel, BorderLayout.PAGE_START); 
				masterPanel.add(center_panel, BorderLayout.CENTER);
				masterPanel.add(bottomButtons, BorderLayout.PAGE_END);
			
				/*
				 * adds master panel to frame
				 */
				pPortal_Frame.getContentPane().add(masterPanel); 
				pPortal_Frame.getContentPane().setBackground(Color.black);
				pPortal_Frame.pack();
				pPortal_Frame.setAlwaysOnTop(false);
				pPortal_Frame.setVisible(true);
				pPortal_Frame.setResizable(true);
				pPortal_Frame.setLocationRelativeTo(null);
				
			}
		});
	}
}
