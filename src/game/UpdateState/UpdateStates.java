package game.UpdateState;

import game.Entity.GenericEntity;
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
	
	
}
