package game.server.GameServer;

import game.client.Entity.CharacterInfo;
import game.util.DB.DBTable;
import game.util.DB.Database;
import game.util.DB.Fields.DBField;
import game.util.DB.Fields.PlayerInfoField;
import game.util.IO.Packages.EmptyPackage;
import game.util.IO.Packages.Package;
import game.util.IO.Packages.PackageFlag;
import game.util.IO.Packages.PackageType;
import game.util.IO.Packages.PlayerInfoPackage;
import game.util.IO.Packages.PlayersInfoPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ClientConnection implements Runnable {
	
	private Database db;
	private Socket client;
	private boolean running;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Package pkg;
	private String playerID;
	public ClientConnection(Database playerDB, Socket accept) {
		db = playerDB;
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
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
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
			db.deleteField(DBTable.playerTable, new PlayerInfoField(new CharacterInfo(), playerID));
			if (oos != null)
				oos.close();
			if (ois != null)
				ois.close();
			
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
			if (pkg.Flag() == PackageFlag.playersInfoRequest) {
				if (pkg.Type() == PackageType.PlayerInfoPackage) {
					HandlePlayerInfoPackage();
				}
			} else if (pkg.Flag() == PackageFlag.closeConnectionRequest) {
				oos.writeObject(new EmptyPackage(PackageFlag.closeConnectionAcknowledged));
				oos.flush();
				running = false;
			} else {
				oos.writeObject(new EmptyPackage(PackageFlag.unknown));
				oos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	private void HandlePlayerInfoPackage() {
		try {
			PlayerInfoPackage pip = (PlayerInfoPackage) pkg;
			playerID = pip.playerID;
			PlayerInfoField pif = new PlayerInfoField(pip.ci, pip.playerID);
			if (!db.containsField(DBTable.playerTable, pif)) {
				db.addField(DBTable.playerTable, pif);
			} else {
				db.changeField(DBTable.playerTable, pif, pif);
			}
			ArrayList<CharacterInfo> cis = new ArrayList<CharacterInfo>();
			ArrayList<String> playerIDs = new ArrayList<String>();
			ArrayList<DBField> pifs = db.getFields(DBTable.playerTable, null, null);
			for (DBField field : pifs) {
				PlayerInfoField t = (PlayerInfoField)field;
				if (!t.playerID.equals(pip.playerID)) {
					cis.add(t.ci);
					playerIDs.add(t.playerID);
				}
			}
			oos.writeObject(new PlayersInfoPackage(cis, playerIDs, PackageFlag.playersInfoAcknowledged));
			oos.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean Listen() {
		try {
			pkg = (Package) ois.readObject();
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
