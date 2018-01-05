package Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Engine.RenderEngine.Textures.Texture2D;

import Audio.Sound;

public class AssetLoader {
	public static final String MODEL_PATH = "res/models/";
	public static final String TEXTURE_PATH = "/textures/";
	public static final String STRUCTURE_PATH = "/structures/";
	public static final String SOUND_PATH = "sounds/";
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(AssetLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int loadSoundBuffer(String wav) { return Sound.loadSound(wav); }
	public static Texture2D	loadTexture(String path) { return new Texture2D(loadImage(path)); }
}
