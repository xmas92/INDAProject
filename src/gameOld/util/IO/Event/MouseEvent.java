package gameOld.util.IO.Event;


import java.awt.Point;


public class MouseEvent implements Event {

	private Point pos;
	
	public MouseEvent(Point position) {
		pos = position;
	}
	
	public Point Location() { return new Point(pos); }

	@Override
	public EventType Type() {
		return EventType.MouseEvent;
	}

}
