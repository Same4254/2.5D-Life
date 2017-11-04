package World.Tiles;

import com.Engine.PhysicsEngine.Bodies.StaticBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector3f;

import Main.Handler;

public abstract class Tile extends StaticBody {
	protected Model model;
	protected Handler handler;
	
	public Tile(Handler handler, CollisionMesh col, Model model, Vector3f position){
		super(col);
		this.handler = handler;
		this.model = model;
		
		setPosition(position);
		handler.getGame().getPhysicsEngine().add(this);
	}
	
	public abstract void render(Camera camera);
	public abstract void update();
}