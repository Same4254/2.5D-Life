package Entity.WorldObjects.Objects;

import Entity.WorldObjects.TileObject;
import Main.Assets;
import Main.Handler;

public class Wall extends TileObject {

	public Wall(Handler handler) {
		super(handler, Assets.wallModel, Assets.wallTexture);
	}

	@Override
	public Wall clone() {
		return new Wall(handler);
	}
}
