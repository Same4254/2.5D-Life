package Entity.FreeMoving;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;

import Entity.WrapperBodies.WrapperModel;
import Main.Handler;

public class Human extends Entity {

	public Human(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Camera camera) {
		body.render(camera);
	}

}
