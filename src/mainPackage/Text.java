package mainPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;
import javax.swing.ImageIcon;

public class Text extends component{
	String FileLocation;
	Color textcolor = Color.BLACK, outlinecolor = Color.white;
	int sizeX, sizeY;
	ImageIcon image;
	
		public Text(int x ,int y, String text) {
			super(x, y, text);
			
		}
	
		
		//draw the text on the screen
		@Override
		public void rander(Graphics g) {
			g.setFont(font);
			if(FileLocation != null) {
				g.drawImage(image.getImage(), x, y, sizeX, sizeY,null);
				
				FontMetrics metrics = g.getFontMetrics(font);
				g.setColor(textcolor);
				g.drawString(text, (x + ((sizeX - metrics.stringWidth(text)) / 2)), (y + ((sizeY - metrics.getHeight()) / 2) + metrics.getAscent()));
			}else if (text != ""){
				Graphics2D g2d =  (Graphics2D) g;
				
				TextLayout tl = new TextLayout(text, font,g2d.getFontRenderContext());
				
				Shape shape = tl.getOutline(null);
				
				g2d.translate(x, y);
				g2d.setColor(outlinecolor);
				g2d.setStroke(new BasicStroke(font.getSize2D() / 10.0f));

				g2d.draw(shape);
				g2d.setColor(textcolor);
				g2d.fill(shape);
				
				g2d.translate(-x, -y);			
			}
		}
		
		
		//set the text color
		public void SetColor(Color color) {
			textcolor = color;
		}
		//set the text out line color
		public void SetOutLineColor(Color color) {
			outlinecolor = color;
		}
		//set the text out line color
		public void setTextImage(String FileLocation,int sizeX ,int sizeY) {
			this.FileLocation = FileLocation;
			this.sizeX = sizeX;
			this.sizeY = sizeY;

			image = new ImageIcon(getClass().getResource(FileLocation));
			
		}
		public String getFileLocation() {
			return FileLocation;
		}

		
}
