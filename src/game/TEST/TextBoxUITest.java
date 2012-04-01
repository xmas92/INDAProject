package game.TEST;

import game.util.IO.InputState;
import game.util.IO.Event.Event;
import game.util.IO.Event.EventListner;
import game.util.UI.Button;
import game.util.UI.TextBox;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TextBoxUITest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {try {
		AppGameContainer agc = new AppGameContainer(new Game() {
			private TextBox tb;
			@Override
			public void update(GameContainer container, int delta)
					throws SlickException {
				InputState.Update(container);
				tb.update(container);
			}
			
			@Override
			public void render(GameContainer container, Graphics g)
					throws SlickException {
				tb.render(container, g);
				
			}
			
			@Override
			public void init(final GameContainer container) throws SlickException {
				tb = new TextBox();
				tb.setLocation(new Point(20,20));
				tb.setDimension(new Dimension(300, 50));
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
