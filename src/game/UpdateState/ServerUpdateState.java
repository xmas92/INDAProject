package game.UpdateState;

import game.Controller.Controller;
import game.Entity.ServerEntity;

public abstract class ServerUpdateState implements Controller {
	protected final ServerEntity entity;
	public ServerUpdateState(ServerEntity entity) {
		this.entity = entity;
	}
}
