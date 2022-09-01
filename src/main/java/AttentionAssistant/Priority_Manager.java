package AttentionAssistant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


public class Priority_Manager {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	LineBorder line = new LineBorder(aa_purple, 2, true);
	JTable table;
	private Notification_System notifSystem;
	private Pomodoro_Timer pomo;
	private static int mouseX;
	private static int mouseY;
	Observer observer;
	int selectedTask;
	int height = 700;
	int width = 550;
	int row;
	Task activeTask;
	boolean isParent;
	DefaultTableModel model;
	int userID;
	DataBase db;
	
	private ArrayList<Task> Task_List;
	
	public Priority_Manager(int userID,DataBase db,boolean isParent) {
		this.Task_List = new ArrayList<Task>();
		this.isParent = isParent;
		this.userID = userID;
		this.db = db;
	}
	
	public Priority_Manager(int userID, DataBase db,Notification_System notifSystem) throws IOException {
		this.Task_List = new ArrayList<Task>();
		this.notifSystem = notifSystem;
		this.pomo = new Pomodoro_Timer();
		this.isParent = false;
		this.userID = userID;
		this.db = db;
	}
	
	public Priority_Manager(int userID,DataBase db) throws IOException {
		this.Task_List = new ArrayList<Task>();
		this.notifSystem = new Notification_System(userID,db);
		this.isParent = false;
		this.userID = userID;
		this.db = db;
	}
	
	public void setPomo(Pomodoro_Timer pomo) {
		this.pomo = pomo;
	}
	
	public Task getActiveTask() {
		return activeTask;
	}
	
	public void observeTask(int userID,Task task,DataBase db,boolean flag,Priority_Manager pm) throws IOException {
		if(flag == false) {
			populateTaskList(userID,db);
			ArrayList<Task> oTasks = observableTasks();
			if(oTasks.size() != 0) {
				task = oTasks.get(0);
			}else {
				firstTaskWindow(userID, db, pm);
				populateTaskList(userID,db);
				oTasks.clear();
				oTasks = observableTasks();
				task = oTasks.get(0);
			}
		}
		//deconstruct old observer*************************************************************************
		
		for(int i=0;i<Task_List.size();i++) {
			if(Task_List.get(i).getTaskID() == task.getTaskID()) {
				Task_List.get(i).setPriority(true);
				Task tempT = Task_List.get(i);
				db.UpdateTask(tempT);
			}else {
				Task_List.get(i).setPriority(false);
				Task tempT = Task_List.get(i);
				db.UpdateTask(tempT);
			}
		}
		task.setPriority(true);
		
		if(isParent == false) {
			//observer = new Observer();
			//observer.monitor(task, db, notifSystem, pomo);
			/** Paul's code to make the observer a separate thread */
            observer = new Observer(task, db, notifSystem, pomo);
            Thread observerThread = new Thread(observer);
            observerThread.start();
            pomo.clickStart();
            Date timestamp = new Date();
            db.AddEvent(userID, timestamp, "started");
            activeTask = task;
    		System.out.println("Task "+task.getTaskName()+" activated");
            pomo.taskrefresh();
		}
	}
	
	public Task getNonObservableTask() {
		Task task = new Task();
		for(int i=0;i<Task_List.size();i++) {
			if(Task_List.get(i).getObservable() == false) {
				task = Task_List.get(i);
			}
		}
		if(task.getTaskName() == null) {
			task.setTaskName("take a breather");
		}
		return task;
	}
	
	public ArrayList<Task> observableTasks() {
		populateTaskList(userID, db);
		ArrayList<Task> list = new ArrayList<Task>();
		for(int i=0;i<Task_List.size();i++) {
			if(Task_List.get(i).getObservable()==true) {
				list.add(Task_List.get(i));
			}
		}
		return list;
	}
	
