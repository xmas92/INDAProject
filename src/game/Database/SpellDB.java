package game.Database;

import game.Entity.ServerSpell;
import game.Event.Event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class SpellDB implements Database {
	private static HashMap<UUID, ServerSpell> spells = new HashMap<UUID, ServerSpell>();
	private static boolean destroy = false;
	public static void Destroy() { destroy = true; }
	
	public synchronized static ServerSpell getSpell(UUID uuid) {
		return spells.get(uuid);
	}
	
	public synchronized static ServerSpell getSpell(long UUIDp1, long UUIDp2) {
		return spells.get(new UUID(UUIDp1, UUIDp2));
	}

	public synchronized static void removeSpell(UUID uuid) {
		spells.remove(uuid);
	}
	
	public synchronized static void removeSpell(long UUIDp1, long UUIDp2) {
		spells.remove(new UUID(UUIDp1, UUIDp2));
	}
	
	public synchronized static void addSpell(ServerSpell spell, UUID uuid) {
		spells.put(uuid, spell);
	}
	
	public synchronized static void sendToAll(Event e) {
		for (ServerSpell ss: spells.values()) {
			ss.Callback(e);
		}
	}
	
	public synchronized static void updateAll(int delta) {
		Iterator<ServerSpell> it = spells.values().iterator();
		destroy = false;
		while (it.hasNext()) {
			it.next().Update(delta);
			if (destroy) {
				destroy = false;
				it.remove();
			}
		}
	}
}
