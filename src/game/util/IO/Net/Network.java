package game.util.IO.Net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.minlog.Log;

public class Network {

	static public void register(EndPoint endpoint) {
		Kryo kryo = endpoint.getKryo();
		kryo.register(Login.class);
		kryo.register(PlayerInfo.class);
		kryo.register(CharacterInfo.class);
		kryo.register(GameServerInfo.class);
		kryo.register(LoginGranted.class);
		kryo.register(LoginRefused.class);
		kryo.register(UpdatePlayer.class);
		kryo.register(RemovePlayer.class);
		Log.set(Log.LEVEL_ERROR);
	}
	
	static public class Login {
		public String username;
		public int passwordHash;
	}
	
	static public class PlayerInfo {
		public String player;
		public CharacterInfo characterInfo;
	}
	
	static public class CharacterInfo {
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
	
	static public class GameServerInfo {
		public String ip;
		public int port;
	}

	static public class LoginGranted {
		public GameServerInfo gsi;
	}
	static public class LoginRefused {
		
	}
	
	static public class UpdatePlayer {
		public PlayerInfo playerInfo;
	}
	static public class RemovePlayer {
		public String username;
	}
	
	static public class ProjectileSpellInfo {
		public float x, y, deltaX, deltaY, speed;
		
	}
}
