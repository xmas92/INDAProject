package gameOld.client.Entity;

import gameOld.util.Geom.Rectangle;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public interface Entity {
	public Point2D position();
	public Dimension2D dimension();
	public Rectangle collisionBox();
}
