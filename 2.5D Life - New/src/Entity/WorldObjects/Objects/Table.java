package Entity.WorldObjects.Objects;

import Entity.WorldObjects.TileObject;
import Main.Assets;
import Main.Handler;

public class Table extends TileObject {

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
