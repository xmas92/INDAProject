package game.DrawState;

import game.View;
import game.Entity.GenericEntity;

public abstract class DrawState implements View {
	protected final GenericEntity entity;
	public DrawState(GenericEntity entity) {
		this.entity = entity;
	}
}
