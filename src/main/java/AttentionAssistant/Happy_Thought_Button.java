package AttentionAssistant;

import java.util.ArrayList;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Class that contains information whenever Happy_Thought_Button
 * is called.
 * @author krchr
 */
public class Happy_Thought_Button {

	/*
	 * global variables for HTB GUI
	 */
	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	LineBorder line = new LineBorder(aa_purple, 2, true);
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = 400; 
	private int width = 625; 
	private int mouseX;
	private int mouseY;
	JButton dislikeMedia;
	JButton likeMedia;
	JButton flagMedia;
	int current = 0; 
	final static boolean shouldFill = true; 
	final static boolean shouldWeightX = true; 
	final static boolean RIGHT_TO_LEFT = false; 
	
	
	/**
	 * List of all Media objects held in the database 
	 */
	private ArrayList<Media> Media_List; 
	
	/**
	 * Instantiating empty Happy_Thought_Button object
	 * 
	 */
	public Happy_Thought_Button(DataBase db) {
		this.Media_List = new ArrayList<Media>();
	}

	
	/**
	 * Get Media_List
	 * @return ArrayList
	 */
	public ArrayList<Media> getMediaList() {
		return this.Media_List;
	}
	
	/**
	 * Set Media_List
	 * @param ArrayList
	 */
	public void setMediaList(ArrayList<Media> mediaList) {
		this.Media_List = mediaList;
	}
	
	/**
	 * creates an array of images to display
	 * 
	 * @author krchr
	 */
	public void getHappyMedia(Happy_Thought_Button htb, ArrayList<String> happyMedia) {		
		for(Media media : htb.Media_List) {
			if(media.getFlagged() != true && media.getRating() != 0){
				happyMedia.add(media.getMedia_ID_Tag());
			}
		}
	}
	
