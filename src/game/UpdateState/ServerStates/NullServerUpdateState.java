package game.UpdateState.ServerStates;

import game.UpdateState.ServerUpdateState;

public class NullServerUpdateState extends ServerUpdateState {

	public NullServerUpdateState() {
		super(null);
	}

	@Override
	public void Update(int delta) {
		return;
	}

}
