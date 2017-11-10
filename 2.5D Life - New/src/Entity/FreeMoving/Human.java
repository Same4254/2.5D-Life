package Entity.FreeMoving;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Main.Handler;

public class Human extends Entity {

	public Human(Handler handler, Vector2f twoDLocation, Vector2f twoDDimension, String objPath, String modelTexturePath, Shader modelShader) {
		super(handler, twoDLocation, twoDDimension, objPath, modelTexturePath, modelShader);
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Camera camera) {
		body.render(camera);
	}

}
