package Entity.FreeMoving.AI.Action.Human.MovementFunction;

import Entity.FreeMoving.Entity;

@FunctionalInterface
public interface Function {
	
	/**
	 * x - this represents the x variable in an equation, not anything to do with world position -> saved over time 
	 */
	public boolean function(Entity entity, float x, float maxX, boolean negative, boolean zAxis, float delta);
}
