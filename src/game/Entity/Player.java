package game.Entity;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import game.Client;
import game.Controller.NetworkController;
import game.Event.Event;
import game.Event.NetworkEvent;
import game.Event.PlayerDrawEvent;
import game.Geometry.Rectangle;
import game.Input.InputState;
import game.Input.Key;
import game.Network.GameKryoReg.CreatePlayer;
import game.Network.GameKryoReg.PlayerMovement;
import game.Screen.GameScreen;
import game.Zones.Zone;
import game.Zones.Zones;

public class Player implements Entity {

	private final Key UpKey = new Key(Input.KEY_W, 0);
	private final Key DownKey = new Key(Input.KEY_S, 0);
	private final Key RightKey = new Key(Input.KEY_D, 0);
	private final Key LeftKey = new Key(Input.KEY_A, 0);
	
	private Image graphic;
	private float x, y, speed;
	private int deltaX, deltaY, w, h;
	private long timeSinceLastUpdate = 0;
	private boolean changed = true;
	private boolean Movable = false;
	private boolean LoadImage = false;
	private String imageRef;

	@Override
	public void Update(int delta) {
		if (!Movable) return;
		try {
			if (LoadImage) {
				LoadImage = false;
				Image g = graphic;
				try {
					graphic = new Image(imageRef);
				} catch (SlickException e) {
					graphic = g;
				}
			}
			int newDX = 0, newDY = 0;
			if (InputState.Get().KeyboardState.GetKeyState(UpKey).Down()) {
				newDY -= 1;
			}
			if (InputState.Get().KeyboardState.GetKeyState(DownKey).Down()) {
				newDY += 1;
			}
			if (InputState.Get().KeyboardState.GetKeyState(RightKey).Down()) {
				newDX += 1;
			}
			if (InputState.Get().KeyboardState.GetKeyState(LeftKey).Down()) {
				newDX -= 1;
			}
			if (Math.abs(newDY)+Math.abs(newDX) != 0) {
				float movement = (speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(newDY)+Math.abs(newDX));
				Rectangle rect = collisionBox();
				rect.y += newDY * movement;
				Zone z = Zones.CurrentZone();
				if (z != null) {
					if (z.getZoneMap().getCollision(rect)) {
						newDY = 0;
					}
				}
				rect = collisionBox();
				rect.x += newDX * movement;
				if (z != null) {
					if (z.getZoneMap().getCollision(rect)) {
						newDX = 0;
					}
				}
				changed = (newDY != deltaY || newDX != deltaX) || changed;
				deltaX = newDX;
				deltaY = newDY;
				
				x += deltaX * movement;
				y += deltaY * movement;
			} else {
				changed = (newDY != deltaY || newDX != deltaX) || changed;
				deltaX = newDX;
				deltaY = newDY;
			}
			if (changed && timeSinceLastUpdate > 50) {
				timeSinceLastUpdate = 0;
				changed = false;
				PlayerMovement pm = new PlayerMovement();
				pm.x = x; pm.y = y;
				pm.deltaX = deltaX;
				pm.deltaY = deltaY;
				pm.speed = speed;
				NetworkController.SendTCP(pm);
			} else {
				timeSinceLastUpdate += delta;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Draw() {
		if (graphic != null) {
			graphic.draw((GameScreen.w - w) * 0.5f, (GameScreen.h - h) * 0.5f, w, h);
		}
		Client.sendCallback(new PlayerDrawEvent());
	}

	@Override
	public synchronized void Callback(Event e) {
		if (e instanceof NetworkEvent) {
			HandleNetworkEvent((NetworkEvent)e);
		}
	}

	private void HandleNetworkEvent(NetworkEvent e) {
		if (e.Package instanceof CreatePlayer) {
			CreatePlayer cp = (CreatePlayer)e.Package;
			x = cp.x; y = cp.y; w = cp.w; h = cp.h; speed = cp.speed;
			imageRef = cp.imageRef;
			Movable = true;
			LoadImage = true;
		}
	}

	@Override
	public void Initialize() {
		try {
			graphic = new Image("data/noimage.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Point2D position() {
		return new Point2D.Float(x,y);
	}

	@Override
	public Dimension2D dimension() {
		return new Dimension(w, h);
	}

	@Override
	public Rectangle collisionBox() {
		return new Rectangle(x - w * 0.5f, y - h * 0.5f, w, h);
	}
}
