package Entity.WorldObjects.SubObjects;

import Entity.WorldObjects.SubTileObject;
import Entity.WorldObjects.FullObjects.Table;
import Main.Assets;
import Main.Handler;

public class Wall extends SubTileObject {

	public Wall(Handler handler) {
		super(handler, Assets.wallModel, Assets.wallTexture);
	}

	@Override
	public Wall clone() {
		Wall temp = new Wall(handler);
		temp.cleanUp();
		temp.setBody(body.clone());
		handler.getGame().getPhysicsEngine().add(temp.getBody().getStaticBody());

		temp.setSubX(subX);
		temp.setSubY(subY);
		temp.setSubWidth(subWidth);
		temp.setSubHeight(subHeight);
		
		return temp;
	}
}
