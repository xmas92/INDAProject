package gameOld.client.Resource;

import gameOld.client.Game.MainGame;
import gameOld.client.Map.Map;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ResourceManager {
	private HashMap<String, Image> images;
	private HashMap<String, Map> maps;
	private Image NOIMAGE;
	private static ResourceManager self;
	
	public static ResourceManager Manager() {
		if (self == null)
			self = new ResourceManager();
		return self;
	}
	
	private ResourceManager () {
		images = new HashMap<String, Image>();
		maps = new HashMap<String, Map>();
		if (MainGame.apc == null) return;
		try {
			NOIMAGE = new Image(System.getProperty("user.dir") + File.separator + "data" + File.separator + "noimage.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public void init() {
		loadImages();
		loadMaps();
	}
	private void loadImages() {
		if (MainGame.apc == null) return;
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
		boolean loadIMG = (MainGame.apc != null);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				loadMaps(file, path + file.getName() + ":");
			else {
				if (file.getName().endsWith(".tmx")) {
					try {
						maps.put(path + file.getName(), new Map(file.getPath(), loadIMG));
					} catch (Exception e) {
						System.err.println("Could not load map: " + file.getPath());
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Map getMap(String map) {
		return maps.get(map);
	}
	
	public Image getImage(String image) {
		Image img = images.get(image);
		return (img!=null)?img:NOIMAGE;
	}
}
