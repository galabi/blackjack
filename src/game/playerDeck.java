package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import mainPackage.Audio;
import mainPackage.Card;
import mainPackage.Main;
import mainPackage.Text;


public class playerDeck {
	public Text TotalPointsL;
	public ArrayList<Card> deck;
	int deck_num,totalpoints, deck_base_x;
	boolean ace = false, finish = false;
	Audio audio = new Audio(); 
	
	public playerDeck(int deck_num) {
		this.deck_num = deck_num;
		int number_of_hands = Main.game.p.playerdecks.size()+1;
		deck_base_x = ((Main.width - (11 * (number_of_hands)))/(number_of_hands+1))*(deck_num+1)-28;
		
		deck = new ArrayList<Card>();
		deck.clear();
		TotalPointsL = new Text((deck_base_x + 33)+ (deck.size()-1)*17, 390, "0");
		TotalPointsL.font = new Font("Gisha", Font.BOLD, 14);
		TotalPointsL.setTextImage("/totalpointsback.png", 30, 30);
		
	}
	
	public void getcard(){
		audio.PlayAudio("card");
		//only first hand
		if(deck.size() == 0) {
			//the player take his first card
			totalpoints = totalpoints + Main.game.Deck.get(0).points;
			TotalPointsL.setText(String.valueOf(totalpoints));
			deck.add(Main.game.Deck.remove(0));
			deck.get(deck.size()-1).AddCard(deck_base_x + ((deck.size()-1)*17) ,405);
			
			if(deck.get(deck.size()-1).points == 11) ace = true;
			
			//the dealer take his first cards
			Main.game.d.firstcard();	
		}
		
		// second hand+	
		totalpoints = totalpoints + Main.game.Deck.get(0).points;
		TotalPointsL.setText(String.valueOf(totalpoints));
		deck.add(Main.game.Deck.remove(0));
		deck.get(deck.size()-1).AddCard(deck_base_x + ((deck.size()-1)*17) ,405);

		
		if(deck.get(deck.size()-1).points == 11) ace = true;	
		
		if(totalpoints > 21 && ace) {
			totalpoints = totalpoints - 10;
			TotalPointsL.setText(String.valueOf(totalpoints));
			ace = false;
		}else if(totalpoints >= 21) {
			Main.game.playerhand++;
			finish = true;
			if(totalpoints>21) TotalPointsL.SetColor(Color.red);
		}
		TotalPointsL.setX((deck_base_x + 33)+ (deck.size()-1)*17);
		
		//check if the cards can be split
		if(deck.get(0).points == deck.get(1).points && deck.size() == 2) {
			Main.gamescreen.split.setEnabled(true);
		}else {
			Main.gamescreen.split.setEnabled(false);
		}
		//check if the cards can be doubled
		if(deck.size() > 2) {
			Main.gamescreen.Double.setEnabled(false);
		}else {
			Main.gamescreen.Double.setEnabled(true);
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
	}

	
	public void checkWiner(int dealerPoints) {
		if((dealerPoints < totalpoints && totalpoints <= 21) || (dealerPoints > 21 && totalpoints <= 21)) {
			audio.PlayAudio("win");
			Main.game.p.win();
			TotalPointsL.SetColor(new Color(39,127,23));
			TotalPointsL.SetOutLineColor(Color.BLACK);
		}else if(totalpoints == 21 && deck.size() == 2) {
			audio.PlayAudio("win");
			Main.game.p.blackjack();
			TotalPointsL.SetColor(new Color(39,127,23));
			TotalPointsL.SetOutLineColor(Color.BLACK);
		}else if(dealerPoints == totalpoints && totalpoints <= 21) {
			Main.game.p.draw();
		}else {
			audio.PlayAudio("lose");
			TotalPointsL.SetColor(Color.RED);
		}
		Main.game.p.gamesPlayed++;
	}
	
	public void splitCards() {
		totalpoints -= deck.get(0).points;
		TotalPointsL.setText(String.valueOf(totalpoints));
		getcard();
	}
	
	
	public void set_deck_base_x() {
		int number_of_hands = Main.game.p.playerdecks.size();
		deck_base_x = ((Main.width - (11 * (number_of_hands)))/(number_of_hands+1))*(deck_num+1);
		TotalPointsL.setLocation((deck_base_x + 33)+ (deck.size()-1)*17, 390);
		for(int i = 0; i < deck.size(); i++) {
			deck.get(i).setX(deck_base_x + (i * 17));
		}
	}
	
	public void setButtonsLocation() {
		int buttons = 2;
		if((deck.get(0).points == deck.get(1).points && deck.size() == 2)) {
			buttons++;
		}
		if(deck.size() == 2) {
			buttons++;
		}
		Main.gamescreen.hit.setX((Main.width - ((66*buttons) + (buttons-1)*34))/2 - 5);
		Main.gamescreen.stand.setX((Main.width - ((66*buttons)+ (buttons-1)*34))/2 + 95);
		Main.gamescreen.Double.setX((Main.width - ((66*buttons)+ (buttons-1)*34))/2 + 195);
		Main.gamescreen.split.setX((Main.width - ((66*buttons)+ (buttons-1)*34))/2 + 295);
	}

}
