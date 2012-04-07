package game.client.Game;

import java.util.Collections;
import java.util.HashMap;

import game.client.Entity.Player;
import game.client.Entity.Character;
import game.client.Login.LoginScreen;
import game.client.Map.Map;
import game.client.Resource.ResourceManager;
import game.util.IO.InputState;
import game.util.IO.Net.GameClientListeners;
import game.util.IO.Net.Network;
import game.util.IO.Net.Network.CharacterInfo;
import game.util.IO.Net.Network.GameServerInfo;
import game.util.IO.Net.Network.PlayerInfo;
import game.util.IO.Net.Network.UpdatePlayer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.esotericsoftware.kryonet.Client;

public class MainGame implements Game {

	public AppGameContainer apc;
	private LoginScreen ls;
	private String playerID;
	private GameServerInfo gsi;
	public static Player player;
	public static java.util.Map<String, Character> players;
	private Map map;
	public static Client client;
	public MainGame() {
		ls = new LoginScreen();
	}
	
	@Override
	public boolean closeRequested() {
		if (ls != null)
			return true;
		if (client.isConnected())
			client.stop();
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
		ResourceManager.Manager().init();
		CharacterInfo ci = new CharacterInfo();
		ci.speed = 62;
		ci.imageID = "GameAssets:Player:player.bmp";
		PlayerInfo pi = new PlayerInfo();
		pi.characterInfo = ci;
		pi.player = playerID;
		player = new Player(pi);
		map = ResourceManager.Manager().getMap("bonnyMap2:testmap.tmx");
		players = Collections.synchronizedMap(new HashMap<String, Character>());
		client = new Client();
		client.start();
		Network.register(client);
		GameClientListeners.createListeners();
		try {
			client.connect(5000, gsi.ip, gsi.port, gsi.port + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		if (ls != null) {
			ls.render(container, g);
			return;
		}
		map.draw(player.getPlayerX() - 400, player.getPlayerY() - 300, 800, 600);
		synchronized (players) {
			for (Character c : players.values()) {
				c.draw(player.getPlayerX(), player.getPlayerY());
			}
		}
		player.Draw();
	}
	long time = System.currentTimeMillis();
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (ls != null) {
			ls.update(container, delta);
			if (ls.gsi != null) {
				playerID = ls.un;
				gsi = ls.gsi;
				ls = null;
				apc.setDisplayMode(800, 600, false);
				container.reinit();
			}
			return;
		}
		InputState.Update(container);

		synchronized (players) {
			for (Character c : players.values()) {
				c.update(delta);
			}
		}
		
		player.update(delta);
		if (System.currentTimeMillis() - time > 100) {
			UpdatePlayer up = new UpdatePlayer();
			up.playerInfo = player.getPlayerInfo();
			client.sendUDP(up);
		}
	}
}