	public void setInitialActive() {
		ArrayList<Task> list = observableTasks();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getPriority() == true) {
				activeTask = list.get(i);
			}
		}
		
		if(activeTask.getTaskName() == null) {
			activeTask = list.get(0);
		}
	}
	
	public void open_pm(int userID,DataBase db,Priority_Manager pm) {
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run() {
				//setInitialActive();
				//set up frame
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				//sets window width and height
				frame.setPreferredSize(new Dimension(width, height));
				
				//build title panel
				JMenuBar titlePanel = titlePanel(frame,'P');
				titlePanel.setBorder(line);
				
				//build table panel
				JPanel taskPanel = taskPanel(userID,db,frame,pm);
				taskPanel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
				
				//build button panel
				JPanel buttonPanel = buttonPanel(pm,frame);
				buttonPanel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
				
				frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
				frame.getContentPane().add(taskPanel,BorderLayout.CENTER);
				frame.getContentPane().add(buttonPanel,BorderLayout.PAGE_END);
				frame.pack();
				frame.setVisible(true);
				frame.setResizable(true);
				frame.setLocationRelativeTo(null);
			}
			
		});
	}
	
	//******************************************************************************************************************
	private JPanel buttonPanel(Priority_Manager pm,JFrame frame) {
		JPanel panel = new JPanel();
		panel.setBackground(aa_grey);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		//calendar integration button
		//make cancel button, closes task window without adding
		JButton integration = new JButton("  calendar integration  ");
		integration.setBackground(aa_grey);
		integration.setForeground(Color.white);
		integration.setFocusPainted(false);
		integration.setBorder(new LineBorder(Color.black,10,false));
		integration.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		integration.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Calendar_Integration cal = new Calendar_Integration();
        		
        		try {
					cal.importCal(userID, db, model, table, frame, pm);
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        }});

		//close button
		//make cancel button, closes task window without adding
		JButton close = new JButton("  close  ");
		close.setBackground(aa_grey);
		close.setForeground(Color.white);
		close.setFocusPainted(false);
		close.setBorder(new LineBorder(Color.black,10,false));
		close.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		close.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//close window without saving info
        		frame.dispose();
        }});
		
		panel.setBackground(Color.black);
		panel.add(integration);
		panel.add(close);
		return panel;
	}
	
	private void populateTaskList(int userID,DataBase db) {
		for(int i=0; i<db.SelectAllTasks(userID).size();i++) {
			if(db.SelectAllTasks(userID).get(i).getStatus() != TaskStatus.CLOSED) {
				System.out.println(db.SelectAllTasks(userID).get(i));
				Task_List.add(db.SelectAllTasks(userID).get(i));
			}
			
		}
	}
	
	//*****************************************************************************************************************
	/*
	 * create panel that contains task list and buttons to edit/interact with task list
	 */
	private JPanel taskPanel(int userID,DataBase db,JFrame frame,Priority_Manager pm) {
		JPanel panel = new JPanel();
		
		/*
		 * populate task list from database
		 */
		Task_List.removeAll(Task_List);
		populateTaskList(userID,db);
		
		/*
		 * Create JTable to display task
		 */
		model = new DefaultTableModel(Task_List.size(),0);
		//create table model and add columns
		table = new JTable(model);
		model.addColumn("Task");
		model.addColumn("Description");
		model.addColumn("Active");
		model.addColumn("Due Date");
		model.addColumn("Observable");
		model.addColumn("ID");
		
		//set table visuals
        table.setFillsViewportHeight(true);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.getTableHeader().setBackground(aa_grey);
        table.getTableHeader().setForeground(Color.white);
        table.setGridColor(aa_purple);
        table.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
        
        System.out.println(table.getColumnCount());
        
        //iterates through each task in list
        for(int i=0;i<Task_List.size();i++) {
        	//iterate through table columns
        	for(int j=0;j<6;j++) {
        		//set data for each column from the current task
        		if(j==0) {
        			table.setValueAt(Task_List.get(i).getTaskName(),i,j);
        		}else if(j==1){
        			table.setValueAt(Task_List.get(i).getDescription(),i,j);
        		}else if(j==2){
        			//change to show icon*********************************************
        			table.setValueAt(Task_List.get(i).getPriority(),i,j);
        		}else if(j==3) {
        			table.setValueAt(Task_List.get(i).getStringDate(),i,j);
        		}else if(j==4) {
        			//change to show icon*********************************************
        			table.setValueAt(Task_List.get(i).getObservable(),i,j);
        		}else if(j==5) {
        			table.setValueAt(Task_List.get(i).getTaskID(), i, j);
        		}
        	}
        }
        
        table.removeColumn(table.getColumnModel().getColumn(5));
        //set table background color, font color, and border
        table.setBackground(Color.black);
        table.setForeground(Color.white);
		table.setBorder(null);
		
		/*
		 * creates button and calls function for deleting a task upon click
		 */
		Icon delete_icon;
		delete_icon = new ImageIcon("images/minus.png");
		JButton delete_button = new JButton(delete_icon);
		delete_button.setContentAreaFilled(false);
        delete_button.setBorderPainted(false);
        delete_button.setFocusPainted(false);
		delete_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deleteTask(db,model,table);
        }});
		
		/*
		 * creates button and calls function for marking a task as complete upon click
		 */
		Icon check_icon;
		check_icon = new ImageIcon("images/check.png");
		JButton check_button = new JButton(check_icon);
		check_button.setContentAreaFilled(false);
        check_button.setBorderPainted(false);
        check_button.setFocusPainted(false);
		check_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//markComplete();
        		
        		
        		int rowSel = table.getSelectedRow();
        		System.out.println(table.getModel().getValueAt(rowSel,0));
        		int id = Task_List.get(rowSel).getTaskID();
        		System.out.println(Task_List.get(rowSel).getTaskName());
        		model.removeRow(row);
        		for(int i=0;i<Task_List.size();i++) {
        			if(Task_List.get(i).getTaskID()==id) {
        				Task task = Task_List.get(i);
        				task.setStatus(TaskStatus.CLOSED);
        				Task_List.remove(i);
        				db.UpdateTask(task);
        				Date timestamp = new Date();
    	        		System.out.println(timestamp);
    	        		db.AddEvent(userID, timestamp, "complete");
        			}
        		}
        }});
		
		/**
		 * creates button and call function for adding a task upon click
		 */
		Icon add_icon;
		add_icon = new ImageIcon("images/plus.png");
		JButton add_button = new JButton(add_icon);
		add_button.setContentAreaFilled(false);
		add_button.setBorderPainted(false);
		add_button.setFocusPainted(false);
		add_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		boolean isInt = false;
        		addTask(userID,db,model,table,frame,pm,isInt);
        }});
		
		
		/*
		 * Edit task
		 */
		Icon edit_icon;
		edit_icon = new ImageIcon("images/edit.png");
		JButton edit_button = new JButton(edit_icon);
		edit_button.setContentAreaFilled(false);
		edit_button.setBorderPainted(false);
		edit_button.setFocusPainted(false);
		edit_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		editTask(userID,db,model,table,frame,pm);
        }});
		
		
		/*
		 * calendar style view of tasks
		 */
		Icon calendar_icon;
		calendar_icon = new ImageIcon("images/calendar.png");
		JButton calendar_button = new JButton(calendar_icon);
		calendar_button.setContentAreaFilled(false);
		calendar_button.setBorderPainted(false);
		calendar_button.setFocusPainted(false);
		calendar_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//open calendar view of tasks*********************************************
        }});
		
		/*
		 * mark task as active
		 */
		Icon active_icon;
		active_icon = new ImageIcon("images/active.png");
		JButton active_button = new JButton(active_icon);
		active_button.setContentAreaFilled(false);
		active_button.setBorderPainted(false);
		active_button.setFocusPainted(false);
		active_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//make task active
        		row = table.getSelectedRow();
        		int id = (int) table.getModel().getValueAt(row, 5);
        		for(int i=0;i<Task_List.size();i++) {
        			if(Task_List.get(i).getTaskID() == id) {
        				Task task = Task_List.get(i);
        				boolean flag = true;
        				try {
							observeTask(userID, task, db,flag,pm);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        			}
        		}
        		for(int j=0;j<table.getRowCount();j++) {
        			if(j != row) {
        				table.setValueAt(false, j,2);
        			}else {
        				table.setValueAt(true, j,2);
        			}
        		}
        		table.revalidate();
        		//deconstruct old observer******************************************************************
        }});
		
		 
		//puts table in scrollable panel
		JScrollPane tpane = new JScrollPane(table);
		tpane.setBackground(Color.black);
		
		Border empty = new EmptyBorder(0,0,0,0);
		tpane.setBorder(empty);
		//sets dimensions for table panel
		tpane.setPreferredSize(new Dimension(300,400));
		//create button pane
		JPanel bpane = new JPanel();
		bpane.setBackground(Color.black);
		//set layout so buttons display across x-axis
		bpane.setLayout(new BoxLayout(bpane,BoxLayout.X_AXIS));
		
		//add calendar button to bpane
		bpane.add(calendar_button);
		//add delete button to bpane
		bpane.add(delete_button);
		//add check button to bpane
		bpane.add(check_button);
		//add add button to bpane
		bpane.add(add_button);
		//add edit button to bpane
		bpane.add(edit_button);
		//add make active button to bpane
		bpane.add(active_button);
		
		//set layout of panel so components display vertically
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		//add task table panel to panel
		panel.add(tpane);
		//add button panel to panel
		panel.add(bpane);
		panel.setBackground(Color.black);
		
		if(observableTasks().size() == 0) {
			Task fTask = new Task();
			boolean isInt = false;
			taskWindow(userID, fTask, false, db, model, table, frame,pm,isInt);
		}
		
		
		return panel;
	}
	
	//*****************************************************************************************************************
	/*
	 * Delete task
	 */
	private void deleteTask(DataBase db,DefaultTableModel model,JTable table) {
		int row = table.getSelectedRow();
		int id = (int) table.getModel().getValueAt(row, 5);
		//deletes task from database
		db.DeleteTask(id);
		//deletes task from table
		model.removeRow(table.getSelectedRow());
		for(int i=0;i<Task_List.size();i++) {
			if(Task_List.get(i).getTaskID() == id) {
				Task_List.remove(i);
			}
		}
		
		//gets table to display changes
		table.revalidate();
	}
	
	//*****************************************************************************************************************
	/*
	 * Edit task
	 */
	private void editTask(int userID,DataBase db,DefaultTableModel model,JTable table,JFrame frame,Priority_Manager pm) {
		//get task info and pass it to the task window
		row = table.getSelectedRow();
		int id = (int) table.getModel().getValueAt(row, 5);
		for(int i=0;i<Task_List.size();i++) {
			if(Task_List.get(i).getTaskID() == id) {
				Task task = Task_List.get(i);
				boolean isAnEdit = true;
				boolean isInt = false;
				taskWindow(userID,task,isAnEdit,db,model,table,frame,pm,isInt);
			}
		}
	}
	
	/*
	 * Add Task
	 */
	private void addTask(int userID,DataBase db,DefaultTableModel model,JTable table,JFrame frame,Priority_Manager pm,boolean isInt) {
		Task task = new Task();
		boolean isAnEdit = false;
		taskWindow(userID,task,isAnEdit,db,model,table,frame,pm,isInt);
	}
	
	public void calAddTask(int userID,Task task1,DataBase db,DefaultTableModel model,JTable table,JFrame frame,Priority_Manager pm,boolean isInt) {
		Task task = task1;
		boolean isAnEdit = false;
		taskWindow(userID,task,isAnEdit,db,model,table,frame,pm,isInt);
	}
	
	//******************************************************************************************************************
	/*
	 * Task Window
	 * @param Description, Observable, Status
	 * @return task
	 */
	public void taskWindow(int userID,Task task,boolean isAnEdit,DataBase database,DefaultTableModel model,JTable table,JFrame frame,Priority_Manager pm,boolean isInt) {
		//create task window
		JFrame task_window = new JFrame("Add Task");
		//pin to top of screen
		task_window.setAlwaysOnTop(true);
		//set window background to black
		task_window.setBackground(Color.black);
		//remove default title bar
		task_window.setUndecorated(true);
		task_window.setVisible(true);
		
		//makes custom title panel
		JMenuBar title_panel = titlePanel(task_window,'A');
		
		//creates panel for task information form
		JPanel tpane = new JPanel();
		tpane.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, aa_purple));
		JPanel buttons = new JPanel();
		buttons.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, aa_purple));
				
		//sets up grid to line up labels with text areas
		GridLayout grid = new GridLayout(0,2);
		tpane.setLayout(grid);
		
		//sets up grid for check boxes
		GridLayout grid2 = new GridLayout(0,3);
		buttons.setLayout(grid2);
		
		//creates label for task name input
		JLabel n = new JLabel("   Task: ");
		n.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		n.setForeground(aa_purple);
		
		//creates text area for name input
		JTextArea name = new JTextArea(task.getTaskName());
		name.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		name.setBorder(new LineBorder(Color.black,5,false));
		
		//creates label for description input
		JLabel d = new JLabel("   Description: (key words)");
		d.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		d.setForeground(aa_purple);
		
		//creates text area for description input
		JTextArea descrpt = new JTextArea(task.getDescription());
		descrpt.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		descrpt.setBorder(new LineBorder(Color.black,5,false));
		
		//creates label for date input
		JLabel dd = new JLabel("   Due Date: (mm/dd/yyyy)");
		dd.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		dd.setForeground(aa_purple);
		
		//creates text area for date input
		Format f = new SimpleDateFormat("MM/dd/yyyy");
		String stringDate;
		if(isAnEdit == true || isInt == true) {
			stringDate = f.format(task.getDueDate());
		}else {stringDate = "";}
		JTextArea date = new JTextArea(stringDate);
		date.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		date.setBorder(new LineBorder(Color.black,5,false));
		
		//create check box for if task is to be observed
		JCheckBox observe = new JCheckBox("observable");
		observe.setSelected(task.getObservable());
		observe.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		observe.setForeground(aa_purple);
		observe.setContentAreaFilled(false);
		observe.setFocusPainted(false);
		
		//create check box for if task is a priority task
		JCheckBox priority = new JCheckBox("active");
		priority.setSelected(task.getPriority());
		priority.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		priority.setForeground(aa_purple);
		priority.setContentAreaFilled(false);
		priority.setFocusPainted(false);
		
		//create check box for if task is complete
		JCheckBox status = new JCheckBox("complete");
		if(isAnEdit == true || isInt == true) {
			if(task.getStatus() == TaskStatus.CLOSED) {
				status.setSelected(true);
			}else {status.setSelected(false);}
		}
		
		status.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		status.setForeground(aa_purple);
		status.setContentAreaFilled(false);
		status.setFocusPainted(false);
		
		Task new_task = new Task();
		//creates save button, adds task to database and table
		JButton save = new JButton("add");
		save.setBackground(aa_grey);
		save.setForeground(Color.white);
		save.setFocusPainted(false);
		save.setBorder(new LineBorder(Color.black, 3, true));
		save.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//create task from entered info
        		String n = name.getText();
        		new_task.setTaskName(n);
        		String d = descrpt.getText();
        		new_task.setDescription(d);
        		String dd = date.getText();
        		try {
					Date due = new SimpleDateFormat("MM/dd/yyyy").parse(dd);
					new_task.setDueDate(due);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
        		if(observe.getSelectedObjects() != null) {
        			new_task.setObservable(true);
        		}else {new_task.setObservable(false);}
        		if(priority.getSelectedObjects()!=null) {
        			new_task.setPriority(true);
        		}else {new_task.setPriority(false);}
        		if(status.getSelectedObjects()!=null) {
        			new_task.setStatus(TaskStatus.CLOSED);
        		}else {new_task.setStatus(TaskStatus.OPEN);}
        		
        		if(isAnEdit == true) {
        			new_task.setTaskID(task.getTaskID());
        			database.UpdateTask(new_task);
        			if(new_task.getStatus() == TaskStatus.CLOSED) {
        				Date timestamp = new Date();
                		System.out.println(timestamp);
                		database.AddEvent(userID, timestamp, "complete");
        			}
        			for(int i=0;i<Task_List.size();i++) {
        				if(Task_List.get(i).getTaskID() == new_task.getTaskID()) {
        					Task_List.get(i).setTaskName(new_task.getTaskName());
        					Task_List.get(i).setDescription(new_task.getDescription());
        					Task_List.get(i).setPriority(new_task.getPriority());
        					Task_List.get(i).setDueDate(new_task.getDueDate());
        					Task_List.get(i).setObservable(new_task.getObservable());
        				}
        			}
        			
        			table.setValueAt(new_task.getTaskName(), row, 0);
        			table.setValueAt(new_task.getDescription(), row, 1);
        			table.setValueAt(new_task.getPriority(), row, 2);
        			table.setValueAt(new_task.getStringDate(), row, 3);
        			table.setValueAt(new_task.getObservable(), row, 4);
        		}else{
        			//adds task to database
	        		database.AddTask(new_task,userID);
	        		//creates object v populated with the new tasks details
	        		Vector<Object> v = new Vector<Object>();
	        		v.add(new_task.getTaskName());
	        		v.add(new_task.getDescription());
	        		v.add(new_task.getPriority());
	        		v.add(new_task.getStringDate());
	        		v.add(new_task.getObservable());
	        		//add row and populate it with object v
	        		model.addRow(v);
	        		
	        		Date timestamp = new Date();
	        		System.out.println(timestamp);
	        		database.AddEvent(userID, timestamp, "add");
        		}
        		if(new_task.getPriority() == true) {
        			//observeTask(userID, new_task, database, true);
					activeTask = new_task;
					//pomo.labelRefresh();
        		}
        		
        		//gets table to display changes
        		table.revalidate();
        		frame.dispose();
        		open_pm(userID,database,pm);
        		task_window.dispose();
        		
        }});
		
		//make cancel button, closes task window without adding
		JButton cancel = new JButton("cancel");
		cancel.setBackground(aa_grey);
		cancel.setForeground(Color.white);
		cancel.setFocusPainted(false);
		cancel.setBorder(new LineBorder(Color.black,3,true));
		cancel.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//close window without saving info
        		task_window.dispose();
        }});
		
		//create blank button for spacing (visual only)
		JButton blank = new JButton();
		blank.setContentAreaFilled(false);
		blank.setFocusPainted(false);
		blank.setBorderPainted(false);
		
		//add components to task information panel
		tpane.add(n);
		tpane.add(name);
		tpane.add(d);
		tpane.add(descrpt);
		tpane.add(dd);
		tpane.add(date);
		//add components to button panel
		buttons.add(observe);
		buttons.add(priority);
		buttons.add(status);
		buttons.add(blank);
		buttons.add(save);
		buttons.add(cancel);
		
		//sets location and dimensions of task window
		int x = (int) ((screen.getWidth() - task_window.getWidth()) /2);
		int y = (int) ((screen.getHeight() - task_window.getHeight()) /2);
		task_window.setLocation(x, y);
		
		//sets background of panels
		tpane.setBackground(Color.black);
		buttons.setBackground(Color.black);
		buttons.setForeground(Color.white);
		
		//add title panel, task information panel, and button panel to window
		task_window.add(title_panel, BorderLayout.PAGE_START);
		task_window.add(tpane, BorderLayout.CENTER);
		task_window.add(buttons, BorderLayout.PAGE_END);
		task_window.pack();
	}
	
	
	/*
	 * Task Window
	 * @param Description, Observable, Status
	 * @return task
	 */
	public Task firstTaskWindow(int userID,DataBase database,Priority_Manager pm) {
		Task task = new Task();
		//create task window
		JFrame task_window = new JFrame("Add an Observable Task");
		//pin to top of screen
		task_window.setAlwaysOnTop(true);
		//set window background to black
		task_window.setBackground(Color.black);
		//remove default title bar
		task_window.setUndecorated(true);
		task_window.setVisible(true);
		
		//makes custom title panel
		JMenuBar title_panel = titlePanel(task_window,'O');
		
		//creates panel for task information form
		JPanel tpane = new JPanel();
		tpane.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, aa_purple));
		JPanel buttons = new JPanel();
		buttons.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, aa_purple));
				
		//sets up grid to line up labels with text areas
		GridLayout grid = new GridLayout(0,2);
		tpane.setLayout(grid);
		
		//sets up grid for check boxes
		GridLayout grid2 = new GridLayout(0,3);
		buttons.setLayout(grid2);
		
		//creates label for task name input
		JLabel n = new JLabel("   Task: ");
		n.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		n.setForeground(aa_purple);
		
		//creates text area for name input
		JTextArea name = new JTextArea(task.getTaskName());
		name.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		name.setBorder(new LineBorder(Color.black,5,false));
		
		//creates label for description input
		JLabel d = new JLabel("   Descrition: (key words, separated by commas (,))");
		d.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		d.setForeground(aa_purple);
		
		//creates text area for description input
		JTextArea descrpt = new JTextArea(task.getDescription());
		descrpt.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		descrpt.setBorder(new LineBorder(Color.black,5,false));
		
		//creates label for date input
		JLabel dd = new JLabel("   Due Date: (mm/dd/yyyy)");
		dd.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		dd.setForeground(aa_purple);
		
		new SimpleDateFormat("MM/dd/yyyy");
		String stringDate = "";
		JTextArea date = new JTextArea(stringDate);
		date.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		date.setBorder(new LineBorder(Color.black,5,false));
		
		//create check box for if task is to be observed
		JCheckBox observe = new JCheckBox("observable");
		observe.setSelected(true);
		observe.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		observe.setForeground(aa_purple);
		observe.setContentAreaFilled(false);
		observe.setFocusPainted(false);
		
		//create check box for if task is a priority task
		JCheckBox priority = new JCheckBox("active");
		priority.setSelected(task.getPriority());
		priority.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		priority.setForeground(aa_purple);
		priority.setContentAreaFilled(false);
		priority.setFocusPainted(false);
		
		//create check box for if task is complete
		JCheckBox status = new JCheckBox("complete");
		status.setSelected(false);
		
		status.setFont(new Font("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		status.setForeground(aa_purple);
		status.setContentAreaFilled(false);
		status.setFocusPainted(false);
		
		Task new_task = new Task();
		//creates save button, adds task to database and table
		JButton save = new JButton("add");
		save.setBackground(aa_grey);
		save.setForeground(Color.white);
		save.setFocusPainted(false);
		save.setBorder(new LineBorder(Color.black, 3, true));
		save.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//create task from entered info
        		String n = name.getText();
        		new_task.setTaskName(n);
        		String d = descrpt.getText();
        		new_task.setDescription(d);
        		String dd = date.getText();
        		try {
					Date due = new SimpleDateFormat("MM/dd/yyyy").parse(dd);
					new_task.setDueDate(due);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		if(observe.getSelectedObjects() != null) {
        			new_task.setObservable(true);
        		}else {new_task.setObservable(false);}
        		if(priority.getSelectedObjects()!=null) {
        			new_task.setPriority(true);
        		}else {new_task.setPriority(false);}
        		if(status.getSelectedObjects()!=null) {
        			new_task.setStatus(TaskStatus.CLOSED);
        		}else {new_task.setStatus(TaskStatus.OPEN);}
        		
    			//adds task to database
        		database.AddTask(new_task,userID);
        		Date timestamp = new Date();
        		System.out.println(timestamp);
        		database.AddEvent(userID, timestamp, "add");
        		task_window.dispose();
        		setInitialActive();
		        Monitoring_Bar mb = new Monitoring_Bar();
				mb.monitorBar(userID, db, pomo, pm);
				pomo.monitorbar(mb);
				pomo.clickStart();
				pomo.clickPause();
        }});
		
		//make cancel button, closes task window without adding
		JButton cancel = new JButton("cancel");
		cancel.setBackground(aa_grey);
		cancel.setForeground(Color.white);
		cancel.setFocusPainted(false);
		cancel.setBorder(new LineBorder(Color.black,3,true));
		cancel.setFont(new Font ("TimesRoman", Font.BOLD | Font.PLAIN, 16));
		cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//close window without saving info
        		task_window.dispose();
        }});
		
		//create blank button for spacing (visual only)
		JButton blank = new JButton();
		blank.setContentAreaFilled(false);
		blank.setFocusPainted(false);
		blank.setBorderPainted(false);
		
		//add components to task information panel
		tpane.add(n);
		tpane.add(name);
		tpane.add(d);
		tpane.add(descrpt);
		tpane.add(dd);
		tpane.add(date);
		//add components to button panel
		buttons.add(observe);
		buttons.add(priority);
		buttons.add(status);
		buttons.add(blank);
		buttons.add(save);
		buttons.add(cancel);
		
		//sets location and dimensions of task window
		int x = (int) ((screen.getWidth() - task_window.getWidth()) /2);
		int y = (int) ((screen.getHeight() - task_window.getHeight()) /2);
		task_window.setLocation(x, y);
		
		//sets background of panels
		tpane.setBackground(Color.black);
		buttons.setBackground(Color.black);
		buttons.setForeground(Color.white);
		
		//add title panel, task information panel, and button panel to window
		task_window.add(title_panel, BorderLayout.PAGE_START);
		task_window.add(tpane, BorderLayout.CENTER);
		task_window.add(buttons, BorderLayout.PAGE_END);
		task_window.pack();
		return task;
	}
	
	
	
	//****************************************************************************************************************
	/*creates title panel with close and guide buttons
	 * 
	 */
	private JMenuBar titlePanel(JFrame frame,char key) {
		String t = "";
		switch (key) {
        case 'P':  t = "Priority Manager";
                 break;
        case 'A':  t = "Add Task                                       ";
                 break;
        case 'O':  t = "Add Observable Task";
                 break;
		}
		
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

		JLabel title = new JLabel(t);
		title.setForeground(Color.white);
		title.setBounds(0,0,200,200);
		title.setFont(new Font("Dosis SemiBold", Font.BOLD, 20));
		
		/*
		 * create icons to use as buttons for title bar
		 */
		BufferedImage ci = null;
		BufferedImage gi = null;
		try {
			ci = ImageIO.read(new File("images/exit_circle.png"));
			gi = ImageIO.read(new File("images/guide.png"));
			ImageIO.read(new File("images/AA_exit.png"));
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
        		frame.dispose();
        	
        }});
		
		Image g_img = gi.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		Icon guideIcon = new ImageIcon(g_img);
		
		JButton guide = new JButton(guideIcon);
		guide.setBorderPainted(false);
		guide.setContentAreaFilled(false);
		guide.setFocusPainted(false);guide.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//opens guide
        		Guide guide = new Guide();
				guide.open_Guide("Priority Manager");
        }});
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(250, 0)));
		title_panel.add(guide);
		title_panel.add(close_window);
		
		//returns panel
		return title_panel;
	}
	
	/**
	 * Get Height
	 * @return int
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Set Height
	 * @param int
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Get Width
	 * @return int
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Set Width
	 * @param int
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Get Task_List
	 * @return Task_List
	 */
	public ArrayList<Task> getTask_List() {
		return this.Task_List;
	}
	
	/**
	 * Set Task_List
	 * @param Task_List
	 */
	public void setTask_List(ArrayList<Task> TaskList) {
		this.Task_List = TaskList;
	}
	
	//***************************************************************************************************************
	//FUNCTIONALITY TO ADD
	//***************************************************************************************************************
	
	/**
	 * Export Task List to Parent Portal
	 * @param
	 */
	public void export(){
		//Code to Implement
	}
	
	/**
	 * function to import calendar
	 */
	public void importCalendar() {
		
	}
	
	/*
	 * function that generates/displays user tasks in calendar view 
	 */
	public void userCalendar() {
		
	}
}