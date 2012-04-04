package game.server;

import game.server.GameServer.GameServer;
import game.server.LoginServer.LoginServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EntryPoint {

	public static LoginServer ls;
	public static GameServer gs;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(inStream);
		String line = "";
		startLoginServer();
		startGameServer();
		while (!line.toLowerCase().equals("exit")) {
			try {
				line = reader.readLine();
				if (line.toLowerCase().equals("stop loginserver")) {
					stopLoginServer();
				} else if (line.toLowerCase().equals("start loginserver")) {
					startLoginServer();
				} else if (line.toLowerCase().equals("stop gameserver")) {
					stopGameServer();
				} else if (line.toLowerCase().equals("start gameserver")) {
					startGameServer();
				} else if (line.toLowerCase().equals("exit")) {
					stopLoginServer();
					stopGameServer();
					Thread.sleep(30000);
					System.out.println("Exiting...");
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void startLoginServer() {
		if (ls != null)
			return;
		System.out.println("Init LoginServer");
		ls = new LoginServer();
		Thread lsThread = new Thread(ls);
		System.out.println("Running LoginServer");
		lsThread.start();
	}

	public static void stopLoginServer() {
		ls.Stop();
		ls = null;
		System.out.println("Stopping LoginServer safely within 30 sec.");
	}
	
	public static void startGameServer() {
		if (gs != null)
			return;
		System.out.println("Init GameServer");
		gs = new GameServer();
		Thread lsThread = new Thread(gs);
		System.out.println("Running GameServer");
		lsThread.start();
	}

	public static void stopGameServer() {
		gs.Stop();
		gs = null;
		System.out.println("Stopping GameServer safely within 30 sec.");
	}
	
}
