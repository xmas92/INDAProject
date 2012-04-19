package game.Controller;

import game.Client;
import game.MainGame;
import game.Model;
import game.View;
import game.Event.CloseEvent;
import game.Input.InputState;

public class MainController implements Controller, Model, View {
	
	private static String Title = "Test";
	
	private MainGame MainGame;
	
	public void Initialize() {
		NetworkController.Initialize();
		MainGame = new MainGame();
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
