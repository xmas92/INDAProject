package game.UpdateState.States;

import game.Entity.GenericEntity;
import game.Geometry.Rectangle;
import game.UpdateState.UpdateState;
import game.Zones.Zone;
import game.Zones.Zones;

public class PlayerUpdateState extends UpdateState{

	public PlayerUpdateState(GenericEntity entity) {
		super(entity);
	}

	@Override
	public void Update(int delta) {
		if (Math.abs(entity.deltaX)+Math.abs(entity.deltaY) != 0) {
			float movement = (entity.speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(entity.deltaX)+Math.abs(entity.deltaY));
			Rectangle rect = entity.collisionBox();
			rect.y += entity.deltaY * movement;
			Zone z = Zones.CurrentZone();
			if (z != null) {
				if (z.getZoneMap().getCollision(rect)) {
					entity.deltaY = 0;
				}
			}
			rect = entity.collisionBox();
			rect.x += entity.deltaX * movement;
			if (z != null) {
				if (z.getZoneMap().getCollision(rect)) {
					entity.deltaX = 0;
				}
			}
			entity.x += entity.deltaX * movement;
			entity.y += entity.deltaY * movement;
		}
	}

}
