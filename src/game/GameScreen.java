package game;

import java.awt.Graphics;
import mainPackage.Button;
import mainPackage.Coins;
import mainPackage.Main;
import mainPackage.Settings;
import mainPackage.Text;

public class GameScreen {

	Main main;
	public Text balance,balanceCoin;
	public Button deal,hit,stand,split,Double,clear,reBet,backToMain,settings;
	public Settings settingsScreen;
	public BetMenu betmenu;
	
	public GameScreen(Main main) {
		this.main = main;
		
		//open the single player game mode screen
		//labels
		balance = new Text(70, 127, "");
		balance.setTextImage("/balance.png", 0, 0);
		balanceCoin = new Text(20,115, "");
		balanceCoin.setTextImage("/balance.png", 83, 25);

 		
		//button
		deal = new Button(700, 518 ,76 ,40 ,"Deal" ,"deal",main);
		clear = new Button(625, 518 ,76 ,40 ,"Clear" ,"clear",main);
		reBet = new Button(625, 518 ,76 ,40 ,"Rebet" ,"rebet",main);
		hit = new Button(220, 518, 76 , 40 ,"Hit" ,"hit",main);
		stand = new Button(320, 518,76 , 40 ,"Stand" ,"stand",main);
		split = new Button(520, 518,76 , 40 ,"Split" ,"split",main);
		Double = new Button(420, 518, 76, 40 ,"Double" ,"double",main);
		hit.setEnabled(false);
		stand.setEnabled(false);
		split.setEnabled(false);
		Double.setEnabled(false);
		reBet.setEnabled(false);
		backToMain = new Button(20, 20, 40, 40 ,"Back" , "back",main);
		settings = new Button(740, 20, 40, 40, "settings","settings", main);
		
		settingsScreen = new Settings(main);
		betmenu = new BetMenu(180,520,"betmanu",main);
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
			//when the player didn't press the deal and didn't chooses how match to bet
		}else if (Main.homescreen.memberLogin && !Main.gamescreen.deal.press) {
			Main.gamescreen.betmenu.tick();
			//clear button
			if (clear.press) {
				clear.press = false;
				clear.setEnabled(false);
				Main.gamescreen.betmenu.playerBet.setText("");
				betmenu.resetBet();
				reBet.setEnabled(true);
			//re-bet button
			}else if (reBet.press) {
				Main.gamescreen.betmenu.showBetCoins = true;
				reBet.setEnabled(false);
				clear.setEnabled(true);
				Main.gamescreen.reBet.press = false;
				betmenu.bet = betmenu.oldBet;
				betmenu.playerBet.setText(String.valueOf(betmenu.bet));
				Main.game.p.Bet = betmenu.bet;
				betmenu.audio.PlayAudio("coins");
				betmenu.coins.clear();
				for(int i = betmenu.bet; i>=10;) {
					Coins coin = new Coins(317, 450-(betmenu.coins.size()*5), "",317,450);
					if (i-1000 >= 0) {
						coin.setTextImage("/1000coin.png", 40, 30);
						i-=1000;
					}else if(i-500 >= 0) {
						coin.setTextImage("/500coin.png", 40, 30);
						i-=500;
					}else if(i-100 >= 0) {
						coin.setTextImage("/100coin.png", 40, 30);
						i-=100;
					}else if(i-50 >= 0) {
						coin.setTextImage("/50coin.png", 40, 30);
						i-=50;
					}else{
						coin.setTextImage("/10coin.png", 40, 30);
						i-=10;
					}
					betmenu.coins.add(coin);
					betmenu.coins.get(betmenu.coins.size()-1).setcoinNumber(betmenu.coins.size()-1);

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
			
		//when the player press deal before chooses how match to bet
		}else if (Main.homescreen.memberLogin && Main.gamescreen.deal.press) {
			
			Main.gamescreen.deal.press = false;
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
