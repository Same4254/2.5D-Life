package Entity.FreeMoving.AI.Action.Human;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.PathFinding;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.Objects.TV;
import Main.Handler;

public class WatchTV extends MultiAction {
	private Entity entity;
	private TV tv;
	
	public WatchTV(Handler handler, Entity entity, TV tv) {
		super(handler);
		
		this.entity = entity;
		this.tv = tv;
	}

	@Override
	public void start() {
		super.start();
		
		subActions.add(new FindPlaceToSitAction(handler, entity, PathFinding.getEffectiveArea(handler.getWorld().getLot(entity.getPosition()), tv, new Vector2f(5), false)));
		subActions.add(new Watch(handler, entity, tv));
	}
}


class Watch extends Action { 
	private Entity entity;
	private TV tv;
	
	public Watch(Handler handler, Entity entity, TV tv) { 
		super(handler); 
		
		this.entity = entity;
		this.tv = tv;
	}

	@Override
	public void start() { 
		entity.getNeedManager().getEntertainment().add(40);
		complete = true;
	}
	
	@Override
	public void update(float delta) {
		
	}
}