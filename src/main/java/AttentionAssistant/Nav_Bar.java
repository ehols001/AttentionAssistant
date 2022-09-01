package AttentionAssistant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.*;
import javax.swing.*;


public class Nav_Bar{
	/*
	 * creates specific grey and purple as color objects
	 */
	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	JButton toRefresh;
	JButton menuButton;
	DecimalFormat df = new DecimalFormat("#.#"); 
	private int count;
		
	/*
	 * variables
	 */
	private int x_coord;
	private int y_coord;
	private int size;
	private Color iconColor;
	private float iconOpacity;
	private Color circleColor;
	private float circleOpacity;
	private boolean isVert;
	private boolean isCollapsed;
	private boolean settings_visible=true;
	private boolean pm_isActive=true;
	private boolean pm_visible;
	private boolean pomo_visible;
	private boolean ntb_visible;
	private boolean htb_visible;
	private boolean fts_visible;
	private boolean progress_visible;
	private boolean pomodoro_active;
	private boolean ntb_active;
	private boolean htb_active;
	private boolean fts_active;	
	
	private boolean settings_menu=true;
	private boolean pm_menu=true;
	private boolean pomo_menu=true;
	private boolean ntb_menu=true;
	private boolean htb_menu=true;
	private boolean fts_menu=true;
	private boolean progress_menu=true;
	
	/*
	 * instantiating empty Nav_Bar object
	 */
	public Nav_Bar() {
		this.x_coord = 0;
		this.y_coord = 0;
		this.size = 50;
		this.iconColor = Color.white;
		this.iconOpacity = 100;
		this.circleColor = aa_grey;
		this.circleOpacity = 100;
		this.isVert = false;
		this.isCollapsed = false;
		this.pm_visible = false;
		this.pomo_visible = false;
		this.ntb_visible = false;
		this.htb_visible = false;
		this.fts_visible = false;
		this.progress_visible = false;
		this.pomodoro_active = false;
		this.ntb_active = false;
		this.htb_active = false;
		this.fts_active = false;
	}

	/*
	 * create Nav_Bar with variables set by settings
	 */
	public Nav_Bar(Settings set,DataBase db) {
		this.x_coord = set.getXCoord();
		this.y_coord = set.getYCoord();
		this.size = set.getIconSize();
		this.iconColor = set.getIcons();
		this.iconOpacity = set.getOpacityIcons();
		this.circleColor = set.getIconCircles();
		this.circleOpacity =set.getOpacityCircles();
		this.isVert = set.getIsVertical();
		this.isCollapsed = set.getIsCollapsed();
		this.pm_visible = set.getPmIsVisible();
		this.pomo_visible = set.getTimerIsVisible();
		this.ntb_visible = set.getNtbIsVisible();
		this.htb_visible = set.getHtbIsVisible();
		this.fts_visible = set.getFtsIsVisible();
		this.progress_visible = set.getProgReportIsVisible();
		
		this.pomodoro_active = set.getPomodoroIsActive();
		this.ntb_active = set.getNtbIsActive();
		this.htb_active = set.getHtbIsActive();
		this.fts_active = set.getFtsIsActive();
	}
	
	JPanel icon_panel;
	int counter;
	Monitoring_Bar mb;
	public void run_nav_bar(int userID,Notification_System notifSystem,DataBase db,Nav_Bar navbar,Settings settings,Priority_Manager pm,Pomodoro_Timer pomo,Negative_Thought_Burner ntb,Happy_Thought_Button htb,Free_Thought_Space fts) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
				Runnable timeLoop = () -> {
					System.out.println("minute logged");
					Date timestamp = new Date();
				    db.AddEvent(userID, timestamp, "loggedIn");
				};
				executor.scheduleWithFixedDelay(timeLoop, 1, 5, TimeUnit.MINUTES);
				mb = new Monitoring_Bar();
				mb.monitorBar(userID, db, pomo, pm);
				
				
				pomo.monitorbar(mb);
				
				pomo.clickStart();
				pomo.clickPause();
				
				
				counter = 1;
				count = 0;
				JFrame frame = new JFrame();
				frame.setBounds(x_coord, y_coord, 1000, 1000);
				//removes default title bar from frame 
		        frame.setUndecorated(true);
		        //sets background of frame to transparent
		        frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		        //forces frame to stay on top of screen
		        frame.setAlwaysOnTop(true);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        //sets top-left corner coordinate, pulled from settings
		        frame.setLocation(x_coord, y_coord);
		        //makes frame and contents visible
		        frame.setVisible(true);
		        
