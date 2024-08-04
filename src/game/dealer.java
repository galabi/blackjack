package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import mainPackage.Card;
import mainPackage.Main;
import mainPackage.Text;


public class dealer  {
Text DealerL ,TotalPointsL;
ArrayList<Card> DealerDeck;
int totalpoints=0,miniloop = 0;
boolean ace = false;	

//create the dealer labels
	public dealer() {
		DealerL = new Text(375,100,"Dealer");
		DealerL.SetColor(Color.blue);
		TotalPointsL = new Text(412, 100, "");
		TotalPointsL.font = new Font("Gisha", Font.BOLD, 14);
		TotalPointsL.setTextImage("/totalpointsback.png", 30, 30);
		DealerDeck = new ArrayList<Card>();
		DealerDeck.clear();
	}
	
	public void firstcard() {
		//the dealer take his first card
		
		//the dealer get his first card and set the text
		totalpoints = totalpoints + Main.game.Deck.get(0).points;
		TotalPointsL.setText(String.valueOf(totalpoints));
		DealerDeck.add(Main.game.Deck.remove(0));
		DealerDeck.get(0).AddCard(360 + ((DealerDeck.size()-1)*17) ,110);
		
		//check if the card is ace 
		if(DealerDeck.get(0).points == 11) {
			ace = true;	
			}	
		
		//the dealer tack his second card
		DealerDeck.add(Main.game.Deck.remove(0));
		DealerDeck.get(DealerDeck.size()-1).AddCard(360 + ((DealerDeck.size()-1)*17) ,110);
		DealerDeck.get(DealerDeck.size()-1).FaceUp = false;
		
		//check if the card is ace 
		if(DealerDeck.get(1).points == 11) {
			ace = true;	
			}	
		TotalPointsL.setX(395 + (DealerDeck.size()-1)*17);


	}

	public void play() {
		//add the second card to the dealer total points and show it
		if(DealerDeck.get(1).FaceUp == false) {
			DealerDeck.get(1).FaceUp = true;
			totalpoints = totalpoints + DealerDeck.get(1).points;
			TotalPointsL.setText(String.valueOf(totalpoints));
			
			if(totalpoints > 21 && ace) {
				totalpoints = totalpoints - 10;
				TotalPointsL.setText(String.valueOf(totalpoints));
				ace = false;
			}
		}		
		//the dealer take cards until his total point are more then 17
		else if (totalpoints < 17 && miniloop >= 30) {
		miniloop = 0;
		DealerDeck.add(Main.game.Deck.remove(0));
		DealerDeck.get(DealerDeck.size()-1).AddCard(360 + ((DealerDeck.size()-1)*17) ,110);
		totalpoints = totalpoints + (DealerDeck.get(DealerDeck.size()-1).points);
				TotalPointsL.setText(String.valueOf(totalpoints));
		
			//Check if there is an ace and the total is more then 21
			if(totalpoints > 21 && DealerDeck.get(DealerDeck.size()-1).points == 11) {
				totalpoints = totalpoints - 10;
				TotalPointsL.setText(String.valueOf(totalpoints));
			}
		TotalPointsL.setX(395 + (DealerDeck.size()-1)*17);
		} else if(miniloop <30) miniloop++;
		
	}
	
	
	public void render(Graphics g) {
		DealerL.rander(g);
		for(Card i : DealerDeck) {
			i.render(g);
		}
		if(DealerDeck.size() > 0) {
			TotalPointsL.rander(g);
		}
	}
	
	public void reset(){
	//reset the dealer states 
		totalpoints = 0;
		TotalPointsL.setText(String.valueOf(totalpoints));
		ace = false;
		DealerDeck.clear();
	}
	
	
	public int getTotalPoints() {
		return totalpoints;
	}


}
