package game.Geometry;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Rectangle2D.Float {

	public Rectangle(float x, float y, float w, float h) {
		super(x,y,w,h);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8415412102063708559L;
	
	public Point2D[] getPoints() {
		Point2D[] ret = new Point2D.Float[4];
		ret[0] = new Point2D.Float(x,y);
		ret[1] = new Point2D.Float(x+width,y);
		ret[2] = new Point2D.Float(x+width,y+height);
		ret[3] = new Point2D.Float(x,y+height);
		return ret;
	}
	
}
