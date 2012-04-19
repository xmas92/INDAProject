package game.UserInterface;

import game.Client;
import game.Input.InputState;
import game.Input.Key;
import game.Event.Event;
import game.Event.EventListner;
import game.Event.KeyEvent;

import java.awt.Rectangle;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

public class LoginTextField extends AbstractUserInterface {
	
	private EventListner keyDown, keyPress, keyUp, mouseOver, mouseLeave;
	private Font font;
	private StringBuilder sb;
	private int pos;
	private int margin = 5;
	private Image graphic, normalEmpty, normalText, overEmpty, overText;
	private boolean renderStars;
	public LoginTextField(SpriteSheet ss) {
		graphic = ss.getSprite(0, 0);
		normalEmpty = ss.getSprite(0, 0);
		normalText = ss.getSprite(0, 2);
		overEmpty = ss.getSprite(0, 1);
		overText = ss.getSprite(0, 3);
		setupEventListners();
		setLocksFocus(true);
		pos = 0;
		sb = new StringBuilder();
		font = null;
	}


	public void setPasswordField(boolean b) {
		renderStars = b;
	}
	public String getText() { return sb.toString(); }
	
	private long lastBack = 0;
	private int countBack = 0;
	private void setupEventListners() {
		super.addKeyDownEventListner(new EventListner() {
			@Override
			public void Invoke(Object sender, Event e) {
				Key key = ((KeyEvent)e).Key;
				try {
					if (key.VALUE == Input.KEY_ENTER) {
						System.out.println(sb.toString());
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
		
		super.addKeyUpEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				Key key = ((KeyEvent)e).Key;
				if (key.VALUE == Input.KEY_BACK) {
					countBack = 0;
				}
			}
		});
		
		super.addKeyPressEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				Key key = ((KeyEvent)e).Key;
				if (key.VALUE == Input.KEY_BACK) {
					if (pos > 0) {
						try {
							if (InputState.Get().Time - lastBack > 250 ||
									(InputState.Get().Time - lastBack > 50) && countBack > 3) {
								countBack++;
								lastBack = InputState.Get().Time;
								sb.deleteCharAt(--pos);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} 
				if (keyPress != null)
					keyPress.Invoke(sender, e);
			}
		});
		
		super.addMouseLeaveEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				if (!hasFocus()) {
					if (sb.length() > 0) {
						graphic = normalText;
					} else {
						graphic = normalEmpty;
					}
				}
			}
		});

		// Over
		super.addMouseOverEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				if (!hasFocus()) {
					if (sb.length() > 0) {
						graphic = overText;
					} else {
						graphic = overEmpty;
					}
				}
				if (mouseOver != null)
					mouseOver.Invoke(sender, e);
			}
		});
		super.addLostFocusEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				if (sb.length() == 0) {
					graphic = normalEmpty;
				}
			}
		});
		super.addGainedFocusEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				graphic = normalText;
			}
		});
	}

	@Override
	public void Draw() {
		Font f = Client.Game.getGraphics().getFont();
		if (font != null)
			f = font;
		Rectangle rec = getRectangle();
		graphic.draw(rec.x, rec.y, rec.x + rec.width, rec.y +rec.height,
				0, 0, graphic.getWidth(), graphic.getHeight());
		String s = "";
		if (renderStars) {
			for (int i = 0; i < sb.length(); i++)
				s += "*";
		} else {
			s = sb.toString();
		}
		int w = f.getWidth(s);
		if (rec.height - 2 * margin >= f.getLineHeight()) {
			int i = (int) ((rec.width - w) * 0.5f);
			Client.Game.getGraphics().drawString(s, rec.x + i, rec.y + margin);
		}
	}
	
	@Override
	public void addMouseOverEventListner(EventListner delegate) {
		if (mouseOver == null)
			mouseOver = delegate;
	}
	@Override
	public void addMouseLeaveEventListner(EventListner delegate) {
		if (mouseLeave == null)
			mouseLeave = delegate;
	}
	
	@Override
	public void addKeyDownEventListner(EventListner delegate) {
		if (keyDown == null)
			keyDown = delegate;
	}
	@Override
	public void addKeyPressEventListner(EventListner delegate) {
		if (keyPress == null)
			keyPress = delegate;
	}
	@Override
	public void addKeyUpEventListner(EventListner delegate) {
		if (keyUp == null)
			keyUp = delegate;
	}
}
