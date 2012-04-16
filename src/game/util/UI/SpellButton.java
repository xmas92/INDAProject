package game.util.UI;

import game.client.Entity.Spell.ProjectileSpell;
import game.client.Entity.Spell.Spell;
import game.client.Game.MainGame;
import game.util.IO.Event.Event;
import game.util.IO.Event.EventListner;
import game.util.IO.Event.KeyEvent;
import game.util.IO.Net.Network.CastProjectileSpell;

public class SpellButton extends BasicUIComponent {

	private Spell spell;
	public int key;
	
	public SpellButton(Spell spell) {
		this.spell = spell;
		setIgnoreFocus(true);
		setupEventListners();
	}

	private void setupEventListners() {
		super.addKeyDownEventListner(new EventListner() {
			@Override
			public void Invoke(Object sender, Event e) {
				if (spell.isReady() && ((KeyEvent)e).Key.VALUE == key) {
					Spell s = spell.castSpell();
					CastProjectileSpell cps = new CastProjectileSpell();
					cps.entityInfo = ((ProjectileSpell)s).getEntityInfo();
					cps.type = ((ProjectileSpell)s).type;
					cps.id = ((ProjectileSpell)s).getId();
					MainGame.client.sendTCP(cps);
					MainGame.spells.add(s);
				}
			}
		});
	}
	
}
