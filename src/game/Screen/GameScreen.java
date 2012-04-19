package game.Screen;

import org.newdawn.slick.SlickException;

import game.Client;
import game.Entity.EntityHandler;
import game.Entity.Player;
import game.Event.Event;
import game.Zones.Zones;

public class GameScreen implements Screen {

	public static final GameScreen instance = new GameScreen();
	
	public static final int w = 800, h = 600;
	
	public static final Player player = new Player();
	private Zones zones = new Zones();
	private EntityHandler entities = new EntityHandler();
	
	@Override
	public void Update(int delta) {
		player.Update(delta);
		entities.Update(delta);
	}

	@Override
	public void Initialize() {
		try {
			Client.Game.setDisplayMode(w, h, false);
			zones.Initialize();
			player.Initialize();
			entities.Initialize();
		} catch (SlickException e) {
			e.printStackTrace();
			Client.Game.exit();
		}
	}

	@Override
	public void Draw() {
		Zones.CurrentZone().Draw();
		entities.Draw();
		player.Draw();

	}

	@Override
	public void Callback(Event e) {
		player.Callback(e);
		entities.Callback(e);
	}

	@Override
	public void Dispose() {
		// TODO Auto-generated method stub

	}

}
