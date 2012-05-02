package game.Entity;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.UUID;

import com.esotericsoftware.kryonet.Server;

import game.Database.SpellDB;
import game.Event.Event;
import game.Event.NetworkEvent;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.CGEWithCollisionIgnore;
import game.Network.GameKryoReg.CastProjectileSpell;
import game.Server.PlayerConnection;
import game.UpdateState.CollisionIgnore;
import game.UpdateState.ServerUpdateState;
import game.UpdateState.UpdateStates;

public class ServerSpell implements ServerEntity {
	
	public float x, y, speed, deltaX, deltaY;
	public int w, h;
	
	public PlayerConnection pc;
	public UUID clientUUID, serverUUID;
	
	public float damage = 50;

	public ServerUpdateState sus;

	@Override
	public void Update(int delta) {
		// TODO fix server spell to work like client spell;
		if (sus != null)
			sus.Update(delta);
	}

	@Override
	public void Initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Callback(Event e) {
		if (e instanceof NetworkEvent) { HandleNetworkEvent((NetworkEvent)e); }
	}

	private void HandleNetworkEvent(NetworkEvent e) {
		PlayerConnection pc = (PlayerConnection)e.Connection;
		if (e.Package instanceof CastProjectileSpell) {	HandleCastProjectileSpell(pc, (CastProjectileSpell)e.Package);	}
	}

	private void HandleCastProjectileSpell(PlayerConnection pc, CastProjectileSpell cps) {
		// TODO fix this, need to setup server copy, handle UpdateState, generate a server UUID and save local.
		CGEWithCollisionIgnore cge = new CGEWithCollisionIgnore();
		x = cge.x = cps.x; y = cge.y = cps.y; w = cge.w = cps.w; h = cge.h = cps.h; 
		deltaX = cge.deltaX = cps.deltaX; deltaY = cge.deltaY = cps.deltaY;
		speed = cge.speed = cps.speed;
		sus = UpdateStates.getNewServerState(cps.updateID, this);
		cge.updateID = cps.updateID; cge.drawID = cps.drawID;
		clientUUID = new UUID(cps.UUIDp1, cps.UUIDp2);
		serverUUID = UUID.randomUUID();
		cge.UUIDp1 = serverUUID.getLeastSignificantBits(); 
		cge.UUIDp2 = serverUUID.getMostSignificantBits();
		cge.type = GEType.ProjectileSpell.ordinal();
		cge.UUID = new long[2];
		cge.UUID[0] = pc.uuid.getLeastSignificantBits();
		cge.UUID[1] = pc.uuid.getMostSignificantBits();
		cge.Types = new int[1];
		cge.Types[0] = GEType.ProjectileSpell.ordinal();
		this.pc = pc;
		if (sus instanceof CollisionIgnore) {
			((CollisionIgnore)sus).AddIgnore(cge.UUID);
		}
		((Server)pc.getEndPoint()).sendToAllExceptTCP(pc.getID(), cge);
		SpellDB.addSpell(this, serverUUID);
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
