package game.server.GameServer;

import com.esotericsoftware.kryonet.Connection;

public class PlayerConnection extends Connection {
	public String username;
	public boolean moving = true;
}
