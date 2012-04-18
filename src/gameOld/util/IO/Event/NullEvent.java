package gameOld.util.IO.Event;



public class NullEvent implements Event {

	@Override
	public EventType Type() {
		return EventType.NullEvent;
	}

}
