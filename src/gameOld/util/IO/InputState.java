package gameOld.util.IO;

import org.newdawn.slick.GameContainer;

public class InputState {
	private static InputState lastState;
	private static InputState currentState;
	public final long Time;
	public final KeyboardState KeyboardState;
	public final MouseState MouseState;
	private InputState(GameContainer gc) {
		KeyboardState = new KeyboardState();
		MouseState = new MouseState();
		Time = this.update(gc);
	}
	private long update(GameContainer gc) {
		KeyboardState.Update(gc);
		MouseState.Update(gc);
		return gc.getTime();
	}
	public static void Update(GameContainer gc) {
		lastState = currentState;
		currentState = new InputState(gc);
		if (lastState == null)
			lastState = currentState;
	}
	public static InputState GetLast() throws Exception {
		if (lastState == null)
			throw new Exception();
		return lastState;
	}
	public static InputState Get() throws Exception {
		if (currentState == null)
			throw new Exception();
		return currentState;
		
	}
}
