package game.Entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import game.Model;
import game.View;
import game.Controller.Controller;
import game.Database.GenericEntityDB;
import game.Event.CreateClientGenericEntityEvent;
import game.Event.Event;
import game.Event.EventCallback;
import game.Event.NetworkEvent;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.DestroyGenerticEntity;
import game.Network.GameKryoReg.GenericEntityMovement;

public class EntityHandler implements Model, View, Controller, EventCallback {
	
	private HashMap<UUID, GenericEntity> clientEntities;
	private static boolean destroy = false;
	public static void Destroy() { destroy = true; }
	
	@Override
	public void Callback(Event e) {
		if (e instanceof NetworkEvent) {
			// TODO if sync problems occur change NetworkEvent to a polling algorithm instead of a threaded callback (should be fixed)
			NetworkEvent ne = (NetworkEvent)e;
			if (ne.Package instanceof CreateGenericEntity) {
				CreateGenericEntity cge = (CreateGenericEntity)ne.Package;
				GenericEntity ge = new GenericEntity();
				ge.Callback(ne);
				GenericEntityDB.addEntity(ge, new UUID(cge.UUIDp1, cge.UUIDp2));
			}
			if (ne.Package instanceof GenericEntityMovement) {
				GenericEntityMovement cem = (GenericEntityMovement)ne.Package;
				GenericEntity ge = GenericEntityDB.getEntity(cem.UUIDp1, cem.UUIDp2);
				if (ge != null)
					ge.Callback(ne);
			}
			if (ne.Package instanceof DestroyGenerticEntity) {
				DestroyGenerticEntity dge = (DestroyGenerticEntity)ne.Package;
				GenericEntityDB.removeEntity(dge.UUIDp1, dge.UUIDp2);
			}
		}
		if (e instanceof CreateClientGenericEntityEvent) {
			CreateClientGenericEntityEvent ccgee = (CreateClientGenericEntityEvent)e;
			GenericEntity ge = new GenericEntity();
			ge.Callback(e);
			clientEntities.put(ccgee.uuid, ge);
		}
	}

	@Override
	public void Update(int delta) {
		GenericEntityDB.updateAll(delta);
		Iterator<GenericEntity> it = clientEntities.values().iterator();
		while (it.hasNext()) {
			it.next().Update(delta);
			if (destroy) {
				destroy = false;
				it.remove();
			}
		}
	}

	@Override
	public void Draw() {
		GenericEntityDB.drawAll();
		for (GenericEntity entity : clientEntities.values()) {
			entity.Draw();
		}
	}

	@Override
	public void Initialize() {
		clientEntities = new HashMap<UUID, GenericEntity>();
	}

}
