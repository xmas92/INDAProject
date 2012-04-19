package game;

import game.Controller.MainController;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Client {
	
	public static final AppGameContainer Game = CreateGame();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Game.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static AppGameContainer CreateGame() {
		try {
			return new AppGameContainer(new Game() {
				
				private MainController MainCTRL = new MainController();
				
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
