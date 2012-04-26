package game.UpdateState.States;

import java.util.UUID;

import game.Database.GenericEntityDB;
import game.DrawState.DrawStates;
import game.Entity.EntityHandler;
import game.Entity.GEType;
import game.Entity.GenericEntity;
import game.Screen.GameScreen;
import game.UpdateState.CollisionIgnore;
import game.UpdateState.UpdateState;
import game.Zones.Zone;
import game.Zones.Zones;

public class ProjectileUpdateState extends UpdateState implements CollisionIgnore {

	private UUID[] uuids;
	private GEType[] ignoreTypes;

	private boolean dying = false, dead = false;
	private long dyingTime = 0;
	private boolean PlayerCollision = true;
	public ProjectileUpdateState(GenericEntity entity) {
		super(entity);
	}

	@Override
	public void Update(int delta) {
		if (dying && !dead) {
			if (dyingTime > 1000) {
				dead = true;
				//entity.drawState = DrawStates.getNewState(DrawStates.NullDrawState.ordinal(), entity);
				EntityHandler.Destroy();
			} else {
				dyingTime += delta;
			}
		}
		if (!dying && !dead) {
			Zone z = Zones.CurrentZone();
			if (z.getZoneMap().getCollision(entity.collisionBox())) {
				Die();
			}
			if (GenericEntityDB.anyCollision(entity, uuids, ignoreTypes)) {
				Die();
			}
			if (PlayerCollision) {
				if (GameScreen.player.collisionBox().intersects(entity.collisionBox())) {
					Die();
				}
			}
			
			entity.x += entity.deltaX * entity.speed * delta / 1000.f;
			entity.y += entity.deltaY * entity.speed * delta / 1000.f;
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
		entity.drawState = DrawStates.getNewState(DrawStates.ProjectileDyingDrawState.ordinal(), entity);
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
