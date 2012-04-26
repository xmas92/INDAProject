package game.Entity;

public enum GEType {
	UnknownType,
	Player,
	OtherPlayer,
	ProjectileSpell,
	;
	
	public static GEType getType(int i) {
		for (GEType type : GEType.values()) {
			if (i == type.ordinal())
				return type;
		}
		return UnknownType;
	}
}
