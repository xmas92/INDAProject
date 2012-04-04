package game.client.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import game.util.IO.Net.Flags;
import game.util.IO.Net.NetIOQueue;
import game.util.IO.Net.PlayersInfoPackage;
import game.util.IO.Packages.GameServerInfoPackage;
import game.util.IO.Net.Package;

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
		 
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			Package pkg;
			boolean connected = true;
			while (connected) {
				while ((pkg = NIQQ.pollOutPackage()) != null) {
					// Write package
					long time = System.currentTimeMillis();
					dos.writeChar(pkg.Flag());
					dos.writeChar(pkg.Type());
					pkg.packload().writeToStream(dos);
					dos.flush();
					System.out.println("Write: " + (System.currentTimeMillis() - time));
					// Read package
					time = System.currentTimeMillis();
					char flag = dis.readChar();
					char type = dis.readChar();
					pkg = GetPackage(flag, type, dis);
					System.out.println("Read: " + (System.currentTimeMillis() - time));
					if (pkg == null) {
						connected = false;
						break;
					}
					if (pkg.Flag() == Flags.closeConnectionAcknowledged) {
						connected = false;
						break;
					}
					NIQQ.addInPackage(pkg);
				}
			}
			
			dis.close();
			dos.close();
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

	private Package GetPackage(char flag, char type, DataInputStream dis) {
		if (flag == Flags.playersInfoAcknowledged) {
			PlayersInfoPackage pip = new PlayersInfoPackage(flag);
			pip.readFromStream(dis);
			return pip;
		}
		return null;
	}
}
