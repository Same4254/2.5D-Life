package Entity.FreeMoving.AI.Action;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;

public class GoToAction extends Action {
	private Entity entity;
	
	private Vector2f toGridLocation;
	private Vector2f step;
	
	public GoToAction(Entity entity, Vector2f toGridLocation) {
		this.entity = entity;
		this.toGridLocation = toGridLocation.truncate();
	}
	
	public GoToAction(Entity entity, int x, int z) {
		this(entity, new Vector2f(x, z));
	}

	@Override
	public void start() {
		super.start();
		
		step = toGridLocation.subtract(entity.getGridLocation()).divide(toGridLocation.subtract(entity.getGridLocation()).length()).multiply(entity.getMovementSpeed());
	}
	
	@Override
	public void update(float delta) {
		if(entity.getGridLocation().equals(toGridLocation)) {
			complete = true;
		} else {
			if(!entity.move(step, delta))
				complete = true;
		}
	}
}
