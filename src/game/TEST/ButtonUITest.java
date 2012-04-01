package game.TEST;

import java.awt.Dimension;
import java.awt.Point;

import game.util.IO.InputState;
import game.util.IO.Event.Event;
import game.util.IO.Event.EventListner;
import game.util.UI.Button;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ButtonUITest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer agc = new AppGameContainer(new Game() {
				private Button btn;
				@Override
				public void update(GameContainer container, int delta)
						throws SlickException {
					InputState.Update(container);
					btn.update(container);
				}
				
				@Override
				public void render(GameContainer container, Graphics g)
						throws SlickException {
					btn.render(container, g);
					
				}
				
				@Override
				public void init(final GameContainer container) throws SlickException {
					btn = new Button(new Image("data/normal.bmp"), 
									 new Image("data/over.bmp"), 
									 new Image("data/down.bmp"));
					btn.setLocation(new Point(20,20));
					btn.setDimension(new Dimension(300, 50));
					btn.addMouseUpEventListner(new EventListner() {
						
						@Override
						public void Invoke(Object sender, Event e) {
							//container.exit();
						}
					});
				}
				
				@Override
				public String getTitle() {
					return "ButtonTEST";
				}
				
				@Override
				public boolean closeRequested() {
					return true;
				}
			});
			agc.setDisplayMode(340, 90, false);
			agc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
