package game.client.Entity;

import org.newdawn.slick.Input;

import game.util.IO.InputState;
import game.util.IO.Net.Network.PlayerInfo;

public class Player extends Character {
	private String playerID;
	private boolean changed = false;
	public Player(PlayerInfo playerInfo) {
		super(playerInfo.characterInfo);
		playerID = playerInfo.player;
	}
	
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
				ci.y -= delta * ci.speed / 1000.0f;
				ci.deltaY -= 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_S).Down()) {
				ci.y += delta * ci.speed / 1000.0f;
				ci.deltaY += 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_A).Down()) {
				ci.x -= delta * ci.speed / 1000.0f;
				ci.deltaX -= 1;
			}
			if (is.KeyboardState.GetKeyState(Input.KEY_D).Down()) {
				ci.x += delta * ci.speed / 1000.0f;
				ci.deltaX += 1;
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
