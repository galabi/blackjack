package mainPackage;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Taskbar;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import game.GameLoop;
import game.GameScreen;
import game.HomeScreen;


public class Main extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static int width = 1200 ,height = width /12 * 9; 
	private Thread thread;
	private boolean running = false;
	public static Background background;
	public static JFrame Frame;
	public static HomeScreen homescreen;
	public static GameLoop game;
	public static GameScreen gamescreen;
	public static boolean setting = false;
	public  Main main = this;

	
	public Main() {
		
	window(width, height, "My Game", this);
		
	}
	
	
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		
		running = true;
		
	}
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
		      System.out.println(e);
		      e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
	long lastTime = System.nanoTime();
	double amountOfTicks = 60.0;
	double ns = 1000000000 / amountOfTicks;
	double delta = 0;
	long timer = System.currentTimeMillis();
	int frames = 0;
	while(running) {
		long now = System.nanoTime();
		delta += (now - lastTime)/ns;
		lastTime = now;
		while(delta >= 1) {
			tick();
			delta--;
		}
		if(running) {
			render();
		}
		frames++;
		
		if(System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			Frame.setTitle("FPS: "+frames);
			frames = 0;
			
		}
	}
		stop();
	}
	
	public void tick() {

		try {
			 if(!homescreen.memberLogin){
				homescreen.tick();
			 }else{
				gamescreen.tick();
			 }
			//check if the settings screen need to be run
			if(!gamescreen.settings.press || setting) {
				gamescreen.settingsScreen.tick();
			}else if(gamescreen.settings.press && !setting){

				gamescreen.settings.press = false;
				gamescreen.settingsScreen.playerNameBox.textBoxEnabled = true;
				setting = true;
				gamescreen.settingsScreen.tick();
			}
		}catch (Exception e) {

		      }
	}
	
	
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		try {
			background.render(g);
			if(homescreen.memberLogin) {
				gamescreen.render(g);
			}else {
				homescreen.render(g);
			}
		} catch (Exception e) {
		      System.out.println(e);
		      e.printStackTrace();
		      }
		
		g.dispose();
		bs.show();
	}


	
public void window(int width,int height,String title,Main main){

	//set the game icon
	try {
		Taskbar.getTaskbar().setIconImage(ImageIO.read(getClass().getResource("/icon.PNG")));
	} catch (IOException e) {
	      System.out.println(e);
	      e.printStackTrace();
	  }
	dataBase.setMainInstance(main);
	dataBase.checkForTable();
	
	background = new Background();	
	
	Frame = new JFrame(title);
	Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Frame.setSize(width, height);
	Frame.setMinimumSize(new Dimension(width,height));
	Frame.setResizable(false);
	Frame.setLocationRelativeTo(null);
	Frame.add(main);
	Frame.setVisible(true);
	 
		
	main.start();
	
	homescreen = new HomeScreen(this); 
	homescreen.creatHomeScreen();
	game = new GameLoop(this);
	gamescreen = new GameScreen(this);
	
	}


	public static void main(String[] args) {
		
		new Main();
	}



}