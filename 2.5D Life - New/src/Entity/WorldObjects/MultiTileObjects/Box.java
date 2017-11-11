package Entity.WorldObjects.MultiTileObjects;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.MultiTileObject;
import Main.Assets;
import Main.Handler;

public class Box extends MultiTileObject{

	public Box(Handler handler) {
		super(handler, Assets.boxModel, Assets.boxTexture);

		System.out.println(body.getDimensions());
	}
}
