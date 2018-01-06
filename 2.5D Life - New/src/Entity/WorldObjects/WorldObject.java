package Entity.WorldObjects;

import java.util.ArrayList;
import java.util.HashMap;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Audio.SoundSource;
import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
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
	protected Tile tile;

	protected ArrayList<Item> inventory;
	protected HashMap<Living, Integer> needs, skills;
	protected ApplianceManager applianceManager;
	
	protected Vector2f front;
	
	public WorldObject(Handler handler, Lot lot, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		this.lot = lot;
		
		body = new WrapperStaticBody(wrapperModel, texture);
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
		front = new Vector2f(getWidth(), 0);
		
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
	
	public abstract void update(float delta);
	public abstract Action getAction(Entity entity, Living reason);
	public abstract Item searchForItem(Entity entity, Living reason);
	public abstract WorldObject clone();
	
	protected abstract void initSkillsAndNeeds();
	protected abstract void initInventory();
	
	protected Action searchApplianceForAction(Entity entity, Living reason) {
		for(Appliance appliance : applianceManager.getAppliances()) {
			Action action = appliance.getAction(entity, reason);
			if(action != null) 
				return action;
		}
		return null;
	}
	
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
	public abstract Vector2f[] getApplianceLocations();

	public Vector3f getApplicationPosition() { return applianceManager.getAnAvailablePosition(); }
	public boolean addAppliance(Appliance appliance) { return applianceManager.addAppliance(appliance); }
	public boolean removeAppliance(Appliance appliance) { return applianceManager.removeAppliance(appliance); }
//	public void removeAppliance(Appliance appliance) { applianceManager.removeAppliance(appliance); }
	public Appliance containsApplianceBody(PhysicsBody body) {
		for(Appliance appliance : applianceManager.getAppliances()) 
			if(appliance.getBody().getStaticBody() == body)
				return appliance;
		return null;
	}
	
	public float getX() { return body.getX(); }
	public float getZ() { return body.getZ(); }
	
	public float getWidth() { return body.getWidth(); }
	public float getHeight() { return body.getHeight(); }
	public float getHeightY() { return body.getHeightY(); }
	
	public Vector3f getPosition3D() { return body.getPosition3D(); }
	public Vector2f getPosition2D() { return body.getPosition2D(); }
	public Vector2f getFront() { return front; }
	
	public void setPosition2D(Vector2f position) { 
		body.setRotationPosition2D(position);
		applianceManager.syncPosition();
	}
	
	public void setPosition2D(float x, float z) { setPosition2D(new Vector2f(x, z)); }

	public void setPosition3D(Vector3f position) { 
		body.setPosition3D(position);
		applianceManager.syncPosition();
	}
	
	public void setRotationPosition3D(Vector3f position) { 
		body.setRotationPosition3D(position);
		applianceManager.syncPosition();
	}
	
	public void setAngle(float angle) {
		float angleDiiference = angle - body.getRenderProperties().getTransform().getRotation().y;
		
		if(angle == 0) 
			front = new Vector2f(getWidth(), 0);
		if(angle == 90) 
			front = new Vector2f(0, -1);
		if(angle == 180)
			front = new Vector2f(-1, 0);
		if(angle == 270)
			front = new Vector2f(0, getHeight());
		
		body.setAngle(angle); 
		applianceManager.rotate(angle, angleDiiference);
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

//	public void playSound(int buffer) { body.getSoundSource().play(buffer); }
	public ArrayList<Item> getInventory() { return inventory; } 
	public Lot getLot() { return lot; }	
	public WrapperStaticBody getBody() { return body; }
	public void setTile(Tile tile) { this.tile = tile; }
}
