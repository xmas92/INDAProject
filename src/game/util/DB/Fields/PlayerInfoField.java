package game.util.DB.Fields;

import game.util.DB.DBValue;
import game.util.IO.Net.Network.CharacterInfo;

public class PlayerInfoField implements DBField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8219137319516891347L;
	
	public String playerID;
	public CharacterInfo ci;
	
	public PlayerInfoField(CharacterInfo ci, String playerID) {
		this.playerID = playerID;
		this.ci = ci.clone();
	}

	@Override
	public DBField create() {
		return new PlayerInfoField(ci, playerID);
	}

	@Override
	public boolean contains(DBValue[] where, Object[] values) {
		if (where == null || values == null)
			return true;
		if (where.length > 2 || values.length > 2)
			return false;
		if (where.length == 0) return true;
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o instanceof PlayerInfoField) {
			if (((PlayerInfoField)o).playerID != null)
				return ((PlayerInfoField)o).playerID.equals(playerID);
		}
		return false;
	}

}
