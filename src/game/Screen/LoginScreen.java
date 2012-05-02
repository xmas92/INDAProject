package game.Screen;

import java.awt.Dimension;
import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.Client;
import game.Controller.NetworkController;
import game.Event.Event;
import game.Event.EventListner;
import game.Event.NetworkEvent;
import game.Network.LoginGranted;
import game.Network.LoginKryoReg;
import game.Network.LoginRefused;
import game.Network.LoginRequested;
import game.UserInterface.LoginButton;
import game.UserInterface.LoginTextField;

public class LoginScreen implements Screen {
	
	public static final LoginScreen instance = new LoginScreen();
	
	private Image bg;
	private LoginButton login, quit;
	private LoginTextField username, password;
	
	public String un;
	public String ip;
	public int port;
	public boolean done = false;
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
		if (e instanceof NetworkEvent) {
			NetworkEvent ne = (NetworkEvent)e;
			boolean tmp = false;
			if (ne.Package instanceof LoginGranted) {
				ip = ((LoginGranted)ne.Package).IP; 
				port = ((LoginGranted)ne.Package).Port;
				tmp = true;
			}
			if (ne.Package instanceof LoginRefused) {
				System.out.println("Reason: " + ((LoginRefused)ne.Package).Reason);
			}
			login.Enable();
			ne.Connection.close();
			done = tmp;
		}
	}

	@Override
	public void Initialize() {
		try {
			NetworkController.Register(new LoginKryoReg());
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
					login.Disable();
					un = username.getText();
					doLogin();
				}
	
				private void doLogin() {
					if(NetworkController.Connect("81.229.86.19", 12345)) {
						LoginRequested ret = new LoginRequested();
						ret.Username = username.getText();
						ret.PasswordHash = password.getText().hashCode();
						ret.Version = Client.Version;
						NetworkController.SendTCP(ret);
					} else {
						login.Enable();
					}
				}
			});
			username = new LoginTextField(new SpriteSheet("data/LoginScreen/Username.png", 300, 30));
			password = new LoginTextField(new SpriteSheet("data/LoginScreen/Password.png", 300, 30));
			password.setPasswordField(true);
			username.setLocation(new Point(190, 120));
			password.setLocation(new Point(190, 160));
			username.setDimension(new Dimension(300, 30));
			password.setDimension(new Dimension(300, 30));
		} catch (SlickException e) {
			e.printStackTrace();
			Client.Game.exit();
		}
	}

	@Override
	public void Dispose() {
		
	}

}
