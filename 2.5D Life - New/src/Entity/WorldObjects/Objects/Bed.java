package Entity.WorldObjects.Objects;

import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
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
}
