package mainPackage;

import java.util.ArrayList;
import java.util.Random;


public class DeckOfCards {
	ArrayList<Card> ShuffledDeck;
	Card card;
	int face;

	//call that method if you want a new shuffled deck of cards
	 public ArrayList<Card> getShuffledDeck() {
		ArrayList<Card> Deck = new ArrayList<Card>();
		Deck = NewDeck();//fresh deck of cards
		Shuffle(Deck);//shuffle the fresh deck 
		return ShuffledDeck;
		
 }
	 
	 
	public void Shuffle(ArrayList<Card> deck){
		ShuffledDeck = new ArrayList<Card>();
		ShuffledDeck.clear();//make sure that the shuffled deck array is clear
		Random r = new Random();
		
		for(int i = 0;i <208 ; i++) {
		//add the cards to the deck by a random order
			int randomNum = r.nextInt((208-i));
			ShuffledDeck.add(deck.get(randomNum));
			deck.remove(randomNum);//remove the cards that added to the shuffled deck from the old deck 
	}
	
	}

	
	public ArrayList<Card> NewDeck(){
		int points = 1;
		String location = "/card";
		ArrayList<Card> newDeck = new ArrayList<Card>();
		//build the fresh deck(1111,2222,3333,...)
		for(int j = 1 ;j <=4 ; j++) {
		for(int i = 1 ;i <=52 ; i++) {
			location = "/card" + i + ".png";
			
			if (i%13 >= 10 || i%13 == 0) {
				points = 10;
				if(i%13 > 10) {
					face = i%13;
				}else if(i%13 == 0) {
					face = 13;
				}
			}else if(i%13 == 1) {
				points = 11;
				face = 11;
			}else {
				points = (i%13);
				face = points;
			}
			card = new Card(points, face, location);
			newDeck.add(card);
		}
	}
		return newDeck;
	}
	


}