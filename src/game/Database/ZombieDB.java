package game.Database;

import game.Entity.ServerZombie;
import game.Event.Event;

import java.util.HashMap;
import java.util.UUID;

public class ZombieDB {
	private static HashMap<UUID, ServerZombie> zombies = new HashMap<UUID, ServerZombie>();

	public synchronized static ServerZombie getZombie(UUID uuid) {
		return zombies.get(uuid);
	}
	
	public synchronized static ServerZombie getZombie(long UUIDp1, long UUIDp2) {
		return zombies.get(new UUID(UUIDp1, UUIDp2));
	}

	public synchronized static void removeZombie(UUID uuid) {
		zombies.remove(uuid);
	}
	
	public synchronized static void removeZombie(long UUIDp1, long UUIDp2) {
		zombies.remove(new UUID(UUIDp1, UUIDp2));
	}
	
	public synchronized static void addZombie(ServerZombie zombie, UUID uuid) {
		zombies.put(uuid, zombie);
	}
	
	public synchronized static void sendToAll(Event e) {
		for (ServerZombie p : zombies.values()) {
			p.Callback(e);
		}
	}
	
	public synchronized static void updateAll(int delta) {
		for (ServerZombie p : zombies.values()) {
			p.Update(delta);
		}
	}
}
