package AttentionAssistant;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboPopup;

public class Guide {
	
	static Color aa_grey = new Color(51,51,51);
	static Color aa_purple = new Color(137,31,191);
	static LineBorder line = new LineBorder(aa_purple, 2, true);
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = 550; 
	private int width = 900; 
	private static int mouseX;
	private static int mouseY;
	final static boolean shouldFill = true; 
	final static boolean shouldWeightX = true; 
	final static boolean RIGHT_TO_LEFT = false; 
	int current = 0;
	
	/**
	 * creates title bar
	 */
	private JMenuBar createTitlePanel(Frame pr_frame, CardLayout guideCards, JPanel guideViews, String guideCard) {
		
		JMenuBar title_panel = new JMenuBar();
		title_panel.setBorder(line);
		title_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));	
		title_panel.setBackground(aa_grey);
		title_panel.setBorder(BorderFactory.createLineBorder(aa_purple));
		title_panel.setMaximumSize(new Dimension(800, 20));
		
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

		JLabel title = new JLabel("Guide");
		title.setForeground(Color.white);
		title.setFont(new Font("Serif", Font.BOLD, 25));
		
		/*
		 * create icons to use as buttons for title bar
		 */
		BufferedImage ci = null;
		
		try {
			ci = ImageIO.read(new File("images/exit_circle.png"));
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
		
		JPanel selectionPanel = createGuideSelectionPanel(guideCards, guideViews, guideCard); 
		
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(20, 0)));
		title_panel.add(selectionPanel);
		title_panel.add(Box.createRigidArea(new Dimension(475, 0)));
		title_panel.add(close_window);
		
		return title_panel;
		
	}
	
	private JPanel createGuideSelectionPanel(CardLayout guideCards, JPanel guideViews, String guideCard) {
		
		JPanel selectionPanel = new JPanel(); 
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS)); 
		selectionPanel.setBackground(aa_grey); 
		selectionPanel.setMaximumSize(new Dimension(300, 25)); 
		
		String guideOptions[] = {"Navigation Bar", "Settings", "Priority Manager", "Pomodoro Timer", "Negative Thought Burner", "Happy Thought Button", "Free Thought Space", "Progress Report", "Notification System", "Child Selection Bar", "Parent Portal", "PP Account Management"};
		
		int index = 0; 
		
		JComboBox<String> guideDropDown = new JComboBox<>(guideOptions);
		guideDropDown.setEnabled(true); 
		AccessibleContext accessCont = guideDropDown.getAccessibleContext(); 
		BasicComboPopup pop = (BasicComboPopup) accessCont.getAccessibleChild(0); 
		JList guideList = pop.getList(); 
		guideList.setSelectionForeground(Color.WHITE); 
		guideList.setSelectionBackground(aa_purple); 
		guideDropDown.setBackground(Color.black);
		guideDropDown.setForeground(Color.white);
		for(String guideTitle : guideOptions) {
			if(guideTitle.equals(guideCard)) {
				guideDropDown.setSelectedIndex(index);
			}else {
				index++;
			}
		}
		guideDropDown.setFont(new Font("Serif", Font.BOLD, 18));
		((JLabel)guideDropDown.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
		guideDropDown.setMaximumSize(new Dimension(300,25));
		guideDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//change card layout based on this selection 
				guideCards.show(guideViews, (String) guideDropDown.getSelectedItem());
				current = 0; 
				
			} 
		});
		
		selectionPanel.add(guideDropDown);
		
		return selectionPanel; 
	}
	
	private JPanel createSoloImagePanel(String filepath, int sizeX, int sizeY, boolean multiple, boolean description) {
		
		JPanel imagePanel = new JPanel(); 
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); 
		imagePanel.setBackground(Color.BLACK); 
		imagePanel.setPreferredSize(new Dimension(sizeX, sizeY)); 
		
		JPanel image = new JPanel() {
			@Override
			public void paint(Graphics g) {
				try {
					g.drawImage(ImageIO.read(new File(filepath)), 0, 0, sizeX, sizeY, this);
				}catch (IOException f) {
					f.printStackTrace();
					System.exit(1);
				}
			}
		};
		
		if(multiple) { 
			imagePanel.add(Box.createRigidArea(new Dimension(0, 15)));
		}else if(multiple == false && description == false){
			imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS)); 
			imagePanel.add(Box.createRigidArea(new Dimension(50, 0)));
			imagePanel.setPreferredSize(new Dimension(sizeX + 90, sizeY)); 
		}
		
		imagePanel.add(image); 
		
		return imagePanel;
	}
	
	private JPanel createScrollableImagePanel(ArrayList<String> guideImages, int sizeX, int sizeY) {
		
		BufferedImage leftScroll = null;
		BufferedImage rightScroll = null;
		
		try {
			leftScroll = ImageIO.read(new File("images/left_arrow.png")); 
			rightScroll = ImageIO.read(new File("images/right_arrow.png")); 
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		JPanel scrollableImagePanel = new JPanel(new BorderLayout());
		scrollableImagePanel.setBackground(Color.black);
		scrollableImagePanel.setMaximumSize(new Dimension(500, 600));
		
		CardLayout cardLayout = new CardLayout(); 
		JPanel middle_panel = new JPanel(); 
		middle_panel.setBackground(Color.black);
		middle_panel.setMaximumSize(new Dimension(450, 600));
		middle_panel.setLayout(cardLayout);
		populateMiddlePanel(middle_panel, cardLayout, guideImages, sizeX, sizeY); 
			
		JPanel left_panel = new JPanel();
		left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
		left_panel.setMaximumSize(new Dimension(20, 600));
		
		Image la_img = leftScroll.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		Icon leftArrowIcon = new ImageIcon(la_img);
		JButton left = new JButton(leftArrowIcon); 
		left.setBorderPainted(false); 
		left.setContentAreaFilled(false);
		left.setFocusPainted(false); 
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//scroll image to left	
				current--; 
				if(current >= 0) {
					populateMiddlePanel(middle_panel, cardLayout, guideImages, sizeX, sizeY); 
				}else if(current < 0){
					UIManager.put("Button.foreground", aa_purple);
					UIManager.put("Button.background", aa_grey);
					UIManager.put("OptionPane.background", Color.black);
					UIManager.put("Panel.setOpaque", true);
					UIManager.put("Panel.background", aa_grey);
					UIManager.put("TextField.selectionBackground", Color.WHITE);
					UIManager.put("OptionPane.messageForeground", Color.WHITE);
					
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Scroll right for more guides!");
					current++;
				}
			}
		});
		
		left_panel.add(Box.createRigidArea(new Dimension(0, 250))); 
		left_panel.add(left); 
		left_panel.setBackground(Color.black); 
		
		JPanel right_panel = new JPanel();
		right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
		right_panel.setMaximumSize(new Dimension(20, 600));
		
		Image ra_img = rightScroll.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		Icon rightArrowIcon = new ImageIcon(ra_img);
		JButton right = new JButton(rightArrowIcon); 
		right.setBorderPainted(false); 
		right.setContentAreaFilled(false);
		right.setFocusPainted(false); 
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//scroll image to the right
				current++;
				if(current < guideImages.size()) {
					populateMiddlePanel(middle_panel, cardLayout, guideImages, sizeX, sizeY); 
				}else if(current >= guideImages.size()) {
					
					UIManager.put("Button.foreground", aa_purple);
					UIManager.put("Button.background", aa_grey);
					UIManager.put("OptionPane.background", Color.black);
					UIManager.put("Panel.setOpaque", true);
					UIManager.put("Panel.background", aa_grey);
					UIManager.put("TextField.selectionBackground", Color.WHITE);
					UIManager.put("OptionPane.messageForeground", Color.WHITE);
					
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Scroll left for more guides!");
					current--;
				}
			}
		});
		
		right_panel.add(Box.createRigidArea(new Dimension(0, 250))); 
		right_panel.add(right); 
		right_panel.setBackground(Color.black);  
		
		scrollableImagePanel.add(middle_panel, BorderLayout.CENTER); 
		scrollableImagePanel.add(left_panel, BorderLayout.WEST); 
		scrollableImagePanel.add(right_panel, BorderLayout.EAST);
		
		return scrollableImagePanel; 
	}
	
	public void populateMiddlePanel(JPanel middle_panel, CardLayout cardLayout, ArrayList<String> guideImages, int sizeX, int sizeY) {
		
		JPanel image_panel = new JPanel();
		
		String fileName = ""; 
		
		if(current < guideImages.size()) {
			fileName = guideImages.get(current);
		}else if(current == guideImages.size()) {
			current--;
			fileName = guideImages.get(current);
		}
		
		image_panel = createSoloImagePanel(fileName, sizeX, sizeY, true, false);
		
		middle_panel.add("Guide", image_panel); 
		cardLayout.show(middle_panel, "Guide"); 
	}
	
	private JPanel createButtonsColumnPanel(int counter, JPanel descriptionPanel, CardLayout descriptionCards, String descriptionName) {
		
		JPanel buttonsColumnPanel = new JPanel(); 
		buttonsColumnPanel.setLayout(new BoxLayout(buttonsColumnPanel, BoxLayout.Y_AXIS)); 
		buttonsColumnPanel.setMaximumSize(new Dimension(90, 600)); 
		buttonsColumnPanel.setBackground(aa_grey); 
		buttonsColumnPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); 
		buttonPanel.setMaximumSize(new Dimension(90, 600));
		buttonPanel.setBackground(aa_grey); 
		
		for(int i = 1; i <= counter; i++) {
			
			String cardDescription = descriptionName + i; 
			String filepath = "guideImages/numbers/" + i + ".png"; 
			
			BufferedImage img = null; 
			try {
				img = ImageIO.read(new File(filepath)); 
				img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); 
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1); 
			}
			
			Image numImg = img; 
			Icon number = new ImageIcon(numImg);
			JButton numberButton = new JButton(number);
			numberButton.setBorderPainted(false);
			numberButton.setContentAreaFilled(false);
			numberButton.setFocusPainted(false);
			numberButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					descriptionCards.show(descriptionPanel, cardDescription); 
				}
			});
			
			buttonPanel.add(numberButton);
		}
		
		JScrollPane buttonScroll = new JScrollPane(buttonPanel); 
		buttonScroll.setBackground(aa_grey); 
		Border empty = new EmptyBorder(0,0,0,0);
		buttonScroll.setBorder(empty);
		buttonScroll.setMaximumSize(new Dimension(105, 500)); 
		
		buttonsColumnPanel.add(buttonScroll);
		
		return buttonsColumnPanel; 
	}
	
	private JPanel createDescriptionPanel(CardLayout descriptionCards, int counter, String descriptionName, String directory) {
		
		JPanel descriptionMasterPanel = new JPanel(); 
		descriptionMasterPanel.setLayout(descriptionCards); 
		descriptionMasterPanel.setBackground(Color.WHITE);
		descriptionMasterPanel.setMaximumSize(new Dimension(200, 600)); 
		
		for(int i = 1; i <= counter; i++) {
			
			String cardDescription = descriptionName + i; 
			String filepath = directory + i + ".png";
			
			JPanel descriptionPanel = createSoloImagePanel(filepath, 200, 500, false, true); 
			descriptionPanel.setBackground(Color.WHITE);
			descriptionMasterPanel.add(cardDescription, descriptionPanel); 
		}
		
		return descriptionMasterPanel; 
	}
	
	private JPanel createNavBarGuidePanel() {
		
		JPanel nbGuide = new JPanel(new BorderLayout()); 
		nbGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/nav/navBar.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int navBarCounter = 7; 
		String navBarDirectory = "guideImages/nav/nav"; 
		String navBarDescription = "navBarDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout navBarDescriptionCards = new CardLayout(); 
		JPanel navBarDescriptionPanel = createDescriptionPanel(navBarDescriptionCards, navBarCounter, navBarDescription, navBarDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(navBarCounter, navBarDescriptionPanel, navBarDescriptionCards, navBarDescription); 
		
		nbGuide.add(imagePanel, BorderLayout.WEST);
		nbGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		nbGuide.add(navBarDescriptionPanel, BorderLayout.EAST); 
		
		return nbGuide; 
	}
	
	private JPanel createSettingsGuidePanel() {
		
		JPanel settingsGuide = new JPanel(new BorderLayout()); 
		settingsGuide.setBackground(Color.WHITE); 
		
		int settingsNumberCounter = 28; 
		String settingsDirectory = "guideImages/settings/settings"; 
		String settingsDescription = "settingsDescription";
		ArrayList<String> settingsGuideImages = new ArrayList<String>();
		settingsGuideImages.add("guideImages/settings/settings.png");
		settingsGuideImages.add("guideImages/settings/settingsGeneral.png");
		settingsGuideImages.add("guideImages/settings/settingsNotif.png");
		settingsGuideImages.add("guideImages/settings/settingsPM.png");
		settingsGuideImages.add("guideImages/settings/settingsPomo.png");
		settingsGuideImages.add("guideImages/settings/settingsThought.png");
		
		//create left side image panel
		JPanel imagePanel = createScrollableImagePanel(settingsGuideImages, 470, 470); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		//create right side description panel w/cardlayout
		CardLayout settingsDescriptionCards = new CardLayout(); 
		JPanel settingsDescriptionPanel = createDescriptionPanel(settingsDescriptionCards, settingsNumberCounter, settingsDescription, settingsDirectory); 
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(settingsNumberCounter, settingsDescriptionPanel, settingsDescriptionCards, settingsDescription); 
		
		settingsGuide.add(imagePanel, BorderLayout.WEST);
		settingsGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		settingsGuide.add(settingsDescriptionPanel, BorderLayout.EAST); 
		
		return settingsGuide; 
	}
	
	private JPanel createPMGuidePanel() {
		
		JPanel pmGuide = new JPanel(new BorderLayout()); 
		pmGuide.setBackground(Color.WHITE); 
		
		int pmNumberCounter = 16; 
		String pmDirectory = "guideImages/pm/pm"; 
		String pmDescription = "pmDescription";
		ArrayList<String> pmGuideImages = new ArrayList<String>();
		pmGuideImages.add("guideImages/pm/pm.png");
		pmGuideImages.add("guideImages/pm/taskWindow.png");
		
		//create left side image panel
		JPanel imagePanel = createScrollableImagePanel(pmGuideImages, 470, 470); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		//create right side description panel w/cardlayout
		CardLayout pmDescriptionCards = new CardLayout(); 
		JPanel pmDescriptionPanel = createDescriptionPanel(pmDescriptionCards, pmNumberCounter, pmDescription, pmDirectory); 
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(pmNumberCounter, pmDescriptionPanel, pmDescriptionCards, pmDescription); 
		
		pmGuide.add(imagePanel, BorderLayout.WEST);
		pmGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		pmGuide.add(pmDescriptionPanel, BorderLayout.EAST); 
		
		return pmGuide; 
	}
	
	private JPanel createPTGuidePanel() {
		
		JPanel ptGuide = new JPanel(new BorderLayout()); 
		ptGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/pomo/pomo.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int pomoCounter = 4; 
		String pomoDirectory = "guideImages/pomo/pomo"; 
		String pomoDescription = "pomoDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout pomoDescriptionCards = new CardLayout(); 
		JPanel pomoDescriptionPanel = createDescriptionPanel(pomoDescriptionCards, pomoCounter, pomoDescription, pomoDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(pomoCounter, pomoDescriptionPanel, pomoDescriptionCards, pomoDescription); 
		
		ptGuide.add(imagePanel, BorderLayout.WEST);
		ptGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		ptGuide.add(pomoDescriptionPanel, BorderLayout.EAST); 
		
		return ptGuide; 
	}
	
	private JPanel createNTBGuidePanel() {
		
		JPanel ntbGuide = new JPanel(new BorderLayout()); 
		ntbGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/ntb/NTB.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int ntbCounter = 2; 
		String ntbDirectory = "guideImages/ntb/ntb"; 
		String ntbDescription = "ntbDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout ntbDescriptionCards = new CardLayout(); 
		JPanel ntbDescriptionPanel = createDescriptionPanel(ntbDescriptionCards, ntbCounter, ntbDescription, ntbDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(ntbCounter, ntbDescriptionPanel, ntbDescriptionCards, ntbDescription); 
		
		ntbGuide.add(imagePanel, BorderLayout.WEST);
		ntbGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		ntbGuide.add(ntbDescriptionPanel, BorderLayout.EAST); 
		
		return ntbGuide; 
	}
	
	private JPanel createHTBGuidePanel() {
		
		JPanel htbGuide = new JPanel(new BorderLayout()); 
		htbGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/htb/HTB.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int htbCounter = 5; 
		String htbDirectory = "guideImages/htb/htb"; 
		String htbDescription = "htbDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout htbDescriptionCards = new CardLayout(); 
		JPanel htbDescriptionPanel = createDescriptionPanel(htbDescriptionCards, htbCounter, htbDescription, htbDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(htbCounter, htbDescriptionPanel, htbDescriptionCards, htbDescription); 
		
		htbGuide.add(imagePanel, BorderLayout.WEST);
		htbGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		htbGuide.add(htbDescriptionPanel, BorderLayout.EAST); 
		
		return htbGuide; 
	}
	
	private JPanel createFTSGuidePanel() {
		
		JPanel ftsGuide = new JPanel(new BorderLayout()); 
		ftsGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/fts/FTS.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int ftsCounter = 17; 
		String ftsDirectory = "guideImages/fts/fts"; 
		String ftsDescription = "ftsDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout ftsDescriptionCards = new CardLayout(); 
		JPanel ftsDescriptionPanel = createDescriptionPanel(ftsDescriptionCards, ftsCounter, ftsDescription, ftsDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(ftsCounter, ftsDescriptionPanel, ftsDescriptionCards, ftsDescription); 
		
		ftsGuide.add(imagePanel, BorderLayout.WEST);
		ftsGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		ftsGuide.add(ftsDescriptionPanel, BorderLayout.EAST); 
		
		return ftsGuide; 
	}
	
	private JPanel createPRGuidePanel() {
		
		JPanel prGuide = new JPanel(new BorderLayout()); 
		prGuide.setBackground(Color.WHITE); 
		
		int prNumberCounter = 9; 
		String prDirectory = "guideImages/pr/pr"; 
		String prDescription = "prDescription";
		ArrayList<String> prGuideImages = new ArrayList<String>();
		prGuideImages.add("guideImages/pr/prSummary.png");
		prGuideImages.add("guideImages/pr/prTaskAdded.png");
		prGuideImages.add("guideImages/pr/prTaskCompleted.png");
		prGuideImages.add("guideImages/pr/prGraph.png");
		
		//create left side image panel
		JPanel imagePanel = createScrollableImagePanel(prGuideImages, 470, 470); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		//create right side description panel w/cardlayout
		CardLayout prDescriptionCards = new CardLayout(); 
		JPanel prDescriptionPanel = createDescriptionPanel(prDescriptionCards, prNumberCounter, prDescription, prDirectory); 
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(prNumberCounter, prDescriptionPanel, prDescriptionCards, prDescription); 
		
		prGuide.add(imagePanel, BorderLayout.WEST);
		prGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		prGuide.add(prDescriptionPanel, BorderLayout.EAST); 
		
		return prGuide; 
	}
	
	private JPanel createNSGuidePanel() {
		
		JPanel nsGuide = new JPanel(new BorderLayout()); 
		nsGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/notif/notification.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int nsCounter = 3; 
		String nsDirectory = "guideImages/notif/not"; 
		String nsDescription = "nsDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout nsDescriptionCards = new CardLayout(); 
		JPanel nsDescriptionPanel = createDescriptionPanel(nsDescriptionCards, nsCounter, nsDescription, nsDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(nsCounter, nsDescriptionPanel, nsDescriptionCards, nsDescription); 
		
		nsGuide.add(imagePanel, BorderLayout.WEST);
		nsGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		nsGuide.add(nsDescriptionPanel, BorderLayout.EAST); 
		
		return nsGuide; 
	}
	
	private JPanel createCSBGuidePanel() {
		
		JPanel csbGuide = new JPanel(new BorderLayout()); 
		csbGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/cs/SelectionBar.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int csbCounter = 2; 
		String csbDirectory = "guideImages/cs/cs"; 
		String csbDescription = "csbDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout csbDescriptionCards = new CardLayout(); 
		JPanel csbDescriptionPanel = createDescriptionPanel(csbDescriptionCards, csbCounter, csbDescription, csbDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(csbCounter, csbDescriptionPanel, csbDescriptionCards, csbDescription); 
		
		csbGuide.add(imagePanel, BorderLayout.WEST);
		csbGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		csbGuide.add(csbDescriptionPanel, BorderLayout.EAST); 
		
		return csbGuide; 
	}
	
	private JPanel createPPGuidePanel() {
		
		JPanel parentPortalGuide = new JPanel(new BorderLayout()); 
		parentPortalGuide.setBackground(Color.WHITE); 
		
		int parentPortalNumberCounter = 15; 
		String parentPortalDirectory = "guideImages/pp/pp"; 
		String parentPortalDescription = "ppDescription";
		ArrayList<String> parentPortalGuideImages = new ArrayList<String>();
		parentPortalGuideImages.add("guideImages/pp/parentPortal.png");
		parentPortalGuideImages.add("guideImages/pp/parentPortalGeneral.png");
		parentPortalGuideImages.add("guideImages/pp/parentPortalOnTask.png");
		parentPortalGuideImages.add("guideImages/pp/parentPortalOffTask.png");
		
		//create left side image panel
		JPanel imagePanel = createScrollableImagePanel(parentPortalGuideImages, 470, 470); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		//create right side description panel w/cardlayout
		CardLayout parentPortalDescriptionCards = new CardLayout(); 
		JPanel parentPortalDescriptionPanel = createDescriptionPanel(parentPortalDescriptionCards, parentPortalNumberCounter, parentPortalDescription, parentPortalDirectory); 
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(parentPortalNumberCounter, parentPortalDescriptionPanel, parentPortalDescriptionCards, parentPortalDescription); 
		
		parentPortalGuide.add(imagePanel, BorderLayout.WEST);
		parentPortalGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		parentPortalGuide.add(parentPortalDescriptionPanel, BorderLayout.EAST); 
		
		return parentPortalGuide; 
	}
	
	private JPanel createPPAMGuidePanel() {
		
		JPanel ppamGuide = new JPanel(new BorderLayout()); 
		ppamGuide.setBackground(Color.WHITE); 
		
		//create left side image panel
		JPanel imagePanel = createSoloImagePanel("guideImages/pam/parentAccountManage.png", 500, 500, false, false); 
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, aa_purple));
		
		int ppamCounter = 3; 
		String ppamDirectory = "guideImages/pam/pam"; 
		String ppamDescription = "ppamDescription"; 
		
		//create right side description panel w/cardlayout
		CardLayout ppamDescriptionCards = new CardLayout(); 
		JPanel ppamDescriptionPanel = createDescriptionPanel(ppamDescriptionCards, ppamCounter, ppamDescription, ppamDirectory);
		
		//create middle drop down and scroll panels
		JPanel buttonsColumnPanel = createButtonsColumnPanel(ppamCounter, ppamDescriptionPanel, ppamDescriptionCards, ppamDescription); 
		
		ppamGuide.add(imagePanel, BorderLayout.WEST);
		ppamGuide.add(buttonsColumnPanel, BorderLayout.CENTER);
		ppamGuide.add(ppamDescriptionPanel, BorderLayout.EAST); 
		
		return ppamGuide; 
	}
	
	private JPanel createCenterPanel(CardLayout guideCards, JPanel guideViews, String guideCard) {
		
		JPanel center_panel = new JPanel(); 
		center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.Y_AXIS));
		center_panel.setBackground(aa_grey);
		
		//add card layout that will toggle between different user guide screens
		guideViews.setLayout(guideCards);
		guideViews.setBackground(aa_grey);	
		
		//add navigation bar guide panel 		
		JPanel nbGuide = createNavBarGuidePanel(); 
		guideViews.add("Navigation Bar", nbGuide); 
		
		//add settings guide panel
		JPanel settingsGuide = createSettingsGuidePanel();
		guideViews.add("Settings", settingsGuide); 
		
		// add priority manager guide panel 
		JPanel pmGuide = createPMGuidePanel(); 
		guideViews.add("Priority Manager", pmGuide); 
		
		// add pomodoro timer guide panel
		JPanel ptGuide = createPTGuidePanel(); 
		guideViews.add("Pomodoro Timer", ptGuide); 
		
		//add ntb guide panel 
		JPanel ntbGuide = createNTBGuidePanel(); 
		guideViews.add("Negative Thought Burner", ntbGuide); 
		
		//add htb guide panel 
		JPanel htbGuide = createHTBGuidePanel(); 
		guideViews.add("Happy Thought Button", htbGuide); 
		
		//add fts guide panel 
		JPanel ftsGuide = createFTSGuidePanel(); 
		guideViews.add("Free Thought Space", ftsGuide); 
		
		//add progress report guide panel
		JPanel prGuide = createPRGuidePanel(); 
		guideViews.add("Progress Report", prGuide); 
		
		// add notification system guide panel 
		JPanel nsGuide = createNSGuidePanel(); 
		guideViews.add("Notification System", nsGuide); 
		
		// add child selection bar guide panel
		JPanel csbGuide = createCSBGuidePanel(); 
		guideViews.add("Child Selection Bar", csbGuide); 
		
		// add parent portal guide panel
		JPanel ppGuide = createPPGuidePanel(); 
		guideViews.add("Parent Portal", ppGuide); 
		
		// add pp account management guide panel 
		JPanel ppamGuide = createPPAMGuidePanel(); 
		guideViews.add("PP Account Management", ppamGuide); 
		
		guideCards.show(guideViews, guideCard);
		
		center_panel.add(guideViews); 
		return center_panel; 
	}

	public void open_Guide(String guideCard) {
		EventQueue.invokeLater(new Runnable() {
			@Override 
			public void run() {
			
				JFrame guide_frame = new JFrame("Guide");
				
				guide_frame.setUndecorated(true);
				guide_frame.setPreferredSize(new Dimension(width, height));
				
				JPanel masterPanel = new JPanel(new BorderLayout()); 
				masterPanel.setBackground(aa_grey); 
				masterPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aa_purple));
				
				CardLayout guideCards = new CardLayout();
				JPanel guideViews = new JPanel(); 
				
				JMenuBar title_panel = createTitlePanel(guide_frame, guideCards, guideViews, guideCard); 
				JPanel center_panel = createCenterPanel(guideCards, guideViews, guideCard); 
				
				masterPanel.add(title_panel, BorderLayout.PAGE_START); 
				masterPanel.add(center_panel, BorderLayout.CENTER);
				
				guide_frame.getContentPane().add(masterPanel); 
				guide_frame.getContentPane().setBackground(aa_grey);
				guide_frame.pack();
				guide_frame.setAlwaysOnTop(false);
				guide_frame.setVisible(true);
				guide_frame.setResizable(true);
				guide_frame.setLocationRelativeTo(null);
				
			}
		});
	}
}
