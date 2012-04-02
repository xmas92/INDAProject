package game.util.IO.Packages;

public class LoginInfoPackage implements Package {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2753081184039267914L;

	public final String username;
	public final int passwordHash;
	private PackageFlag flag;

	public LoginInfoPackage(String username, int passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.flag = PackageFlag.unknown;
	}	
	public LoginInfoPackage(String username, int passwordHash, PackageFlag flag) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.flag = flag;
	}

	@Override
	public PackageFlag Flag() {
		return flag;
	}

	@Override
	public PackageType Type() {
		return PackageType.LoginInfoPackage;
	}
}
