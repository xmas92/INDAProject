package game.Entity;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.UUID;

import com.esotericsoftware.kryonet.Server;

import game.Database.LoginDB;
import game.Database.PlayerDB;
import game.DrawState.DrawStates;
import game.Event.Event;
import game.Event.NetworkEvent;
import game.Event.PlayerConnectedEvent;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.CreatePlayer;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.Network.GameKryoReg.PlayerLoginRequest;
import game.Network.GameKryoReg.PlayerMovement;
import game.Server.GameServer;
import game.Server.PlayerConnection;
import game.UpdateState.UpdateStates;

public class ServerPlayer implements ServerEntity {
	
	private float x, y, speed;
	private int deltaX, deltaY, w, h;
	private UUID uuid;
	
	@Override
	public void Update(int delta) {
		if (Math.abs(deltaX)+Math.abs(deltaY) != 0) {
			float movement = (speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(deltaX)+Math.abs(deltaY));
			x += deltaX * movement;
			y += deltaY * movement;
		} 
	}

	@Override
	public void Initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Callback(Event e) {
		if (e instanceof NetworkEvent) { HandleNetworkEvent((NetworkEvent)e); }
		else if (e instanceof PlayerConnectedEvent) { HandlePlayerConnectedEvent((PlayerConnectedEvent)e); }
	}

	private void HandlePlayerConnectedEvent(PlayerConnectedEvent e) {
		CreateGenericEntity cge = new CreateGenericEntity();
		cge.h = h; cge.w = w; cge.speed = speed; cge.x = x; cge.y = y; 
		cge.drawID = DrawStates.PlayerDrawState.ordinal();
		cge.updateID = UpdateStates.PlayerUpdateState.ordinal();
		cge.UUIDp1 = uuid.getLeastSignificantBits(); 
		cge.UUIDp2 = uuid.getMostSignificantBits();
		cge.deltaX = deltaX; cge.deltaY = deltaY;
		cge.type = GEType.OtherPlayer.ordinal();
		e.pc.sendTCP(cge);
	}

	private void HandleNetworkEvent(NetworkEvent e) {
		PlayerConnection pc = (PlayerConnection)e.Connection;
		if (e.Package instanceof PlayerLoginRequest) {	HandlePlayerLoginRequst(pc, (PlayerLoginRequest)e.Package);	}
		else if (e.Package instanceof PlayerMovement) { HandlePlayerMovement(pc , (PlayerMovement)e.Package); }
	}
	
	private void HandlePlayerMovement(PlayerConnection pc, PlayerMovement pm) {
		GenericEntityMovement gem = new GenericEntityMovement();
		gem.x = x = pm.x; gem.y = y = pm.y; gem.deltaX = deltaX = pm.deltaX; gem.deltaY = deltaY = pm.deltaY; gem.speed = speed = pm.speed;
		gem.UUIDp1 = pc.uuid.getLeastSignificantBits(); gem.UUIDp2 = pc.uuid.getMostSignificantBits();
		((Server)pc.getEndPoint()).sendToAllExceptTCP(pc.getID(), gem);
	}

	private void HandlePlayerLoginRequst(PlayerConnection pc, PlayerLoginRequest object) {
		if (pc.username == null) {
			pc.username = object.Username;
			if (!LoginDB.LoggedIn(pc.username)) { pc.close(); return; }
			// TODO check for player in DB instead of reseting everything every time
			CreatePlayer cp = new CreatePlayer();
			cp.x = x = (float) GameServer.PlayerSpawnPoint.getX(); 
			cp.y = y = (float) GameServer.PlayerSpawnPoint.getY(); 
			cp.h = h = 32; cp.w = w = 32; cp.speed = speed = 128;
			// TODO fix so we only need to send a drawState
			cp.imageRef = "data/images/GameAssets/Player/player.bmp";
			pc.sendTCP(cp);
			CreateGenericEntity cge = new CreateGenericEntity();
			cge.h = cp.h; cge.w = cp.w; cge.x = cp.x; cge.y = cp.y; cge.speed = cp.speed;
			cge.drawID = DrawStates.PlayerDrawState.ordinal();
			cge.updateID = UpdateStates.PlayerUpdateState.ordinal();
			cge.UUIDp1 = pc.uuid.getLeastSignificantBits(); 
			cge.UUIDp2 = pc.uuid.getMostSignificantBits();
			((Server)pc.getEndPoint()).sendToAllExceptTCP(pc.getID(), cge);
			uuid = pc.uuid;
			PlayerDB.addPlayer(this, pc.uuid);
		}
	}

	@Override
	public Point2D position() {
		return new Point2D.Float(x,y);
	}

	@Override
	public Dimension2D dimension() {
		return new Dimension(w, h);
	}

	@Override
	public Rectangle collisionBox() {
		return new Rectangle(x - w * 0.5f, y - h * 0.5f, w, h);
	}

}
