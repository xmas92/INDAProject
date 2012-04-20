package game.Zones;

import game.Model;
import game.Controller.Controller;
import game.Event.Event;
import game.Event.EventCallback;

public class Zones implements EventCallback, Model, Controller {

	private static Zone currentzone;
	
	public static Zone CurrentZone() {
		return currentzone;
	}

	@Override
	public void Update(int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Initialize() {
		currentzone = new HubZone();
		currentzone.Initialize();
	}

	@Override
	public void Callback(Event e) {
		if (currentzone != null)
			currentzone.Callback(e);
	}

}
