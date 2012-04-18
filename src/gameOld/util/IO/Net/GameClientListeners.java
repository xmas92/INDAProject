package gameOld.util.IO.Net;

import java.util.Iterator;

import static gameOld.client.Game.MainGame.*;
import gameOld.client.Entity.Character;
import gameOld.client.Entity.Spell.ProjectileSpell;
import gameOld.client.Entity.Spell.Spell;
import gameOld.client.Game.MainGame;
import gameOld.util.IO.Net.Network.CastProjectileSpell;
import gameOld.util.IO.Net.Network.EntityInfo;
import gameOld.util.IO.Net.Network.KillSpell;
import gameOld.util.IO.Net.Network.RemovePlayer;
import gameOld.util.IO.Net.Network.UpdatePlayer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class GameClientListeners {
	public static void createListeners() {
		client.addListener(new ThreadedListener(new Listener() {
            public void connected (Connection connection) {
            }
            
            public void received (Connection connection, Object object) {
            	if (object instanceof UpdatePlayer) {
            		UpdatePlayer up = (UpdatePlayer)object;
            		if (up.playerInfo.player == null)
            			return;
            		if (up.playerInfo.player.equals(player.getPlayerID())) {
						player.setEntityInfo(up.playerInfo.entityInfo);
						return;
            		} else if (!players.containsKey(up.playerInfo.player)) {
            			players.put(up.playerInfo.player, new Character(up.playerInfo.entityInfo));
            		} else {
            			EntityInfo ci = players.get(up.playerInfo.player).getEntityInfo();
            			if (ci.deltaX != up.playerInfo.entityInfo.deltaX ||
            				ci.deltaY != up.playerInfo.entityInfo.deltaY)
            			players.get(up.playerInfo.player).setEntityInfo(up.playerInfo.entityInfo);
            		}
            	}
            	if (object instanceof RemovePlayer) {
            		players.remove(((RemovePlayer)object).username);
            	}
        		if (object instanceof CastProjectileSpell) {
        			ProjectileSpell s = new ProjectileSpell(((CastProjectileSpell)object).type, true, ((CastProjectileSpell)object).id);
        			s.setProjectileSpellInfo(((CastProjectileSpell)object).entityInfo);
        			MainGame.spells.add(s);
        		}
        		if (object instanceof KillSpell) {
        			synchronized (spells) {
        				Iterator<Spell> it = spells.iterator();
        				while (it.hasNext()) {
        					Spell s = it.next();
        					if (s.getId() == ((KillSpell)object).id)
        						s.die();
        				}
        			}
        		}
            }
            public void disconnected (Connection connection) {
            }
		}));
	}
}
