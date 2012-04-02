package game.util.IO.Packages;

public class EmptyPackage implements Package {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4288153444067978385L;

	public PackageFlag flag;

	public EmptyPackage() { flag = PackageFlag.unknown; }
	public EmptyPackage(PackageFlag flag) { this.flag = flag; }
	
	@Override
	public PackageFlag Flag() {
		return flag;
	}

	@Override
	public PackageType Type() {
		return PackageType.EmptyPackage;
	}

}
