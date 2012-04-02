package game.util.IO.Packages;

import java.io.Serializable;

public interface Package extends Serializable{
	PackageFlag Flag();
	PackageType Type();
}
