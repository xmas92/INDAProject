package game.Zones;

import game.Model;
import game.View;
import game.Controller.Controller;

public interface Zone extends Model, Controller, View {
	public ZoneMap getZoneMap();
}
