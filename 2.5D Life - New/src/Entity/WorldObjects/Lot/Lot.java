package Entity.WorldObjects.Lot;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.Lot.Edit.EditMode;
import Entity.WorldObjects.Lot.Edit.LotEdit;
import Main.Handler;
import World.Tiles.Tile;
import World.Tiles.Render.TileInstanceModel;

public class Lot {
	private Tile[][] tiles;
	private Vector2f position;
	private Vector2f dimensions;

	private TileInstanceModel tileInstanceModel;
	private LotEdit lotEdit;
	
	public Lot(Handler handler, Vector2f position, Vector2f dimensions) {
		this.position = position;
		this.dimensions = dimensions;
		
		tiles = new Tile[(int) dimensions.x][(int) dimensions.y];
		
		for(int x = 0; x < tiles.length; x++) {
		for(int y = 0; y < tiles[x].length; y++) {
			tiles[x][y] = new Tile(handler, this, new Vector2f(x, y));
		}}
		
		tileInstanceModel = new TileInstanceModel();
		lotEdit = new LotEdit(handler, this);
	}
	
	public void update(float delta) {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			tile.update(delta);
		
		lotEdit.update(delta);
	}
	
	public void render() {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			tile.render(tileInstanceModel);
		
		lotEdit.render();
	}
	
	public void enableEdit() { lotEdit.setEnabled(true); }
	public void disableEdit() { lotEdit.setEnabled(false); }
	
	public LotEdit getEditMode() { return lotEdit; }
	
	public Tile[][] getTiles() { return tiles; }
	public Vector2f getPosition() { return position; }
	public Vector2f getDimensions() { return dimensions; }
	public int getWidth() { return (int) dimensions.x; }
	public int getHeight() { return (int) dimensions.y; }
}