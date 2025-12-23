package game;

import java.awt.Color;
import java.util.ArrayList;
import mainPackage.Card;
import mainPackage.DeckOfCards;
import mainPackage.Main;
import mainPackage.dataBase;
import mainPackage.placeYourBet;

public class GameLoop {

	 Main main;
	 public int playerhand = 0,winnerCounter = 0;
	 public boolean newgame = true,gamerunning = false;
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
		newgame = false;
		Main.gamescreen.betmenu.showBetCoins = false;
		for(placeYourBet i:Main.gamescreen.placebet){
			i.setEnabled(false);
		}
		resetStats();
		
		//take the bet from the balance and save
		p.money -= p.Bet;
		dataBase.members.get(p.memberNumber).setMoney(p.money);
		dataBase.write(p.id, p.name, p.money, p.gamesPlayed);
		Main.gamescreen.balance.setText("$"+ p.money);
		
		Main.gamescreen.deal.setEnabled(false);
		Main.gamescreen.clear.setEnabled(false);
		Main.gamescreen.reBet.setEnabled(false);
		Main.gamescreen.hit.setEnabled(true);
		Main.gamescreen.stand.setEnabled(true);
		p.firstPlay();//player take card
		Main.gamescreen.hand.SetAngle(p.playerdecks.get(playerhand).deck_num*-45+225);
		}
		
		//wait until the player press buttons
		if(playerhand < p.playerdecks.size()) {
			
			//check if the hand have more then 21 point
			if(p.playerdecks.get(playerhand).totalpoints >= 21) {
				if(p.playerdecks.get(playerhand).totalpoints==21) {
					p.playerdecks.get(playerhand).TotalPointsL.SetColor(new Color(39,127,23));
				}else {
					p.playerdecks.get(playerhand).TotalPointsL.SetColor(Color.red);
				}
				playerhand++;
				setPlayingHandColor();
				if(playerhand >= p.playerdecks.size()) {
					return;
				}
			}
			
			//check if the cards can be split
			int num_split_hands = 0;
			for(int i = 0;i<p.playerdecks.size();i++) {
				if(	p.playerdecks.get(i).deck_num == p.playerdecks.get(playerhand).deck_num) {
					num_split_hands ++; 
				}
			}
			if(num_split_hands < 3 && p.playerdecks.get(playerhand).deck.size() == 2 && p.playerdecks.get(playerhand).deck.get(0).points == p.playerdecks.get(playerhand).deck.get(1).points && p.money - p.playerdecks.get(playerhand).bet >= 0) {
				Main.gamescreen.split.setEnabled(true);
			}else {
				Main.gamescreen.split.setEnabled(false);
			}
			//check if the cards can be doubled
			if(p.playerdecks.get(playerhand).deck.size() == 2 && Main.game.p.money - p.playerdecks.get(playerhand).bet >= 0) {
				Main.gamescreen.Double.setEnabled(true);
			}else {
				Main.gamescreen.Double.setEnabled(false);
			}
			
			//set the buttons location
			p.playerdecks.get(playerhand).setButtonsLocation();
			
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
				p.money -= p.playerdecks.get(playerhand).bet;
				p.Bet += p.playerdecks.get(playerhand).bet;
				p.playerdecks.get(playerhand).bet = p.playerdecks.get(playerhand).bet*2;
				dataBase.members.get(p.memberNumber).setMoney(p.money);
				dataBase.write(p.id, p.name, p.money, p.gamesPlayed);
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
				
				int splithands = 0;
				for(int i = playerhand;i<p.playerdecks.size();i++) {
					if(p.playerdecks.get(i).deck_num == p.playerdecks.get(playerhand).deck_num) {
						splithands++;
					}
				}
				playerDeck deck = new playerDeck(p.playerdecks.get(playerhand).deck_num);
				p.playerdecks.add(playerhand + splithands, deck);
				if(p.playerdecks.get(playerhand).deck.get(0).points == 11) {
					p.playerdecks.get(playerhand).totalpoints += 10;
				}
				
				p.Bet += p.playerdecks.get(playerhand + splithands).bet;
				p.money -= p.playerdecks.get(playerhand + splithands).bet;
				dataBase.members.get(p.memberNumber).setMoney(p.money);
				dataBase.write(p.id, p.name, p.money, p.gamesPlayed);
				
				p.split(splithands);
				
				p.playerdecks.get(playerhand + splithands).deck_base_y = p.playerdecks.get(playerhand).deck_base_y - (150 * splithands);
				p.playerdecks.get(playerhand + splithands).coinsTargetY = p.playerdecks.get(playerhand).coinsTargetY -  (150 * splithands);
				
				p.playerdecks.get(playerhand + splithands).deck.get(0).setY(p.playerdecks.get(playerhand + splithands).deck_base_y);
				p.playerdecks.get(playerhand + splithands).deck.get(0).setX(p.playerdecks.get(playerhand + splithands).deck_base_x);
				p.playerdecks.get(playerhand + splithands).TotalPointsL.setLocation((p.playerdecks.get(playerhand + splithands).deck_base_x + 57)+(p.playerdecks.get(playerhand + splithands).deck.size()-1)*25+p.playerdecks.get(playerhand + splithands).totalPointsOffset,p.playerdecks.get(playerhand + splithands).deck_base_y -22 + p.playerdecks.get(playerhand + splithands).totalPointsOffset + (p.playerdecks.get(playerhand + splithands).deck.size()*7*p.playerdecks.get(playerhand + splithands).angle/15));
				p.playerdecks.get(playerhand + splithands).TotalPointsL.setText(Integer.toString(p.playerdecks.get(playerhand + splithands).totalpoints));
				
				for(int i = 0; i < p.playerdecks.get(playerhand + splithands).coins.size(); i++) {
					p.playerdecks.get(playerhand + splithands).coins.get(i).setLocation(p.playerdecks.get(playerhand + splithands).coins.get(i).targetX,p.playerdecks.get(playerhand + splithands).coinsTargetY-(i*7));
				}
				p.playerdecks.get(playerhand + splithands).playerBet.setY(p.playerdecks.get(playerhand + splithands).coinsTargetY + 67);
				
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
				if (winnerCounter < p.playerdecks.size()) {
					p.playerdecks.get(winnerCounter).checkWiner(Main.game.d.totalpoints);
					p.playerdecks.get(winnerCounter).time = System.currentTimeMillis();
					winnerCounter++;
					}else {
						if((p.playerdecks.get(winnerCounter-1).time +1000 < System.currentTimeMillis())) {
							int win = 0;
							for(playerDeck d: p.playerdecks) {
								if(d.win) {
									win++;
								}
							}
							if(win == 0) {
								p.playerdecks.get(0).audio.PlayAudio("lose");
							}else {
								p.playerdecks.get(0).audio.PlayAudio("win");
							}
							newgame = true;
							for(placeYourBet i:Main.gamescreen.placebet){
								i.setEnabled(true);
							}
							gamerunning = false;
							Main.gamescreen.deal.setEnabled(true);
							Main.gamescreen.clear.setEnabled(true);
						}
				}
			}
		}else {
			Main.gamescreen.hand.SetAngle(p.playerdecks.get(playerhand).deck_num*-45+225);
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
	winnerCounter = 0;
	d.reset();
	for(playerDeck i : Main.game.p.playerdecks) {
		i.reset();
	}
	Main.game.p.playerdecks.clear();
	playerhand = 0;
	
	//check if you need a new deck of cards
	if(Deck.size()<10) {
		Deck = new DeckOfCards().getShuffledDeck();
		System.out.println("new deck of cards");
		}
	}		
}



