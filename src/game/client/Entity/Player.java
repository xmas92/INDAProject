package game.client.Entity;

import org.newdawn.slick.Input;

import game.client.Game.MainGame;
import game.util.Geom.Rectangle;
import game.util.IO.InputState;
import game.util.IO.Net.Network.PlayerInfo;

public class Player extends Character {
	private String playerID;
	private boolean changed = false;
	private float health = 100.0f;
	
	public Player(PlayerInfo playerInfo) {
		super(playerInfo.characterInfo);
		playerID = playerInfo.player;
	}
	
	public float getHealth() { return health; }
	public void setHealth(float health) { this.health = health; }
	
	public float getPlayerX() {
		return ci.x;
	}
	public float getPlayerY() {
		return ci.y;
	}
	
	public void Draw() {
		if (graphic == null)
			return;
		graphic.draw(400,300);
	}
	
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getPlayerID() {
		return playerID;
	}
	
	@Override
	public void update(int delta) {
		try {
			InputState is = InputState.Get();
			ci.deltaY = 0;
			ci.deltaX = 0;
			
			if (is.KeyboardState.GetKeyState(Input.KEY_W).Down()) {
				ci.deltaY -= 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_S).Down()) {
				ci.deltaY += 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_A).Down()) {
				ci.deltaX -= 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_D).Down()) {
				ci.deltaX += 1;
			}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PlayerInfo getPlayerInfo() {
		PlayerInfo pi = new PlayerInfo();
		pi.player = playerID;
		pi.characterInfo = getCharacterInfo();
		return pi;
	}
	
	@Override
	public boolean hasChanged() {
		return changed;
	}
}
