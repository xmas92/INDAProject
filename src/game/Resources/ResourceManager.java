package game.Resources;

import game.Client;
import game.Model;
import game.Zones.ZoneMap;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ResourceManager implements Model {
	private HashMap<String, Image> images;
	private HashMap<String, ZoneMap> maps;
	private Image NOIMAGE;
	private static ResourceManager self;
	
	public static ResourceManager Manager() {
		if (self == null)
			self = new ResourceManager();
		return self;
	}
	
	private ResourceManager () {
		images = new HashMap<String, Image>();
		maps = new HashMap<String, ZoneMap>();
		if (Client.Game == null) return;
		try {
			NOIMAGE = new Image(System.getProperty("user.dir") + File.separator + "data" + File.separator + "noimage.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void Initialize() {
		loadImages();
		loadMaps();
	}
	private void loadImages() {
		if (Client.Game == null) return;
		File root = new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "images");
		loadImages(root, "");
	}
	private void loadImages(File root, String path) {
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				loadImages(file, path + file.getName() + ":");
			else {
				try {
					images.put(path + file.getName(), new Image(file.getPath()));
				} catch (Exception e) {
					System.err.println("Could not load image: " + file.getPath());
				}
			}
		}
	}
	private void loadMaps() {
		File root = new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps");
		loadMaps(root, "");
	}
	private void loadMaps(File root, String path) {
		boolean loadIMG = (Client.Game != null);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				loadMaps(file, path + file.getName() + ":");
			else {
				if (file.getName().endsWith(".tmx")) {
					try {
						maps.put(path + file.getName(), new ZoneMap(file.getPath(), loadIMG));
					} catch (Exception e) {
						System.err.println("Could not load map: " + file.getPath());
						e.printStackTrace();
					}
				}
			}
		}
	}

	public ZoneMap getMap(String map) {
		return maps.get(map);
	}
	
	public Image getImage(String image) {
		Image img = images.get(image);
		return (img!=null)?img:NOIMAGE;
	}
}
