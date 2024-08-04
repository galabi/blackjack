package game;

import java.awt.Graphics;

import mainPackage.Button;
import mainPackage.Main;
import mainPackage.Settings;
import mainPackage.Text;
import mainPackage.Textbox;

public class GameScreen {

	Main main;
	public Textbox bet;
	public Text placeBet, yourBet, balance;
	public Button start,hit,stand,split,Double,backToMain,settings;
	public Settings settingsScreen;
	
	public GameScreen(Main main) {
		this.main = main;
		
		//open the single player game mode screen

		
		///text field
		bet = new Textbox(140, 180, 55,20 ,"10",main);
		
		
		//labels
		placeBet = new Text(20, 195, "Place your bet:");
		yourBet = new Text(20, 135 ,"Your Bet is: " + "$"+ Main.game.p.Bet);
		balance = new Text(20, 165, "Balance:");
		
		
		//button
		start = new Button(30, 518 ,76 ,40 ,"Start" ,"start",main);
		hit = new Button(220, 518, 76 , 40 ,"Hit" ,"hit",main);
		stand = new Button(320, 518,76 , 40 ,"Stand" ,"stand",main);
		split = new Button(520, 518,76 , 40 ,"Split" ,"split",main);
		Double = new Button(420, 518, 76, 40 ,"Double" ,"double",main);
		hit.setEnabled(false);
		stand.setEnabled(false);
		split.setEnabled(false);
		Double.setEnabled(false);
		backToMain = new Button(20, 20, 40, 40 ,"Back" , "back",main);
		settings = new Button(740, 20, 40, 40, "settings","settings", main);
		
		settingsScreen = new Settings(main);
	}
	
	public void rander(Graphics g) {
		bet.rander(g);
		placeBet.rander(g);
		yourBet.rander(g);
		balance.rander(g);
		start.rander(g);
		backToMain.rander(g);
		settings.rander(g);
		
		if(Main.game.gameruning) {
			hit.rander(g);
			stand.rander(g);
			split.rander(g);
			Double.rander(g);
		}
		Main.game.p.render(g);
		Main.game.d.render(g);
		
		if(Main.setting) {
			settingsScreen.rander(g);
		}
		
	}

}
