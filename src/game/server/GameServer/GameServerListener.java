package game.server.GameServer;

import static game.StaticVars.*;
import game.client.Map.Map;
import game.util.IO.Net.Network.CastProjectileSpell;
import game.util.IO.Net.Network.EntityInfo;
import game.util.IO.Net.Network.PlayerInfo;
import game.util.IO.Net.Network.RemovePlayer;
import game.util.IO.Net.Network.UpdatePlayer;

import java.util.HashMap;

import org.newdawn.slick.SlickException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServerListener extends Listener {

	public HashMap<String, EntityInfo> playerDB;
	public HashMap<String, String> playersZone;
	public HashMap<String, Map> zones;
	private Server server;
	
	public GameServerListener(HashMap<String, EntityInfo> playerDB, Server server) {
		this.playerDB = playerDB;
		this.server = server;
		zones = new HashMap<String, Map>();
		playersZone = new HashMap<String, String>();
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
				int x= 0, y = 0, i = 0;
				if (pi.entityInfo.x < 0 || pi.entityInfo.x / m.getTileWidth()+1 > m.getWidth() ) {
					x = 1;
				} 
				if (pi.entityInfo.y < 0 || pi.entityInfo.y / m.getTileHeight()+1 > m.getHeight()) {
					y = 1;
				} 
				if (x == 0 && y == 0){
					i += m.getTileId((int)pi.entityInfo.x / m.getTileWidth(), (int)pi.entityInfo.y / m.getTileHeight(), 3);
					i += m.getTileId((int)pi.entityInfo.x / m.getTileWidth()+1, (int)pi.entityInfo.y / m.getTileHeight(), 3);
					i += m.getTileId((int)pi.entityInfo.x / m.getTileWidth(), (int)pi.entityInfo.y / m.getTileHeight()+1, 3);
					i += m.getTileId((int)pi.entityInfo.x / m.getTileWidth()+1, (int)pi.entityInfo.y / m.getTileHeight()+1, 3);
				}
				if (x+y+i != 0) {
					UpdatePlayer up = new UpdatePlayer();
					up.playerInfo = new PlayerInfo();
					up.playerInfo.entityInfo = playerDB.get(pi.player);
					if (x == 0 && y == 1) {
						up.playerInfo.entityInfo.x = pi.entityInfo.x;
						up.playerInfo.entityInfo.deltaX = 1;
					} else {
						up.playerInfo.entityInfo.deltaX = 0;
					}
					if (x == 1 && y == 0) {
						up.playerInfo.entityInfo.y = pi.entityInfo.y;
						up.playerInfo.entityInfo.deltaY = 1;
					} else {
						up.playerInfo.entityInfo.deltaY = 0;
					}
					up.playerInfo.player = pi.player;
					
					if (pc.moving)
						server.sendToAllExceptTCP(c.getID(), up);
					pc.moving = false;
					if (__updateWithUDP) {
						server.sendToUDP(c.getID(), up);
					} else {
						server.sendToTCP(c.getID(), up);
					}
					return;
				}
			}
			pc.moving = true;
			playerDB.put(pi.player, pi.entityInfo);
			if (pi.entityInfo.deltaX == 0 && pi.entityInfo.deltaY == 0 || !__updateWithUDP) {
				server.sendToAllExceptTCP(c.getID(), object);
			} else {
				server.sendToAllExceptUDP(c.getID(), object);
			}
		}
		if (object instanceof CastProjectileSpell) {
			server.sendToAllExceptTCP(c.getID(), object);
		}
	}
	
	@Override
	public void connected(Connection c)  {
		for (String s : playerDB.keySet()) {
			UpdatePlayer up = new UpdatePlayer();
			up.playerInfo = new PlayerInfo();
			up.playerInfo.entityInfo = playerDB.get(s);
			up.playerInfo.player = s;
			server.sendToTCP(c.getID(), up);
		}
	}
	
	@Override
	public void disconnected(Connection c) {
		playerDB.remove(((PlayerConnection)c).username);
		RemovePlayer rp = new RemovePlayer();
		rp.username = ((PlayerConnection)c).username;
		server.sendToAllExceptTCP(c.getID(), rp);
	}

}
