package game.Event;

import game.Input.MouseButton;

public class MouseClickEvent extends MouseButtonEvent implements Event {

	public MouseClickEvent(MouseButton mb) {
		super(mb);
	}

}
