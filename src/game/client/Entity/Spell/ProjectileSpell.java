package game.client.Entity.Spell;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import org.newdawn.slick.Image;

import game.client.Entity.Spell.PSType;
import game.client.Game.GameInfo;
import game.client.Game.MainGame;
import game.client.Resource.ResourceManager;
import game.util.Geom.Rectangle;
import game.util.IO.InputState;
import game.util.IO.Net.Network.EntityInfo;

public class ProjectileSpell implements Spell {
	
	private int cooldown, timeSinceLastCast;
	private final boolean cast;
	private boolean dead = false, dying = false;
	private EntityInfo info;
	private Image graphic;
	public final PSType type;
	
	public ProjectileSpell(PSType t) {
		this(t, false);
	}
	public ProjectileSpell(PSType t, boolean cast) {
		this.cast = cast;
		info = new EntityInfo();
		type = t;
		graphic = ResourceManager.Manager().getImage(info.imageID);
		if (graphic != null) {
			info.w = graphic.getWidth();
			info.h = graphic.getHeight();
		}
	}
	
	public void setProjectileSpellInfo(EntityInfo psi) {
		if (info.imageID == null)
			graphic = ResourceManager.Manager().getImage(psi.imageID);
		else if (!info.imageID.equals(psi.imageID))
			graphic = ResourceManager.Manager().getImage(psi.imageID);
		info = psi.clone();
	}
	
	public EntityInfo getEntityInfo() {
		return info.clone();
	}
	
	@Override
	public boolean isReady() {
		return (timeSinceLastCast > cooldown);
	}

	@Override
	public Spell castSpell() {
		ProjectileSpell ps = new ProjectileSpell(type, true);
		EntityInfo psi = info.clone();
		psi.x = MainGame.player.getPlayerX();
		psi.y = MainGame.player.getPlayerY();
		try {
			psi.deltaX = InputState.Get().MouseState.getPosition().x - GameInfo.Width / 2;
			psi.deltaY = InputState.Get().MouseState.getPosition().y - GameInfo.Height / 2;
			float length = (float)Math.sqrt(psi.deltaX*psi.deltaX + psi.deltaY*psi.deltaY);
			psi.deltaX /= length;
			psi.deltaY /= length;
		} catch (Exception e) {
			e.printStackTrace();
			psi.deltaX = 1;
			psi.deltaY = 0;
		}
		ps.setProjectileSpellInfo(psi);
		timeSinceLastCast = 0;
		return ps;
	}

	@Override
	public void update(int delta) {
		timeSinceLastCast += delta;
		if (cast) {
			info.x += info.deltaX * info.speed * delta / 1000.f;
			info.y += info.deltaY * info.speed * delta / 1000.f;
			if (MainGame.map.getCollision(collisionBox()))
				die();
			if (dying)
				dead = true;
		}
	}

	@Override
	public void draw(float x, float y) {
		if (cast) {
			if (!dead) {
				float absX = info.x-x+(GameInfo.Width-info.w)*0.5f, absY = info.y-y+(GameInfo.Height-info.h)*0.5f;
				if (absX > -GameInfo.Width && absX < 2*GameInfo.Width &&
					absY > -GameInfo.Height && absY < 2*GameInfo.Height) {
					graphic.draw(absX,absY,info.w,info.h);
				}
			}
		}
	}
	public void setSpeed(float f) {
		info.speed = f;
	}
	@Override
	public boolean isDead() {
		return dead;
	}
	@Override
	public void die() {
		dying = true;
	}
	@Override
	public Point2D position() {
		return new Point2D.Float(info.x - (float)dimension().getWidth() * 0.5f, info.y - (float)dimension().getHeight() * 0.5f);
	}
	@Override
	public Dimension2D dimension() {
		if (graphic != null)
			return new Dimension(graphic.getWidth(), graphic.getHeight());
		return new Dimension();
	}
	@Override
	public Rectangle collisionBox() {
		return new Rectangle((float)position().getX(), (float)position().getY(), (float)dimension().getWidth(), (float)dimension().getHeight());
	}
}
