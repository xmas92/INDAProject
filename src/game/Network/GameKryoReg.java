package game.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class GameKryoReg implements KryoRegister {

	@Override
	public void Register(EndPoint ep) {
		Kryo k = ep.getKryo();
		k.register(PlayerLoginRequest.class);
		k.register(PlayerMovement.class);
		k.register(CreatePlayer.class);
		k.register(CreateGenericEntity.class);
		k.register(DestroyGenerticEntity.class);
		k.register(GenericEntityMovement.class);
	}
	
	static public class PlayerLoginRequest {
		public String Username;
	}
	
	static public class PlayerMovement {
		public float x, y, speed;
		public int deltaX, deltaY;
	}
	
	static public class CreatePlayer {
		public float x, y, speed;
		public int w, h;
		public String imageRef;
	}
	
	static public class CreateGenericEntity {
		public long UUIDp1, UUIDp2;
		public float x, y, speed;
		public int w, h, deltaX, deltaY;
		public int updateID, drawID;
	}
	
	static public class DestroyGenerticEntity {
		public long UUIDp1, UUIDp2;
	}
	
	static public class GenericEntityMovement {
		public long UUIDp1, UUIDp2;
		public float x, y, speed;
		public int deltaX, deltaY;
	}

}
