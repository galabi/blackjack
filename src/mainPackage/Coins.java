package mainPackage;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Coins extends Text{
	public double speedX = 0,speedY = 0,doubleX,doubleY;
	public int movingTimes = 30,coinNumber=0,targetX,targetY;
	boolean Enabled = true;
	
	public Coins(int x, int y, String text,int targetX,int targetY) {
		super(x, y, text);
		doubleX = x;
		doubleY = y;
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public void tick() {
		if(speedX == 0) {
			speedX = (double)(targetX-x)/movingTimes; 
			speedY = (double)((targetY-(coinNumber*7))-y)/movingTimes; 
			Enabled = true;
		}
		if(movingTimes != 0) {
			doubleX = doubleX + speedX;
			doubleY = doubleY + speedY;
			x=(int)doubleX;
			y=(int)doubleY;
			movingTimes--;
			if(movingTimes == 0) {
				setLocation(targetX, targetY-(coinNumber*7));
				if(targetX == 570 && targetY == 60) {
					Enabled = false;
				}
			}
		}
		
	}
	@Override
	public void setLocation(int x,int y) {
		this.x = x;
		this.y = y;
		doubleX = x;
		doubleY = y;
		this.targetX = x;
		this.targetY = y+(coinNumber*7);
	}
	
	public ImageIcon getImage() {
		return image;	
	}
	
	public void setcoinNumber(int coinNumber) {
		this.coinNumber = coinNumber;
	}
	
	@Override
	public void render(Graphics g) {
		if(Enabled) {
			g.drawImage(image.getImage(), x, y, sizeX, sizeY,null);
		}
	}
}
