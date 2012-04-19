package game.Event;

import com.esotericsoftware.kryonet.Connection;

public class NetworkEvent implements Event {

	public final Connection Connection;
	public final Object Package;
	public NetworkEvent(Connection connection, Object object) {
		Connection = connection;
		Package = object;
	}

}
