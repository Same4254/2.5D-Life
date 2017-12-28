package Entity.WorldObjects;

import java.util.ArrayList;
import java.util.HashMap;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;
import World.Tiles.Tile;

public abstract class WorldObject {
	protected Handler handler;
	protected WrapperStaticBody body;
	protected Tile tile;

	protected ArrayList<Item> inventory;
	protected HashMap<Living, Integer> needs, skills;
	
	protected Vector2f front;
	
	public WorldObject(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		
		body = new WrapperStaticBody(wrapperModel, texture);
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
		front = new Vector2f(getWidth(), 0);
		
		inventory = new ArrayList<>();
		needs = new HashMap<>();
		skills = new HashMap<>();
		
		initSkillsAndNeeds();
		initInventory();
	}
	
	public void render() {
//		body.getRenderProperties().getTransform().setScale(new Vector3f(1, .1, 1));
		body.render();
	}
	
	public void update() {	}
	
	public abstract Action getAction(Entity entity, Living reason);
	public abstract Item searchForItem(Entity entity, Living reason);
	public abstract WorldObject clone();
	
	protected abstract void initSkillsAndNeeds();
	protected abstract void initInventory();
	
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
	public abstract void rotateFront(float angle);

	public float getX() { return body.getX(); }
	public float getZ() { return body.getZ(); }
	
	public float getWidth() { return body.getWidth(); }
	public float getHeight() { return body.getHeight(); }
	
	public Vector3f getPosition3D() { return body.getPosition3D(); }
	public Vector2f getPosition2D() { return body.getPosition2D(); }
	public Vector2f getFront() { return front; }
	
	public void setPosition2D(Vector2f position) { body.setRotationPosition2D(position); }
	public void setPosition2D(float x, float z) { setPosition2D(new Vector2f(x, z)); }

	public void setAngle(float angle) {
		if(angle == 0) 
			front = new Vector2f(getWidth(), 0);
		if(angle == 90) 
			front = new Vector2f(0, -1);
		if(angle == 180)
			front = new Vector2f(-1, 0);
		if(angle == 270)
			front = new Vector2f(0, getHeight());
		
		body.setAngle(angle); 
	}

	public HashMap<Living, Integer> getNeeds() { return needs; }
	public HashMap<Living, Integer> getSkills() { return skills; } 
	public ArrayList<Item> getInventory() { return inventory; } 
	
	public WrapperStaticBody getBody() { return body; }
	public void setTile(Tile tile) { this.tile = tile; }
}
