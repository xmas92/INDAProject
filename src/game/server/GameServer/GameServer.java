package game.server.GameServer;

import game.util.IO.Net.Network;
import game.util.IO.Net.Network.CharacterInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class GameServer implements Runnable{
	private int port = 0;
	private HashMap<String, CharacterInfo> playerDB = new HashMap<String, CharacterInfo>();
	public GameServer() {
		try {
			// TODO add players
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
			server.addListener(new GameServerListener(playerDB, server));
			server.start();
			server.bind(port, port+1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	synchronized public void Stop() {
		server.stop();
	}
}
