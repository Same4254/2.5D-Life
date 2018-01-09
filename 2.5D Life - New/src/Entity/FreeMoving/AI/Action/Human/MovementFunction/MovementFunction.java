package Entity.FreeMoving.AI.Action.Human.MovementFunction;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.Action;
import Main.Handler;

public class MovementFunction extends Action {
	public static Function stairMovement = (entity, x, maxX, negative, zAxis, delta) -> {
		if(Math.abs(x) >= Math.abs(maxX))
			return true;//TODO snap to the actual final location
		
		if(zAxis)
			entity.getBody().addZ(!negative ? delta * 2.5f : -delta * 2.5f);
		else
			entity.getBody().addX(!negative ? delta * 2.5f : -delta * 2.5f);
		
		float y;
		
		if(x > 0)
			y = (float) (x - (.5 * Math.sin((2 * Math.PI * x) - 5)) + .48);
		else
			y = (float) (-x + (.5 * Math.sin((2 * Math.PI * x) - 1.3)) + .48);
		entity.getBody().setY(y);
		
		return false;
	};
	
	private Function function;
	private Entity entity;
	private float x;
	
	private boolean negative, zAxis;
	
	public MovementFunction(Handler handler, Entity entity, Function function, boolean negative, boolean zAxis) {
		super(handler);
		
		this.entity = entity;
		this.function = function;
		
		this.negative = negative;
		this.zAxis = zAxis;
	}

	@Override
	public void update(float delta) {
		x += delta * 2.5;
		complete = function.function(entity, x, 4, negative, zAxis, delta);
	}
}
