package game.Entity;

import game.Database.PlayerDB;
import game.DrawState.DrawStates;
import game.Event.Event;
import game.Event.PlayerConnectedEvent;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.UpdateState.ServerUpdateState;
import game.UpdateState.UpdateStates;
import game.UpdateState.ServerStates.PlayerServerUpdateState;
import game.UpdateState.ServerStates.ZombieServerUpdateState;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.UUID;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class ServerZombie implements ServerEntity, ServerChacer {
	public Server server;
	public UUID uuid;
	public ZombieServerUpdateState sus = new ZombieServerUpdateState(this);
	public float hp = 100;
	public Point2D spawnPos = new Point2D.Float();
	public ServerZombie(Server s) {
		server = s;
	}
	public float x, y, speed, deltaX, deltaY;
	public int w, h;
	
	
	@Override
	public void Update(int delta) {
		PlayerDB.chaceAll(this);
		sus.Update(delta);
	}

	@Override
	public void Initialize() {
		x = (float) spawnPos.getX();; y = (float) spawnPos.getY();; 
		speed = 64; w = 64; h = 64;
		uuid = UUID.randomUUID();
	}

	@Override
	public void Callback(Event e) {
		if (e instanceof PlayerConnectedEvent) { HandlePlayerConnectedEvent((PlayerConnectedEvent)e); }
	}
	
	private void HandlePlayerConnectedEvent(PlayerConnectedEvent e) {
		if (!sus.dead) {
			CreateGenericEntity cge = new CreateGenericEntity();
			cge.h = h; cge.w = w; cge.speed = speed; cge.x = x; cge.y = y; 
			cge.drawID = DrawStates.ZombieDrawState.ordinal();
			cge.updateID = UpdateStates.PlayerUpdateState.ordinal();
			cge.UUIDp1 = uuid.getLeastSignificantBits(); 
			cge.UUIDp2 = uuid.getMostSignificantBits();
			cge.deltaX = deltaX; cge.deltaY = deltaY;
			cge.type = GEType.OtherPlayer.ordinal();
			e.pc.sendTCP(cge);
		}
	}

	@Override
	public void Chace(Collection<ServerPlayer> entities) {
		Point2D chace = null;
		double last = 320;
		for (ServerPlayer sp : entities) {
			if (sp.position().distance(position()) < last) {
				last = sp.position().distance(position());
				chace = sp.position();
			}
		}
		chace = chace!=null?(chace.distance(spawnPos) > 400?null:chace):chace;
		if (chace != null) {
			deltaX = (float)chace.getX() - x;
			deltaY = (float)chace.getY() - y;
			float lengt = (float)Math.sqrt(deltaX*deltaX+deltaY*deltaY);
			deltaX /= lengt;
			deltaY /= lengt;
		} else {
			if (spawnPos.getX() - x > 2 || spawnPos.getY() - y > 2) {
				deltaX = (float)spawnPos.getX() - x;
				deltaY = (float)spawnPos.getY() - y;
				float lengt = (float)Math.sqrt(deltaX*deltaX+deltaY*deltaY);
				deltaX /= lengt;
				deltaY /= lengt;
			} else {
				deltaX = 0;
				deltaY = 0;
			}
		}
	}

	@Override
	public Point2D position() {
		return new Point2D.Float(x,y);
	}

	@Override
	public Dimension2D dimension() {
		return new Dimension(w, h);
	}

	@Override
	public Rectangle collisionBox() {
		return new Rectangle(x - w * 0.5f, y - h * 0.5f, w, h);
	}

	public void takeDamage(ServerSpell spell) {
		if (!sus.dead)
			hp -= spell.damage;
	}

}
