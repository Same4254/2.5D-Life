package Entity.WorldObjects.Lot;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Main.Handler;
import World.Tiles.Tile;
import World.Tiles.Render.TileInstanceModel;

public class Floor {
	private TileInstanceModel tileModel;
	private Tile[][] tiles;
	private Vector3f position;
	private Vector2f dimensions;
	
	public Floor(Handler handler, TileInstanceModel tileModel, Vector3f position, Vector2f dimensions) {
		this.tileModel = tileModel;
		this.position = position;
		this.dimensions = dimensions;
		
		tiles = new Tile[(int) dimensions.x][(int) dimensions.y];
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
				tile.render(tileModel);
	}
	
	public Tile[][] getTiles() { return tiles; }
	public Vector3f getPosition() { return position; }
	public Vector2f getDimensions() { return dimensions; }
}
