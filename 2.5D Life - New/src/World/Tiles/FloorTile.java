package World.Tiles;

import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector3f;

import Main.Handler;

public class FloorTile extends Tile {
	private DefaultRenderProperties renderProperties;
	
	public FloorTile(Handler handler, CollisionMesh col, Model model, Vector3f position) {
		super(handler, col, model, position);
		
		renderProperties = new DefaultRenderProperties(new Transform(position, new Vector3f(), new Vector3f(1)), 200, .1f, 0);
	}

	@Override
	public void render(Camera camera) {
		model.render(renderProperties, camera);
	}

	@Override
	public void update() {
		
	}

}
