package game;

import game.Controller.Controller;
import game.Controller.NetworkController;
import game.Event.Event;
import game.Event.EventCallback;
import game.Network.GameKryoReg;
import game.Network.GameKryoReg.PlayerLoginRequest;
import game.Screen.GameScreen;
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
		if (LoginScreen.instance.done) {
			activeScreen = GameScreen.instance;
			activeScreen.Initialize();
			LoginScreen.instance.done = false;
			NetworkController.Reset();
			NetworkController.Register(new GameKryoReg());
			NetworkController.Connect(LoginScreen.instance.ip, LoginScreen.instance.port, LoginScreen.instance.port + 1);
			PlayerLoginRequest plr = new PlayerLoginRequest();
			plr.Username = LoginScreen.instance.un;
			NetworkController.SendTCP(plr);
		}
		if (activeScreen != null)
			activeScreen.Update(delta);
	}

	@Override
	public void Initialize() {
		activeScreen = LoginScreen.instance;
		activeScreen.Initialize();
	}

}
