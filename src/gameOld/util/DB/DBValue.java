package gameOld.util.DB;

public class DBValue {
	private String name;
	private DBValueType type;
	public DBValue(DBValueType type, String name) {
		this.name = name;
		this.type = type;
	}
	public DBValueType Type() {
		return type;
	}
	public String Name() {
		return name;
	}
}