		        CardLayout cardLayout = new CardLayout();
		        JPanel panel = new JPanel();
		        panel.setBounds(x_coord, y_coord, 1000, 1000);
		        panel.setLayout(cardLayout);
		        
		        //panel for buttons
		        icon_panel = iconPanel(userID,cardLayout,db,navbar,settings,pm,pomo,ntb,htb,fts,frame);
		        panel.add("iPanel", icon_panel);
		        cardLayout.show(panel, "iPanel");
		        frame.getContentPane().add(panel);
				frame.pack();
				frame.setVisible(true);
				frame.setResizable(true);
				
				toRefresh = new JButton();
		        toRefresh.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		rebuildPanel(userID,cardLayout,db, navbar, settings, pm, pomo, ntb, htb, fts, panel,frame);
		        	}});
		        pm.open_pm(userID, db,pm);
			}
		});
	}
	
	private void rebuildPanel(int userID,CardLayout cardLayout,DataBase db,Nav_Bar navbar,Settings settings,Priority_Manager pm,Pomodoro_Timer pomo,Negative_Thought_Burner ntb,Happy_Thought_Button htb,Free_Thought_Space fts, JPanel panel,JFrame frame) {
		JPanel new_icon_panel = new JPanel();
		if(counter % 2 != 0) {
			new_icon_panel = iconPanel(userID,cardLayout,db, navbar, settings, pm, pomo, ntb, htb, fts, frame);
			panel.add("newIPanel",new_icon_panel);
			cardLayout.show(panel, "newIPanel");
			panel.remove(icon_panel);
		}else {
			icon_panel = iconPanel(userID,cardLayout,db, navbar, settings, pm, pomo, ntb, htb, fts, frame);
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

	/*
	 * create panel that houses active & visible feature icons
	 */
	private JPanel iconPanel(int userID,CardLayout cardLayout,DataBase db,Nav_Bar navbar,Settings settings,Priority_Manager pm, Pomodoro_Timer pomo, Negative_Thought_Burner ntb,Happy_Thought_Button htb,Free_Thought_Space fts, JFrame frame) {
		JPanel panel = new JPanel();
		//displays buttons vertically if true, horizontally is false
		if(isVert == true) {
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			frame.setSize(size, size*9);
			panel.setSize(size, size*9);
		}else {
			panel.setLayout(new FlowLayout(FlowLayout.LEFT,1,0));
			frame.setSize(size*11, size*2);
			panel.setSize(size*11, size*2);
		}
		
		menuButton = createButton("images/menu_button.png");
		menuButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//expand navbar
        		settings_menu = !settings_menu;
    	    	pm_menu = !pm_menu;
	    		pomo_menu = !pomo_menu;
	    		ntb_menu = !ntb_menu;
	    		htb_menu = !htb_menu;
	    		fts_menu = !fts_menu;
	    		progress_menu = !progress_menu;
        		toRefresh.doClick();
        		System.out.println("clicked");
        }});
		JButton settingsButton = createButton("images/setting_button.png");
		JButton pmButton = createButton("images/manager.png");
		JButton pomoButton = createButton("images/timer.png");
		JButton ntbButton = createButton("images/burner.png");
		JButton htbButton = createButton("images/happy_button.png");
		JButton ftsButton = createButton("images/thought_space.png");
		JButton progressButton = createButton("images/progress.png");
		
		
		if(isCollapsed == true) {
	        panel.add(menuButton);
	        if(count == 0) {
	        	settings_menu = false;
    	    	pm_menu = false;
	    		pomo_menu = false;
	    		ntb_menu = false;
	    		htb_menu = false;
	    		fts_menu = false;
	    		progress_menu = false;
	    		count++;
	        }else {count--;}
		}else {
			panel.remove(menuButton);
			settings_menu = true;
	    	pm_menu = true;
    		pomo_menu = true;
    		ntb_menu = true;
    		htb_menu = true;
    		fts_menu = true;
    		progress_menu = true;
		}
		
		if(settings_visible == true && settings_menu == true) {
	    	settingsButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//open settings
	        		settings.open_settings(userID, db, navbar, settings, pm, pomo, ntb, htb, fts);
	        }});
	    	panel.add(settingsButton);
		}
		
	    if(pm_visible == true && pm_isActive == true && pm_menu == true) {
	    	pmButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//open pm
	        		pm.open_pm(userID,db,pm);
	        }});
			panel.add(pmButton);
	    }
	    
		if(pomo_visible == true && pomodoro_active == true && pomo_menu == true) {
			pomoButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//open pomo
	        		pomo.makeVisible();
	        }});
			panel.add(pomoButton);
		}
		if(ntb_visible == true && ntb_active == true && ntb_menu == true) {
			ntbButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		ntb.run_ntb(settings,htb, db);
	        		Date timestamp = new Date();
	        		System.out.println(timestamp);
	        		db.AddEvent(userID, timestamp, "ntb");
	        }});
			panel.add(ntbButton);
		}
		if(htb_visible == true && htb_active == true && htb_menu == true) {
			htbButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//open htb
	        		htb.open_htb(db, userID);
	        		Date timestamp = new Date();
	        		System.out.println(timestamp);
	        		db.AddEvent(userID, timestamp, "htb");
	        }});
			panel.add(htbButton);
		}
		if(fts_visible == true && fts_active == true && fts_menu == true) {
			ftsButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//open fts
	        		fts.runFts(fts, db, userID);
	        		Date timestamp = new Date();
	        		System.out.println(timestamp);
	        		db.AddEvent(userID, timestamp, "fts");
	        }});
			panel.add(ftsButton);
		}
		if(progress_visible == true && progress_menu == true) {
			progressButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		//open progress report
	        		Progress_Report pr = new Progress_Report();
	        		pr.open_progressReport(userID, db);
	        }});
			panel.add(progressButton);
		}
		
        
        //sets background of panel to transparent
        panel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        return panel;
	}
	
	
	private JButton createButton(String imgFile) {
		JButton button = new JButton();
		BufferedImage img = null;
		BufferedImage circle = null;
		try {
			//gets image for specific buttons icon
			img = ImageIO.read(new File(imgFile));
			img.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			//gets circle image
			circle = ImageIO.read(new File("images/circle.png"));
			circle.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		colorIcon(img);
		colorCircle(circle);
		
		float iconOpac = iconOpacity/100;
		BufferedImage imgOpac = new BufferedImage(size, size,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gicon = imgOpac.createGraphics();
        gicon.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, iconOpac));
        gicon.drawImage(img, 0, 0, size, size, 0, 0, img.getWidth(), img.getHeight(), null);
		
        
        float circleOpac = circleOpacity/100;
		BufferedImage circOpac = new BufferedImage(size, size,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gcirc = circOpac.createGraphics();
        gcirc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, circleOpac));
        gcirc.drawImage(circle, 0, 0, size, size, 0, 0, circle.getWidth(), circle.getHeight(), null);
        
		int m = (int)(size*(15.0f/100.0f));
		// create new image of icon image on top of circle image
        BufferedImage newImg = new BufferedImage(size, size,img.getType());
        Graphics2D graphic = newImg.createGraphics();
        graphic.drawImage(circOpac, 0, 0, size, size, 0, 0, circOpac.getWidth(), circOpac.getHeight(), null);
        graphic.drawImage(imgOpac,m,m,size-m,size-m,0,0,imgOpac.getWidth(),imgOpac.getHeight(), null);
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
	
	/*
	 * adjusts color and/or opacity of specified icon image to specified color/opacity
	 */
	public BufferedImage colorIcon(BufferedImage image) {
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
	
	
	/*
	 * get changed settings
	 */
	public void refresh(Settings set) {
		this.x_coord = set.getXCoord();
		this.y_coord = set.getYCoord();
		this.size = set.getIconSize();
		this.iconColor = set.getIcons();
		this.iconOpacity = set.getOpacityIcons();
		this.circleColor = set.getIconCircles();
		this.circleOpacity =set.getOpacityCircles();
		this.isVert = set.getIsVertical();
		this.isCollapsed = set.getIsCollapsed();
		
		this.pm_visible = set.getPmIsVisible();
		this.pomo_visible = set.getTimerIsVisible();
		this.ntb_visible = set.getNtbIsVisible();
		this.htb_visible = set.getHtbIsVisible();
		this.fts_visible = set.getFtsIsVisible();
		this.progress_visible = set.getProgReportIsVisible();
		
		this.pomodoro_active = set.getPomodoroIsActive();
		this.ntb_active = set.getNtbIsActive();
		this.htb_active = set.getHtbIsActive();
		this.fts_active = set.getFtsIsActive();
		count=0;
		toRefresh.doClick();
	}
}