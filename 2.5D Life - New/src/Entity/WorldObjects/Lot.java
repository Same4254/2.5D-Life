package Entity.WorldObjects;

import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Main.Handler;
import Utils.ImageLoader;
import World.Tiles.Tile;

public class Lot {
	private Handler handler;
	private Tile[][] tiles;
	private Vector2f position;
	private int width, height;
	
	public Lot(Handler handler, Vector2f position, int width, int height) {
		this.handler = handler;
		this.position = position;
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
		
		for(int x = 0; x < tiles.length; x++) {
		for(int y = 0; y < tiles[x].length; y++) {
			tiles[x][y] = new Tile(handler, this, new Vector2f(x, y), ImageLoader.MODEL_PATH + "Tile.obj", ImageLoader.TEXTURE_PATH + "Gold.png", handler.getWorld().getShader());
		}}
		
//		tiles[0][0].add(new Table(new Vector2f(), new Vector2f(1), "Table", handler.getWorld().getShader()));
	}
	
	public void update() {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			tile.update();
	}
	
	public void render(Camera camera) {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			tile.render(camera);
	}
	
	public Tile[][] getTiles() { return tiles; }
	public Vector2f getPosition() { return position; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
