package game.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class GameKryoReg implements KryoRegister {

	@Override
	public void Register(EndPoint ep) {
		Kryo k = ep.getKryo();
		k.register(PlayerLoginRequest.class);
	}
	
	static public class PlayerLoginRequest {
		public String Username;
	}

}
