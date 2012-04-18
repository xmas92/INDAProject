package gameOld.util.IO.Event;

import gameOld.util.IO.MouseButton;

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
