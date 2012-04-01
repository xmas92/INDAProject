package game.util.IO.Event;


import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.GameContainer;

public class EventQueue {
	
	LinkedList<Event> queue = new LinkedList<>();
	Iterator<Event> it;
	
	public void Update(GameContainer gc) {
		
		it = queue.iterator();
	}
	
	public void flush() {
		queue.clear();
	}
	public Event poll() {
		return (it==null)?null:(it.hasNext())?it.next():null;
	}

}
