package mainPackage;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Audio {
	File cardfile,winfile,losefile,coin,coins;
	public Audio() {
		cardfile = new File("./sound/cards.wav");
		winfile = new File("./sound/win.wav");
		losefile = new File("./sound/lose.wav");
		coin = new File("./sound/coin.wav");
		coins = new File("./sound/coin.wav");
	}
	
	public void PlayAudio(String Audio) {
		File file = cardfile;
		switch (Audio) {
		case "card": 
			file = cardfile;
			break;
		case "win":
			file = winfile;
			break;
		case "lose":
			file = losefile;
		break;
		case "coin":
			file = coin;
		break;
		case "coins":
			file = coins;
		break;
		}
		try {
		    AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.getAbsoluteFile());  
		    Clip clip = AudioSystem.getClip();
		    clip.open(audioIn);
	        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue((float) (80 * (Main.game.p.getVolume()-1) + 6));
		    clip.start();
		} catch (Exception e) {
		}
	}
	
}
