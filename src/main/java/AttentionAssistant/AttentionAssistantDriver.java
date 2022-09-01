package AttentionAssistant;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;

public class AttentionAssistantDriver {
	static Color aa_grey = new Color(51,51,51);
	static Color aa_purple = new Color(137,31,191);
	static JLabel appLabel = new JLabel("The Attention Assistant");
	private static int mouseX;
	private static int mouseY;
	public static String userinput = "";
	
	static JFrame frame;
	static JPanel WelcomePanel;
	static JPanel LoginPanel;
	static JPanel RegisterPanel;
	static JPanel ForgotPanel;
	static JPanel ChangePassPanel;
	static JPanel cardPane;
    static CardLayout card;
    
	static Priority_Manager pm;
	static LineBorder line = new LineBorder(aa_purple, 2, true);
	public static void main(String[] args) throws Exception {
		run_login();
		/**
		 * TEST CODE
		 * DELETE BEFORE PRODUCTION
		 */
		//Observer testObserver = new Observer();
		//Task testTask = new Task();
		
		//MouseTracker testMouse = new MouseTracker();
		//testMouse.startTracking();
		
		//testObserver.keywordsGenerator(testTask);
		
		//KeyBoardTracker testKeyboard = new KeyBoardTracker();
		//testKeyboard.startTracking(null);
	
	}
		//this is a hashing function to hash the user/parents password and their security questions  
		public static String hash (String password) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				
				md.update(password.getBytes());
				
				byte[] outputbytearray = md.digest();
				
				StringBuilder sb = new StringBuilder();
				
