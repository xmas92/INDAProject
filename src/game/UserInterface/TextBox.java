package game.UserInterface;

import game.TEST.ChatboxTest;

import java.awt.Rectangle;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public class TextBox extends AbstractUserInterface {
	
	private Font font; 
	public String string = "abcdefghijlkmnopqrstuvwxyzåäö";
	private Image graphic;
	private int lineCount = 1; //temporary set as 1, else 0
	private int margin = 5; 
	
	
	public TextBox(Image i) {
		graphic = i;
		setLocksFocus(true);
		font = null;
	}
	
	public void addString(String s) {
		string = s; 
		lineCount++; 
	}
	
	String temporary = ""; 
	
	@Override
	public void Draw(){
		Font f = ChatboxTest.agc.getGraphics().getFont();
		if (font != null) 
			f = font;
		
		Rectangle rec = getRectangle();
		graphic.draw(rec.x, rec.y, rec.x + rec.width, rec.y + rec.height,
				0, 0, graphic.getWidth(), graphic.getHeight());
		
		int charWidth = f.getWidth("i");
		int charHeight = f.getHeight("A");
		int numberOfChar =  rec.width/charWidth;
						
		if(f.getWidth(string) > rec.width - margin) {
		
			temporary = string.substring(0, numberOfChar); 
			ChatboxTest.agc.getGraphics().drawString(temporary, rec.x + 2*margin, rec.y + charHeight*1); //HOW DO USE INCREMENTz?
			temporary = string.substring(numberOfChar, string.length()); 
			ChatboxTest.agc.getGraphics().drawString(temporary, rec.x + 2*margin, rec.y + charHeight*2); //HOW DO USE INCREMENTz?

			
			} else {
			ChatboxTest.agc.getGraphics().drawString(string, rec.x + margin, rec.y + charHeight*0 + margin * lineCount);	
			}		
		}

}
