package game.Server;

import game.Database.LoginDB;
import game.Database.UserDB;
import game.Network.LoginGranted;
import game.Network.LoginRefused;
import game.Network.LoginRequested;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LoginListener extends Listener {

	private final LoginGranted login;
	
	public LoginListener(LoginGranted login) {
		this.login = login;
	}

	@Override
	public void received (Connection c, Object object) {
		if (object instanceof LoginRequested) {
			LoginRequested lr = (LoginRequested)object;
			if (UserDB.ValidUser(lr.Username,lr.PasswordHash)) {
				if (LoginDB.LoggedIn(lr.Username)) {
					LoginRefused ret = new LoginRefused();
					ret.Reason = "Already Logged In";
					c.sendTCP(ret);
				} else {
					c.sendTCP(login);
					LoginDB.Login(lr.Username);
				}
			} else {
				LoginRefused ret = new LoginRefused();
				ret.Reason = "Bad Authentication";
				c.sendTCP(ret);
			}
			c.close();
		}
	}
	
	@Override
	public void connected(Connection connection)  {
		
	}
	
	@Override
	public void disconnected(Connection connection) {
		
	}
}
