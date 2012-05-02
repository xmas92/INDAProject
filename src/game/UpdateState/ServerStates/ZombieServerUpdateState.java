package game.UpdateState.ServerStates;

import game.DrawState.DrawStates;
import game.Entity.GEType;
import game.Entity.ServerZombie;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.DestroyGenerticEntity;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.UpdateState.ServerUpdateState;
import game.UpdateState.UpdateStates;
import game.Zones.HubZone;
import game.Zones.Zone;

public class ZombieServerUpdateState extends ServerUpdateState {

	public ZombieServerUpdateState(ServerZombie entity) {
		super(entity);
	}
	
	// TODO fix serverside zones 
	private static Zone z = new HubZone();
	private long lastTime = System.currentTimeMillis();
	private float oldDX, oldDY;
	public boolean dead = false;
	private int deadTime = 0;
	@Override
	public void Update(int delta) {
		ServerZombie entity = (ServerZombie)this.entity;
		if (dead) {
			if (deadTime > 30000) Respawn();
			else deadTime  += delta;
		}
		if (entity.hp <= 0 && !dead) {
			Die();
			return;
		}
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
	private void Respawn() {
		ServerZombie entity = (ServerZombie)this.entity;
		CreateGenericEntity cge = new CreateGenericEntity();
		cge.h = entity.h; cge.w = entity.w; cge.speed = entity.speed; 
		cge.x = entity.x; cge.y = entity.y; 
		cge.drawID = DrawStates.ZombieDrawState.ordinal();
		cge.updateID = UpdateStates.PlayerUpdateState.ordinal();
		cge.UUIDp1 = entity.uuid.getLeastSignificantBits(); 
		cge.UUIDp2 = entity.uuid.getMostSignificantBits();
		cge.deltaX = entity.deltaX; cge.deltaY = entity.deltaY;
		cge.type = GEType.OtherPlayer.ordinal();
		entity.server.sendToAllTCP(cge);
		dead = false;
		deadTime = 0;
	}
	private void Die() {
		ServerZombie entity = (ServerZombie)this.entity;
		DestroyGenerticEntity dge = new DestroyGenerticEntity();
		dge.UUIDp1 = entity.uuid.getLeastSignificantBits();
		dge.UUIDp2 = entity.uuid.getMostSignificantBits();
		entity.server.sendToAllTCP(dge);
		dead = true;
		entity.hp = 100;
		entity.deltaX = 0;
		entity.deltaY = 0;
		entity.x = (float) entity.spawnPos.getX();
		entity.y = (float) entity.spawnPos.getY();
	}
}
