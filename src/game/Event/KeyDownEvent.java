package game.Event;


public class KeyDownEvent extends KeyEvent implements Event {

	public KeyDownEvent(game.Input.Key key) {
		super(key);
	}

}
