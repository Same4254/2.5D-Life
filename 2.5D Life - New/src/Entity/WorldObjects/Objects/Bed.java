package Entity.WorldObjects.Objects;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.FreeMoving.AI.Action.Human.MoveToAction;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Main.Assets;
import Main.Handler;

public class Bed extends MultiTileObject {

	public Bed(Handler handler, Lot lot) {
		super(handler, lot, Assets.bedModel, Assets.bedTexture);

	}

	@Override
	public void update(float delta) {

	}
	
	@Override
	public Action getAction(Entity entity, Living reason) {
		if(reason == Living.Sleep)
			return new GoToBedAction(handler, entity, this);
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		return null;
	}

	@Override
	protected void initSkillsAndNeeds() {
		needs.put(Living.Sleep, 100);
	}

	@Override
	protected void initInventory() {
		
	}
	
	@Override
	public WorldObject clone() { return new Bed(handler, lot); }

	@Override
	public Vector2f[] getApplianceLocations() {
		return null;
	}
}

class GoToBedAction extends MultiAction {
	private Entity entity;
	private Bed bed;
	
	public GoToBedAction(Handler handler, Entity entity, Bed bed) {
		super(handler);
		
		this.entity = entity;
		this.bed = bed;
	}
	
	@Override 
	public void start() {
		super.start();
		
		subActions.add(new MoveToAction(handler, bed, entity));
		subActions.add(new SleepSubAction(handler, bed, entity));
	}
}

class SleepSubAction extends Action {
	private Entity entity;
	private Bed bed;
	
	public SleepSubAction(Handler handler, Bed bed, Entity entity) {
		super(handler);
		
		this.bed = bed;
		this.entity = entity;
	}

	@Override
	public void update(float delta) {
		if(entity.getNeedManager().getSleep().getValue() > 95) 
			complete = true;
		else
			entity.getNeedManager().getSleep().add(8 * delta);
	}
}