package game;

import java.awt.Graphics;
import mainPackage.Button;
import mainPackage.Main;
import mainPackage.Text;
import mainPackage.Textbox;
import mainPackage.dataBase;


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
		baseYMembers = 750 - (dataBase.members.size()*30);
		login = new Text(880, (baseYMembers - 40), "Login:");
		text = new Textbox(60, 690,100,20, "",main);	
		newgame = new Text(60, 670, "New Members");
		signup = new Button(170, 685,76, 35, "Sign up","signup",main);
		NewMemberInfo = new Text(60, 745, "New members get 500$ to bet with them");
		dataBase.read();
		login.setLocation(880, (baseYMembers - 40));
		
	}
	
	//draw the component on the screen
	public void render(Graphics g) {
		login.render(g);
		text.render(g);
		newgame.render(g);
		signup.render(g);
		NewMemberInfo.render(g);
		
		for(Members i : dataBase.members) {
			i.render(g);
		}
		

	}
	
	public void tick() {
		//check if the player press the login or the X button
		if(!memberLogin) {
			text.textBoxEnabled = true;
			
			
			for(Members i : dataBase.members) {
				if(i.logMember.press) {
					memberLogin = true;
					playingMember = i.memberNumber;
					Main.game.p.setPlayer(dataBase.members.get(Main.homescreen.playingMember).getName(), dataBase.members.get(Main.homescreen.playingMember).money, 
							dataBase.members.get(Main.homescreen.playingMember).gamesPlayed,Main.homescreen.playingMember,dataBase.members.get(Main.homescreen.playingMember).id);
					Main.background.imagloction = "table";
					Main.gamescreen.settingsScreen.playerNameBox.setText(dataBase.members.get(Main.homescreen.playingMember).getName());
					Main.game.resetStats();
					Main.game.newgame = true;
					text.textBoxEnabled = false;
					
					
				}else if(i.X.press) {
					dataBase.deletePlayer(i.memberNumber);
					return;
				}
			}
			//check if the player press the sign up button
			if (signup.press || text.enter) {
				String newname = text.getText();
				newname = newname.substring(0, 1).toUpperCase() + newname.substring(1, newname.length());
				dataBase.createPlayer(newname);
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
