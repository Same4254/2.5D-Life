package Entity.WorldObjects.Objects;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Main.Assets;
import Main.Handler;

public class Bed extends MultiTileObject {

	public Bed(Handler handler) {
		super(handler, Assets.bedModel, Assets.bedTexture);

	}

	@Override
	public WorldObject clone() {
		return new Bed(handler);
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
