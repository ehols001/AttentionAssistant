package AttentionAssistant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.TextAttribute;
import java.awt.image.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class Parent_Bar{
	private static int mouseX;
	private static int mouseY;
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	int sWidth = (int) screen.getWidth();
	int sHeight = (int) screen.getHeight();
	static Color aa_grey = new Color(51,51,51);
	static Color aa_purple = new Color(137,31,191);
	static LineBorder line = new LineBorder(aa_purple, 2, true);
	private int height = 750; 
	private int width = 575;
	private int counter;
	JButton toRefresh;
	private ArrayList<User_Account> childAccounts;
	private Parent_Account parent;
	private DataBase db;
	
	public Parent_Bar(Parent_Account p, DataBase db) {
		this.parent = new Parent_Account(p);
		this.childAccounts = db.Select_All_Users_Linked_Account(parent);
		this.db = db;
	}
	
	JPanel child_panel;
	
	public void run_parent_bar() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				counter = 1;
				JFrame frame = new JFrame();
				frame.setBounds(0, 0, 1000, 1000);
				//removes default title bar from frame 
		        frame.setUndecorated(true);
		        //sets background of frame to transparent
		        frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		        //forces frame to stay on top of screen
		        frame.setAlwaysOnTop(true);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setLocation(0, 0);
		        //makes frame and contents visible
		        frame.setVisible(true);
		        
		        CardLayout cardLayout = new CardLayout();
		        JPanel panel = new JPanel();
		        panel.setBounds(0, 0, 100, 1000);
		        panel.setLayout(cardLayout);
		        
		        //panel for buttons
		        child_panel = childPanel(db,cardLayout, panel, frame);
		        panel.add("iPanel", child_panel);
		        cardLayout.show(panel, "iPanel");
		        frame.getContentPane().add(panel);
				frame.pack();
				frame.setVisible(true);
				frame.setResizable(true);
				
				toRefresh = new JButton();
		        toRefresh.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		rebuildPanel(db,cardLayout,panel,frame);
		        }});
				
			}
		});
	}
	
	Priority_Manager pm;
	String[] children;
	private JPanel childPanel(DataBase db,CardLayout cardLayout,JPanel panel,JFrame frame) {
		JPanel cPanel = new JPanel();
		frame.setPreferredSize(new Dimension(80,(80*9)));
		cPanel.setPreferredSize(new Dimension(80,(80*9)));
		cPanel.setLayout(new BoxLayout(cPanel,BoxLayout.Y_AXIS));
		
		if(childAccounts.size() != 0) {
			//display button for each linked child - opens child management
	        for(int i=0;i<childAccounts.size();i++) {
	        	int childID = childAccounts.get(i).getUserID();
	        	Settings settings = new Settings(db,childID);
	        	Parent_Portal menu = new Parent_Portal();
	        	Progress_Report pr = new Progress_Report(); 
	        	pm = new Priority_Manager(childID,db,true);
	        	JButton button = createButton(childAccounts.get(i).getName());
	        	button.addActionListener(new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		
	            		menu.open_parentPortal(db, settings, pm, pr, childID);
	            	}});
	        	cPanel.add(button);
	        }
	        cPanel.revalidate();
			cPanel.repaint();
			frame.revalidate();
			frame.repaint();
		}
		
		JButton account = createAmButton();
		account.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		accountManage();
        	}});
		cPanel.add(account);
		cPanel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		frame.revalidate();
		frame.repaint();
		return cPanel;
	}
	
	
	private void accountManage() {
		JFrame frame = new JFrame();
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.black);
		frame.setPreferredSize(new Dimension(width, height));
		JMenuBar titlePanel = titlePanel(frame);
		
		
		int x = (int) ((screen.getWidth() - 570) /2);
		int y = (int) ((screen.getHeight() - 750) /2);
		frame.setLocation(x,y);
		//set up title panel************************************
		
		
		JPanel linkPanel = linkPanel(frame);
		
		frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
		frame.getContentPane().add(linkPanel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	JComboBox<String> accounts;
	private JPanel linkPanel(JFrame frame) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(aa_grey);
		panel.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, aa_purple));
		
		JLabel linkChild = new JLabel("Link Child Account");
		JLabel unlinkChild = new JLabel("Unlink Child Account");
		JLabel changePassword = new JLabel("Change Password");
		
		JLabel childName = new JLabel("Child Username: ");
		JLabel childPw = new JLabel("Child Password: ");
		JLabel oldPw = new JLabel("Old Password: ");
		JLabel newPw = new JLabel("New Password: ");
		JLabel newPwRe = new JLabel("Re-Enter Password: ");
		
		JTextField childNameText = new JTextField();
		JPasswordField childPwText = new JPasswordField();
		JPasswordField oldPwText = new JPasswordField();
		JPasswordField newPwText = new JPasswordField();
		JPasswordField newPwReText = new JPasswordField();
		
		JButton link = new JButton("Link");
		JButton pwChange = new JButton("Change");
		
		Font font = new Font("Dosis SemiBold", Font.BOLD, 15);
		Font font2 = new Font("Dosis SemiBold", Font.BOLD, 18);
		
		Map<TextAttribute,Object> att = new HashMap<>(font2.getAttributes());
		att.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		
		linkChild.setFont(font2.deriveFont(att));
		linkChild.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		linkChild.setBounds(190,50,250,30);
		linkChild.setForeground(aa_purple);
		
		panel.add(linkChild);
		
		//link child account
		childName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		childName.setBounds(50,100,150,30);
		childName.setForeground(aa_purple);
		childName.setFont(font);
		panel.add(childName);
		
        childNameText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        childNameText.setBounds(250,100,250,30);
        childNameText.setForeground(aa_purple);
        childNameText.setFont(font);
        childNameText.setCaretColor(aa_purple);
        panel.add(childNameText);
                
        childPw.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        childPw.setBounds(50,140,150,30);
        childPw.setForeground(aa_purple);
        childPw.setFont(font);
        panel.add(childPw);
        
        childPwText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        childPwText.setBounds(250,140,250,30);
        childPwText.setForeground(aa_purple);
        childPwText.setFont(font);
        childPwText.setCaretColor(aa_purple);
        panel.add(childPwText);
        
        link.setBackground(aa_purple);
        link.setForeground(Color.white);
        link.setFont(font);
        link.setBorderPainted(false);
        
        link.setBounds(425,180,75,30);
        panel.add(link);
        
		
        //unlink account************************************************************************
        unlinkChild.setFont(font2.deriveFont(att));
		unlinkChild.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		unlinkChild.setBounds(190,280,250,30);
		unlinkChild.setForeground(aa_purple);
		
		panel.add(unlinkChild);
        
        children = new String[childAccounts.size()];
        Integer[] c = new Integer[childAccounts.size()];
        for(int i=0;i<childAccounts.size();i++) {
        	String name = childAccounts.get(i).getName();
        	Integer cid = childAccounts.get(i).getUserID();
        	children[i] = name;
        	c[i] = cid;
        }
        
        ArrayList<String> listChildren = new ArrayList<String>(Arrays.asList(children));
        
        accounts = new JComboBox<String>();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(children);
        accounts.setModel(model);
        accounts.setVisible(true);
        accounts.setBounds(100,330,150,30);
        accounts.setSelectedIndex(-1);
        panel.add(accounts);
        
        JButton delete = new JButton("Unlink");
        delete.setBackground(aa_purple);
        delete.setForeground(Color.white);
        delete.setFont(font);
        delete.setBorderPainted(false);
        delete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int s = accounts.getSelectedIndex();
        		for(int j=0;j<childAccounts.size();j++) {
        			if(c[s] == childAccounts.get(j).getUserID()) {
        				// remove linked account
        				User_Account user = childAccounts.get(j);
        				childAccounts.remove(j);
        				db.DeleteLinked_Account(parent, user);
        				JFrame unlinked = new JFrame();
        				JOptionPane.showMessageDialog(unlinked, "Child account unlinked");
        				
        				listChildren.remove(s);
        				children = listChildren.toArray(new String [0]);
        				DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<String>(children);
        				accounts.setModel(newModel);
        				accounts.setSelectedIndex(-1);
        				unlinked.dispose();
        				toRefresh.doClick();
	        		}
        		}
        		// refresh
        		
        	}
        });
        delete.setBounds(300,330,100,30);
        panel.add(delete);
        
        
		//password change***********************************************************************
        changePassword.setFont(font2.deriveFont(att));
        changePassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        changePassword.setBounds(190,430,250,30);
        changePassword.setForeground(aa_purple);
		panel.add(changePassword);
        
        oldPw.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        oldPw.setBounds(50,480,150,30);
        oldPw.setForeground(aa_purple);
        oldPw.setFont(font);
        panel.add(oldPw);
        
        oldPwText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        oldPwText.setBounds(250,480,250,30);
        oldPwText.setForeground(aa_purple);
        oldPwText.setFont(font);
        oldPwText.setCaretColor(aa_purple);
        panel.add(oldPwText);
                
        newPw.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        newPw.setBounds(50,520,150,30);
        newPw.setForeground(aa_purple);
        newPw.setFont(font);
        panel.add(newPw);
        
        newPwText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        newPwText.setBounds(250,520,250,30);
        newPwText.setForeground(aa_purple);
        newPwText.setFont(font);
        newPwText.setCaretColor(aa_purple);
        panel.add(newPwText);
        
        
        newPwRe.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        newPwRe.setBounds(50,560,150,30);
        newPwRe.setForeground(aa_purple);
        newPwRe.setFont(font);
        panel.add(newPwRe);
        
        newPwReText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        newPwReText.setBounds(250,560,250,30);
        newPwReText.setForeground(aa_purple);
        newPwReText.setFont(font);
        newPwReText.setCaretColor(aa_purple);
        panel.add(newPwReText);
        
        
        pwChange.setBackground(aa_purple);
        pwChange.setForeground(Color.white);
        pwChange.setFont(font);
        pwChange.setBorderPainted(false);
        pwChange.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame errorframe = new JFrame();
        		String t;
        		
        		String oldPassword = new String(oldPwText.getPassword());
        		String newPassword = new String(newPwText.getPassword());
        		String rNewPassword = new String(newPwReText.getPassword());
        		String opwd = hash(oldPassword);
        		String npwd = hash(newPassword);
        		String rnpwd = hash(rNewPassword);
        		if(opwd.isEmpty() == true || npwd.isEmpty() == true || rnpwd.isEmpty() == true) {
        			
    				t = "Please retry, 1 or more password fields left blank.";
        		}
        		else if(opwd.equals(parent.getPassword()) == true && npwd.equals(rnpwd)) {
    				parent.setPassword(npwd);
        			db.UpdateParent_Account(parent);
        			t = "Password updated!";
        		}else {
        			if(opwd.equals(parent.getPassword()) == false) {
        				t = "Old password is incorrect.";
        			}else if(npwd != rnpwd) {
        				t = "New password does not match re-entered new password.";
        			}else {
        				t = "Password error.";
        			}
        			childPwText.setText("");
        		}
				JOptionPane.showMessageDialog(errorframe, t);
				errorframe.dispose();
        		
        		oldPwText.setText("");
        		newPwText.setText("");
        		newPwReText.setText("");
        	}
        });
        pwChange.setBounds(400,600,100,30);
        panel.add(pwChange);
        
        link.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// add child account  
        		String password = new String(childPwText.getPassword());
        		String pwd = hash(password);
        		String usr = new String(childNameText.getText());
        	
        		if(pwd.isEmpty() == true || usr.isEmpty() == true) {
        			JFrame errorframe = new JFrame();
    				JOptionPane.showMessageDialog(errorframe, "Please input Password or Username");
    				errorframe.dispose();
        		}
        		else {
	        		User_Account UserAccount = db.SearchUser_Account(usr, pwd);
	        		
	        		if(UserAccount.getPassword().equals(pwd) == true && UserAccount.getUsername().equals(usr) == true) {
        				boolean alreadyLinked = false;
        				for(int i=0;i<childAccounts.size();i++) {
        					if(childAccounts.get(i).getUserID() == UserAccount.getUserID()) {
        						alreadyLinked = true;
        					}
        				}
        				if(alreadyLinked == false) {
        					childAccounts.add(UserAccount);
        					db.AddLinked_Account(parent, UserAccount);
        					JFrame success = new JFrame();
        					JOptionPane.showMessageDialog(success, "Sucessfully linked Child account!!");
        					toRefresh.doClick();
        					success.dispose();
        					
        					listChildren.add(UserAccount.getName());
            				children = listChildren.toArray(new String [0]);
            				DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<String>(children);
            				accounts.setModel(newModel);
            				accounts.setSelectedIndex(-1);
            				toRefresh.doClick();
        					
        				}else {
        					JFrame exist = new JFrame();
            				JOptionPane.showMessageDialog(exist, "Child account already linked");
            				exist.dispose();
            				
        				}
        				
        				childNameText.setText("");
        				childPwText.setText("");
	        		}else {
	        			JFrame errorframe = new JFrame();
        				JOptionPane.showMessageDialog(errorframe, "Incorrect Username/Password! Please try again!");
        				childPwText.setText("");
        				errorframe.dispose();
	        		}
        		}
        		
        	}
        });
        
		return panel;
	}
	
	private JButton createButton(String name) {
		JButton button = new JButton();
		int size = 70;
		
		BufferedImage circle = null;
		try {
			//gets circle image
			circle = ImageIO.read(new File("images/circle.png"));
			circle.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		//colorIcon(img);
		colorCircle(circle);
		
		//set up font
		String n = String.valueOf(name.charAt(0));
		BufferedImage img = new BufferedImage(size, size,circle.getType());
        Graphics2D graphicT = img.createGraphics();
        graphicT.setFont(new Font("Dosis SemiBold", Font.BOLD, 55));
        graphicT.drawString(n, 17, 55);
        graphicT.setColor(Color.white);
        graphicT.dispose();
		
		// create new image of icon image on top of circle image
        BufferedImage newImg = new BufferedImage(size, size,circle.getType());
        Graphics2D graphic = newImg.createGraphics();
        graphic.drawImage(circle, 0, 0, size, size, 0, 0, circle.getWidth(), circle.getHeight(), null);
        graphic.drawImage(img, 0, 0, size, size, 0, 0, img.getWidth(), img.getHeight(), null);
        graphic.dispose();
		
		//creates an ImageIcon
        ImageIcon icon = new ImageIcon(newImg);
        button.setIcon(icon);
        //make non-icon area of button invisible
        button.setContentAreaFilled(false);
        //remove button border
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(false);
        button.setMargin(new Insets(0,0,0,0));
		
		return button;
	}
	
	private JButton createAmButton() {
		JButton button = new JButton();
		int size = 70;
		
		BufferedImage am = null;
		BufferedImage circle = null;
		try {
			
			am = ImageIO.read(new File("images/am.png"));
			am.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			//gets circle image
			circle = ImageIO.read(new File("images/circle.png"));
			circle.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		colorCircle(circle);
        
		// create new image of icon image on top of circle image
        BufferedImage newImg = new BufferedImage(size, size,circle.getType());
        Graphics2D graphic = newImg.createGraphics();
        graphic.drawImage(circle, 0, 0, size, size, 0, 0, circle.getWidth(), circle.getHeight(), null);
        graphic.drawImage(am, 12, 7, size-7, size-13, 0, 0, am.getWidth(), am.getHeight(), null);
        graphic.dispose();
		
		//creates an ImageIcon
        ImageIcon icon = new ImageIcon(newImg);
        button.setIcon(icon);
        //make non-icon area of button invisible
        button.setContentAreaFilled(false);
        //remove button border
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(false);
        button.setMargin(new Insets(0,0,0,0));
		
		return button;
	}
	
	public BufferedImage colorIcon(BufferedImage image) {
		Color iconColor = Color.white;
		//get new red, green, blue values from color
		int red = iconColor.getRed();
		int green = iconColor.getGreen();
		int blue = iconColor.getBlue();
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
	
	/*
	 * adjusts color and/or opacity of circle image to specified color/opacity
	 */
	private BufferedImage colorCircle(BufferedImage image) {
		Color circleColor = aa_grey;
		//get new red, green, blue values from color
		int red = circleColor.getRed();
		int green = circleColor.getGreen();
		int blue = circleColor.getBlue();
		//get height and width of image to be altered
	    int width = image.getWidth();
	    int height = image.getHeight();
	    WritableRaster raster = image.getRaster();

	    //recolors image to new rgb values
	    for (int xx = 0; xx < width; xx++) {
	      for (int yy = 0; yy < height; yy++) {
	        int[] pixels = raster.getPixel(xx, yy, (int[]) null);
	        //rgb
	        pixels[0] = red;
	        pixels[1] = green;
	        pixels[2] = blue;
	        raster.setPixel(xx, yy, pixels);
	      }
	    }
	    return image;
	}
	
	
	private void rebuildPanel(DataBase db,CardLayout cardLayout,JPanel panel,JFrame frame) {
		JPanel new_child_panel = new JPanel();
		if(counter % 2 != 0) {
			new_child_panel = childPanel(db,cardLayout,panel,frame);
			panel.add("newIPanel",new_child_panel);
			cardLayout.show(panel, "newIPanel");
			panel.remove(child_panel);
		}else {
			child_panel = childPanel(db,cardLayout,panel,frame);
			panel.add("iPanel",child_panel);
			cardLayout.show(panel, "iPanel");
			panel.remove(new_child_panel);
		}
		counter++;
		panel.revalidate();
		panel.repaint();
		frame.revalidate();
		frame.repaint();
	}
	
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

		JLabel title = new JLabel("Account Management");
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
		guide.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//opens guide
        		Guide guide = new Guide();
				guide.open_Guide("PP Account Management");
        }});
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(225, 0)));
		title_panel.add(guide);
		title_panel.add(close_window);
		
		//returns panel
		return title_panel;
		
	}
	
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
}