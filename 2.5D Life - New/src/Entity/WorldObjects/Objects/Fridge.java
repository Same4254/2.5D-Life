package Entity.WorldObjects.Objects;

import Entity.FreeMoving.AI.Living.Needs.Hunger;
import Entity.WorldObjects.MultiTileObject;
import Main.Assets;
import Main.Handler;

public class Fridge extends MultiTileObject {

	public Fridge(Handler handler) {
		super(handler, Assets.boxModel, Assets.boxTexture);
	}

	@Override
	public Fridge clone() {
		return new Fridge(handler);
	}
}
