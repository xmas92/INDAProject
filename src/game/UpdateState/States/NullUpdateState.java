package game.UpdateState.States;

import game.UpdateState.UpdateState;

public class NullUpdateState extends UpdateState {

	public NullUpdateState() {
		super(null);
	}

	@Override
	public void Update(int delta) {
		return;
	}

}
