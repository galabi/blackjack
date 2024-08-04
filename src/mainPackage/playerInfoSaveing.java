package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import game.Members;


public class playerInfoSaveing {
	
	private Formatter x;
	private Scanner s;
	String name = "Player" ,newMember;
	int money=500,gamesPlayed=0,numberOfMembers=1;
	public ArrayList<Members> members;
	private Main main;
	
	public playerInfoSaveing(Main main) {
		this.main = main;
	}
	
	
	//add member to the members save
	public void addMember() {
		
		if(!newMember.isEmpty()) {
		numberOfMembers++;
		newMember = newMember.substring(0, 1).toUpperCase() + newMember.substring(1, newMember.length());
		try {
			x = new Formatter("players.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		x.format("%s " ,numberOfMembers);
		
		try {
			for(Members i : members) {
				x.format("%s %s %s ",i.getName() ,i.getMoney(),i.getGamesPlayed());
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		x.format("%s %s %s ", newMember,"500","0");//add the new member to the file 
		//add the new member to the screen
		members.add(new Members(newMember, 500, 0, numberOfMembers-1,main));
		members.get(members.size()-1).memberSelect();
		x.close();
		
		Main.homescreen.baseYMembers = 500 - (members.size()*30);
		for(Members i : members) {
			i.memberSelectUpdate();
		}
		Main.homescreen.login.setLocation(580, Main.homescreen.baseYMembers - 30);
	}
}
	
	
	public void save() {
		try {
			x = new Formatter("players.txt");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		x.format("%s " ,numberOfMembers);
		
		try {
			for(Members i : members) {
				x.format("%s %s %s ",i.getName() ,i.getMoney(),i.getGamesPlayed());
				}
		} catch (Exception e) {
			x.format("%s %s %s ",name ,money,gamesPlayed);
		}

		x.close();
	}
	
	
	//read from the file
	public void readFile() {
		
		while(true){
			try {	
			s = new Scanner(new File("players.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("file do not find");
			}	
			try {	
			numberOfMembers = s.nextInt();
			} catch (Exception e) {
				System.out.println("there is no players saved!");
				save();
			}
			try {
			members = new ArrayList<Members>();
			
			//adding member to the screen
			for(int i=0;i<numberOfMembers;i++) {
				name = s.next();
				money = s.nextInt();
				gamesPlayed = s.nextInt();
				members.add(new Members(name,money,gamesPlayed,i,main));	
				
			}
			s.close();
			break;
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		}
	}
	
	public void deletePlayer(int memberNumber) {
		//set the id of all the members after the member you want to delete
		for(int i = (memberNumber+1); i< numberOfMembers;i++) {
			members.get(i).setMemberNumber(i-1);
			//members.get(i).logMember.setmemberNumber(i-1);//the login button
			//members.get(i).X.setmemberNumber(i-1);//the X button
			
		}
		//delete the player from the save
		members.remove(memberNumber);
		numberOfMembers--;
		save();
		//update the member labels 
		Main.homescreen.baseYMembers = 500 - (Main.save.members.size()*30);
		for(Members i : members) {
			i.memberSelectUpdate();
		}
		Main.homescreen.login.setLocation(580, Main.homescreen.baseYMembers - 30);
	}
	
	
	
	
	//get the number of members
	public int getNumberOfMembers() {
		return numberOfMembers;
	}
	//after creating member to save add him to the members count 
	public void addMemberToCount() {
	numberOfMembers++;
	}
	
	public void setNewMember(String newMember) {
		this.newMember = newMember;
		
	}
	public String getNewMember() {
		return newMember;
	}
		
	
}
