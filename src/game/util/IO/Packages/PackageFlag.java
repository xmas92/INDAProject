package game.util.IO.Packages;

public enum PackageFlag {
	// Acknowledgment
	pokeback,
	loginGranted,
	loginRefused,
	closeConnectionAcknowledged,
	
	// Requests
	poke,
	loginRequest,
	closeConnectionRequest,
	
	// Unknown
	unknown,
}
