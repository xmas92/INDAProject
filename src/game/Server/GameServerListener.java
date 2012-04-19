package game.Server;

import java.util.HashMap;
import java.util.UUID;

import game.Database.LoginDB;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.CreatePlayer;
import game.Network.GameKryoReg.DestroyGenerticEntity;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.Network.GameKryoReg.PlayerLoginRequest;
import game.Network.GameKryoReg.PlayerMovement;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServerListener extends Listener {
	
	private Server server;
	
	private HashMap<UUID, CreateGenericEntity> players = new HashMap<UUID, CreateGenericEntity>();
	
	public GameServerListener(Server server) {
		this.server = server;
	}

	@Override
	public void received (Connection c, Object object) {
		PlayerConnection pc = (PlayerConnection)c;
		if (object instanceof PlayerLoginRequest) {
			if (pc.username == null) {
				pc.username = ((PlayerLoginRequest)object).Username;
				if (!LoginDB.LoggedIn(pc.username)) pc.close();
				CreatePlayer cp = new CreatePlayer();
				cp.x = 64; cp.y = 64; cp.h = 32; cp.w = 32; cp.speed = 64;
				cp.imageRef = "data/images/GameAssets/Player/player.bmp";
				pc.sendTCP(cp);
				CreateGenericEntity cge = new CreateGenericEntity();
				cge.h = cp.h; cge.w = cp.w; cge.x = cp.x; cge.y = cp.y; cge.speed = cp.speed;
				cge.imageRef = cp.imageRef;
				cge.UUIDp1 = pc.uuid.getLeastSignificantBits(); cge.UUIDp2 = pc.uuid.getMostSignificantBits();
				players.put(pc.uuid, cge);
				server.sendToAllExceptTCP(pc.getID(), cge);
			}
		}
		if (object instanceof PlayerMovement) {
			PlayerMovement pm = (PlayerMovement)object;
			GenericEntityMovement gem = new GenericEntityMovement();
			gem.x = pm.x; gem.y = pm.y; gem.deltaX = pm.deltaX; gem.deltaY = pm.deltaY; gem.speed = pm.speed;
			gem.UUIDp1 = pc.uuid.getLeastSignificantBits(); gem.UUIDp2 = pc.uuid.getMostSignificantBits();
			server.sendToAllExceptTCP(pc.getID(), gem);
		}
	}
	
	@Override
	public void connected(Connection c)  {
		((PlayerConnection)c).uuid = UUID.randomUUID();
		for (CreateGenericEntity cge : players.values()) {
			c.sendTCP(cge);
		}
	}
	
	@Override
	public void disconnected(Connection c)  {
		PlayerConnection pc = (PlayerConnection)c;
		if (pc.username != null) LoginDB.Logout(pc.username);
		DestroyGenerticEntity dge = new DestroyGenerticEntity();
		dge.UUIDp1 = pc.uuid.getLeastSignificantBits(); dge.UUIDp2 = pc.uuid.getMostSignificantBits();
		players.remove(pc.uuid);
		server.sendToAllExceptTCP(pc.getID(), dge);
	}
}
