package Entity.FreeMoving.AI.Action;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Utils.Util;

public class TurnToAction extends Action {
	private Entity entity;
	private Vector2f target;
	
	public TurnToAction(Entity entity, Vector2f target) {
		this.entity = entity;
		this.target = target;
	}
	
	@Override
	public void update(float delta) {
		entity.setRotation(Util.getAngle(entity.getLocation(), target));
		complete = true;
	}
}
