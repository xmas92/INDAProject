package game.UpdateState.States;

import game.Entity.GenericEntity;
import game.UpdateState.UpdateState;

public class ProjectileSpellUpdateState extends UpdateState {

	public ProjectileSpellUpdateState(GenericEntity entity) {
		super(entity);
	}

	@Override
	public void Update(int delta) {
		if (Math.abs(entity.deltaX)+Math.abs(entity.deltaY) != 0) {
			float movement = (entity.speed * delta / 1000.0f) / (float)Math.sqrt(Math.abs(entity.deltaX)+Math.abs(entity.deltaY));
			entity.x += entity.deltaX * movement;
			entity.y += entity.deltaY * movement;
		} 
	}

}
