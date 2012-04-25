package game.Entity;

import game.DrawState.DrawState;
import game.DrawState.DrawStates;
import game.Event.Event;
import game.Event.NetworkEvent;
import game.Geometry.Rectangle;
import game.Network.GameKryoReg.CreateGenericEntity;
import game.Network.GameKryoReg.GenericEntityMovement;
import game.UpdateState.UpdateState;
import game.UpdateState.UpdateStates;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class GenericEntity implements Entity {
	public float x, y, speed;
	public int deltaX, deltaY, w, h;
	public UpdateState updateState;
	public DrawState drawState;
	
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
				CreateGenericEntity cge = (CreateGenericEntity)ne.Package;
				x = cge.x; y = cge.y; speed = cge.speed; h = cge.h; w = cge.w;
				deltaX = cge.deltaX; deltaY = cge.deltaY;
				updateState = UpdateStates.getNewState(cge.drawID, this);
				drawState = DrawStates.getNewState(cge.drawID, this);
			}
			if (ne.Package instanceof GenericEntityMovement) {
				GenericEntityMovement cem = (GenericEntityMovement)ne.Package;
				deltaX = cem.deltaX; deltaY = cem.deltaY;
				x = cem.x; y = cem.y;
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
}
