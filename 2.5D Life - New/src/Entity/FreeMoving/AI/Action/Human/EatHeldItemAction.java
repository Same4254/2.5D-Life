package Entity.FreeMoving.AI.Action.Human;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.Items.FoodItem;
import Main.Handler;

public class EatHeldItemAction extends MultiAction {
	private Entity entity;
	
	public EatHeldItemAction(Handler handler, Entity entity) {
		super(handler);
		
		this.entity = entity;
	}
	
	@Override
	public void start() {
		if(entity.getInventory().getHeldItem() == null || !(entity.getInventory().getHeldItem() instanceof FoodItem)) {
			complete = true;
			return;
		} else {
			FoodItem food = (FoodItem) entity.getInventory().removeHeldObject();
			entity.getNeedManager().getHunger().add(food.getValue());
			complete = true;
		}
	}
}
