package game.Screen;

import org.newdawn.slick.SlickException;

import game.Client;
import game.Entity.Player;
import game.Event.Event;
import game.Zones.HubZone;

public class GameScreen implements Screen {

	public static final GameScreen instance = new GameScreen();
	
	public static final int w = 800, h = 600;
	
	public static final Player player = new Player();
	private static HubZone zone = new HubZone();
	
	@Override
	public void Update(int delta) {
		player.Update(delta);

	}

	@Override
	public void Initialize() {
		try {
			Client.Game.setDisplayMode(w, h, false);
			
			player.Initialize();
			zone.Initialize();
		} catch (SlickException e) {
			e.printStackTrace();
			Client.Game.exit();
		}
	}

	@Override
	public void Draw() {
		zone.Draw();
		player.Draw();

	}

	@Override
	public void Callback(Event e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Dispose() {
		// TODO Auto-generated method stub

	}

}
