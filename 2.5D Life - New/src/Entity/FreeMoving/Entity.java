package Entity.FreeMoving;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Assets;
import Main.Handler;
import Utils.ImageLoader;
import World.Tiles.Tile;

public abstract class Entity {
	protected Handler handler;
	protected WrapperStaticBody body;

	public Entity(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		body = new WrapperStaticBody(wrapperModel, texture);
	}
	
	public boolean collide(Lot lot, Vector2f velocity) {
		Tile[][] tiles = lot.getTiles();
		
		Vector2f currentLocation = body.getPosition2D();
		
		Vector2f step = velocity.normalized().divide(Tile.TILE_RESOLUTION);

		Vector2f position = body.getPosition2D();
		
		int maxWidth = (int) Math.ceil(body.getWidth());
		int maxHeight = (int) Math.ceil(body.getHeight());
		
		try {
			position = position.add(step);
			do {
				body.setPosition2D(position);
				Vector2f gridPosition = position.subtract(maxWidth / 2f, maxHeight / 2f).truncate();
				for(int x = (int) gridPosition.x; x < gridPosition.x + maxWidth + 1; x++) { 
				for(int y = (int) gridPosition.y; y < gridPosition.y + maxHeight + 1; y++) {
					tiles[x][y].getBody().getModel().setTexture(Assets.redTexture);
					if(tiles[x][y].collide(this)) return true;
				}}
				position = position.add(step);
			} while(position.subtract(currentLocation).length() <= velocity.length());
			return false;
		} finally {
			body.setPosition2D(currentLocation);
		}
	}
	
	public abstract void update(float delta);
	public abstract void render(Camera camera);
	
	public WrapperStaticBody getBody() { return body; }
}
