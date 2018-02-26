package Entity.FreeMoving.AI.Action.Human;

import java.util.ArrayList;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.PathFinding;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Main.Handler;
import Utils.Util;

public class GoToAction extends MultiAction {
	protected Floor floor;
	protected Entity entity;
	
	protected Vector2f start;
	protected Vector2f toGridLocation;
	
	public GoToAction(Handler handler, Floor floor, Entity entity, Vector2f start, Vector2f target) {
		super(handler);
		
		this.handler = handler;
		this.floor = floor;
		this.entity = entity;
		this.start = start;
		this.toGridLocation = target;
	}
	
	public GoToAction(Handler handler, Lot lot, Entity entity, Vector2f toGridLocation) {
		this(handler, lot.getFloor(entity.getPosition3D()), entity, entity.getPosition2D(), toGridLocation);
	}
	
	@Override
	public void start() {
		super.start();
		
		ArrayList<Vector2f> path = PathFinding.simplifyPath(PathFinding.aStar(entity, floor, entity.getPosition2D(), toGridLocation));
		if(path == null)
			return;
		
//		System.out.println("-------\n\n");
		
		for(Vector2f point : path) {
			subActions.add(new Move(handler, entity, point));
		}
	}
}