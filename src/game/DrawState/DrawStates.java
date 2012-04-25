package game.DrawState;

import game.DrawState.States.UnknownDrawState;
import game.Entity.GenericEntity;

public enum DrawStates {
	PlayerDrawState,
	;
	
	public static DrawState getNewState(int id, GenericEntity entity) {
		if (id == PlayerDrawState.ordinal()) { return new game.DrawState.States.PlayerDrawState(entity); }
		return new UnknownDrawState(entity);
	}
}
