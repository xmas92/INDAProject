package game.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class UserDB implements Database {

	private static final String file = "Users.db";

	private static HashMap<String, Integer> database;
	private static boolean loaded = false;
	
	public static synchronized boolean AddUser(String user, int pass) {
		if (!loaded)
			load();
		if (database.containsKey(user)) {
			return false;
		}
		return (database.put(user, pass) == null);
	}
	
	public static synchronized boolean UpdateUser(String user, int pass) {
		if (!loaded)
			load();
		if (database.containsKey(user)) {
			return (database.put(user, pass) != null);
		}
		return false;
	}
	
	public static synchronized boolean ContainsUser(String user) {
		return database.containsKey(user);
	}
	
	public static synchronized boolean ValidUser(String user, int pass) {
		if (!loaded)
			load();
		return (new Integer(pass)).equals(database.get(user));
		
	}
	
	public static synchronized void PrintUsers() {
		if (!loaded)
			load();
		for (String user : database.keySet()) {
			System.out.println(user);
		}
	}
	
	
	public static void Save() {
		if (database == null) return;
		try {
			File f = new File(file);
			if (f.exists()) {
				f.renameTo(new File(file + ".tmp"));
			}
			f = new File(file);
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(database);
			oos.close();
		} catch (IOException e) {
			File f = new File(file);
			if (f.exists()) {
				f.delete();
			}
			f = new File(file + ".tmp");
			if (f.exists()) {
				f.renameTo(new File(file));
			}
			e.printStackTrace();
		} finally {
			File f = new File(file + ".tmp");
			if (f.exists()) {
				f.delete();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void load() {
		if (!loaded) {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				database = (HashMap<String, Integer>)ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				database = new HashMap<String, Integer>();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (database == null) {
					File f = new File(file);
					if (f.exists()) {
						f.renameTo(new File(file + ".backup-" + System.currentTimeMillis()));
					}
					database = new HashMap<String, Integer>();
				}
				loaded = true;
			}
		}
	}
	
}
