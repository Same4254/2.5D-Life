package Entity.WorldObjects.Lot;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Main.Handler;
import World.Tiles.Tile;

public class Floor {
	private Handler handler;
	private Lot lot;
	private Tile[][] tiles;
	
	private Vector3f position;
	private Vector2f dimensions;
	
	public Floor(Handler handler, Lot lot, Vector3f position, Vector2f dimensions) {
		this.handler = handler;
		this.lot = lot;
		this.position = position;
		this.dimensions = dimensions;
		
		tiles = new Tile[(int) dimensions.x * 2][(int) dimensions.y * 2];
	}
	
	public void update(float delta) {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			if(tile != null)
				tile.update(delta);
	}
	
	public void render() {
		for(Tile[] t : tiles)
		for(Tile tile : t)
			if(tile != null)
				tile.render(Lot.tileInstanceModel);
	}
	
	public Tile getTile(Vector2f position) { return tiles[(int) (position.x * 2)][(int) (position.y * 2)]; }
	public Lot getLot() { return lot; }
	public Tile[][] getTiles() { return tiles; }
	public Vector3f getPosition() { return position; }
	public Vector2f getDimensions() { return dimensions; }
}
