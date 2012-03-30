package game.util.IO.Packages;

import java.io.Serializable;

public class LoginInfoPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2753081184039267914L;

	public final String username;
	public final int passwordHash;

	public LoginInfoPackage(String username, int passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}
}
