package game.Screen;

import game.Model;
import game.View;
import game.Controller.Controller;
import game.Event.EventCallback;

public interface Screen extends Controller, Model, View, EventCallback {
	void Initilize();
	void Dispose();
}
