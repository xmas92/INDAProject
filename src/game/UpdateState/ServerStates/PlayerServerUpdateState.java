package game.UpdateState.ServerStates;

import game.Entity.ServerEntity;
import game.Entity.ServerZombie;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.UpdateState.ServerUpdateState;
import game.Zones.HubZone;
import game.Zones.Zone;

public class PlayerServerUpdateState extends ServerUpdateState {

	public PlayerServerUpdateState(ServerEntity entity) {
		super(entity);
	}
	
	// TODO fix serverside zones 
	private static Zone z = new HubZone();
	private long lastTime = System.currentTimeMillis();
	private float oldDX, oldDY;
	@Override
	public void Update(int delta) {
		if (entity instanceof ServerZombie) {
			ServerZombie entity = (ServerZombie)this.entity;
			if (Math.abs(entity.deltaX)+Math.abs(entity.deltaY) != 0) {
				float movement = (entity.speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(entity.deltaX)+Math.abs(entity.deltaY));
				Rectangle rect = entity.collisionBox();
				rect.y += entity.deltaY * movement;
				if (z.getZoneMap() == null) z.Initialize();
				if (z.getZoneMap().getCollision(rect)) {
					entity.deltaY = 0;
				}
				rect = entity.collisionBox();
				rect.x += entity.deltaX * movement;
				if (z.getZoneMap().getCollision(rect)) {
					entity.deltaX = 0;
				}
				if (Math.abs(entity.deltaX)+Math.abs(entity.deltaY) != 0) {
					movement = (entity.speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(entity.deltaX)+Math.abs(entity.deltaY));
				} else {
					movement = 0;
				}
				entity.x += entity.deltaX * movement;
				entity.y += entity.deltaY * movement;
			}
			if (oldDX != entity.deltaX || oldDY != entity.deltaY) {
				if (System.currentTimeMillis() - lastTime < 200) return;
				lastTime = System.currentTimeMillis();
				GenericEntityMovement gem = new GenericEntityMovement();
				oldDX = gem.deltaX = entity.deltaX; 
				oldDY = gem.deltaY = entity.deltaY;
				gem.speed = entity.speed;
				gem.x = entity.x; gem.y = entity.y;
				gem.UUIDp1 = entity.uuid.getLeastSignificantBits();
				gem.UUIDp2 = entity.uuid.getMostSignificantBits();
				entity.server.sendToAllTCP(gem);
			}
		}
	}
}
