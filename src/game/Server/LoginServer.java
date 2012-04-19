package game.Server;

import game.Network.LoginGranted;
import game.Network.LoginRefused;
import game.Network.LoginRequested;

import java.io.BufferedReader;
import java.io.FileReader;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class LoginServer implements Runnable {

	private int port = 0;
	private int gsPort = 0;
	private String gsIP = "";

	public LoginServer() {
		try {
			String l;
			BufferedReader reader = new BufferedReader(new FileReader("LoginServer.ini"));
			while ((l = reader.readLine()) != null) {
				if (l.startsWith("listeningport="))
					port = Integer.parseInt(l.substring(("listeningport=").length()));
				else if (l.startsWith("gameserver="))
					gsIP = l.substring(("gameserver=").length());
				else if (l.startsWith("gameserverport="))
					gsPort = Integer.parseInt(l.substring(("gameserverport=").length()));
			}
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
			server.getKryo().register(LoginGranted.class);
			server.getKryo().register(LoginRequested.class);
			server.getKryo().register(LoginRefused.class);
			LoginGranted login = new LoginGranted();
			login.IP = gsIP;
			login.Port = gsPort;
			server.addListener(new LoginListener(login));
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
