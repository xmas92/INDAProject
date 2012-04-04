package game.util.IO.Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Packagble {
	void readFromStream(DataInputStream dis);
	void writeToStream(DataOutputStream dos);
}
