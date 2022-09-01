package AttentionAssistant;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import AttentionAssistant.Pomodoro_Timer.Work_Break;

public class Monitoring_Bar {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	int sWidth = (int) screen.getWidth();
	int sHeight = (int) screen.getHeight();
	static Color aa_grey = new Color(51,51,51);
	static Color aa_purple = new Color(137,31,191);
	static LineBorder line = new LineBorder(aa_purple, 2, true);
	JButton toRefresh;
	int counter;
	JPanel buttonPanel;
	boolean isWork;
	
	
	public void monitorBar(int userID,DataBase db,Pomodoro_Timer pomo,Priority_Manager pm) {
		
		if(pomo.getWBMonitor() == Work_Break.Work) {
			isWork = true;
		}else {
			isWork = false;
		}
		
		counter = 1;
		JFrame frame = new JFrame();
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
        buttonPanel = buttons(userID,db,pomo,pm,frame);
        panel.add("iPanel", buttonPanel);
        cardLayout.show(panel, "iPanel");
        frame.getContentPane().add(panel);
		frame.pack();
		frame.setLocation((sWidth-70), (sHeight-200));
		frame.setVisible(true);
		frame.setResizable(true);
		
		toRefresh = new JButton();
        toRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		rebuildPanel(userID,db,cardLayout,panel,frame,pomo,pm);
        }});
	}
	
	public void refreshBar() {
		isWork = !isWork;
		toRefresh.doClick();
		System.out.print(isWork);
	}
	
	private void rebuildPanel(int userID,DataBase db,CardLayout cardLayout,JPanel panel,JFrame frame,Pomodoro_Timer pomo,Priority_Manager pm) {
		JPanel newButtonPanel = new JPanel();
		if(counter % 2 != 0) {
			newButtonPanel = buttons(userID,db,pomo,pm,frame);
			panel.add("newIPanel",newButtonPanel);
			cardLayout.show(panel, "newIPanel");
			panel.remove(buttonPanel);
		}else {
			buttonPanel = buttons(userID,db,pomo,pm,frame);
			panel.add("iPanel",buttonPanel);
			cardLayout.show(panel, "iPanel");
			panel.remove(newButtonPanel);
		}
		counter++;
		panel.revalidate();
		panel.repaint();
		frame.revalidate();
		frame.repaint();
	}
	
	private JPanel buttons(int userID,DataBase db,Pomodoro_Timer pomo,Priority_Manager pm,JFrame frame) {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(120,120,120));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JButton minimize = new JButton();
		JButton pomoButton = new JButton();
		JButton monitorButton = new JButton();
		int size = 50;
		
		BufferedImage mini = null;
		BufferedImage workP = null;
		BufferedImage breakP = null;
		BufferedImage monitor = null;
		BufferedImage square = null;
		try {
			mini = ImageIO.read(new File("images/minimize.png"));
			mini.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			workP = ImageIO.read(new File("images/case.png"));
			workP.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			breakP = ImageIO.read(new File("images/mug.png"));
			breakP.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			monitor = ImageIO.read(new File("images/monitor.png"));
			monitor.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			//gets circle image
			square = ImageIO.read(new File("images/monitorSquare.png"));
			square.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		colorSquare(workP,Color.black);
		colorSquare(breakP,Color.black);
		colorSquare(monitor,Color.black);
		colorSquare(square,new Color(5,100,255));
		
		// create new image of icon image on top of circle image
        BufferedImage newImg = new BufferedImage(size, size,square.getType());
        Graphics2D graphic = newImg.createGraphics();
        graphic.drawImage(square, 0, 0, size, size, 0, 0, square.getWidth(), square.getHeight(), null);
        graphic.drawImage(monitor, 0, 0, size, size, 0, 0, monitor.getWidth(), monitor.getHeight(), null);
        graphic.dispose();
		
      //creates an ImageIcon
        ImageIcon miniIcon = new ImageIcon(mini);
        minimize.setIcon(miniIcon);
        //make non-icon area of button invisible
        minimize.setContentAreaFilled(false);
        //remove button border
        minimize.setBorderPainted(false);
        minimize.setFocusPainted(false);
        minimize.setRolloverEnabled(false);
        minimize.setMargin(new Insets(0,0,0,0));
        minimize.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.setState(JFrame.ICONIFIED);
        	}});
        
        //creates an ImageIcon
        ImageIcon icon = new ImageIcon(newImg);
        monitorButton.setIcon(icon);
        //make non-icon area of button invisible
        monitorButton.setContentAreaFilled(false);
        //remove button border
        monitorButton.setBorderPainted(false);
        monitorButton.setFocusPainted(false);
        monitorButton.setRolloverEnabled(false);
        monitorButton.setMargin(new Insets(0,0,0,0));
        monitorButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Notification_System notifSystem;
        		Task task = pm.getActiveTask();
        		Observer observer = new Observer();
				try {
					notifSystem = new Notification_System(userID,db);
					observer = new Observer(task, db, notifSystem, pomo);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                Thread observerThread = new Thread(observer);
                observerThread.start();
                pomo.clickStart();
                Date timestamp = new Date();
                db.AddEvent(userID, timestamp, "started");
        		System.out.println("Task "+task.getTaskName()+" activated");
                pomo.taskrefresh();
        	}});
        Color color;
        if(isWork == false) {
        	color = Color.red;
        }else {
        	color = Color.green;
        }
        colorSquare(square,color);
		
     // create new image of icon image on top of circle image
        BufferedImage pomoImg = new BufferedImage(size, size,square.getType());
        Graphics2D graphic2 = pomoImg.createGraphics();
        graphic2.drawImage(square, 0, 0, size, size, 0, 0, square.getWidth(), square.getHeight(), null);
        
        if(isWork == false) {
        	graphic2.drawImage(breakP, 0, 0, size, size, 0, 0, breakP.getWidth(), breakP.getHeight(), null);
        }else {
        	graphic2.drawImage(workP, 0, 0, size, size, 0, 0, workP.getWidth(), workP.getHeight(), null);
        }
                
        graphic2.dispose();
		
		//creates an ImageIcon
        ImageIcon icon2 = new ImageIcon(pomoImg);
        pomoButton.setIcon(icon2);
        //make non-icon area of button invisible
        pomoButton.setContentAreaFilled(false);
        //remove button border
        pomoButton.setBorderPainted(false);
        pomoButton.setFocusPainted(false);
        pomoButton.setRolloverEnabled(false);
        pomoButton.setMargin(new Insets(0,0,0,0));
        pomoButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		pomo.setbuttonto0(userID,db, pm);
        		refreshBar();
        	}});
        panel.add(minimize);
        panel.add(pomoButton);
        panel.add(monitorButton);
		return panel;
	}
	
	private BufferedImage colorSquare(BufferedImage image,Color color) {
		Color circleColor = color;
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
	
	
}
