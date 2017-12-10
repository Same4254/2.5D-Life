package Entity.WorldObjects.Lot;

import com.Engine.Util.Vectors.Vector2f;

import Main.Assets;
import Main.Handler;
import World.Tiles.Tile;
import World.Tiles.Render.TileInstanceModel;

public class Lot {
	private Handler handler;
	private Tile[][] tiles;
	private Vector2f position;
	private int width, height;
	
	private EditMode editMode;
	
	public Lot(Handler handler, Vector2f position, int width, int height) {
		this.handler = handler;
		this.position = position;
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
		
		for(int x = 0; x < tiles.length; x++) {
		for(int y = 0; y < tiles[x].length; y++) {
			tiles[x][y] = new Tile(handler, this, new Vector2f(x, y), Assets.tileModel, Assets.goldTexture);
		}}
		
		editMode = new EditMode(handler, this);
//		tiles[0][0].add(new Table(new Vector2f(), new Vector2f(1), "Table", handler.getWorld().getShader()));
	}
	
	public void update(float delta) {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			tile.update();
		
		editMode.update(delta);
	}
	
	TileInstanceModel model = new TileInstanceModel();
	
	public void render() {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			tile.render(model);
		
		editMode.render();
	}
	
	public void enableEdit() { editMode.setEnabled(true); }
	public void disableEdit() { editMode.setEnabled(false); }
	
	public EditMode getEditMode() { return editMode; }
	
	public Tile[][] getTiles() { return tiles; }
	public Vector2f getPosition() { return position; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}