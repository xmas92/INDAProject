package game.Screen;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.Client;
import game.Event.Event;
import game.Event.EventListner;
import game.UserInterface.LoginButton;
import game.UserInterface.LoginTextField;

public class LoginScreen implements Screen {
	
	private Image bg;
	private LoginButton login, quit;
	private LoginTextField username, password;
	
	public String un;
	@Override
	public void Update(int delta) {
		login.Update(delta);
		quit.Update(delta);
		username.Update(delta);
		password.Update(delta);
	}

	@Override
	public void Draw() {
		bg.draw(0, 0, 540, 280);
		login.Draw();
		quit.Draw();
		username.Draw();
		password.Draw();
	}

	@Override
	public void Callback(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Initilize() {
		try {
			Client.Game.setDisplayMode(540, 280, false);
			bg = new Image("data/LoginScreen/BG.png");
			login = new LoginButton(new SpriteSheet("data/LoginScreen/Loginbtn.png", 140, 25));
			quit = new LoginButton(new SpriteSheet("data/LoginScreen/Quitbtn.png", 140, 25));
			login.setLocation(new Point(190,195));
			quit.setLocation(new Point(340, 195));
			login.setDimension(new Dimension(140, 25));
			quit.setDimension(new Dimension(140, 25));
			quit.addMouseClickEventListner(new EventListner() {
				
				@Override
				public void Invoke(Object sender, Event e) {
					Client.Game.exit();
				}
			});
			login.addMouseClickEventListner(new EventListner() {
				
				@Override
				public void Invoke(Object sender, Event e) {
					doLogin();
				}
	
				private void doLogin() {
					System.out.println("loging");
				}
			});
			username = new LoginTextField(new SpriteSheet("data/LoginScreen/Username.png", 300, 30));
			password = new LoginTextField(new SpriteSheet("data/LoginScreen/Password.png", 300, 30));
			password.setPasswordField(true);
			username.setLocation(new Point(190, 120));
			password.setLocation(new Point(190, 160));
			username.setDimension(new Dimension(300, 30));
			password.setDimension(new Dimension(300, 30));
		} catch (SlickException e1) {
			e1.printStackTrace();
			return;
		}
	}

	@Override
	public void Dispose() {
		
	}

}
