package game.util.IO.Event;

import game.util.IO.MouseButton;

public class MouseButtonEvent implements Event {

	public final MouseButton Button;
	
	public MouseButtonEvent(MouseButton mb) {
		Button = mb;
	}

	@Override
	public EventType Type() {
		return EventType.MouseButtonEvent;
	}

}
