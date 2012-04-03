package game.client;

import game.client.Game.MainGame;

import org.newdawn.slick.AppGameContainer;

public class EntryPoint {

	//test commit 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainGame game = new MainGame();
			AppGameContainer apc = new AppGameContainer(game);
			apc.setDisplayMode(540, 280, false);
			game.apc = apc;
			apc.setAlwaysRender(true);
			apc.setUpdateOnlyWhenVisible(false);
			apc.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
