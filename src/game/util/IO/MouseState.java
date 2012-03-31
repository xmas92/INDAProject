package game.util.IO;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class MouseState {
	private int x, y;
	private boolean r, m, l;
	public MouseState() {
		
	}
	public void Update(GameContainer gc) {
		x = gc.getInput().getAbsoluteMouseX();
		y = gc.getInput().getAbsoluteMouseY();
		l = gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		m = gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
		r = gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
	}
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean isLeftButtonDown() { return l; }
	public boolean isMiddleButtonDown() { return m; }
	public boolean isRightButtonDown() { return r; }
}
