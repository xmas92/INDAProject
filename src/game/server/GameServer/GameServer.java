package game.server.GameServer;

import static game.util.DB.DBInfo._PLAYERDB;
import game.util.DB.DBTable;
import game.util.DB.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class GameServer implements Runnable{
	private int port = 0;
	private Database playerDB = new Database();
	private boolean running;
	public GameServer() {
		if (new File(_PLAYERDB).exists())
			playerDB.load(_PLAYERDB);
		else {
			playerDB.addTable(DBTable.playerTable);
			playerDB.save(_PLAYERDB);
		}
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

	@Override
	public void run() {
		ServerSocket ss = null;
		running = true;
		try {
			ss = new ServerSocket(port);
			System.out.println("Binding: " + ss.getInetAddress() + ":" + ss.getLocalPort());
			ss.setSoTimeout(30000);
			while(!ss.isClosed() && running) {
				try {
					ClientConnection cc = new ClientConnection(playerDB, ss.accept());
					Thread t = new Thread(cc);
					t.start();
				} catch (SocketTimeoutException e) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ss != null) {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		playerDB.save(_PLAYERDB);
		System.out.println("Stopping GameServer");
	}
	
	synchronized public void Stop() {
		running = false;
	}
}
