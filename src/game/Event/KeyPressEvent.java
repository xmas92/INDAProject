package game.Event;


public class KeyPressEvent extends KeyEvent implements Event {

	public KeyPressEvent(game.Input.Key key) {
		super(key);
	}

}
