package game.Server;

import game.Database.PlayerDB;
import game.Database.SpellDB;
import game.Entity.ServerZombie;
import game.Network.GameKryoReg;
import game.Resources.ResourceManager;

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


	public static ServerZombie sz;
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
			sz = new ServerZombie(server);
			sz.Initialize();
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
				SpellDB.updateAll(delta);
				sz.Update(delta);
				Thread.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized public int TPS() {
		return tps;
	}
	
	synchronized public void Stop() {
		server.stop();
	}
}