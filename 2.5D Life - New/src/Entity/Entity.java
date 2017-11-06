package Entity;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WrapperBodies.WrapperStaticBody;
import World.Tiles.Tile;

public class Entity {
	private WrapperStaticBody body;
	
	public Entity() {
		
	}
	
	public boolean collide(Tile[][] tiles, Vector2f velocity) {
		Vector2f currentLocation = body.getPosition2D();
		Vector2f step = velocity.normalized().divide(Tile.TILE_RESOLUTION);

		Vector2f position = body.getPosition2D();
		
		int maxWidth = (int) Math.ceil(body.getWidth());
		int maxHeight = (int) Math.ceil(body.getHeight());
		
		try {
			do {
				body.setPosition2D(position);
				Vector2f gridPosition = position.subtract(maxWidth / 2f, maxHeight / 2f).truncate();
				for(int x = (int) gridPosition.x; x < gridPosition.x + maxWidth + 1; x++) { 
				for(int y = (int) gridPosition.y; y < gridPosition.y + maxHeight + 1; y++) {
					if(tiles[x][y].collide(this)) return true;
				}}
			} while((position = position.add(step)).subtract(body.getPosition2D()).length() < velocity.length()); 
			
			return false;
		} finally {
			body.setPosition2D(currentLocation);
		}
	}
	
	public WrapperStaticBody getBody() { return body; }
}
