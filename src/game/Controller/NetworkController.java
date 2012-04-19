package game.Controller;

import game.Event.NetworkEvent;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class NetworkController implements Controller {
	
	private Client client;
	private boolean init = false;
	private Queue<NetworkEvent> EventQueue;
	private boolean connected = false;

	private NetworkController() { }
	private static NetworkController self;
	private static NetworkController Singelton() {
		if (self == null)
			self = new NetworkController();
		return self;
	}
	
	public static void Register(Class<?> c) {
		Singelton().register(c);
	}
	
	private void register(Class<?> c) {
		client.getKryo().register(c);
	}

	public static void Connect(String ip, int TCP, int UDP) {
		Singelton().connect(ip, TCP, UDP);
	}
	
	private void connect(String ip, int TCP, int UDP) {
		try {
			client.connect(8000, ip, TCP, UDP);
			connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void Disconnect() {
		Singelton().close();
	}

	public static void Initialize() {
		Singelton().init();
	}
	
	public static NetworkEvent PollEvent() {
		return Singelton().pollEvent();
	}

	public static void SendTCP(Object Package) {
		Singelton().sendTCP(Package);
	}
	
	private void sendTCP(Object Package) {
		if (!connected) return;
		client.sendTCP(Package);
	}

	public static void SendUDP(Object Package) {
		Singelton().sendUDP(Package);
	}
	
	private void sendUDP(Object Package) {
		if (!connected) return;
		client.sendUDP(Package);
	}
	
	private synchronized NetworkEvent pollEvent() {
		if (!init) return null;
		return EventQueue.poll();
	}

	private synchronized void addEvent(NetworkEvent e) {
		EventQueue.add(e);
	}
	
	private void init() {
		if (init) return;
		init = true;
		EventQueue = new LinkedList<NetworkEvent>();
		client = new Client();
		client.start();
		client.addListener(new ThreadedListener(new Listener() {
            public void connected (Connection connection) {
            }
            public void received (Connection connection, Object object) {
            	addEvent(new NetworkEvent(connection, object));
            }
            public void disconnected (Connection connection) {
            	close();
            }
		}));
	}
	
	private void close() {
		client.close();
		EventQueue = null;
		connected = false;
		init = false;
	}
	
	@Override
	public void Update(int delta) {
		// TODO Auto-generated method stub

	}

}
