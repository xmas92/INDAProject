package game.Event;


public interface EventListner {
	/**
	 * Listens for an event from senders
	 * @param sender
	 * @param e
	 */
	void Invoke(Object sender, Event e);
}
