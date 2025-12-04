package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import mainPackage.Audio;
import mainPackage.Card;
import mainPackage.Coins;
import mainPackage.Main;
import mainPackage.Text;


public class playerDeck {
	public Text TotalPointsL,playerBet;
	public ArrayList<Card> deck;
	public ArrayList<Coins> coins;
	public int bet ,angle = 15,totalPointsOffset, coinsTargetX,coinsTargetY;
	int deck_num,totalpoints, deck_base_x,deck_base_y ,coinsWidth = 60;
	boolean ace = false,win;
	Audio audio = new Audio();
	public long time;
	
	public playerDeck(int deck_num) {
		this.deck_num = deck_num;
		deck_base_x = 282;
		deck_base_y = 562;
		
		deck = new ArrayList<Card>();
		coins = new ArrayList<Coins>();
		
		for(Coins i : Main.gamescreen.placebet.get(deck_num).getCoins()) {
			Coins coin = new Coins(i.getX(),i.getY(), "",i.targetX,i.targetY);
			coin.setTextImage(i.getFileLocation(), 60, 45);
			coin.setcoinNumber(coins.size());
			coins.add(coin);
		}
		
		deck.clear();
		TotalPointsL = new Text((deck_base_x + 57)+ (deck.size()-1)*25,  deck_base_y -22 + (deck.size()*7*angle/15), "0");
		TotalPointsL.font = new Font("Gisha", Font.BOLD, 18);
		TotalPointsL.setTextImage("/totalpointsback.png", 45, 45);
		bet = Main.gamescreen.placebet.get(deck_num).bet;
		playerBet = new Text(Main.gamescreen.placebet.get(deck_num).playerBet.getX(),Main.gamescreen.placebet.get(deck_num).playerBet.getY(), String.valueOf(bet));
				
		set_deck_base_x();
	}
	
	public void getcard(){
		audio.PlayAudio("card");
		//only first hand
		if(deck.size() == 0) {
			//the player take his first card
			totalpoints = totalpoints + Main.game.Deck.get(0).points;
			TotalPointsL.setText(String.valueOf(totalpoints));
			deck.add(Main.game.Deck.remove(0));
			deck.get(deck.size()-1).angle = angle;
			deck.get(deck.size()-1).AddCard(deck_base_x + ((deck.size()-1)*25) ,deck_base_y + ((deck.size()-1)*7*angle/15));
			
			if(deck.get(deck.size()-1).points == 11) ace = true;
			
			
		}
		
		// second hand+	
		totalpoints = totalpoints + Main.game.Deck.get(0).points;
		TotalPointsL.setText(String.valueOf(totalpoints));
		deck.add(Main.game.Deck.remove(0));
		deck.get(deck.size()-1).angle = angle;
		deck.get(deck.size()-1).AddCard(deck_base_x + ((deck.size()-1)*25) ,deck_base_y + ((deck.size()-1)*7*angle/15));
		TotalPointsL.setLocation((deck_base_x + 57) + (deck.size()-1)*25+totalPointsOffset, deck_base_y - 22 + (deck.size()*7*angle/15));	
		
		if(deck.get(deck.size()-1).points == 11) ace = true;	
		
		if(totalpoints > 21 && ace) {
			totalpoints = totalpoints - 10;
			TotalPointsL.setText(String.valueOf(totalpoints));
			
			ace = false;
		}		
	}
	
	
	public void reset() {		
		deck.clear();
	}
	
