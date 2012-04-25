package game.Controller;

import game.Client;
import game.MainGame;
import game.Model;
import game.View;
import game.Event.CloseEvent;
import game.Event.Event;
import game.Input.InputState;
import game.Resources.ResourceManager;

public class MainController implements Controller, Model, View {
	
	public static String Title = "Test";
	
	private MainGame MainGame;
	
	public void sendCallback(Event e) {
		MainGame.Callback(e);
	}
	
	public void Initialize() {
		MainGame = new MainGame();
		
		ResourceManager.Manager().Initialize();
		
		NetworkController.Initialize();
		MainGame.Initialize();
		Client.Game.setAlwaysRender(true);
		Client.Game.setUpdateOnlyWhenVisible(false);
		
		NetworkController.SetCallback(MainGame);
	}
	
	@Override
	public void Draw() {
		MainGame.Draw();
	}

	@Override
	public void Update(int delta) {
		InputState.Update(Client.Game);
		MainGame.Update(delta);
	}

	public String setTitel() {
		return Title;
	}

	public boolean Close() {
		CloseEvent e = new CloseEvent();
		MainGame.Callback(e);
		return e.Close();
	}

}
