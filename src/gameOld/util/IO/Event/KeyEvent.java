package gameOld.util.IO.Event;

import gameOld.util.IO.Key;

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
