package game;

import java.awt.Graphics;
import java.util.ArrayList;

import mainPackage.Button;
import mainPackage.Coins;
import mainPackage.Image;
import mainPackage.Main;
import mainPackage.Settings;
import mainPackage.Text;
import mainPackage.placeYourBet;

public class GameScreen {

	Main main;
	public Text balance,balanceCoin;
	public Image hand;
	public Button deal,hit,stand,split,Double,clear,reBet,backToMain,settings;
	public ArrayList<placeYourBet> placebet;
	public Settings settingsScreen;
	public BetMenu betmenu;
	
	public GameScreen(Main main) {
		this.main = main;
		
		settingsScreen = new Settings(main);
		betmenu = new BetMenu(250,780,"betmanu",main);
		
		//open the single player game mode screen
		//labels
		balance = new Text(105, 190, "");
		balance.setTextImage("/balance.png", 0, 0);
		balanceCoin = new Text(30,172, "");
		balanceCoin.setTextImage("/balance.png", 127, 37);
		hand = new Image(480, 345, "",225,225,"/Hand.png");
 		hand.SetAngle(180);
		//button
		deal = new Button(1050, 800 ,114 ,60 ,"Deal" ,"deal",main);
		clear = new Button(937, 800 ,114 ,60 ,"Clear" ,"clear",main);
		reBet = new Button(937, 800 ,114 ,60 ,"Rebet" ,"rebet",main);
		hit = new Button(330, 800, 114 ,60 ,"Hit" ,"hit",main);
		stand = new Button(480, 800,114 ,60 ,"Stand" ,"stand",main);
		split = new Button(780, 800,114 ,60 ,"Split" ,"split",main);
		Double = new Button(630, 800,114 ,60 ,"Double" ,"double",main);
		hit.setEnabled(false);
		stand.setEnabled(false);
		split.setEnabled(false);
		Double.setEnabled(false);
		reBet.setEnabled(false);
		backToMain = new Button(30, 30, 60, 60 ,"Back" , "back",main);
		settings = new Button(1110, 30, 60, 60, "settings","settings", main);
		placebet = new ArrayList<placeYourBet>();
		placebet.add(new placeYourBet(271, 562, 130, 127, "placebet", "placebet",main));
		placebet.add(new placeYourBet(534, 610, 130, 127, "placebet", "placebet",main));
		placebet.add(new placeYourBet(801, 592, 130, 127, "placebet", "placebet",main));
		placebet.get(0).setAngle(15);
		placebet.get(2).setAngle(-15);
		placebet.get(1).press = true;
	
	}
	
	
	public void tick() {
		//when the player press deal
		if((Main.homescreen.memberLogin && Main.gamescreen.deal.press && Main.game.p.getBet()>0) || Main.game.gameruning) {
			Main.gamescreen.deal.press = false;
			Main.game.gameruning = true;
			Main.game.tick();
			//if the player press the coin in the middle of the game
			for(Button i : betmenu.coinsB) {
				i.press = false;
			}
			//when the player didn't press the deal and didn't choose how much to bet
		}else if (Main.homescreen.memberLogin && !Main.gamescreen.deal.press) {
			Main.gamescreen.betmenu.tick();
			//clear button
			if (clear.press) {
				clear.press = false;
				clear.setEnabled(false);
				betmenu.resetBet();
				reBet.setEnabled(true);
			//re-bet button
			}else if (reBet.press) {
				Main.gamescreen.betmenu.showBetCoins = true;
				reBet.setEnabled(false);
				clear.setEnabled(true);
				Main.gamescreen.reBet.press = false;
				placebet.get(betmenu.bet_choose).bet = placebet.get(betmenu.bet_choose).oldBet;
				placebet.get(betmenu.bet_choose).playerBet.setText(String.valueOf(placebet.get(betmenu.bet_choose).bet));
				Main.game.p.Bet =+ placebet.get(betmenu.bet_choose).bet;
				betmenu.audio.PlayAudio("coins");
				placebet.get(betmenu.bet_choose).coins.clear();
				for(int i = placebet.get(betmenu.bet_choose).bet; i>=10;) {
					Coins coin = new Coins(betmenu.targetX, betmenu.targetY-(placebet.get(betmenu.bet_choose).coins.size()*7), "",betmenu.targetX,betmenu.targetY);
					if (i-1000 >= 0) {
						coin.setTextImage("/1000coin.png", 60, 45);
						i-=1000;
					}else if(i-500 >= 0) {
						coin.setTextImage("/500coin.png", 60, 45);
						i-=500;
					}else if(i-100 >= 0) {
						coin.setTextImage("/100coin.png", 60, 45);
						i-=100;
					}else if(i-50 >= 0) {
						coin.setTextImage("/50coin.png", 60, 45);
						i-=50;
					}else{
						coin.setTextImage("/10coin.png", 60, 45);
						i-=10;
					}
					placebet.get(betmenu.bet_choose).coins.add(coin);
					placebet.get(betmenu.bet_choose).coins.get(placebet.get(betmenu.bet_choose).coins.size()-1).setcoinNumber(placebet.get(betmenu.bet_choose).coins.size()-1);

				}
			//beck to main button
			}else if(Main.gamescreen.backToMain.press) {
				backToMain.press = false;
				deal.press = false;
				split.setEnabled(false);
				hit.setEnabled(false);
				stand.setEnabled(false);
				Double.setEnabled(false);
				deal.setEnabled(true);
				Main.homescreen.memberLogin = false;
				betmenu.resetBet();
				Main.DB.members.get(Main.homescreen.playingMember).logMember.press = false;
				Main.DB.members.get(Main.homescreen.playingMember).memberCerence.setText(Main.game.p.money +" $");
				Main.DB.members.get(Main.homescreen.playingMember).membersName.setText(Main.game.p.name);

				Main.background.setBackground("main");
				Main.game.p.playerX = 0;
				
			
			}
			
		//when the player press deal before chooses how much to bet
		}else if (Main.homescreen.memberLogin && Main.gamescreen.deal.press) {
			
			Main.gamescreen.deal.press = false;
		}
		
		//update the coins location
		for(playerDeck i:Main.game.p.playerdecks) {
			for(Coins j:i.coins) {
				if(j.movingTimes >0) {
					j.tick();
				}
			}
		}
	}
	public void rander(Graphics g) {
		balanceCoin.rander(g);
		balance.rander(g);
		deal.rander(g);
		clear.rander(g);
		reBet.rander(g);
		backToMain.rander(g);
		settings.rander(g);
		betmenu.rander(g);
		hand.rander(g);
		for(placeYourBet i : placebet) {
			i.rander(g);
		}

		
		
		if(Main.game.gameruning) {
			hit.rander(g);
			stand.rander(g);
			split.rander(g);
			Double.rander(g);
		}
		Main.game.d.render(g);
		Main.game.p.render(g);
		
		if(Main.setting) {
			settingsScreen.rander(g);
		}
		
	}

}
