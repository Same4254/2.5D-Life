package Entity.WrapperBodies;

import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMeshLoader;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.Shaders.Default.Model;

public class WrapperModel {
	private Model model;
	private CollisionMesh collisionMesh;
	
	public WrapperModel(String objPath) {
		model = new Model(ModelLoader.loadOBJ(objPath));
		collisionMesh = CollisionMeshLoader.loadObj(objPath);
	}
	
	public Model getModel() { return model; }
	public CollisionMesh getCollisionMesh() { return collisionMesh; }
}
