package AttentionAssistant;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Negative_Thought_Burner {
	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	LineBorder line = new LineBorder(aa_purple, 2, true);
	private int mouseX;
	private int mouseY;
	int height = 600;
	int width = 600;
	 int x =195;
	Timer t;
	JButton toRefresh;
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

		JLabel title = new JLabel("Negative Thought Burner      ");
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
		guide.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//opens guide
        		Guide guide = new Guide();
				guide.open_Guide("Negative Thought Burner");
        }});
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(295, 0)));
		title_panel.add(guide);
		title_panel.add(close_window);
		
		//returns panel
		return title_panel;
		
	}

	private JLayeredPane welcomepage(CardLayout card,JFrame frame, Settings settings, Happy_Thought_Button happy_thought_button, DataBase db) {
		JLayeredPane  panel = new JLayeredPane();
		panel.setBackground(aa_grey);
		panel.setLayout(null);
		JButton burnbut=new JButton("Burn!");
		JLabel burn1 = new JLabel("Something bugging you?");
		JLabel burn2 = new JLabel("Watch it burn away!");

		burn1.setBounds(240, 50, 280, 100);
		burn1.setForeground(Color.WHITE);
		burn1.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
		panel.add(burn1);
		
		
		burn2.setBounds(260, 80, 280, 100);
		burn2.setForeground(Color.WHITE);
		burn2.setFont(new Font("Dosis SemiBold",Font.BOLD,20));
		panel.add(burn2);
		
		
		
		JTextArea firstnametext = new JTextArea();
		firstnametext.setBounds(100, 200, 500, 250);
	    firstnametext.setBackground(new Color(195,195,195));
	    firstnametext.setFont(new Font("Dosis SemiBold", Font.BOLD, 30));
        firstnametext.setLineWrap(true);
        firstnametext.setCaretColor(Color.red);
        firstnametext.setWrapStyleWord(true);
        panel.add(firstnametext);

        String path = "images/fire.gif";
		JPanel fire = generateNewGIF(path);
		fire.setVisible(false);
		panel.add(fire);
		panel.setPosition(fire, 0);
		
			JPanel fire2 = generateNewGIF2(path);
			fire2.setVisible(false);
			panel.add(fire2);
			//panel.setPosition(fire2, -1);

        burnbut.setBounds(300, 480, 97, 35);
        burnbut.setHorizontalTextPosition(SwingConstants.CENTER);
		 burnbut.setVerticalTextPosition(SwingConstants.CENTER);
		 burnbut.setFont(new Font("Dosis SemiBold", Font.BOLD, 17));
		 burnbut.setBorderPainted(false);
		 burnbut.setBackground(Color.WHITE);
		 burnbut.setForeground(aa_purple);
		 burnbut.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        		t = new Timer(50, new ActionListener() {
	        			  @Override
	        			  public void actionPerformed(ActionEvent arg0) {

	        				   if(e.getSource() == burnbut) {
	        					
	        					   fire.setVisible(true);
	        					  fire.setBackground(new Color(0,0,0,125)); 
	        					   fire.setOpaque(false);
	        					
	        					   
		        					
	        					   fire2.setVisible(true);
	        					   fire2.setBackground(new Color(0,0,0,125)); 
	        					   fire2.setOpaque(false);
	        					
	        					   firstnametext.setBackground(new Color(x--,x--,x--));
	        						
	        		                if(x==51 && settings.getIsAutoLinked() == false) { 
	        		                	
	        		                		t.stop();
		        		                	x=195;
		        		                	firstnametext.setVisible(true);
		        		                	firstnametext.setBackground(new Color(x,x,x));
		        		                	firstnametext.selectAll();
		    	        		       		firstnametext.replaceSelection("");
		    	        		       			
		    	        		       		fire2.setVisible(false);
		        		                	 fire.setVisible(false);
		        		                	//middle_panel.setVisible(false);
	        		                }
	        		                else if(x==51 && settings.getIsAutoLinked() == true) {
	        		                		int userID = settings.getUserID();
	        		                		
	        		                		t.stop();
		        		                	x=195;
		        		                	firstnametext.setVisible(true);
		        		                	firstnametext.setBackground(new Color(x,x,x));
		        		                	firstnametext.selectAll();
		    	        		       		firstnametext.replaceSelection("");
		    	        		       			
		    	        		       		fire2.setVisible(false);
		        		                	 fire.setVisible(false);
		        		                	
		        		                	happy_thought_button.open_htb(db, userID);
		        		    				Date timestamp = new Date();
		        		            		System.out.println(timestamp);
		        		            		db.AddEvent(userID, timestamp, "htb");
		        		                	
		        		            		frame.dispose();
	        		                	}
	        		                }
	        		               
	        		       		
	        		       		}
	        			  
	        			});
	        			t.start();
             
	        			//https://java-demos.blogspot.com/2013/10/fade-effect-for-jlabel-using-timer.html
	        }});
		 panel.add(burnbut);
		
			if(settings.getIsAutoLinked() == false) {
      
            	
            	
            	JButton Happythought=new JButton("Happy Thought Button");
            	Happythought.setBounds(10, 500, 230, 35);
            	Happythought.setHorizontalTextPosition(SwingConstants.CENTER);
            	Happythought.setVerticalTextPosition(SwingConstants.CENTER);
            	Happythought.setFont(new Font("Dosis SemiBold", Font.BOLD, 17));
            	Happythought.setBorderPainted(false);
            	Happythought.setForeground(Color.WHITE);
            	Happythought.setBackground(aa_purple);
            	Happythought.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		int userID = settings.getUserID();
                		happy_thought_button.open_htb(db, userID);
	    				Date timestamp = new Date();
	            		System.out.println(timestamp);
	            		db.AddEvent(userID, timestamp, "htb");
	                	
	            		frame.dispose();
                	
                }});
        		panel.add(Happythought);
			}
        //TODO if autolink is enabled in settings it opens right into htb 
		 
		 //onclik in ntb for htb button 
		 //one line in nav bar where htb is ran onclick
		 //loggin when its autolinked when it autoopens for onclick also
		return panel;
	}
	
	
	public JPanel generateNewGIF(String filepath) {
		JPanel mediaPanel = new JPanel();
	
	
		mediaPanel.setMaximumSize(new Dimension(500, 260));
		
		JLabel label = new JLabel();
		label.setMaximumSize(new Dimension(500, 260));
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(filepath).getImage().getScaledInstance(500, 260, Image.SCALE_FAST));
		label.setIcon(imageIcon);
		mediaPanel.setBounds(100, 200, 500, 250);
	//	mediaPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		mediaPanel.add(label);
		
		return mediaPanel;
	}

	
	public JPanel generateNewGIF2(String filepath) {
		JPanel mediaPanel = new JPanel();
	
	
		mediaPanel.setMaximumSize(new Dimension(500, 220));
		
		JLabel label = new JLabel();
		label.setMaximumSize(new Dimension(500, 220));
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(filepath).getImage().getScaledInstance(500, 220, Image.SCALE_FAST));
		label.setIcon(imageIcon);
		mediaPanel.setBounds(100, 200, 500, 220);
	//	mediaPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		mediaPanel.add(label);
		
		return mediaPanel;
	}

	JLayeredPane icon_panel = new JLayeredPane();
	int counter;
	public void run_ntb(Settings settings, Happy_Thought_Button htb, DataBase db) {
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run() {
				counter = 1;
				//set up frame
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				//sets window width and height
			
				CardLayout cardLayout = new CardLayout();
				JPanel panel = new JPanel();
				panel.setBackground(aa_grey);
				panel.setLayout(cardLayout);
				//build title panel
				JMenuBar titlePanel = titlePanel(frame);
				titlePanel.setBorder(line);
				//build table panel
				icon_panel = welcomepage(cardLayout,frame,settings,htb,db);
				//icon_panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
			    panel.add("PT", icon_panel);
			    cardLayout.show(panel, "iPanel");
			    panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
				frame.getContentPane().add(titlePanel,BorderLayout.PAGE_START);
				frame.getContentPane().add(panel,BorderLayout.CENTER);
				frame.setPreferredSize(new Dimension(700, 600)); 

				frame.pack();
				frame.setVisible(true);
				frame.setResizable(true);
				frame.setLocationRelativeTo(null);
				

				toRefresh = new JButton();
		        toRefresh.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		rebuildPanel(settings, db, cardLayout,panel, frame, settings, htb);
		        	}});
				
			}
		});
	}
	public void refresh(Settings settings){

		
		if (counter != 0 ) {
			toRefresh.doClick();
		}
	}
	public void rebuildPanel(Settings setting, DataBase db,CardLayout cardLayout,JPanel panel, JFrame frame, Settings settings, Happy_Thought_Button htb) {
		JLayeredPane new_icon_panel = new JLayeredPane();
		
		if(counter % 2 != 0) {
	
			new_icon_panel = welcomepage(cardLayout,frame,settings,htb,db);
			panel.add("newIPanel",new_icon_panel);
			cardLayout.show(panel, "newIPanel");
			panel.remove(icon_panel);
	
		}else {
			panel.remove(icon_panel);
			icon_panel = welcomepage(cardLayout,frame,settings,htb,db);
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
	
}