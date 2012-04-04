package game.server.GameServer;

import static game.util.IO.Net.Flags.*;
import static game.util.IO.Net.Types.*;
import game.client.Entity.CharacterInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientConnection implements Runnable {
	
	private Map<String, CharacterInfo> db;
	private Socket client;
	private boolean running;
	private DataOutputStream dos;
	private DataInputStream dis;
	private char flag, type;
	private String playerID;
	public ClientConnection(HashMap<String, CharacterInfo> playerDB, Socket accept) {
		db = Collections.synchronizedMap(playerDB);
		client = accept;
	}

	@Override
	public void run() {
		running = true;
		System.out.println("(GS)Client connected: " + client.getInetAddress());
		try {
			client.setSoTimeout(30000);
		} catch (SocketException e) {
			
			e.printStackTrace();
		}

		try {
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		while (running && !client.isClosed()) {
			if (Listen())
				Respond();
			else
				Disconnect();
		}
		try {
			db.remove(playerID);
			if (dos != null)
				dos.close();
			if (dis != null)
				dis.close();
			
			client.close();
			System.out.println("(GS)Closing Connection: " + client.getInetAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Disconnect() {
		running = false;
	}

	private void Respond() {
		try {
			if (flag == playersInfoRequest) {
				HandlePlayerInfoPackage();
			} else if (flag == closeConnectionRequest) {
				dos.writeChar(closeConnectionAcknowledged);
				dos.writeChar(EmptyPackage);
				running = false;
			} else {
				dos.writeChar(unknown);
				dos.writeChar(EmptyPackage);
			}
			dos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	private void HandlePlayerInfoPackage() {
		try {
			CharacterInfo ci = new CharacterInfo();
			ci.readFromStream(dis);
			playerID = dis.readUTF();
			db.put(playerID, ci);

			long time = System.currentTimeMillis();
			dos.writeChar(playersInfoAcknowledged);
			dos.writeChar(PlayersInfoPackage);
			Set<String> s = db.keySet();
			synchronized (db) {
				for (String string : s) {
					if (!string.equals(playerID)) {
						CharacterInfo nci = db.get(string);
						// Write ci
						dos.writeBoolean(true);
						nci.writeToStream(dos);
						// Write string
						dos.writeUTF(string);
					}
				}
			}
			dos.writeBoolean(false);
			System.out.println("Write: " + (System.currentTimeMillis() - time));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean Listen() {
		try {
			flag = dis.readChar();
			type = dis.readChar();
		} catch (SocketTimeoutException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
			return false;
		}
		return true;
	}

}
