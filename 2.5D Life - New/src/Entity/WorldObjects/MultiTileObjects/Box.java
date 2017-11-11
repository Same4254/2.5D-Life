package Entity.WorldObjects.MultiTileObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.MultiTileObject;
import Main.Handler;

public class Box extends MultiTileObject{

	public Box(Handler handler, Vector2f twoDDimension, String name, Shader modelShader) {
		super(handler, twoDDimension, name, modelShader);

		body.getRenderProperties().getTransform().setScale(new Vector3f(.5, 1, 1));
		
	}
}
