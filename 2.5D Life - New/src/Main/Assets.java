package Main;

import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Textures.Texture2D;

import Entity.WrapperBodies.WrapperModel;
import Utils.ImageLoader;

public class Assets {
	public static DefaultShader defaultShader;

	public static WrapperModel playerModel;
	public static Texture2D playerTexture;
	
	public static WrapperModel wallModel;
	public static Texture2D wallTexture;
	
	public static WrapperModel tableModel;
	public static Texture2D tableTexture;
	
	public static WrapperModel boxModel;
	public static Texture2D boxTexture;
	
	public static WrapperModel tileModel;
	public static Texture2D goldTexture;
	public static Texture2D redTexture;
	
	public static void init() {
		defaultShader = new DefaultShader();
		defaultShader.getRenderer().usingFrustumCulling(false);
		
		playerModel = new WrapperModel(ImageLoader.MODEL_PATH + "Cube Person.obj", defaultShader);
		playerTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Cube Person.png");
		
		tileModel = new WrapperModel(ImageLoader.MODEL_PATH + "Tile.obj", defaultShader);
		goldTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Gold.png");
		redTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Bullet.png");
		
		wallModel = new WrapperModel(ImageLoader.MODEL_PATH + "Wall.obj", defaultShader);
		wallTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Wall.png");
		
		tableModel = new WrapperModel(ImageLoader.MODEL_PATH + "Table.obj", defaultShader);
		tableTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Table.png");
		
		boxModel = new WrapperModel(ImageLoader.MODEL_PATH + "Box.obj", defaultShader);
		boxTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Box.png");
	}
}
