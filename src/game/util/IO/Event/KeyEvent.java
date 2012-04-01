package game.util.IO.Event;

import game.util.IO.Key;

public class KeyEvent implements Event {

	public final Key Key;
	public KeyEvent(Key key) {
		Key = key;
	}

	@Override
	public EventType Type() {
		return EventType.KeyEvent;
	}

}
