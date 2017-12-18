package Entity.WorldObjects.Objects;

import Entity.WorldObjects.MultiTileObject;
import Main.Assets;
import Main.Handler;

public class Box extends MultiTileObject {

	public Box(Handler handler) {
		super(handler, Assets.boxModel, Assets.boxTexture);

	}

	@Override
	public Box clone() {
		return new Box(handler);
	}
}
