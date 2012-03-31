package game.util.IO;

import org.newdawn.slick.GameContainer;

public class InputState {
	public final KeyboardState KeyboardState;
	public final MouseState MouseState;
	public InputState() {
		KeyboardState = new KeyboardState();
		MouseState = new MouseState();
	}
	public void Update(GameContainer gc) {
		KeyboardState.Update(gc);
	}
	
}
