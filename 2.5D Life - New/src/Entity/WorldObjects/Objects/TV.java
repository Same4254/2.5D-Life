package Entity.WorldObjects.Objects;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Main.Assets;
import Main.Handler;

public class TV extends MultiTileObject {
	public TV(Handler handler) {
		super(handler, Assets.tvModel, Assets.tvTexture);
		
	}

	@Override
	public WorldObject clone() {
		return new TV(handler);
	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		return null;
	}
}
