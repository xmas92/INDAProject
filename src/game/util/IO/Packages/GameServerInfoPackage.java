package game.util.IO.Packages;

import java.io.Serializable;

public class GameServerInfoPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9037099160598170800L;

	public final String ip;
	public final int port;

	public GameServerInfoPackage(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
}
