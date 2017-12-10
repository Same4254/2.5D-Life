package World.Tiles;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

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
	
	private Handler handler;
	private Lot lot;

	private WorldObject object;
	private WrapperStaticBody body;

	public Tile(Handler handler, Lot lot, Vector2f position, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		this.lot = lot;
		
		body = new WrapperStaticBody(wrapperModel, texture);
		body.setPosition2D(position);
		
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
	}
	
	public void render(TileInstanceModel model) {
		Vector2f temp = body.getPosition2D().add(lot.getPosition());
		
		body.getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
//		body.render();
		model.render((DefaultRenderProperties) body.getRenderProperties());
		
//		if(fullObject != null) {
//			fullObject.getBody().getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
//			fullObject.render(camera);
//		}
		if(object != null) {
			if(object instanceof MultiTileObject)  {
				if(!((MultiTileObject)object).getTiles().isEmpty() && ((MultiTileObject)object).getTiles().get(0) == this) {
					object.getBody().getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
					object.render();
				}
			} else {
				object.getBody().getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
				object.render();
			}
		}
	}
	
	public void update() {
		if(object != null) {
			if(object instanceof MultiTileObject) 
				if(!((MultiTileObject) object).getTiles().isEmpty() && ((MultiTileObject) object).getTiles().get(0) == this)
					object.update();
			object.update();
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
		if(object.getBody().getStaticBody() == body)
			return object;
		return null;
	}
	
	public boolean containsAnything() { return object != null; }

	public Lot getLot() { return lot; }
	public WorldObject getObject() { return object; }
	public WrapperStaticBody getBody() { return body; }
}