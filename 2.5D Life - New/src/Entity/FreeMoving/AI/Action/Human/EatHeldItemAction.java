package Entity.FreeMoving.AI.Action.Human;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.Items.FoodItem;
import Main.Handler;

public class EatHeldItemAction extends MultiAction {
	private Entity entity;
	private FoodItem food;
	
	public EatHeldItemAction(Handler handler, Entity entity) {
		super(handler);
		
		this.entity = entity;
	}
	
	@Override
	public void start() {
		super.start();
		
		if(entity.getInventory().getHeldItem() == null || !(entity.getInventory().getHeldItem() instanceof FoodItem)) {
			complete = true;
			return;
		} else {
			food = (FoodItem) entity.getInventory().getHeldItem();
		}
	}
	
	@Override
	public void update(float delta) {
		if(food.getIntegrity() <= 0 || entity.getNeedManager().getHunger().getValue() >= 100) {
			complete = true;
			entity.getInventory().removeHeldObject();
			return;
		} else {
			float before = food.getIntegrity();
			food.degrade(entity.getEatingSpeed() * delta);
			
			float amount = ((before - food.getIntegrity()) / 100) * food.getValue();
			entity.getNeedManager().getHunger().add(amount);
		}
	}
}
