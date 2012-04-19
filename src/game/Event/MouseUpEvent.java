package game.Event;

import game.Input.MouseButton;

public class MouseUpEvent extends MouseButtonEvent implements Event {

	public MouseUpEvent(MouseButton mb) {
		super(mb);
	}

}
