package Entity.WorldObjects.Objects;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.Items.Item;
import Main.Assets;
import Main.Handler;

public class Box extends MultiTileObject {

	public Box(Handler handler) {
		super(handler, Assets.boxModel, Assets.boxTexture);

	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		return null;
	}

	@Override
	protected void initSkillsAndNeeds() {
		
	}

	@Override
	protected void initInventory() {
		
	}
	
	@Override
	public Box clone() { return new Box(handler); }
}
