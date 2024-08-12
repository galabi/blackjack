package game;

import java.awt.Color;
import java.awt.Font;
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
	public int bet ,angle = 0,totalPointsOffset = -15, coinsOffSetX = -49;;
	int deck_num,totalpoints, deck_base_x,deck_base_y;
	boolean ace = false, finish = false;
	Audio audio = new Audio();
	public long time;
	
	public playerDeck(int deck_num) {
		this.deck_num = deck_num;
		deck_base_x = 365;
		deck_base_y = 405;
		
		deck = new ArrayList<Card>();
		coins = new ArrayList<Coins>();
		
		for(Coins i : Main.gamescreen.betmenu.getCoins()) {
			Coins coin = new Coins(317, 450-(coins.size()*5), "",317,450);
			coin.setTextImage(i.getFileLocation(), 40, 30);
			coin.setcoinNumber(coins.size());
			coins.add(coin);
		}
		
		deck.clear();
		TotalPointsL = new Text((deck_base_x + 33)+ (deck.size()-1)*17, 390, "0");
		TotalPointsL.font = new Font("Gisha", Font.BOLD, 14);
		TotalPointsL.setTextImage("/totalpointsback.png", 30, 30);
		bet = Main.gamescreen.betmenu.bet;
		playerBet = new Text(Main.gamescreen.betmenu.playerBet.getX(), 495, String.valueOf(bet));
	}
	
	public void getcard(){
		audio.PlayAudio("card");
		//only first hand
		if(deck.size() == 0) {
			//the player take his first card
			totalpoints = totalpoints + Main.game.Deck.get(0).points;
			TotalPointsL.setText(String.valueOf(totalpoints));
			if(deck_num == Main.game.playerhand) {
				TotalPointsL.SetColor(Color.blue);
			}
			deck.add(Main.game.Deck.remove(0));
			deck.get(deck.size()-1).angle = angle;
			deck.get(deck.size()-1).AddCard(deck_base_x + ((deck.size()-1)*17) ,405 + ((deck.size()-1)*5*angle/15));
			
			if(deck.get(deck.size()-1).points == 11) ace = true;
			
			//the dealer take his first cards
			Main.game.d.firstcard();	
		}
		
		// second hand+	
		totalpoints = totalpoints + Main.game.Deck.get(0).points;
		TotalPointsL.setText(String.valueOf(totalpoints));
		deck.add(Main.game.Deck.remove(0));
		deck.get(deck.size()-1).angle = angle;
		deck.get(deck.size()-1).AddCard(deck_base_x + ((deck.size()-1)*17) ,deck_base_y + ((deck.size()-1)*5*angle/15));
		TotalPointsL.setLocation((deck_base_x + 33)+ (deck.size()-1)*17, deck_base_y + totalPointsOffset + (deck.size()*5*angle/15));	
		
		if(deck.get(deck.size()-1).points == 11) ace = true;	
		
		if(totalpoints > 21 && ace) {
			totalpoints = totalpoints - 10;
			TotalPointsL.setText(String.valueOf(totalpoints));
			
			ace = false;
		}else if(totalpoints >= 21) {
			if(totalpoints==21) {
				TotalPointsL.SetColor(new Color(39,127,23));
			}else {
				TotalPointsL.SetColor(Color.red);
			}
			if(bet == Main.game.p.Bet) {
				Main.game.playerhand++;
			}
			finish = true;
		}
				
		//check if the cards can be split
		if(deck.get(0).points == deck.get(1).points && deck.size() == 2 && Main.game.p.playerdecks.size() < 3 && Main.game.p.money - Main.game.p.Bet >= 0) {
			Main.gamescreen.split.setEnabled(true);
		}else {
			Main.gamescreen.split.setEnabled(false);
		}
		//check if the cards can be doubled
		if(deck.size() == 2 && Main.game.p.money - Main.game.p.Bet >= 0) {
			Main.gamescreen.Double.setEnabled(true);
		}else {
			Main.gamescreen.Double.setEnabled(false);
		}
		
		//set the buttons location
		setButtonsLocation();
		
	}
	
	
	public void reset() {		
		deck.clear();
	}
	
	public void rander(Graphics g) {
		for(Card i : deck) {
			i.render(g);
		}
		TotalPointsL.rander(g);
		for(Text i : coins) {
			i.rander(g);
			}
		playerBet.rander(g);
	}

	
	public void checkWiner(int dealerPoints) {
		Main.game.p.gamesPlayed++;
		int coinsSize = coins.size();
		//winner case
		if((dealerPoints < totalpoints && totalpoints <= 21) || (dealerPoints > 21 && totalpoints <= 21)) {
			Main.game.winLose = true;
			for(int i = 0; i< coinsSize ;i++) {
				Coins coin = new Coins(380,40+(i*-5),"",coins.get(i).getX(),coins.get(coinsSize-1).getY()-5);
				coin.movingTimes = 45;
				if(i < (coinsSize)/2 || bet == Main.game.p.Bet) {
					coin.setcoinNumber(i);
				}else {
					//winner after double
					coin.setcoinNumber(i-(coinsSize/2));
				}
				coin.setTextImage(coins.get(i).getFileLocation(), 40, 30);
				coins.add(coin);
			}
			//black jack case
			if(totalpoints == 21 && deck.size() == 2) {
				Main.game.p.blackjack(bet);
				playerBet.setText(Integer.toString((int)(bet*2.5)));
				int j = 0;
				for(int i = bet/2; i>=10;) {
					Coins coin = new Coins(380,40+(j*-5), "",coins.get(j).getX()-40,coins.get(coinsSize-1).getY()-5 );
					coin.setcoinNumber(j);
					coin.movingTimes = 45;
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
					coins.add(coin);
					j++;
				}
			}else {
				//Regular win
				Main.game.p.win(bet);
				playerBet.setText(Integer.toString(bet*2));
			}
			audio.PlayAudio("win");
			TotalPointsL.SetColor(new Color(39,127,23));
			
			//draw case
		}else if(dealerPoints == totalpoints && totalpoints <= 21) {
			Main.game.p.draw(bet);
			
			//lose case
		}else {
			audio.PlayAudio("lose");
			TotalPointsL.SetColor(Color.RED);
		}
	}
	
	public void splitCards() {
		totalpoints -= deck.get(0).points;
		TotalPointsL.setText(String.valueOf(totalpoints));
		getcard();
	}
	
	public void doubleHand() {
		int coinsSize = coins.size();
		for(int i = 0; i< coinsSize ;i++) {
			Coins coin = new Coins(coins.get(i).getX()-40,coins.get(i).getY(),"",coins.get(i).getX()-40,coins.get(0).getY());
			coin.setcoinNumber(i);
			coin.setTextImage(coins.get(i).getFileLocation(), 40, 30);
			coins.add(coin);
		}
		playerBet.setText(Integer.toString(bet));
		playerBet.setX(playerBet.getX()-20);
	}
	
	
	public void set_deck_base_x() {
		int number_of_hands = Main.game.p.playerdecks.size();
		int coinsOffSetY = 0;

		switch (number_of_hands) {
		case 1:
			totalPointsOffset = -15;
			deck_base_x = 365;
			angle = 0;
			coinsOffSetX = -49;
			break;
		case 2:
			if(deck_num == 0) {
				totalPointsOffset = -15;
				deck_base_x = 365;
				deck_base_y = 405;
				angle = 0;
				coinsOffSetX = -49;
				
			}else {
				totalPointsOffset = -20;
				deck_base_x = 543;
				deck_base_y = 393;
				angle = -15;
				coinsOffSetX = -40;
			}
			break;
		case 3:
			if(deck_num == 0) {
				totalPointsOffset = -5;
				deck_base_x = 190;
				deck_base_y = 375;
				angle = 15;
				coinsOffSetX = -70;
				coinsOffSetY = -15;
			}else if(deck_num == 1) {
				totalPointsOffset = -15;
				deck_base_x = 365;
				deck_base_y = 405;
				angle = 0;
				coinsOffSetX = -49;
			}else {
				totalPointsOffset = -20;
				deck_base_x = 543;
				deck_base_y = 393;
				angle = -15;
				coinsOffSetX = -40;
			break;
		}
		}

		TotalPointsL.setLocation((deck_base_x + 33)+ (deck.size()-1)*17, deck_base_y +totalPointsOffset + (deck.size()*5*angle/15));
		
		for(int i = 0; i < deck.size(); i++) {
			deck.get(i).setX(deck_base_x + (i*17));
			deck.get(i).setY(deck_base_y + (i*5*angle/15));
			deck.get(i).angle = angle;
		}
		for(int i = 0; i < coins.size(); i++) {
			coins.get(i).setLocation((deck_base_x + coinsOffSetX) ,(coins.get(i).getY()+coinsOffSetY));
		}
		playerBet.setLocation(deck_base_x + Main.gamescreen.betmenu.playerBet.getX()-317 + coinsOffSetX,playerBet.getY() + coinsOffSetY);
	}
		
	public void setButtonsLocation() {
		int buttons = 2;
		if(deck.get(0).points == deck.get(1).points && deck.size() == 2 && Main.game.p.playerdecks.size() < 3 && Main.game.p.money - Main.game.p.Bet >= 0) {
			buttons++;
		}
		if(deck.size() == 2 && Main.game.p.money - Main.game.p.Bet >= 0) {
			buttons++;
		}
		Main.gamescreen.hit.setX((Main.width - ((66*buttons) + (buttons-1)*30))/2 - 5);
		Main.gamescreen.stand.setX((Main.width - ((66*buttons)+ (buttons-1)*30))/2 + 91);
		Main.gamescreen.Double.setX((Main.width - ((66*buttons)+ (buttons-1)*30))/2 + 187);
		Main.gamescreen.split.setX((Main.width - ((66*buttons)+ (buttons-1)*30))/2 + 283);
	}


}
