package game.Zones;

import game.Event.Event;
import game.Event.PlayerDrawEvent;
import game.Resources.ResourceManager;
import game.Screen.GameScreen;


public class HubZone implements Zone {

	private ZoneMap map;
	
	@Override
	public void Initialize() {
		try {
			map = ResourceManager.Manager().getMap("bonnyMap2:testmap.tmx");
			if (map == null)
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Update(int delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Draw() {
		if (map != null) {
			map.drawBG((float)GameScreen.player.position().getX()-GameScreen.w*0.5f, (float)GameScreen.player.position().getY()-GameScreen.h*0.5f, GameScreen.w, GameScreen.h);
		}
	}

	@Override
	public ZoneMap getZoneMap() {
		return map;
	}

	@Override
	public void Callback(Event e) {
		if (e instanceof PlayerDrawEvent) {
			if (map != null) {
				map.drawFG((float)GameScreen.player.position().getX()-GameScreen.w*0.5f, (float)GameScreen.player.position().getY()-GameScreen.h*0.5f, GameScreen.w, GameScreen.h);
			}
		}
	}

}
