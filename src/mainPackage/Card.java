package mainPackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class Card {
int x,y,sizeX = 84, sizeY = 120;
public int points,face;
public int angle = 0;
public Boolean FaceUp = true;
String FileLocation,BackFileLocation = "/cardback.PNG";
public ImageIcon image, backimg;

	public Card(int points, int face, String FileLocation) {
		super();
		this.points = points;
		this.FileLocation = FileLocation;
		this.face = face;
		image = new ImageIcon(getClass().getResource(FileLocation));
		backimg = new ImageIcon(getClass().getResource(BackFileLocation));
	}

	//add the card to the screen
	public void AddCard(int x, int y) {
		this.x = x;
		this.y = y;
		}
	
	public void render(Graphics g) {
		if(FaceUp) {
			Graphics2D g2d =  (Graphics2D) g;
			AffineTransform old = g2d.getTransform();
			g2d.rotate(Math.toRadians(angle),x+(sizeX/2),y+(sizeY/2));
			g2d.drawImage(image.getImage(), x, y, sizeX, sizeY,null);
	        g2d.setTransform(old);
		}else {
			g.drawImage(backimg.getImage(), x, y, sizeX, sizeY,null);
		}

	}
	

	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}