package Entity.WorldObjects.Objects;

import com.Engine.PhysicsEngine.Render.PhysicsRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Main.Assets;
import Main.Game;
import Main.Handler;

public class TV extends MultiTileObject {

	private Model collision;
	
	public TV(Handler handler) {//Need to rotate the position
		super(handler, Assets.tvModel, Assets.tvTexture);
		
		collision = new Model(Assets.tvModel.getModel().getModelData());
		collision.setShader(Game.physicsShader);
	}

	@Override
	public WorldObject clone() {
		return new TV(handler);
	}
	
	public void render() {
		body.render();
		collision.render(new PhysicsRenderProperties(new Transform(body.getStaticBody().getPosition(), body.getStaticBody().getRotation(), body.getStaticBody().getScale()), new Vector3f(0, 1, 0), false));
	}
}
