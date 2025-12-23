package mainPackage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Audio {

	
	private static Map<String, URL> soundPaths = new HashMap<>();	
	static {
		loadSound("card", "/cards.wav");
        loadSound("win", "/win.wav");
        loadSound("lose", "/lose.wav");
        loadSound("coin", "/coin.wav");
	}
	
	private static void loadSound(String name, String path) {
        URL url = Audio.class.getResource(path);
        if (url != null) {
            soundPaths.put(name, url);
        } else {
            System.err.println("Sound not found: " + path);
        }
    }
            
	public void PlayAudio(String soundName) {
        new Thread(() -> {
            try {
                URL url = soundPaths.get(soundName);
                if (url == null) return;

                Clip clip = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                clip.open(ais);
                
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float volValue = (float) (80 * (Main.game.p.getVolume() - 1) + 6);
                volume.setValue(volValue);
                
                clip.addLineListener(new LineListener() {
                    @Override
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    }
                });
                clip.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
	
}
