package Entity.WorldObjects.Objects;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.PathFinding;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.FreeMoving.AI.Action.Human.FindPlaceToSitAction;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Main.Assets;
import Main.Handler;

public class TV extends MultiTileObject {
	public TV(Handler handler, Lot lot) {
		super(handler, lot, Assets.tvModel, Assets.tvTexture);
		
		
//		body.getSoundSource().setVolume(1000000);
//		body.getSoundSource().setLooping(true);
//		body.getSoundSource().play(Assets.bounceSoundBuffer);
	}
	
	@Override
	public void update(float delta) {
		
	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		if(reason == Living.Entertainment) {
			entity.addAction(new WatchTV(handler, entity, this));
		}
			
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		return null;
	}

	@Override
	protected void initSkillsAndNeeds() {
		needs.put(Living.Entertainment, 100);
	}

	@Override
	protected void initInventory() {
		
	}
	
	@Override
	public WorldObject clone() { return new TV(handler, lot); }

	@Override
	public Vector2f[] getApplianceLocations() {
		return null;
	}
}

class WatchTV extends MultiAction {
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
		
		subActions.add(new FindPlaceToSitAction(handler, entity, PathFinding.getEffectiveArea(tv, new Vector2f(5), false)));
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
		super.start();
	}
	
	@Override
	public void update(float delta) {
		if(entity.getNeedManager().getEntertainment().getValue() > 95)
			complete = true;
		else
			entity.getNeedManager().getEntertainment().add(10 * delta);
		
//		entity.getNeedManager().getEntertainment().add(10 * delta);
//		if(entity.getNeedManager().getLowest().asEnum() != Living.Entertainment) 
//			complete = true;
	}
}