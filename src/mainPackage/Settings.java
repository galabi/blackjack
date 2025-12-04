package mainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Settings {
	int sizeX = 375 ,sizeY = 200 ,x = (Main.width-sizeX)/2,y = (Main.height-sizeY)/12 * 4;
	Button back, nameB;
	Text headline, playerName;
	SliderBar sliderbar;
	public Textbox playerNameBox;
	
	public Settings(Main main) {
		back = new Button(x+sizeX-40, y+10, 30, 30, "back","back", main);
		headline = new Text(x+15, y+30, "Settings:");
		headline.setFont(new Font("Gisha", Font.BOLD, 22));
		sliderbar = new SliderBar(x+15,y+80,sizeX-30,20,"bar",main);
		playerName = new Text(x+15, y + 150 , "Nametag:");
		playerNameBox = new Textbox(x+105, y+135, 90, 20, "", main);
		nameB = new Button(x+195, y+131, 40, 35, "enter","login", main);
	}
	
	public void tick() {
		//check if the player press the back button
		if(back.press || Main.gamescreen.settings.press) {
			back.press = false;
			Main.setting = false;
			//playerNameBox.textBoxEnabled = true;
			Main.gamescreen.settings.press = false;
			playerNameBox.textBoxEnabled = false;
			}
		//check if the player press the change name button
		if(nameB.press || (playerNameBox.enter && playerNameBox.textBoxEnabled)) {
			playerNameBox.enter = false;
			nameB.press = false;
			String newMember = playerNameBox.getText();
			newMember = newMember.substring(0, 1).toUpperCase() + newMember.substring(1, newMember.length());
			Main.game.p.setName(newMember);
			Main.game.p.playerX = 0;
			
			dataBase.members.get(Main.homescreen.playingMember).setName(playerNameBox.getText());
			dataBase.write(Main.game.p.getId(), Main.game.p.getName(), Main.game.p.getMoney(), Main.game.p.getGamesPlayed());
			}
	}
	
	public void render(Graphics g){
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(x, y, sizeX, sizeY);
		g.setColor(new Color(248,210,71));
		g.drawRect(x, y, sizeX, sizeY);
		sliderbar.render(g);
		headline.render(g);
		back.render(g);
		playerName.render(g);
		playerNameBox.render(g);
		nameB.render(g);
	}
	
}
