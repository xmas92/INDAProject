package game.Event;

import java.util.UUID;

import game.DrawState.DrawStates;
import game.Entity.GEType;
import game.UpdateState.UpdateStates;

public class CreateClientGenericEntityEvent implements Event {
	public float x, y, deltaX, deltaY, speed;
	public int w,h;
	public UpdateStates us;
	public DrawStates ds;
	public UUID uuid;
	public GEType type;
	public int[] ignoreType;
}
