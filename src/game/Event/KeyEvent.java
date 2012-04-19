package game.Event;

import game.Input.Key;

public class KeyEvent implements Event {

	public final Key Key;
	public KeyEvent(Key key) {
		Key = key;
	}
}
