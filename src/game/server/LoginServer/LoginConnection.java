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
import game.util.IO.AcknowledgeFlag;
import game.util.IO.AcknowledgeFlags;
import game.util.IO.RequestFlag;
import game.util.IO.RequestFlags;
import game.util.IO.Packages.GameServerInfoPackage;
import game.util.IO.Packages.LoginInfoPackage;

public class LoginConnection implements Runnable {

	private Database loginDB;
	private Socket client;
	private GameServerInfoPackage gsip;
	private boolean running = false;
	private RequestFlags flag;
	private ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public LoginConnection(Database loginDB, Socket client, GameServerInfoPackage gsip) {
		this.loginDB = loginDB;
		this.client = client;
		this.gsip = gsip;
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
			// TODO Auto-generated catch block
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
			oos.writeObject(new RequestFlag(RequestFlags.poke));
			oos.flush();
			
			AcknowledgeFlag flag = (AcknowledgeFlag) ois.readObject();
			if (flag.flag != AcknowledgeFlags.pokeback)
				running = false;
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	private void Respond() {
		try {
			if (this.flag == RequestFlags.loginRequest) {
				LoginInfoPackage lip = (LoginInfoPackage) ois.readObject();
				
				LoginInfoField  lif = (LoginInfoField)loginDB.getField(DBTable.loginTable, 
																	   new DBValue[]{ new DBValue(DBString, "username") }, 
																	   new Object[]{ lip.username });
				if (lif != null) {
					if (lif.passwordHash == lip.passwordHash) {
						oos.writeObject(new AcknowledgeFlag(AcknowledgeFlags.loginGranted));
						oos.writeObject(gsip);
						oos.flush();
						running = false;
						return;
					}
				}
				oos.writeObject(new AcknowledgeFlag(AcknowledgeFlags.loginRefused));
				oos.flush();
			} else {
				oos.writeObject(new AcknowledgeFlag(AcknowledgeFlags.unkownRequest));
				oos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	private boolean Listen() {
		try {
			RequestFlag rf = (RequestFlag) ois.readObject();
			flag = rf.flag;
		} catch (SocketTimeoutException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
		return true;
	}
}