				for(byte b :outputbytearray) {
					sb.append(String.format("%02x", b));
				}
				return sb.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return "";
			
		}
	
		//this is the top of the panel - decorated and will allow you to move the panel around
		private static JMenuBar titlePanel(JFrame frame) {
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

			JLabel title = new JLabel("The Attention Assistant");
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
	        		frame.dispose();
	        	
	        }});
			
			Image g_img = gi.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			Icon guideIcon = new ImageIcon(g_img);
			
			JButton guide = new JButton(guideIcon);
			guide.setBorderPainted(false);
			guide.setContentAreaFilled(false);
			guide.setFocusPainted(false);
			
			title_panel.add(title);
			title_panel.add(Box.createRigidArea(new Dimension(400, 0)));
			//title_panel.add(guide);
			title_panel.add(close_window);
			
			//returns panel
			return title_panel;
			
		}
	
		
		//Login screen Card Layout
		private static JPanel loginpage(CardLayout card, JFrame frame) {
			JPanel panel = new JPanel();
			panel.setBackground(aa_grey);
			panel.setLayout(null);

			
			BufferedImage cat2 = null;
			 BufferedImage cat3 = null;
			    try {
			    	cat2 = ImageIO.read(new File("avatarSelection/avatar_cat2.png"));
			    	cat3 = ImageIO.read(new File("avatarSelection/avatar_cat3.png"));
				
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			    
			    JLabel catpic = new JLabel(new ImageIcon(cat2));
			    catpic.setBounds(500, 50, 138, 150);
			    panel.add(catpic);
			    
			    JLabel cat2pic = new JLabel(new ImageIcon(cat3));
			    cat2pic.setBounds(60, 50, 132, 150);
			    panel.add(cat2pic);
			
			
			JLabel loglbl = new JLabel("Login");
			JLabel userlbl = new JLabel("Username: ");
			JLabel passlbl = new JLabel("Password: ");
			JLabel newlbl = new JLabel("New Here? ");
			
			JTextField user = new JTextField();
			JPasswordField pass = new JPasswordField();
			
			JButton loginbut=new JButton("Sign In");
			JButton forgotpassbut=new JButton("Forgot Password?");
			JButton signbut= new JButton("Sign Up");
			JButton backButton = new JButton("Back");
			
			
			loglbl.setBounds(300, 80, 280, 100);
			loglbl.setForeground(aa_purple);
			loglbl.setFont(new Font("Dosis SemiBold",Font.BOLD,40));
			panel.add(loglbl);
			
			
			
			 userlbl.setBounds(170, 180, 200, 100); 
			 userlbl.setForeground(new Color(255, 255, 255));
			 userlbl.setFont(new Font("Dosis SemiBold",Font.BOLD,18));
			 panel.add(userlbl);
			 
			 user.setBounds(290, 220, 180, 23);
			 user.setBackground(aa_purple);
			 panel.add(user);
	        

			 passlbl.setBounds(173, 230, 200, 100); 
			 passlbl.setForeground(new Color(255, 255, 255));
			 passlbl.setFont(new Font("Dosis SemiBold",Font.BOLD,18));
			 panel.add(passlbl);
			 
			 pass.setBounds(290, 268, 180, 23);
			 pass.setBackground(aa_purple);
			 panel.add(pass);
			 
			 loginbut.setBounds(300, 320, 97, 35);
			 loginbut.setHorizontalTextPosition(SwingConstants.CENTER);
			 loginbut.setVerticalTextPosition(SwingConstants.CENTER);
			 loginbut.setFont(new Font("Dosis SemiBold", Font.BOLD, 17));
			 loginbut.setBorderPainted(false);
			 loginbut.setBackground(aa_purple);
			 loginbut.setForeground(Color.WHITE);
			 loginbut.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		
		        		DataBase db = new DataBase();	
		        		String pwds = new String(pass.getPassword());
		        		String pwd = hash(pwds);
		        		String usr = new String(user.getText());
		        	
		        		if(pwd.isEmpty() == true || usr.isEmpty() == true) {
		        			JFrame errorframe = new JFrame();
	        				JOptionPane.showMessageDialog(errorframe, "Please input Password or Username");
		        		}
		        		else {
		        			db.DatabaseSetUp();
			        		User_Account UserAccount = db.SearchUser_Account(usr, pwd);
			        		Parent_Account ParentAccount = db.SearchParent_Account(usr,pwd);
			        		//this is looking for an acc with this exact password and username, so i have to check the pass and user
			        
			        		
			        		//TODO dispose frame when successful login
			        		if(UserAccount.getPassword().equals(pwd) == true && UserAccount.getUsername().equals(usr) == true) {
			        			JFrame success = new JFrame();
		        				JOptionPane.showMessageDialog(success, "Sucessfully Logged into Child account!! Logging in now...");
		        				User_Account UserAccount2 = db.UsernameUser_Account(usr);
		        				int userid = UserAccount2.getUserID();
		        			
		        				Settings sett = new Settings(db,userid);
		        				
		        				frame.dispose();
		        				success.dispose();
		        				childPortal(userid,sett,db);
			        		}
			        		else if (ParentAccount.getPassword().equals(pwd) == true && ParentAccount.getUsername().equals(usr) == true) {
			        			JFrame success = new JFrame();
		        				JOptionPane.showMessageDialog(success, "Sucessfully Logged into Parent account!! Logging in now...");
		        				Parent_Account ParentAccount2 = db.UsernameParent_Account(usr);
		        				int parentid = ParentAccount2.getParentID();
		        				frame.dispose();
		        				success.dispose();
		        				parentPortal(parentid,db);
			        		}
			        		else {
			        			JFrame errorframe = new JFrame();
		        				JOptionPane.showMessageDialog(errorframe, "Incorrect Username/Password! Please try again!");
			        		}
		        		}
		        	
		        	
		        }});
			 panel.add(loginbut);
			 //TODO make this reset pass later 
			 forgotpassbut.setBounds(260, 390, 200, 35);
			 forgotpassbut.setHorizontalTextPosition(SwingConstants.CENTER);
			 forgotpassbut.setVerticalTextPosition(SwingConstants.CENTER);
			 forgotpassbut.setFont(new Font("Dosis SemiBold", Font.BOLD, 17));
			 forgotpassbut.setBorderPainted(false);
			 forgotpassbut.setBackground(Color.WHITE);
			 forgotpassbut.setForeground(aa_purple);
			 forgotpassbut.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		card.last(cardPane);
		        		
		        		//switch to other card w forgotpass
		        	
		        }});
			 panel.add(forgotpassbut);
			 
		     newlbl.setBounds(230, 430, 200, 100); 
		     newlbl.setForeground(new Color(255, 255, 255));
		     newlbl.setFont(new Font("Dosis SemiBold",Font.BOLD,15));
		     panel.add(newlbl);
			 
			 signbut.setBounds(315, 463, 95, 35);
			 signbut.setHorizontalTextPosition(SwingConstants.CENTER);
			 signbut.setVerticalTextPosition(SwingConstants.CENTER);
			 signbut.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
			 signbut.setBorderPainted(false);
			 signbut.setBackground(aa_purple);
			 signbut.setForeground(Color.white);
			 signbut.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        	card.next(cardPane);
		        }});
			 panel.add(signbut);

			 
			 backButton.setBounds(50, 500, 78, 40);
		     backButton.setBorderPainted(false);
		     backButton.setBackground(Color.WHITE);
		     backButton.setForeground(new Color(159,89,155));
		     backButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
		     backButton.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        	card.previous(cardPane);
		        }});
		     panel.add(backButton);

			return panel;
		}
	
		//this is the reset password card layout
		private static JPanel resetPass(CardLayout card, JFrame frame) {
			JPanel panel = new JPanel();
			panel.setBackground(aa_grey);
			panel.setLayout(null);
		

			  String[] optionsToChoose = {"The name of your first pet?", "The name of your best friend?", "Your favorite food?", "Your favorite school subject?"};

			  String[] optionsToChoose2 = {"The street you live on?", "Favorite video game?", "Your favorite teachers name?", "Your favorite animal?"};



			JLabel username = new JLabel("Username: ");
			JLabel sqlabel1 = new JLabel("Security Question 1: ");
			JLabel ans1 = new JLabel("Answer: ");
			JLabel sqlabel2 = new JLabel("Security Question 2: ");
			JLabel ans2 = new JLabel("Answer: ");
			
		
			JComboBox<String> security1 = new JComboBox<>(optionsToChoose);
			JComboBox<String> security2 = new JComboBox<>(optionsToChoose2);
			
			JTextField usernametext = new JTextField();
			JLabel register = new JLabel("Forgot your password?");
			JPasswordField answer2text = new JPasswordField();
			JPasswordField answer2text2 = new JPasswordField();
			
			JButton Child = new JButton("Child");
		    JButton Parent = new JButton("Parent");
			JButton accButton = new JButton("Change Password");
		    JButton backButton = new JButton("Back");
		    JButton loginButton = new JButton("Forgot Your Questions?");
		    
		    BufferedImage cat3 = null;
		    try {
				
		    	cat3 = ImageIO.read(new File("avatarSelection/avatar_cat3.png"));
			
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		    
		    JLabel dinopic = new JLabel(new ImageIcon(cat3));
		    dinopic.setBounds(100, 182, 127, 150);
		    panel.add(dinopic);
		
			register.setBounds(200, 0, 360, 100);
			register.setForeground(aa_purple);
			register.setFont(new Font("Dosis SemiBold",Font.BOLD,30));
			register.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(register);

			
			Child.setBounds(397, 80, 78, 40);
			Child.setBorderPainted(false);
			Child.setBackground(aa_purple);
			Child.setForeground(Color.WHITE);
			Child.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
			Child.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//declares the string depending on who is registering
	        		userinput = "Child";
	        		//changes the color of button upon click
	        		Child.setBackground(Color.WHITE);
	        		Child.setForeground(aa_purple);
	        		 Parent.setBackground(aa_purple);
	      	        Parent.setForeground(Color.WHITE);
	      	        
	        }});
	        panel.add(Child);
	        
	        Parent.setBounds(525, 80, 95, 40);
	        Parent.setBorderPainted(false);
	        Parent.setBackground(aa_purple);
	        Parent.setForeground(Color.WHITE);
	        Parent.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
	        Parent.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//declares the string depending on who is registering
	        		userinput = "Parent";
	        		//changes the color of button upon click
	        		Parent.setBackground(Color.WHITE);
	        		 Parent.setForeground(aa_purple);
	        		 Child.setBackground(aa_purple);
	     			Child.setForeground(Color.WHITE);
	        		
	        }});
	        panel.add(Parent);
		        
	       
			username.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			username.setBounds(310, 152, 270, 28);
			username.setForeground(aa_purple);
			username.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(username);
	        
			usernametext.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			usernametext.setBounds(398, 152, 270, 28);
		    usernametext.setForeground(Color.BLACK);
	        usernametext.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(usernametext);
	        
	        sqlabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        sqlabel1.setBounds(250, 202, 270, 28);
	        sqlabel1.setForeground(aa_purple);
	        sqlabel1.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(sqlabel1);
	        
	        
	        security1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        security1.setBounds(398, 202, 270, 28);
	        security1.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        security1.setForeground(Color.BLACK);
	        panel.add(security1);
	        
	        ans1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        ans1.setBounds(340, 252, 270, 28);
	        ans1.setForeground(aa_purple);
	        ans1.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(ans1);
	        
	        answer2text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        answer2text.setBounds(398, 252, 270, 28);
	        answer2text.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        answer2text.setForeground(Color.BLACK);
	        panel.add(answer2text);
	      
	       
	        sqlabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        sqlabel2.setBounds(250, 302, 270, 28);
	        sqlabel2.setForeground(aa_purple);
	        sqlabel2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(sqlabel2);
	        
	        security2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        security2.setBounds(398, 302, 270, 28);
	        security2.setForeground(Color.BLACK);
	        security2.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(security2);
	        
	        
	        ans2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        ans2.setBounds(340, 352, 270, 28);
	        ans2.setForeground(aa_purple);
	        ans2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(ans2);
	        
	        answer2text2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        answer2text2.setBounds(398, 352, 270, 28);
	        answer2text2.setForeground(Color.BLACK);
	        answer2text2.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(answer2text2);
	        
	        
	        accButton.setBounds(397, 425, 200, 37);
	        accButton.setBorderPainted(false);
	        accButton.setBackground(aa_purple);
	        accButton.setForeground(Color.WHITE);
	        accButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        accButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		 int SQ1int = security1.getSelectedIndex();
	        		int SQ2int = security2.getSelectedIndex();
	  		       
	  		    DataBase db = new DataBase();	
        		String usr = new String(usernametext.getText());
        		String anssqinput1 = new String(answer2text.getPassword());
        		String anssqinput2 = new String(answer2text2.getPassword());

        		String ansinput1 = hash(anssqinput1);
        		String ansinput2 = hash(anssqinput2);
        		 
        		db.DatabaseSetUp();
    			
        		User_Account UserAccount = db.UsernameUser_Account(usr);
        		User_Account addChildUser = new User_Account();
        		Parent_Account ParentAccount = db.UsernameParent_Account(usr);
        		Parent_Account addParentUser = new Parent_Account();
        		
        		
        		
        		
        		if(usr.isEmpty() == true || ansinput1.isEmpty() == true ||  ansinput2.isEmpty() == true) {
        			JFrame errorframe = new JFrame();
    				JOptionPane.showMessageDialog(errorframe, "One of your fields are blank, please fill them in.");
        		}
        		else if(UserAccount.getUsername().isEmpty() == true  &&  ParentAccount.getUsername().isEmpty() == true ) {
        			JFrame errorframe = new JFrame();
    				JOptionPane.showMessageDialog(errorframe, "Username does not exist. Please Try Again.");
        		}
        		else {

        			if(userinput == "Child" ) {
        			
    	        		int userID = UserAccount.getUserID();
    	        		String Fn = UserAccount.getName();
    	        		String pw = UserAccount.getPassword();
    	        		int sq1 = UserAccount.getSQ_Key();
    	        		int sq2 = UserAccount.getSQ_Key2();
    	        		String sq_ans1 = UserAccount.getSQ_Answer();
    	        		String sq_ans2 = UserAccount.getSQ_Answer2();
    	        		
    	        		
    	        		if((sq1 != SQ1int && sq_ans1.equals(ansinput1) == false) || (sq2 != SQ2int && sq_ans2.equals(ansinput2) == false)) {
    	        			JFrame errorframe = new JFrame();
    	    				JOptionPane.showMessageDialog(errorframe, "Security Questions are not correct. Please Try Again.");
    	        		}
    	        		else {
    	        			addChildUser.setName(Fn);
            				addChildUser.setUserID(userID);
            				addChildUser.setUsername(usr);
            				addChildUser.setPassword(pw);
            				addChildUser.setSQ_Key(sq1);		
            				addChildUser.setSQ_Key2(sq2); 
            				//TODO add hash for users ans
            				addChildUser.setSQ_Answer(sq_ans1);
            				addChildUser.setSQ_Answer2(sq_ans2); 
            				
            				db.UpdateUser_Account(addChildUser);
            				ChangePasspage(card,usr,userinput);
            				
    	        		}
    	        		
        				
        			}
        			else if(userinput == "Parent"){
        		
    	        		int userID = ParentAccount.getParentID();
    	        		String pw = ParentAccount.getPassword();
    	        		int sq1 = ParentAccount.getSQ_Key();
    	        		int sq2 = ParentAccount.getSQ_Key2();
    	        		String sq_ans1 = ParentAccount.getSQ_Answer();
    	        		String sq_ans2 = ParentAccount.getSQ_Answer2();
    	        		
    	        		
    	        		if((sq1 != SQ1int && sq_ans1.equals(ansinput1) == false) || (sq2 != SQ2int && sq_ans2.equals(ansinput2) == false)) {
    	        			JFrame errorframe = new JFrame();
    	    				JOptionPane.showMessageDialog(errorframe, "Security Questions are not correct. Please Try Again.");
    	        		}
    	        		else {
    	        			addParentUser.setParentID(userID);
        	        		addParentUser.setUsername(usr);
        	        		addParentUser.setPassword(pw);
        	        		addParentUser.setSQ_Key(sq1);		
        	        		addParentUser.setSQ_Key2(sq2); 
            				//TODO add hash for users ans
        	        		addParentUser.setSQ_Answer(sq_ans1);
        	        		addParentUser.setSQ_Answer2(sq_ans2); 
            				
            				db.UpdateParent_Account(addParentUser);
            				ChangePasspage(card,usr,userinput);
            				
    	        		}
    	        		
        				
        			}
        			else {
        			JOptionPane.showMessageDialog(null, "You must select a child or parent account to change password!","Confirmation", JOptionPane.WARNING_MESSAGE);
        			}
        			
        			
        		}
	  		      
	        	
	        }});
	        panel.add(accButton);
	        
	        loginButton.setBounds(450, 490, 215, 40);
	        loginButton.setBorderPainted(false);
	        loginButton.setBackground(aa_purple);
	        loginButton.setForeground(Color.WHITE);
	        loginButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
	        loginButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		ForgotSQpage(card);
	        }});
	        panel.add(loginButton);
	       
	        
	        backButton.setBounds(350, 490, 78, 40);
	        backButton.setBorderPainted(false);
	        backButton.setBackground(Color.WHITE);
	        backButton.setForeground(aa_purple);
	        backButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
	        backButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
		        card.previous(cardPane);
	        	card.previous(cardPane);
	        }});
	        panel.add(backButton);
	        
	        
	    
	        //TODO add a login button by the back button
			return panel;
		
		}
		
		
		//this it the panel that will popup if the user inputs their correct security questions so they are able to change their password
		private static JFrame ChangePasspage(CardLayout card, String user,String userinput) {
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			JMenuBar titlePanel = titlePanel(frame);
			frame.setUndecorated(true);
			frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
	        frame.setPreferredSize(new Dimension(700, 600)); 
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			panel.setBackground(aa_grey);
			panel.setLayout(null);
			panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
		

			JLabel register = new JLabel("Success! Please Enter a New Password!");
		//	JLabel oldpasslbl = new JLabel("Old Password: ");
			JLabel ans1 = new JLabel("New Password: ");
			JLabel reenterlabel2 = new JLabel("Reenter New Password: ");
			
		
		//	JPasswordField oldpass = new JPasswordField();
			JPasswordField newpass = new JPasswordField();
			JPasswordField reenterpass = new JPasswordField();
		
			JButton accButton = new JButton("Change Password");
		    JButton backButton = new JButton("Back");
		    
		    BufferedImage cat3 = null;
		    try {
				
		    	cat3 = ImageIO.read(new File("avatarSelection/avatar_cat3.png"));
			
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		    
		    JLabel dinopic = new JLabel(new ImageIcon(cat3));
		    dinopic.setBounds(100, 182, 127, 150);
		    panel.add(dinopic);
		
			register.setBounds(100, 0, 550, 100);
			register.setForeground(aa_purple);
			register.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
			register.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(register);

	        ans1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        ans1.setBounds(250, 252, 270, 28);
	        ans1.setForeground(aa_purple);
	        ans1.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(ans1);
	        
	        newpass.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        newpass.setBounds(398, 252, 270, 28);
	        newpass.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        newpass.setForeground(Color.BLACK);
	        panel.add(newpass);
	      
	       
	        reenterlabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        reenterlabel2.setBounds(215, 302, 270, 28);
	        reenterlabel2.setForeground(aa_purple);
	        reenterlabel2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(reenterlabel2);
	        
	        reenterpass.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        reenterpass.setBounds(398, 302, 270, 28);
	        reenterpass.setForeground(Color.BLACK);
	        reenterpass.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(reenterpass);
	        
	        	        
	        
	        accButton.setBounds(397, 425, 200, 37);
	        accButton.setBorderPainted(false);
	        accButton.setBackground(aa_purple);
	        accButton.setForeground(Color.WHITE);
	        accButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        accButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	  		    DataBase db = new DataBase();	
        		String usr = new String(user);
        	
        		String newpassStr = new String(newpass.getPassword());
        		String reenterpassStr = new String(reenterpass.getPassword());
        		String newpassString = hash(newpassStr);
        		String reenterpassString = hash(reenterpassStr);
        		
        		
        		if(newpassString.equals(reenterpassString) == false) {
        			JFrame errorframe = new JFrame();
    				JOptionPane.showMessageDialog(errorframe, "New Passwords do no match! Please try again!.");
        		}
        		else {
        			if(usr.isEmpty() == true || newpassString.isEmpty() == true ||  reenterpassString.isEmpty() == true) {
            			JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "One of your fields are blank, please fill them in.");
            		}
            		else {

            			if(userinput == "Child" ) {
            				db.DatabaseSetUp();
                			
        	        		User_Account UserAccount = db.UsernameUser_Account(usr);
        	        		User_Account addChildUser = new User_Account();
        	        		int userID = UserAccount.getUserID();
        	        		String Fn = UserAccount.getName();
        	        		String pass = UserAccount.getPassword();
        	        		int sq_key1 = UserAccount.getSQ_Key();
        	        		int sq_key2 = UserAccount.getSQ_Key2();
        	        		String sq_ans1 = UserAccount.getSQ_Answer();
        	        		String sq_ans2 = UserAccount.getSQ_Answer2();
        	        		
        	        		addChildUser.setName(Fn);
            				addChildUser.setUserID(userID);
            				addChildUser.setUsername(usr);
            				addChildUser.setPassword(newpassString);
            				addChildUser.setSQ_Key(sq_key1);
            				addChildUser.setSQ_Key2(sq_key2);
            				addChildUser.setSQ_Answer(sq_ans1);
            				addChildUser.setSQ_Answer2(sq_ans2);
            				
            				db.UpdateUser_Account(addChildUser);
            				
            				  Object[] options = {"OK"};
          				    int n = JOptionPane.showOptionDialog(frame,
          				                   "Successfully Changed Password! You may now login! ","Success",
          				                 JOptionPane.YES_NO_CANCEL_OPTION,
        					             JOptionPane.DEFAULT_OPTION,
          				                   null,
          				                   options,
          				                   options[0]);
          				    if(n == 0) {
          				    	frame.dispose();
          				    	card.next(cardPane);
          				    	card.next(cardPane);
          				    }
            			}
            			else if(userinput == "Parent"){
            				db.DatabaseSetUp();
                			
        	        		Parent_Account ParentAccount = db.UsernameParent_Account(usr);
        	        		Parent_Account addParentUser = new Parent_Account();
        	        		int userID = ParentAccount.getParentID();
        	        		int sq_key1 = ParentAccount.getSQ_Key();
        	        		int sq_key2 = ParentAccount.getSQ_Key2();
        	        		String sq_ans1 = ParentAccount.getSQ_Answer();
        	        		String sq_ans2 = ParentAccount.getSQ_Answer2();
        	        	
        	        		addParentUser.setParentID(userID);
        	        		addParentUser.setUsername(usr);
        	        		addParentUser.setPassword(newpassString);
        	        		addParentUser.setSQ_Key(sq_key1);
        	        		addParentUser.setSQ_Key2(sq_key2);
        	        		addParentUser.setSQ_Answer(sq_ans1);
        	        		addParentUser.setSQ_Answer2(sq_ans2);
            				
            				db.UpdateParent_Account(addParentUser);
            				
            				  Object[] options = {"OK"};
            				    int n = JOptionPane.showOptionDialog(frame,
            				                   "Successfully Changed Password! You may now login! ","Success",
            				                   JOptionPane.PLAIN_MESSAGE,
            				                   JOptionPane.QUESTION_MESSAGE,
            				                   null,
            				                   options,
            				                   options[0]);
            				    if(n == 0) {
            				    	frame.dispose();
              				    	card.next(cardPane);
              				    	card.next(cardPane);
            				    }
            				
            			}
            			else {
            			JOptionPane.showMessageDialog(null, "You must select a child or parent account to change password!","Confirmation", JOptionPane.WARNING_MESSAGE);
            			}
            		}
    	  		      
        		}
        		
	        	
	        }});
	        panel.add(accButton);
	        
	 
	        frame.getContentPane().add(panel);
			return frame;
		}
	
		//this is the pop JFrame if the user has forgotten their security questions
		private static JFrame ForgotSQpage(CardLayout card) {
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			JMenuBar titlePanel = titlePanel(frame);
			frame.setUndecorated(true);
			frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
	        frame.setPreferredSize(new Dimension(700, 600)); 
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			panel.setBackground(aa_grey);
			panel.setLayout(null);
			panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
		

			JLabel register = new JLabel("Forgot your Security Questions?");
			


			  	JLabel username = new JLabel("Username: ");
			  	JLabel password = new JLabel("Password: ");

			
				
				JTextField usernametext = new JTextField();
				JPasswordField passwordtext = new JPasswordField();
			
				JButton Child = new JButton("Child");
			    JButton Parent = new JButton("Parent");
				JButton accButton = new JButton("Change Questions");
			    JButton backButton = new JButton("Back");
			  
			    BufferedImage cat3 = null;
			    try {
					
			    	cat3 = ImageIO.read(new File("avatarSelection/avatar_cat3.png"));
				
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			    
			    JLabel dinopic = new JLabel(new ImageIcon(cat3));
			    dinopic.setBounds(100, 182, 127, 150);
			    panel.add(dinopic);
			
				register.setBounds(200, 0, 360, 100);
				register.setForeground(aa_purple);
				register.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
				register.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(register);

				Child.setBounds(397, 80, 78, 40);
				Child.setBorderPainted(false);
				Child.setBackground(aa_purple);
				Child.setForeground(Color.WHITE);
				Child.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
				Child.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		//declares the string depending on who is registering
		        		userinput = "Child";
		        		//changes the color of button upon click
		        		Child.setBackground(Color.WHITE);
		        		Child.setForeground(aa_purple);
		        		 Parent.setBackground(aa_purple);
		      	        Parent.setForeground(Color.WHITE);
		      	        
		        }});
		        panel.add(Child);
		        
		        Parent.setBounds(525, 80, 95, 40);
		        Parent.setBorderPainted(false);
		        Parent.setBackground(aa_purple);
		        Parent.setForeground(Color.WHITE);
		        Parent.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
		        Parent.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		//declares the string depending on who is registering
		        		userinput = "Parent";
		        		//changes the color of button upon click
		        		Parent.setBackground(Color.WHITE);
		        		 Parent.setForeground(aa_purple);
		        		 Child.setBackground(aa_purple);
		     			Child.setForeground(Color.WHITE);
		        		
		        }});
		        panel.add(Parent);
			        
				
				username.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				username.setBounds(310, 252, 270, 28);
				username.setForeground(aa_purple);
				username.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
		        panel.add(username);
		        
				usernametext.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				usernametext.setBounds(398, 252, 270, 28);
			    usernametext.setForeground(Color.BLACK);
		        usernametext.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
		        panel.add(usernametext);
		    	
				password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				password.setBounds(310, 302, 270, 28);
				password.setForeground(aa_purple);
				password.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
		        panel.add(password);
		        
		        passwordtext.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				passwordtext.setBounds(398, 302, 270, 28);
				passwordtext.setForeground(Color.BLACK);
				passwordtext.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
		        panel.add(passwordtext);
		        
	        
	        accButton.setBounds(397, 452, 250, 37);
	        accButton.setBorderPainted(false);
	        accButton.setBackground(aa_purple);
	        accButton.setForeground(Color.WHITE);
	        accButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        accButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	
		  		    DataBase db = new DataBase();	
		  		    String usr = new String(usernametext.getText());
		        	String pwds = new String(passwordtext.getPassword());
		        	String pwd = hash(pwds);
	        	
		        	db.DatabaseSetUp();
        			
	        		User_Account UserAccount = db.UsernameUser_Account(usr);
	        		User_Account addChildUser = new User_Account();
	        		Parent_Account ParentAccount = db.UsernameParent_Account(usr);
	        		Parent_Account addParentUser = new Parent_Account();
	        		
        			if(usr.isEmpty() == true || pwd.isEmpty() == true ) {
            			JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "One of your fields are blank, please fill them in.");
            		}
        			else if (UserAccount.getUsername().isEmpty() == true  &&  ParentAccount.getUsername().isEmpty() == true ) {
        				JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "Username does not exist.");
        			}
            		else {

            			if(userinput == "Child" ) {
            			
        	        		int userID = UserAccount.getUserID();
        	        		String Fn = UserAccount.getName();
        	        		String pass = UserAccount.getPassword();
        	        		int SQ1int = UserAccount.getSQ_Key();
        	        		int SQ2int = UserAccount.getSQ_Key2();
        	        		String ansinput1 = UserAccount.getSQ_Answer();
        	        		String ansinput2 = UserAccount.getSQ_Answer2();
        	        		
        	        		if(pass.equals(pwd) == false) {
        	        			JFrame errorframe = new JFrame();
                				JOptionPane.showMessageDialog(errorframe, "Password is incorrect. Please try again.");
        	        		}
        	        		else {

            	        		
            	        		addChildUser.setName(Fn);
                				addChildUser.setUserID(userID);
                				addChildUser.setUsername(usr);
                				addChildUser.setPassword(pass);
                				addChildUser.setSQ_Key(SQ1int);
                				addChildUser.setSQ_Key2(SQ2int);
                				addChildUser.setSQ_Answer(ansinput1);
                				addChildUser.setSQ_Answer2(ansinput2);
                				
                				db.UpdateUser_Account(addChildUser);
                				
                				frame.dispose();
                				ChangeSQpage(card,usr,userinput);
                				
        	        		}
        	        	
            			}
            			else if(userinput == "Parent"){
            	
                			
        	        	
        	        		String pass = ParentAccount.getPassword();
        	        		int userID = ParentAccount.getParentID();
        	        		int SQ1int = ParentAccount.getSQ_Key();
        	        		int SQ2int = ParentAccount.getSQ_Key2();
        	        		String ansinput1 = ParentAccount.getSQ_Answer();
        	        		String ansinput2 = ParentAccount.getSQ_Answer2();
        	        		
        	        		
        	        		if(pass.equals(pwd) == false) {
        	        			JFrame errorframe = new JFrame();
                				JOptionPane.showMessageDialog(errorframe, "Password is incorrect. Please try again.");
        	        		}
        	        		else {

            	        		addParentUser.setParentID(userID);
            	        		addParentUser.setUsername(usr);
            	        		addParentUser.setPassword(pass);
            	        		addParentUser.setSQ_Key(SQ1int);
            	        		addParentUser.setSQ_Key2(SQ2int);
            	        		addParentUser.setSQ_Answer(ansinput1);
            	        		addParentUser.setSQ_Answer2(ansinput2);
                				
                				db.UpdateParent_Account(addParentUser);
                				frame.dispose();
                				ChangeSQpage(card,usr,userinput);
                				
        	        		}
        	        	
            				
            			}
            			else {
            			JOptionPane.showMessageDialog(null, "You must select a child or parent account to change password!","Confirmation", JOptionPane.WARNING_MESSAGE);
            			}
            		}
    	  		      
	        }});
	        panel.add(accButton);
	        frame.getContentPane().add(panel);
			return frame;
		}
		
		//this is the popup JFrame that will allow the user to change their security  questions (this popup is used in ForgotSQpage
		private static JFrame ChangeSQpage(CardLayout card, String user,String userinput) {
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			JMenuBar titlePanel = titlePanel(frame);
			frame.setUndecorated(true);
			frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
	        frame.setPreferredSize(new Dimension(700, 600)); 
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			panel.setBackground(aa_grey);
			panel.setLayout(null);
			panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
		

			JLabel register = new JLabel("Please Input New Security Questions!");
			  String[] optionsToChoose = {"The name of your first pet?", "The name of your best friend?", "Your favorite food?", "Your favorite school subject?"};

			  String[] optionsToChoose2 = {"The street you live on?", "Favorite video game?", "Your favorite teachers name?", "Your favorite animal?"};



			  
				JLabel sqlabel1 = new JLabel("Security Question 1: ");
				JLabel ans1 = new JLabel("Answer: ");
				JLabel sqlabel2 = new JLabel("Security Question 2: ");
				JLabel ans2 = new JLabel("Answer: ");
				
			
				JComboBox<String> security1 = new JComboBox<>(optionsToChoose);
				JComboBox<String> security2 = new JComboBox<>(optionsToChoose2);
				
		
				JPasswordField answer2text = new JPasswordField();
				JPasswordField answer2text2 = new JPasswordField();
				
				
				JButton accButton = new JButton("Change Questions");
			    JButton backButton = new JButton("Back");
			  
			    BufferedImage cat3 = null;
			    try {
					
			    	cat3 = ImageIO.read(new File("avatarSelection/avatar_cat3.png"));
				
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			    
			    JLabel dinopic = new JLabel(new ImageIcon(cat3));
			    dinopic.setBounds(100, 182, 127, 150);
			    panel.add(dinopic);
			
				register.setBounds(200, 0, 360, 100);
				register.setForeground(aa_purple);
				register.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
				register.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(register);

		        sqlabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        sqlabel1.setBounds(250, 152, 270, 28);
		        sqlabel1.setForeground(aa_purple);
		        sqlabel1.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
		        panel.add(sqlabel1);
		        
		        
		        security1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        security1.setBounds(398, 152, 270, 28);
		        security1.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
		        security1.setForeground(Color.BLACK);
		        panel.add(security1);
		        
		        ans1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        ans1.setBounds(340, 202, 270, 28);
		        ans1.setForeground(aa_purple);
		        ans1.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
		        panel.add(ans1);
		        
		        answer2text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        answer2text.setBounds(398, 202, 270, 28);
		        answer2text.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
		        answer2text.setForeground(Color.BLACK);
		        panel.add(answer2text);
		      
		       
		        sqlabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        sqlabel2.setBounds(250, 252, 270, 28);
		        sqlabel2.setForeground(aa_purple);
		        sqlabel2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
		        panel.add(sqlabel2);
		        
		        security2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        security2.setBounds(398, 252, 270, 28);
		        security2.setForeground(Color.BLACK);
		        security2.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
		        panel.add(security2);
		        
		        
		        ans2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        ans2.setBounds(340, 302, 270, 28);
		        ans2.setForeground(aa_purple);
		        ans2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
		        panel.add(ans2);
		        
		        answer2text2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		        answer2text2.setBounds(398, 302, 270, 28);
		        answer2text2.setForeground(Color.BLACK);
		        answer2text2.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
		        panel.add(answer2text2);
		        
	        
	        accButton.setBounds(397, 452, 250, 37);
	        accButton.setBorderPainted(false);
	        accButton.setBackground(aa_purple);
	        accButton.setForeground(Color.WHITE);
	        accButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        accButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	
		  		    DataBase db = new DataBase();	
		  		    String usr = new String(user);
	        		int SQ1int = security1.getSelectedIndex();
		        	int SQ2int = security2.getSelectedIndex();
	        		String ansin1 = new String(answer2text.getPassword());
	        		String ansin2 = new String(answer2text2.getPassword());
	        		String ansinput1 = hash(ansin1);
	        		String ansinput2 = hash(ansin2);
	  		
        		
        		
        	
        			if(ansinput1.isEmpty() == true ||  ansinput2.isEmpty() == true) {
            			JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "One of your fields are blank, please fill them in.");
            		}
            		else {

            			if(userinput == "Child" ) {
            				db.DatabaseSetUp();
                			
        	        		User_Account UserAccount = db.UsernameUser_Account(usr);
        	        		User_Account addChildUser = new User_Account();
        	        		int userID = UserAccount.getUserID();
        	        		String Fn = UserAccount.getName();
        	        		String pass = UserAccount.getPassword();
        	        		
        	        	

            	        		
            	        		addChildUser.setName(Fn);
                				addChildUser.setUserID(userID);
                				addChildUser.setUsername(usr);
                				addChildUser.setPassword(pass);
                				addChildUser.setSQ_Key(SQ1int);
                				addChildUser.setSQ_Key2(SQ2int);
                				addChildUser.setSQ_Answer(ansinput1);
                				addChildUser.setSQ_Answer2(ansinput2);
                				
                				db.UpdateUser_Account(addChildUser);
                				
                				  Object[] options = {"OK"};
              				    int n = JOptionPane.showOptionDialog(frame,
              				                   "Successfully Changed Security Questions! You may now login!","Success",
              				                 JOptionPane.YES_NO_CANCEL_OPTION,
            					             JOptionPane.DEFAULT_OPTION,
              				                   null,
              				                   options,
              				                   options[0]);
              				    if(n == 0) {
              				  	frame.dispose();
          				    	card.next(cardPane);
          				    	card.next(cardPane);
              				    
        	        		}
        	        	
            			}
            			else if(userinput == "Parent"){
            				db.DatabaseSetUp();
                			
        	        		Parent_Account ParentAccount = db.UsernameParent_Account(usr);
        	        		Parent_Account addParentUser = new Parent_Account();
        	        		String pass = ParentAccount.getPassword();
        	        		int userID = ParentAccount.getParentID();
        	        		
        	        		
        	        		
        	        		

            	        		addParentUser.setParentID(userID);
            	        		addParentUser.setUsername(usr);
            	        		addParentUser.setPassword(pass);
            	        		addParentUser.setSQ_Key(SQ1int);
            	        		addParentUser.setSQ_Key2(SQ2int);
            	        		addParentUser.setSQ_Answer(ansinput1);
            	        		addParentUser.setSQ_Answer2(ansinput2);
                				
                				db.UpdateParent_Account(addParentUser);
                				
                				  Object[] options = {"OK"};
                				    int n = JOptionPane.showOptionDialog(frame,
                				    				"Successfully Changed Security Questions! You may now login!","Success",
                				                   JOptionPane.PLAIN_MESSAGE,
                				                   JOptionPane.QUESTION_MESSAGE,
                				                   null,
                				                   options,
                				                   options[0]);
                				    if(n == 0) {
                				    	frame.dispose();
                  				    	card.next(cardPane);
                  				    	card.next(cardPane);
                  				 
        	        		}
        	        	
            				
            			}
            			else {
            			JOptionPane.showMessageDialog(null, "You must select a child or parent account to change password!","Confirmation", JOptionPane.WARNING_MESSAGE);
            			}
            		}
    	  		      
        		
        		
	        	
	        }});
	        panel.add(accButton);
	        frame.getContentPane().add(panel);
			return frame;
		}
	
		//this is the popup page that is used from the registration page to register security questions for the user/parent account
		private static JFrame SQpage(CardLayout card, String user,String userinput) {
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			JMenuBar titlePanel = titlePanel(frame);
			frame.setUndecorated(true);
			frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
	        frame.setPreferredSize(new Dimension(700, 600)); 
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			panel.setBackground(aa_grey);
			panel.setLayout(null);
			panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
		

			JLabel register = new JLabel("Please Fill out Security Questions!");
			  String[] optionsToChoose = {"The name of your first pet?", "The name of your best friend?", "Your favorite food?", "Your favorite school subject?"};

			  String[] optionsToChoose2 = {"The street you live on?", "Favorite video game?", "Your favorite teachers name?", "Your favorite animal?"};



			
			JLabel sqlabel1 = new JLabel("Security Question 1: ");
			JLabel ans1 = new JLabel("Answer: ");
			JLabel sqlabel2 = new JLabel("Security Question 2: ");
			JLabel ans2 = new JLabel("Answer: ");
			
		
			JComboBox<String> security1 = new JComboBox<>(optionsToChoose);
			JComboBox<String> security2 = new JComboBox<>(optionsToChoose2);
			
			JPasswordField answer2text = new JPasswordField();
			JPasswordField answer2text2 = new JPasswordField();
			
			
			JButton accButton = new JButton("Confirm Security Questions");
		    JButton backButton = new JButton("Back");
		    JButton loginButton = new JButton("Login");
		    
		    BufferedImage cat3 = null;
		    try {
				
		    	cat3 = ImageIO.read(new File("avatarSelection/avatar_cat3.png"));
			
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		    
		    JLabel dinopic = new JLabel(new ImageIcon(cat3));
		    dinopic.setBounds(100, 182, 127, 150);
		    panel.add(dinopic);
		
			register.setBounds(200, 0, 360, 100);
			register.setForeground(aa_purple);
			register.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
			register.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(register);
	        
	        sqlabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        sqlabel1.setBounds(250, 202, 270, 28);
	        sqlabel1.setForeground(aa_purple);
	        sqlabel1.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(sqlabel1);
	        
	        
	        security1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        security1.setBounds(398, 202, 270, 28);
	        security1.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        security1.setForeground(Color.BLACK);
	        panel.add(security1);
	        
	        ans1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        ans1.setBounds(340, 252, 270, 28);
	        ans1.setForeground(aa_purple);
	        ans1.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(ans1);
	        
	        answer2text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        answer2text.setBounds(398, 252, 270, 28);
	        answer2text.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        answer2text.setForeground(Color.BLACK);
	        panel.add(answer2text);
	      
	       
	        sqlabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        sqlabel2.setBounds(250, 302, 270, 28);
	        sqlabel2.setForeground(aa_purple);
	        sqlabel2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(sqlabel2);
	        
	        security2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        security2.setBounds(398, 302, 270, 28);
	        security2.setForeground(Color.BLACK);
	        security2.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(security2);
	        
	        
	        ans2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        ans2.setBounds(340, 352, 270, 28);
	        ans2.setForeground(aa_purple);
	        ans2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(ans2);
	        
	        answer2text2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        answer2text2.setBounds(398, 352, 270, 28);
	        answer2text2.setForeground(Color.BLACK);
	        answer2text2.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(answer2text2);
	        
	        	        
	        
	        accButton.setBounds(397, 425, 250, 37);
	        accButton.setBorderPainted(false);
	        accButton.setBackground(aa_purple);
	        accButton.setForeground(Color.WHITE);
	        accButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        accButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	
		  		    DataBase db = new DataBase();	
	        		String usr = new String(user);
	        		int SQ1int = security1.getSelectedIndex();
		        	int SQ2int = security2.getSelectedIndex();
	        		String ansin1 = new String(answer2text.getPassword());
	        		String ansin2 = new String(answer2text2.getPassword());
	        		String ansinput1 = hash(ansin1);
	        		String ansinput2 = hash(ansin2);
	  		
        		
        		
        	
        			if(usr.isEmpty() == true || ansinput1.isEmpty() == true ||  ansinput2.isEmpty() == true) {
            			JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "One of your fields are blank, please fill them in.");
            		}
            		else {

            			if(userinput == "Child" ) {
            				db.DatabaseSetUp();
                			
        	        		User_Account UserAccount = db.UsernameUser_Account(usr);
        	        		User_Account addChildUser = new User_Account();
        	        		int userID = UserAccount.getUserID();
        	        		String Fn = UserAccount.getName();
        	        		String pass = UserAccount.getPassword();
        	        		
        	        		
        	        		addChildUser.setName(Fn);
            				addChildUser.setUserID(userID);
            				addChildUser.setUsername(usr);
            				addChildUser.setPassword(pass);
            				addChildUser.setSQ_Key(SQ1int);
            				addChildUser.setSQ_Key2(SQ2int);
            				addChildUser.setSQ_Answer(ansinput1);
            				addChildUser.setSQ_Answer2(ansinput2);
            				
            				db.UpdateUser_Account(addChildUser);
            				
            				  Object[] options = {"OK"};
          				    int n = JOptionPane.showOptionDialog(frame,
          				                   "Successfully Registered Child Account! Logging in now...","Success",
          				                 JOptionPane.YES_NO_CANCEL_OPTION,
        					             JOptionPane.DEFAULT_OPTION,
          				                   null,
          				                   options,
          				                   options[0]);
          				    if(n == 0) {
          				  	User_Account UserAccount2 = db.UsernameUser_Account(usr);
	        				int userid = UserAccount2.getUserID();
	        				
	        				//File object for directory
	        				File directoryPath = new File("happyThoughtMedia");
	        				
	        				//List of all files in happyThoughtMedia directory
	        				String files[] = directoryPath.list();
	        				
	        				/*
	        				 * for all the media that exists in the happyThoughtMediaDirectory
	        				 * add files to database as pre-loaded library
	        				 */
	        				for(int i = 0; i < files.length; i++) {
	        					Media media = new Media("happyThoughtMedia/" + files[i]);
	        					db.AddMedia(media, userid);
	        				}
	        				
	        				Settings sett = new Settings(userid);
	        			
	        				db.AddSettings(sett, userid);
	        			
	        				
	        				frame.dispose();
	        				childPortal(userid,sett,db);
          				    }
            			}
            			else if(userinput == "Parent"){
            				db.DatabaseSetUp();
                			
        	        		Parent_Account ParentAccount = db.UsernameParent_Account(usr);
        	        		Parent_Account addParentUser = new Parent_Account();
        	        		String pass = ParentAccount.getPassword();
        	        		int userID = ParentAccount.getParentID();
        	        		
        	        	
        	        		addParentUser.setParentID(userID);
        	        		addParentUser.setUsername(usr);
        	        		addParentUser.setPassword(pass);
        	        		addParentUser.setSQ_Key(SQ1int);
        	        		addParentUser.setSQ_Key2(SQ2int);
        	        		addParentUser.setSQ_Answer(ansinput1);
        	        		addParentUser.setSQ_Answer2(ansinput2);
            				
            				db.UpdateParent_Account(addParentUser);
            				
            				  Object[] options = {"OK"};
            				    int n = JOptionPane.showOptionDialog(frame,
            				                   "Successfully Registered Parent Account! Logging in now... ","Success",
            				                   JOptionPane.PLAIN_MESSAGE,
            				                   JOptionPane.QUESTION_MESSAGE,
            				                   null,
            				                   options,
            				                   options[0]);
            				    if(n == 0) {
            				    	Parent_Account ParentAccount2 = db.UsernameParent_Account(usr);
			        				int Parentid = ParentAccount2.getParentID();
			      				
			        	
			        				frame.dispose();
			        				parentPortal(Parentid,db);
            				    }
            				
            			}
            			else {
            			JOptionPane.showMessageDialog(null, "You must select a child or parent account to change password!","Confirmation", JOptionPane.WARNING_MESSAGE);
            			}
            		}
    	  		      
        		
        		
	        	
	        }});
	        panel.add(accButton);
	        
