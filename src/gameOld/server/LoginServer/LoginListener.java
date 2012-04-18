package gameOld.server.LoginServer;

import gameOld.util.DB.DBTable;
import gameOld.util.DB.DBValue;
import gameOld.util.DB.Database;
import gameOld.util.DB.Fields.LoginInfoField;
import static gameOld.util.DB.DBValueType.DBString;
import static gameOld.util.IO.Net.Network.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LoginListener extends Listener {
	
	private Database db;
	private GameServerInfo gsi;
	
	public LoginListener(Database db, GameServerInfo gsi) {
		this.db = db;
		this.gsi = gsi;
	}
	
	@Override
	public void received (Connection c, Object object) {
		if (object instanceof Login) {
			try {
				Login login = (Login)object;
				LoginInfoField lif;
					lif = (LoginInfoField)db.getField(DBTable.loginTable, 
							   new DBValue[]{ new DBValue(DBString, "username") }, 
							   new Object[]{ login.username });
				if (lif != null) {
					if (lif.passwordHash == login.passwordHash) {
						LoginGranted lg = new LoginGranted();
						lg.gsi = gsi;
						c.sendTCP(lg);
						return;
					}
				}
				c.sendTCP(new LoginRefused());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void connected(Connection connection)  {
		
	}
	
	@Override
	public void disconnected(Connection connection) {
		
	}
}
