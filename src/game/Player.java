package game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import mainPackage.Main;
import mainPackage.Text;


public class Player {
int money = 0,gamesPlayed,memberNumber,Bet=0,id;
public int playerX = 0;
public Text PlayerNameL;
String name = "";
public ArrayList<playerDeck> playerdecks;
double Volume = 0.7;


public Player(){
	PlayerNameL = new Text(0, 515, name);
	PlayerNameL.SetColor(Color.blue);
	playerdecks = new ArrayList<playerDeck>();
	playerdecks.clear();
	
}


public void firstPlay(){
	//create the first deck
	if(playerdecks.size() <= 0) {
		for(int i = 0;i<Main.gamescreen.placebet.size();i++) {
			if(Main.gamescreen.placebet.get(i).bet > 0) {
				playerdecks.add(new playerDeck(i));
			}
		}
		for(playerDeck i : playerdecks) {
			i.getcard();
		}
		//the dealer take his first cards
		Main.game.d.firstcard();
		}
		playerdecks.get(0).TotalPointsL.SetColor(Color.blue);
}

public void play(){
	playerdecks.get(Main.game.playerhand).getcard();
}


public void split(int splithands) {
	playerdecks.get(Main.game.playerhand+splithands).deck.add(playerdecks.get(Main.game.playerhand).deck.remove(playerdecks.get(Main.game.playerhand).deck.size()-1));
	playerdecks.get(Main.game.playerhand+splithands).deck.get(0).setY(playerdecks.get(Main.game.playerhand+splithands).deck.get(0).getY()-(5*playerdecks.get(Main.game.playerhand+1).deck.get(0).angle/17));
	playerdecks.get(Main.game.playerhand+splithands).splitCards();
	
	playerdecks.get(Main.game.playerhand).splitCards();
	playerdecks.get(Main.game.playerhand).getcard();
	
	
	
}
public void blackjack(int bet) {
	//when the player win with blackjack 
	money = (int) (money + bet* 2.5);
	Main.gamescreen.balance.setText("$"+ money );
	save();
}

public void win(int bet) {
	//when the player win  
	money = (money + bet*2);
	Main.gamescreen.balance.setText("$"+ money );
	save();
}
	

public void draw(int bet) {
	//if the player and the dealer have the same points
	money = money + bet;
	Main.gamescreen.balance.setText("$"+ money );
	save();
}

public void save() {
	Main.DB.members.get(memberNumber).setMoney(money);
	Main.DB.members.get(memberNumber).setGamesPlayed(gamesPlayed);
	Main.DB.write(id,name,money,gamesPlayed);
}


	

public void render(Graphics g) {
	if(playerX == 0 && name != "") {
		FontMetrics metrics = g.getFontMetrics(PlayerNameL.getFont());
		playerX = (Main.Frame.getWidth() - metrics.stringWidth(name)) / 2;
		PlayerNameL.setX(playerX);
		PlayerNameL.setText(name);
	}
	PlayerNameL.rander(g);
	for(playerDeck i : playerdecks) {
		i.rander(g);
	}
}

public void setPlayer(String name,int money,int gamesPlayed,int memberNumber,int id){
	this.name = name;
	this.money = money;
	this.gamesPlayed = gamesPlayed;
	this.memberNumber = memberNumber;
	this.id = id;
	PlayerNameL.setText(name);
	Main.gamescreen.balance.setText("$"+ money );

	
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
public int getId() {
	return id;
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
public void setVolume(double Volume) {
	this.Volume = Volume;
}

public double getVolume() {
	return Volume;
}



}
