package Entity.WorldObjects.MultiTileObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.MultiTileObject;
import Main.Handler;

public class Box extends MultiTileObject{

	public Box(Handler handler, Vector2f twoDDimension, String name, Shader modelShader) {
		super(handler, twoDDimension, name, modelShader);

	}
}
