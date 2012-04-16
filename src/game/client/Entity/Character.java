package game.client.Entity;

import game.client.Game.MainGame;
import game.client.Resource.ResourceManager;
import game.util.Geom.Rectangle;
import game.util.IO.Net.Network.CharacterInfo;

import org.newdawn.slick.Image;

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
		if (ci.imageID != this.ci.imageID)
				graphic = ResourceManager.Manager().getImage(ci.imageID);
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
	private boolean changed = false;
	public void update(int delta) {
		changed = (ci.deltaX != 0 || ci.deltaY != 0);
		if (changed) {
			float change = (ci.speed * delta / 1000.0f) / (float)Math.sqrt(ci.deltaX*ci.deltaX + ci.deltaY*ci.deltaY);
			if (!MainGame.map.getCollision(new Rectangle(ci.x, ci.y+ci.deltaY*change, 32,32))) 
				ci.y += ci.deltaY * change;
			else
				ci.deltaY = 0;
			if (!MainGame.map.getCollision(new Rectangle(ci.x+ci.deltaX*change, ci.y, 32,32))) 
					ci.x += ci.deltaX * change;
			else
				ci.deltaX = 0;
		}
		changed = (ci.deltaX != 0 || ci.deltaY != 0);
	}
	public boolean hasChanged() {
		return true;
	}
}
