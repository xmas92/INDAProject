package game.Controller;

import game.Event.EventCallback;
import game.Event.NetworkEvent;
import game.Network.KryoRegister;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class NetworkController implements Controller {
	
	private static Client client;
	private static boolean init = false;
	private static boolean connected = false;
	private static EventCallback Callback = null;
	
	public static void Register(KryoRegister kryoReg) {
		if (!init) return;
		kryoReg.Register(client);
	}

	public static boolean Connect(String ip, int TCP) {
		return connect(ip, TCP, -1);
	}

	public static boolean Connect(String ip, int TCP, int UDP) {
		return connect(ip, TCP, UDP);
	}
	
	private static boolean connect(String ip, int TCP, int UDP) {
		if (connected) return true;
		try {
			if (UDP == -1) {
				client.connect(5000, ip, TCP);
			} else {
				client.connect(5000, ip, TCP, UDP);
			}
			connected = true;
		} catch (IOException e) {
			System.err.print(e.getMessage());
		}
		return connected;
	}
	
	public static void SetCallback(EventCallback callback) {
		Callback = callback;
	}
	
	public static void Disconnect() {
		if (!connected) return;
		client.close();
		connected = false;
	}
	public static void Reset() {
		init = false;
		Initialize();
	}

	public static void Initialize() {
		if (init) return;
		init = true;
		client = new Client();
		client.start();
		client.addListener(new ThreadedListener(new Listener() {
            public void connected (Connection connection) {
            }
            public void received (Connection connection, Object object) {
            	if (Callback != null) {
            		Callback.Callback(new NetworkEvent(connection, object));
            	}
            }
            public void disconnected (Connection connection) {
            	Disconnect();
            }
		}));
	}

	public static void SendTCP(Object Package) {
		if (!connected) return;
		client.sendTCP(Package);
	}

	public static void SendUDP(Object Package) {
		if (!connected) return;
		client.sendUDP(Package);
	}
	
	@Override
	public void Update(int delta) {
		// TODO Auto-generated method stub

	}

}
