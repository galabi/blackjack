package mainPackage;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Card {
int x,y,sizeX = 50, sizeY = 80;
public int points,face;
public Boolean FaceUp = true;
String FileLocation,BackFileLocation = "/cardback.PNG";;
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
			g.drawImage(image.getImage(), x, y, sizeX, sizeY,null);
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
	
}