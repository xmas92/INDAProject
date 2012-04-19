package game.Entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import game.Client;
import game.Event.Event;
import game.Screen.GameScreen;

public class Player implements Entity {
	
	private Image graphic;
	public float x, y, w = 32, h = 32;
	private float deltaX = 45.254834f, deltaY = 45.254834f;

	@Override
	public void Update(int delta) {
		if (Client.Game.getInput().isKeyDown(Input.KEY_W)) {
			y -= deltaY * delta / 1000.0f;
		}
		if (Client.Game.getInput().isKeyDown(Input.KEY_S)) {
			y += deltaY * delta / 1000.0f;
		}
		if (Client.Game.getInput().isKeyDown(Input.KEY_A)) {
			x -= deltaX * delta / 1000.0f;
		}
		if (Client.Game.getInput().isKeyDown(Input.KEY_D)) {
			x += deltaX * delta / 1000.0f;
		}
	}

	@Override
	public void Draw() {
		if (graphic != null)
			graphic.draw((GameScreen.w - w) * 0.5f, (GameScreen.h - h) * 0.5f, w, h);
	}

	@Override
	public void Callback(Event e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Initialize() {
		try {
			graphic = new Image("data/images/GameAssets/Player/player.bmp");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
