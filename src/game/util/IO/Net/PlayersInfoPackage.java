package game.util.IO.Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import game.client.Entity.CharacterInfo;

public class PlayersInfoPackage implements Package, Packagble {
	
	public final ArrayList<CharacterInfo> cis;
	public final ArrayList<String> playerIDs;
	private final char flag;
	
	public PlayersInfoPackage(char flag) {
		this.cis = new ArrayList<CharacterInfo>();
		this.playerIDs = new ArrayList<String>();
		this.flag = flag;
	}
	
	@SuppressWarnings("unchecked")
	public PlayersInfoPackage(ArrayList<CharacterInfo> cis, ArrayList<String> playerIDs, char flag) {
		this.cis = new ArrayList<CharacterInfo>();
		for (int i = 0; i < cis.size(); i++) {
			this.cis.add(cis.get(i).clone());
		}
		this.playerIDs = (ArrayList<String>) playerIDs.clone();
		this.flag = flag;
	}
	@Override
	public char Flag() {
		return flag;
	}

	@Override
	public char Type() {
		return Types.PlayersInfoPackage;
	}
	@Override
	public void readFromStream(DataInputStream dis) {
		try {
			while (dis.readBoolean()) {
				CharacterInfo ci = new CharacterInfo();
				ci.readFromStream(dis);
				cis.add(ci);
				playerIDs.add(dis.readUTF());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void writeToStream(DataOutputStream dos) {
		try {
			for (int i = 0; i < cis.size(); i++) {
				dos.writeBoolean(true);
				cis.get(i).writeToStream(dos);
				dos.writeUTF(playerIDs.get(i));
			}
			dos.writeBoolean(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Packagble packload() {
		return this;
	}

}
