package game.Event;

import game.Input.MouseButton;

public class MouseDownEvent extends MouseButtonEvent implements Event {

	public MouseDownEvent(MouseButton mb) {
		super(mb);
	}

}
