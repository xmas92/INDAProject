package game.UserInterface;

import game.Client;
import game.TEST.ChatboxTest;

import java.awt.Rectangle;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class TextBox extends AbstractUserInterface {
	
	private Graphics g; 
	private Font font; 
	public String temp = "test";
	private Image graphic;
	private int lineCount = 0; 
	private int margin = 5; 
	
	
	public TextBox(Image i) {
		graphic = i;
		setLocksFocus(true);
		font = null;
	}
	
	public void addString(String s) {
		lineCount++; 
		temp = s; 
	}
	
	String test = "abcasjdaösljfalskf";
	
	@Override
	public void Draw(){
		Font f = ChatboxTest.agc.getGraphics().getFont();
		if (font != null) 
			f = font;
		
		int charWidth = f.getWidth("A");
		
		int charHeight = f.getHeight("A");
		
		Rectangle rec = getRectangle();
		graphic.draw(rec.x, rec.y, rec.x + rec.width, rec.y + rec.height,
				0, 0, graphic.getWidth(), graphic.getHeight());
		
		ChatboxTest.agc.getGraphics().drawString(test, rec.x + 2*margin, rec.y + margin);	
		
	}

}
