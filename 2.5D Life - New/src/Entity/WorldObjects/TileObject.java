package Entity.WorldObjects;

import com.Engine.RenderEngine.Textures.Texture2D;

import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;

public abstract class TileObject extends WorldObject {
	
	public TileObject(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);
	}

	@Override
	public boolean addToTile(Tile tile) {
		if(!tile.containsAnything()) {
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
	public void rotateFront(float angle) {
		front = Util.rotate(front, angle);
	}

	@Override
	public void clearTile() {
		tile = null;
	}
}
