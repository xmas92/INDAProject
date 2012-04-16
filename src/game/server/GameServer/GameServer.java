package game.server.GameServer;

import game.client.Entity.Player;
import game.client.Entity.Spell.Spell;
import game.client.Map.Map;
import game.client.Resource.ResourceManager;
import game.util.IO.Net.Network;
import game.util.IO.Net.Network.KillSpell;
import game.util.Physics.Physics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class GameServer implements Runnable {
	private int port = 0;
	private java.util.Map<String, Player> playerDB = Collections.synchronizedMap(new HashMap<String, Player>());
	private java.util.Map<Spell, String> spells = Collections.synchronizedMap(new HashMap<Spell, String>());
	public GameServer() {
		try {
			ResourceManager.Manager().init();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			String l;
			BufferedReader reader = new BufferedReader(new FileReader("LoginServer.ini"));
			while ((l = reader.readLine()) != null) {
				if (l.startsWith("gameserverport="))
					port = Integer.parseInt(l.substring(("gameserverport=").length()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	Server server;
	@Override
	public void run() {
		try {
			server = new Server() {
                protected Connection newConnection () {
                    return new PlayerConnection();
                }
			};
			Network.register(server);
			GameServerListener gsl = new GameServerListener(playerDB, server);
			gsl.spells = spells;
			server.addListener(gsl);
			server.start();
			server.bind(port, port+1);
			boolean running = true;
			long time = System.currentTimeMillis(),
				 tsl = System.currentTimeMillis();
			int i = 0;
			Map m = gsl.zones.get("WorldHUB"); // TODO Zones....
			while (running) {
				i++;
				if (System.currentTimeMillis() - time > 1000) {
					time = System.currentTimeMillis();
					//System.out.println(i + " Ticks / s");
					i = 0;
				}
				int delta = (int)(System.currentTimeMillis() - tsl);
				tsl = System.currentTimeMillis();
				synchronized (spells) {
					Iterator<Spell> it = spells.keySet().iterator();
					while (it.hasNext()) {
						Spell s = it.next();
						s.update(delta);
						if (m.getCollision(s.collisionBox())) {
							KillSpell ks = new KillSpell();
							ks.id = s.getId();
							server.sendToAllTCP(ks);
							it.remove();
						}
					}
				}	
				for (Player p : playerDB.values()) {
					synchronized (spells) {
						Iterator<Spell> it = spells.keySet().iterator();
						while (it.hasNext()) {
							Spell s = it.next();
							if (spells.get(s).equals(p.getPlayerID()))
								continue;
							if (Physics.getCollision(p.collisionBox(), s.collisionBox())) {
								KillSpell ks = new KillSpell();
								ks.id = s.getId();
								server.sendToAllTCP(ks);
								it.remove();
							}
						}
					}
				}
				Thread.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	synchronized public void Stop() {
		server.stop();
	}
}
