package game.client.Entity;

import game.util.IO.Net.Network.CharacterInfo;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Character {
	protected CharacterInfo ci;
	protected Image graphic = null;
	public Character (CharacterInfo characterInfo) {
		ci = new CharacterInfo();
		setCharacterInfo(characterInfo);
	}
	public CharacterInfo getCharacterInfo() {
		return ci.clone();
	}
	public void setCharacterInfo(CharacterInfo ci) {
		try {
		if (ci.imageID != this.ci.imageID)
				graphic = new Image(ci.imageID);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.ci = ci.clone();
	}
	
	public void draw(float x, float y) {
		if (graphic == null)
			return;
		float screenX = ci.x - x + 400;
		float screenY = ci.y - y + 300;
		if (screenX < -1000 || screenY < -1000 || screenX > 2000 || screenY > 2000)
			return;
		graphic.draw(screenX, screenY);
	}
	public void update(int delta) {
		if (ci.deltaY == -1) {
			ci.y -= delta * ci.speed / 1000.0f;
		}
		if (ci.deltaY == 1) {
			ci.y += delta * ci.speed / 1000.0f;
		}
		if (ci.deltaX == -1) {
			ci.x -= delta * ci.speed / 1000.0f;
		}
		if (ci.deltaX == 1) {
			ci.x += delta * ci.speed / 1000.0f;
		}
	}
}
