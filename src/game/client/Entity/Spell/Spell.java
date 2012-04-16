package game.client.Entity.Spell;

public interface Spell {
	boolean isReady();
	Spell castSpell();
	void update(int delta);
	void draw(float x, float y);
	boolean isDead();
	void die();
}
