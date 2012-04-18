package gameOld.client.Login;

import java.awt.Dimension;
import java.awt.Point;

import gameOld.util.IO.InputState;
import gameOld.util.IO.Event.Event;
import gameOld.util.IO.Event.EventListner;
import gameOld.util.IO.Net.Network;
import gameOld.util.IO.Net.Network.GameServerInfo;
import gameOld.util.IO.Net.Network.Login;
import gameOld.util.IO.Net.Network.LoginGranted;
import gameOld.util.IO.Net.Network.LoginRefused;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.*;

public class LoginScreen implements Game {
	
	private Image bg;
	private LoginButton login, quit;
	private LoginTextField username, password;
	public GameServerInfo gsi = null;
	public String un;
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
				final Client client = new Client();
				client.start();
				Network.register(client);
				
				client.addListener(new ThreadedListener(new Listener() {
                    public void connected (Connection connection) {
                    }
                    
                    public void received (Connection connection, Object object) {
                            if (object instanceof LoginGranted) {
                            	gsi = ((LoginGranted)object).gsi;
                            	un = username.getText();
                            }
                            if (object instanceof LoginRefused) {
                            }
                        	connection.close();
                    }
                    public void disconnected (Connection connection) {
                    	client.close();
                    }
				}));
				try {
					client.connect(5000, "81.229.86.19", 12345);
					Login l = new Login();
					l.username = username.getText();
					l.passwordHash = password.getText().hashCode();
					client.sendTCP(l);
				} catch (Exception e) {
					e.printStackTrace();
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
