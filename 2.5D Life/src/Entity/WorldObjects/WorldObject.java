package Entity.WorldObjects;

import java.util.ArrayList;
import java.util.HashMap;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.Entity.Living;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Appliances.Appliance;
import Entity.WrapperBodies.WrapperModel;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;
import World.Tiles.Tile;

public abstract class WorldObject {
	protected Handler handler;
	protected Lot lot;
	protected WrapperStaticBody body;
	protected ArrayList<Tile> tiles;

	protected ArrayList<Item> inventory;
	protected HashMap<Living, Integer> needs, skills;
	protected ApplianceManager applianceManager;
	
	protected Vector2f front;
	
	public WorldObject(Handler handler, Lot lot, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		this.lot = lot;
		
		body = new WrapperStaticBody(wrapperModel, texture);
		body.roundDimensionsToGrid();
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
		front = new Vector2f(getWidth() / 2.0, 0);
		
		tiles = new ArrayList<>();
		inventory = new ArrayList<>();
		needs = new HashMap<>();
		skills = new HashMap<>();
		
		initSkillsAndNeeds();
		initInventory();
		
		applianceManager = new ApplianceManager(this, getApplianceLocations());
	}
	
	public void render() {
		body.render();
		applianceManager.render();
	}
	
	public void masterUpdate(float delta) {
		applianceManager.update(delta);
		update(delta);
	}
	
	public boolean addToTile(Tile tile) {
		//Check the tiles near it to confirm nothing is in the way
		if(!tiles.isEmpty()) {
			System.err.println("List Not empty");
			return false;
		}

		for(float x = tile.getX(); x < tile.getX() + getWidth(); x += .5) {
			for(float z = tile.getZ(); z < tile.getZ() + getHeight(); z += .5) {
				if(lot.getTile(new Vector3f(x, getY(), z)).containsAnything())
					return false;
			}
		}
		
		for(float x = tile.getX(); x < tile.getX() + getWidth(); x += .5) {
			for(float z = tile.getZ(); z < tile.getZ() + getHeight(); z += .5) { 
				Tile tempTile = lot.getTile(new Vector3f(x, getY(), z));
				tempTile.add(this);
				tiles.add(tempTile);
			}
		}
		
		setPosition2D(tile.getPosition2D());
		return true;
	}
	
	public abstract void update(float delta);
	protected abstract void initSkillsAndNeeds();
	protected abstract void initInventory();
	public abstract Vector2f[] getApplianceLocations();
	
	public abstract Action getAction(Entity entity, Living reason);
	public abstract Item searchForItem(Entity entity, Living reason);
	public abstract WorldObject clone();
	
	public Vector3f getApplicationPosition() { return applianceManager.getAnAvailablePosition(); }
	public boolean addAppliance(Appliance appliance) { return applianceManager.addAppliance(appliance); }
	public boolean removeAppliance(Appliance appliance) { 
		if(applianceManager.getAppliances().contains(appliance)) {
			appliance.removeFromTile();
			return applianceManager.removeAppliance(appliance); 
		}
		return false;
	}
//	public void removeAppliance(Appliance appliance) { applianceManager.removeAppliance(appliance); }
	public Appliance containsApplianceBody(PhysicsBody body) {
		for(Appliance appliance : applianceManager.getAppliances()) 
			if(appliance.getBody().getStaticBody() == body)
				return appliance;
		return null;
	}
	
	protected Action searchApplianceForAction(Entity entity, Living reason) {
		for(Appliance appliance : applianceManager.getAppliances()) {
			Action action = appliance.getAction(entity, reason);
			if(action != null) 
				return action;
		}
		return null;
	}
	
	public void removeFromTile() {
		for(Tile tile : tiles) 
			tile.remove(this);
		tiles.clear();
	}
	
	public void cleanUp() {
		handler.getGame().getPhysicsEngine().remove(body.getStaticBody());
	}
	
	public float getX() { return body.getX(); }
	public float getY() { return body.getY(); }
	public float getZ() { return body.getZ(); }
	
	public float getWidth() { return body.getWidth(); }
	public float getHeight() { return body.getHeight(); }
	public float getHeightY() { return body.getHeightY(); }
	
	public Vector3f getPosition3D() { return body.getPosition3D(); }
	public Vector2f getPosition2D() { return body.getPosition2D(); }
	public Vector2f getFront() { return front; }
	
	public Vector3f getTopLeftPosition3D() { return body.getPosition3D().subtract(getWidth() / 2, 0, getHeight() / 2); }
	public Vector2f getTopLeftPosition2D() { return body.getPosition2D().subtract(getWidth() / 2,    getHeight() / 2); }

	public void setPosition2D(float x, float z) { setPosition2D(new Vector2f(x, z)); }
	public void setPosition2D(Vector2f position) {
		position = position.add(getWidth() / 2, getHeight() / 2);
		body.setPosition2D(position);
		
		applianceManager.syncPosition();
	}
	
	public void setPosition3D(float x, float y, float z) { setPosition3D(new Vector3f(x, y, z)); }
	public void setPosition3D(Vector3f position) {
		position = position.add(getWidth() / 2, 0, getHeight() / 2);
		body.setPosition3D(position);
		
		applianceManager.syncPosition();
	}
	
	public void setAngle(float angle) {
		if(angle == 0) 
			front = new Vector2f(getWidth() / 2.0, 0);
		if(angle == 90) 
			front = new Vector2f(0, -getHeight() / 2.0);
		if(angle == 180)
			front = new Vector2f(-getWidth() / 2.0, 0);
		if(angle == 270)
			front = new Vector2f(0, getHeight() / 2.0);

		applianceManager.rotate(angle, angle - body.getRenderProperties().getTransform().getRotation().y);
		body.setAngle(angle); 
	}

	public HashMap<Living, Integer> getNeeds() { 
		HashMap<Living, Integer> temp = new HashMap<>();
		temp.putAll(needs);
		
		for(Appliance appliance : applianceManager.getAppliances()) 
			temp.putAll(appliance.getNeeds());
		
		return temp; 
	}
	
	public HashMap<Living, Integer> getSkills() {
		HashMap<Living, Integer> temp = new HashMap<>();
		temp.putAll(skills);
		
		for(Appliance appliance : applianceManager.getAppliances()) 
			temp.putAll(appliance.getSkills());
		
		return temp; 
	}
	
	public ArrayList<Item> getInventory() { return inventory; }
	public Lot getLot() { return lot; }	
	public WrapperStaticBody getBody() { return body; }
	public ArrayList<Tile> getTiles() { return tiles; }
}
