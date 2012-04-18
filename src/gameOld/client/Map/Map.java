package gameOld.client.Map;

import gameOld.util.Geom.Rectangle;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.esotericsoftware.minlog.Log;

public class Map extends TiledMap{

	private int collisionLayer = 3, bgLayer = 0, fgLayer = 2, bgObjectLayer = 1, eventLayer = 4;

	public void setCollisionLayer(int LayerID) { collisionLayer = LayerID; }
	public void setBGLayer(int LayerID) { bgLayer = LayerID; }
	public void setFGLayer(int LayerID) { fgLayer = LayerID; }
	public void setBGObjectLayer(int LayerID) { bgObjectLayer = LayerID; }
	public void setEventLayer(int LayerID) { eventLayer = LayerID; }
	
	public Map(String ref) throws SlickException {
		super(ref);
		// TODO Auto-generated constructor stub
	}
	public Map(String ref, boolean b) throws SlickException {
		super(ref, b);
		// TODO Auto-generated constructor stub
	}
	
	public boolean getCollision(Point2D p) {
		if (p.getX() < 0 || p.getY() < 0 || p.getX() > getWidth() * getTileWidth() || p.getY() > getHeight() * getTileHeight())
			return true;
		if (collisionLayer < 0 || collisionLayer >= getLayerCount()) {
			Log.error("Invalid BG layerIDs");
			return false;
		}
		int tileX = (int) p.getX() / getTileWidth(),
			tileY = (int) p.getY() / getTileHeight();
		return getTileId(tileX, tileY, collisionLayer) != 0;
	}
	
	
	public boolean getCollision(Line2D line) {
		return getCollision(line.getP1()) || getCollision(line.getP2());
	}
	
	public boolean getCollision(Rectangle rect) {
		return getCollision(rect.getPoints());
	}

	public boolean getCollision(Point2D[] points) {
		boolean ret = false;
		for (Point2D p : points) {
			ret |= getCollision(p);
		}
		return ret;
	}
	public void drawBG(float x, float y, int w, int h) {
		if (bgLayer < 0 || bgLayer >= getLayerCount() || bgObjectLayer < 0 || bgObjectLayer >= getLayerCount()) {
			Log.error("Invalid BG layerIDs");
			return;
		}
		int cTileX = (int) x / getTileWidth();
		int cTileY = (int) y / getTileHeight();
		int numTileX = w / getTileWidth() + 2;
		int numTileY = h / getTileHeight() + 2;
		x -= cTileX * getTileWidth();
		y -= cTileY * getTileHeight();
		for (int iY = 0; iY < numTileY; iY++) {
			if (iY + cTileY >= 0 && iY + cTileY < getHeight()) {
				for (int iX = 0; iX < numTileX; iX++) {
					if (iX + cTileX >= 0 && iX + cTileX  < getWidth()) {
						Image i = getTileImage(iX + cTileX, iY + cTileY, bgLayer);
						if (i != null)
							i.draw(iX*getTileWidth()-x, iY*getTileHeight()-y);
						i = getTileImage(iX + cTileX, iY + cTileY, bgObjectLayer);
							if (i != null)
								i.draw(iX*getTileWidth()-x, iY*getTileHeight()-y);
					}
				}
				
			}
		}
	}
	
	public void drawFG(float x, float y, int w, int h) {
		if (fgLayer < 0 || fgLayer >= getLayerCount()) {
			Log.error("Invalid FG layerID");
			return;
		}
		int cTileX = (int) x / getTileWidth();
		int cTileY = (int) y / getTileHeight();
		int numTileX = w / getTileWidth() + 2;
		int numTileY = h / getTileHeight() + 2;
		x -= cTileX * getTileWidth();
		y -= cTileY * getTileHeight();
		for (int iY = 0; iY < numTileY; iY++) {
			if (iY + cTileY >= 0 && iY + cTileY < getHeight()) {
				for (int iX = 0; iX < numTileX; iX++) {
					if (iX + cTileX >= 0 && iX + cTileX  < getWidth()) {
						Image i = getTileImage(iX + cTileX, iY + cTileY, fgLayer);
						if (i != null)
							i.draw(iX*getTileWidth()-x, iY*getTileHeight()-y);
					}
				}
				
			}
		}
	}
	
	public void draw(float x, float y, int w, int h) {
		int cTileX = (int) x / getTileWidth();
		int cTileY = (int) y / getTileHeight();
		int numTileX = w / getTileWidth() + 2;
		int numTileY = h / getTileHeight() + 2;
		x -= cTileX * getTileWidth();
		y -= cTileY * getTileHeight();
		for (int layer = 0; layer < getLayerCount();layer++) {
			for (int iY = 0; iY < numTileY; iY++) {
				if (iY + cTileY >= 0 && iY + cTileY < getHeight()) {
					for (int iX = 0; iX < numTileX; iX++) {
						if (iX + cTileX >= 0 && iX + cTileX  < getWidth()) {
							Image i = getTileImage(iX + cTileX, iY + cTileY, layer);
							if (i != null)
								i.draw(iX*getTileWidth()-x, iY*getTileHeight()-y);
						}
					}
					
				}
			}
		}
	}
	
	
	
}
