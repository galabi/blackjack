package mainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;



public class Textbox extends component implements KeyListener,MouseListener,MouseMotionListener{

	int sizeX,sizeY,mouseX,mouseY;
	FontMetrics metrics;
	public int pointerLocation;
	boolean press = false;
	public boolean enter = false,release = false,textBoxEnabled = false;
	
		public Textbox(int x ,int y, int sizeX, int sizeY, String text,Main main) {
			super(x, y, text);
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			pointerLocation = getText().length();
			main.addKeyListener(this);
			main.addMouseListener(this);
			main.addMouseMotionListener(this);
			font = new Font("Gisha", Font.PLAIN, 16);
		}
		
		//draw the text box on the screen
		@Override
		public void rander(Graphics g) {
			String textbox = getText();
			g.setColor(Color.white);
			g.fillRect(x, y, sizeX, sizeY);
			g.draw3DRect(x, y, sizeX, sizeY, !press);
			g.setColor(Color.black);
			metrics = g.getFontMetrics(font);
			g.setFont(font);
			if (press) {
				textbox = textbox.substring(0, pointerLocation) + "|"+ textbox.substring(pointerLocation, textbox.length());

				g.drawString(textbox, x+5, (y + 1 + ((sizeY - metrics.getHeight()) / 2) + metrics.getAscent()));
			}else {
				g.drawString(textbox, x+5, (y + 1 + ((sizeY - metrics.getHeight()) / 2) + metrics.getAscent()));
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getExtendedKeyCode() != KeyEvent.VK_BACK_SPACE  && e.getExtendedKeyCode() != KeyEvent.VK_ESCAPE && press) {
				text = getText().substring(0, pointerLocation) + e.getKeyChar() + getText().substring(pointerLocation, getText().length());
				pointerLocation++;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(getText().length() > 0 && press) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_BACK_SPACE: 
					if(pointerLocation > 0) {
						text = getText().substring(0, pointerLocation-1) + getText().substring(pointerLocation, getText().length());
						pointerLocation--;
					}
					break;
				case KeyEvent.VK_LEFT: 
					if(release && pointerLocation > 0) {
						release = false;
						pointerLocation--;
					}
					break;
				case KeyEvent.VK_RIGHT: 
					if(release && pointerLocation < getText().length()) {
						release = false;
						pointerLocation++;
					}
					break;

				}
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				enter = true;
				press = false;
			}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE && press) {
				press = false;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			release = true;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			enter = false;
			
			if((e.getX() <= x+sizeX) && (e.getX() >= x) && (e.getY() <= y+sizeY) && (e.getY() >= y) && textBoxEnabled) {
				press = true;
				for(int i = 1; i <= text.length();i++) {
					if(e.getX()<= x+5+metrics.stringWidth(text.substring(0,i))) {
						pointerLocation = i-1;
						break;
					}else if(e.getX() > x+5+metrics.stringWidth(text)) {
						pointerLocation = text.length();
						break;
					}
				}
			}else {
				press = false;
			}
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		
		@Override
		public void mouseDragged(MouseEvent e) {

			
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}
		
		

}
