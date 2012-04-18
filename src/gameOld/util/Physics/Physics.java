package gameOld.util.Physics;

import java.awt.geom.Point2D;

import gameOld.util.Geom.Rectangle;

public class Physics {
	
	static public boolean getCollision(Rectangle r1, Rectangle r2) {
		return r1.intersects(r2);
	}
	
	static public double getDistanceSquared(Point2D p1, Point2D p2) {
		double x = p1.getX() - p2.getX(),
		       y = p1.getY() - p2.getY();
		x *= x; y *= y;
		return x + y;
	}
}
