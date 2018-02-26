package Main;

import java.awt.image.BufferedImage;

import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Textures.Texture2D;

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
	
	public static WrapperModel stairModel;
	public static Texture2D stairTexture;

	public static enum TileTextureIndecies {
		GRASS(0), CRACKED_STONE_BRICK(1), COBBLESTONE(2), STONE_BRICK(3), ORANGE_STONE(4), PURPLE_STONE(5), WHITE_BRICK(6);
		
		private int num;
		
		private TileTextureIndecies(int num) {
			this.num = num;
		}
		
		public int getValue() { return num; }
	};
	
	public static void init() {
		defaultShader = new DefaultShader();
//		defaultShader.getRenderer().usingFrustumCulling(false);
		
		stairModel = new WrapperModel("Stairs", defaultShader);
		stairTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Stairs.png");
		
		stoveModel = new WrapperModel("Stove", defaultShader);
		stoveTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Stove.png");
		
		fridgeModel = new WrapperModel("Fridge", defaultShader);
		fridgeTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Fridge.png");
		
		computerModel = new WrapperModel("Computer", defaultShader);
		computerTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Computer.png");
		
		chairModel = new WrapperModel("Chair", defaultShader);
		chairTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Chair.png");
		
		tvModel = new WrapperModel("TV", defaultShader);
		tvTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "TV.png");
		
		computerDeskModel = new WrapperModel("ComputerDesk", defaultShader);
		computerDeskTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "ComputerDesk.png");
		
		bedModel = new WrapperModel("Bed", defaultShader);
		bedTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Bed.png");
		
		playerModel = new WrapperModel("CubePerson", defaultShader);
		playerTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Cube Person.png");
		
		tileModel = new WrapperModel("Tile", defaultShader);
		sampleFloorTextures = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "SampleFloorTexture.png");
		sampleFloorTextures.setNumberOfRows(3);
		
		wallModel = new WrapperModel("Wall", defaultShader);
		wallTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Wall.png");
		
		tableModel = new WrapperModel("Table", defaultShader);
		tableTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Table.png");
		
//		boxModel = new WrapperModel(ImageLoader.MODEL_PATH + "Physics/VectorTop.obj", defaultShader);
		boxModel = new WrapperModel("Box", defaultShader);
		boxTexture = AssetLoader.loadTexture(AssetLoader.TEXTURE_PATH + "Box.png");
	}
}
