package gameOld.util.DB.Fields;

import java.io.Serializable;

import gameOld.util.DB.DBValue;

public interface DBField extends Serializable{
	DBField create();
	boolean contains(DBValue[] where, Object[] values);
}
