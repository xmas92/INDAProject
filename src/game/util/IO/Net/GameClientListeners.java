package game.util.IO.Net;

import game.client.Entity.Character;
import game.client.Entity.ProjectileSpell;
import game.client.Game.MainGame;
import static game.client.Game.MainGame.*;
import game.util.IO.Net.Network.CastProjectileSpell;
import game.util.IO.Net.Network.CharacterInfo;
import game.util.IO.Net.Network.RemovePlayer;
import game.util.IO.Net.Network.UpdatePlayer;

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
						player.setCharacterInfo(up.playerInfo.characterInfo);
						return;
            		} else if (!players.containsKey(up.playerInfo.player)) {
            			players.put(up.playerInfo.player, new Character(up.playerInfo.characterInfo));
            		} else {
            			CharacterInfo ci = players.get(up.playerInfo.player).getCharacterInfo();
            			if (ci.deltaX != up.playerInfo.characterInfo.deltaX ||
            				ci.deltaY != up.playerInfo.characterInfo.deltaY)
            			players.get(up.playerInfo.player).setCharacterInfo(up.playerInfo.characterInfo);
            		}
            	}
            	if (object instanceof RemovePlayer) {
            		players.remove(((RemovePlayer)object).username);
            	}

        		if (object instanceof CastProjectileSpell) {
        			ProjectileSpell s = new ProjectileSpell(true);
        			s.setProjectileSpellInfo(((CastProjectileSpell)object).psi);
        			MainGame.spells.add(s);
        		}
            }
            public void disconnected (Connection connection) {
            }
		}));
	}
}
