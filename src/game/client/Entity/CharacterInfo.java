package game.client.Entity;

import game.util.IO.Net.Packagble;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class CharacterInfo implements Packagble{
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

	@Override
	public void readFromStream(DataInputStream ois) {
		try {
			if (serialVersionUID != ois.readLong())
				throw new Exception();
			x = ois.readFloat();
			y = ois.readFloat();
			deltaX = ois.readFloat();
			deltaY = ois.readFloat();
			angle = ois.readFloat();
			speed = ois.readFloat();
			imageID = ois.readUTF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void writeToStream(DataOutputStream oos) {
		try {
			oos.writeLong(serialVersionUID);
			oos.writeFloat(x);
			oos.writeFloat(y);
			oos.writeFloat(deltaX);
			oos.writeFloat(deltaY);
			oos.writeFloat(angle);
			oos.writeFloat(speed);
			oos.writeUTF(imageID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
