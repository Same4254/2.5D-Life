package Entity.WorldObjects.SubObjects;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.SubTileObject;
import Main.Assets;
import Main.Handler;

public class Wall extends SubTileObject {

	public Wall(Handler handler) {
		super(handler, Assets.wallModel, Assets.wallTexture);
	}
}
