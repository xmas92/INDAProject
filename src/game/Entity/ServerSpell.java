package game.Entity;

import java.util.UUID;

import com.esotericsoftware.kryonet.Server;

import game.Database.SpellDB;
import game.Event.Event;
import game.Event.NetworkEvent;
import game.Network.GameKryoReg.CastProjectileSpell;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Server.PlayerConnection;

public class ServerSpell implements ServerEntity {

	@Override
	public void Update(int delta) {
		// TODO
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
		CreateGenericEntity cge = new CreateGenericEntity();
		cge.x = cps.x; cge.y = cps.y; cge.w = cps.w; cge.h = cps.h; cge.deltaX = cps.deltaX; cge.deltaY = cps.deltaY;
		cge.updateID = cps.updateID; cge.drawID = cps.drawID;
		cge.UUIDp1 = cps.UUIDp1; cge.UUIDp2 = cps.UUIDp2;
		((Server)pc.getEndPoint()).sendToAllExceptTCP(pc.getID(), cge);
		SpellDB.addSpell(this, UUID.randomUUID());
	}

}
