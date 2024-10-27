package game;

import java.awt.Graphics;
import java.util.ArrayList;
import mainPackage.Audio;
import mainPackage.Button;
import mainPackage.Coins;
import mainPackage.Main;
import mainPackage.component;
import mainPackage.placeYourBet;

public class BetMenu extends component{
	
	Main main;
	Button coin1,coin2,coin3,coin4,coin5;
	public ArrayList<Button> coinsB;
	public int targetX = 471,targetY = 675,bet_choose = 1;
	public boolean showBetCoins = true;
	public Audio audio = new Audio();

	
	public BetMenu(int x, int y, String text,Main main) {
		super(x, y, text);
		this.main = main;
		
		coin1 = new Button(x, y,60,45,"10", "10coin",main);
		coin2 = new Button(x-60, y-17,60,45,"50", "50coin",main);
		coin3 = new Button(x-120, y-35, 60,45,"100", "100coin",main);
		coin4 = new Button(x-180, y-55, 60,45,"500", "500coin",main);
		coin5 = new Button(x-240, y-80, 60,45,"1000", "1000coin",main);
		
		coinsB = new ArrayList<Button>();
		coinsB.add(coin1);
		coinsB.add(coin2);
		coinsB.add(coin3);
		coinsB.add(coin4);
		coinsB.add(coin5);

	}
	
	public void tick() {
		for(Button i : coinsB) {
			if(i.press && Main.game.p.money - Main.game.p.Bet - Integer.valueOf(i.getText()) >= 0) {
				i.press = false;
				showBetCoins = true;
				Main.gamescreen.clear.setEnabled(true);
				Main.gamescreen.reBet.setEnabled(false);
				Main.gamescreen.placebet.get(bet_choose).bet += Integer.valueOf(i.getText());
				Main.game.p.Bet += Integer.valueOf(i.getText());
				Coins coin = new Coins(i.getX(), i.getY(), "",targetX,targetY);
				audio.PlayAudio("coin");
				switch (Integer.valueOf(i.getText())) {
				case 10:
					coin.setTextImage("/10coin.png", 60, 45);
					break;
				case 50:
					coin.setTextImage("/50coin.png", 60, 45);
					break;
				case 100:
					coin.setTextImage("/100coin.png", 60, 45);
					break;
				case 500:
					coin.setTextImage("/500coin.png", 60, 45);
					break;
				case 1000:
					coin.setTextImage("/1000coin.png", 60, 45);
					break;
				}
				Main.gamescreen.placebet.get(bet_choose).coins.add(coin);
				Main.gamescreen.placebet.get(bet_choose).coins.get(Main.gamescreen.placebet.get(bet_choose).coins.size()-1).setcoinNumber(Main.gamescreen.placebet.get(bet_choose).coins.size()-1);
				Main.gamescreen.placebet.get(bet_choose).playerBet.setText(String.valueOf(Main.gamescreen.placebet.get(bet_choose).bet));
				Main.gamescreen.placebet.get(bet_choose).playerBet.setLocation(targetX+9, targetY+67);
				
				for(playerDeck j : Main.game.p.playerdecks) {
					j.coins.clear();
					j.playerBet.setText("");
				}
			}else if(i.press) {
				i.press = false;
			}
		}
		for(placeYourBet i : Main.gamescreen.placebet) {
			for(Coins j : i.coins){
				if((j.getX() < j.targetX || j.getY() > (j.targetY-(i.coins.size()*5)))) {
					j.tick();
				
				}
			}
		}
		
		
		if(Main.homescreen.memberLogin && Main.gamescreen.placebet.get(bet_choose).bet == 0) {
			Main.gamescreen.clear.setEnabled(false);
		}
		if (Main.homescreen.memberLogin && Main.gamescreen.placebet.get(bet_choose).oldBet == 0) {
			Main.gamescreen.reBet.setEnabled(false);
		}
	}
	
	@Override
	public void rander(Graphics g) {
		for(Button i : coinsB) {
			i.rander(g);
		}

	}
	
	public void resetBet() {
		Main.game.p.Bet -= Main.gamescreen.placebet.get(bet_choose).bet;
		Main.gamescreen.placebet.get(bet_choose).bet = 0;
		Main.gamescreen.placebet.get(bet_choose).coins.clear();
		for(playerDeck i : Main.game.p.playerdecks) {
			i.coins.clear();
			i.playerBet.setText("");
		}
		Main.gamescreen.placebet.get(bet_choose).playerBet.setText("");
		Main.gamescreen.betmenu.showBetCoins = true;
	}


}
