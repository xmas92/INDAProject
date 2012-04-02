package game.client.Login;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.util.IO.InputState;
import game.util.IO.Event.Event;
import game.util.IO.Event.EventListner;
import game.util.IO.Packages.EmptyPackage;
import game.util.IO.Packages.GameServerInfoPackage;
import game.util.IO.Packages.LoginInfoPackage;
import game.util.IO.Packages.Package;
import game.util.IO.Packages.PackageFlag;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class LoginScreen implements Game{
	
	private Image bg;
	private LoginButton login, quit;
	private LoginTextField username, password;
	public LoginScreen() {
		
	}

	@Override
	public boolean closeRequested() {
		return true;
	}

	@Override
	public String getTitle() {
		return "MyGameName - LoginScreen";
	}

	@Override
	public void init(final GameContainer container) throws SlickException {
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
				container.exit();
			}
		});
		login.addMouseClickEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				doLogin();
			}

			private void doLogin() {
				Socket socket = null;
				try {
					socket = new Socket("81.229.86.19", 12345);
				 
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					Package pkg;
					oos.writeObject(new LoginInfoPackage(username.getText(), password.getText().hashCode(),PackageFlag.loginRequest));
					oos.flush();
					pkg = (Package) ois.readObject();
					if (pkg.Flag() == PackageFlag.loginRefused)
						System.out.println("Login Refused");
					else if (pkg.Flag() == PackageFlag.loginGranted) {
						System.out.println("Login Granted");
						GameServerInfoPackage gsip = (GameServerInfoPackage) pkg;
						System.out.println("Gameserver: " + gsip.ip + ":" + gsip.port);
					}
					oos.writeObject(new EmptyPackage(PackageFlag.closeConnectionRequest));
					oos.flush();
					pkg = (Package) ois.readObject();
					if (pkg.Flag() != PackageFlag.closeConnectionAcknowledged)
						throw new Exception();
					ois.close();
					oos.close();
					socket.close();
			   } catch (Exception e) {
					e.printStackTrace();
			   } finally {
					try {
						if (socket != null)
							socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
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
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		bg.draw(0, 0, container.getWidth(), container.getHeight());
		login.render(container, g);
		quit.render(container, g);
		username.render(container, g);
		password.render(container, g);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		InputState.Update(container);
		login.update(container);
		quit.update(container);
		username.update(container);
		password.update(container);
	}
}
