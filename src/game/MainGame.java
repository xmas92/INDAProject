package game;

import game.Controller.Controller;
import game.Controller.NetworkController;
import game.Event.Event;
import game.Event.EventCallback;
import game.Event.NetworkEvent;
import game.Screen.LoginScreen;
import game.Screen.Screen;

public class MainGame implements Controller, Model, View, EventCallback {

	private Screen activeScreen = new LoginScreen();
	
	public MainGame() {
		activeScreen.Initilize();
	}
	
	@Override
	public void Callback(Event e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Draw() {
		if (activeScreen != null)
			activeScreen.Draw();
	}

	@Override
	public void Update(int delta) {
		NetworkEvent e = null;
		while ((e = NetworkController.PollEvent()) != null) {
			activeScreen.Callback(e);
		}
		if (activeScreen != null)
			activeScreen.Update(delta);
	}

}
