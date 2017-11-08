package Entity.WorldObjects.FullObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.FullTileObject;
import Main.Handler;

public class Table extends FullTileObject {

	public Table(Handler handler, Vector2f twoDDimension, String name, Shader modelShader) {
		super(handler, twoDDimension, name, modelShader);
		
	}
}
