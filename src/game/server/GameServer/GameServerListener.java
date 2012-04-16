package game.server.GameServer;

import static game.StaticVars.*;
import game.client.Entity.Player;
import game.client.Entity.Spell.ProjectileSpell;
import game.client.Entity.Spell.Spell;
import game.client.Map.Map;
import game.util.IO.Net.Network.CastProjectileSpell;
import game.util.IO.Net.Network.PlayerInfo;
import game.util.IO.Net.Network.RemovePlayer;
import game.util.IO.Net.Network.UpdatePlayer;

import java.util.Collections;
import java.util.HashMap;

import org.newdawn.slick.SlickException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServerListener extends Listener {

	public java.util.Map<String, Player> playerDB;
	public java.util.Set<Spell> spells;
	public java.util.Map<String, String> playersZone;
	public java.util.Map<String, Map> zones;
	private Server server;
	
	public GameServerListener(java.util.Map<String, Player> playerDB, Server server) {
		this.playerDB = playerDB;
		this.server = server;
		zones = Collections.synchronizedMap(new HashMap<String, Map>());
		playersZone = Collections.synchronizedMap(new HashMap<String, String>());
		try {
			// TODO Fix maps.... (zones)
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
				playerDB.put(pi.player, new Player(pi));
			}
			Map m = zones.get(playersZone.get(pi.player));
			if (m != null) {
				if (m.getCollision(playerDB.get(pi.player).collisionBox())) {
					UpdatePlayer up = new UpdatePlayer();
					up.playerInfo = new PlayerInfo();
					up.playerInfo.entityInfo = playerDB.get(pi.player).getEntityInfo();
					up.playerInfo.entityInfo.deltaX = 0;
					up.playerInfo.entityInfo.deltaY = 0;
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
			playerDB.get(pi.player).setEntityInfo(pi.entityInfo);
			if (pi.entityInfo.deltaX == 0 && pi.entityInfo.deltaY == 0 || !__updateWithUDP) {
				server.sendToAllExceptTCP(c.getID(), object);
			} else {
				server.sendToAllExceptUDP(c.getID(), object);
			}
		}
		if (object instanceof CastProjectileSpell) {
			ProjectileSpell s = new ProjectileSpell(((CastProjectileSpell)object).type, true,((CastProjectileSpell)object).id);
			s.setProjectileSpellInfo(((CastProjectileSpell)object).entityInfo);
			spells.add(s);
			server.sendToAllExceptTCP(c.getID(), object);
		}
	}
	
	@Override
	public void connected(Connection c)  {
		for (String s : playerDB.keySet()) {
			UpdatePlayer up = new UpdatePlayer();
			up.playerInfo = new PlayerInfo();
			up.playerInfo.entityInfo = playerDB.get(s).getEntityInfo();
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
