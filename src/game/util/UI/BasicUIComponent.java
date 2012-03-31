package game.util.UI;

import game.util.IO.EventListner;

import org.newdawn.slick.GameContainer;

public abstract class BasicUIComponent implements UIComponent {
	
	private boolean ignoreFocus = false;
	public boolean getIgnoreFocus() { return ignoreFocus; }
	public void setIgnoreFocus(boolean ignoreFocus) { this.ignoreFocus = ignoreFocus; }
	
	private boolean focus = false;
	
	@Override
	public void update(GameContainer container) {
		// TODO Auto-generated method stub
		
		
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

	
	private EventListner updated, rendered;
	
	public void addUpdateEventListner(EventListner delegate) {
		if (updated == null)
			updated = delegate;
	}	
	public void addRenderEventListner(EventListner delegate) {
		if (rendered == null)
			rendered = delegate;
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
}
