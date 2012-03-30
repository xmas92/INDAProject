package game.server.LoginServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;

import game.util.DB.DBTable;
import game.util.DB.Database;
import static game.util.DB.DBInfo.*;

public class LoginServer {
	public LoginServer() {
		int port = 0, gsPort = 0;
		String gsIP = "";
		Database loginDB = new Database();
		if (new File(_LOGINDB).exists())
			loginDB.load(_LOGINDB);
		else {
			loginDB.addTable(DBTable.loginTable);
			loginDB.save(_LOGINDB);
		}
		try {
			String l;
			BufferedReader reader = new BufferedReader(new FileReader("LoginServer.ini"));
			while ((l = reader.readLine()) != null) {
				if (l.startsWith("port="))
					port = Integer.parseInt(l.substring(("port=").length()));
				else if (l.startsWith("gameserver="))
					gsIP = l.substring(("gameserver=").length());
				else if (l.startsWith("gameserverport="))
					port = Integer.parseInt(l.substring(("gameserverport=").length()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			ServerSocket ss = new ServerSocket(port);
			 while(!ss.isClosed()) {
				 
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
