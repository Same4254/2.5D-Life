package Entity.FreeMoving.AI;

import java.util.ArrayList;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.Human.GoToNextFloorAction;
import Entity.FreeMoving.AI.Action.Human.Move;
import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Stairs;
import Main.Handler;
import Utils.Util;

public class PathFinding2 {
	/**
	 * Check the current floor to see if the y values match
	 * 		if not 
	 * 			find any stairs
	 * 				no stairs? -> not that way
	 * 			check the next floor on all stairs
	 * 		else, check to see if path possible on that same floor
	 * 			possible? -> Trace back actions
	 * 			check stairs
	 * compare trace backs by their overall distance
	 * 
	 */
	public static ArrayList<Action> findPath(Handler handler, Entity entity, Lot lot, Vector3f target) {
		System.out.println("Checking Path From: " + target);
		
		return getShortest(checkFloor(handler, entity, lot.getFloor(entity.getPosition3D()), new ArrayList<Stairs>(), entity.getPosition2D(), target));
	}
	
	private static ArrayList<ArrayList<Action>> checkFloor(Handler handler, Entity entity, Floor floor, ArrayList<Stairs> checked, Vector2f from, Vector3f target) {
		System.out.println("--------------");
		ArrayList<ArrayList<Action>> paths = new ArrayList<>();
		System.out.println("Starting To Check floor: " + floor.getPosition().y / Lot.floorHeight);
		System.out.println("From: " + from);
		
		System.out.println("Checking if the path is on this floor...");
		if(target.y == floor.getPosition().y) {//Check if the target is on this floor, and if it is possible to get there
			System.out.println("Running A Star....");
			System.out.println("From: " + from);
			System.out.println("To: " + Util.to2D(target));
			System.out.println("THIS ONE");
			ArrayList<Action> path = getPath(handler, entity, floor, from, Util.to2D(target));
			if(path != null) {
				paths.add(path);
				return paths;
			}
		}
		System.out.println("Nope");
		
		System.out.println("Going To Check The Next Floor Up");
		if(floor.getPosition().y < Lot.floorCount * Lot.floorHeight) {//Check Next Floor Up
			System.out.println("Checking All of the stairs On this floor going up");
			for(Stairs stair : findStairs(floor)) {
				if(checked.contains(stair))
					continue;

				System.out.println("Adding stair to checked list");
				ArrayList<Stairs> tempChecked = new ArrayList<>();
				for(Stairs s : checked) {
					tempChecked.add(s);
				}
				
				tempChecked.add(stair);
				
				System.out.println("Checking to see that it is possible to get to these stairs at: " + stair.getPosition2D().add(stair.getFront()));
				ArrayList<Action> path = getPath(handler, entity, floor, from, stair.getPosition2D().add(stair.getFront()));
				if(path != null) {
					System.out.println("Adding the results of the next floor to this floor's results");
					for(ArrayList<Action> actions : checkFloor(handler, entity, floor.getLot().getFloor(floor.getPosition().add(0, Lot.floorHeight, 0)), tempChecked, stair.getTopLeftPosition2D(), target)) {
						actions.add(0, new GoToNextFloorAction(handler, entity, stair));
						paths.add(actions);
					}
				}
			}
		}
		
		if(floor.getPosition().y > 0) {
			
		}
		
		return paths;
	}
	
	private static ArrayList<Action> getPath(Handler handler, Entity entity, Floor floor, Vector2f from, Vector2f to) {
		ArrayList<Action> actions = new ArrayList<>();
		ArrayList<Vector2f> path = PathFinding.simplifyPath(PathFinding.aStar(entity, floor, from, to));
		if(path == null)
			return null;
		
		for(Vector2f point : path) {
			System.out.println(point);
			actions.add(new Move(handler, entity, point));
		}
		
		return actions;
	}
	
	private static ArrayList<Stairs> findStairs(Floor floor) {
		ArrayList<Stairs> stairs = new ArrayList<>();
		
		for(int x = 0; x < floor.getTiles().length; x++) {
			for(int y = 0; y < floor.getTiles()[x].length; y++) {
				if(floor.getTiles()[x][y] != null && floor.getTiles()[x][y].getObject() instanceof Stairs) {
					if(!stairs.contains(floor.getTiles()[x][y].getObject()))
						stairs.add((Stairs) floor.getTiles()[x][y].getObject());
				}
			}
		}
		
		return stairs;
	}
	
	private static ArrayList<Action> getShortest(ArrayList<ArrayList<Action>> lists) {
		if(lists.size() > 0)
			return lists.get(0);
		return null;
	}
}
