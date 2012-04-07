package game.client.Entity;

public class ProjectileSpell implements Spell {
	
	private int cooldown, timeSinceLastCast;
	private final boolean cast;
	
	public ProjectileSpell() {
		cast = false;
	}
	public ProjectileSpell(boolean cast) {
		this.cast = cast;
	}
	
	@Override
	public boolean isReady() {
		return (timeSinceLastCast > cooldown);
	}

	@Override
	public Spell castSpell() {
		// TODO Auto-generated method stub
		timeSinceLastCast = 0;
		return null;
	}

	@Override
	public void update(int delta) {
		timeSinceLastCast += delta;
	}

	@Override
	public void draw(float x, float y) {
		if (cast) {
			
		}
	}

}
