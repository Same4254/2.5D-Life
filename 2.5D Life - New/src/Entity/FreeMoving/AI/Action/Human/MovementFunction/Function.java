package Entity.FreeMoving.AI.Action.Human.MovementFunction;

import Entity.FreeMoving.Entity;

@FunctionalInterface
public interface Function {
	public void function(Entity entity, float x);
}
