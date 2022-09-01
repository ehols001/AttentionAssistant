package AttentionAssistant;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.UIManager.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import AttentionAssistant.Pomodoro_Timer.Work_Break;

import java.io.File;


//pomodoro_timer.refresh
//TODO loggin system for how much user is using timer ie. get count/get timer
//TODO get status for timer


public class Pomodoro_Timer 
{
	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	Monitoring_Bar mb;
	int initalbreak = 0, initalmin = 0, breakmin =0,min= 0,sec=0;
	private boolean MainTimerRunning;
	private boolean BreakTimerRunning;
	private boolean paused;
	private boolean pomodoro_active;
	private JButton lastButtonPressed;  
	JButton toRefresh;
	JButton taskRefresh;
	JButton tobreak;
	LineBorder line = new LineBorder(aa_purple, 2, true);
	JLabel time = new JLabel("00m:00s");
	JButton startbut=new JButton("Start");
	JButton pausebut=new JButton("Pause");
	JButton endbut=new JButton("Reset");
	JLabel c=new JLabel("Work Timer");
	JLabel b=new JLabel("Break Timer");
	private boolean breakboolean = false;
	private int mouseX;
	private int mouseY;
	int height = 600;
	int width = 600;

	Timer t;
	
	/*
	 * instantiating empty timer object
	 */
	public Pomodoro_Timer(Settings settings,DataBase db,Priority_Manager pm) {
		this.MainTimerRunning = false;
		this.BreakTimerRunning = false;
		this.paused = false;
		this.pomodoro_active = false;
		this.lastButtonPressed = null;
		
		run_pomo(settings,db,pm);
	}
	
	public Pomodoro_Timer() {
		this.MainTimerRunning = false;
		this.BreakTimerRunning = false;
		this.paused = false;
		this.pomodoro_active = false;
		this.lastButtonPressed = null;
	}
	public void monitorbar(Monitoring_Bar monitoring) {
		mb = monitoring;
		
	}
	public void makeVisible() {
		visibleButton.doClick();
	}
	
	public enum Work_Break {
		Work,
		Break,
		Null,

	}
	public Work_Break getWorkBreakStatus(){
		
		  
		if (BreakTimerRunning == true && MainTimerRunning == false) {
			System.out.println("Break");
			return Work_Break.Break;
			 
		}
		else if (MainTimerRunning == true && BreakTimerRunning == false){
			System.out.println("Work");
			return Work_Break.Work;
		}
		else if (paused == true){
			System.out.println("Paused");
			return Work_Break.Null;	
		}
		else {
			System.out.println("other");
			return Work_Break.Null;
		}

	}
	public Work_Break getWBMonitor(){
		  
		if (BreakTimerRunning == true && MainTimerRunning == false) {
			return Work_Break.Break;
			 
		}
		else if (MainTimerRunning == true && BreakTimerRunning == false){
			return Work_Break.Work;
		}
		else if (paused == true){
			if(getWorkBreakStatus() == Work_Break.Null) {
				return Work_Break.Work;	
			}else {
				return null;
			}
		}
		else {
			if(getWorkBreakStatus() == Work_Break.Null) {
				return Work_Break.Work;	
			}
			else {
				return null;
			}
		}

	}
	

	public void Input(Settings settings) {
		
		int maintime;
		maintime = settings.getWorkPeriod();
		min = maintime;
		initalmin = min;
		int breaktime;
		breaktime = settings.getBreakPeriod();
		breakmin = breaktime;
		initalbreak = breakmin;
	
	}
	
	public ArrayList<Task> tasks(Priority_Manager pm) {
		ArrayList<Task> list = new ArrayList<Task>();
		list  =  pm.observableTasks();
		
		return list;
	}
	
	public String ActiveTask(Priority_Manager pm) {
		String taskLabel;
		
		if(pm.getActiveTask() == null) {
			taskLabel = " ";
		}else {
			taskLabel = new String("Task: " + pm.getActiveTask().getTaskName());
		}
		
		return taskLabel;
	
	}
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
		JLabel title = new JLabel("Pomodoro Timer      ");
		title.setForeground(Color.white);
		title.setBounds(0,0,200,200);
		title.setFont(new Font("Dosis SemiBold", Font.BOLD, 20));
		
		/*
		 * create icons to use as buttons for title bar
		 */
		BufferedImage ci = null;
		BufferedImage gi = null;
		BufferedImage exit = null;
		
