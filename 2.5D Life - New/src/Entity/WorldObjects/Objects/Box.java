package Entity.WorldObjects.Objects;

import Entity.WorldObjects.MultiTileObject;
import Main.Assets;
import Main.Handler;

public class Box extends MultiTileObject{

	public Box(Handler handler) {
		super(handler, Assets.boxModel, Assets.boxTexture);

	}

	@Override
	public Box clone() {
		Box temp = new Box(handler);
		temp.cleanUp();
		temp.setBody(body.clone());
		handler.getGame().getPhysicsEngine().add(temp.getBody().getStaticBody());
		
		return temp;
	}
}
