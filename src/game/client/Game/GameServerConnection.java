package game.client.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.util.IO.Net.NetIOQueue;
import game.util.IO.Packages.GameServerInfoPackage;
import game.util.IO.Packages.Package;
import game.util.IO.Packages.PackageFlag;

public class GameServerConnection implements Runnable {
	private final NetIOQueue NIQQ;
	private final GameServerInfoPackage gsip;
	public GameServerConnection(GameServerInfoPackage gsip , NetIOQueue NIQQ) {
		this.NIQQ = NIQQ;
		this.gsip = gsip;
	}
	
	@Override
	public void run() {
		Socket socket = null;
		try {
			socket = new Socket(gsip.ip, gsip.port);
		 
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Package pkg;
			boolean connected = true;
			while (connected) {
				while ((pkg = NIQQ.pollOutPackage()) != null) {
					// Write package
					long time = System.currentTimeMillis();
					oos.writeObject(pkg);
					oos.flush();
					System.out.println("Write: " + (System.currentTimeMillis() - time));
					// Read package
					time = System.currentTimeMillis();
					pkg = (Package) ois.readObject();
					System.out.println("Read: " + (System.currentTimeMillis() - time));
					if (pkg.Flag() == PackageFlag.closeConnectionAcknowledged)
						connected = false;
					NIQQ.addInPackage(pkg);
				}
			}
			
			ois.close();
			oos.close();
			socket.close();
	   } catch (Exception e) {
			e.printStackTrace();
	   } finally {
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	   }
		
	}
}
