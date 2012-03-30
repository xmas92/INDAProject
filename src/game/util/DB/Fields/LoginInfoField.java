package game.util.DB.Fields;

import game.util.DB.DBValue;
import game.util.DB.DBValueType;

public class LoginInfoField implements DBField {

	public String username;
	public int passwordHash;

	public LoginInfoField(String username, String password) {
		this.username = username;
		this.passwordHash = password.hashCode();
		
	}
	public LoginInfoField(String username, int passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}
	
	@Override
	public boolean contains(DBValue[] where, Object[] values) {
		if (where.length > 2 || values.length > 2)
			return false;
		if (where.length == 0) return false;
		boolean u = false, p = false;
		if (where[0].Name() == "username" && where[0].Type() == DBValueType.DBString) 
			u = (username == (String)values[0]);
		else if (where[0].Name() == "passwordHash" && where[0].Type() == DBValueType.DBInt) 
			p = (passwordHash == (int)values[0]);
		if (where.length == 1 || !(u || p)) return u || p;
		if (u) {
			if (where[1].Name() == "passwordHash" && where[0].Type() == DBValueType.DBInt) 
				p = (passwordHash == (int)values[0]);
		} else {
			if (where[1].Name() == "username" && where[0].Type() == DBValueType.DBString)
				u = (username == (String)values[0]);
		}
		return u && p;
	}

	@Override
	public DBField create() {
		return new LoginInfoField(username, passwordHash);
	}

}
