package game;

import game.Controller.Controller;
import game.Event.Event;
import game.Event.EventCallback;
import game.Screen.LoginScreen;
import game.Screen.Screen;

public class MainGame implements Controller, Model, View, EventCallback {

	private Screen activeScreen;
	
	@Override
	public void Callback(Event e) {
		activeScreen.Callback(e);
	}

	@Override
	public void Draw() {
		if (activeScreen != null)
			activeScreen.Draw();
	}

	@Override
	public void Update(int delta) {
		if (activeScreen != null)
			activeScreen.Update(delta);
	}

	@Override
	public void Initialize() {
		activeScreen = new LoginScreen();
		activeScreen.Initialize();
	}

}
