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
	
	private WorldObject fullObject;
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
		
		if(fullObject != null) {
			if(fullObject instanceof MultiTileObject)  {
				if(!((MultiTileObject)fullObject).getTiles().isEmpty() && ((MultiTileObject)fullObject).getTiles().get(0) == this) {
					fullObject.getBody().getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
					fullObject.render();
				}
			} else {
				fullObject.getBody().getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
				fullObject.render();
			}
		}
	}
	
	public void update() {
		if(fullObject != null) {
			if(fullObject instanceof MultiTileObject) 
				if(!((MultiTileObject)fullObject).getTiles().isEmpty() && ((MultiTileObject)fullObject).getTiles().get(0) == this)
					fullObject.update();
			fullObject.update();
		}
	}
	
	public boolean add(WorldObject obj) {
		if(fullObject == null) {
			if(obj instanceof TileObject) {
				fullObject = (TileObject) obj;
			} else if(obj instanceof MultiTileObject) {
				MultiTileObject temp = (MultiTileObject) obj;
				fullObject = temp;
			}
			
			return true;
		}
		return false;
	}
	
//	public boolean collide(Entity e) {
//		Rectangle2D collisionRect = new Rectangle2D.Float(e.getBody().getX() - body.getX(), e.getBody().getZ() - body.getZ(), e.getBody().getWidth(), e.getBody().getHeight());
//		
//		return collisionRect.intersects(full)
//		
//		for(int x = 0; x < TILE_RESOLUTION; x++)  
//		for(int y = 0; y < TILE_RESOLUTION; y++) 
//			if((subTileObjects[x][y] != null || fullObject != null) && collisionRect.intersects(rectangles[x][y])) return true;
//		return false;
//	}
//	
//	public boolean collide(WorldObject obj) {
//		if(fullObject == null) {
//			if(obj instanceof TileObject || obj instanceof MultiTileObject) {
//				for(SubTileObject[] subTiles : subTileObjects)  
//				for(SubTileObject o : subTiles) 
//					if(o != null) return true;
//			} else {
//				SubTileObject temp = (SubTileObject) obj;
//				
//				if(temp.getSubX() + temp.getSubWidth() > TILE_RESOLUTION)
//					return true;
//				if(temp.getSubY() + temp.getSubHeight() > TILE_RESOLUTION)
//					return true;
//				
//				for(int x = temp.getSubX(); x < temp.getSubX() + temp.getSubWidth(); x++) { 
//				for(int y = temp.getSubY(); y < temp.getSubY() + temp.getSubHeight(); y++) {
////					if(subTileObjects[x][y] != null && subTileObjects[x][y].getClass().equals(obj.getClass())) continue;
//					if(subTileObjects[x][y] != null) return true;
//				}}
//			}
//		} else return true;
//		return false;
//	}
	
	public WorldObject remove(WorldObject object) {
		if(fullObject == object) {
			if(!(object instanceof MultiTileObject))
				object.clearTile();
			fullObject = null;
			return object;
		} 
		return null;
	}
	
	public WorldObject findObject(PhysicsBody body) {
		if(fullObject != null && fullObject.getBody().getStaticBody() == body)
			return fullObject;
		return null;
	}
	
	public boolean containsAnything() {
		if(fullObject != null) 
			return true;
		return false;
	}

	public Lot getLot() { return lot; }
	public WorldObject getFullObject() { return fullObject; }
	public WrapperStaticBody getBody() { return body; }
}