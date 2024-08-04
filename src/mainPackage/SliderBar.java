package mainPackage;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;

public class SliderBar extends component implements MouseMotionListener,MouseListener{

	int sizeX,sizeY;
	ImageIcon slider, bar,sliderEnd;
	Text zero, full,volume;
	boolean Released = true;
	
	
	public SliderBar(int x, int y, int sizeX, int sizeY, String text,Main main) {
		super(x, y, text);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		main.addMouseMotionListener(this);
		main.addMouseListener(this);
		
		slider = new ImageIcon(getClass().getResource("/slider.png"));
		sliderEnd = new ImageIcon(getClass().getResource("/slider_end.png"));
		bar = new ImageIcon(getClass().getResource("/slider_bar.png"));
		
		zero = new Text(x, y+40, "0");
		zero.setFont(new Font("Gisha", Font.BOLD, 12));
		full = new Text(x + sizeX - 20, y+40, "Full");
		full.setFont(new Font("Gisha", Font.BOLD, 12));
		volume = new Text(x , y-15, "Volume");
	}
	
	
	@Override
	public void rander(Graphics g) {
		zero.rander(g);
		full.rander(g);
		volume.rander(g);
		g.drawImage(sliderEnd.getImage(), x, y, 20, 20, null);
		g.drawImage(sliderEnd.getImage(), x+sizeX-20, y, 20, 20, null);
		g.drawImage(slider.getImage(), x, y, sizeX, sizeY, null);
		g.drawImage(bar.getImage(), (int) (x + (sizeX*Main.game.p.getVolume()))-20, y-7, 40, 40, null);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if((e.getX() <= x+sizeX) && (e.getX() >= x) && (e.getY() <= y+sizeY) && (e.getY() >= y)) {
			Released = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Released = true;		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
				
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}


	@Override
	public void mouseDragged(MouseEvent e) {
		if((e.getX() <= x+sizeX) && (e.getX() >= x) && !Released) {
			Main.game.p.setVolume(((e.getX() - (float)x)/(float)sizeX));
		}	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
