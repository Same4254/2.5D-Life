package Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Engine.RenderEngine.Textures.Texture2D;

public class AssetLoader {
	public static final String MODEL_PATH = "res/Models/";
	public static final String COLLISION_PATH = "res/Collision Meshes/";
	public static final String TEXTURE_PATH = "/Textures/";
	public static final String SOUND_PATH = "Sounds/";
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(AssetLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Texture2D	loadTexture(String path) { return new Texture2D(loadImage(path)); }
}