//	        loginButton.setBounds(500, 490, 78, 40);
//	        loginButton.setBorderPainted(false);
//	        loginButton.setBackground(aa_purple);
//	        loginButton.setForeground(Color.WHITE);
//	        loginButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
//	        loginButton.addActionListener(new ActionListener() {
//	        	public void actionPerformed(ActionEvent e) {
//	        	//redirect the user to the login page
//	        	card.previous(cardPane);
//	        }});
//	        panel.add(loginButton);
	        frame.getContentPane().add(panel);
			return frame;
		}
		
		//this is the welcome page cardlayour where the user can choose to register/login to their account
		private static JPanel welcomepage(CardLayout card, JFrame frame) {
			JPanel panel = new JPanel();
			panel.setBackground(aa_grey);
			panel.setLayout(null);
		
			//TODO add logo
			JLabel Label = new JLabel("Welcome to");
			JLabel Label2 = new JLabel("The Attention Assistant!");
			JLabel option = new JLabel("Please login or register for an account");
			JButton login=new JButton("Login");
			JButton register= new JButton("Register"); 
			JButton parentportal=new JButton("Parent Portal");
			JButton ChildPortal= new JButton("Child Portal"); 
			
			BufferedImage cat1 = null;
			 BufferedImage duck = null;
			    try {
			    	cat1 = ImageIO.read(new File("avatarSelection/avatar_cat1.png"));
			    	duck = ImageIO.read(new File("avatarSelection/avatar_duck.png"));
				
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			    
			    JLabel catpic = new JLabel(new ImageIcon(cat1));
			    catpic.setBounds(500, 80, 138, 150);
			    panel.add(catpic);
			    
			    JLabel duckpic = new JLabel(new ImageIcon(duck));
			    duckpic.setBounds(60, 80, 132, 150);
			    panel.add(duckpic);
			
			
			Label.setBounds(285, 115, 280, 100);
			Label.setForeground(new Color(255, 255, 255));
			Label.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
			panel.add(Label);
			
			Label2.setBounds(225, 180, 280, 100);
			Label2.setForeground(new Color(255, 255, 255));
			Label2.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
			panel.add(Label2);
			

			option.setBounds(200, 280, 290, 100);
			option.setForeground(new Color(255, 255, 255));
			option.setFont(new Font("Dosis SemiBold",Font.BOLD,15));
			panel.add(option);

//			
//			parentportal.setBounds(185, 400, 140, 35);
//			parentportal.setHorizontalTextPosition(SwingConstants.CENTER);
//			parentportal.setVerticalTextPosition(SwingConstants.CENTER);
//			parentportal.setFont(new Font("Dosis SemiBold", Font.BOLD, 17));
//			parentportal.setBorderPainted(false);
//			parentportal.setBackground(aa_purple);
//			parentportal.setForeground(Color.WHITE);
//			parentportal.addActionListener(new ActionListener() {
//		        	public void actionPerformed(ActionEvent e) {
//		        		DataBase db = new DataBase();	
//		        		String password = new String("1");
//		        		String usr = new String("Test");
//		  
//
//		        		String pwd = hash(password);
//		       
//		        		
//		        		db.DatabaseSetUp();
//		        		
//		        		Parent_Account ParentAccount = db.SearchParent_Account(usr,pwd);
//		        		Parent_Account addParentUser = new Parent_Account();
//		        	
//		        		
//		        		if(ParentAccount.getPassword().equals(pwd) == true && ParentAccount.getUsername().equals(usr) == true) {
//		        			JFrame success = new JFrame();
//	        				JOptionPane.showMessageDialog(success, "Test Parent Account Logging In!");
//	        				
//	        				//Parent_Account Parent_Account2 = db.Username_Parent_Account(usr);
//	        				int ParentID = ParentAccount.getParentID();
//	        				frame.dispose();
//	        				success.dispose();
//	        				parentPortal(ParentID,db);
//		        		}
//		        		else {
//		        			
//		        			addParentUser.setUsername(usr);
//		        			addParentUser.setPassword(pwd);
//		        			db.AddParent_Account(addParentUser);
//		        			
//		        			//Parent_Account Parent_Account2 = db.Username_Parent_Account(usr);
//	        				int ParentID = ParentAccount.getParentID();
//	        				
//	        			
//	        				JFrame success = new JFrame();
//	        				JOptionPane.showMessageDialog(success, "Successfully Registered Test Parent Account! Logging in now...");
//	        				
//	        				frame.dispose();
//	        				success.dispose();
//	        				parentPortal(ParentID,db);
//		        		}
//		        		
//		        }});
//			 panel.add(parentportal);
			 
			 login.setBounds(225, 380, 97, 35);
			 login.setHorizontalTextPosition(SwingConstants.CENTER);
			 login.setVerticalTextPosition(SwingConstants.CENTER);
			 login.setFont(new Font("Dosis SemiBold", Font.BOLD, 17));
			 login.setBorderPainted(false);
			 login.setBackground(aa_purple);
			 login.setForeground(Color.WHITE);
			 login.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        	card.next(cardPane);
		        	
		        }});
			 panel.add(login);
			 
			 
