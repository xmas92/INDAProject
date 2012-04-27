package game.DrawState;

import game.DrawState.States.ProjectileDyingDrawState;
import game.DrawState.States.ProjectileLiveDrawState;
import game.DrawState.States.PlayerDrawState;
import game.DrawState.States.UnknownDrawState;
import game.DrawState.States.ZombieDrawState;
import game.Entity.GenericEntity;

public enum DrawStates {
	UnknownDrawState, PlayerDrawState, ProjectileLiveDrawState, ProjectileDyingDrawState, NullDrawState, ZombieDrawState,
	;
	
	public static DrawState getNewState(int id, GenericEntity entity) {
		if (id == PlayerDrawState.ordinal()) { return new PlayerDrawState(entity); }
		if (id == ZombieDrawState.ordinal()) { return new ZombieDrawState(entity); }
		if (id == ProjectileLiveDrawState.ordinal()) { return new ProjectileLiveDrawState(entity); }
		if (id == ProjectileDyingDrawState.ordinal()) { return new ProjectileDyingDrawState(entity); }
		if (id == NullDrawState.ordinal()) { return null; }
		return new UnknownDrawState(entity);
	}
}
