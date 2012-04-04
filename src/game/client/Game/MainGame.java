package game.client.Game;

import java.util.HashMap;
import java.util.Iterator;

import game.client.Entity.CharacterInfo;
import game.client.Entity.Player;
import game.client.Entity.Character;
import game.client.Login.LoginScreen;
import game.client.Map.Map;
import game.util.IO.InputState;
import game.util.IO.Net.Flags;
import game.util.IO.Net.NetIOQueue;
import game.util.IO.Net.PlayerInfoPackage;
import game.util.IO.Net.PlayersInfoPackage;
import game.util.IO.Net.Package;
import game.util.IO.Net.Types;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class MainGame implements Game {

	public AppGameContainer apc;
	private LoginScreen ls;
	private String playerID;
	private Player player;
	private HashMap<String, Character> players;
	private Map map;
	private final NetIOQueue NIQQ = new NetIOQueue();
	public MainGame() {
		ls = new LoginScreen();
	}
	
	@Override
	public boolean closeRequested() {
		return true;
	}

	@Override
	public String getTitle() {
		if (ls != null)
			return ls.getTitle();
		return "MainGameWindow";
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		if (ls != null) {
			ls.init(container);
			System.out.println("LoginScreen Init");
			return;
		}
		System.out.println("Main Game Init");
		CharacterInfo ci = new CharacterInfo();
		ci.speed = 62;
		ci.imageID = "data/GameAssets/Player/player.bmp";
		player = new Player(ci);
		player.setPlayerID(playerID);
		map = new Map("data/GameAssets/Map/untitled.tmx");
		players = new HashMap<String, Character>();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if (ls != null) {
			ls.render(container, g);
			return;
		}
		map.draw(player.getPlayerX() - 400, player.getPlayerY() - 300, 800, 600);
		for (Character c : players.values()) {
			c.draw(player.getPlayerX(), player.getPlayerY());
		}
		player.Draw();
	}
	
	private long lastUpdateTime;
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (ls != null) {
			ls.update(container, delta);
			if (ls.gsip != null) {
				playerID = ls.un;
				GameServerConnection gs = new GameServerConnection(ls.gsip, NIQQ);
				Thread t = new Thread(gs);
				t.start();
				ls = null;
				apc.setDisplayMode(800, 600, false);
				container.reinit();
			}
			return;
		}
		InputState.Update(container);
		HandleNetIO(container, delta);
		for (Character c : players.values()) {
			c.update(delta);
		}
		player.update(delta);
		if (container.getTime() - lastUpdateTime > 200 && NIQQ.outSize() < 2) {
			lastUpdateTime = container.getTime();
			SendPlayerUpdate();
		}
	}

	private void SendPlayerUpdate() {
		PlayerInfoPackage pkg = new PlayerInfoPackage(player.getCharacterInfo(), player.getPlayerID(), Flags.playersInfoRequest);
		NIQQ.addOutPackage(pkg);
	}

	private void HandleNetIO(GameContainer container, int delta) {
		Package pkg;
		while ((pkg = NIQQ.pollInPackage()) != null) {
			if (pkg.Flag() == Flags.playersInfoAcknowledged) {
				if (pkg.Type() == Types.PlayersInfoPackage) {
					PlayersInfoPackage pip = (PlayersInfoPackage) pkg;
					for (int i = 0; i < pip.cis.size() && i < pip.playerIDs.size(); i++) {
						if (players.containsKey(pip.playerIDs.get(i))) {
							players.get(pip.playerIDs.get(i)).setCharacterInfo(pip.cis.get(i));
						} else {
							players.put(pip.playerIDs.get(i), new Character(pip.cis.get(i)));
						}
					}
					Iterator<String> it = players.keySet().iterator();
					while (it.hasNext()) {
						if (!pip.playerIDs.contains(it.next())) {
							it.remove();
						}
					}
				}
			}
		}
	}

}
