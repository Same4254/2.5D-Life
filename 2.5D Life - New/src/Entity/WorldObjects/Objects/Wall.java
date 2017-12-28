package Entity.WorldObjects.Objects;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.TileObject;
import Entity.WorldObjects.Items.Item;
import Main.Assets;
import Main.Handler;

public class Wall extends TileObject {

	public Wall(Handler handler) {
		super(handler, Assets.wallModel, Assets.wallTexture);
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
	public Wall clone() { return new Wall(handler); }
}
