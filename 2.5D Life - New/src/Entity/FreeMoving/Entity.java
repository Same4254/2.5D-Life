package Entity.FreeMoving;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.ActionQueue;
import Entity.FreeMoving.AI.Living.Needs.NeedManager;
import Entity.WrapperBodies.WrapperModel;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;
import Utils.Util;

public abstract class Entity {
	public static enum Living {
		Hunger, Entertainment, Sleep,
		Cooking, Programming
	};
	
	protected Handler handler;
	protected ActionQueue actionQueue;
	protected NeedManager needManager;
	protected Inventory inventory;
	
	protected WrapperStaticBody body;
	protected Vector2f movementSpeed;
	protected float eatingSpeed;// Abscure variable, will depend on traits of the entities later on

	public Entity(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		
		actionQueue = new ActionQueue();
		needManager = new NeedManager();
		
		body = new WrapperStaticBody(wrapperModel, texture);
		movementSpeed = new Vector2f();
		eatingSpeed = 10;
		
		inventory = new Inventory(handler, this);
	}
	
	public void move(Vector2f velocity, float delta) {
		float angle = Util.getAngle(velocity);
		
		if(Util.withinRange(angle, Util.roundNearestMultiple(angle, 45), 5))
			angle = Util.roundNearestMultiple(angle, 45);
			
		setAngle(angle);
		body.add(velocity.multiply(delta));
	}
	
	public void update(float delta) {
		actionQueue.update(delta);
		needManager.update(delta);
	}
	
	public void render() { body.render(); }
	
	public NeedManager getNeedManager() { return needManager; }
	public void addAction(Action a) { if(a != null) actionQueue.add(a); }
	
	public float getX() { return body.getX(); }
	public float getZ() { return body.getZ(); }
	
	public float getEatingSpeed() { return eatingSpeed; }
	public void setEatingSpeed(float eatingSpeed) { this.eatingSpeed = eatingSpeed; }
	public Vector2f getMovementSpeed() { return movementSpeed; }
	
	public Vector2f getPosition() { return body.getPosition2D(); }
	public Vector2f getGridPosition() { return body.getPosition2D().truncate(); }
	
	public void setPosition2D(Vector2f position) { body.setPosition2D(position); }
	public void setPosition2D(float x, float z) { body.setPosition2D(x, z); }
	
	public Vector2f roundPosToGrid() { return body.roundPosToGrid(); }
	public void setAngle(float angle) { body.getRenderProperties().getTransform().setRotation(new Vector3f(0, angle, 0)); }
	
	public Inventory getInventory() { return inventory; }
	
//	public WrapperStaticBody getBody() { return body; }
}
