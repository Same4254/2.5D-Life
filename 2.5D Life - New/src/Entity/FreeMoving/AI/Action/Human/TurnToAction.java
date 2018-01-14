package Entity.FreeMoving.AI.Action.Human;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.Action;
import Main.Handler;
import Utils.Util;

public class TurnToAction extends Action {
	private Entity entity;
	private Vector2f target;
	
	public TurnToAction(Handler handler, Entity entity, Vector2f target) {
		super(handler);
		
		this.entity = entity;
		this.target = target;
	}
	
	@Override
	public void update(float delta) {
		entity.setAngle(Util.getAngle(entity.getPosition2D(), target));
		complete = true;
	}
}
