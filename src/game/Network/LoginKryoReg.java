package game.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class LoginKryoReg implements KryoRegister {
	
	@Override
	public void Register(EndPoint ep) {
		Kryo k = ep.getKryo();
		k.register(LoginGranted.class);
		k.register(LoginRefused.class);
		k.register(LoginRequested.class);
	}

}
