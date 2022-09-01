package AttentionAssistant;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Layer {
	private int mouseClickX,mouseClickY,mouseReleaseX,mouseReleaseY,group;
	private Color primaryColor,secondaryColor;
	private int stroke;
	private String text;
	private String tool;
	private Font font;
	private boolean isFilled;
	private int rand1,rand2;
	private BufferedImage image;
	
	//opened image
	public Layer(int width,int height,String tool,BufferedImage image) {
		this.mouseClickX = 0;
		this.mouseClickY = 0;
		this.mouseReleaseX = width;
		this.mouseReleaseY = height;
		this.tool = tool;
		this.image = image;
	}
	
	//grouped
	public Layer(int mouseClickX,int mouseClickY,int mouseReleaseX,int mouseReleaseY,Color primaryColor,Color secondaryColor,int stroke,String tool,int group) {
		this.mouseClickX = mouseClickX;
		this.mouseClickY = mouseClickY;
		this.mouseReleaseX = mouseReleaseX;
		this.mouseReleaseY = mouseReleaseY;
		this.primaryColor = primaryColor;
		this.stroke = stroke;
		this.tool = tool;
		this.group = group;
	}
	
	//spray, grouped
	public Layer(int mouseClickX,int mouseClickY,int mouseReleaseX,int mouseReleaseY,Color primaryColor,Color secondaryColor,int stroke,String tool,int group,int rand1,int rand2) {
		this.mouseClickX = mouseClickX;
		this.mouseClickY = mouseClickY;
		this.mouseReleaseX = mouseReleaseX;
		this.mouseReleaseY = mouseReleaseY;
		this.primaryColor = primaryColor;
		this.stroke = stroke;
		this.tool = tool;
		this.group = group;
		this.rand1 = rand1;
		this.rand2 = rand2;
	}
	
	//not grouped
	public Layer(int mouseClickX,int mouseClickY,int mouseReleaseX,int mouseReleaseY,Color primaryColor,Color secondaryColor,int stroke,String tool,boolean isFilled) {
		this.mouseClickX = mouseClickX;
		this.mouseClickY = mouseClickY;
		this.mouseReleaseX = mouseReleaseX;
		this.mouseReleaseY = mouseReleaseY;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.stroke = stroke;
		this.tool = tool;
		this.group = 0;
		this.isFilled = isFilled;
	}
	
	//text
	public Layer(int mouseClickX,int mouseClickY,int fontSize,Color primaryColor,Color secondaryColor,String text,String tool,Font font,int stroke) {
		this.mouseClickX = mouseClickX;
		this.mouseClickY = mouseClickY;
		this.mouseReleaseY = 0;
		this.mouseReleaseX = fontSize;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.text = text;
		this.tool = tool;
		this.font = font;
		this.stroke = stroke;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	public int getStroke() {
		return this.stroke;
	}
	public int getMouseClickX() {
		return this.mouseClickX;
	}
	
	public int getMouseClickY() {
		return this.mouseClickY;
	}
	
	public int getMouseReleaseX() {
		return this.mouseReleaseX;
	}
	
	public int getMouseReleaseY() {
		return this.mouseReleaseY;
	}
	
	public Color getPrimary() {
		return this.primaryColor;
	}
	
	public Color getSecondary() {
		return this.secondaryColor;
	}
	public String getText() {
		return this.text;
	}
	public String getTool() {
		return this.tool;
	}
	public boolean getIsFilled() {
		return this.isFilled;
	}
	public Font getFont() {
		return this.font;
	}
	public int getGroup() {
		return this.group;
	}
	public int getRand1() {
		return this.rand1;
	}
	public int getRand2() {
		return this.rand2;
	}
}
