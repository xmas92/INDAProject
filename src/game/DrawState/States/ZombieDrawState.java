package game.DrawState.States;

import game.Client;
import game.Entity.GenericEntity;
import game.Geometry.Rectangle;
import game.Screen.GameScreen;

public class ZombieDrawState extends SingleImage {

	public ZombieDrawState(GenericEntity entity) {
		super(entity, "GameAssets:zombie.bmp");
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
			Rectangle rec = entity.collisionBox();
			Client.Game.getGraphics().drawRect(rec.x- (float)GameScreen.player.position().getX() + (GameScreen.w * 0.5f), 
											   rec.y- (float)GameScreen.player.position().getY() + (GameScreen.h * 0.5f),
											   rec.width, rec.height);
		}
	}
	
}
