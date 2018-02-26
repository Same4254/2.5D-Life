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

public class GoToNextFloorAction extends MultiAction {
	private Entity entity;
	private Stairs stair;
	
	public GoToNextFloorAction(Handler handler, Entity entity) {
		super(handler);
		
		this.entity = entity;
	}
	
	public GoToNextFloorAction(Handler handler, Entity entity, Stairs stair) {
		this(handler, entity);
		
		this.stair = stair;
	}

	@Override
	public void start() {
		super.start();

		Lot lot = handler.getWorld().getLot(entity.getPosition2D());
		Floor floor = lot.getFloor(entity.getPosition3D());

		if(stair == null) {
			ArrayList<Stairs> stairs = new ArrayList<>();
			for(int x = 0; x < lot.getWidth(); x++) {
				for(int y = 0; y < lot.getHeight(); y++) {
					WorldObject object = floor.getTiles()[x][y].getObject();
					if(object != null && object instanceof Stairs) {
						stairs.add((Stairs) object);
					}
				}
			}
			
			stairs.sort((o1, o2) -> {
				if(o1.getPosition2D().distance(entity.getPosition2D()) < o2.getPosition2D().distance(entity.getPosition2D()))
					return -1;
				return 1;
			});
			
			if(!stairs.isEmpty()) {
				stair = stairs.get(0);
			} else {
				complete = true;
				return;
			}
		}
		
		subActions.add(new MoveToAction(handler, stair, entity));
		
		Vector2f front = stair.getFront();
		if(front.x > 0)
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, true, false, false));
		else if(front.y < 0)
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, false, true, false));
		else if(front.x < 0)
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, false, false, false));
		else if(front.y > 0)
			subActions.add(new MovementFunction(handler, entity, MovementFunction.stairMovement, floor.getPosition().y, floor.getPosition().y + Lot.floorHeight, true, true, false));
		
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
