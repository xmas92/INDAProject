package game.Zones;

import game.Screen.GameScreen;

import org.newdawn.slick.SlickException;

public class HubZone implements Zone {

	private ZoneMap map;
	
	@Override
	public void Initialize() {
		try {
			map = new ZoneMap("data/maps/bonnyMap2/testmap.tmx");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void Update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Draw() {
		if (map != null)
			map.draw(GameScreen.player.x, GameScreen.player.y, GameScreen.w, GameScreen.h);
	}

}
