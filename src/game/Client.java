package game;

import game.Controller.MainController;
import game.Event.Event;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Client {
	/**
	 * Version of the client (must be same as serve)
	 */
	public static final String Version = "TEST";
	/**
	 * The instance of this game
	 */
	public static AppGameContainer Game;
	
	private static MainController MainCTRL = new MainController();
	
	/**
	 * Send an event callback that ripples through the program
	 * @param e the event
	 */
	public static void sendCallback(Event e) {
		MainCTRL.sendCallback(e);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Game = CreateGame();
			Game.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static AppGameContainer CreateGame() {
		try {
			return new AppGameContainer(new Game() {
				
				@Override
				public void update(GameContainer container, int delta)
						throws SlickException {
					MainCTRL.Update(delta);
				}
				
				@Override
				public void render(GameContainer container, Graphics g)
						throws SlickException {
					MainCTRL.Draw();
				}
				
				@Override
				public void init(GameContainer container) throws SlickException {
					MainCTRL.Initialize();					
				}
				
				@Override
				public String getTitle() {
					return MainCTRL.setTitel();
				}
				
				@Override
				public boolean closeRequested() {
					return MainCTRL.Close();
				}
				
			});
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
}
