package game.server.LoginServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import game.util.DB.DBTable;
import game.util.DB.DBValue;
import static game.util.DB.DBValueType.*;
import game.util.DB.Database;
import game.util.DB.Fields.LoginInfoField;
import game.util.IO.Packages.EmptyPackage;
import game.util.IO.Packages.Package;
import game.util.IO.Packages.GameServerInfoPackage;
import game.util.IO.Packages.LoginInfoPackage;
import game.util.IO.Packages.PackageFlag;

public class LoginConnection implements Runnable {

	private Database loginDB;
	private Socket client;
	private GameServerInfoPackage gsip;
	private boolean running = false;
	private Package pkg;
	private ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public LoginConnection(Database loginDB, Socket client, GameServerInfoPackage gsip) {
		this.loginDB = loginDB;
		this.client = client;
		this.gsip = new GameServerInfoPackage(gsip.ip, gsip.port);
		this.gsip.flag = PackageFlag.loginGranted;
	}
	
	@Override
	public void run() {
		running = true;
		System.out.println("Client connected: " + client.getInetAddress());
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
				Poke();
		}
		try {
			if (oos != null)
				oos.close();
			if (ois != null)
				ois.close();
			
			client.close();
			System.out.println("Closing Connection: " + client.getInetAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void Poke() {
		try {
			oos.writeObject(new EmptyPackage(PackageFlag.poke));
			oos.flush();
			
			pkg = (Package) ois.readObject();
			if (pkg.Flag() != PackageFlag.pokeback)
				running = false;
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	private void Respond() {
		try {
			if (pkg.Flag() == PackageFlag.loginRequest) {
				LoginInfoPackage lip = (LoginInfoPackage) pkg;
				
				LoginInfoField  lif = (LoginInfoField)loginDB.getField(DBTable.loginTable, 
																	   new DBValue[]{ new DBValue(DBString, "username") }, 
																	   new Object[]{ lip.username });
				if (lif != null) {
					if (lif.passwordHash == lip.passwordHash) {
						oos.writeObject(gsip);
						oos.flush();
						return;
					}
				}
				oos.writeObject(new EmptyPackage(PackageFlag.loginRefused));
				oos.flush();
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

	private boolean Listen() {
		try {
			pkg = (Package) ois.readObject();
		} catch (SocketTimeoutException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
		return true;
	}
}
