package game.UpdateState;

import game.Controller.Controller;
import game.Entity.GenericEntity;

public abstract class UpdateState implements Controller {
	protected final GenericEntity entity;
	public UpdateState(GenericEntity entity) {
		this.entity = entity;
	}
}
