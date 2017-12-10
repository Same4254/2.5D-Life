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
		Wall temp = new Wall(handler);
		temp.cleanUp();
		temp.setBody(body.clone());
		handler.getGame().getPhysicsEngine().add(temp.getBody().getStaticBody());

		return temp;
	}
}
