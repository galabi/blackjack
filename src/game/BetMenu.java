package game;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import mainPackage.Audio;
import mainPackage.Button;
import mainPackage.Coins;
import mainPackage.Main;
import mainPackage.Text;
import mainPackage.component;

public class BetMenu extends component{
	
	Main main;
	Button coin1,coin2,coin3,coin4,coin5;
	Text playerBet;
	public ArrayList<Coins> coins;
	public ArrayList<Button> coinsB;
	public int bet = 0,oldBet = 0;
	public boolean showBetCoins = true;
	public Audio audio = new Audio();

	
	public BetMenu(int x, int y, String text,Main main) {
		super(x, y, text);
		this.main = main;
		
		coin1 = new Button(x, y,40,30,"10", "10coin",main);
		coin2 = new Button(x-40, y-12,40,30,"50", "50coin",main);
		coin3 = new Button(x-80, y-24, 40,30,"100", "100coin",main);
		coin4 = new Button(x-120, y-38, 40,30,"500", "500coin",main);
		coin5 = new Button(x-160, y-55, 40,30,"1000", "1000coin",main);
		
		playerBet = new Text(317, 495, "");
		
		coins = new ArrayList<Coins>();
		coinsB = new ArrayList<Button>();
		coinsB.add(coin1);
		coinsB.add(coin2);
		coinsB.add(coin3);
		coinsB.add(coin4);
		coinsB.add(coin5);

	}
	
	public void tick() {
		for(Button i : coinsB) {
			if(i.press && Main.game.p.money -bet - Integer.valueOf(i.getText()) >= 0) {
				i.press = false;
				showBetCoins = true;
				Main.gamescreen.clear.setEnabled(true);
				Main.gamescreen.reBet.setEnabled(false);
				bet += Integer.valueOf(i.getText());
				Main.game.p.Bet = bet;
				Coins coin = new Coins(i.getX(), i.getY(), "",317,450);
				audio.PlayAudio("coin");
				switch (Integer.valueOf(i.getText())) {
				case 10:
					coin.setTextImage("/10coin.png", 40, 30);
					coins.add(coin);
					break;
				case 50:
					coin.setTextImage("/50coin.png", 40, 30);
					coins.add(coin);
					break;
				case 100:
					coin.setTextImage("/100coin.png", 40, 30);
					coins.add(coin);
					break;
				case 500:
					coin.setTextImage("/500coin.png", 40, 30);
					coins.add(coin);
					break;
				case 1000:
					coin.setTextImage("/1000coin.png", 40, 30);
					coins.add(coin);
					break;
				}
				coins.get(coins.size()-1).setcoinNumber(coins.size()-1);
				playerBet.setText(String.valueOf(bet));
				for(playerDeck j : Main.game.p.playerdecks) {
					j.coins.clear();
					j.playerBet.setText("");
				}
			}else if(i.press) {
				i.press = false;
			}
		}
		for(Coins i : coins){
			if((i.getX() < 317 || i.getY() > (450-(coins.size()*5)))) {
				i.tick();
				
			}
		}
		
		if(Main.homescreen.memberLogin && bet == 0) {
			Main.gamescreen.clear.setEnabled(false);
		}
		if (Main.homescreen.memberLogin && oldBet == 0) {
			Main.gamescreen.reBet.setEnabled(false);
		}
	}
	
	@Override
	public void rander(Graphics g) {
		for(Button i : coinsB) {
			i.rander(g);
		}
		if(showBetCoins) {
			for(Text i : coins) {
				i.rander(g);
			}
			FontMetrics metrics = g.getFontMetrics(font);
			playerBet.setX(317 + ((40 - metrics.stringWidth(playerBet.getText())) / 2));
			playerBet.rander(g);
		}

	}
	
	public void resetBet() {
		bet = 0;
		coins.clear();
		Main.game.p.setBet(0);
		for(playerDeck i : Main.game.p.playerdecks) {
			i.coins.clear();
			i.playerBet.setText("");
		}
		playerBet.setText("");
	}
	public ArrayList<Coins> getCoins(){
		return coins;
	}

}
