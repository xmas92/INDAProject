package game.client.Entity;

public interface Spell {
	boolean isReady();
	Spell castSpell();
	void update(int delta);
	void draw(float x, float y);
}
