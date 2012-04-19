package game.Entity;

import game.Event.Event;
import game.Event.NetworkEvent;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.Screen.GameScreen;
import game.Zones.Zone;
import game.Zones.Zones;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GenericEntity implements Entity {
	
	private Image graphic;
	private float x, y, speed;
	private int deltaX, deltaY, w, h;
	private boolean LoadImage = false;
	private String imageRef;
	
	@Override
	public void Update(int delta) {
		if (LoadImage) {
			LoadImage = false;
			Image g = graphic;
			try {
				graphic = new Image(imageRef);
			} catch (SlickException e) {
				graphic = g;
			}
		}
		if (Math.abs(deltaX)+Math.abs(deltaY) != 0) {
			float movement = (speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(deltaX)+Math.abs(deltaY));
			Rectangle rect = collisionBox();
			rect.y += deltaY * movement;
			Zone z = Zones.CurrentZone();
			if (z != null) {
				if (z.getZoneMap().getCollision(rect)) {
					//deltaY = 0;
				}
			}
			rect = collisionBox();
			rect.x += deltaX * movement;
			if (z != null) {
				if (z.getZoneMap().getCollision(rect)) {
					//deltaX = 0;
				}
			}
			x += deltaX * movement;
			y += deltaY * movement;
			MoveCorrection(delta);
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
	public void Draw() {
		if (graphic != null) {
			int drawX = (int)(x - GameScreen.player.position().getX() + (GameScreen.w - w) * 0.5f),
				drawY = (int)(y - GameScreen.player.position().getY() + (GameScreen.h - h) * 0.5f);
			if (drawX > -GameScreen.w && drawX < GameScreen.w * 2 &&
				drawY > -GameScreen.h && drawY < GameScreen.w * 2) {
				graphic.draw(drawX, drawY, w, h);
			}
		}
	}

	@Override
	public void Callback(Event e) {
		if (e instanceof NetworkEvent) {
			NetworkEvent ne = (NetworkEvent)e;
			if (ne.Package instanceof CreateGenericEntity) {
				CreateGenericEntity cge = (CreateGenericEntity)ne.Package;
				x = cge.x; y = cge.y; speed = cge.speed; h = cge.h; w = cge.w;
				imageRef = cge.imageRef; LoadImage = true;
				System.out.println("Create GE");
			}
			if (ne.Package instanceof GenericEntityMovement) {
				GenericEntityMovement cem = (GenericEntityMovement)ne.Package;
				deltaX = cem.deltaX; deltaY = cem.deltaY;
				MoveCorrection(x - cem.x, y - cem.y);
				System.out.println("Update GE");
			}
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
		return new Rectangle(x - w * 0.5f, y - h * 0.5f, w , h);
	}
	
	private int timeLeft = 0;
	private float needToMoveX = 0, needToMoveY = 0;
	private void MoveCorrection(int delta) {
		return;/*
		float ratio = (float)delta / (float)timeLeft;
		timeLeft -= delta;
		if (ratio > 0) {
			x += needToMoveX * ratio;
			y += needToMoveY *ratio;
			needToMoveX -= needToMoveX * ratio;
			needToMoveY -= needToMoveY * ratio;
		} else {
			x += needToMoveX;
			y += needToMoveY;
			needToMoveX = 0;
			needToMoveY = 0;
		}*/
	}

	private void MoveCorrection(float x, float y) {
		this.x += x;
		this.y += y;
		timeLeft = 500;
		needToMoveX += x;
		needToMoveY += y;
	}
}
