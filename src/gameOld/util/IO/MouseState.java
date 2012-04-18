package gameOld.util.IO;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class MouseState {
	private Point pos;
	private boolean r, m, l;
	public MouseState() {
		
	}
	public void Update(GameContainer gc) {
		int x = gc.getInput().getAbsoluteMouseX(),
		y = gc.getInput().getAbsoluteMouseY();
		pos = new Point(x, y);
		l = gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		m = gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
		r = gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
	}
	public Point getPosition() { return new Point(pos); }
	public boolean isLeftButtonDown() { return l; }
	public boolean isMiddleButtonDown() { return m; }
	public boolean isRightButtonDown() { return r; }
}
