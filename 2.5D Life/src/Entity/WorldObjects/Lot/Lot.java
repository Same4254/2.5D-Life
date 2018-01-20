package Entity.WorldObjects.Lot;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Lot.Edit.LotEdit;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;
import World.Tiles.Render.TileInstanceModel;

public class Lot {
	public static final TileInstanceModel tileInstanceModel = new TileInstanceModel();
	public static final int floorHeight = 6, floorCount = 3; 
	
	private Floor[] floors;
	private Vector3f position;
	private Vector2f dimensions;

	private LotEdit lotEdit;
	
	public Lot(Handler handler, Vector3f position, Vector2f dimensions) {
		this.position = position;
		this.dimensions = dimensions;
		
		lotEdit = new LotEdit(handler, this);
		floors = new Floor[floorCount];
		
		for(int i = 0; i < floorCount; i++) 
			floors[i] = new Floor(handler, this, position.add(0, i * floorHeight, 0), dimensions);
	
		Tile[][] tempTiles = floors[0].getTiles();
		for(int x = 0; x < tempTiles.length; x++)
		for(int z = 0; z < tempTiles[x].length; z++)
			tempTiles[x][z] = new Tile(handler, floors[0], new Vector3f(x / 2f, 0, z / 2f));
	}
	
	public void update(float delta) {
		for(Floor floor : floors)
			floor.update(delta);
		
		lotEdit.update(delta);
	}
	
	public void render() {
		for(Floor floor : floors)
			floor.render();
		
		lotEdit.render();
	}
	
	public Floor getFloor(Vector3f position) {
		for(int i = 0; i < floors.length; i++) {
			Floor floor = floors[i];
			if(getFloor(i + 1) == null) 
				return floor;
			if(position.y >= floor.getPosition().y && position.y < getFloor(i + 1).getPosition().y)
				return floor;
		}
		return null;
	}
	
	public Floor getFloor(int num) {
		if(num >= 0 && num < floors.length)
			return floors[num];
		return null;
	}
	
	public Tile[][] getFloorTiles(Vector3f position) {
		Floor floor = getFloor(position);
		if(floor != null)
			return floor.getTiles();
		return null;
	}
	
	public Tile[][] getFloorTiles(int num) {
		return getFloor(num) == null ? null : getFloor(num).getTiles();
	}
	
	public Tile getTile(Vector3f position) { return getFloor(position).getTile(Util.to2D(position)); }
	public Floor[] getFloors() { return floors; }

	public void enableEdit() { lotEdit.setEnabled(true); }
	public void disableEdit() { lotEdit.setEnabled(false); }
	
	public LotEdit getEditMode() { return lotEdit; }
	
	public Vector3f getPosition() { return position; }
	public Vector2f getDimensions() { return dimensions; }
	public int getWidth() { return (int) dimensions.x; }
	public int getHeight() { return (int) dimensions.y; }
}