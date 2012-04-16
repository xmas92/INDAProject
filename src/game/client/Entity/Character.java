package game.client.Entity;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import game.client.Game.GameInfo;
import game.client.Game.MainGame;
import game.client.Resource.ResourceManager;
import game.util.Geom.Rectangle;
import game.util.IO.Net.Network.EntityInfo;

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
		float screenX = info.x - x + GameInfo.Width * 0.5f;
		float screenY = info.y - y + GameInfo.Height * 0.5f;
		if (screenX < -1000 || screenY < -1000 || screenX > 2000 || screenY > 2000)
			return;
		graphic.draw(screenX, screenY);
	}
	private boolean changed = false;
	public void update(int delta) {
		changed = (info.deltaX != 0 || info.deltaY != 0);
		if (changed) {
			float change = (info.speed * delta / 1000.0f) / (float)Math.sqrt(info.deltaX*info.deltaX + info.deltaY*info.deltaY);
			if (!MainGame.map.getCollision(new Rectangle(info.x, info.y+info.deltaY*change, 32,32))) 
				info.y += info.deltaY * change;
			else
				info.deltaY = 0;
			if (!MainGame.map.getCollision(new Rectangle(info.x+info.deltaX*change, info.y, 32,32))) 
					info.x += info.deltaX * change;
			else
				info.deltaX = 0;
		}
		changed = (info.deltaX != 0 || info.deltaY != 0);
	}
	public boolean hasChanged() {
		return true;
	}
	@Override
	public Point2D position() {
		return new Point2D.Float(info.x, info.y);
	}
	@Override
	public Dimension2D dimension() {
		if (graphic != null)
			return new Dimension(graphic.getWidth(), graphic.getHeight());
		return new Dimension();
	}
	@Override
	public Rectangle collisionBox() {
		return new Rectangle(info.x, info.y, (float)dimension().getWidth(), (float)dimension().getHeight());
	}
}
