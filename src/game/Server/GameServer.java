package game.Server;

import game.Database.PlayerDB;
import game.Database.SpellDB;
import game.Database.ZombieDB;
import game.Entity.ServerZombie;
import game.Network.GameKryoReg;
import game.Resources.ResourceManager;
import game.Zones.HubZone;
import game.Zones.ZoneMap;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class GameServer implements Runnable {
	private int port = 0;
	public GameServer() {
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


	private Server server;
	private int tps = 0;
	@Override
	public void run() {
		try {
			ResourceManager.Manager().Initialize();
			server = new Server() {
                protected Connection newConnection () {
                    return new PlayerConnection();
                }
			};
			HubZone z = new HubZone();
			z.Initialize();
			ZoneMap m = z.getZoneMap();
			SetupSpawnPoints(m);
			(new GameKryoReg()).Register(server);
			GameServerListener gsl = new GameServerListener(server);
			server.addListener(gsl);
			server.start();
			server.bind(port, port+1);
			boolean running = true;
			long lastTime = System.currentTimeMillis();
			while (running) {
				int delta = (int)(System.currentTimeMillis() - lastTime);
				lastTime = System.currentTimeMillis();
				PlayerDB.updateAll(delta);
				ZombieDB.updateAll(delta);
				SpellDB.updateAll(delta);
				Thread.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SetupSpawnPoints(ZoneMap m) {
		for (int tileX = 0; tileX < m.getWidth(); tileX++) {
			for (int tileY = 0; tileY < m.getHeight(); tileY++) {
				if (m.getTileId(tileX, tileY, 5) != 0) {
					CreateZombie(tileX,tileY); 
				}
				if (m.getTileId(tileX, tileY, 6) != 0) {
					SetSpawnPoint(tileX,tileY);
				}
			}
		}
	}
	public static Point2D PlayerSpawnPoint;
	private void SetSpawnPoint(int tileX, int tileY) {
		PlayerSpawnPoint = new Point2D.Float((tileX - 0.5f) * 32.0f, (tileY - 0.5f) * 32.0f);
	}

	private void CreateZombie(int tileX, int tileY) {
		ServerZombie sz = new ServerZombie(server);
		sz.spawnPos = new Point2D.Float((tileX - 0.5f) * 32.0f, (tileY - 0.5f) * 32.0f);
		sz.Initialize();
		ZombieDB.addZombie(sz, sz.uuid);
		
	}

	synchronized public int TPS() {
		return tps;
	}
	
	synchronized public void Stop() {
		server.stop();
	}
}