		try {
			ci = ImageIO.read(new File("images/exit_circle.png"));
			gi = ImageIO.read(new File("images/guide.png"));
			exit = ImageIO.read(new File("images/AA_exit.png"));
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
    			frame.setVisible(false);
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
				guide.open_Guide("Pomodoro Timer");
        }});
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(275, 0)));
		title_panel.add(guide);
		title_panel.add(close_window);
		
		//returns panel
		return title_panel;
	}

	public void clickStart() {
		startbut.doClick();
	}
	public void clickPause() {
		pausebut.doClick();
	}
	
	private JPanel taskPanel(Priority_Manager pm) {
		JPanel panel = new JPanel();
		panel.setBackground(aa_grey);	
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setPreferredSize(new Dimension(500,190));
		
		JLabel taskLabel=new JLabel(ActiveTask(pm));
		taskLabel.setForeground(Color.white);
		taskLabel.setFont(new Font("Dosis SemiBold",Font.BOLD,22));
		panel.add(taskLabel);
		
		panel.add(Box.createRigidArea(new Dimension(0,350)));
		
		return panel;
	}
	private JPanel clockPanel(JFrame frame,CardLayout cardLayout, Priority_Manager pm,Settings setting, DataBase db) {
		JPanel panel = new JPanel();
		panel.setBackground(aa_grey);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setPreferredSize(new Dimension(500,60));
		
		time.setForeground(Color.white);
		time.setFont(new Font("Dosis SemiBold",Font.BOLD,50));
		panel.add(time);

		return panel;
	}
	
	private JPanel ButtonPanel(JFrame frame,CardLayout cardLayout, Priority_Manager pm,Settings setting, DataBase db) {
	
		JPanel panel = new JPanel();
		panel.setBackground(aa_grey);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setPreferredSize(new Dimension(500,80));
		
	//	startbut.setPreferredSize(new Dimension(55,55));
		startbut.setBorderPainted(false);
		startbut.setBackground(aa_purple);
		startbut.setForeground(Color.WHITE);
		startbut.setFont(new Font("San Francisco", Font.BOLD, 15));
		startbut.setRolloverEnabled(false);
		startbut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {

        		JButton buttonPressed = (JButton) e.getSource();
    			if(lastButtonPressed == buttonPressed)
    			{
    				//JOptionPane.showMessageDialog(frame, "Please do not push the same button twice.");
    				System.out.print(1);
    			
    			}
    			else {
    				
        			if(e.getSource()==startbut) {		
        				paused = false;
        			
        					if (b.isVisible()  == true) {
            					t.start();
            		
            				}
            				else {
            				MainTimer(setting,db,pm);
             	
            				}
        				}
        			
        		
        			}	
    			
    			
    			lastButtonPressed = buttonPressed;
        	}});
			//panel.add(Box.createRigidArea(new Dimension(10, 0)));
			panel.add(startbut);
			
		//pausebut.setPreferredSize(new Dimension(55,55));
		pausebut.setBorderPainted(false);
		pausebut.setBackground(aa_purple);
		pausebut.setForeground(Color.WHITE);
		pausebut.setFont(new Font("San Francisco", Font.BOLD, 15));
		pausebut.setRolloverEnabled(false);
		pausebut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JButton buttonPressed = (JButton) e.getSource();
        	
       		if(lastButtonPressed == buttonPressed)
        		{        			
       			System.out.print(1);
        		}
       		else {
       			if(e.getSource()==pausebut) {
       				paused = true;
    				if(t == null) {
    					JFrame frame = new JFrame();
    					JOptionPane.showMessageDialog(frame, "Timer has not begun.");
    				}
    				else {
    					t.stop();
    					if(BreakTimerRunning == true) {
    						BreakTimerRunning = false;
    						getWorkBreakStatus();
    					}
    					else if (MainTimerRunning  == true) {
    						MainTimerRunning = false;
    						getWorkBreakStatus();
    						
    					}
   
    				}
    				
    			}
       		}
        		paused = false;
        		lastButtonPressed = buttonPressed;
        		
        }});
		//panel.add(Box.createRigidArea(new Dimension(10, 0)));
		panel.add(pausebut);
		
		//endbut.setPreferredSize(new Dimension(55,35));
		endbut.setBorderPainted(false);
		endbut.setBackground(aa_purple);
		endbut.setForeground(Color.WHITE);
		endbut.setFont(new Font("San Francisco", Font.BOLD, 15));
		endbut.setRolloverEnabled(false);
		endbut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JButton buttonPressed = (JButton) e.getSource();
   				if(lastButtonPressed == buttonPressed){
   					System.out.print(1);
    				}
   				else {
   					if(e.getSource()==endbut) {
   	        		
   	        			if(t == null) {
   	    					JFrame frame = new JFrame();
   	    					JOptionPane.showMessageDialog(frame, "Timer has not begun.");
   	    					getWorkBreakStatus();
   	    				}
   	    				else {
   	    				t.stop();
   	    				sec= 0;
   	    				min=0;
   	    				breakmin =0;
   	    				c.setVisible(false);
   	    				b.setVisible(false);
   	    				MainTimerRunning = false;
   	    				BreakTimerRunning = false;
   	    				paused = false;
   	    				time.setText(String.valueOf("00m:00s"));
   	    		   	    lastButtonPressed = null;
   	    		   	    min = initalmin;
   	    		   	    breakmin = initalbreak;
   	    				getWorkBreakStatus();
   	    				
   	    				}	
   				}
    				
    				
        			lastButtonPressed = buttonPressed;
        		
    			}
    			
        		
        }});
	//	panel.add(Box.createRigidArea(new Dimension(10, 0)));
		panel.add(endbut);
		
		if(min == 0 && breakmin == 0) {
			lastButtonPressed = null; 	
		}
		return panel;
		
	}


	public JPanel Labelpanel() {
		JPanel panel = new JPanel();
		panel.setBackground(aa_grey);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setPreferredSize(new Dimension(500,30));
		
	
			//c.setPreferredSize(new Dimension(80,75));
				c.setForeground(Color.white);
				c.setFont(new Font("Dosis SemiBold",Font.BOLD,20));;
				panel.add(c);
			//	c.setPreferredSize(new Dimension(80,75));
				b.setForeground(Color.white);
				b.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
				panel.add(b);
				
				c.setVisible(false);
				b.setVisible(false);
				
				//panel.add(Box.createRigidArea(new Dimension(0,15)));
				
				return panel;
	}
	
	/**
	 * break timer function. Creates the break  timer from user input and also ensures that the timer stops properly at 00:00
	 */
	public void BreakTimer(Settings setting, DataBase db,Priority_Manager pm) {
		Notification_System notif;
		try {
			notif = new Notification_System(setting.getUserID(),db);
			notif.breakTime();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		t = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String  ddsecond,ddminute;
				DecimalFormat dformat = new DecimalFormat("00");
				
				
				sec--;
				ddsecond = dformat.format(sec);
				ddminute = dformat.format(breakmin);
				time.setText(String.valueOf(ddminute+"m:"+ddsecond+"s"));
				if(breakmin == 0 && sec==0) {
					t.stop();
					b.setVisible(false);
					Notification_System notifs;
					try {
						notifs = new Notification_System(setting.getUserID(),db);
						notifs.workTime(pm.activeTask);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (breakboolean == true) {
						breakboolean = false;
						mb.refreshBar();
					}
					else {
						mb.refreshBar();
						 Object[] options = {"Begin Timer"};
						 int breaktimertask = JOptionPane.showOptionDialog(null,
						             "Break is up! Time to begin your work again!",
						             "Break Timer is up!",
						             JOptionPane.YES_NO_CANCEL_OPTION,
						             JOptionPane.DEFAULT_OPTION,
						             null,
						             options,
						             options[0]);  

						 System.out.println(breaktimertask);  

						

						 if(breaktimertask==0){  //clicking this button will begin timer
							 MainTimer(setting, db,pm);
								min = initalmin;	
						 }
					}
				
					 }
				

				
				if(sec == -1) {
					sec =59;
					breakmin--;
					ddsecond = dformat.format(sec);
					ddminute = dformat.format(breakmin);
					time.setText(String.valueOf(ddminute+"m:"+ddsecond+"s"));	
					
					
				}
				
		
			}
			
		});
		if(min == 0 && breakmin == 0) {
			JFrame errorframe = new JFrame();
			JOptionPane.showMessageDialog(errorframe, "You have not set your work and break peroids. Please go to settings to enable them.");
			lastButtonPressed = null;
			BreakTimerRunning = false;
			MainTimerRunning = false;
			b.setVisible(false);
			//getWorkBreakStatus();
			
		}else {
			t.start();
			b.setVisible(true);
			BreakTimerRunning = true;
			MainTimerRunning = false;
			paused = false;
			min = initalmin;
			breakmin = initalbreak;
		}
	}
	/**
	 * main timer function. Creates the main pomodoro timer from user input and also ensures that the timer stops properly at 00:00
	 */
	
	public void TaskDropDown(Settings setting, DataBase db, Priority_Manager pm) {
		   JFrame jFrame = new JFrame();
		   
		   String[] array = new String[tasks(pm).size()];
		   
		   for(int i=0; i< tasks(pm).size(); i++) {
			 array[i] = tasks(pm).get(i).getTaskName();
		   }
	        JComboBox<String> jComboBox = new JComboBox<>(array);
	        jComboBox.setBounds(80, 50, 200, 20);

	        JButton jButton = new JButton("Done");
	        jButton.setBounds(120, 100, 90, 20);

	        JLabel jLabel = new JLabel();
	        jLabel.setBounds(90, 100, 400, 100);

	        jFrame.add(jButton);
	        jFrame.add(jComboBox);
	        jFrame.add(jLabel);
	        
	        jFrame.setLocationRelativeTo(null);
	        jFrame.setLayout(null);
	        jFrame.setSize(350, 250);
	        jFrame.setVisible(true);
	        
	        
	        jButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String newtask = jComboBox.getItemAt(jComboBox.getSelectedIndex()) + " is your new active task!";
	                int taskint =jComboBox.getSelectedIndex();
	                Task thistask;
	                thistask = tasks(pm).get(taskint);
	                jLabel.setText(newtask);
	                int userid =setting.getUserID();
	                try {
						pm.observeTask(userid, thistask, db, true,pm);
						lastButtonPressed = null;
						BreakTimerRunning = false;
						MainTimerRunning = false;
						paused = false;
						min = initalmin;
						breakmin = initalbreak;
						refresh(setting);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	         
	            }
	        });


	}
	
	
	public void MainTimer(Settings setting, DataBase db,Priority_Manager pm) {
	t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String  ddsecond,ddminute;
				DecimalFormat dformat = new DecimalFormat("00");	
				sec--;
				
				ddsecond = dformat.format(sec);
				ddminute = dformat.format(min);
				time.setText(String.valueOf(ddminute+"m:"+ddsecond+"s"));
				if(min == 0 && sec==0) {
					MainTimerRunning = false;
					BreakTimerRunning = false;
					paused = false;
					t.stop();
					c.setVisible(false);
					if (breakboolean == true) {
						mb.refreshBar();
						breakboolean = false;
					}
					else {
						mb.refreshBar();
						 Object[] options = {"Yes","No"};
						 int initaltask = JOptionPane.showOptionDialog(null,
						             "Have you completed your task?",
						             "Tasks",
						             JOptionPane.YES_NO_CANCEL_OPTION,
						             JOptionPane.DEFAULT_OPTION,
						             null,
						             options,
						             options[1]);  

						// System.out.println(initaltask);  

						

						 if(initaltask==0){  //for yes; the user has finished their initial task and will need to pick/assign a new task, or terminate the timer
							 
							 Notification_System notif;
								try {
									notif = new Notification_System(setting.getUserID(),db);
									 notif.taskCompleted(pm.getActiveTask());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							 Object[] NewTask = {"Yes","No"}; //new option button to ask user if they have any other tasks to work on
							 int NewTaskInt = JOptionPane.showOptionDialog(null,
							             "Do you have any other tasks to work on?",
							             "Other Tasks",
							             JOptionPane.YES_NO_CANCEL_OPTION,
							             JOptionPane.DEFAULT_OPTION,
							             null,
							             NewTask,
							             NewTask[1]);  

					
							 if(NewTaskInt==0){ //for yes; i.e. user has another tasks to work on
								  //ask the user to assign what task they are currently working on (pulled from database) 
								 //TODO add another button where they can select their new task
								 //prompt the user to input a new break and work timespan allotment
								    t.stop();
									sec=min=0;
									time.setText(String.valueOf("00m:00s"));
									//Maininput(); //TODO this is going to need to redirect the user back to the setting
									//Breakinput(); //this prompts the user for new work timespan allotments, will need to occur after new task is assigned
									c.setVisible(false);
									b.setVisible(false);
								
									TaskDropDown(setting, db, pm);
								 
								 }else if(NewTaskInt==1){ //for no, meaning that they have no new tasks to work on...
									 //(ask the user to assign a new task via priority manager)
									 //or terminate the pomodoro timer
											 Object[] NoNewTask = {"Add New Task","Close Pomodoro Timer"};
											 int NonewTaskInt = JOptionPane.showOptionDialog(null,
											             "Tasks",
											             "Have you completed your task?",
											             JOptionPane.YES_NO_CANCEL_OPTION,
											             JOptionPane.DEFAULT_OPTION,
											             null,
											             NoNewTask,
											             NoNewTask[1]);  
								
										 
									
											
									
											 if(NonewTaskInt==0){  //for yes
												 TaskDropDown(setting, db, pm);
												 lastButtonPressed = null;
											 }else if(NonewTaskInt==1){ //for Close Pomodoro Timer
												System.exit(1);
											 }else{ //none selected
											     System.out.println("no option choosen");
											 }
										
								 }else{ //none selected
								     System.out.println("no option choosen");
								 }

						
						 }else if(initaltask==1){ //for no
							 //break timer repeats; user has not finished inital task. 
							BreakTimer(setting, db,pm);
							MainTimerRunning = false;
							BreakTimerRunning = true;
							paused = false;
							lastButtonPressed = null;
							getWorkBreakStatus();
						 }else{ //none selected
						     System.out.println("no option choosen");
						 }
						
					}
					
				
				}
				
				
				if(sec == -1) {
					sec =59;
					min--;
					ddsecond = dformat.format(sec);
					ddminute = dformat.format(min);
					time.setText(String.valueOf(ddminute+"m:"+ddsecond+"s"));	
					
				}				
			}		
		});


		if(min == 0 && breakmin == 0) {
			JFrame errorframe = new JFrame();
			JOptionPane.showMessageDialog(errorframe, "You have not set your work and break peroids. Please go to settings to enable them.");
			lastButtonPressed = null;
			c.setVisible(false);
			BreakTimerRunning = false;
			MainTimerRunning = false;
			paused = false;
			getWorkBreakStatus();
		}else {
			t.start();
			c.setVisible(true);
			BreakTimerRunning = false;
			MainTimerRunning = true;
			paused = false;
			getWorkBreakStatus();
			
		}
	}
	
	

	/**
	 * initializes the buttons and adds them to the frame, and initializes the labels that are used depending on what timer is running
	 */
	JPanel icon_panel;
	JPanel task_panel;
	JPanel button_panel;
	JPanel label_panel;
	int counter;
	int count;
	JButton visibleButton;
	public void run_pomo(Settings settings,DataBase db, Priority_Manager pm) {
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run() {
				counter = 1;
				count = 1;
				
				//set up frame
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.setBackground(aa_grey);
				//sets window width and height
				
				//build title panel
				JMenuBar titlePanel = titlePanel(frame);
				titlePanel.setBorder(line);
			
				CardLayout cardLayout = new CardLayout();
				CardLayout cardLayout2 = new CardLayout();
				
				task_panel = taskPanel(pm);
				icon_panel = clockPanel(frame,cardLayout,pm, settings, db);
				button_panel = ButtonPanel(frame,cardLayout,pm, settings, db);
				label_panel = Labelpanel();
				
				
				JPanel taskPanel = new JPanel();
				JPanel iconPanel = new JPanel();
				JPanel buttonPanel = new JPanel();
				JPanel labelPanel = new JPanel();
				taskPanel.setBackground(aa_grey);
				iconPanel.setBackground(aa_grey);
				buttonPanel.setBackground(aa_grey);
				labelPanel.setBackground(aa_grey);
				
				
				taskPanel.setLayout(cardLayout2);
				iconPanel.setLayout(cardLayout);
				
				JPanel master = new JPanel();
				//master.setLayout(new BoxLayout(master,BoxLayout.Y_AXIS));
				//master.setLayout(null);
				master.setBackground(aa_grey);
		
			
				taskPanel.add("tPanel",task_panel);
				iconPanel.add("iPanel",icon_panel);
				buttonPanel.add("bPanel",button_panel);
				labelPanel.add("lPanel",label_panel);
				
				master.add(Box.createRigidArea(new Dimension(0, 115)));
				master.add(taskPanel);
				master.add(iconPanel);
				master.add(labelPanel);
				master.add(buttonPanel);
			
				master.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
			    cardLayout.show(iconPanel, "iPanel");
			    cardLayout2.show(taskPanel, "tPanel");
			    //panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
				frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
				frame.getContentPane().add(master,BorderLayout.CENTER);
				//frame.getContentPane().add(labelPanel,BorderLayout.AFTER_LAST_LINE);
			
				frame.setPreferredSize(new Dimension(width, height)); 

				frame.pack();
				frame.setVisible(false);
				frame.setResizable(true);
				frame.setLocationRelativeTo(null);
				Input(settings);
			
				
				tobreak = new JButton();
				tobreak.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		tobreak(settings,db,pm);
		        	}});
		        
				toRefresh = new JButton();
		        toRefresh.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		rebuildPanel(settings, db, cardLayout,iconPanel, frame, pm);
		        	}});
		        
		        taskRefresh = new JButton();
		        taskRefresh.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent f) {
		        		rebuildTaskPanel(pm, cardLayout2,taskPanel, frame);
		        	}});
		        
		        visibleButton = new JButton();
		        visibleButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent g) {
						// TODO Auto-generated method stub
						System.out.println("visible button clicked");
						frame.setVisible(true);
					}
		        	
		        });
			}
		});
	}
	
	
	private void rebuildTaskPanel(Priority_Manager pm,CardLayout cardLayout,JPanel panel,JFrame frame) {
		JPanel new_task_panel = new JPanel();
		if(counter % 2 != 0) {
			new_task_panel = taskPanel(pm);
			panel.add("newTPanel",new_task_panel);
			cardLayout.show(panel, "newTPanel");
			panel.remove(task_panel);
		}else {
			task_panel = taskPanel(pm);
			panel.add("tPanel",task_panel);
			cardLayout.show(panel, "tPanel");
			panel.remove(new_task_panel);
		}
		counter++;
		panel.revalidate();
		panel.repaint();
		frame.revalidate();
		frame.repaint();
	}
	
	public void taskrefresh() {

		taskRefresh.doClick();
	
	}
	
	
	
	public void refresh(Settings settings){
		min = 0;
		breakmin =0;
		sec =0;
		min = settings.getWorkPeriod();
		initalmin = min;
		breakmin = settings.getBreakPeriod();
		initalbreak = breakmin;
		this.pomodoro_active = settings.getPomodoroIsActive();
   	    MainTimerRunning = false;
   	    BreakTimerRunning = false;
   	    paused = false;	
   	    
		if(this.t != null) {
			t.stop();
			time.setText(String.valueOf("00m:00s"));
			c.setVisible(false);
	   	    b.setVisible(false);
	   	    lastButtonPressed = null;
		}
		
		if (counter != 0 ) {
			toRefresh.doClick();
		}
	}
	public void rebuildPanel(Settings setting, DataBase db,CardLayout cardLayout,JPanel panel, JFrame frame, Priority_Manager pm) {
		JPanel new_icon_panel = new JPanel();
		
		if(counter % 2 != 0) {
	
			new_icon_panel = clockPanel(frame,cardLayout,pm, setting, db);
			panel.add("newIPanel",new_icon_panel);
			cardLayout.show(panel, "newIPanel");
			panel.remove(icon_panel);
	
		}else {
			panel.remove(icon_panel);
			icon_panel = clockPanel(frame,cardLayout, pm, setting, db);
			panel.add("iPanel",icon_panel);
			cardLayout.show(panel, "iPanel");
			panel.remove(new_icon_panel);
		}
		counter++;
		panel.revalidate();
		panel.repaint();
		frame.revalidate();
		frame.repaint();
	}
	
	public void setbuttonto0(int userID,DataBase db,Priority_Manager pm) {
		if(getWorkBreakStatus() == Work_Break.Break) {
			try {
				Notification_System notif = new Notification_System(userID, db);
				notif.workTime(pm.getActiveTask());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		tobreak.doClick();
	
	}
	
	public void tobreak(Settings setting, DataBase db,Priority_Manager pm) {
		breakboolean = true;
		if(c.isVisible() == true) { //worktimer is true
				t.stop();
				sec= 0;
				min=0;
				breakmin =0;
				c.setVisible(false);
				b.setVisible(false);
				MainTimerRunning = false;
				BreakTimerRunning = true;
				paused = false;
				time.setText(String.valueOf("00m:00s"));
		   	    lastButtonPressed = null;
		   	    min = initalmin;
		   	    breakmin = initalbreak;
				BreakTimer(setting,db,pm);				
				
		}
		else if(b.isVisible() == true) {
			t.stop();
			sec= 0;
			min=0;
			breakmin =0;
			c.setVisible(false);
			b.setVisible(false);
			MainTimerRunning = true;
			BreakTimerRunning = false;
			paused = false;
			time.setText(String.valueOf("00m:00s"));
	   	    lastButtonPressed = null;
	   	    min = initalmin;
	   	    breakmin = initalbreak;
			MainTimer(setting,db,pm);
		}
	}
	
	

}