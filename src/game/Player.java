package game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import mainPackage.Main;
import mainPackage.Text;


public class Player {
int money = 0,gamesPlayed,memberNumber,newBet=10,Bet=10;
public int playerX = 0;
public Text PlayerNameL;
Text error;
String name = "",StringnewBet= "0";
public ArrayList<playerDeck> playerdecks;
boolean ace = false, BetIsOk = false;
double Volume = 0.7;


public Player(){
	PlayerNameL = new Text(0, 380, name);
	PlayerNameL.SetColor(Color.blue);

	error = new Text(210, 195, "");
	error.SetColor(Color.red);
	playerdecks = new ArrayList<playerDeck>();
	playerdecks.clear();
	
}



public void play(){
	//create the first deck
	if(playerdecks.size() <= 0) {
		playerDeck mydeck = new playerDeck(0);
		playerdecks.add(mydeck);
		}
	//take cards
	playerdecks.get(Main.game.playerhand).getcard();
}


public void split() {
	playerdecks.get(playerdecks.size()-1).deck.add(playerdecks.get(Main.game.playerhand).deck.remove(playerdecks.get(Main.game.playerhand).deck.size()-1));
	playerdecks.get(playerdecks.size()-1).totalpoints += playerdecks.get(playerdecks.size()-1).deck.get(0).points;
	playerdecks.get(Main.game.playerhand).splitCards();
	
}
public void blackjack() {
	//when the player win with blackjack 
	money = (int) (money + Bet* 2.5);
	Main.gamescreen.balance.setText("Balance: "+ "$"+ money );
	Main.save.members.get(memberNumber).setMoney(money);
	Main.save.members.get(memberNumber).setGamesPlayed(gamesPlayed);
	Main.save.save();
}

public void win() {
	//when the player win  
	money = (money + Bet*2);
	Main.gamescreen.balance.setText("Balance: "+ "$"+ money );
	Main.save.members.get(memberNumber).setMoney(money);
	Main.save.members.get(memberNumber).setGamesPlayed(gamesPlayed);
	Main.save.save();
}
	

public void draw() {
	//if the player and the dealer have the same points
	money = money + Bet;
	Main.gamescreen.balance.setText("Balance: "+ "$"+ money );
	Main.save.members.get(memberNumber).setGamesPlayed(gamesPlayed);
	Main.save.save();
}


public void NewBetSetting() {
	StringnewBet = Main.gamescreen.bet.getText();
	
	if(!BetIsOk || !Main.gamescreen.start.press) {
	//check if the player put in the text box a string ;
		try {
			//if the bet number is OK
		if(Integer.parseInt(StringnewBet) > 0 && StringnewBet != null) {
			this.newBet = Integer.parseInt(StringnewBet);	
		BetIsOk = true;
		error.setText("");
		
		}else if(Integer.parseInt(StringnewBet) < 0) {
			//if the number is negative or = to 0
			error.setText("You Enterd negative number");
			Main.gamescreen.start.press = false;
			
		}
	
	} catch (Exception e) {
		//if the player put in the text box a string
		error.setText("Error");
		Main.gamescreen.start.press = false;
		}	
	}
	
	if(BetIsOk) {
		//after the player enter legal number the bet label will update
	Main.gamescreen.yourBet.setText("Your Bet is: "+ "$"+ this.newBet );
	error.setText("");
	StringnewBet = "0";
	}
}
	

public void render(Graphics g) {
	if(playerX == 0 && name != "") {
		FontMetrics metrics = g.getFontMetrics(PlayerNameL.getFont());
		playerX = (Main.Frame.getWidth() - metrics.stringWidth(name)) / 2;
		PlayerNameL.setLocation(playerX, 380);
		PlayerNameL.setText(name);
	}
	PlayerNameL.rander(g);
	error.rander(g);	
	for(playerDeck i : playerdecks) {
		i.rander(g);
	}
}

public void setPlayer(String name,int money,int gamesPlayed,int memberNumber){
	this.name = name;
	this.money = money;
	this.gamesPlayed = gamesPlayed;
	this.memberNumber = memberNumber;
	PlayerNameL.setText(name);
	Main.gamescreen.balance.setText("Balance: "+ "$"+ money );
	
}

public int getMoney() {
	return money;
}
public void setMoney(int money) {
	this.money = money;
}
public int getGamesPlayed() {
	return gamesPlayed;
}
public void setGamesPlayed(int gamesPlayed) {
	this.gamesPlayed = gamesPlayed;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
} 
public int getBet() {
	return Bet;
}
public void setBet(int bet) {
	Bet = bet;
}
public int getNewBet() {
	return newBet;
}
public void setNewBet(String StringnewBet) {
	this.StringnewBet = StringnewBet;
}
public void setVolume(double Volume) {
	this.Volume = Volume;
}

public double getVolume() {
	return Volume;
}



}
