package Entity.WorldObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class FullTileObject extends WorldObject {
	
	public FullTileObject(Vector2f twoDDimension, String name, Shader modelShader) {
		super(twoDDimension, name, modelShader);
	}

	@Override
	public void rotateLeft() {
		body.getRenderProperties().rotate(new Vector3f(0, 90, 0));
	}

	@Override
	public void rotateRight() {
		body.getRenderProperties().rotate(new Vector3f(0, -90, 0));
	}
}
