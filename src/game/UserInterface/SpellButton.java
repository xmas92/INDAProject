package game.UserInterface;

import java.util.UUID;

import org.newdawn.slick.Input;

import game.Client;
import game.Controller.NetworkController;
import game.DrawState.DrawStates;
import game.Event.CreateClientGenericEntityEvent;
import game.Event.Event;
import game.Event.EventListner;
import game.Event.KeyEvent;
import game.Network.GameKryoReg.CastProjectileSpell;
import game.Screen.GameScreen;
import game.UpdateState.UpdateStates;
import game.Input.InputState;

public class SpellButton extends AbstractUserInterface {
	
	private int cooldown = 1500;
	private UpdateStates updateState = UpdateStates.ProjectileUpdateState;
	private DrawStates drawState = DrawStates.ProjectileLiveDrawState;
	private float speed = 256;
	private int w = 32, h = 32;
	private int key = Input.KEY_1;
	
	private long lasttimecast = 0;
	
	public SpellButton() {
		setIgnoreFocus(true);
		addKeyDownEventListner(new EventListner() {
			
			@Override
			public void Invoke(Object sender, Event e) {
				if (((KeyEvent)e).Key.VALUE != key) return;
				if (System.currentTimeMillis() - lasttimecast > cooldown) {
					lasttimecast = System.currentTimeMillis();
					CreateClientGenericEntityEvent ccgee = new CreateClientGenericEntityEvent();
					CastProjectileSpell cps = new CastProjectileSpell();
					ccgee.us = updateState; cps.updateID = updateState.ordinal();
					ccgee.ds = drawState; cps.drawID = drawState.ordinal();
					cps.x = ccgee.x = (float)GameScreen.player.position().getX();
					cps.y = ccgee.y = (float)GameScreen.player.position().getY();
					cps.w = ccgee.w = w; cps.h = ccgee.h = h;
					cps.speed = ccgee.speed = speed;
					try {
						ccgee.deltaX = InputState.Get().MouseState.getPosition().x - GameScreen.w * 0.5f;
						ccgee.deltaY = InputState.Get().MouseState.getPosition().y - GameScreen.h * 0.5f;
						float length = (float)Math.sqrt(ccgee.deltaX*ccgee.deltaX + ccgee.deltaY*ccgee.deltaY);
						ccgee.deltaX /= length;
						ccgee.deltaY /= length;
					} catch (Exception e1) {
						e1.printStackTrace();
						ccgee.deltaX = 1;
						ccgee.deltaY = 0;
					}
					cps.deltaX = ccgee.deltaX; cps.deltaY = ccgee.deltaY;
					ccgee.uuid = UUID.randomUUID();
					cps.UUIDp1 = ccgee.uuid.getLeastSignificantBits();
					cps.UUIDp2 = ccgee.uuid.getMostSignificantBits();
					Client.sendCallback(ccgee);
					NetworkController.SendTCP(cps);
				}
			}
		});
	}

}
