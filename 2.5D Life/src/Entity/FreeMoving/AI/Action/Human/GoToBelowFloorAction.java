package Entity.FreeMoving.AI.Action.Human;

import java.util.ArrayList;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.FreeMoving.AI.Action.Human.MovementFunction.MovementFunction;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Stairs;
import Main.Handler;

public class GoToBelowFloorAction extends MultiAction {
	private Entity entity;
	
	public GoToBelowFloorAction(Handler handler, Entity entity) {
		super(handler);
		
		this.entity = entity;
	}

	@Override
	public void start() {
		super.start();
		
		if(entity.getPosition3D().y < Lot.floorHeight) {
			complete = true;
			return;
		}
		
		Lot lot = handler.getWorld().getLot(entity.getPosition2D());
		Floor floor = lot.getFloor(entity.getPosition3D().subtract(0, Lot.floorHeight, 0));
		
		ArrayList<WorldObject> stairs = new ArrayList<>();
		for(int x = 0; x < lot.getWidth(); x++) {
			for(int y = 0; y < lot.getHeight(); y++) {
				WorldObject object = floor.getTiles()[x][y].getObject();
				if(object != null && object instanceof Stairs) {
					stairs.add(object);
				}
			}
		}
		
		stairs.sort((o1, o2) -> {
			if(o1.getPosition2D().distance(entity.getPosition2D()) < o2.getPosition2D().distance(entity.getPosition2D()))
				return -1;
			return 1;
		});
		
		WorldObject stair = null;
		
		if(!stairs.isEmpty()) {
			stair = stairs.get(0);
		} else {
			complete = true;
			return;
		}
		
		Vector2f front = stair.getFront();
		if(front.x > 0) {
			subActions.add(new GoToAction(handler, lot, entity, stair.getPosition2D()));
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, false, false, true));
		} else if(front.y < 0) {
			subActions.add(new GoToAction(handler, lot, entity, stair.getPosition2D().add(0, stair.getHeight())));
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, false, true, true));
		} else if(front.x < 0) {
			subActions.add(new GoToAction(handler, lot, entity, stair.getPosition2D().add(stair.getWidth(), 0)));
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, true, false, true));
		} else if(front.y > 0) {
			subActions.add(new GoToAction(handler, lot, entity, stair.getPosition2D()));
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, false, true, true));
		}
		
//		if(angle == 0) 
//			front = new Vector2f(getWidth(), 0);
//		if(angle == 90) 
//			front = new Vector2f(0, -1);
//		if(angle == 180)
//			front = new Vector2f(-1, 0);
//		if(angle == 270)
//			front = new Vector2f(0, getHeight());
	}
}
