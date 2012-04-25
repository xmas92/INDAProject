package game.Database;

import game.Entity.ServerPlayer;
import game.Event.Event;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDB implements Database {
	private static HashMap<UUID, ServerPlayer> players = new HashMap<UUID, ServerPlayer>();
	
	public synchronized static ServerPlayer getPlayer(UUID uuid) {
		return players.get(uuid);
	}
	
	public synchronized static ServerPlayer getPlayer(long UUIDp1, long UUIDp2) {
		return players.get(new UUID(UUIDp1, UUIDp2));
	}

	public synchronized static void removePlayer(UUID uuid) {
		players.remove(uuid);
	}
	
	public synchronized static void removePlayer(long UUIDp1, long UUIDp2) {
		players.remove(new UUID(UUIDp1, UUIDp2));
	}
	
	public synchronized static void addPlayer(ServerPlayer player, UUID uuid) {
		players.put(uuid, player);
	}
	
	public synchronized static void sendToAll(Event e) {
		for (ServerPlayer p : players.values()) {
			p.Callback(e);
		}
	}
	
	public synchronized static void updateAll(int delta) {
		for (ServerPlayer p : players.values()) {
			p.Update(delta);
		}
	}
}
