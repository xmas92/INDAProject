package gameOld.client.Entity.Spell;

import gameOld.client.Entity.Entity;

public interface Spell extends Entity {
	boolean isReady();
	Spell castSpell();
	void update(int delta);
	void draw(float x, float y);
	boolean isDead();
	void die();
	int getId();
}
