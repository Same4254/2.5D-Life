package Entity.WorldObjects;

import java.util.HashMap;

import com.Engine.RenderEngine.Textures.Texture2D;

import Entity.WrapperBodies.WrapperModel;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;
import World.Tiles.Tile;

public abstract class WorldObject {
	protected Handler handler;
	protected WrapperStaticBody body;
	protected Tile tile;
	
	public WorldObject(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		
		body = new WrapperStaticBody(wrapperModel, texture);
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
	}
	
	public void render() {
//		body.getRenderProperties().getTransform().setScale(new Vector3f(1, .1, 1));
		body.render();
	}
	
	public void update() {	}
	
	public abstract WorldObject clone();
	public abstract boolean addToTile(Tile tile);
	
	public WorldObject removeFromTile() {
		if(tile != null)
			return tile.remove(this);
		return null;
	}
	
	public void cleanUp() {
		handler.getGame().getPhysicsEngine().remove(body.getStaticBody());
	}
	
	public abstract void clearTile();
	public abstract void rotateLeft();
	public abstract void rotateRight();

	public float getX() { return body.getX(); }
	public float getZ() { return body.getZ(); }
	
	public float getWidth() { return body.getWidth(); }
	public float getHeight() { return body.getHeight(); }
	
	public WrapperStaticBody getBody() { return body; }
	public void setBody(WrapperStaticBody body) { this.body = body; }
	
	public void setTile(Tile tile) { this.tile = tile; }
}
