package game;

import java.awt.Graphics;
import mainPackage.Button;
import mainPackage.Main;
import mainPackage.Text;
import mainPackage.Textbox;


public class HomeScreen {
	
	public int baseYMembers = 500;
	public int playingMember; //the member that playing in the game
	public Text login,newgame,NewMemberInfo;
	public boolean gameStart = false;
	public Textbox text;
	Button signup;
	Main main;
	
	public HomeScreen(Main main) {
		this.main = main;
	}
	
	public void creatHomeScreen() {
		Main.save.readFile();
		baseYMembers = 500 - (Main.save.members.size()*30);
		login = new Text(580, (baseYMembers - 30), "Login:");
		text = new Textbox(60, 440,100,20, "",main);	
		newgame = new Text(60, 420, "New Members");
		signup = new Button(170, 435,76, 35, "Sign up","signup",main);
		NewMemberInfo = new Text(60, 495, "New members get 500$ to bet with them");

		//add the members to the single menu
		try {
			for(Members i : Main.save.members) {
				i.memberSelect();
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//draw the component on the screen
	public void rander(Graphics g) {
		login.rander(g);
		text.rander(g);
		newgame.rander(g);
		signup.rander(g);
		NewMemberInfo.rander(g);
		
		for(Members i : Main.save.members) {
			i.rander(g);
		}

	}
	
	public void tick() {
		//check if the player press the login or the X button
		if(!gameStart) {
			for(Members i : Main.save.members) {
				if(i.logMember.press) {
					gameStart = true;
					playingMember = i.memberNumber;
					Main.game.p.setPlayer(Main.save.members.get(Main.homescreen.playingMember).getName(), Main.save.members.get(Main.homescreen.playingMember).money, Main.save.members.get(Main.homescreen.playingMember).gamesPlayed,Main.homescreen.playingMember);
					Main.background.imagloction = "table";
					Main.gamescreen.settingsScreen.playerNameBox.setText(Main.save.members.get(Main.homescreen.playingMember).getName());
				}else if(i.X.press) {
					Main.save.deletePlayer(i.memberNumber);
					break;
				}
			}
			//check if the player press the sign up button
			if (signup.press || text.enter) {
				Main.save.setNewMember(text.getText());
				Main.save.addMember();
				text.setText("");
				signup.press = false;
				text.enter = false;
				text.pointerLocation = 0;
			}
		}
	}
	
	public int getbaseYMembers() {
		return baseYMembers;
	}
}
