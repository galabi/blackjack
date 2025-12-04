package mainPackage;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class placeYourBet extends Button{

	public int bet = 0,oldBet = 0;
	public Text playerBet;
	public ArrayList<Coins> coins;
	
	public placeYourBet(int x, int y, int sizeX, int sizeY, String text, String FileLocation, Main main) {
		super(x, y, sizeX, sizeY, text, FileLocation, main);
		playerBet = new Text(x-75, y+127, "");
		coins = new ArrayList<Coins>();
	}
	
	//draw the button on the screen
	@Override
	public void render(Graphics g) {
		if(!buttonEnabled)return;
		Graphics2D g2d =  (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(angle),x,y);
		if(!press){
			g2d.drawImage(image.getImage(), x, y, sizeX, sizeY, null);
		}else {
			g2d.drawImage(imagepress.getImage(), x, y, sizeX, sizeY, null);
		}
        g2d.setTransform(old);
        

		if(Main.gamescreen.betmenu.showBetCoins && coins.size() > 0) {
	        for(Coins i: coins) {
	        	i.render(g);
	        }
			FontMetrics metrics = g.getFontMetrics(font);
			playerBet.setX(coins.get(0).targetX + ((60 - metrics.stringWidth(playerBet.getText())) / 2));
			playerBet.render(g);
		}
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
			//set all the button don't be press
			for(placeYourBet i : Main.gamescreen.placebet) {
				i.press = false;
			}
			//set the button that was press to press
			press = true;
			pressing = false;
			if(Main.gamescreen.placebet.get(0).press) {
				Main.gamescreen.betmenu.bet_choose = 0;
				Main.gamescreen.betmenu.targetX = 177;
				Main.gamescreen.betmenu.targetY = 652;
				if(coins.size()>0) {
					Main.gamescreen.clear.buttonEnabled = true;
				}
			}else if(Main.gamescreen.placebet.get(1).press){
				Main.gamescreen.betmenu.bet_choose = 1;
				Main.gamescreen.betmenu.targetX = 471;
				Main.gamescreen.betmenu.targetY = 675;
				if(coins.size()>0) {
					Main.gamescreen.clear.buttonEnabled = true;
				}
			}else {
				Main.gamescreen.betmenu.bet_choose = 2;
				Main.gamescreen.betmenu.targetX = 751;
				Main.gamescreen.betmenu.targetY = 675;
				if(coins.size()>0) {
					Main.gamescreen.clear.buttonEnabled = true;
				}
			}

			
		}
	}
	
	public ArrayList<Coins> getCoins(){
		oldBet = bet;
		return coins;
	} 
}
