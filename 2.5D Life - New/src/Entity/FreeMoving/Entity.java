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
	
	public boolean move(Vector2f velocity, float delta) {
//		if(!collide(handler.getWorld().getTestLot(), velocity.multiply(delta))) {
//			float angle = (float) Math.toDegrees(Math.acos((velocity.dot(new Vector2f(1, 0)) / velocity.length())));
			float angle = (float) Math.toDegrees(Math.atan2(velocity.x, velocity.y));
		
//			if(velocity.y > 0 && velocity.x == 0)
//				angle -= 180;
			
//			if(velocity.y > 0) {
//				if(velocity.x > 0)
//					angle -= 90;
//				if(velocity.x < 0)
//					angle += 90;
//			} 
			
			angle -= 90;
//			System.out.print(velocity + " " + angle + " ");
			
			if(Util.withinRange(angle, Util.roundNearestMultiple(angle, 45), 5))
				angle = Util.roundNearestMultiple(angle, 45);

//			System.out.println(angle);
			
			body.getRenderProperties().getTransform().setRotation(new Vector3f(0, angle, 0));
			body.add(velocity.multiply(delta));
			return true;
//		}
//		return false;
	}
	
//	public boolean collide(Lot lot, Vector2f velocity) {
//		Tile[][] tiles = lot.getTiles();
//		
//		Vector2f currentLocation = body.getPosition2D();
//		Vector2f step = velocity.normalize().divide(Tile.TILE_RESOLUTION);
//		Vector2f position = body.getPosition2D();
//		
//		int maxWidth = (int) Math.ceil(body.getWidth());
//		int maxHeight = (int) Math.ceil(body.getHeight());
//		
//		try {
//			position = position.add(step);
//			do {
//				body.setPosition2D(position);
//				Vector2f gridPosition = position.subtract(maxWidth / 2f, maxHeight / 2f).truncate();
//				for(int x = (int) gridPosition.x; x < gridPosition.x + maxWidth + 1; x++) { 
//				for(int y = (int) gridPosition.y; y < gridPosition.y + maxHeight + 1; y++) {
//					if(x >= 0 && y >= 0 && x < lot.getWidth() && y < lot.getHeight()) {
//						tiles[x][y].getBody().getModel().setTexture(Assets.redTexture);
//						if(tiles[x][y].collide(this)) return true;
//					}
//				}}
//				position = position.add(step);
//			} while(position.subtract(currentLocation).length() <= velocity.length());
//			return false;
//		} finally {
//			body.setPosition2D(currentLocation);
//		}
//	}
	
	public void addAction(Action a) { actionQueue.add(a); }
	
	public float getX() { return body.getX(); }
	public float getY() { return body.getY(); }
	public float getZ() { return body.getZ(); }
	
	public Vector2f getMovementSpeed() { return movementSpeed; }
	
	public Vector2f getCenterLocation() { return new Vector2f(body.getHitBox().getCenterX(), body.getHitBox().getCenterY()); }
	public Vector2f getLocation() { return body.getPosition2D(); }
	public Vector2f getGridLocation() { return body.getPosition2D().truncate(); }
	
	public void update(float delta) {
		actionQueue.update(delta);
		needManager.update(delta);
	}
	
	public void render() {
		body.render();
	}
	
	public WrapperStaticBody getBody() { return body; }
}
