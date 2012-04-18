package gameOld.client.Entity;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import gameOld.client.Game.GameInfo;
import gameOld.client.Game.MainGame;
import gameOld.client.Resource.ResourceManager;
import gameOld.util.Geom.Rectangle;
import gameOld.util.IO.Net.Network.EntityInfo;

import org.newdawn.slick.Image;

public class Character implements Entity{
	protected EntityInfo info;
	protected Image graphic = null;
	public Character (EntityInfo characterInfo) {
		info = new EntityInfo();
		setEntityInfo(characterInfo);
	}
	public EntityInfo getEntityInfo() {
		return info.clone();
	}
	public void setEntityInfo(EntityInfo info) {
		if (info.imageID != this.info.imageID)
				graphic = ResourceManager.Manager().getImage(info.imageID);
		this.info = info.clone();
	}
	
	public void draw(float x, float y) {
		if (graphic == null)
			return;
		float screenX = info.x - x + (GameInfo.Width - info.w) * 0.5f;
		float screenY = info.y - y + (GameInfo.Height - info.h) * 0.5f;
		if (screenX < -GameInfo.Width || screenY < -GameInfo.Height || screenX > GameInfo.Width * 2 || screenY > GameInfo.Height * 2)
			return;
		graphic.draw(screenX, screenY);
	}
	private boolean changed = false;
	public void update(int delta) {
		changed = (info.deltaX != 0 || info.deltaY != 0);
		if (changed) {
			float change = (info.speed * delta / 1000.0f) / (float)Math.sqrt(info.deltaX*info.deltaX + info.deltaY*info.deltaY);
			Rectangle rec = collisionBox();
			rec.y += info.deltaY*change;
			if (!MainGame.map.getCollision(rec)) {
				rec = collisionBox();
				info.y += info.deltaY * change;
			} else {
				rec = collisionBox();
				info.deltaY = 0;
			}
			rec.x += info.deltaX*change;
			if (!MainGame.map.getCollision(rec)) {
				info.x += info.deltaX * change;
			} else {
				info.deltaX = 0;
			}
		}
		changed = (info.deltaX != 0 || info.deltaY != 0);
	}
	public boolean hasChanged() {
		return true;
	}
	@Override
	public Point2D position() {
		return new Point2D.Float(info.x - (float)dimension().getWidth() * 0.5f, info.y - (float)dimension().getHeight() * 0.5f);
	}
	@Override
	public Dimension2D dimension() {
		return new Dimension((int)info.w, (int)info.h);
	}
	@Override
	public Rectangle collisionBox() {
		return new Rectangle((float)position().getX(), (float)position().getY(), (float)dimension().getWidth(), (float)dimension().getHeight());
	}
}
