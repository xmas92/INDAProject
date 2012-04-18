package gameOld.util.IO;

import org.newdawn.slick.GameContainer;

public class KeyState {
	public final Key key;
	private boolean state;
	public KeyState(Key key) {
		this.key = key;
	}

	public void Update(GameContainer gc) {
		state = key.isDown(gc.getInput());
	}
	
	public boolean Down() {
		return state;
	}
}
