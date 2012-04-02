package game.util.IO.Net;

import java.util.LinkedList;

public class NetIOQueue {
	private LinkedList<Package> InQueue, OutQueue;
	private int pollCount = -1;
	public NetIOQueue() {
		InQueue = new LinkedList<Package>();
		OutQueue = new LinkedList<Package>();
	}
	
	public synchronized void addInPackage(Package pkg) {
		InQueue.add(pkg);
	}	
	public synchronized void addOutPackage(Package pkg) {
		OutQueue.add(pkg);
	}
	
	public synchronized Package pollInPackage() {
		if (pollCount < 0) {
			pollCount = InQueue.size();
			return null;
		}
		pollCount--;
		return InQueue.poll();
	}
	public synchronized Package pollOutPackage() {
		return OutQueue.poll();
	}
}
