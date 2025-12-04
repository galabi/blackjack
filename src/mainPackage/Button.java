package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class Button extends component implements MouseListener{
	
int sizeX,sizeY,angle = 0;
public boolean press = false;
boolean pressing = false,buttonEnabled = true;
String FileLocation;
Color mycolor = new Color(226, 226, 226, 244);
public ImageIcon image,imagepress;


	
	public Button(int x ,int y, int sizeX, int sizeY, String text,String FileLocation,Main main) {
		super(x, y, text);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.FileLocation = FileLocation;
		main.addMouseListener(this);
		image = new ImageIcon(getClass().getResource("/"+FileLocation+".png"));
		try {
			imagepress = new ImageIcon(getClass().getResource("/"+FileLocation+"_press.png"));
		}catch (Exception e) {
			imagepress = image;
		}
	}
	
	//draw the button on the screen
	@Override
	public void render(Graphics g) {
		if(!buttonEnabled)return;
		Graphics2D g2d =  (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(angle),x,y);
		if(!pressing) {
			g2d.drawImage(image.getImage(), x, y, sizeX, sizeY, null);
		}else {
			g2d.drawImage(imagepress.getImage(), x, y, sizeX, sizeY, null);
		}
        g2d.setTransform(old);
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
		if((e.getX() <= x+sizeX-3) && (e.getX() >= x+3) && (e.getY() <= y+sizeY-5) && (e.getY() >= y+5) && buttonEnabled) {
			pressing = true;
		}
	}

	//check if the button released
	@Override
	public void mouseReleased(MouseEvent e) {
		if((e.getX() <= x+sizeX-3) && (e.getX() >= x+3) && (e.getY() <= y+sizeY-5) && (e.getY() >= y+5) && buttonEnabled && pressing) {
			press = true;
		}
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
	
	public void setAngle(int angle) {
		this.angle = angle;
	}
}
