package game.UserInterface;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;


import game.Model;
import game.View;
import game.Controller.Controller;
import game.Input.InputState;
import game.Input.KeyState;
import game.Input.MouseButton;
import game.Event.EventListner;
import game.Event.KeyEvent;
import game.Event.MouseButtonEvent;
import game.Event.MouseEvent;
import game.Event.NullEvent;

public class AbstractUserInterface implements Model, View, Controller {

	private boolean ignoreFocus = false;
	public boolean getIgnoreFocus() { return ignoreFocus; }
	public void setIgnoreFocus(boolean ignoreFocus) { this.ignoreFocus = ignoreFocus; }
	private boolean locksFocus = false;
	public boolean getLocksFocus() { return locksFocus; }
	public void setLocksFocus(boolean locksFocus) { this.locksFocus = locksFocus; }

	private static AbstractUserInterface lastFocus = null;
	private static AbstractUserInterface currentFocus = null;
	private static boolean focusChanged = false;
	private static boolean lockFocus = false;

	private int layer;
	public int getLayer() { return layer; }
	public void setLayer(int layer) { this.layer = layer; }

	private AbstractUserInterface parent;
	public AbstractUserInterface getParent() { return parent; }
	private void setParent(AbstractUserInterface parent) { this.parent = parent; }
	private LinkedList<AbstractUserInterface> children;
	
	public void addChild(AbstractUserInterface child) {
		addChild(child,0);
	}
	public void addChild(AbstractUserInterface child, int layer) {
		if (children == null)
			children = new LinkedList<AbstractUserInterface>();
		children.add(child);
		child.setParent(this);
		Collections.sort(children, new Comparator<AbstractUserInterface>() {
			@Override
			public int compare(AbstractUserInterface arg0, AbstractUserInterface arg1) {
				//return Integer.compare(arg0.getLayer(), arg1.getLayer());
				return arg0.getLayer() - arg1.getLayer(); // TODO Danger, unexpected behavior with big and small values
			}
		});
	}
	public void removeChild(AbstractUserInterface child) {
		children.remove(child);
	}
	
	private Point location = new Point();
	public Point getLocation() { return location; }
	public void setLocation(Point location) { this.location = new Point(location); }
	private Dimension dimension = new Dimension();
	public Dimension getDimension() { return dimension; }
	public void setDimension(Dimension dimension) { this.dimension = new Dimension(dimension); }
	public Rectangle getRectangle() { return new Rectangle(location, dimension); }
	
	private boolean rightB, leftB, middleB;
	
	private boolean enabled = true;
	public boolean isEnabled() { return enabled; }
	public void Enable() { enabled = true; onEnable.Invoke(this, new NullEvent()); }
	public void Disable() { enabled = false; onDisable.Invoke(this, new NullEvent()); }
	
	public void removeFocus() { 
		if (lostFocus != null)
			lostFocus.Invoke(this, new NullEvent());
		currentFocus = null;
		lockFocus = false;
	}
	public boolean hasFocus() {
		return currentFocus == this;
	}
	
	private void gainFocus() {
		if (currentFocus == this)
			return;
		if (focusChanged) {
			currentFocus = this;
		} else {
			lastFocus = currentFocus;
			if (lastFocus != null)
				lastFocus.removeFocus();
			currentFocus = this;
			focusChanged = true;
		}
		
	}
	private EventListner mouseEnter, mouseLeave, mouseOver, mouseClick, mouseMultiClick, mouseUp, mouseDown;
	
	public void addMouseEnterEventListner(EventListner delegate) {
			if (mouseEnter == null)
				mouseEnter = delegate;
	}
	public void addMouseLeaveEventListner(EventListner delegate) {
		if (mouseLeave == null)
			mouseLeave = delegate;
	}
	public void addMouseOverEventListner(EventListner delegate) {
		if (mouseOver == null)
			mouseOver = delegate;
	}
	public void addMouseMultiClickEventListner(EventListner delegate) {
		if (mouseMultiClick == null)
			mouseMultiClick = delegate;
	}
	public void addMouseClickEventListner(EventListner delegate) {
		if (mouseClick == null)
			mouseClick = delegate;
	}
	public void addMouseUpEventListner(EventListner delegate) {
		if (mouseUp == null)
			mouseUp = delegate;
	}
	public void addMouseDownEventListner(EventListner delegate) {
		if (mouseDown == null)
			mouseDown = delegate;
	}

	
	private EventListner updated, rendered, validate;
	
	public void addUpdateEventListner(EventListner delegate) {
		if (updated == null)
			updated = delegate;
	}	
	public void addRenderEventListner(EventListner delegate) {
		if (rendered == null)
			rendered = delegate;
	}
	public void addValidateEventListner(EventListner delegate) {
		if (validate == null)
			validate = delegate;
	}
	
	private EventListner lostFocus, gainedFocus;
	
	public void addLostFocusEventListner(EventListner delegate) {
		if (lostFocus == null)
			lostFocus = delegate;
	}	
	public void addGainedFocusEventListner(EventListner delegate) {
		if (gainedFocus == null)
			gainedFocus = delegate;
	}
	
	
	private EventListner keyDown, keyUp, keyPress;
	
