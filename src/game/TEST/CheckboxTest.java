package game.TEST;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.Client;
import game.Event.Event;
import game.Screen.Screen;
import game.UserInterface.Checkbox;

public class CheckboxTest implements Screen {
	
	public static final CheckboxTest instance = new CheckboxTest();
	private Checkbox cb; 
	
	public CheckboxTest() {
		
	}
	
	@Override
	public void Update(int delta) {
		cb.Update(delta); 
		
	}

	@Override
	public void Initialize() {
		try {
		Client.Game.setDisplayMode(100, 100, false);
		cb.setLocation(new Point(25,25)); 
		cb.setDimension(new Dimension(15,15)); 
		cb = new Checkbox(new SpriteSheet("data/GameAssets/checkboxsprite.png",15, 30)); 
		}
		catch(SlickException e)
		{
			e.printStackTrace();
			System.exit(1); 
		}
	}

	@Override
	public void Draw() {
		cb.Draw();
		
	}

	@Override
	public void Callback(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
