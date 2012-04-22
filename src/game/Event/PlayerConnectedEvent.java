package game.Event;

import game.Server.PlayerConnection;

public class PlayerConnectedEvent implements Event {
	public PlayerConnection pc;
	public PlayerConnectedEvent(PlayerConnection c) {
		pc = c;
	}

}