//			 ChildPortal.setBounds(350, 400, 125, 35);
//			 ChildPortal.setHorizontalTextPosition(SwingConstants.CENTER);
//			 ChildPortal.setVerticalTextPosition(SwingConstants.CENTER);
//			 ChildPortal.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
//			 ChildPortal.setBorderPainted(false);
//			 ChildPortal.setBackground(Color.white);
//			 ChildPortal.setForeground(aa_purple);
//			 ChildPortal.addActionListener(new ActionListener() {
//		        	public void actionPerformed(ActionEvent e) {
//		        		DataBase db = new DataBase();	
//		        		//open child portal
//		        		String password = new String("1");
//		        		String usr = new String("Test");
//		        		String first = new String("TestChild");
//		        		
//		        		String pwd = hash(password);
//		        		
//		        		
//		        		
//		        		db.DatabaseSetUp();
//		        		User_Account UserAccount = db.SearchUser_Account(usr, pwd);
//		        		User_Account addChildUser = new User_Account();
//		        
//		      		   //keep user name
//		        		//change password to security 1 &2 
//		        		//add one more to display question
//		        		//if questoins are right, do popup for change new passwrod
//		        		//another popup saying 1-2 questions wrong
//		        
//		        		//TODO shouldnt be able to run if string is empty
//		        		if(UserAccount.getPassword().equals(pwd) == true && UserAccount.getUsername().equals(usr) == true) {
//		        			JFrame success = new JFrame();
//	        				JOptionPane.showMessageDialog(success, "Test Child Account Logging In!");
//	        				User_Account UserAccount2 = db.UsernameUser_Account(usr);
//	        				int userid = UserAccount2.getUserID();
//	        				
//	        				Settings sett = new Settings(db,userid);
//	        				
//	           				System.out.print("this is theuser id" + userid);
//	        				frame.dispose();
//	        				success.dispose();
//	        				childPortal(userid,sett,db);
//		        		}
//		        		else {
//		        			addChildUser.setUsername(usr);
//	        				addChildUser.setPassword(pwd);
//	        				addChildUser.setName(first);
//	        				db.AddUser_Account(addChildUser);
//	        				
//	        				User_Account UserAccount2 = db.UsernameUser_Account(usr);
//	        				int userid = UserAccount2.getUserID();
//	        				
//	        				//File object for directory
//	        				File directoryPath = new File("happyThoughtMedia");
//	        				
//	        				//List of all files in happyThoughtMedia directory
//	        				String files[] = directoryPath.list();
//	        				
//	        				/*
//	        				 * for all the media that exists in the happyThoughtMediaDirectory
//	        				 * add files to database as pre-loaded library
//	        				 */
//	        				for(int i = 0; i < files.length; i++) {
//	        					Media media = new Media("happyThoughtMedia/" + files[i]);
//	        					db.AddMedia(media, userid);
//	        				}
//	        				
//	        				Settings sett = new Settings(userid);
//	        				
//	        				db.AddSettings(sett, userid);
//	        				
//	        				JFrame success = new JFrame();
//	        				JOptionPane.showMessageDialog(success, "Successfully Registered Test Child Account! Logging in now...");
//	        				
//	        				System.out.print("this is theuser id" + userid);
//	        				frame.dispose();
//	        				success.dispose();
//	        				childPortal(userid,sett,db);
//	        		
//		        		}
//		        }});
//			 panel.add(ChildPortal);
//			 
//			 
			 register.setBounds(350, 380, 100, 35);
			 register.setHorizontalTextPosition(SwingConstants.CENTER);
			 register.setVerticalTextPosition(SwingConstants.CENTER);
			 register.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
			 register.setBorderPainted(false);
			 register.setBackground(Color.white);
			 register.setForeground(aa_purple);
			 register.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        	card.next(cardPane);
		        	card.next(cardPane);
		        }});
			 panel.add(register);
			 

			return panel;
		}
				
		//this is the register cardlayout where the user/parent can register for an account
		private static JPanel Registerpage(CardLayout card,JFrame frame) {
			JPanel panel = new JPanel();
			panel.setBackground(aa_grey);
			panel.setLayout(null);
		
			//TODO add logo
			JLabel firstName = new JLabel("First Name: ");
			JLabel Username = new JLabel("Username: ");
			JLabel password = new JLabel("Password: ");
			JLabel password2 = new JLabel("Re-enter Password: ");
		
			JTextField firstnametext = new JTextField();
			JTextField usernametext = new JTextField();

			
			JLabel register = new JLabel("Register");
			
			JPasswordField passwordtext = new JPasswordField();
			JPasswordField reenterpasswordtext = new JPasswordField();
			
			JButton Child = new JButton("Child");
		    JButton Parent = new JButton("Parent");
			JButton accButton = new JButton("Create Account");
		    JButton backButton = new JButton("Back");
		    JButton loginButton = new JButton("Login");
		    
		    BufferedImage dino = null;
		    try {
				
				dino = ImageIO.read(new File("avatarSelection/avatar_dino.png"));
			
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		    
		    JLabel dinopic = new JLabel(new ImageIcon(dino));
		    dinopic.setBounds(100, 182, 127, 150);
		    panel.add(dinopic);
		
			register.setBounds(225, 0, 280, 100);
			register.setForeground(aa_purple);
			register.setFont(new Font("Dosis SemiBold",Font.BOLD,40));
			register.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(register);

			
			Child.setBounds(397, 100, 78, 40);
			Child.setBorderPainted(false);
			Child.setBackground(aa_purple);
			Child.setForeground(Color.WHITE);
			Child.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
			Child.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//declares the string depending on who is registering
	        		userinput = "Child";
	        		//changes the color of button upon click
	        		Child.setBackground(Color.WHITE);
	        		Child.setForeground(aa_purple);
	        		 Parent.setBackground(aa_purple);
	      	        Parent.setForeground(Color.WHITE);
	      	        
	        }});
	        panel.add(Child);
	        
	        Parent.setBounds(525, 100, 95, 40);
	        Parent.setBorderPainted(false);
	        Parent.setBackground(aa_purple);
	        Parent.setForeground(Color.WHITE);
	        Parent.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
	        Parent.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//declares the string depending on who is registering
	        		userinput = "Parent";
	        		//changes the color of button upon click
	        		Parent.setBackground(Color.WHITE);
	        		 Parent.setForeground(aa_purple);
	        		 Child.setBackground(aa_purple);
	     			Child.setForeground(Color.WHITE);
	        		
	        }});
	        panel.add(Parent);
		        
	       
			firstName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			firstName.setBounds(300, 182, 270, 28);
			firstName.setForeground(aa_purple);
			firstName.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(firstName);
	        
			firstnametext.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			firstnametext.setBounds(398, 182, 270, 28);
		    firstnametext.setForeground(Color.BLACK);
	        firstnametext.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(firstnametext);
	        
	        Username.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        Username.setBounds(300, 242, 270, 28);
	        Username.setForeground(aa_purple);
	        Username.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(Username);
	        
	        
	        usernametext.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        usernametext.setBounds(398, 242, 270, 28);
	        usernametext.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        usernametext.setForeground(Color.BLACK);
	        panel.add(usernametext);
	        
	        password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        password.setBounds(300, 302, 270, 28);
	        password.setForeground(aa_purple);
	        password.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(password);
	        
	        passwordtext.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        passwordtext.setBounds(398, 302, 270, 28);
	        passwordtext.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        passwordtext.setForeground(Color.BLACK);
	        panel.add(passwordtext);
	        
	        
	        password2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        password2.setBounds(250, 362, 270, 28);
	        password2.setForeground(aa_purple);
	        password2.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        panel.add(password2);
	        
	        reenterpasswordtext.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	        reenterpasswordtext.setBounds(398, 362, 270, 28);
	        reenterpasswordtext.setForeground(Color.BLACK);
	        reenterpasswordtext.setFont(new Font("Dosis SemiBold", Font.PLAIN, 15));
	        panel.add(reenterpasswordtext);
	        
	        
	        accButton.setBounds(397, 425, 200, 37);
	        accButton.setBorderPainted(false);
	        accButton.setBackground(aa_purple);
	        accButton.setForeground(Color.WHITE);
	        accButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
	        accButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		DataBase db = new DataBase();	
	        		String passwords = new String(passwordtext.getPassword());
	        		String pwd = hash(passwords);
	        		
	        		String rPwd = new String(reenterpasswordtext.getPassword());
	        		String reenterPwd = hash(rPwd);
	        		String usr = new String(usernametext.getText());
	        		String fn = new String(firstnametext.getText());
	        
	        		if(pwd.isEmpty() == true || usr.isEmpty() == true) {
	        			JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "Please input Password or Username");
	        		}
	        		else if (pwd.length() < 5) {
	        			JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "Password is too short, please make your password over 5 characters.");
	        		}
	        		else {
	        			db.DatabaseSetUp();
	        			
	        			
		    
		        		User_Account UserAccount =  db.UsernameUser_Account(usr);
		        		Parent_Account ParentAccount = db.UsernameParent_Account(usr);
		        		User_Account addChildUser = new User_Account();
		        		Parent_Account addParentUser = new Parent_Account();
		        	
		        		
		        		if(userinput == "Child") {
		        			if(pwd.equals(reenterPwd) == false) {
		        				JOptionPane.showMessageDialog(null, "Password do not match! Please reenter your password!","Confirmation", JOptionPane.WARNING_MESSAGE);
			        			//TODO make sure user cannot have a super short password
			        		}
		        			else {
		        				if(UserAccount.getUsername().isEmpty() == true  &&  ParentAccount.getUsername().isEmpty() == true ) {
		        					addChildUser.setUsername(usr);
			        				addChildUser.setPassword(pwd);
			        				addChildUser.setName(fn);
	
			        				db.AddUser_Account(addChildUser);
			        				SQpage(card,usr,userinput);
			        				frame.dispose();
		        					
		        				}
			        			else  {
			        			
			        			
			        				JOptionPane.showMessageDialog(null, "Username in use!","Confirmation", JOptionPane.WARNING_MESSAGE);
			        			}
		        			}
		        				
		        
		        		}
		        		else if(userinput == "Parent") {
		        			if(pwd.equals(reenterPwd) == false) {
		        				JOptionPane.showMessageDialog(null, "Password do not match! Please reenter your password!","Confirmation", JOptionPane.WARNING_MESSAGE);
			        			
			        		}
		        			else {
		        					if(UserAccount.getUsername().isEmpty() == true &&  ParentAccount.getUsername().isEmpty() == true) {
			        				
			        				addParentUser.setUsername(usr);
			        				addParentUser.setPassword(pwd);
			        				db.AddParent_Account(addParentUser);
			        				
			        				SQpage(card,usr,userinput);
			        				frame.dispose();
		        				} 
				      			else {
					      				
					      				 JOptionPane.showMessageDialog(null, "Username in use!","Confirmation", JOptionPane.WARNING_MESSAGE);
					      			}
		        		}
		        				
		        		
		        		}
		        		else {
		        			JOptionPane.showMessageDialog(null, "You must select a child or parent account to register!","Confirmation", JOptionPane.WARNING_MESSAGE);
		        		}
	        		}
	        		
	        	
	        	
	        }});
	        panel.add(accButton);
	        
	        loginButton.setBounds(500, 490, 78, 40);
	        loginButton.setBorderPainted(false);
	        loginButton.setBackground(aa_purple);
	        loginButton.setForeground(Color.WHITE);
	        loginButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
	        loginButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	//redirect the user to the login page
	        	card.previous(cardPane);
	        }});
	        panel.add(loginButton);
	       
	        
	        backButton.setBounds(397, 490, 78, 40);
	        backButton.setBorderPainted(false);
	        backButton.setBackground(Color.WHITE);
	        backButton.setForeground(aa_purple);
	        backButton.setFont(new Font("Dosis SemiBold", Font.BOLD, 14));
	        backButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	card.first(cardPane);
	        }});
	        panel.add(backButton);
	        
	        
	    
	        //TODO add a login button by the back button
			return panel;
		}
		
		//this initiates the parent portal
		private static void parentPortal(int userID, DataBase db) {
			Parent_Account parent = db.SelectParent_Account(userID);//***************************************************************************
			Parent_Bar parentBar = new Parent_Bar(parent,db);
			parentBar.run_parent_bar();
		}
		
		//this initiates the child portal
		static Notification_System notif;
		static Pomodoro_Timer pomo;
		static Negative_Thought_Burner ntb;
		static Happy_Thought_Button htb;
		static Free_Thought_Space fts;
		private static void childPortal(int userID,Settings settings,DataBase db) {
			
			//Settings settings = new Settings(userID);
			Nav_Bar navbar = new Nav_Bar(settings,db);
			//db.DatabaseSetUp();
					
			
			ntb = new Negative_Thought_Burner();
			htb = new Happy_Thought_Button(db);
			fts = new Free_Thought_Space();
			/**
			 * loading default happy media library into database 
			 * this will take place just once during user registration
			 * will remove from driver after user registration is created
			 */
			
			
			/**
			 * get settings
			 * this db.AddSettings(settings) call is temporary for testing purposes until user class is created
			 * 
			 * db.AddSettings(settings, userID) will be called from during the registration process for a new user
			 * db.selectSettings(userID) will be called during the login process for a returning user
			 */
			
			
			//db.AddSettings(settings, userID); 
			
			try {
				notif = new Notification_System(userID,db);
				pm = new Priority_Manager(userID,db);
				
				pomo = new Pomodoro_Timer(settings,db,pm);
				pm.setPomo(pomo);
				notif.setPomo(pomo);
				
				navbar.run_nav_bar(userID,notif,db,navbar,settings,pm,pomo,ntb,htb,fts);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	 
		}
		
		//this runs the login panel from the driver
		public static void run_login() {
			//EventQueue.invokeLater(new Runnable(){
				//@Override
				//public void run() {
			JFrame frame = new JFrame();
			frame.setUndecorated(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// TODO add title panel frame.setUndecorated(true);
			frame.setTitle("The Attention Assistant");
	        frame.setBackground(aa_grey);
	        frame.setPreferredSize(new Dimension(700, 600)); 
	        //makes frame and contents visible
	    
	        
	        JMenuBar titlePanel = titlePanel(frame);
	        
	        card = new CardLayout();
	        WelcomePanel = new JPanel();
	        LoginPanel = new JPanel();
	        RegisterPanel = new JPanel();
	        ForgotPanel = new JPanel();
	        cardPane = new JPanel();
	        
	        LoginPanel = loginpage(card,frame);
	        RegisterPanel = Registerpage(card,frame);
	        WelcomePanel = welcomepage(card,frame);
	        ForgotPanel = resetPass(card,frame);
	     
	    	
	    	
	    	cardPane.setLayout(card);
	        cardPane.setBounds(300, 50, 700, 600);
	        cardPane.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
	    	cardPane.add(WelcomePanel, "Welcome Panel");
	        cardPane.add(LoginPanel, "Login Panel");
	        cardPane.add(RegisterPanel, "Register Panel");
	        cardPane.add(ForgotPanel, "Forgot Password Panel");
	            
	        
	        cardPane.addMouseListener(new MouseAdapter(){
				@Override 
				public void mousePressed(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
				}
			});
	    	frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
	        frame.getContentPane().add(cardPane);
	        frame.setPreferredSize(new Dimension(700, 600)); 
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);

		}
	
}

