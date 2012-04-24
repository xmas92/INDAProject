package game.DrawState.States;

import game.Entity.GenericEntity;

public class PlayerDrawState extends SingleImage {

	public PlayerDrawState(GenericEntity entity) {
		super(entity, "GameAssets:Player:player.bmp");
	}

}
