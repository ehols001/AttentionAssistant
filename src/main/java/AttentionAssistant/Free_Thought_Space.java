package AttentionAssistant;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

public class Free_Thought_Space {
	Color aa_grey = new Color(51,51,51);
	Color aa_purple = new Color(137,31,191);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private String activeTool;
	private LineBorder line = new LineBorder(Color.gray, 2, true);
	private LineBorder lineSelected = new LineBorder(Color.white, 2, true);
	private LineBorder purpLine = new LineBorder(aa_purple, 1, true);
	private LineBorder purpLine2 = new LineBorder(aa_purple, 2, true);
	private Color selectedColor;
	private boolean isFilled;
	private Stack<Layer> addedLayers;
	private Stack<Layer> removedLayers;
	private Color primary,secondary;
	private static int mouseX;
	private static int mouseY;
	private int strokeWidth;
	public JFileChooser fileChooser = null;
	Color customSelected;
	JPanel drawingSpace;
	Image drawn;
	JPanel ds;
	int dsW,dsH,group;
	BufferedImage canvasShow,canvasHide;
	Graphics2D g;
	int clickX,clickY,releaseX,releaseY;
	
	
	public Free_Thought_Space() {
		this.primary = Color.black;
		this.secondary = aa_purple;
		this.strokeWidth = 5;
		this.addedLayers = new Stack<Layer>();
		this.removedLayers = new Stack<Layer>();
		this.group = 1;
	}
	
