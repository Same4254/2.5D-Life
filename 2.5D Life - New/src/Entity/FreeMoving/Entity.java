package Entity.FreeMoving;

import java.util.ArrayList;
import java.util.Collections;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.AI.PathFinding;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.ActionQueue;
import Entity.FreeMoving.AI.Action.GoToAction;
import Entity.FreeMoving.AI.Needs.NeedManager;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Assets;
import Main.Handler;
import World.Tiles.Tile;

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
	
	public void goTo(Lot lot, Vector2f toPosition) {//Slight rounding issue, action needs to get the starting location when it's its turn <- Fix Those
		ArrayList<Vector2f> path = PathFinding.aStar(lot, getGridLocation(), toPosition);
		Collections.reverse(path);
		
		for(Vector2f point : path) 
			actionQueue.add(new GoToAction(this, point));
	}
	
	public boolean move(Vector2f velocity, float delta) {
//		if(!collide(handler.getWorld().getTestLot(), velocity.multiply(delta))) {
//			float angle = (float) Math.toDegrees(Math.acos(new Vector2f(0, 1).dot(velocity.normalize()))) - 90;
//			float angle = (float) (90 - Math.atan(velocity.y / velocity.x) * 360 / 2 / Math.PI);
			
//			body.getRenderProperties().getTransform().setRotation(new Vector3f(0, angle - 90, 0));
			body.add(velocity.multiply(delta));
			return true;
//		}
//		return false;
	}
	
	public boolean collide(Lot lot, Vector2f velocity) {
		Tile[][] tiles = lot.getTiles();
		
		Vector2f currentLocation = body.getPosition2D();
		
		Vector2f step = velocity.normalize().divide(Tile.TILE_RESOLUTION);

		Vector2f position = body.getPosition2D();
		
		int maxWidth = (int) Math.ceil(body.getWidth());
		int maxHeight = (int) Math.ceil(body.getHeight());
		
		try {
			position = position.add(step);
			do {
				body.setPosition2D(position);
				Vector2f gridPosition = position.subtract(maxWidth / 2f, maxHeight / 2f).truncate();
				for(int x = (int) gridPosition.x; x < gridPosition.x + maxWidth + 1; x++) { 
				for(int y = (int) gridPosition.y; y < gridPosition.y + maxHeight + 1; y++) {
					if(x >= 0 && y >= 0 && x < lot.getWidth() && y < lot.getHeight()) {
						tiles[x][y].getBody().getModel().setTexture(Assets.redTexture);
						if(tiles[x][y].collide(this)) return true;
					}
				}}
				position = position.add(step);
			} while(position.subtract(currentLocation).length() <= velocity.length());
			return false;
		} finally {
			body.setPosition2D(currentLocation);
		}
	}
	
	public void addAction(Action a) {
		actionQueue.add(a);
	}
	
	public float getX() { return body.getX(); }
	public float getY() { return body.getY(); }
	public float getZ() { return body.getZ(); }
	
	public Vector2f getMovementSpeed() { return movementSpeed; }
	
	public Vector2f getCenterLocation() { return new Vector2f(body.getHitBox().getCenterX(), body.getHitBox().getCenterY()); }
	public Vector2f getLocation() { return body.getPosition2D(); }
	public Vector2f getGridLocation() { return body.getPosition2D().truncate(); }
	
	public void update(float delta) {
		actionQueue.update(delta);
		needManager.update();
	}
	
	public void render() {
		body.render();
	}
	
	public WrapperStaticBody getBody() { return body; }
}
