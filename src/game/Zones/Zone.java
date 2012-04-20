package game.Zones;

import game.Model;
import game.View;
import game.Controller.Controller;
import game.Event.EventCallback;

public interface Zone extends Model, Controller, View, EventCallback {
	public ZoneMap getZoneMap();
}
