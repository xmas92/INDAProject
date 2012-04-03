package game.util.IO.Packages;

import game.client.Entity.CharacterInfo;

public class PlayerInfoPackage implements Package {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3288279888507917279L;
	
	public final CharacterInfo ci;
	public final String playerID;
	private final PackageFlag flag;
	
	public PlayerInfoPackage(CharacterInfo ci, String playerID, PackageFlag flag) {
		this.playerID = playerID;
		this.ci = new CharacterInfo();
		this.ci.deltaX = ci.deltaX;
		this.ci.deltaY = ci.deltaY;
		this.ci.speed = ci.speed;
		this.ci.x = ci.x;
		this.ci.y = ci.y;
		this.ci.angle = ci.angle;
		this.ci.imageID = ci.imageID;
		this.flag = flag;
	}
	
	@Override
	public PackageFlag Flag() {
		return flag;
	}

	@Override
	public PackageType Type() {
		// TODO Auto-generated method stub
		return PackageType.PlayerInfoPackage;
	}

}
