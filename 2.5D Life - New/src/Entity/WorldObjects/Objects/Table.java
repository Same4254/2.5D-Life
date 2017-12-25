package Entity.WorldObjects.Objects;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.TileObject;
import Entity.WorldObjects.Items.Item;
import Main.Assets;
import Main.Handler;

public class Table extends TileObject {

	public Table(Handler handler) {
		super(handler, Assets.tableModel, Assets.tableTexture);
		
	}

	@Override
	public Table clone() {
		return new Table(handler);
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
