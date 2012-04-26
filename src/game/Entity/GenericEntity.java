package game.Entity;

import game.DrawState.DrawState;
import game.DrawState.DrawStates;
import game.Event.CreateClientGenericEntityEvent;
import game.Event.Event;
import game.Event.NetworkEvent;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.CGEWithCollisionIgnore;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.UpdateState.CollisionIgnore;
import game.UpdateState.UpdateState;
import game.UpdateState.UpdateStates;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class GenericEntity implements Entity {
	public float x, y, speed, deltaX, deltaY;
	public int  w, h;
	public UpdateState updateState;
	public DrawState drawState;
	public GEType type;
	
	@Override
	public void Update(int delta) { 
		if (updateState != null)
			updateState.Update(delta);
	}

	@Override
	public void Initialize() {
		
	}

	@Override
	public void Draw() {
		if (drawState != null)
			drawState.Draw();
	}

	@Override
	public void Callback(Event e) {
		if (e instanceof NetworkEvent) {
			NetworkEvent ne = (NetworkEvent)e;
			if (ne.Package instanceof CreateGenericEntity) {
				CreateThis((CreateGenericEntity)ne.Package);
			}
			if (ne.Package instanceof GenericEntityMovement) {
				GenericEntityMovement cem = (GenericEntityMovement)ne.Package;
				deltaX = cem.deltaX; deltaY = cem.deltaY;
				x = cem.x; y = cem.y;
			}
		}
		if (e instanceof CreateClientGenericEntityEvent) {
			CreateThis((CreateClientGenericEntityEvent)e);
		}
	}

	private void CreateThis(CreateGenericEntity cge) {
		x = cge.x; y = cge.y; speed = cge.speed; h = cge.h; w = cge.w;
		deltaX = cge.deltaX; deltaY = cge.deltaY;
		updateState = UpdateStates.getNewState(cge.updateID, this);
		drawState = DrawStates.getNewState(cge.drawID, this);
		type = GEType.getType(cge.type);
		if (cge instanceof CGEWithCollisionIgnore) {
			HandleCGEWithCollisionIgnore((CGEWithCollisionIgnore)cge);
		}
	}

	private void HandleCGEWithCollisionIgnore(CGEWithCollisionIgnore cgewci) {
		if (updateState instanceof CollisionIgnore) {
			((CollisionIgnore)updateState).AddIgnore(cgewci.UUID);
			((CollisionIgnore)updateState).AddIgnoreType(cgewci.Types);
		}
	}

	private void CreateThis(CreateClientGenericEntityEvent ccgee) {
		x = ccgee.x; y = ccgee.y; speed = ccgee.speed; h = ccgee.h; w = ccgee.w;
		deltaX = ccgee.deltaX; deltaY = ccgee.deltaY;
		updateState = UpdateStates.getNewState(ccgee.us.ordinal(), this);
		drawState = DrawStates.getNewState(ccgee.ds.ordinal(), this);
		type = ccgee.type;
		if (updateState instanceof CollisionIgnore) {
			((CollisionIgnore)updateState).AddIgnoreType(ccgee.ignoreType);
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
}
