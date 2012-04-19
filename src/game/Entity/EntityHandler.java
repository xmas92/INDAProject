package game.Entity;

import java.util.HashMap;
import java.util.UUID;

import game.Model;
import game.View;
import game.Controller.Controller;
import game.Event.Event;
import game.Event.EventCallback;
import game.Event.NetworkEvent;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.DestroyGenerticEntity;
import game.Network.GameKryoReg.GenericEntityMovement;

public class EntityHandler implements Model, View, Controller, EventCallback {

	private HashMap<UUID, GenericEntity> entities;
	
	@Override
	public void Callback(Event e) {
		if (e instanceof NetworkEvent) {
			NetworkEvent ne = (NetworkEvent)e;
			if (ne.Package instanceof CreateGenericEntity) {
				CreateGenericEntity cge = (CreateGenericEntity)ne.Package;
				entities.put(new UUID(cge.UUIDp1, cge.UUIDp2), new GenericEntity());
				entities.get(new UUID(cge.UUIDp1, cge.UUIDp2)).Callback(ne);
			}
			if (ne.Package instanceof GenericEntityMovement) {
				GenericEntityMovement cem = (GenericEntityMovement)ne.Package;
				GenericEntity ge = entities.get(new UUID(cem.UUIDp1, cem.UUIDp2));
				if (ge != null)
					ge.Callback(ne);
			}
			if (ne.Package instanceof DestroyGenerticEntity) {
				DestroyGenerticEntity dge = (DestroyGenerticEntity)ne.Package;
				entities.remove(new UUID(dge.UUIDp1, dge.UUIDp2));
			}
		}
	}

	@Override
	public void Update(int delta) {
		for (GenericEntity entity : entities.values()) {
			entity.Update(delta);
		}
	}

	@Override
	public void Draw() {
		for (GenericEntity entity : entities.values()) {
			entity.Draw();
		}
	}

	@Override
	public void Initialize() {
		entities =  new HashMap<UUID, GenericEntity>();
	}

}
