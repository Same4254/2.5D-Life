package Main;

import java.awt.image.BufferedImage;

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
	
	public static WrapperModel bedModel;
	public static Texture2D bedTexture;
	
	public static WrapperModel fridgeModel;
	public static Texture2D fridgeTexture;
	
	public static WrapperModel chairModel;
	public static Texture2D chairTexture;
	
	public static WrapperModel tvModel;
	public static Texture2D tvTexture;
	
	public static WrapperModel stoveModel;
	public static Texture2D stoveTexture;
	
	public static WrapperModel tileModel;
	public static Texture2D sampleFloorTextures;

	public static enum TileTextureIndecies {
		WHITE_BRICK(0), CRACKED_STONE_BRICK(1), COBBLESTONE(2), STONE_BRICK(3), ORANGE_STONE(4), PURPLE_STONE(5), HARDWOOD(6);
		
		private int num;
		
		private TileTextureIndecies(int num) {
			this.num = num;
		}
		
		public int getValue() { return num; }
	};
	
	public static BufferedImage house, maze;
	
	public static void init() {
		house = ImageLoader.loadImage(ImageLoader.STRUCTURE_PATH + "House.png");
		maze = ImageLoader.loadImage(ImageLoader.STRUCTURE_PATH + "Maze.png");
		
		defaultShader = new DefaultShader();
//		defaultShader.getRenderer().usingFrustumCulling(false);
		
		stoveModel = new WrapperModel(ImageLoader.MODEL_PATH + "Stove.obj", defaultShader);
		stoveTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Stove.png");
		
		fridgeModel = new WrapperModel(ImageLoader.MODEL_PATH + "Fridge.obj", defaultShader);
		fridgeTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Fridge.png");
		
		chairModel = new WrapperModel(ImageLoader.MODEL_PATH + "Chair.obj", defaultShader);
		chairTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Chair.png");
		
		tvModel = new WrapperModel(ImageLoader.MODEL_PATH + "TV.obj", defaultShader);
		tvTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "TV.png");
		
		bedModel = new WrapperModel(ImageLoader.MODEL_PATH + "Bed.obj", defaultShader);
		bedTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Bed.png");
		
		playerModel = new WrapperModel(ImageLoader.MODEL_PATH + "Cube Person.obj", defaultShader);
		playerTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Cube Person.png");
		
		tileModel = new WrapperModel(ImageLoader.MODEL_PATH + "Tile.obj", defaultShader);
		sampleFloorTextures = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "SampleFloorTexture.png");
		sampleFloorTextures.setNumberOfRows(3);
		
		wallModel = new WrapperModel(ImageLoader.MODEL_PATH + "Wall.obj", defaultShader);
		wallTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Wall.png");
		
		tableModel = new WrapperModel(ImageLoader.MODEL_PATH + "Table.obj", defaultShader);
		tableTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Table.png");
		
//		boxModel = new WrapperModel(ImageLoader.MODEL_PATH + "Physics/VectorTop.obj", defaultShader);
		boxModel = new WrapperModel(ImageLoader.MODEL_PATH + "Box.obj", defaultShader);
		boxTexture = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Box.png");
	}
}
