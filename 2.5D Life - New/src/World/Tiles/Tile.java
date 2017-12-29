package World.Tiles;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.TileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;
import World.Tiles.Render.TileInstanceModel;

public class Tile {
	public static final int TILE_RESOLUTION = 4;
	
	private Lot lot;

	private WorldObject object;
	private WrapperStaticBody body;

	public Tile(Handler handler, Lot lot, Vector2f position, WrapperModel wrapperModel, Texture2D texture) {
		this.lot = lot;
		
		body = new WrapperStaticBody(wrapperModel, texture);
		body.setPosition2D(position);
		
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
	}
	
	public void render(TileInstanceModel model) {
		body.render();

		if(object != null) {
			if(object instanceof MultiTileObject)  {
				if(!((MultiTileObject) object).getTiles().isEmpty() && ((MultiTileObject)object).getTiles().get(0) == this) 
					object.render();
			} else 
				object.render();
		}
	}
	
	public void update(float delta) {
		if(object != null) {
			if(object instanceof MultiTileObject) { 
				if(!((MultiTileObject) object).getTiles().isEmpty() && ((MultiTileObject) object).getTiles().get(0) == this) {
					object.update(delta);
					return;
				}
			}
			object.update(delta);
		}
	}
	
	public boolean add(WorldObject obj) {
		if(!containsAnything()) {
			if(obj instanceof TileObject) 
				object = (TileObject) obj;
			else if(obj instanceof MultiTileObject) 
				object = (MultiTileObject) obj;
			return true;
		}
		return false;
	}
	
	public WorldObject remove(WorldObject obj) {
		if(object == obj) {
			if(!(object instanceof MultiTileObject))
				object.clearTile();
			object = null;
			return obj;
		}
		return null;
	}
	
	public WorldObject findObject(PhysicsBody body) {
		if(object != null && object.getBody().getStaticBody() == body)
			return object;
		return null;
	}
	
	public boolean containsAnything() { return object != null; }

	public Lot getLot() { return lot; }
	public WorldObject getObject() { return object; }
	public WrapperStaticBody getBody() { return body; }
}