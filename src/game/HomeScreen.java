package game;

import java.awt.Graphics;
import mainPackage.Button;
import mainPackage.Main;
import mainPackage.Text;
import mainPackage.Textbox;


public class HomeScreen {
	
	public int baseYMembers = 700;
	public int playingMember; //the member that playing in the game
	public Text login,newgame,NewMemberInfo;
	public boolean memberLogin = false;
	public Textbox text;
	Button signup;
	Main main;
	
	public HomeScreen(Main main) {
		this.main = main;
	}
	
	public void creatHomeScreen() {
		Main.DB.read();
		baseYMembers = 750 - (Main.DB.members.size()*30);
		login = new Text(880, (baseYMembers - 40), "Login:");
		text = new Textbox(60, 690,100,20, "",main);	
		newgame = new Text(60, 670, "New Members");
		signup = new Button(170, 685,76, 35, "Sign up","signup",main);
		NewMemberInfo = new Text(60, 745, "New members get 500$ to bet with them");
		
	}
	
	//draw the component on the screen
	public void rander(Graphics g) {
		login.rander(g);
		text.rander(g);
		newgame.rander(g);
		signup.rander(g);
		NewMemberInfo.rander(g);
		
		for(Members i : Main.DB.members) {
			i.rander(g);
		}
		

	}
	
	public void tick() {
		//check if the player press the login or the X button
		if(!memberLogin) {
			text.textBoxEnabled = true;
			
			
			for(Members i : Main.DB.members) {
				if(i.logMember.press) {
					memberLogin = true;
					playingMember = i.memberNumber;
					Main.game.p.setPlayer(Main.DB.members.get(Main.homescreen.playingMember).getName(), Main.DB.members.get(Main.homescreen.playingMember).money, Main.DB.members.get(Main.homescreen.playingMember).gamesPlayed,Main.homescreen.playingMember,Main.DB.members.get(Main.homescreen.playingMember).id);
					Main.background.imagloction = "table";
					Main.gamescreen.settingsScreen.playerNameBox.setText(Main.DB.members.get(Main.homescreen.playingMember).getName());
					Main.game.resetStats();
					Main.game.newgame = true;
					text.textBoxEnabled = false;
					
					
				}else if(i.X.press) {
					Main.DB.deletePlayer(i.memberNumber);
					return;
				}
			}
			//check if the player press the sign up button
			if (signup.press || text.enter) {
				String newname = text.getText();
				newname = newname.substring(0, 1).toUpperCase() + newname.substring(1, newname.length());
				Main.DB.createPlayer(newname);
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
