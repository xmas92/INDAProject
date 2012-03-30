package game.server.LoginServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import game.util.DB.DBTable;
import game.util.DB.Database;
import game.util.IO.Packages.GameServerInfoPackage;
import static game.util.DB.DBInfo.*;

public class LoginServer implements Runnable {
	private int port = 0;
	private GameServerInfoPackage gsip = null;
	private Database loginDB = new Database();
	private boolean running;
	public LoginServer() {
		if (new File(_LOGINDB).exists())
			loginDB.load(_LOGINDB);
		else {
			loginDB.addTable(DBTable.loginTable);
			loginDB.save(_LOGINDB);
		}
		try {
			String l;
			BufferedReader reader = new BufferedReader(new FileReader("LoginServer.ini"));
			int gsPort = 0;
			String gsIP = "";
			while ((l = reader.readLine()) != null) {
				if (l.startsWith("port="))
					port = Integer.parseInt(l.substring(("port=").length()));
				else if (l.startsWith("gameserver="))
					gsIP = l.substring(("gameserver=").length());
				else if (l.startsWith("gameserverport="))
					port = Integer.parseInt(l.substring(("gameserverport=").length()));
			}
			gsip = new GameServerInfoPackage(gsIP, gsPort);
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
			ss.setSoTimeout(30000);
			while(!ss.isClosed() && running) {
				try {
				 LoginConnection lc = new LoginConnection(loginDB, ss.accept(), gsip);
				 Thread t = new Thread(lc);
				 t.start();
				} catch (SocketTimeoutException e) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println("Stopping LoginServer");
	}
	
	synchronized public void Stop() {
		running = false;
	}
}
