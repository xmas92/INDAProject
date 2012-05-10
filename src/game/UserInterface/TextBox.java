package game.UserInterface;

import game.Client;

import java.awt.Rectangle;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
/**
 * not finished don't be hating
 * @author bwc
 *
 */
public class TextBox extends AbstractUserInterface {
	
	private Font font; 
	public String temp = "";
	private Image graphic;
	private int lineCount = 0; 
	private int margin = 10; 
	
	
	public TextBox(Image i) {
		graphic = i;
		setLocksFocus(true);
		font = null;
	}
	
	public void addString(String s) {
		lineCount++; 
		temp = s; 
	}
	
	
	@Override
	public void Draw(){
		if (!isEnabled()) return;
		if (graphic == null) return;
		
		Font f = Client.Game.getGraphics().getFont();
		if (font != null) 
			f = font;
		
		int charWidth = f.getWidth("A");
		int charHeight = f.getHeight("A");
		
		Rectangle rec = getRectangle();
		graphic.draw(rec.x, rec.y, rec.x + rec.width, rec.y +rec.height,
				0, 0, graphic.getWidth(), graphic.getHeight());
		
		Client.Game.getGraphics().drawString(temp, rec.x + margin, rec.y + charHeight*lineCount);
	}

}
