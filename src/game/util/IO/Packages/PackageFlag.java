package game.util.IO.Packages;

public enum PackageFlag {
	// Acknowledgment
	pokeback,
	loginGranted,
	loginRefused,
	closeConnectionAcknowledged,
	playersInfoAcknowledged,
	
	// Requests
	poke,
	loginRequest,
	closeConnectionRequest,
	playersInfoRequest,
	
	// Unknown
	unknown,
}
