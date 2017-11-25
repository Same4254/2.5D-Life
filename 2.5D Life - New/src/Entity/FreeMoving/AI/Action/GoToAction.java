package Entity.FreeMoving.AI.Action;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Utils.Util;

public class GoToAction extends Action {
	private Entity entity;
	
	private Vector2f toGridLocation;
	private Vector2f step;
	
	public GoToAction(Entity entity, Vector2f toGridLocation) {
		this.entity = entity;
		this.toGridLocation = toGridLocation;
	}
	
	public GoToAction(Entity entity, int x, int z) {
		this(entity, new Vector2f(x, z));
	}

	@Override
	public void start() {
		super.start();
		
		Vector2f location = entity.getLocation();
		
		step = toGridLocation.subtract(location).divide(toGridLocation.subtract(location).length()).multiply(entity.getMovementSpeed());
	}
	
	@Override
	public void update(float delta) {
//		Vector2f pos = Util.roundNearestTenth(entity.getCenterLocation());

		if(Util.withinRange(entity.getLocation(), toGridLocation, .1f)) {
			complete = true;
		} else {
			if(!entity.move(step, delta))
				complete = true;
		}
	}
}
