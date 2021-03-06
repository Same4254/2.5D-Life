package Entity.WorldObjects.Lot.Edit;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Lot.Lot;
import Main.Handler;
import World.Tiles.Tile;

public class TilePlacementDragList {
	private Handler handler;
	private Lot lot;

	private Tile[][] tiles;
	
	public TilePlacementDragList(Handler handler, Lot lot) {
		this.handler = handler;
		this.lot = lot;
		
		tiles = new Tile[(int) lot.getDimensions().x * 2][(int) lot.getDimensions().y * 2];
	}
	
	public void fill(Tile originalTile, Vector2f position) {
		if(originalTile != null) {
			boolean inX = true;
			float x = originalTile.getX();
			while(inX) {
				boolean inZ = true;
				float z = originalTile.getZ();
				while(inZ) { 
					if(tiles[(int) (x * 2)][(int) (z * 2)] == null) {
						tiles[(int) (x * 2)][(int) (z * 2)] = new Tile(handler, originalTile.getFloor(), new Vector3f(x, originalTile.getPosition3D().y, z));
						tiles[(int) (x * 2)][(int) (z * 2)].setTextureIndex(originalTile.getTextureIndex());
					}
						
					if(position.y > originalTile.getBody().getPosition2D().y) {
						z += .5;
						if(z > position.y) {
							inZ = false;
							
							for(int xi = 0; xi < tiles.length; xi++)
							for(int zi = 0; zi < tiles[0].length; zi++) {
								Tile tile = tiles[xi][zi];
								
								if(tile != null)
									if(tile.getZ() > position.y || tile.getZ() < originalTile.getZ())
										removeTile(xi, zi);
							}
										
						}
						
					} else {
						z -= .5;
						if(z < position.y)
							inZ = false;
						
						for(int xi = 0; xi < tiles.length; xi++)
						for(int zi = 0; zi < tiles[0].length; zi++) {
							Tile tile = tiles[xi][zi];
							
							if(tile != null)
								if(tile.getZ() < position.y || tile.getZ() > originalTile.getZ())
									removeTile(xi, zi);
						}
					}
				}
				
				if(position.x > originalTile.getBody().getPosition2D().x) {
					x += .5;
					if(x > position.x) {
						inX = false;
						
						for(int xi = 0; xi < tiles.length; xi++)
						for(int zi = 0; zi < tiles[0].length; zi++) {
							Tile tile = tiles[xi][zi];
							
							if(tile != null)
								if(tile.getX() > position.x || tile.getX() < originalTile.getX())
									removeTile(xi, zi);
						}
					}
				} else {
					x -= .5;
					if(x < position.x) {
						inX = false;
						
						for(int xi = 0; xi < tiles.length; xi++)
						for(int zi = 0; zi < tiles[0].length; zi++) {
							Tile tile = tiles[xi][zi];
							
							if(tile != null)
								if(tile.getX() < position.x || tile.getX() > originalTile.getX())
									removeTile(xi, zi);
						}
					}
				}
			}
		}
	}
	
	public void clear() {
		for(int x = tiles.length - 1; x >= 0; x--) {
			for(int z = tiles[x].length - 1; z >= 0; z--) {
				if(tiles[x][z] != null) {
					tiles[x][z].cleanUp();
					tiles[x][z] = null;
				}
			}
		}
	}
	
	public void place() {
		for(int x = tiles.length - 1; x >= 0; x--) {
			for(int z = tiles[x].length - 1; z >= 0; z--) {
				Tile tile = tiles[x][z];
				if(tile != null) {
					if(lot.getFloorTiles(tile.getPosition3D())[(int) (tile.getPosition2D().x * 2)][(int) (tile.getPosition2D().y * 2)] == null)
						lot.getFloorTiles(tile.getPosition3D())[(int) (tile.getPosition2D().x * 2)][(int) (tile.getPosition2D().y * 2)] = tile;
					else 
						tile.cleanUp();
					tiles[x][z] = null;
				}
			}
		}
	}
	
	public void render() {
		for(int x = tiles.length - 1; x >= 0; x--) 
			for(int z = tiles[x].length - 1; z >= 0; z--) 
				if(tiles[x][z] != null)
					tiles[x][z].render(Lot.tileInstanceModel);
	}
	
	private void removeTile(int xi, int zi) {
		tiles[xi][zi].cleanUp();
		tiles[xi][zi] = null;
	}
}
