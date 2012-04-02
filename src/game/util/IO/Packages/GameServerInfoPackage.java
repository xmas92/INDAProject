package game.util.IO.Packages;

public class GameServerInfoPackage implements Package {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9037099160598170800L;

	public final String ip;
	public final int port;
	public PackageFlag flag;

	public GameServerInfoPackage(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.flag = PackageFlag.unknown;
	}

	@Override
	public PackageFlag Flag() {
		return flag;
	}

	@Override
	public PackageType Type() {
		return PackageType.GameServerInfoPackage;
	}
}