	public void render(Graphics g) {
		for(Card i : deck) {
			i.render(g);
		}
		TotalPointsL.render(g);
		for(Text i : coins) {
			i.render(g);
			}
		FontMetrics metrics = g.getFontMetrics(playerBet.font);
		playerBet.setX(coinsTargetX + ((coinsWidth - metrics.stringWidth(playerBet.getText())) / 2));
		playerBet.render(g);
	}

	
	public void checkWiner(int dealerPoints) {
		Main.game.p.gamesPlayed++;
		int coinsSize = coins.size();
		//winner case
		if((dealerPoints < totalpoints && totalpoints <= 21) || (dealerPoints > 21 && totalpoints <= 21)) {
			win = true;
			for(int i = 0; i< coinsSize ;i++) {
				Coins coin = new Coins(570,60+(i*-7),"",coins.get(i).getX(),coins.get(coinsSize-1).getY()-7);
				coin.movingTimes = 45;
				if(i < (coinsSize)/2 || bet == Main.gamescreen.placebet.get(deck_num).bet) {
					coin.setcoinNumber(i);
				}else {
					//winner after double
					coin.setcoinNumber(i-(coinsSize/2));
				}
				coin.setTextImage(coins.get(i).getFileLocation(), 60, 45);
				coins.add(coin);
			}
			//black jack case
			if(totalpoints == 21 && deck.size() == 2) {
				coinsWidth = 120;
				coinsTargetX = coins.get(0).getX()-60;
				Main.game.p.blackjack(bet);
				playerBet.setText(Integer.toString((int)(bet*2.5)));
				int j = 0;
				for(int i = bet/2; i>=10;) {
					Coins coin = new Coins(570,60+(j*-5), "",coinsTargetX,coins.get(j).targetY);
					coin.setcoinNumber(j);
					coin.movingTimes = 45;
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
					coins.add(coin);
					j++;
				}
			}else {
				//Regular win
				Main.game.p.win(bet);
				playerBet.setText(Integer.toString(bet*2));
			}
			TotalPointsL.SetColor(new Color(39,127,23));
			
			//draw case
		}else if(dealerPoints == totalpoints && totalpoints <= 21) {
			Main.game.p.draw(bet);
			
			//lose case
		}else {
			TotalPointsL.SetColor(Color.RED);
			playerBet.setText("");
			for(Coins i:coins) {
				i.targetX = 570;
				i.targetY = 60;
				i.speedX = 0;
				i.movingTimes = 60;
			}
		}
	}
	
	public void splitCards() {
		totalpoints = deck.get(0).points;
		if(deck.get(deck.size()-1).points == 11) ace = true;
		TotalPointsL.setText(String.valueOf(totalpoints));
	}
	
	public void doubleHand() {
		coinsWidth = 120;
		coinsTargetX = coins.get(0).getX()-60;
		int coinsSize = coins.size();
		for(int i = 0; i< coinsSize ;i++) {
			Coins coin = new Coins(coins.get(i).getX()-60,coins.get(i).getY(),"",coins.get(i).getX()-60,coins.get(0).getY());
			coin.setcoinNumber(i);
			coin.setTextImage(coins.get(i).getFileLocation(), 60, 45);
			coins.add(coin);
		}
		playerBet.setText(Integer.toString(bet));
		playerBet.setX(playerBet.getX()-30);
	}
	
	
	public void set_deck_base_x() {
		if(coins.size() <0)return;
		switch (deck_num) {
		case 0:
			totalPointsOffset = 15;
			deck_base_x = 265;
			deck_base_y = 570;
			angle = 15;
			coinsTargetX = 177;
			coinsTargetY = 652;
			break;
		case 1:
			totalPointsOffset = 0;
			deck_base_x = 544;
			deck_base_y = 607;
			angle = 0;
			coinsTargetX = 471;
			coinsTargetY = 675;
			break;
		case 2:
			totalPointsOffset = -7;
			deck_base_x = 826;
			deck_base_y = 577;
			angle = -15;
			coinsTargetX = 751;
			coinsTargetY = 675;
			break;
		}

		TotalPointsL.setLocation((deck_base_x + 57)+ (deck.size()-1)*25 +totalPointsOffset, deck_base_y -22 + (deck.size()*7*angle/15));
		
		for(int i = 0; i < deck.size(); i++) {
			deck.get(i).setX(deck_base_x + (i*25));
			deck.get(i).setY(deck_base_y + (i*angle/22));
			deck.get(i).angle = angle;
		}
		for(int i = 0; i < coins.size(); i++) {
			coins.get(i).setLocation(coinsTargetX,(coinsTargetY-(i*7)));
		}
		playerBet.setLocation(coins.get(0).targetX + 13, coins.get(0).targetY + 67);
	}
		
	public void setButtonsLocation() {
		int buttons = 2;
		if(deck.size() == 2 && deck.get(0).points == deck.get(1).points &&  Main.game.p.money - Main.game.p.Bet >= 0) {
			buttons++;
		}
		if(deck.size() == 2 && Main.game.p.money - Main.game.p.Bet >= 0) {
			buttons++;
		}
		Main.gamescreen.hit.setX((Main.width - ((99*buttons) + (buttons-1)*45))/2 - 7);
		Main.gamescreen.stand.setX((Main.width - ((99*buttons)+ (buttons-1)*45))/2 + 136);
		Main.gamescreen.Double.setX((Main.width - ((99*buttons)+ (buttons-1)*45))/2 + 280);
		Main.gamescreen.split.setX((Main.width - ((99*buttons)+ (buttons-1)*45))/2 + 424);
	}


}
