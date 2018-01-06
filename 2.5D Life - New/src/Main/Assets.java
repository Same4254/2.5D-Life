package Main;

import java.awt.image.BufferedImage;

import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Textures.Texture2D;

import Audio.Sound;
import Entity.WrapperBodies.WrapperModel;
import Utils.AssetLoader;

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
	
	public static WrapperModel computerDeskModel;
	public static Texture2D computerDeskTexture;
	
	public static WrapperModel computerModel;
	public static Texture2D computerTexture;
	
	public static WrapperModel tileModel;
	public static Texture2D sampleFloorTextures;

	public static int bounceSoundBuffer;
	
	public static BufferedImage house, maze;
	
	public static enum TileTextureIndecies {
		GRASS(0), CRACKED_STONE_BRICK(1), COBBLESTONE(2), STONE_BRICK(3), ORANGE_STONE(4), PURPLE_STONE(5), WHITE_BRICK(6);
		
		private int num;
		
		private TileTextureIndecies(int num) {
			this.num = num;
		}
		
		public int getValue() { return num; }
	};
	
	public static void init() {
		house = AssetLoader.loadImage(AssetLoader.STRUCTURE_PATH + "House.png");
		maze = AssetLoader.loadImage(AssetLoader.STRUCTURE_PATH + "Maze.png");
		
		bounceSoundBuffer = Sound.loadSound(AssetLoader.SOUND_PATH + "bounce.wav");
		
		defaultShader = new DefaultShader();
//		defaultShader.getRenderer().usingFrustumCulling(false);
		
		stoveModel = new WrapperModel(AssetLoader.MODEL_PATH + "Stove.obj", defaultShader);
		stoveTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Stove.png");
		
		fridgeModel = new WrapperModel(AssetLoader.MODEL_PATH + "Fridge.obj", defaultShader);
		fridgeTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Fridge.png");
		
		computerModel = new WrapperModel(AssetLoader.MODEL_PATH + "Computer.obj", defaultShader);
		computerTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Computer.png");
		
		chairModel = new WrapperModel(AssetLoader.MODEL_PATH + "Chair.obj", defaultShader);
		chairTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Chair.png");
		
		tvModel = new WrapperModel(AssetLoader.MODEL_PATH + "TV.obj", defaultShader);
		tvTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "TV.png");
		
		computerDeskModel = new WrapperModel(AssetLoader.MODEL_PATH + "ComputerDesk.obj", defaultShader);
		computerDeskTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "ComputerDesk.png");
		
		bedModel = new WrapperModel(AssetLoader.MODEL_PATH + "Bed.obj", defaultShader);
		bedTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Bed.png");
		
		playerModel = new WrapperModel(AssetLoader.MODEL_PATH + "Cube Person.obj", defaultShader);
		playerTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Cube Person.png");
		
		tileModel = new WrapperModel(AssetLoader.MODEL_PATH + "Tile.obj", defaultShader);
		sampleFloorTextures = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "SampleFloorTexture.png");
		sampleFloorTextures.setNumberOfRows(3);
		
		wallModel = new WrapperModel(AssetLoader.MODEL_PATH + "Wall.obj", defaultShader);
		wallTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Wall.png");
		
		tableModel = new WrapperModel(AssetLoader.MODEL_PATH + "Table.obj", defaultShader);
		tableTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Table.png");
		
//		boxModel = new WrapperModel(ImageLoader.MODEL_PATH + "Physics/VectorTop.obj", defaultShader);
		boxModel = new WrapperModel(AssetLoader.MODEL_PATH + "Box.obj", defaultShader);
		boxTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Box.png");
	}
}
