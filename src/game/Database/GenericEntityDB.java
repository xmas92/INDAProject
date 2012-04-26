package game.Database;

import game.Entity.GEType;
import game.Entity.GenericEntity;
import game.Event.Event;
import game.Geometry.Rectangle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class GenericEntityDB implements Database {
private static HashMap<UUID, GenericEntity> entities = new HashMap<UUID, GenericEntity>();
	
	public synchronized static GenericEntity getEntity(UUID uuid) {
		return entities.get(uuid);
	}
	
	public synchronized static GenericEntity getEntity(long UUIDp1, long UUIDp2) {
		return entities.get(new UUID(UUIDp1, UUIDp2));
	}

	public synchronized static boolean removeEntity(UUID uuid) {
		return (entities.remove(uuid) != null);
	}
	
	public synchronized static boolean removeEntity(long UUIDp1, long UUIDp2) {
		return (entities.remove(new UUID(UUIDp1, UUIDp2)) != null);
	}
	
	public synchronized static void addEntity(GenericEntity entity, UUID uuid) {
		entities.put(uuid, entity);
	}
	
	public synchronized static void sendToAll(Event e) {
		for (GenericEntity ge : entities.values()) {
			ge.Callback(e);
		}
	}

	public synchronized static void updateAll(int delta) {
		for (GenericEntity ge : entities.values()) {
			ge.Update(delta);
		}
	}
	
	public synchronized static void drawAll() {
		for (GenericEntity ge : entities.values()) {
			ge.Draw();
		}
	}
	
	public synchronized static boolean anyCollision(Rectangle rect) {
		for (GenericEntity ge : entities.values()) {
			if (ge.collisionBox().intersects(rect)) return true;
		}
		return false;
	}

	public synchronized static boolean anyCollision(GenericEntity entity) {
		for (GenericEntity ge : entities.values()) {
			if (ge != entity)
				if (ge.collisionBox().intersects(entity.collisionBox())) 
					return true;
		}
		return false;
	}

	public synchronized static boolean anyCollision(GenericEntity entity, UUID[] uuids,
			GEType[] ignoreTypes) {
		Iterator<UUID> it = entities.keySet().iterator();
		while (it.hasNext()) {
			UUID t = it.next();
			boolean cont = false;
			if (uuids != null) {
				for (UUID uuid : uuids) {
					if (uuid.equals(t)) {
						cont = true;
						break;
					}
				}
			}
			if (cont) continue;
			cont = false;
			GenericEntity ge = entities.get(t);
			if (ignoreTypes != null) {
				for (GEType geType : ignoreTypes) {
					if (geType.equals(ge.type)) {
						cont = true;
						break;
					}
				}
			}
			if (cont) continue;
			if (ge != entity) {
				if (ge.collisionBox().intersects(entity.collisionBox())) {
					return true;
				}
			}
		}
		
		
		return false;
	}
}
