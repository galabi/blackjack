package game;

import java.awt.Color;
import java.util.ArrayList;
import mainPackage.Card;
import mainPackage.Coins;
import mainPackage.DeckOfCards;
import mainPackage.Main;

public class GameLoop {

	 Main main;
	 public int playerhand = 0,winnerCunter = 0;
	 public boolean newgame = true,gameruning = false,winLose = false;
	 public Player p;
	 dealer d;
	 ArrayList<Card> Deck = new DeckOfCards().getShuffledDeck();
	 
	public GameLoop(Main main) {
		this.main = main;
		p = new Player();
		d = new dealer();
	}


	public void tick() {
		//new game setup
		if(newgame) {
		Main.gamescreen.betmenu.showBetCoins = false;
		resetStats();
		Main.gamescreen.betmenu.oldBet = Main.gamescreen.betmenu.bet;
		p.Bet = Main.gamescreen.betmenu.bet;
		winLose = false;
		
		//take the bet from the balance and save
		p.money -= p.Bet;
		Main.DB.members.get(p.memberNumber).setMoney(p.money);
		Main.DB.write(p.id, p.name, p.money, p.gamesPlayed);
		
		//Main.save.members.get(p.memberNumber).setMoney(p.money);
		//Main.save.save();
		
		Main.gamescreen.balance.setText("$"+ p.money);
		
		Main.gamescreen.deal.setEnabled(false);
		Main.gamescreen.clear.setEnabled(false);
		Main.gamescreen.reBet.setEnabled(false);
		Main.gamescreen.hit.setEnabled(true);
		Main.gamescreen.stand.setEnabled(true);
		p.play();//player take card
		newgame = false;
		}
		
		//wait until the player press buttons
		if(playerhand < p.playerdecks.size()) {
			
			if(p.playerdecks.get(playerhand).deck.size() < 2) {
				p.play();
				//hit button
			}else if(Main.gamescreen.hit.press) {
				Main.gamescreen.hit.press = false;
				Main.gamescreen.split.setEnabled(false);
				p.play();
				//double button
			}else if (Main.gamescreen.Double.press) {
				Main.gamescreen.Double.press = false;
				p.money = p.money - p.Bet;
				p.playerdecks.get(playerhand).bet = p.Bet*2;
				Main.DB.members.get(p.memberNumber).setMoney(p.money);
				Main.DB.write(p.id, p.name, p.money, p.gamesPlayed);
				//Main.save.members.get(p.memberNumber).setMoney(p.money);
				//Main.save.save();
				
				Main.gamescreen.balance.setText("$"+ p.money);
				p.playerdecks.get(playerhand).doubleHand();
				p.play();
				p.playerdecks.get(playerhand).TotalPointsL.SetColor(Color.black);
				Main.game.playerhand++;
				setPlayingHandColor();
				//split button
			}else if (Main.gamescreen.split.press) {
				Main.gamescreen.split.press = false;
				Main.gamescreen.split.setEnabled(false);
				p.money = p.money - p.Bet;
				Main.DB.members.get(p.memberNumber).setMoney(p.money);
				Main.DB.write(p.id, p.name, p.money, p.gamesPlayed);
				//Main.save.members.get(p.memberNumber).setMoney(p.money);
				//Main.save.save();
				playerDeck deck = new playerDeck(Main.game.p.playerdecks.size());
				Main.game.p.playerdecks.add(deck);
				Main.game.p.split();
				for(playerDeck i : Main.game.p.playerdecks) {
					i.set_deck_base_x();
				}
				//stand button
			}else if (Main.gamescreen.stand.press) {
				Main.gamescreen.stand.press = false;
				Main.gamescreen.split.setEnabled(false);
				playerhand++;
				setPlayingHandColor();
			}
		}
		
		//after the player press the stand button or get more then 21 points in all the hands
		if(playerhand == p.playerdecks.size()) {
			Main.gamescreen.deal.press = false;
			Main.gamescreen.reBet.press = false;
			Main.gamescreen.clear.press = false;
			Main.gamescreen.hit.setEnabled(false);
			Main.gamescreen.stand.setEnabled(false);
			Main.gamescreen.Double.setEnabled(false);
			Main.gamescreen.reBet.setEnabled(false);
			Main.gamescreen.deal.setEnabled(false);
			Main.gamescreen.clear.setEnabled(false);
			d.play();
			
			//the dealer took over 17 points
			if (d.totalpoints >= 17){
				
				//the clock for the win/loss 
				if (winnerCunter < p.playerdecks.size()) {
					p.playerdecks.get(winnerCunter).checkWiner(Main.game.d.totalpoints);
					p.playerdecks.get(winnerCunter).time = System.currentTimeMillis();
					winnerCunter++;
					}else {
						if((p.playerdecks.get(winnerCunter-1).time +1000 > System.currentTimeMillis()) && winLose) {
							for(playerDeck i:p.playerdecks) {
								for(Coins j:i.coins) {
									j.tick();
								}
							}
						}else {
							newgame = true;
							gameruning = false;
							Main.gamescreen.deal.setEnabled(true);
							Main.gamescreen.clear.setEnabled(true);
						}
				}
			}
		}
	}

	
public void setPlayingHandColor() {
	//set the color of the name to black
	for(playerDeck i: p.playerdecks) {
		if(i.totalpoints < 21) {
			i.TotalPointsL.SetColor(Color.BLACK);
		}
	}
	//set the playing hand to blue
	if(playerhand < p.playerdecks.size()) {
		p.playerdecks.get(playerhand).TotalPointsL.SetColor(Color.blue);
	}
}

public void resetStats() {
	//Prepare the game for a new game
	winnerCunter = 0;
	d.reset();
	for(playerDeck i : Main.game.p.playerdecks) {
		i.reset();
	}
	Main.game.p.playerdecks.clear();
	playerhand = 0;
	newgame = true;
	
	//check if you need a new deck of cards
	if(Deck.size()<10) {
		Deck = new DeckOfCards().getShuffledDeck();
		System.out.println("new deck of cards");
		}
	}		
}



