package mainPackage;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

public class Button extends component implements MouseListener{
	
int sizeX,sizeY;
public boolean press = false;
boolean pressing = false,buttonEnabled = true;
String FileLocation;
Color mycolor = new Color(226, 226, 226, 244);
public ImageIcon image,imagepress;

	public Button(int x ,int y, int sizeX, int sizeY, String text,Main main) {
		super(x, y, text);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		main.addMouseListener(this);
	}
	
	public Button(int x ,int y, int sizeX, int sizeY, String text,String FileLocation,Main main) {
		super(x, y, text);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.FileLocation = FileLocation;
		main.addMouseListener(this);
		image = new ImageIcon(getClass().getResource("/"+FileLocation+".png"));
		imagepress = new ImageIcon(getClass().getResource("/"+FileLocation+"_press.png"));
	}
	
	//draw the button on the screen
	@Override
	public void rander(Graphics g) {
		if(!buttonEnabled)return;
		g.setColor(mycolor);
		if(FileLocation == null) {
			g.setColor(Color.black);
			FontMetrics metrics = g.getFontMetrics(font);
			g.setFont(font);
			g.drawString(text, (x + ((sizeX - metrics.stringWidth(text)) / 2)), (y + ((sizeY - metrics.getHeight()) / 2) + metrics.getAscent()));
		}else {
			if(!pressing) {
				g.drawImage(image.getImage(), x, y, sizeX, sizeY, null);
			}else {
				g.drawImage(imagepress.getImage(), x, y, sizeX, sizeY, null);
			}
		}
	}
		
	public void setEnabled(boolean enabled) {
		buttonEnabled = enabled;
		if(enabled) {
			mycolor = new Color(mycolor.getRed(),mycolor.getGreen(),mycolor.getBlue(),244);
		}else {
			mycolor = new Color(mycolor.getRed(),mycolor.getGreen(),mycolor.getBlue(),154);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	//check if the button pressed
	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX());
		if((e.getX() <= x+sizeX) && (e.getX() >= x) && (e.getY() <= y+sizeY) && (e.getY() >= y) && buttonEnabled) {
			pressing = true;
			press = true;
		}
	}

	//check if the button released
	@Override
	public void mouseReleased(MouseEvent e) {
		pressing = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void setImage(String FileLocation) {
		this.FileLocation = FileLocation;
		image = new ImageIcon(getClass().getResource(FileLocation));
	}
}
