package game.Input;

import org.newdawn.slick.Input;

public class Key {
	public final int VALUE;
	public final int TYPE;
	public final String CHARACTER;
	public final String ALTCHARACTER;
	public Key(int key, int type) {
		VALUE = key;
		TYPE = type;
		CHARACTER = null;
		ALTCHARACTER = null;
	}
	public Key(int key, int type, String string) {
		VALUE = key;
		TYPE = type;
		CHARACTER = string;
		ALTCHARACTER = null;
	}
	public Key(int key, int type, String string, String altString) {
		VALUE = key;
		TYPE = type;
		CHARACTER = string;
		ALTCHARACTER = altString;
	}
	public boolean isDown(Input input) {
		return input.isKeyDown(VALUE);
	}
}