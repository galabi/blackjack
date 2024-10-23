package mainPackage;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Background {
	public String imagloction = "main";
	ImageIcon imag,imagb;
	public boolean back = false;
	
	public Background() {
		imag = new ImageIcon(getClass().getResource("/main_background.PNG"));
		imagb = new ImageIcon(getClass().getResource("/table_blackjack.PNG"));
		
	}
	
	//the main func that run the image of the background
	public void rander(Graphics g) {
		switch (imagloction) {
		case "main":
			g.drawImage(imag.getImage(), 0, 0, Main.width, Main.height, null);
		break;
		case "table": 
			g.drawImage(imagb.getImage(), 0, 0, Main.width, Main.height, null);
		break;
		}
	}
	
	/**
	 * set the background of the main frame
	 * "/table_blackjack.PNG"
	 * "/main_background.PNG"
	**/
	public void setBackground(String imagloction) {
		this.imagloction = imagloction;
	}
}
