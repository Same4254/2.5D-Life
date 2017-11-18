package Entity.FreeMoving;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WrapperBodies.WrapperModel;
import Main.Handler;

public class Human extends Entity {
	
	public Human(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);
		
		movementSpeed = new Vector2f(8);
	}

	@Override
	public void update(float delta) {
		actionQueue.update(delta);
	}

	@Override
	public void render(Camera camera) {
		body.render(camera);
	}

}
