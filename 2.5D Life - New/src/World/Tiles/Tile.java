package World.Tiles;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.TileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Assets;
import Main.Handler;
import World.Tiles.Render.TileInstanceModel;

public class Tile {
	public static final int TILE_RESOLUTION = 4;
	
	private Handler handler;
	private Lot lot;
	
	private WorldObject object;
	private WrapperStaticBody body;

	public Tile(Handler handler, Lot lot, Vector3f position) {
		this.handler = handler;
		this.lot = lot;
		
		body = new WrapperStaticBody(Assets.tileModel);
		body.setPosition3D(position);
		
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
	}
	
	public void render(TileInstanceModel model) {
		model.render(body.getRenderProperties());

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
					object.masterUpdate(delta);
					return;
				}
			}
			object.masterUpdate(delta);
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
		if(object != null) {
			if(object.getBody().getStaticBody() != body)
				return object.containsApplianceBody(body);
			else 
				return object;
		}
		return null;
	}
	
	public void cleanUp() {
		handler.getGame().getPhysicsEngine().remove(body.getStaticBody());
//		body.getSoundSource().delete();
	}
	
	public int getTextureIndex() { return body.getRenderProperties().getTextureAtlasIndex(); }
	public void setTextureIndex(int index) { body.getRenderProperties().setTextureAtlasIndex(index); }
	
	public boolean containsAnything() { return object != null; }

	public Vector2f getPosition2D() { return body.getPosition2D(); }
	public Vector3f getPosition3D() { return body.getPosition3D(); }
	
	public void setPosition3D(Vector3f position) { body.setPosition3D(position); }
	public void setPosition2D(Vector2f position) { body.setPosition2D(position); }
	
	public Lot getLot() { return lot; }
	public WorldObject getObject() { return object; }
	public WrapperStaticBody getBody() { return body; }
}