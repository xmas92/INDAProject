package game.util.physics;

import game.util.Geom.Rectangle;

public class Physics {
	
	static Rectangle rect1; 
	static Rectangle rect2; 
	
	public Physics(Rectangle rect1, Rectangle rect2) {
		Physics.rect1 = rect1; 
		Physics.rect2 = rect2; 
	}
	
	static boolean isCollide() {
		return(rect1.intersects(rect2)); 
	}
	
}
