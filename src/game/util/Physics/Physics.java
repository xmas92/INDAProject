package game.util.Physics;

import game.util.Geom.Rectangle;

public class Physics {
	
	static public boolean getCollision(Rectangle r1, Rectangle r2) {
		return r1.intersects(r2);
	}
}