	JButton refresh;
	public void runFts(Free_Thought_Space fts,DataBase db,int userID) {
		drawn = null;
		JFrame frame = new JFrame("Free Thought Space");
		frame.setBackground(Color.BLACK);
		frame.setSize(new Dimension(dim.width-200,dim.height-100));
		frame.setPreferredSize(new Dimension(dim.width-200,dim.height-100));
		frame.setLocation((dim.width-frame.getWidth())/2,20);
		frame.setUndecorated(true);
		
		dsW = frame.getWidth()-100;
		dsH = frame.getHeight()-200;
		
		JMenuBar title = titlePanel(frame);
		//add title bar- needs maximize/shrink & minimize buttons
		title.setBorder(purpLine2);

		frame.getContentPane().add(title,BorderLayout.PAGE_START);
		//add toolbar
		frame.getContentPane().add(toolBar(),BorderLayout.WEST);
		
		//add draw space
		ds = drawingSpace();
		
		frame.getContentPane().add(ds,BorderLayout.CENTER);
		
		//add colorbar
		frame.getContentPane().add(createColorBar(db,userID),BorderLayout.PAGE_END);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	//*************************************************************************************drawing panel
	boolean dragged;
	JLabel drawing;
	private JPanel drawingSpace() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, aa_purple));
		drawing = new JLabel();
		drawing.setFocusable(false);
		drawing.setBackground(Color.white);
		drawing.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, aa_purple));
		if (canvasShow == null) {
			canvasShow = new BufferedImage(dsW,dsH,BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = canvasShow.createGraphics();  // Graphics context for isDrawing to OSI.
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, dim.width,dim.height);
            graphics.dispose();
		}
		MouseMotionListener mListen = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				releaseX = e.getX();
				releaseY = e.getY();
				dragged = true;
				if (activeTool == "erase"){
					addedLayers.push(new Layer(clickX, clickY, releaseX, releaseY,Color.white,Color.white,strokeWidth,"erase",group));
					repaintDS();
					clickX = releaseX;
					clickY = releaseY;
				}
				if (activeTool == "pencil") {
					addedLayers.push(new Layer(clickX, clickY, releaseX, releaseY,primary,secondary,strokeWidth,"pencil",group));
					repaintDS();
					clickX = releaseX;
					clickY = releaseY;
				}
				if (activeTool == "spray") {
					int rand1,rand2;
					Random rand = new Random();   
                    rand1 = rand.nextInt(strokeWidth);
                    rand2 = rand.nextInt(strokeWidth);
					addedLayers.push(new Layer(clickX, clickY, releaseX, releaseY,primary,secondary,strokeWidth,"spray",group,rand1,rand2));
					repaintDS();
					clickX = releaseX;
					clickY = releaseY;
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		MouseListener listen = new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				clickX = e.getX();
				clickY = e.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (activeTool == "line" && dragged) {
					addedLayers.push(new Layer(clickX, clickY, releaseX, releaseY,primary,secondary,strokeWidth,"line",isFilled));
					repaintDS();
				}
				else if (activeTool == "rectangle" && dragged) {
					addedLayers.push(new Layer(clickX, clickY, releaseX, releaseY,primary,secondary,strokeWidth,"rectangle",isFilled));
				}
				else if (activeTool == "oval" && dragged) {
					addedLayers.push(new Layer(clickX, clickY, releaseX, releaseY,primary,secondary,strokeWidth,"oval",isFilled));
				}
				
				else if (activeTool == "text"){
					JFrame tFrame = new JFrame();
					tFrame.setAlwaysOnTop(true);
					tFrame.setLocation(clickX,clickY);
					tFrame.setPreferredSize(new Dimension(180,250));
					tFrame.setBackground(aa_grey);
					tFrame.setForeground(Color.white);
					
					tFrame.setUndecorated(true);
					
					JMenuBar close = new JMenuBar();
					close.setLayout(new FlowLayout(FlowLayout.RIGHT));
					close.setBackground(aa_grey);
					close.setBorder(BorderFactory.createMatteBorder(2,2,0,2,aa_purple));
					
					BufferedImage exit = null;
					try {
						exit = ImageIO.read(new File("images/exit_circle.png"));
					}catch(Exception e1){
						e1.printStackTrace();
						System.exit(1);
					}
					
					Image c_img = exit.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
					
					JButton exitButton = new JButton();
					exitButton.setIcon(new ImageIcon(c_img));
					exitButton.setContentAreaFilled(false);
					exitButton.setFocusable(false);
					exitButton.setBorderPainted(false);
					exitButton.addActionListener(new ActionListener() {
			        	public void actionPerformed(ActionEvent e) {
			        		//close window without saving 
			        		tFrame.dispose();
			        	
			        }});
					
					close.add(exitButton);
					tFrame.setJMenuBar(close);
					
					JPanel panel = new JPanel();
					panel.setLayout(null);
					panel.setBackground(aa_grey);
					panel.setForeground(Color.white);
					panel.setBorder(BorderFactory.createMatteBorder(0,2,2,2,aa_purple));
										
					JLabel textLabel = new JLabel("Text: ");
					textLabel.setBounds(10,10,50,30);
					textLabel.setForeground(Color.white);
					JTextArea text = new JTextArea();
					text.setBounds(60,10,80,30);
					
					
					JLabel fontLabel = new JLabel("Font: ");
					fontLabel.setForeground(Color.white);
					fontLabel.setBounds(10,50,50,30);
					String[] fontStrings = {"Dialog","DialogInput","Monospaced","Serif","SansSerif"};
					JComboBox<String> fonts = new JComboBox<String>(fontStrings);
					fonts.setBounds(60,50,100,30);
					
										
					JLabel sizeLabel = new JLabel("Size: ");
					sizeLabel.setForeground(Color.white);
					sizeLabel.setBounds(10,90,50,30);
					JTextArea size = new JTextArea();
					size.setBounds(60,90,80,30);
					
					
										
					JCheckBox bold = new JCheckBox("Bold");
					bold.setBounds(10,130,80,30);
					bold.setForeground(Color.white);
					bold.setBackground(aa_grey);
					JCheckBox italic = new JCheckBox("Italic");
					italic.setForeground(Color.white);
					italic.setBackground(aa_grey);
					italic.setBounds(100,130,80,30);
					bold.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							// TODO Auto-generated method stub
							if(bold.isSelected() == true) {
								italic.setSelected(false);
							}
						}
						
					});
					italic.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							// TODO Auto-generated method stub
							if(italic.isSelected() == true) {
								bold.setSelected(false);
							}
						}
						
					});
					
					
					JButton okay = new JButton("ok");
					okay.setBackground(aa_purple);
					okay.setForeground(Color.white);
					okay.setBorder(purpLine);
					okay.setBounds(10,170,50,30);
					okay.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							Font f;
							int s = Integer.parseInt(size.getText());
							if(bold.isSelected() == true) {
								f = new Font((String) fonts.getItemAt(fonts.getSelectedIndex()),Font.BOLD,s);
							}else if(italic.isSelected() == true) {
								f = new Font((String) fonts.getItemAt(fonts.getSelectedIndex()),Font.ITALIC,s);
							}else{
								f = new Font((String) fonts.getItemAt(fonts.getSelectedIndex()),Font.PLAIN,s);
							}
							
							addedLayers.push(new Layer(clickX, clickY, s, primary, secondary,text.getText(),"text", f,strokeWidth));
							repaintDS();
							tFrame.dispose();
						}
						
					});
					
					JButton cancel = new JButton("cancel");
					cancel.setBackground(aa_purple);
					cancel.setForeground(Color.white);
					cancel.setBorder(purpLine);
					cancel.setBounds(65,170,85,30);
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							tFrame.dispose();
						}
						
					}); 
					
					panel.add(textLabel);
					panel.add(text);
					panel.add(fontLabel);
					panel.add(fonts);
					panel.add(sizeLabel);
					panel.add(size);
					panel.add(bold);
					panel.add(italic);
					panel.add(okay);
					panel.add(cancel);
					
					tFrame.setVisible(true);
					tFrame.add(panel,BorderLayout.CENTER);
					tFrame.pack();
				}
				
				dragged = false;
				repaintDS();
				group++;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		drawing.setIcon(new ImageIcon(canvasShow));
		drawing.addMouseListener(listen);
		drawing.addMouseMotionListener(mListen);
		panel.add(drawing);
		return panel;
	}
	
	private void undo() {
		if(addedLayers.size() != 0){
			//add grouped logic
			
			Layer last = addedLayers.lastElement();
			int grouped = last.getGroup();
			addedLayers.pop();
			removedLayers.add(last);
			for(int i=0;i<addedLayers.size();i++) {
				if(addedLayers.get(i).getGroup() == grouped && grouped != 0) {
					Layer groupLayer = addedLayers.get(i);
					addedLayers.remove(i);
					removedLayers.add(groupLayer);
				}
			}
			repaintDS();
		}
	}
	
	private void redo() {
		if(removedLayers.size() != 0){
			//add grouped logic
			
			Layer last = removedLayers.lastElement();
			int grouped = last.getGroup();
			removedLayers.pop();
			addedLayers.add(last);
			for(int i=0;i<removedLayers.size();i++) {
				if(removedLayers.get(i).getGroup() == grouped && grouped != 0) {
					Layer groupLayer = removedLayers.get(i);
					removedLayers.remove(i);
					addedLayers.add(groupLayer);
				}
			}
			repaintDS();
		}
	}
	
	private void clear() {
		addedLayers.clear();
		removedLayers.clear();
		repaintDS();
	}
	
	private void repaintDS() {
		canvasHide = new BufferedImage(dsW,dsH,BufferedImage.TYPE_INT_ARGB);
		g = canvasHide.createGraphics();
		g.setBackground(Color.white);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRect(0, 0, dim.width,dim.height);
		
		for(int i=0;i<addedLayers.size();i++) {
			//draw each layer
			drawLayer(addedLayers.get(i));
		}
		drawing.setIcon(new ImageIcon(canvasHide));
		drawing.repaint();
		drawing.revalidate();
		ds.revalidate();
		ds.repaint();
	}
	
	int brushPoints[][];
	
	private void drawLayer(Layer layer) {
		System.out.println(layer.getTool());
		int cX = layer.getMouseClickX();
		int cY = layer.getMouseClickY();
		int rX = layer.getMouseReleaseX();
		int rY = layer.getMouseReleaseY();
		
		if(layer.getTool() == "image") {
			g.drawImage(layer.getImage(),0,0,canvasShow.getWidth(),canvasShow.getHeight(),0,0,layer.getImage().getWidth(),layer.getImage().getHeight(),null);
			return;
		}
		
		if(layer.getTool() == "pencil") {
			g.setColor(layer.getPrimary());
            g.fillOval(cX, cY, layer.getStroke(), layer.getStroke());
			return;
		}
		
		if(layer.getTool() == "erase") {
			g.setColor(Color.white);
            g.fillOval(cX, cY, layer.getStroke(), layer.getStroke());
			return;
		}
		
		if (layer.getTool() == "line") {
			g.setColor(layer.getPrimary());
            g.setStroke(new BasicStroke(layer.getStroke(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(cX, cY, rX, rY);        // draw the line between the two brushPoints.
            return;
        }

        if (layer.getTool() == "spray") {
        	g.setColor(layer.getPrimary());
            brushPoints = new int[(layer.getStroke() * layer.getStroke()) / 10][2];    //create a new two-dimensional array of variable size
            for (int i = 0; i < (layer.getStroke() * layer.getStroke()) / 10; i++)
            {
                int pts[] = new int[2];
                    pts[0] = layer.getRand1();
                    pts[1] = layer.getRand2();
                g.drawRect(cX + pts[0], cY + pts[1], 1, 1);  //draw a rectangle to create a brush effect
                brushPoints[i] = pts;
            }
            return;
        }
        
        if(layer.getTool() == "text") {
        	g.setColor(layer.getPrimary());
        	g.setFont(layer.getFont());
			g.drawString(layer.getText(), layer.getMouseClickX(), layer.getMouseClickY());
        	return;
        }

        int positionX, positionY;   // Top left corner of rectangle that contains the figure.
        int width, height;         // Width and height of rectangle that contains the figure.
        
        if (rX >= cX){  // pointX2 is left edge
            positionX = cX;
            width = rX - cX;
        }else{   // pointX1 is left edge
            positionX = rX;
            width = cX - rX;
        }
        
        if (rY >= cY){  // pointY2 is top edge
            positionY = cY;
            height = rY - cY;
        }else{   // pointY1 is top edge
            positionY = rY;
            height = cY - rY;
        }

        if (layer.getTool() == "rectangle" && layer.getIsFilled() == false){
        	g.setColor(layer.getPrimary());
            g.setStroke(new BasicStroke(layer.getStroke(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawRect(positionX, positionY, width, height);
            return;
        }

        if (layer.getTool() == "oval" && layer.getIsFilled() == false){
        	g.setColor(layer.getPrimary());
            g.setStroke(new BasicStroke(layer.getStroke(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawOval(positionX, positionY, width, height);
            return;
        }

        if (layer.getTool() == "oval" && layer.getIsFilled() == true)            //if isSelected tool is FILLED OVAL
        {
        	g.setColor(layer.getSecondary());
            g.fillOval(positionX, positionY, width, height);      //draw filled oval
            
            
	        g.setColor(layer.getPrimary());
	        g.setStroke(new BasicStroke(layer.getStroke(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawOval(positionX, positionY, width, height);
            return;
        }

        if (layer.getTool() == "rectangle" && layer.getIsFilled() == true)      //if isSelected tool is FILLED RECTANGLE
        {
        	g.setColor(layer.getSecondary());
            g.fillRect(positionX, positionY, width, height);      //draw filled rectangle
            
            g.setColor(layer.getPrimary());
            g.setStroke(new BasicStroke(layer.getStroke(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawRect(positionX, positionY, width, height);              //draw rectangle
            return;
        }

	}
	
	//*************************************************************************************tool bar
	private JPanel toolBar() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100,700));
		panel.setLayout(null);
		//panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBackground(Color.black);
		panel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, aa_purple));
		
		panel.add(Box.createRigidArea(new Dimension(0,15)));
		ArrayList<JButton> tools = toolButtons();
		for(int i=0;i<tools.size();i++) {
			panel.add(tools.get(i));
		}
		
		return panel;
	}
	
	private ArrayList<JButton> toolButtons(){
		ArrayList<JButton> tools = new ArrayList<JButton>();
		BufferedImage pencilImg = null;
		BufferedImage sprayImg = null;
		BufferedImage lineImg = null;
		BufferedImage rectangleImg = null;
		BufferedImage ovalImg = null;
		BufferedImage textImg = null;
		BufferedImage eraseImg = null;
		BufferedImage clearImg = null;
		BufferedImage undoImg = null;
		BufferedImage redoImg = null;
		try {
			pencilImg = ImageIO.read(new File("images/pencil.png"));
			sprayImg = ImageIO.read(new File("images/paint-spray.png"));
			lineImg = ImageIO.read(new File("images/line.png"));
			rectangleImg = ImageIO.read(new File("images/rectangle.png"));
			ovalImg = ImageIO.read(new File("images/oval.png"));
			textImg = ImageIO.read(new File("images/add-text.png"));
			eraseImg = ImageIO.read(new File("images/eraser.png"));
			//make clear image for icon
			clearImg = ImageIO.read(new File("images/clear.png"));
			undoImg = ImageIO.read(new File("images/undo.png"));
			redoImg = ImageIO.read(new File("images/redo.png"));
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		JButton pencil = new JButton();
		pencil.setIcon(new ImageIcon(pencilImg));
		pencil.setContentAreaFilled(false);
		pencil.setFocusPainted(false);
		pencil.setBorderPainted(false);
		pencil.setBounds(25,15,50,50);
		
		JButton spray = new JButton();
		spray.setIcon(new ImageIcon(sprayImg));
		spray.setContentAreaFilled(false);
		spray.setFocusPainted(false);
		spray.setBorderPainted(false);
		spray.setBounds(25,70,50,50);
		
		JButton line = new JButton();
		line.setIcon(new ImageIcon(lineImg));
		line.setContentAreaFilled(false);
		line.setFocusPainted(false);
		line.setBorderPainted(false);
		line.setBounds(25,125,50,50);
		
		JButton rectangle = new JButton();
		rectangle.setIcon(new ImageIcon(rectangleImg));
		rectangle.setContentAreaFilled(false);
		rectangle.setFocusPainted(false);
		rectangle.setBorderPainted(false);
		rectangle.setBounds(25,180,50,50);
		
		JButton oval = new JButton();
		oval.setIcon(new ImageIcon(ovalImg));
		oval.setContentAreaFilled(false);
		oval.setFocusPainted(false);
		oval.setBorderPainted(false);
		oval.setBounds(25,235,50,50);
		
		JButton text = new JButton();
		text.setIcon(new ImageIcon(textImg));
		text.setContentAreaFilled(false);
		text.setFocusPainted(false);
		text.setBorderPainted(false);
		text.setBounds(25,290,50,50);
		
		JButton erase = new JButton();
		erase.setIcon(new ImageIcon(eraseImg));
		erase.setContentAreaFilled(false);
		erase.setFocusPainted(false);
		erase.setBorderPainted(false);
		erase.setBounds(25,345,50,50);		
		
		pencil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTool = "pencil";
				pencil.setBorderPainted(true);
				pencil.setBorder(new MatteBorder(0,0,1,0,aa_purple));
				spray.setBorderPainted(false);
				line.setBorderPainted(false);
				rectangle.setBorderPainted(false);
				oval.setBorderPainted(false);
				text.setBorderPainted(false);
				erase.setBorderPainted(false);
			}
		});
		spray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTool = "spray";
				pencil.setBorderPainted(false);
				spray.setBorderPainted(true);
				spray.setBorder(new MatteBorder(0,0,1,0,aa_purple));
				line.setBorderPainted(false);
				rectangle.setBorderPainted(false);
				oval.setBorderPainted(false);
				text.setBorderPainted(false);
				erase.setBorderPainted(false);
			}
		});
		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTool = "line";
				pencil.setBorderPainted(false);
				spray.setBorderPainted(false);
				line.setBorderPainted(true);
				line.setBorder(new MatteBorder(0,0,1,0,aa_purple));
				rectangle.setBorderPainted(false);
				oval.setBorderPainted(false);
				text.setBorderPainted(false);
				erase.setBorderPainted(false);
				
			}
		});
		rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTool = "rectangle";
				pencil.setBorderPainted(false);
				spray.setBorderPainted(false);
				line.setBorderPainted(false);
				rectangle.setBorderPainted(true);
				rectangle.setBorder(new MatteBorder(0,0,1,0,aa_purple));
				oval.setBorderPainted(false);
				text.setBorderPainted(false);
				erase.setBorderPainted(false);
			}
		});
		oval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTool = "oval";
				pencil.setBorderPainted(false);
				spray.setBorderPainted(false);
				line.setBorderPainted(false);
				rectangle.setBorderPainted(false);
				oval.setBorderPainted(true);
				oval.setBorder(new MatteBorder(0,0,1,0,aa_purple));
				text.setBorderPainted(false);
				erase.setBorderPainted(false);
			}
		});
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTool = "text";
				pencil.setBorderPainted(false);
				spray.setBorderPainted(false);
				line.setBorderPainted(false);
				rectangle.setBorderPainted(false);
				oval.setBorderPainted(false);
				text.setBorderPainted(true);
				text.setBorder(new MatteBorder(0,0,1,0,aa_purple));
				erase.setBorderPainted(false);
			}
		});
		erase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTool = "erase";
				pencil.setBorderPainted(false);
				spray.setBorderPainted(false);
				line.setBorderPainted(false);
				rectangle.setBorderPainted(false);
				oval.setBorderPainted(false);
				text.setBorderPainted(false);
				erase.setBorderPainted(true);
				erase.setBorder(new MatteBorder(0,0,1,0,aa_purple));
			}
		});
		
		JButton clear = new JButton();
		clear.setIcon(new ImageIcon(clearImg));
		clear.setContentAreaFilled(false);
		clear.setFocusPainted(false);
		clear.setBorderPainted(false);
		clear.setBounds(25,400,50,50);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		
		JButton undo = new JButton();
		undo.setIcon(new ImageIcon(undoImg));
		undo.setContentAreaFilled(false);
		undo.setFocusPainted(false);
		undo.setBorderPainted(false);
		undo.setBounds(25,455,50,50);
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		
		JButton redo = new JButton();
		redo.setIcon(new ImageIcon(redoImg));
		redo.setContentAreaFilled(false);
		redo.setFocusPainted(false);
		redo.setBorderPainted(false);
		redo.setBounds(25,510,50,50);
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		});
		
		tools.add(pencil);
		tools.add(spray);
		tools.add(line);
		tools.add(rectangle);
		tools.add(oval);
		tools.add(text);
		tools.add(erase);
		tools.add(clear);
		tools.add(undo);
		tools.add(redo);
		return tools;
	}
	
	//*************************************************************************************color bar
	int size=25;
	public JPanel createColorBar(DataBase db, int userID) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, aa_purple));
		JPanel palette = colorPalette(db,userID);
		JPanel selection = colorSelection(db,userID);
		
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(palette);
		panel.add(selection);
		return panel;
	}
	
	BufferedImage c1 = null;
	BufferedImage c2 = null;
	private JPanel colorSelection(DataBase db,int userID) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		panel.setPreferredSize(new Dimension(500,150));
		panel.setBackground(Color.black);
		panel.setForeground(Color.white);
		
		try {
			c1 = ImageIO.read(new File("images/square.png"));
			c2 = ImageIO.read(new File("images/square.png"));
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		colorIcon(c1,primary);
		colorIcon(c2,secondary);
		
		JButton primaryB = new JButton();
		JButton secondaryB = new JButton();
		primaryB.setIcon(new ImageIcon(c1));
		primaryB.setContentAreaFilled(false);
		primaryB.setBorder(line);
		secondaryB.setIcon(new ImageIcon(c2));
		secondaryB.setContentAreaFilled(false);
		secondaryB.setBorder(line);
		primaryB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedColor != null) {
					primary = selectedColor;
					colorIcon(c1,primary);
					primaryB.setIcon(new ImageIcon(c1));
				}
			}
		});
		secondaryB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedColor != null) {
					secondary = selectedColor;
					colorIcon(c2,secondary);
					secondaryB.setIcon(new ImageIcon(c2));
				}
			}
		});
		
		JCheckBox filled = new JCheckBox("filled");
		filled.setBackground(Color.black);
		filled.setForeground(Color.white);
		filled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFilled = filled.isSelected();
			}
		});
		
		JLabel stroke = new JLabel("Stroke width:");
		stroke.setForeground(Color.white);
		JSlider strokeSlider = new JSlider(JSlider.HORIZONTAL,0,100,1);
		strokeSlider.setPreferredSize(new Dimension(150,75));
		strokeSlider.setMinorTickSpacing((int) 5);
		strokeSlider.setMajorTickSpacing((int) 10);
		strokeSlider.setPaintTicks(true);
        strokeSlider.setPaintLabels(true);
        strokeSlider.setPaintTrack(true);
		strokeSlider.setBackground(Color.black);
		strokeSlider.setForeground(aa_purple);
		strokeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				strokeWidth = strokeSlider.getValue();
			}
		});
		
		JLabel p = new JLabel("primary: ");
		JLabel s = new JLabel("secondary: ");
		p.setForeground(Color.white);
		s.setForeground(Color.white);
		panel.add(Box.createRigidArea(new Dimension(50,0)));
        panel.add(p);
        panel.add(primaryB);
        panel.add(Box.createRigidArea(new Dimension(15,0)));
        panel.add(s);
        panel.add(secondaryB);
        panel.add(Box.createRigidArea(new Dimension(50,0)));
        panel.add(filled);
        panel.add(Box.createRigidArea(new Dimension(50,0)));
        panel.add(stroke);
        panel.add(strokeSlider);
        panel.add(Box.createRigidArea(new Dimension(50,0)));
        
		
		return panel;
	}
	
	private void addColor(DataBase db,int userID) {
		if(selectedColor == null) {
			selectedColor = Color.white;
		}
		Color initialcolor = selectedColor;
		Color customColor = JColorChooser.showDialog(null,"select color", initialcolor);
		System.out.println(customColor.getRGB());
		db.AddFTS_Color(customColor, userID);
	}
	
	//*************************************************************************************palettes
	JPanel customPanel;
	int count;
	private JPanel colorPalette(DataBase db,int userID) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		panel.setBackground(Color.black);
		panel.setMaximumSize(new Dimension(110,150));
		
		JPanel paletteSelection = new JPanel();
		JPanel newColor = new JPanel();
		paletteSelection.setBackground(aa_grey);
		paletteSelection.setForeground(Color.white);
		newColor.setBackground(aa_grey);
		newColor.setForeground(Color.white);
		
		String palettesAvailable[] = {"rainbow","greyscale","custom colors"};
		JComboBox<String> palettes = new JComboBox<String>(palettesAvailable);
		palettes.setMaximumSize(new Dimension(100,20));
		
		JButton custom = new JButton("new color");
		
		paletteSelection.setLayout(new FlowLayout(FlowLayout.CENTER));
		paletteSelection.add(palettes);
		newColor.setLayout(new FlowLayout(FlowLayout.CENTER));
		newColor.add(custom);
		
		JPanel cardPanel = new JPanel();
		cardPanel.setBackground(aa_grey);
		CardLayout cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);
		cardPanel.setMaximumSize(new Dimension(300,150));//******************************for color button spacing, kind of
		
		JPanel greyPanel = greyScale();
		JPanel rainbowPanel = rainbow();
		customPanel = custom(db,userID);
		
		cardPanel.add("rainbow",rainbowPanel);
		cardPanel.add("grey",greyPanel);
		cardPanel.add("custom",customPanel);
		
		palettes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(palettes.getItemAt(palettes.getSelectedIndex()) == "greyscale") {
					cardLayout.show(cardPanel,"grey");
				}else if(palettes.getItemAt(palettes.getSelectedIndex()) == "custom colors") {
					cardLayout.show(cardPanel,"custom");
				}else {
					cardLayout.show(cardPanel,"rainbow");
				}
			}
		});
		
		custom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addColor(db,userID);
				cardPanel.remove(2);
				JPanel newCustom = new JPanel();
				newCustom = custom(db,userID);
				cardPanel.add("custom",newCustom);
				cardLayout.show(cardPanel, "custom");
			}
		});
		
		JPanel jp = new JPanel();
		jp.setBackground(aa_grey);
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
		jp.add(Box.createRigidArea(new Dimension(0,20)));
		jp.add(paletteSelection);
		jp.add(newColor);
		
		panel.add(Box.createRigidArea(new Dimension(90,10)));
		panel.add(cardPanel);
		panel.add(jp);
		
		return panel;
	}
	
	ArrayList<JButton> buttons1;
	private JPanel greyScale() {
		buttons1 = new ArrayList<JButton>();
		JPanel gPanel = new JPanel();
		gPanel.setBackground(aa_grey);
		gPanel.setLayout(new BoxLayout(gPanel,BoxLayout.Y_AXIS));
		gPanel.setBackground(aa_grey);
		
		JPanel r1 = new JPanel();
		r1.setBackground(aa_grey);
		JPanel r2 = new JPanel();
		r2.setBackground(aa_grey);
		JPanel r3 = new JPanel();
		r3.setBackground(aa_grey);
		
		ArrayList<Color> colors = new ArrayList<Color>();
		
		colors.add(new Color(255,255,255)); //white
		colors.add(new Color(247,247,247)); //grey 1
		colors.add(new Color(239,239,239)); //grey 2
		colors.add(new Color(231,231,231)); //grey 3
		colors.add(new Color(223,223,223)); //grey 4
		colors.add(new Color(215,215,215)); //grey 5
		colors.add(new Color(207,207,207)); //grey 6
		colors.add(new Color(199,199,199)); //grey 7
		colors.add(new Color(191,191,191)); //grey 8
		colors.add(new Color(183,183,183)); //grey 9
		colors.add(new Color(175,175,175)); //grey 10
		colors.add(new Color(167,167,167)); //grey 11
		colors.add(new Color(159,159,159)); //grey 12
		colors.add(new Color(151,151,151)); //grey 13
		colors.add(new Color(143,143,143)); //grey 14
		colors.add(new Color(135,135,135)); //grey 15
		colors.add(new Color(127,127,127)); //grey 16
		colors.add(new Color(119,119,119)); //grey 17
		colors.add(new Color(111,111,111)); //grey 18
		colors.add(new Color(103,103,103)); //grey 19
		colors.add(new Color(95,95,95)); //grey 20
		colors.add(new Color(87,87,87)); //grey 21
		colors.add(new Color(79,79,79)); //grey 22
		colors.add(new Color(71,71,71)); //grey 23
		colors.add(new Color(63,63,63)); //grey 24
		colors.add(new Color(55,55,55)); //grey 25
		colors.add(new Color(47,47,47)); //grey 26
		colors.add(new Color(39,39,39)); //grey 27
		colors.add(new Color(31,31,31)); //grey 28
		colors.add(new Color(0,0,0)); //black
		
		//use colors to generate button for each color
		buttons1 = colorButtons(colors);
		int third = buttons1.size()/3;
		for(int i=0;i<buttons1.size();i++) {
			JButton b = buttons1.get(i);
			int k = i;
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					b.setBorder(new LineBorder(Color.white, 2, true));
					for(int j=0;j<buttons1.size();j++) {
						if(j != k) {
							buttons1.get(j).setBorder(line);
						}
					}
				}
			});
			buttons1.get(i).setContentAreaFilled(false);
			if(i<third) {
				r1.add(buttons1.get(i));
			}else if(i<third*2) {
				r2.add(buttons1.get(i));
			}else {
				r3.add(buttons1.get(i));
			}
		}
		gPanel.add(r1);
		gPanel.add(r2);
		gPanel.add(r3);
		
		return gPanel;
	}
	
	ArrayList<JButton> buttons2;
	private JPanel rainbow() {
		JPanel rPanel = new JPanel();
		rPanel.setBackground(aa_grey);
		rPanel.setLayout(new BoxLayout(rPanel,BoxLayout.Y_AXIS));
		buttons2 = new ArrayList<JButton>();
		ArrayList<Color> colors = new ArrayList<Color>();
		
		//make colors as list
		colors.add(new Color(102,0,0)); //dark red
		colors.add(new Color(102,51,0)); //dark orange
		colors.add(new Color(102,102,0)); //dark yellow
		colors.add(new Color(0,102,0)); //dark green
		colors.add(new Color(0,102,102)); //dark aqua
		colors.add(new Color(0,51,102)); //dark teal
		colors.add(new Color(0,0,102)); //dark blue
		colors.add(new Color(51,0,102)); //dark purple
		colors.add(new Color(102,0,102)); //dark fusia
		colors.add(new Color(102,0,51)); //dark raspberry
		
		colors.add(new Color(255,0,0)); //red
		colors.add(new Color(255,128,0)); //orange
		colors.add(new Color(255,255,0)); //yellow
		colors.add(new Color(0,255,0)); //green
		colors.add(new Color(0,255,255)); //aqua
		colors.add(new Color(0,128,255)); //teal
		colors.add(new Color(0,0,255)); //blue
		colors.add(new Color(127,0,255)); //purple
		colors.add(new Color(255,0,255)); //fuscia
		colors.add(new Color(255,0,127)); //raspberry
		
		colors.add(new Color(255,153,153)); //light red
		colors.add(new Color(255,204,153)); //light orange
		colors.add(new Color(255,255,153)); //light yellow
		colors.add(new Color(204,255,153)); //light green
		colors.add(new Color(153,255,255)); //light aqua
		colors.add(new Color(153,204,255)); //light teal
		colors.add(new Color(153,153,255)); //light blue
		colors.add(new Color(204,153,255)); //light purple
		colors.add(new Color(255,153,255)); //light fuscia
		colors.add(new Color(255,153,204)); //light raspberry
		
		JPanel r1 = new JPanel();
		r1.setBackground(aa_grey);
		JPanel r2 = new JPanel();
		r2.setBackground(aa_grey);
		JPanel r3 = new JPanel();
		r3.setBackground(aa_grey);
		
		//use colors to generate button for each color
		buttons2 = colorButtons(colors);
		int third = buttons2.size()/3;
		for(int i=0;i<buttons2.size();i++) {
			JButton b = buttons2.get(i);
			int k = i;
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					b.setBorder(new LineBorder(Color.white, 2, true));
					for(int j=0;j<buttons2.size();j++) {
						if(j != k) {
							buttons2.get(j).setBorder(line);
						}
					}
				}
			});
			buttons2.get(i).setContentAreaFilled(false);
			if(i<third) {
				r1.add(buttons2.get(i));
			}else if(i<third*2) {
				r2.add(buttons2.get(i));
			}else {
				r3.add(buttons2.get(i));
			}
		}
		
		rPanel.add(r1);
		rPanel.add(r2);
		rPanel.add(r3);
		
		return rPanel;
	}
	
	ArrayList<JButton> buttons;
	private JPanel custom(DataBase db,int userID) {
		buttons = new ArrayList<JButton>();
		JPanel cPanel = new JPanel();
		cPanel.setBackground(aa_grey);
		//get colors from database, make buttons with
		ArrayList<Color> colors = db.SelectAllFTS_Color(userID);
		
		if(colors.size() > 0) {
			buttons = colorButtons(colors);
			
			JPanel r1 = new JPanel();
			r1.setBackground(aa_grey);
			JPanel r2 = new JPanel();
			r2.setBackground(aa_grey);
			JPanel r3 = new JPanel();
			r3.setBackground(aa_grey);
			
			for(int i=0;i<buttons.size();i++) {
				JButton b = buttons.get(i);
				
				if(i<10) {
					r1.add(buttons.get(i));
				}else if(i<20) {
					r2.add(buttons.get(i));
				}else {
					r3.add(buttons.get(i));
				}
				
				int k = i;
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						customSelected = colors.get(k);
						b.setBorder(new LineBorder(Color.white, 2, true));
						for(int j=0;j<buttons.size();j++) {
							if(j != k) {
								buttons.get(j).setBorder(line);
								
							}
						}
					}
				});
			}
			
			cPanel.add(r1);
			if(buttons.size() >= 10) {
				cPanel.add(r2);
			}if(buttons.size() >= 20) {
				cPanel.add(r3);
			}
			
		}
		return cPanel;
	}
	
	private ArrayList<JButton> colorButtons(ArrayList<Color> colors){
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		int i;
		for(i=0;i<colors.size();i++) {
			Color current = colors.get(i);
			
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("images/square.png"));
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			
			colorIcon(img,current);
			JButton button = new JButton();
			button.setIcon(new ImageIcon(img));
			button.setBorder(line);
			button.setContentAreaFilled(false);
			button.setMargin(new Insets(0,0,0,0));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedColor = current;
					button.setBorder(lineSelected);
				}
			});
			buttons.add(button);
		}
		return buttons;
	}
	
	public BufferedImage colorIcon(BufferedImage image,Color current) {
		//get new red, green, blue values from color
		int red = current.getRed();
		int green = current.getGreen();
		int blue = current.getBlue();
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

		JLabel title = new JLabel("Free Thought Space");
		title.setForeground(Color.white);
		//title.setBounds(0,0,200,200);
		title.setFont(new Font("Dosis SemiBold", Font.BOLD, 20));
		
		JMenu file = new JMenu("file");
		file.setFont(new Font("Dosis SemiBold", Font.BOLD, 15));
		file.setBackground(aa_grey);
		file.setForeground(Color.white);
		file.setBorder(purpLine);
		
		JMenuItem newFile = new JMenuItem("new");
		newFile.setBackground(aa_grey);
		newFile.setForeground(aa_purple);
		newFile.setBorderPainted(false);
		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		
		JMenuItem open = new JMenuItem("open");
		open.setBackground(aa_grey);
		open.setForeground(aa_purple);
		open.setBorderPainted(false);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				//open jpeg or png
				JFileChooser ch = getFileChooser();                                            //open file chooser
                int result = ch.showOpenDialog(drawingSpace);
                if (result==JFileChooser.APPROVE_OPTION )                                      //if OK
                {
                	BufferedImage image = null;
                    try{
                        image = ImageIO.read(ch.getSelectedFile()); //set current image to isSelected image
                    }catch (IOException ex){
                        JOptionPane.showMessageDialog(null, "Could not open file");
                    }
					addedLayers.push(new Layer(image.getWidth(),image.getHeight(),"image",image));
					repaintDS();
                }
			}
		});
		
		JMenuItem save = new JMenuItem("save");
		save.setBackground(aa_grey);
		save.setForeground(aa_purple);
		save.setBorderPainted(false);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//save as png
				JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showSaveDialog(drawingSpace);
                if (result==JFileChooser.APPROVE_OPTION )
                {
                    try
                    {
                        File selectedFile = jFileChooser.getSelectedFile();
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".png");      //get isSelected file
                        BufferedImage img = getScreenShot(drawing);            //get current image screenshot
                        ImageIO.write(img, "png", selectedFile);                               //write the image to the isSelected file
                    } catch (IOException ioe)
                    {
                        JOptionPane.showMessageDialog(null, "Could not save the file");
                    }
                }
			}
		});
		
		file.add(newFile);
		file.add(open);
		file.add(save);
		
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
		guide.setFocusPainted(false);
		guide.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//opens guide
        		Guide guide = new Guide();
				guide.open_Guide("Free Thought Space");
        }});
		
		title_panel.add(file);
		title_panel.add(Box.createRigidArea(new Dimension(500, 0)));
		title_panel.add(title);
		title_panel.add(Box.createRigidArea(new Dimension(450, 0)));
		title_panel.add(guide);
		title_panel.add(close_window);
		
		//returns panel
		return title_panel;
	}
	
	public JFileChooser getFileChooser()
    {
        if (fileChooser ==null)
        {
            fileChooser = new JFileChooser();                        //create file chooser
            fileChooser.setFileFilter(new PNGFileFilter());         //set file extension to .png
        }
        return fileChooser;
    }
	
	public static BufferedImage getScreenShot(Component component)    //used to get the current image drawn on the screen
    {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());   // paints into image's Graphics
        return image;
    }
	
	private static class PNGFileFilter extends FileFilter
    {
        public boolean accept(File file)             //filer files to display
        {
            return file.getName().toLowerCase().endsWith(".png") || file.isDirectory();
        }

        public String getDescription()
        {
            return "PNG image  (*.png) ";
        }
    }
	
}
