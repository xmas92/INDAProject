package game.Server;

import java.util.UUID;

import com.esotericsoftware.kryonet.Connection;


public class PlayerConnection extends Connection {
	public String username;
	public UUID uuid;
	
}