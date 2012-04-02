package game.client;

import game.client.Login.LoginScreen;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class EntryPoint {

	//test commit 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			AppGameContainer apc = new AppGameContainer(new LoginScreen());
			apc.setDisplayMode(540, 280, false);
			apc.start();
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
	}

}
