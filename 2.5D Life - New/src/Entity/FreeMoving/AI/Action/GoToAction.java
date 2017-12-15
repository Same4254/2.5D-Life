package Entity.FreeMoving.AI.Action;

import java.util.ArrayList;
import java.util.Collections;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.PathFinding;
import Entity.WorldObjects.Lot.Lot;
import Utils.Util;

public class GoToAction extends Action {
	private ActionQueue movePoints;
	private Lot lot;
	private Entity entity;
	private Vector2f toGridLocation;
	
	public GoToAction(Lot lot, Entity entity, Vector2f toGridLocation) {
		this.lot = lot;
		this.entity = entity;
		this.toGridLocation = toGridLocation;
		
		movePoints = new ActionQueue();
	}
	
	public GoToAction(Lot lot, Entity entity, int x, int z) {
		this(lot, entity, new Vector2f(x, z));
	}

	@Override
	public void start() {
		super.start();
		
		ArrayList<Vector2f> path = PathFinding.simplifyPath(PathFinding.aStar(entity, lot, entity.getLocation(), toGridLocation));
		if(path == null)
			return;
		
		for(Vector2f point : path)   
			movePoints.add(new Move(entity, point));
	}
	
	@Override
	public void update(float delta) {
		if(started && movePoints.getActions().isEmpty())
			complete = true;
		else
			movePoints.update(delta);
	}
}

class Move extends Action {
	private Entity entity;
	
	private Vector2f toGridLocation;
	private Vector2f startLocation;
	private Vector2f step;
	
	public Move(Entity entity, Vector2f toGridLocation) {
		this.entity = entity;
		this.toGridLocation = toGridLocation;
	}
	
	public Move(Entity entity, int x, int z) {
		this(entity, new Vector2f(x, z));
	}

	@Override
	public void start() {
		super.start();
		
		startLocation = entity.getLocation();
		
		step = toGridLocation.subtract(startLocation).divide(toGridLocation.subtract(startLocation).length()).multiply(entity.getMovementSpeed());
	}
	
	@Override
	public void update(float delta) {
		if(Util.withinRange(entity.getLocation(), toGridLocation, .2f)) {
			complete = true;
			entity.getBody().setPosition2D(entity.getBody().roundPosToGrid());
		} else {
			entity.move(step, delta);
		}
	}
}
