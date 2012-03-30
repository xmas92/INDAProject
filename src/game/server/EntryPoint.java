package game.server;

import game.server.LoginServer.LoginServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputStreamReader inStream = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(inStream);
		System.out.println("Init LoginServer");
		LoginServer ls = new LoginServer();
		Thread lsThread = new Thread(ls);
		System.out.println("Running LoginServer");
		lsThread.start();
		String line = "";
		while (!line.toLowerCase().equals("exit")) {
			try {
				line = reader.readLine();
				if (line.toLowerCase().equals("stop loginserver")) {
					ls.Stop();
					System.out.println("Stopping login server safely within 30 sec.");
				} else if (line.toLowerCase().equals("exit")) {
					System.out.println("Exiting...");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
