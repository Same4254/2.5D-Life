package Entity.WorldObjects.FullObjects;

import Entity.WorldObjects.FullTileObject;
import Entity.WorldObjects.SubObjects.Wall;
import Main.Assets;
import Main.Handler;

public class Table extends FullTileObject {

	public Table(Handler handler) {
		super(handler, Assets.tableModel, Assets.tableTexture);
		
	}

	@Override
	public Table clone() {
		Table temp = new Table(handler);
		temp.cleanUp();
		temp.setBody(body.clone());
		handler.getGame().getPhysicsEngine().add(temp.getBody().getStaticBody());
		
		return temp;
	}
}
