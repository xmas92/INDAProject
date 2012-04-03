package game.client.Entity;

import java.io.Serializable;

public class CharacterInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7537423065297786966L;
	
	public float x, y, deltaX, deltaY, angle, speed;
	public String imageID;
	
	public CharacterInfo clone(){
		CharacterInfo ret = new CharacterInfo();
		ret.x = x;
		ret.y = y;
		ret.deltaX = deltaX;
		ret.deltaY = deltaY;
		ret.angle = angle;
		ret.speed = speed;
		ret.imageID = imageID;
		return ret;
	}
}
