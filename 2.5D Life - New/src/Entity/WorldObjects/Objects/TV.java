package Entity.WorldObjects.Objects;

import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Main.Assets;
import Main.Handler;

public class TV extends MultiTileObject {
	public TV(Handler handler) {//Need to rotate the position
		super(handler, Assets.tvModel, Assets.tvTexture);
		
	}

	@Override
	public WorldObject clone() {
		return new TV(handler);
	}
}
