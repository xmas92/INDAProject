package game.server.GameServer;

import game.util.IO.Net.Network.CharacterInfo;
import game.util.IO.Net.Network.PlayerInfo;
import game.util.IO.Net.Network.RemovePlayer;
import game.util.IO.Net.Network.UpdatePlayer;

import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServerListener extends Listener {

	public HashMap<String, CharacterInfo> playerDB;
	private Server server;
	
	public GameServerListener(HashMap<String, CharacterInfo> playerDB, Server server) {
		this.playerDB = playerDB;
		this.server = server;
	}
	
	@Override
	public void received (Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;
		if (object instanceof UpdatePlayer) {
			PlayerInfo pi = ((UpdatePlayer)object).playerInfo;
			if (pc.username == null) {
				pc.username = pi.player;
			}
			playerDB.put(pi.player, pi.characterInfo);
			server.sendToAllExceptTCP(c.getID(), object);
		}
	}
	
	@Override
	public void connected(Connection c)  {
		
	}
	
	@Override
	public void disconnected(Connection c) {
		playerDB.remove(((PlayerConnection)c).username);
		RemovePlayer rp = new RemovePlayer();
		rp.username = ((PlayerConnection)c).username;
		server.sendToAllExceptTCP(c.getID(), rp);
	}

}
