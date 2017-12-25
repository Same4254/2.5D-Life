package Entity.FreeMoving.AI.Action.Human;

import java.util.ArrayList;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.PathFinding;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.Lot.Lot;
import Main.Handler;
import Utils.Util;

public class GoToAction extends MultiAction {
	protected Lot lot;
	protected Entity entity;
	protected Vector2f toGridLocation;
	
	public GoToAction(Handler handler, Lot lot, Entity entity, Vector2f toGridLocation) {
		super(handler);
		
		this.lot = lot;
		this.entity = entity;
		this.toGridLocation = toGridLocation;
	}
	
	public GoToAction(Handler handler, Lot lot, Entity entity, int x, int z) {
		this(handler, lot, entity, new Vector2f(x, z));
	}

	@Override
	public void start() {
		super.start();
		
		ArrayList<Vector2f> path = PathFinding.simplifyPath(PathFinding.aStar(entity, lot, entity.getPosition(), toGridLocation));
		if(path == null)
			return;
		
		for(Vector2f point : path)   
			subActions.add(new Move(handler, entity, point));
	}
}

class Move extends Action {
	private Entity entity;
	
	private Vector2f toGridLocation;
	private Vector2f startLocation;
	private Vector2f step;
	
	public Move(Handler handler, Entity entity, Vector2f toGridLocation) {
		super(handler);
		
		this.entity = entity;
		this.toGridLocation = toGridLocation;
	}
	
	public Move(Handler handler, Entity entity, int x, int z) {
		this(handler, entity, new Vector2f(x, z));
	}

	@Override
	public void start() {
		super.start();
		
		startLocation = entity.getPosition();
		
		step = toGridLocation.subtract(startLocation).divide(toGridLocation.subtract(startLocation).length()).multiply(entity.getMovementSpeed());
	}
	
	@Override
	public void update(float delta) {
		if(Util.withinRange(entity.getPosition(), toGridLocation, .2f)) {
			complete = true;
			entity.setPosition2D(entity.roundPosToGrid());
		} else {
			entity.move(step, delta);
		}
	}
}
