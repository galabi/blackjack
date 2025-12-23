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
int totalpoints=0;
long time;
boolean ace = false;	

//create the dealer labels
	public dealer() {
		DealerL = new Text(562,150,"Dealer");
		DealerL.SetColor(Color.blue);
		TotalPointsL = new Text(618, 150, "");
		TotalPointsL.font = new Font("Gisha", Font.BOLD, 18);
		TotalPointsL.setTextImage("/totalpointsback.png", 45, 45);
		DealerDeck = new ArrayList<Card>();
		DealerDeck.clear();
	}
	
	public void firstcard() {
		//the dealer take his first card
		
		//the dealer get his first card and set the text
		totalpoints = totalpoints + Main.game.Deck.get(0).points;
		TotalPointsL.setText(String.valueOf(totalpoints));
		DealerDeck.add(Main.game.Deck.remove(0));
		DealerDeck.get(0).AddCard(540 + ((DealerDeck.size()-1)*25) ,165);
		
		//check if the card is ace 
		if(DealerDeck.get(0).points == 11) {
			ace = true;	
			}	
		
		//the dealer tack his second card
		DealerDeck.add(Main.game.Deck.remove(0));
		DealerDeck.get(DealerDeck.size()-1).AddCard(540 + ((DealerDeck.size()-1)*25) ,165);
		DealerDeck.get(DealerDeck.size()-1).FaceUp = false;
		
		//check if the card is ace 
		if(DealerDeck.get(1).points == 11) {
			ace = true;	
			}	
		TotalPointsL.setX(592 + (DealerDeck.size()-1)*25);


	}

	public void play() {
		//add the second card to the dealer total points and show it
		if(DealerDeck.get(1).FaceUp == false) {
			time = System.currentTimeMillis();
			Main.game.p.playerdecks.get(0).audio.PlayAudio("card");
			DealerDeck.get(1).FaceUp = true;
			totalpoints = totalpoints + DealerDeck.get(DealerDeck.size()-1).points;
			TotalPointsL.setText(String.valueOf(totalpoints));
			
			if(totalpoints > 21) {
			    if(DealerDeck.get(DealerDeck.size()-1).points == 11) {
			        totalpoints = totalpoints - 10;
			    } 
			    else if (ace) {
			        totalpoints = totalpoints - 10;
			        ace = false;
			    }
			    TotalPointsL.setText(String.valueOf(totalpoints));
			}
		}		
		//the dealer take cards until his total point are more then 17
		else if (totalpoints < 17 && time + 1000 < System.currentTimeMillis()) {
		time = System.currentTimeMillis();
		Main.game.p.playerdecks.get(0).audio.PlayAudio("card");
		DealerDeck.add(Main.game.Deck.remove(0));
		DealerDeck.get(DealerDeck.size()-1).AddCard(540 + ((DealerDeck.size()-1)*25) ,165);
		totalpoints = totalpoints + (DealerDeck.get(DealerDeck.size()-1).points);
				TotalPointsL.setText(String.valueOf(totalpoints));
		
			//Check if there is an ace and the total is more then 21
			if(totalpoints > 21 && DealerDeck.get(DealerDeck.size()-1).points == 11) {
				totalpoints = totalpoints - 10;
				TotalPointsL.setText(String.valueOf(totalpoints));
			}
		TotalPointsL.setX(592 + (DealerDeck.size()-1)*25);
		}
		
	}
	
	
	public void render(Graphics g) {
		DealerL.render(g);
		for(Card i : DealerDeck) {
			i.render(g);
		}
		if(DealerDeck.size() > 0) {
			TotalPointsL.render(g);
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
