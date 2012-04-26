package game.UpdateState;

import game.Entity.GenericEntity;
import game.Entity.ServerEntity;
import game.UpdateState.ServerStates.NullServerUpdateState;
import game.UpdateState.ServerStates.PlayerServerUpdateState;
import game.UpdateState.ServerStates.ProjectileServerUpdateState;
import game.UpdateState.States.NullUpdateState;
import game.UpdateState.States.PlayerUpdateState;
import game.UpdateState.States.ProjectileUpdateState;

public enum UpdateStates {
	PlayerUpdateState, ProjectileUpdateState,
	;

	public static UpdateState getNewState(int id, GenericEntity entity) {
		if (id == PlayerUpdateState.ordinal()) { return new PlayerUpdateState(entity); }
		if (id == ProjectileUpdateState.ordinal()) { return new ProjectileUpdateState(entity); }
		return new NullUpdateState();
	}
	
	public static ServerUpdateState getNewServerState(int id, ServerEntity entity) {
		if (id == PlayerUpdateState.ordinal()) { return new PlayerServerUpdateState(entity); }
		if (id == ProjectileUpdateState.ordinal()) { return new ProjectileServerUpdateState(entity); }
		return new NullServerUpdateState();
	}
	
}
