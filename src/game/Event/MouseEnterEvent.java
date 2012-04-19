package game.Event;

import java.awt.Point;


public class MouseEnterEvent extends MouseEvent implements Event {

	public MouseEnterEvent(Point position) {
		super(position);
	}

}
