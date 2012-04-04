package game.TEST;

import game.util.IO.Packages.PackageFlag;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class NetIOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread server = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ServerSocket ss = null;
				try {
					ss = new ServerSocket(12345);
					System.out.println("Binding: " + ss.getInetAddress() + ":" + ss.getLocalPort());
					ss.setSoTimeout(30000);
					Socket client = ss.accept();
					long time = System.currentTimeMillis();
					int i = 0;
					ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
					while(!client.isClosed()) {
						try {
							ois.readUTF();++i;
							if (System.currentTimeMillis() - time > 1000) {
								time = System.currentTimeMillis();
								System.out.println("Reads/s: " + i);
								i = 0;
							}
							
						} catch (Exception e) {
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (ss != null) {
					try {
						ss.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Stopping Server");
			}
		});
		server.start();
		try {
			Socket s = new Socket("81.229.86.19", 12345);
			long time = System.currentTimeMillis();
			int i = 0;
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			while(!s.isClosed()) {
				try {
					oos.writeUTF("Test");++i;
					if (System.currentTimeMillis() - time > 1000) {
						time = System.currentTimeMillis();
						System.out.println("Writes/s: " + i);
						i = 0;
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
