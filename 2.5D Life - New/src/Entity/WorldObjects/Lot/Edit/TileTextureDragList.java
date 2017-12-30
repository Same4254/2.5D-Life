package Entity.WorldObjects.Lot.Edit;

import java.util.HashMap;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.Lot.Lot;
import World.Tiles.Tile;

public class TileTextureDragList {
	private HashMap<Tile, Integer> originalIndecies;
	private Lot lot;
	
	public TileTextureDragList(Lot lot) {
		this.lot = lot;
		
		originalIndecies = new HashMap<>();
	}
	
	public void fill(Tile originalTile, Vector2f position) {
		if(originalTile != null) {
			
			boolean inX = true;
			int x = (int) originalTile.getBody().getPosition2D().x;
			while(inX) {
				boolean inZ = true;
				int z = (int) originalTile.getBody().getPosition2D().y;
				while(inZ) {
					Tile currentTile = lot.getTiles()[x][z];
					if(!originalIndecies.keySet().contains(currentTile)) {
						originalIndecies.put(currentTile, currentTile.getTextureIndex());
						currentTile.setTextureIndex(originalTile.getTextureIndex());
					}
					
					if(position.y > originalTile.getBody().getPosition2D().y) {
						z++;
						if(z > position.y)
							inZ = false;
					} else {
						z--;
						if(z < position.y)
							inZ = false;
					}
				}
				
				if(position.x > originalTile.getBody().getPosition2D().x) {
					x++;
					if(x > position.x)
						inX = false;
				} else {
					x--;
					if(x < position.x)
						inX = false;
				}
			}
		}
	}
	
	public void place() {
		originalIndecies.clear();
	}
	
	public void clear() {
		for(Tile t : originalIndecies.keySet())
			t.setTextureIndex(originalIndecies.get(t));
		
		originalIndecies.clear();
	}
}
