package game.DrawState.States;

import org.newdawn.slick.Image;

import game.DrawState.DrawState;
import game.Entity.GenericEntity;
import game.Resources.ResourceManager;
import game.Screen.GameScreen;

public abstract class SingleImage extends DrawState {
	
	private Image graphic;
	
	public SingleImage(GenericEntity entity, String image) {
		super(entity);
		graphic = ResourceManager.Manager().getImage(image);
	}

	@Override
	public void Draw() {
		if (graphic != null) {
			int drawX = (int)(entity.x - GameScreen.player.position().getX() + (GameScreen.w - entity.w) * 0.5f),
				drawY = (int)(entity.y - GameScreen.player.position().getY() + (GameScreen.h - entity.h) * 0.5f);
			if (drawX > -GameScreen.w && drawX < GameScreen.w * 2 &&
				drawY > -GameScreen.h && drawY < GameScreen.w * 2) {
				graphic.draw(drawX, drawY, entity.w, entity.h);
			}
		}
	}

}
