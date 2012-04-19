package game.Entity;

import game.Model;
import game.View;
import game.Controller.Controller;
import game.Event.EventCallback;

public interface Entity extends Controller, Model, View, EventCallback {
	
}
