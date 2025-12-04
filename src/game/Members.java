package game;

import java.awt.Graphics;
import mainPackage.Button;
import mainPackage.Main;
import mainPackage.Text;


public class Members {
int baseX = 880;
String name;
public int money,gamesPlayed ,memberNumber,baseYMembers,id;
Text membersName,memberCerence;
public Button logMember,X;
Main main;


//set the member info for the login select menu 
public Members(String name,int money,int gamesPlayed,int memberNumber,int id,Main main) {
	this.name = name;
	this.money = money;
	this.gamesPlayed = gamesPlayed;
	this.memberNumber = memberNumber;
	this.id = id;
	this.main = main;
}	

//add 2 labels and 2 buttons for the login select
public void memberSelect() {
	membersName = new Text(870, (Main.homescreen.baseYMembers + 45 * memberNumber), name);
	memberCerence = new Text(967, (Main.homescreen.baseYMembers + 45 * memberNumber), "$ "+ money);
	logMember = new Button(1050, (Main.homescreen.baseYMembers - 30 + (45 * memberNumber)),60, 52, "Login","login",main);
	X = new Button(817, (Main.homescreen.baseYMembers - 30 + (45 * memberNumber)), 45,45, "X","x", main);
}

//after deleting member from the save list updating the the location of the member select 
public void memberSelectUpdate() {
	membersName.setLocation(baseX, (Main.homescreen.baseYMembers + 45 * memberNumber));
	memberCerence.setLocation(baseX + 97, (Main.homescreen.baseYMembers + 45 * memberNumber));
	logMember.setLocation(baseX + 225, (Main.homescreen.baseYMembers - 30 + (45 * memberNumber)));
	X.setLocation(baseX - 52, (Main.homescreen.baseYMembers - 30 + (45 * memberNumber)));
}

public void render(Graphics g) {
	memberCerence.render(g);
	membersName.render(g);
	logMember.render(g);
	X.render(g);
}



public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getMoney() {
	return money;
}

public int getId() {
	return id;
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
public void setMemberNumber(int memberNumber) {
	this.memberNumber = memberNumber;
}






}
