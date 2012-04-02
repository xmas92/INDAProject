package game.util.UI;

import java.awt.Rectangle;

import game.util.IO.InputState;
import game.util.IO.Key;
import game.util.IO.Event.Event;
import game.util.IO.Event.EventListner;
import game.util.IO.Event.KeyEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class TextBox extends BasicUIComponent {

	private EventListner keyDown;
	private Font font;
	private StringBuilder sb;
	private int pos;
	private int margin = 5;
	public TextBox() {
		setupEventListners();
		setLocksFocus(true);
		pos = 0;
		sb = new StringBuilder();
		font = null;
	}
	
	public String getText() { return sb.toString(); }
	
	private void setupEventListners() {
		super.addKeyDownEventListner(new EventListner() {
			@Override
			public void Invoke(Object sender, Event e) {
				Key key = ((KeyEvent)e).Key;
				try {
					if (key.VALUE == Input.KEY_ENTER) {
						System.out.println(sb.toString());
					} else if (key.VALUE == Input.KEY_BACK) {
						if (pos > 0)
							sb.deleteCharAt(--pos);
					} else if (key.VALUE == Input.KEY_ESCAPE) {
						removeFocus();
					} else if (InputState.Get().KeyboardState.GetKeyState(Input.KEY_LSHIFT).Down()) {
						if (key.ALTCHARACTER != null)
							sb.insert(pos++, key.ALTCHARACTER);
						else if (key.CHARACTER != null)
							sb.insert(pos++, key.CHARACTER);
					} else {
						if (key.CHARACTER != null)
							sb.insert(pos++, key.CHARACTER);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (keyDown != null)
					keyDown.Invoke(sender, e);
			}
		});
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		Font f = g.getFont();
		if (font != null)
			f = font;
		Rectangle rec = getRectangle();
		Color c = g.getColor();
		g.setColor(Color.blue);
		g.drawRect(rec.x, rec.y, rec.width, rec.height);
		g.setColor(c);
		if (rec.height - 2 * margin >= f.getLineHeight()) {
			g.drawString(sb.toString(), rec.x + margin, rec.y + margin);
		}
		if (hasFocus()) {
			float a = rec.x + f.getWidth(sb.toString()) + 4,
				  b = rec.y + f.getLineHeight();
			g.drawLine(a, b, a+10, b);
		}
	}
	

	@Override
	public void addKeyDownEventListner(EventListner delegate) {
		if (keyDown == null)
			keyDown = delegate;
	}

}
