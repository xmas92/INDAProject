package game.Event;

import java.awt.Point;


public class MouseOverEvent extends MouseEvent implements Event {

	public MouseOverEvent(Point position) {
		super(position);
	}

}
