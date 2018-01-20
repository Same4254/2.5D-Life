package World.Tiles;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Assets;
import Main.Handler;
import World.Tiles.Render.TileInstanceModel;

public class Tile {
	public static final int TILE_RESOLUTION = 2;
	
	private Handler handler;
	private Lot lot;
	private Floor floor;
	
	private WorldObject object;
	private WrapperStaticBody body;

	public Tile(Handler handler, Floor floor, Vector3f position) {
		this.handler = handler;
		this.floor = floor;
		this.lot = floor.getLot();
		
		body = new WrapperStaticBody(Assets.tileModel);
		body.setPosition3D(position);
		
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
	}
	
	public void render(TileInstanceModel model) {
		model.render(body.getRenderProperties());
//		body.render();
		
		if(object != null) {
			if(!object.getTiles().isEmpty() && object.getTiles().get(0) == this) 
				object.render();
		}
	}
	
	public void update(float delta) {
		if(object != null) {
			if(!object.getTiles().isEmpty() && object.getTiles().get(0) == this) 
				object.update(delta);
		}
	}
	
	public boolean add(WorldObject obj) {
		if(!containsAnything()) {
			object = obj;
			return true;
		}
		return false;
	}
	
	public void remove(WorldObject obj) {
		if(object == obj) 
			object = null;
	}
	
	public WorldObject findObject(PhysicsBody body) {
		if(object != null && object.getBody().getStaticBody() == body) 
			return object;
		return null;
	}
	
	public void cleanUp() {
		handler.getGame().getPhysicsEngine().remove(body.getStaticBody());
	}
	
	public int getTextureIndex() { return body.getRenderProperties().getTextureAtlasIndex(); }
	public void setTextureIndex(int index) { body.getRenderProperties().setTextureAtlasIndex(index); }
	
	public boolean containsAnything() { return object != null; }

	public Vector2f getPosition2D() { return body.getPosition2D(); }
	public Vector3f getPosition3D() { return body.getPosition3D(); }
	
	public float getX() { return getPosition3D().x; }
	public float getY() { return getPosition3D().y; }
	public float getZ() { return getPosition3D().z; }
	
	public void setPosition3D(Vector3f position) { 
		body.setPosition3D(position); 
		this.floor = floor.getLot().getFloor(body.getPosition3D()); 
	}
	public void setPosition2D(Vector2f position) { body.setPosition2D(position); }
	
	public Lot getLot() { return lot; }
	public Floor getFloor() { return floor; } 
	public WorldObject getObject() { return object; }
	public WrapperStaticBody getBody() { return body; }
}