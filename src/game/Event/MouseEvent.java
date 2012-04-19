package game.Event;


import java.awt.Point;


public class MouseEvent implements Event {

	private Point pos;
	
	public MouseEvent(Point position) {
		pos = position;
	}
	
	public Point Location() { return new Point(pos); }
	
}
