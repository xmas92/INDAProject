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
		super(playerInfo.entityInfo);
		playerID = playerInfo.player;
	}
	
	public float getHealth() { return health; }
	public void setHealth(float health) { this.health = health; }
	
	public float getPlayerX() {
		return info.x;
	}
	public float getPlayerY() {
		return info.y;
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
			info.deltaY = 0;
			info.deltaX = 0;
			
			if (is.KeyboardState.GetKeyState(Input.KEY_W).Down()) {
				info.deltaY -= 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_S).Down()) {
				info.deltaY += 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_A).Down()) {
				info.deltaX -= 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_D).Down()) {
				info.deltaX += 1;
			}
			changed = (info.deltaX != 0 || info.deltaY != 0);
			if (changed) {
				float change = (info.speed * delta / 1000.0f) / (float)Math.sqrt(info.deltaX*info.deltaX + info.deltaY*info.deltaY);
				if (!MainGame.map.getCollision(new Rectangle(info.x, info.y+info.deltaY*change, 32,32))) 
					info.y += info.deltaY * change;
				else
					info.deltaY = 0;
				if (!MainGame.map.getCollision(new Rectangle(info.x+info.deltaX*change, info.y, 32,32))) 
						info.x += info.deltaX * change;
				else
					info.deltaX = 0;
			}
			changed = (info.deltaX != 0 || info.deltaY != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PlayerInfo getPlayerInfo() {
		PlayerInfo pi = new PlayerInfo();
		pi.player = playerID;
		pi.entityInfo = getEntityInfo();
		return pi;
	}
	
	@Override
	public boolean hasChanged() {
		return changed;
	}
}
