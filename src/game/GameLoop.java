package game;

import java.awt.Color;
import java.util.ArrayList;
import mainPackage.Card;
import mainPackage.DeckOfCards;
import mainPackage.Main;

public class GameLoop {

	 Main main;
	 public int playerhand = 0,winnerCunter = 0;
	 public boolean newgame = true,gameruning = false;
	 public Player p;
	 dealer d;
	 ArrayList<Card> Deck = new DeckOfCards().getShuffledDeck();
	 
	public GameLoop(Main main) {
		this.main = main;
		p = new Player();
		d = new dealer();
	}


	public void tick() {
		//if the player press the back to main screen button
		if(Main.gamescreen.backToMain.press) {
			Main.gamescreen.backToMain.press = false;
			Main.gamescreen.start.press = false;
			Main.homescreen.gameStart = false;
			Main.gamescreen.start.setEnabled(true);
			Main.save.members.get(Main.homescreen.playingMember).logMember.press = false;
			Main.save.members.get(Main.homescreen.playingMember).memberCerence.setText(Main.game.p.money +" $");
			Main.save.members.get(Main.homescreen.playingMember).membersName.setText(Main.game.p.name);

			Main.background.setBackground("main");
			resetStats();
			p.playerX = 0;
			return;
		}
		
		
		if(newgame) {
		p.NewBetSetting();
		if(!p.BetIsOk) {
			return;
		}
		resetStats();
		
		//take the bet from the balance and save
		p.Bet = p.newBet;
		p.money -= p.Bet;
		Main.save.members.get(p.memberNumber).setMoney(p.money);
		Main.save.save();
		Main.gamescreen.balance.setText("Balance: "+ p.money + " $");
		
		Main.gamescreen.start.setEnabled(false);
		Main.gamescreen.hit.setEnabled(true);
		Main.gamescreen.stand.setEnabled(true);
		Main.gamescreen.Double.setEnabled(true);
		p.play();//player take card
		newgame = false;
		}
		
		//wait until the player press buttons
		if(playerhand < p.playerdecks.size()) {
			//when the player press the stand button the loop is end
			for(int i = 0; i < p.playerdecks.size(); i++) {
				if(i == playerhand) {
					p.playerdecks.get(i).TotalPointsL.SetColor(Color.blue);
				}else {
					p.playerdecks.get(i).TotalPointsL.SetColor(Color.black);
				}
			}
			if(p.playerdecks.get(playerhand).deck.size() < 2) {
				p.play();
			}else if(Main.gamescreen.hit.press) {
				Main.gamescreen.hit.press = false;
				p.play();
			}else if (Main.gamescreen.Double.press) {
				Main.gamescreen.Double.press = false;
				p.money = p.money - p.Bet;
				Main.save.members.get(p.memberNumber).setMoney(p.money);
				Main.save.save();
				p.newBet = p.newBet*2;
				p.Bet = p.newBet;
				Main.gamescreen.yourBet.setText("Your Bet is: "+ p.newBet + " $");
				p.play();
				Main.game.playerhand++;
			}else if (Main.gamescreen.split.press) {
				Main.gamescreen.split.press = false;
				Main.gamescreen.split.setEnabled(false);
				p.money = p.money - p.Bet;
				Main.save.members.get(p.memberNumber).setMoney(p.money);
				Main.save.save();
				playerDeck deck = new playerDeck(Main.game.p.playerdecks.size());
				Main.game.p.playerdecks.add(deck);
				Main.game.p.split();
				for(playerDeck i : Main.game.p.playerdecks) {
					i.set_deck_base_x();
				}
			}else if (Main.gamescreen.stand.press) {
				Main.gamescreen.stand.press = false;
				p.playerdecks.get(playerhand).TotalPointsL.SetColor(Color.black);
				playerhand ++;
			}
			
		}
		
		//after the player press the stand button or get more then 21 points
		if(playerhand == p.playerdecks.size()) {
			gameruning = false;
			d.play();

			
			if (d.totalpoints >= 17){
				
				if (winnerCunter < p.playerdecks.size()) {
					if (winnerCunter == 0 || p.playerdecks.get(winnerCunter-1).time + 2000 < System.currentTimeMillis()) {
						p.playerdecks.get(winnerCunter).checkWiner(Main.game.d.totalpoints);
						p.playerdecks.get(winnerCunter).time = System.currentTimeMillis();
						winnerCunter++;
					}
				}else {
					Main.gamescreen.start.press = false;
					Main.gamescreen.start.setEnabled(true);
					newgame = true;
					p.BetIsOk = false;
				}


			}
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



