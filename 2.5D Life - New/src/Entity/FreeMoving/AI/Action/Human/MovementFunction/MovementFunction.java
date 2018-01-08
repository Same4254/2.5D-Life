package Entity.FreeMoving.AI.Action.Human.MovementFunction;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.Action;
import Main.Handler;

public class MovementFunction extends Action {
	public static Function stairMovement = (entity, x) -> {
		entity.getBody().setX(x);
		
		float y = (float) (x - (.5 * Math.sin((2 * Math.PI * x) - 1.9)) -.025);
//		System.out.println(y);
		entity.getBody().setY(y);
	};
	
	private Function function;
	private Entity entity;
	private float x;
	
	public MovementFunction(Handler handler, Entity entity, Function function) {
		super(handler);
		
		this.entity = entity;
		this.function = function;
	}

	@Override
	public void update(float delta) {
		x += 1 * delta;
		function.function(entity, x);
	}
}
