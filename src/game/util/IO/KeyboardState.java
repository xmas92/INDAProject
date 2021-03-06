package game.util.IO;

import java.util.Enumeration;
import java.util.Hashtable;

import org.newdawn.slick.GameContainer;

public class KeyboardState {
	private Hashtable<Integer, KeyState> keys;
	public KeyboardState () {
		keys = new Hashtable<Integer, KeyState>();
		for (int i = 0; i < Keys.Keys.length; i++) {
			keys.put(Keys.Keys[i].VALUE, new KeyState(Keys.Keys[i]));
		}
	}
	public void Update(GameContainer gc) {
		Enumeration<KeyState> en = keys.elements();
		while (en.hasMoreElements())
			en.nextElement().Update(gc);
	}
	public Enumeration<KeyState> GetKeyStates() {
		return keys.elements();
	}
	public KeyState GetKeyState(Key key) {
		return keys.get(key.VALUE);
	}
	public KeyState GetKeyState(int key) {
		return keys.get(key);
	}
}
