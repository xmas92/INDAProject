package gameOld.util.UI;

import gameOld.client.Entity.Spell.ProjectileSpell;
import gameOld.client.Entity.Spell.Spell;
import gameOld.client.Game.MainGame;
import gameOld.util.IO.Event.Event;
import gameOld.util.IO.Event.EventListner;
import gameOld.util.IO.Event.KeyEvent;
import gameOld.util.IO.Net.Network.CastProjectileSpell;

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
