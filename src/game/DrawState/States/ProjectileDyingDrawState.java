package game.DrawState.States;

import org.newdawn.slick.Color;

import game.Entity.GenericEntity;
import game.Screen.GameScreen;

public class ProjectileDyingDrawState extends SingleImage {

	public ProjectileDyingDrawState(GenericEntity entity) {
		super(entity, "");
	}

	@Override
	public void Draw() {
		if (graphic != null) {
			int drawX = (int)(entity.x - GameScreen.player.position().getX() + (GameScreen.w - entity.w) * 0.5f),
				drawY = (int)(entity.y - GameScreen.player.position().getY() + (GameScreen.h - entity.h) * 0.5f);
			if (drawX > -GameScreen.w && drawX < GameScreen.w * 2 &&
				drawY > -GameScreen.h && drawY < GameScreen.w * 2) {
				graphic.draw(drawX, drawY, entity.w, entity.h, Color.pink);
			}
		}
	}

}