package gameOld.client.Login;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import gameOld.util.IO.Event.Event;
import gameOld.util.IO.Event.EventListner;
import gameOld.util.UI.BasicUIComponent;

public class LoginButton extends BasicUIComponent {
	private EventListner mouseOver, mouseDown, mouseUp, mouseLeave;
	private Image graphic, normal, over, down;
	private Color filter;
	public LoginButton(SpriteSheet ss) {
		graphic = ss.getSprite(0, 0);
		normal = ss.getSprite(0, 0);
		over = ss.getSprite(0, 1);
		down = ss.getSprite(0, 2);
		setupEventListners();
	}
	
	private void setupEventListners() {
		// Over
		super.addMouseOverEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				if (graphic != down)
					graphic = over;
				if (mouseOver != null)
					mouseOver.Invoke(sender, e);
			}
		});
		// Down
		super.addMouseDownEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				graphic = down;
				if (mouseDown != null)
					mouseDown.Invoke(sender, e);
			}
		});
		// Up
		super.addMouseUpEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				graphic = over;
				if (mouseUp != null)
					mouseUp.Invoke(sender, e);
			}
		});
		// leave
		super.addMouseLeaveEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				graphic = normal;
				if (mouseLeave != null)
					mouseLeave.Invoke(sender, e);
			}
		});
	}

	@Override
	public void addMouseOverEventListner(EventListner delegate) {
		if (mouseOver == null)
			mouseOver = delegate;
	}
	@Override
	public void addMouseUpEventListner(EventListner delegate) {
		if (mouseDown == null)
			mouseDown = delegate;
	}
	@Override
	public void addMouseDownEventListner(EventListner delegate) {
		if (mouseUp == null)
			mouseUp = delegate;
	}
	@Override
	public void addMouseLeaveEventListner(EventListner delegate) {
		if (mouseLeave == null)
			mouseLeave = delegate;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		if (!isEnabled()) return;
		if (graphic == null) return;
		Rectangle rec = getRectangle();
		if (filter == null) {
			g.drawImage(graphic, rec.x, rec.y, rec.x + rec.width, rec.y +rec.height,
					0, 0, graphic.getWidth(), graphic.getHeight());
		} else {
			g.drawImage(graphic, rec.x, rec.y, rec.x + rec.width, rec.y +rec.height,
					0, 0, graphic.getWidth(), graphic.getHeight(), filter);
		}
	}
}
