package Entity.WorldObjects;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import World.Tiles.Tile;

public abstract class FullTileObject extends WorldObject {
	
	public FullTileObject(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);
	}

	@Override
	public void rotateLeft() {
		body.getRenderProperties().rotate(new Vector3f(0, 90, 0));
	}

	@Override
	public void rotateRight() {
		body.getRenderProperties().rotate(new Vector3f(0, -90, 0));
	}
	
	@Override
	public boolean addToTile(Tile tile) {
		if(!tile.collide(this)) {
			if(this.tile != null) 
				this.tile.remove(this);
			tile.add(this);
			this.tile = tile;
			
			body.setPosition2D(tile.getBody().getPosition2D());
			return true;
		}
		
		return false;
	}

	@Override
	public void clearTile() {
		tile = null;
	}
}
