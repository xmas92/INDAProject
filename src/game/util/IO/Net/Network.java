package game.util.IO.Net;

import game.client.Entity.Spell.PSType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.minlog.Log;

public class Network {

	static public void register(EndPoint endpoint) {
		Kryo kryo = endpoint.getKryo();
		kryo.register(Login.class);
		kryo.register(PlayerInfo.class);
		kryo.register(EntityInfo.class);
		kryo.register(GameServerInfo.class);
		kryo.register(LoginGranted.class);
		kryo.register(LoginRefused.class);
		kryo.register(UpdatePlayer.class);
		kryo.register(RemovePlayer.class);
		kryo.register(CastProjectileSpell.class);
		kryo.register(PSType.class);
		kryo.register(KillSpell.class);
		Log.set(Log.LEVEL_ERROR);
	}
	
	static public class Login {
		public String username;
		public int passwordHash;
	}
	
	static public class PlayerInfo {
		public String player;
		public EntityInfo entityInfo;
	}
	static public class EntityInfo {
		public float x, y, deltaX, deltaY, speed, w, h;
		public String imageID;
		
		public EntityInfo clone(){
			EntityInfo ret = new EntityInfo();
			ret.x = x;
			ret.y = y;
			ret.deltaX = deltaX;
			ret.deltaY = deltaY;
			ret.speed = speed;
			ret.imageID = imageID;
			ret.w = w;
			ret.h = h;
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
	
	static public class CastProjectileSpell {
		public EntityInfo entityInfo;
		public PSType type;
		public int id;
	}
	
	static public class KillSpell {
		public int id;
	}
	
	static public class UpdatePlayerHealth {
		public float health;
	}
}
