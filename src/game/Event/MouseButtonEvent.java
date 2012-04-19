package game.Event;

import game.Input.MouseButton;

public class MouseButtonEvent implements Event {

	public final MouseButton Button;
	
	public MouseButtonEvent(MouseButton mb) {
		Button = mb;
	}
}
