package game.UserInterface;

import java.awt.Rectangle;

import game.Event.Event;
import game.Event.EventListner;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Checkbox extends AbstractUserInterface{
	private EventListner mouseClick;
	private Image graphic, uncheck, check; 
	private boolean b; 
	public Checkbox(SpriteSheet ss) {
		graphic = ss.getSprite(0, 0);
		uncheck = ss.getSprite(0, 0);
		check = ss.getSprite(0, 1);
		setupEventListeners();
	}
	
	public boolean isChecked() {
		return b; 
	}
	
	private void setupEventListeners() {
			
		super.addMouseClickEventListner(new EventListner() {
			@Override
			public void Invoke(Object sender, Event e) {
				graphic = check; 
				b = true; 
				if (graphic == check) {
					graphic = uncheck; 
					b = false; 
				}
			}
		});
	}
	
	@Override
	public void addMouseClickEventListner(EventListner delegate) {
		if (mouseClick == null) {
			mouseClick = delegate; 
		}
	}
	
	@Override
	public void Draw() {
		if (!isEnabled()) return;
		if (graphic == null) return;

		Rectangle rec = new Rectangle();
		graphic.draw(rec.x, rec.y, rec.x + rec.width, rec.y +rec.height,
					0, 0, graphic.getWidth(), graphic.getHeight());
		
	}
	
}
