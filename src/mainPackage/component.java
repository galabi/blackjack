package mainPackage;

import java.awt.Font;
import java.awt.Graphics;


public class component {
	int x,y;
	String text;
	public Font font = new Font("Gisha", Font.BOLD, 16);

	
	public component(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public void rander(Graphics g) {
		
	}
	
	public void setLocation(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int x) {
		this.x = x;
	}
	
	//set the text
	public void setText(String text) {
		this.text = text;
	}
	//get the text
	public String getText() {
		return text;
	}
	//set font of the text
	public void setFont(Font font) {
		this.font = font;
	}
	public Font getFont() {
		return font;
	}
}
