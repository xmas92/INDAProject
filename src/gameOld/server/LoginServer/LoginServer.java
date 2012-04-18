package gameOld.server.LoginServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import gameOld.util.DB.DBTable;
import gameOld.util.DB.Database;
import gameOld.util.DB.Fields.LoginInfoField;
import gameOld.util.IO.Net.Network;
import gameOld.util.IO.Net.Network.GameServerInfo;
import static gameOld.util.DB.DBInfo.*;

public class LoginServer implements Runnable {
	private int port = 0;
	private final GameServerInfo gsi = new GameServerInfo();
	private Database loginDB = new Database();
	public LoginServer() {
		if (new File(_LOGINDB).exists())
			loginDB.load(_LOGINDB);
		else {
			loginDB.addTable(DBTable.loginTable);
			loginDB.save(_LOGINDB);
		}
		try {
			if (!loginDB.containsField(DBTable.loginTable,  new LoginInfoField("Axel", "pass".hashCode())))
				loginDB.addField(DBTable.loginTable, new LoginInfoField("Axel", "pass".hashCode()));
			if (!loginDB.containsField(DBTable.loginTable,  new LoginInfoField("Bonny", "pass".hashCode())))
				loginDB.addField(DBTable.loginTable, new LoginInfoField("Bonny", "pass".hashCode()));
			if (!loginDB.containsField(DBTable.loginTable,  new LoginInfoField("test", "pass".hashCode())))
				loginDB.addField(DBTable.loginTable, new LoginInfoField("test", "pass".hashCode()));
			if (!loginDB.containsField(DBTable.loginTable,  new LoginInfoField("test2", "pass".hashCode())))
				loginDB.addField(DBTable.loginTable, new LoginInfoField("test2", "pass".hashCode()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			String l;
			BufferedReader reader = new BufferedReader(new FileReader("LoginServer.ini"));
			int gsPort = 0;
			String gsIP = "";
			while ((l = reader.readLine()) != null) {
				if (l.startsWith("listeningport="))
					port = Integer.parseInt(l.substring(("listeningport=").length()));
				else if (l.startsWith("gameserver="))
					gsIP = l.substring(("gameserver=").length());
				else if (l.startsWith("gameserverport="))
					gsPort = Integer.parseInt(l.substring(("gameserverport=").length()));
			}
			gsi.ip = gsIP;
			gsi.port = gsPort;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	private Server server;
	
	@Override
	public void run() {
		try {
			server = new Server() {
                protected Connection newConnection () {
                    // By providing our own connection implementation, we can store per
                    // connection state without a connection ID to state look up.
                    return new LoginConnection();
                }
			};
			Network.register(server);
			server.addListener(new LoginListener(loginDB, gsi));
			server.start();
			server.bind(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	synchronized public void Stop() {
		server.stop();
	}
	static public class LoginConnection extends Connection {
		
	}
}
