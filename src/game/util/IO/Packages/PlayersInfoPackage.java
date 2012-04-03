package game.util.IO.Packages;

import java.util.ArrayList;

import game.client.Entity.CharacterInfo;

public class PlayersInfoPackage implements Package {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2983310511998961910L;
	public final ArrayList<CharacterInfo> cis;
	public final ArrayList<String> playerIDs;
	private final PackageFlag flag;
	
	@SuppressWarnings("unchecked")
	public PlayersInfoPackage(ArrayList<CharacterInfo> cis, ArrayList<String> playerIDs, PackageFlag flag) {
		this.cis = new ArrayList<CharacterInfo>();
		for (int i = 0; i < cis.size(); i++) {
			this.cis.add(cis.get(i).clone());
		}
		this.playerIDs = (ArrayList<String>) playerIDs.clone();
		this.flag = flag;
	}
	@Override
	public PackageFlag Flag() {
		// TODO Auto-generated method stub
		return flag;
	}

	@Override
	public PackageType Type() {
		// TODO Auto-generated method stub
		return PackageType.PlayersInfoPackage;
	}

}
