package Entity.WorldObjects.Objects;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Main.Assets;
import Main.Handler;

public class Stove extends MultiTileObject {

	public Stove(Handler handler) {
		super(handler, Assets.stoveModel, Assets.stoveTexture);

	}

	@Override
	public void update(float delta) {
		
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
		skills.put(Living.Cooking, 100);
	}

	@Override
	protected void initInventory() {
		
	}

	@Override
	public WorldObject clone() { return new Stove(handler); }
}
