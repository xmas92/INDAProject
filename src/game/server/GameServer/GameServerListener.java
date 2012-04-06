package game.server.GameServer;

import game.client.Map.Map;
import game.util.IO.Net.Network.CharacterInfo;
import game.util.IO.Net.Network.PlayerInfo;
import game.util.IO.Net.Network.RemovePlayer;
import game.util.IO.Net.Network.UpdatePlayer;

import java.util.HashMap;

import org.newdawn.slick.SlickException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServerListener extends Listener {

	public HashMap<String, CharacterInfo> playerDB;
	public HashMap<String, String> playersZone;
	public HashMap<String, Map> zones;
	private Server server;
	
	public GameServerListener(HashMap<String, CharacterInfo> playerDB, Server server) {
		this.playerDB = playerDB;
		this.server = server;
		zones = new HashMap<>();
		playersZone = new HashMap<>();
		try {
			zones.put("WorldHUB", new Map("data/maps/bonnyMap2/testmap.tmx", false));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void received (Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;
		if (object instanceof UpdatePlayer) {
			PlayerInfo pi = ((UpdatePlayer)object).playerInfo;
			if (pc.username == null) {
				pc.username = pi.player;
				playersZone.put(pc.username, "WorldHUB");
			}
			Map m = zones.get(playersZone.get(pi.player));
			if (m != null) {
				int i = 0;
				i += m.getTileId((int)pi.characterInfo.x / m.getTileWidth(), (int)pi.characterInfo.y / m.getTileHeight(), 3);
				i += m.getTileId((int)pi.characterInfo.x / m.getTileWidth()+1, (int)pi.characterInfo.y / m.getTileHeight(), 3);
				i += m.getTileId((int)pi.characterInfo.x / m.getTileWidth(), (int)pi.characterInfo.y / m.getTileHeight()+1, 3);
				i += m.getTileId((int)pi.characterInfo.x / m.getTileWidth()+1, (int)pi.characterInfo.y / m.getTileHeight()+1, 3);
				if (i != 0) {
					UpdatePlayer up = new UpdatePlayer();
					up.playerInfo = new PlayerInfo();
					up.playerInfo.characterInfo = playerDB.get(pi.player);
					up.playerInfo.characterInfo.deltaX = 0;
					up.playerInfo.characterInfo.deltaY = 0;
					up.playerInfo.player = pi.player;
					server.sendToTCP(c.getID(), up);
					return;
				}
			}
			playerDB.put(pi.player, pi.characterInfo);
			server.sendToAllExceptUDP(c.getID(), object);
		}
	}
	
	@Override
	public void connected(Connection c)  {
		
	}
	
	@Override
	public void disconnected(Connection c) {
		playerDB.remove(((PlayerConnection)c).username);
		RemovePlayer rp = new RemovePlayer();
		rp.username = ((PlayerConnection)c).username;
		server.sendToAllExceptTCP(c.getID(), rp);
	}

}
