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
import game.Input.InputState;
import game.Screen.Screen;
import game.UserInterface.Checkbox;

public class CheckboxTest implements Screen {
	
	public static void main(String[] args) {
		AppGameContainer apc;
		try {
			apc = new AppGameContainer(new Game() {
				CheckboxTest cbt = new CheckboxTest();
				@Override
				public void update(GameContainer container, int delta)
						throws SlickException {
					InputState.Update(container);
					cbt.Update(delta);
				}
				
				@Override
				public void render(GameContainer container, Graphics g)
						throws SlickException {
					cbt.Draw();
				}
				
				@Override
				public void init(GameContainer container) throws SlickException {
					cbt.Initialize();
				}
				
				@Override
				public String getTitle() {
					// TODO Auto-generated method stub
					return "test";
				}
				
				@Override
				public boolean closeRequested() {
					// TODO Auto-generated method stub
					return true;
				}
			});
		apc.setDisplayMode(100, 100, false);
		apc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
			cb = new Checkbox(new SpriteSheet("data/GameAssets/checkboxsprite.png",15, 15)); 
			cb.setLocation(new Point(25,25)); 
			cb.setDimension(new Dimension(15,15)); 
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
