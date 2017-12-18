package Entity.WorldObjects.Objects;

import Entity.WorldObjects.MultiTileObject;
import Main.Assets;
import Main.Handler;

public class Fridge extends MultiTileObject {
	
	public Fridge(Handler handler) {
		super(handler, Assets.fridgeModel, Assets.fridgeTexture);

	}

	@Override
	public Fridge clone() {
		return new Fridge(handler);
	}
}
