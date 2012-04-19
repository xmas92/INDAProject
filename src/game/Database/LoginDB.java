package game.Database;

import java.util.HashSet;

public class LoginDB implements Database {

	private static HashSet<String> database = new HashSet<String>();
	
	public static boolean Login(String user) {
		return database.add(user);
	}
	
	public static boolean LoggedIn(String user) {
		return database.contains(user);
	}
	
	public static boolean Logout(String user) {
		return database.remove(user);
	}
	
}
