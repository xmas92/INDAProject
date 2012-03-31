package game.util.IO.Net;

import java.io.Serializable;

public class AcknowledgeFlag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8241511238000982199L;
	public final AcknowledgeFlags flag;
	public AcknowledgeFlag(AcknowledgeFlags flag) {
		this.flag = flag;
	}
}
