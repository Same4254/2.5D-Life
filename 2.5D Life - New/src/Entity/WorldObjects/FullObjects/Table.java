package Entity.WorldObjects.FullObjects;

import Entity.WorldObjects.FullTileObject;
import Main.Assets;
import Main.Handler;

public class Table extends FullTileObject {

	public Table(Handler handler) {
		super(handler, Assets.tableModel, Assets.tableTexture);
		
	}
}
