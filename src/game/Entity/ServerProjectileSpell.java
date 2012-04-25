package game.Entity;

import java.util.UUID;

import game.Event.Event;
import game.Event.NetworkEvent;

public class ServerProjectileSpell implements ServerEntity {
	
	private float x, y, speed;
	private int deltaX, deltaY, w, h;
	private int drawID;
	private UUID uuid;
	
	@Override
	public void Update(int delta) {
		if (Math.abs(deltaX)+Math.abs(deltaY) != 0) {
			float movement = (speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(deltaX)+Math.abs(deltaY));
			x += deltaX * movement;
			y += deltaY * movement;
		} 
	}

	@Override
	public void Initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Callback(Event e) {
		if (e instanceof NetworkEvent) { HandleNetworkEvent((NetworkEvent)e); }
	}

	private void HandleNetworkEvent(NetworkEvent e) {
		// TODO Auto-generated method stub
		
	}

}
