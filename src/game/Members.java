package game;

import java.awt.Graphics;
import mainPackage.Button;
import mainPackage.Main;
import mainPackage.Text;


public class Members {
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
	membersName = new Text(580, (Main.homescreen.baseYMembers + 30 * memberNumber), name);
	memberCerence = new Text(645, (Main.homescreen.baseYMembers + 30 * memberNumber), "$ "+ money);
	logMember = new Button(700, (Main.homescreen.baseYMembers - 20 + (30 * memberNumber)),40, 35, "Login","login",main);
	X = new Button(545, (Main.homescreen.baseYMembers - 20 + (30 * memberNumber)), 30,30, "X","x", main);
}

//after deleting member from the save list updating the the location of the member select 
public void memberSelectUpdate() {
	membersName.setLocation(580, (Main.homescreen.baseYMembers + 30 * memberNumber));
	memberCerence.setLocation(645, (Main.homescreen.baseYMembers + 30 * memberNumber));
	logMember.setLocation(700, (Main.homescreen.baseYMembers - 20 + (30 * memberNumber)));
	X.setLocation(545, (Main.homescreen.baseYMembers - 20 + (30 * memberNumber)));
}

public void rander(Graphics g) {
	memberCerence.rander(g);
	membersName.rander(g);
	logMember.rander(g);
	X.rander(g);
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
