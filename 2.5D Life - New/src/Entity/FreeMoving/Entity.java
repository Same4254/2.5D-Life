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
	protected Handler handler;
	protected ActionQueue actionQueue;
	protected NeedManager needManager;
	
	protected WrapperStaticBody body;
	protected Vector2f movementSpeed;

	public Entity(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		this.handler = handler;
		
		actionQueue = new ActionQueue();
		needManager = new NeedManager();
		
		body = new WrapperStaticBody(wrapperModel, texture);
		movementSpeed = new Vector2f();
	}
	
	public void move(Vector2f velocity, float delta) {
		float angle = (float) Math.toDegrees(Math.atan2(velocity.x, velocity.y));
		angle -= 90;
		if(Util.withinRange(angle, Util.roundNearestMultiple(angle, 45), 5))
			angle = Util.roundNearestMultiple(angle, 45);
			
		body.getRenderProperties().getTransform().setRotation(new Vector3f(0, angle, 0));
		body.add(velocity.multiply(delta));
	}
	
	public void update(float delta) {
		actionQueue.update(delta);
		needManager.update(delta);
	}
	
	public void render() { body.render(); }
	
	public NeedManager getNeedManager() { return needManager; }
	public void addAction(Action a) { actionQueue.add(a); }
	
	public float getX() { return body.getX(); }
	public float getY() { return body.getY(); }
	public float getZ() { return body.getZ(); }
	
	public Vector2f getMovementSpeed() { return movementSpeed; }
	
	public Vector2f getCenterLocation() { return new Vector2f(body.getHitBox().getCenterX(), body.getHitBox().getCenterY()); }
	public Vector2f getLocation() { return body.getPosition2D(); }
	public Vector2f getGridLocation() { return body.getPosition2D().truncate(); }
	
	public WrapperStaticBody getBody() { return body; }
}
