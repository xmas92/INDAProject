package game.Server;

import game.Database.UserDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server {

	private static LoginServer ls;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(inStream);
		String line = "";
		StartLoginServer();
		boolean running = true;
		while (running) {
			try {
				line = reader.readLine();
				if (line == null)
					line = "";
				if (line.toLowerCase().equals("add user")) {
					System.out.println("Add user");
					System.out.print("Username: ");
					String u = reader.readLine();
					if ("cancel".equalsIgnoreCase(u))
						continue;
					System.out.print("Password: ");
					String p = reader.readLine();
					if ("cancel".equalsIgnoreCase(p))
						continue;
					if (u != null && p != null)
						UserDB.AddUser(u, p.hashCode());
				} else if (line.toLowerCase().equals("print users")) {
					UserDB.PrintUsers();
				} else if (line.toLowerCase().equals("save users")) {
					UserDB.Save();
				} else if (line.toLowerCase().equals("stop loginserver")) {
					StopLoginServer();
				} else if (line.toLowerCase().equals("start loginserver")) {
					StartLoginServer();
				} else if (line.toLowerCase().equals("exit")) {
					StopLoginServer();
					UserDB.Save();
					running = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static boolean lsStarted = false;
	private static void StartLoginServer() {
		if (lsStarted)
			return;
		System.out.println("Starting Loginserver");
		ls = new LoginServer();
		Thread t = new Thread(ls);
		t.start();
		lsStarted = true;
	}
	private static void StopLoginServer() {
		if (!lsStarted)
			return;
		System.out.println("Stopping Loginserver");
		ls.Stop();
		ls = null;
		lsStarted = false;
	}

}
