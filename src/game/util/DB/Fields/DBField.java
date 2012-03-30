package game.util.DB.Fields;

import game.util.DB.DBValue;

public interface DBField {
	DBField create();
	boolean contains(DBValue[] where, Object[] values);
}
