package game.TEST;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.Event.Event;
import game.Input.InputState;
import game.Screen.Screen;
import game.UserInterface.CopyTextField;
import game.UserInterface.TextBox;

public class ChatboxTest implements Screen {
	
	private TextBox tb; 
	private CopyTextField ltf; 
	public static AppGameContainer agc; 
	
	public static void main(String args[]){
		try{
			agc = new AppGameContainer(new Game() {
				ChatboxTest cbt = new ChatboxTest();
				
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
					return "Chatbox test";
				}
				
				@Override
				public boolean closeRequested() {
					// TODO Auto-generated method stub
					return true;
				}
			});
			agc.setDisplayMode(800, 600, false);
			agc.start();
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Update(int delta) {
		// TODO Auto-generated method stub
		tb.Update(delta);
		ltf.Update(delta);
	}

	@Override
	public void Initialize() {
		// TODO Auto-generated method stub
		try {
			tb = new TextBox(new Image("data/GameAssets/chatbox.png"));
			ltf = new CopyTextField(new SpriteSheet("data/LoginScreen/Username.png", 300, 30));
			tb.setLocation(new Point(25,25)); 
			tb.setDimension(new Dimension(200,300)); 
			ltf.setLocation(new Point(500,500));
			ltf.setDimension(new Dimension(300,30));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void Draw() {
		// TODO Auto-generated method stub
		tb.Draw();
		ltf.Draw();
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
