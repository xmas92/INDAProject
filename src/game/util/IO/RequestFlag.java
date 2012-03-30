package game.util.IO;

import java.io.Serializable;

public class RequestFlag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1811696549325743546L;
	
	public final RequestFlags flag;
	public RequestFlag(RequestFlags flag) {
		this.flag = flag;
	}
}
