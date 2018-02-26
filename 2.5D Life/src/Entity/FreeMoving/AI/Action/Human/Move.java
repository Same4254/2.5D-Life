package Entity.FreeMoving.AI.Action.Human;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.Action;
import Main.Handler;
import Utils.Util;

public class Move extends Action {
	private Entity entity;
	
	private Vector2f toGridLocation;
	private Vector2f startLocation;
	private Vector2f step;
	
	public Move(Handler handler, Entity entity, Vector2f toGridLocation) {
		super(handler);
		
		this.entity = entity;
		this.toGridLocation = toGridLocation;
	}
	
	@Override
	public void start() {
		super.start();
		
		startLocation = entity.getCornerPosition2D();
		
		step = toGridLocation.subtract(startLocation).divide(toGridLocation.subtract(startLocation).length()).multiply(entity.getMovementSpeed());
	}
	
	@Override
	public void update(float delta) {
		if(Util.withinRange(entity.getCornerPosition2D(), toGridLocation, .1f)) {
			complete = true;
//			entity.setPosition2D(toGridLocation);
		} else {
			entity.move(step, delta);
//			System.out.println("Entity: " + entity.getCornerPosition2D() + ", Goal: " + toGridLocation);
		}
	}
	
	public String toString() {
		return "From: " + startLocation + ", To: " + toGridLocation;
	}
}

