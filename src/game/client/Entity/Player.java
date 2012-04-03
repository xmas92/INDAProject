package game.client.Entity;

import org.newdawn.slick.Input;

import game.util.IO.InputState;

public class Player extends Character {

	private String playerID;
	public Player(CharacterInfo characterInfo) {
		super(characterInfo);
		// TODO Auto-generated constructor stub
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}