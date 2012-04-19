package game.Event;

import java.awt.Point;


public class MouseLeaveEvent extends MouseEvent implements Event {

	public MouseLeaveEvent(Point position) {
		super(position);
	}

}
