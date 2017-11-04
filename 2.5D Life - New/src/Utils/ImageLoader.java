package Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Engine.RenderEngine.Textures.Texture2D;

public class ImageLoader {
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Texture2D	loadTexture(String path) {
		return new Texture2D(loadImage(path));
	}
}
