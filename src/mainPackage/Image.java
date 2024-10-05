package mainPackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class Image extends component{
	
	int sizeX,sizeY,angle = 0;
	String FileLocation;
	ImageIcon image;
	
	public Image(int x, int y, String text,int sizeX,int sizeY,String FileLocation) {
		super(x, y,text);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.FileLocation = FileLocation; 
		image = new ImageIcon(getClass().getResource(FileLocation));
	}

	@Override
	public void rander(Graphics g) {
		Graphics2D g2d =  (Graphics2D) g;	
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(angle),x+(sizeX/2),y+(sizeY/2));
		g2d.drawImage(image.getImage(), x, y, sizeX, sizeY,null);
        g2d.setTransform(old);
	}
	
	public String getFileLocation() {
		return FileLocation;
	}
	
	public void SetFileLocation(String FileLocation) {
		this.FileLocation = FileLocation;
	}
	
	public void SetAngle(int angle) {
		this.angle = angle;
	}
}
