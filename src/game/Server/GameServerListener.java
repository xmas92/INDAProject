package game.Server;

import java.util.UUID;

import game.Database.LoginDB;
import game.Database.PlayerDB;
import game.Database.ZombieDB;
import game.Entity.ServerPlayer;
import game.Entity.ServerSpell;
import game.Event.NetworkEvent;
import game.Event.PlayerConnectedEvent;
import game.Network.GameKryoReg.CastProjectileSpell;
import game.Network.GameKryoReg.DestroyGenerticEntity;
import game.Network.GameKryoReg.PlayerLoginRequest;
import game.Network.GameKryoReg.PlayerMovement;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServerListener extends Listener {
	
	private Server server;
	
	public GameServerListener(Server server) {
		this.server = server;
	}

	@Override
	public void received (Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;
		if (object instanceof PlayerLoginRequest) {
			if (pc.username == null) {
				ServerPlayer newPlayer = new ServerPlayer();
				newPlayer.Callback(new NetworkEvent(pc, object));
				ZombieDB.sendToAll(new PlayerConnectedEvent(pc));
			}
		} else if (object instanceof PlayerMovement) {
			PlayerDB.sendToAll(new NetworkEvent(pc, object));
		} else if (object instanceof CastProjectileSpell) {
			ServerSpell ss = new ServerSpell();
			ss.Callback(new NetworkEvent(pc, object));
		}
	}
	
	@Override
	public void connected(Connection c)  {
		((PlayerConnection)c).uuid = UUID.randomUUID();
		PlayerDB.sendToAll(new PlayerConnectedEvent((PlayerConnection)c));
	}
	
	@Override
	public void disconnected(Connection c)  {
		PlayerConnection pc = (PlayerConnection)c;
		if (pc.username != null) LoginDB.Logout(pc.username);
		DestroyGenerticEntity dge = new DestroyGenerticEntity();
		dge.UUIDp1 = pc.uuid.getLeastSignificantBits(); dge.UUIDp2 = pc.uuid.getMostSignificantBits();
		PlayerDB.removePlayer(pc.uuid);
		server.sendToAllExceptTCP(pc.getID(), dge);
	}
}
