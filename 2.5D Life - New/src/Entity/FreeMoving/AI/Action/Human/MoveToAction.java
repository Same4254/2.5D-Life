package Entity.FreeMoving.AI.Action.Human;

import Entity.FreeMoving.Entity;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Main.Handler;

public class MoveToAction extends GoToAction {

	private WorldObject worldObject;
	
	public MoveToAction(Handler handler, Lot lot, WorldObject worldObject, Entity entity) {
		super(handler, lot, entity, worldObject.getPosition2D().add(worldObject.getFront()).truncate());

		this.worldObject = worldObject;
	}
	
	@Override
	public void start() {
		super.start();
		
		entity.addAction(new TurnToAction(handler, entity, worldObject.getPosition2D()));
	}
}
