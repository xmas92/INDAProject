package game.util.UI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface UIComponent {
	void render(GameContainer container, Graphics g);
	void update(GameContainer container);
}
