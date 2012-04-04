package game.util.IO.Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import game.client.Entity.CharacterInfo;

public class PlayerInfoPackage implements Package , Packagble{
	
	public final CharacterInfo ci;
	public String playerID;
	private final char flag;
	
	public PlayerInfoPackage(CharacterInfo ci, String playerID, char flag) {
		this.playerID = playerID;
		this.ci = ci.clone();
		this.flag = flag;
	}
	
	@Override
	public char Flag() {
		return flag;
	}

	@Override
	public char Type() {
		return Types.PlayerInfoPackage;
	}

	@Override
	public Packagble packload() {
		return this;
	}

	@Override
	public void readFromStream(DataInputStream dis) {
		try {
			ci.readFromStream(dis);
			playerID = dis.readUTF();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeToStream(DataOutputStream dos) {
		try {
			ci.writeToStream(dos);
			dos.writeUTF(playerID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
