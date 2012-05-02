package game.UpdateState.ServerStates;

import java.util.UUID;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import game.Database.PlayerDB;
import game.Database.SpellDB;
import game.Database.ZombieDB;
import game.Entity.GEType;
import game.Entity.ServerEntity;
import game.Entity.ServerSpell;
import game.Network.GameKryoReg.DestroyGenerticEntity;
import game.UpdateState.CollisionIgnore;
import game.UpdateState.ServerUpdateState;
import game.Zones.HubZone;
import game.Zones.Zone;
import game.Zones.ZoneMap;
import game.Zones.Zones;

public class ProjectileServerUpdateState extends ServerUpdateState implements CollisionIgnore {
	private UUID[] uuids;
	private GEType[] ignoreTypes;

	private boolean dying = false, dead = false;
	private long dyingTime = 0;
	private boolean PlayerCollision = true;
	public ProjectileServerUpdateState(ServerEntity entity) {
		super(entity);
	}

	// TODO fix serverside zones 
	private static Zone z = new HubZone();
	@Override
	public void Update(int delta) {
		if (!(entity instanceof ServerSpell)) return;
		ServerSpell entity = (ServerSpell)this.entity;
		if (dying && !dead) {
			if (dyingTime > 1500) {
				dead = true;
				DestroyGenerticEntity dge = new DestroyGenerticEntity();
				dge.UUIDp1 = entity.serverUUID.getLeastSignificantBits();
				dge.UUIDp2 = entity.serverUUID.getMostSignificantBits();
				((Server)entity.pc.getEndPoint()).sendToAllExceptTCP(entity.pc.getID(), dge);
				dge.UUIDp1 = entity.clientUUID.getLeastSignificantBits();
				dge.UUIDp2 = entity.clientUUID.getMostSignificantBits();
				entity.pc.sendTCP(dge);
				SpellDB.Destroy();
			} else {
				dyingTime += delta;
			}
		}
		if (!dying && !dead) {
			entity.x += entity.deltaX * entity.speed * delta / 1000.f;
			entity.y += entity.deltaY * entity.speed * delta / 1000.f;
			
			
			if (z.getZoneMap() == null) z.Initialize();
			if (z.getZoneMap().getCollision(entity.collisionBox())) {
				Die();
			}
			if (PlayerDB.anyCollision(entity, uuids)) {
				Die();
			}
			if (ZombieDB.hitZombie(entity)) {
				Die();
			}
		}
	}

	@Override
	public void AddIgnore(long[] uuids) {
		this.uuids = new UUID[uuids.length / 2];
		for (int i = 0; i < uuids.length; i+=2) {
			this.uuids[i/2] = new UUID(uuids[i], uuids[i+1]);
		}
	}
	

	private void Die() {
		if (dying || dead) return;
		dying = true;
	}

	@Override
	public void AddIgnoreType(int[] types) {
		ignoreTypes = new GEType[types.length];
		for (int i = 0; i < ignoreTypes.length; i++) {
			ignoreTypes[i] = GEType.getType(types[i]);
			if (ignoreTypes[i] == GEType.Player)
				PlayerCollision = false;
		}
	}

}
