package game.Server;

import game.Database.LoginDB;
import game.Network.GameKryoReg.PlayerLoginRequest;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameServerListener extends Listener {
	
	@Override
	public void received (Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;
		if (object instanceof PlayerLoginRequest) {
			if (pc.username == null) {
				pc.username = ((PlayerLoginRequest)object).Username;
				if (!LoginDB.LoggedIn(pc.username)) pc.close();
			}
		}
	}
	
	@Override
	public void connected(Connection c)  {
		
	}
	
	@Override
	public void disconnected(Connection c)  {
		PlayerConnection pc = (PlayerConnection)c;
		if (pc.username != null) LoginDB.Logout(pc.username);
	}
}
