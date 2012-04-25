package game.UpdateState;

import game.Entity.GenericEntity;
import game.UpdateState.States.NullUpdateState;
import game.UpdateState.States.PlayerUpdateState;

public enum UpdateStates {
	PlayerUpdateState,
	;

	public static UpdateState getNewState(int id, GenericEntity entity) {
		if (id == PlayerUpdateState.ordinal()) { return new PlayerUpdateState(entity); }
		return new NullUpdateState();
	}
	
	
}