	/*
	 * create new Graphics for each image
	 * @author krchr
	 */
	public JPanel generateNewJPG(String filepath) {
		JPanel mediaPanel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				try {
					g.drawImage(ImageIO.read(new File(filepath)), 0, 0, 480, 360, this);
				}catch (IOException f) {
					f.printStackTrace();
					System.exit(1);
				}
			}
		};
		return mediaPanel;
	}
	
	public JPanel generateNewGIF(String filepath) {
		JPanel mediaPanel = new JPanel();
		mediaPanel.setLayout(new BoxLayout(mediaPanel, BoxLayout.X_AXIS)); 
		mediaPanel.setBackground(Color.black);
		mediaPanel.setMaximumSize(new Dimension(480, 360));
		
		JLabel label = new JLabel();
		label.setBackground(Color.black);
		label.setMaximumSize(new Dimension(480, 360));
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(filepath).getImage().getScaledInstance(420, 300, Image.SCALE_FAST));
		label.setIcon(imageIcon);
		mediaPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		mediaPanel.add(label);
		
		return mediaPanel;
	}
	
	public JMenuBar generateTitlePanel(JFrame htb_frame) {
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
				htb_frame.setLocation(htb_frame.getX() + e.getX() - mouseX, htb_frame.getY() + e.getY() - mouseY);
			}
		});
		
		title_panel.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});

		JLabel title = new JLabel("Happy Thought Button");
		title.setForeground(Color.white);
		title.setFont(new Font("Serif", Font.BOLD, 20));
		
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
        		htb_frame.dispose();
        	}
        });
		
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
				guide.open_Guide("Happy Thought Button");
        }});
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(290, 0)));
		title_panel.add(guide);
		title_panel.add(close_window);
		
		return title_panel;
	}
	
	public JPanel generateCenterPanel(CardLayout cardLayout, JPanel middle_panel, Happy_Thought_Button htb, ArrayList<String> happyMedia) {
		
		getHappyMedia(htb, happyMedia);
		
		BufferedImage leftScroll = null;
		BufferedImage rightScroll = null;
		
		try {
			leftScroll = ImageIO.read(new File("images/left_arrow.png")); 
			rightScroll = ImageIO.read(new File("images/right_arrow.png")); 
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		JPanel center_panel = new JPanel(new BorderLayout());
		center_panel.setBackground(Color.black);
		center_panel.setMaximumSize(new Dimension(500, 460));
		
		middle_panel.setBackground(Color.black);
		middle_panel.setMaximumSize(new Dimension(480, 360));
		middle_panel.setLayout(cardLayout);
		populateMiddlePanel(middle_panel, cardLayout, happyMedia); 
			
		JPanel left_panel = new JPanel();
		left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
		left_panel.setMaximumSize(new Dimension(40, 360));
		
		Image la_img = leftScroll.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Icon leftArrowIcon = new ImageIcon(la_img);
		JButton left = new JButton(leftArrowIcon); 
		left.setBorderPainted(false); 
		left.setContentAreaFilled(false);
		left.setFocusPainted(false); 
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reset thumbs up/down
				dislikeMedia.setBorderPainted(false);
				likeMedia.setBorderPainted(false);
				//scroll image to left	
				current--; 
				if(current >= 0) {
					populateMiddlePanel(middle_panel, cardLayout, happyMedia); 
				}else if(current < 0){
					UIManager.put("Button.foreground", aa_purple);
					UIManager.put("Button.background", aa_grey);
					UIManager.put("OptionPane.background", Color.black);
					UIManager.put("Panel.setOpaque", true);
					UIManager.put("Panel.background", aa_grey);
					UIManager.put("TextField.selectionBackground", Color.WHITE);
					UIManager.put("OptionPane.messageForeground", Color.WHITE);
					
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Scroll right to see more Happy Thoughts!");
					current++;
				}
			}
		});
		
		left_panel.add(Box.createRigidArea(new Dimension(0, 100))); 
		left_panel.add(left); 
		left_panel.setBackground(Color.black); 
		
		JPanel right_panel = new JPanel();
		right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
		right_panel.setMaximumSize(new Dimension(40, 360));
		
		Image ra_img = rightScroll.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Icon rightArrowIcon = new ImageIcon(ra_img);
		JButton right = new JButton(rightArrowIcon); 
		right.setBorderPainted(false); 
		right.setContentAreaFilled(false);
		right.setFocusPainted(false); 
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reset thumbs up/down
				dislikeMedia.setBorderPainted(false);
				likeMedia.setBorderPainted(false);
				//scroll image to the right
				current++;
				if(current < happyMedia.size()) {
					populateMiddlePanel(middle_panel, cardLayout, happyMedia); 
				}else if(current >= happyMedia.size()) {
					
					UIManager.put("Button.foreground", aa_purple);
					UIManager.put("Button.background", aa_grey);
					UIManager.put("OptionPane.background", Color.black);
					UIManager.put("Panel.setOpaque", true);
					UIManager.put("Panel.background", aa_grey);
					UIManager.put("TextField.selectionBackground", Color.WHITE);
					UIManager.put("OptionPane.messageForeground", Color.WHITE);
					
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Scroll left to see more Happy Thoughts!");
					current--;
				}
			}
		});
		
		right_panel.add(Box.createRigidArea(new Dimension(0, 100))); 
		right_panel.add(right); 
		right_panel.setBackground(Color.black);  
		
		center_panel.add(middle_panel, BorderLayout.CENTER); 
		center_panel.add(left_panel, BorderLayout.WEST); 
		center_panel.add(right_panel, BorderLayout.EAST);
		
		return center_panel;
	}
	
	public void populateMiddlePanel(JPanel middle_panel, CardLayout cardLayout, ArrayList<String> happyMedia) {
		
		JPanel media_panel = new JPanel();
		media_panel.setLayout(new BoxLayout(media_panel, BoxLayout.Y_AXIS));
		media_panel.setBackground(Color.black);
		media_panel.setMaximumSize(new Dimension(480, 360));
		
		String fileName = ""; 
		
		if(current < happyMedia.size()) {
			fileName = happyMedia.get(current);
		}else if(current == happyMedia.size()) {
			current--;
			fileName = happyMedia.get(current);
		}
		
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		
		if(fileExtension.equals("jpg")) {
			media_panel = generateNewJPG(fileName);
		}else if(fileExtension.equals("gif")) {
			media_panel = generateNewGIF(fileName);
		}
		
		middle_panel.add("Media", media_panel); 
		cardLayout.show(middle_panel, "Media"); 
	}
	
	public JPanel generateBottomPanel(JPanel middle_panel, CardLayout cardLayout, Happy_Thought_Button htb, DataBase db, ArrayList<String> happyMedia) {
		
		BufferedImage flag = null;
		BufferedImage thUp = null;
		BufferedImage thDwn = null;
		
		try {
			flag = ImageIO.read(new File("images/flag.png"));
			thUp = ImageIO.read(new File("images/thumb_up.png"));
			thDwn = ImageIO.read(new File("images/thumb_down.png")); 
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		JPanel bottom_panel = new JPanel(); 
		bottom_panel.setLayout(new BoxLayout(bottom_panel, BoxLayout.X_AXIS));
		bottom_panel.setBackground(Color.black);
		bottom_panel.setMaximumSize(new Dimension(480, 50));				
		
		Image thumbUp_img = thUp.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
		Icon thumbsUp = new ImageIcon(thumbUp_img);
		Image thumbDwn_img = thDwn.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
		Icon thumbsDown = new ImageIcon(thumbDwn_img);
		
		likeMedia = new JButton(thumbsUp);		
		likeMedia.setBorderPainted(false);
		likeMedia.setContentAreaFilled(false);
		likeMedia.setFocusPainted(false);
		likeMedia.setMaximumSize(new Dimension(35, 35)); 
		likeMedia.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//like media actions 
        		if(likeMedia.isBorderPainted() == false) {
        			dislikeMedia.setBorderPainted(false); 
        			likeMedia.setBorderPainted(true);
        			likeMedia.setBorder(new LineBorder(Color.GREEN));
        			for(Media media : htb.Media_List) {
            			if(media.getMedia_ID_Tag() == happyMedia.get(current)) {
            				media.setRating(2); 
            				db.UpdateMedia(media);
            			}
            		}
        		}else if(likeMedia.isBorderPainted() == true) {
        			likeMedia.setBorderPainted(false);
        			for(Media media : htb.Media_List) {
            			if(media.getMedia_ID_Tag() == happyMedia.get(current)) {
            				media.setRating(1); 
            				db.UpdateMedia(media);
            			}
            		}
        		}
        	}
        });
		
		dislikeMedia = new JButton(thumbsDown);
		dislikeMedia.setBorderPainted(false);
		dislikeMedia.setContentAreaFilled(false);
		dislikeMedia.setFocusPainted(false);
		dislikeMedia.setMaximumSize(new Dimension(35, 35)); 
		dislikeMedia.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		/////////////////////////
        		
        		UIManager.put("Button.foreground", aa_purple);
				UIManager.put("Button.background", aa_grey);
				UIManager.put("OptionPane.background", Color.black);
				UIManager.put("Panel.setOpaque", true);
				UIManager.put("Panel.background", aa_grey);
				UIManager.put("TextField.selectionBackground", Color.WHITE);
				UIManager.put("OptionPane.messageForeground", Color.WHITE);
        		
				JLabel dislikeLabel = new JLabel("<html><center>Sorry you didn't like this one! It will no longer appear in the Happy Thought Button.</center></html>");
				dislikeLabel.setFont(new Font("Serif", Font.BOLD, 16));
				dislikeLabel.setForeground(Color.white);
				
				int response = JOptionPane.showConfirmDialog(null, dislikeLabel, "Dislike Media", JOptionPane.OK_CANCEL_OPTION);
				switch (response) {
				case JOptionPane.OK_OPTION:
					for(Media media : htb.Media_List) {
	        			if(media.getMedia_ID_Tag().equals(happyMedia.get(current))) {
	        				media.setRating(0); 
            				db.UpdateMedia(media);
	        			}
					}	       
	        		
					/**
					 * flip to next image once current media is flagged
					 * unless its last image, then flip to previous
					 */
					if(current < happyMedia.size() - 1) {
						current++;
						populateMiddlePanel(middle_panel, cardLayout, happyMedia); 
						happyMedia.remove(current - 1);
					}else if(current >= happyMedia.size() - 1) {
						current--;
						populateMiddlePanel(middle_panel, cardLayout, happyMedia); 
						happyMedia.remove(current + 1);
					}
				case JOptionPane.CANCEL_OPTION:
					break;
        		}
				
				
        		
        		/**
        		 * old method of dislike rating without removing from view
        		
        		//dislike media actions
        		if(dislikeMedia.isBorderPainted() == false) {
        			likeMedia.setBorderPainted(false); 
        			dislikeMedia.setBorderPainted(true);
        			dislikeMedia.setBorder(new LineBorder(Color.RED));
        			for(Media media : htb.Media_List) {
            			if(media.getMedia_ID_Tag() == happyMedia.get(current)) {
            				media.setRating(0); 
            				db.UpdateMedia(media);
            			}
            		}
        		}else if(dislikeMedia.isBorderPainted() == true) {
        			dislikeMedia.setBorderPainted(false);
        			for(Media media : htb.Media_List) {
            			if(media.getMedia_ID_Tag() == happyMedia.get(current)) {
            				media.setRating(1); 
            				db.UpdateMedia(media);
            			}
            		}
        		}
        		*/
        	}
        });
		
		Image flag_img = flag.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		Icon flagIcon = new ImageIcon(flag_img);
		
		flagMedia = new JButton(flagIcon);
		flagMedia.setBorderPainted(false);
		flagMedia.setContentAreaFilled(false);
		flagMedia.setFocusPainted(false);
		flagMedia.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//flag media actions 
        		UIManager.put("Button.foreground", aa_purple);
				UIManager.put("Button.background", aa_grey);
				UIManager.put("OptionPane.background", Color.black);
				UIManager.put("Panel.setOpaque", true);
				UIManager.put("Panel.background", aa_grey);
				UIManager.put("TextField.selectionBackground", Color.WHITE);
				UIManager.put("OptionPane.messageForeground", Color.WHITE);
        		
        		//JFrame flagFrame = new JFrame();
        		//JOptionPane.showMessageDialog(flagFrame,"Media has been flagged! It will no longer appear in the Happy Thought Button.","Alert",JOptionPane.WARNING_MESSAGE);
				JLabel flagLabel = new JLabel("<html><center>Media has been flagged! It will no longer appear in the Happy Thought Button.</center></html>");
				flagLabel.setFont(new Font("Serif", Font.BOLD, 16));
				flagLabel.setForeground(Color.white);
				
				int response = JOptionPane.showConfirmDialog(null, flagLabel, "Flag Media", JOptionPane.OK_CANCEL_OPTION);
				switch (response) {
				case JOptionPane.OK_OPTION:
					for(Media media : htb.Media_List) {
	        			if(media.getMedia_ID_Tag().equals(happyMedia.get(current))) {
	        				media.setFlagged(true); 
	        				db.UpdateMedia(media);
	        			}
					}	       
	        		
					/**
					 * flip to next image once current media is flagged
					 * unless its last image, then flip to previous
					 */
					if(current < happyMedia.size() - 1) {
						current++;
						populateMiddlePanel(middle_panel, cardLayout, happyMedia); 
						happyMedia.remove(current - 1);
					}else if(current >= happyMedia.size() - 1) {
						current--;
						populateMiddlePanel(middle_panel, cardLayout, happyMedia); 
						happyMedia.remove(current + 1);
					}
				case JOptionPane.CANCEL_OPTION:
					break;
        		}
        	}
        });
		
		bottom_panel.add(Box.createRigidArea(new Dimension(235, 0)));
		bottom_panel.add(likeMedia); 
		bottom_panel.add(Box.createRigidArea(new Dimension(50, 0)));
		bottom_panel.add(dislikeMedia); 
		bottom_panel.add(Box.createRigidArea(new Dimension(145, 0))); 	
		bottom_panel.add(flagMedia);
		
		return bottom_panel; 
	}
	
	public JPanel generateMediaViewer(JMenuBar title_panel, JPanel center_panel, JPanel bottom_panel) {
		
		JPanel media_viewer = new JPanel(new BorderLayout());
		media_viewer.setBackground(Color.black);
		media_viewer.setMaximumSize(new Dimension(width, height));
		
		media_viewer.add(title_panel, BorderLayout.PAGE_START); 
		media_viewer.add(center_panel, BorderLayout.CENTER);
		media_viewer.add(bottom_panel, BorderLayout.PAGE_END);
		
		return media_viewer; 
	}
	
	/**
	 * creates/displays HTB GUI
	 * @author krchr
	 */
	public void open_htb(DataBase db, int userID) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				ArrayList<String> happyMedia = new ArrayList<String>();
				Happy_Thought_Button htb = new Happy_Thought_Button(db); 
				htb.setMediaList(db.SelectAllMedias(userID)); 
				
				CardLayout cardLayout = new CardLayout();
				JPanel middle_panel = new JPanel(); 
				
				JFrame htb_frame = new JFrame("Happy Thought Button");
				htb_frame.setUndecorated(true);
				htb_frame.setPreferredSize(new Dimension(width, height));
							
				JMenuBar title_panel = generateTitlePanel(htb_frame);				
				JPanel center_panel = generateCenterPanel(cardLayout, middle_panel, htb, happyMedia);
				JPanel bottom_panel = generateBottomPanel(middle_panel, cardLayout, htb, db, happyMedia);
				JPanel media_viewer = generateMediaViewer(title_panel, center_panel, bottom_panel);
				media_viewer.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aa_purple));
				
				/*
				 * adds main panel to frame
				 */
				htb_frame.getContentPane().add(media_viewer); 
				htb_frame.getContentPane().setBackground(Color.black);
				htb_frame.pack();
				htb_frame.setAlwaysOnTop(false);
				htb_frame.setVisible(true);
				htb_frame.setResizable(true);
				htb_frame.setLocationRelativeTo(null);
			}
		});
	}
}