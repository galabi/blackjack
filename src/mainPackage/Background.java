package mainPackage;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Background {
	public String imagloction = "main";
	ImageIcon imag,imagb;
	public boolean back = false;
	
	public Background() {
		try {
			imag = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/main_background.png")));
			imagb = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/table_blackjack.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//the main func that run the image of the background
	public void render(Graphics g) {
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
