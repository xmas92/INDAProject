package game.Event;

public class CloseEvent implements Event {
	private boolean close = true;
	public void DoNotClose() {
		close = false;
	}
	public boolean Close() {
		return close;
	}
}