	public void addKeyDownEventListner(EventListner delegate) {
		if (keyDown == null)
			keyDown = delegate;
	}	
	public void addKeyUpEventListner(EventListner delegate) {
		if (keyUp == null)
			keyUp = delegate;
	}	
	public void addKeyPressEventListner(EventListner delegate) {
		if (keyPress == null)
			keyPress = delegate;
	}
	
	private EventListner onEnable, onDisable;
	
	public void addOnEnabledListner(EventListner delegate) {
		if (onEnable == null)
			onEnable = delegate;
	}
	public void addOnDisabledListner(EventListner delegate) {
		if (onDisable == null)
			onDisable = delegate;
	}
	
	@Override
	public void Update(int delta) {
		if (!enabled)
			return;
		InputState last = null;
		InputState current = null;
		try {
			last = InputState.GetLast();
			current = InputState.Get();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Rectangle r = this.getRectangle();
		Point lp = last.MouseState.getPosition(),
			  cp = current.MouseState.getPosition();
		boolean lr = last.MouseState.isRightButtonDown(),
				cr = current.MouseState.isRightButtonDown(),
				ll = last.MouseState.isLeftButtonDown(),
				cl = current.MouseState.isLeftButtonDown(),
				lm = last.MouseState.isMiddleButtonDown(),
				cm = current.MouseState.isMiddleButtonDown();
		
		if (mouseEnter != null) {
			if (r.contains(cp) && !r.contains(lp))
				mouseEnter.Invoke(this, new MouseEvent(cp));
		}
		if (mouseOver != null) {
			if (r.contains(cp))
				mouseOver.Invoke(this, new MouseEvent(cp));
		}
		if (r.contains(lp) && !r.contains(cp)) {
			if (mouseLeave != null)
				mouseLeave.Invoke(this, new MouseEvent(cp));
			rightB = false;
			leftB = false;
			middleB = false;
		}
		boolean resetFocusChanged = focusChanged;
		if (r.contains(cp)) {
			if (!lr && cr) {
				gainFocus();
				if (mouseDown != null)
					mouseDown.Invoke(this, new MouseButtonEvent(MouseButton.RIGHT));
				rightB = true;
			}
			if (!ll && cl) {
				gainFocus();
				if (mouseDown != null)
					mouseDown.Invoke(this, new MouseButtonEvent(MouseButton.LEFT));
				leftB = true;
			}
			if (!lm && cm) {
				gainFocus();
				if (mouseDown != null)
					mouseDown.Invoke(this, new MouseButtonEvent(MouseButton.MIDDLE));
				middleB = true;
			}
			if (mouseUp != null) {
				if (!cr && lr) {
					mouseUp.Invoke(this, new MouseButtonEvent(MouseButton.RIGHT));
					if (mouseClick != null) {
						if (rightB)
							mouseClick.Invoke(this, new MouseButtonEvent(MouseButton.RIGHT));
					}
					rightB = false;
				}
				if (!cl && ll) {
					mouseUp.Invoke(this, new MouseButtonEvent(MouseButton.LEFT));
					if (mouseClick != null) {
						if (leftB)
							mouseClick.Invoke(this, new MouseButtonEvent(MouseButton.LEFT));
					}
					leftB = false;
				}
				if (!cm && lm) {
					mouseUp.Invoke(this, new MouseButtonEvent(MouseButton.MIDDLE));
					if (mouseClick != null) {
						if (middleB)
							mouseClick.Invoke(this, new MouseButtonEvent(MouseButton.MIDDLE));
					}
					middleB = false;
				}
				if (mouseMultiClick != null) {
					try {
						throw new Exception("Not yet implemented");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		if (validate != null)
			validate.Invoke(this, new NullEvent());
		
		if (children != null)
			for (AbstractUserInterface child : children) {
				child.Update(delta);
			}

		
		focusChanged = resetFocusChanged;
		
		boolean focused = (currentFocus == this);
		if (gainedFocus != null) {
			if (focused && (lastFocus != this)) {
				gainedFocus.Invoke(this, new NullEvent());
				lockFocus = locksFocus;
			}
		}
		
		if (focused || (ignoreFocus && !lockFocus)) {
			Enumeration<KeyState> en = current.KeyboardState.GetKeyStates();
			while (en.hasMoreElements()) {
				KeyState ck = en.nextElement();
				KeyState lk = last.KeyboardState.GetKeyState(ck.key);
				if (keyDown != null) {
					if (ck.Down() && !lk.Down()) {
						keyDown.Invoke(this, new KeyEvent(ck.key));
					}
				}
				if (keyPress != null) {
					if (ck.Down()) {
						keyPress.Invoke(this, new KeyEvent(ck.key));
					}
				}
				if (keyUp != null) {
					if (lk.Down() && !ck.Down()) {
						keyUp.Invoke(this, new KeyEvent(ck.key));
					}
				}
			}
		}
		if (updated != null)
			updated.Invoke(this, new NullEvent());
	}

	@Override
	public void Draw() {
		if (rendered != null)
			rendered.Invoke(this, new NullEvent());
		if (children != null)
			for (AbstractUserInterface child : children) {
				child.Draw();
			}
	}

}
