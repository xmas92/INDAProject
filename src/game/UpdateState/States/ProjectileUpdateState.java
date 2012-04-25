package game.UpdateState.States;

import game.Database.GenericEntityDB;
import game.DrawState.DrawStates;
import game.Entity.EntityHandler;
import game.Entity.GenericEntity;
import game.UpdateState.UpdateState;
import game.Zones.Zone;
import game.Zones.Zones;

public class ProjectileUpdateState extends UpdateState {

	private boolean dying = false, dead = false;
	private long dyingTime = 0;
	public ProjectileUpdateState(GenericEntity entity) {
		super(entity);
	}

	@Override
	public void Update(int delta) {
		if (dying) {
			if (dyingTime > 1000) {
				dead = true;
				entity.drawState = DrawStates.getNewState(DrawStates.NullDrawState.ordinal(), entity);
				EntityHandler.Destroy();
			} else {
				dyingTime += delta;
			}
		}
		if (!dying && !dead) {
			Zone z = Zones.CurrentZone();
			if (z.getZoneMap().getCollision(entity.collisionBox()) || GenericEntityDB.anyCollision(entity)) {
				Die();
			}
			entity.x += entity.deltaX * entity.speed * delta / 1000.f;
			entity.y += entity.deltaY * entity.speed * delta / 1000.f;
		}
	}

	private void Die() {
		if (dying || dead) return;
		dying = true;
		entity.drawState = DrawStates.getNewState(DrawStates.ProjectileDyingDrawState.ordinal(), entity);
	}

}
