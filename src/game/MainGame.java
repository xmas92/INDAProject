package game;

import game.Controller.Controller;
import game.Event.Event;
import game.Event.EventCallback;

public class MainGame implements Controller, Model, View, EventCallback {

	@Override
	public void Callback(Event e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Draw() {
		Client.Game.getGraphics().drawLine(0, 0, 800, 600);
	}

	@Override
	public void Update(int delta) {
		// TODO Auto-generated method stub

	}

}
