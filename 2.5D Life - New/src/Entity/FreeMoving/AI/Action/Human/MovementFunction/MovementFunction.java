package Entity.FreeMoving.AI.Action.Human.MovementFunction;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.Action;
import Main.Handler;
import Utils.Util;

public class MovementFunction extends Action {
	public static Function stairMovement = (entity, x, minX, maxX, negative, zAxis, reverse, delta) -> {
		if(!reverse) {
			if(Util.withinRange(Math.abs(x), Math.abs(maxX), .1f))
				return true;//TODO snap to the actual final location
		} else if(Util.withinRange(Math.abs(x), Math.abs(minX), .1f)) {
			return true;
		}
		
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
	private float minX, maxX;
	
	private boolean negative, zAxis, reverse;
	
	public MovementFunction(Handler handler, Entity entity, Function function, float minX, float maxX, boolean negative, boolean zAxis, boolean reverse) {
		super(handler);
		
		this.entity = entity;
		this.function = function;
		
		this.negative = negative;
		this.zAxis = zAxis;
		this.reverse = reverse;
		
		this.minX = minX;
		this.maxX = maxX;
		
		if(reverse)
			x = maxX;
		else
			x = minX;
	}

	@Override
	public void update(float delta) {
		if(!reverse)
			x += delta * 2.5;
		else
			x -= delta * 2.5;
		complete = function.function(entity, x, minX, maxX, negative, zAxis, reverse, delta);
	}
}
