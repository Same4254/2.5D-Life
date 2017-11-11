package Entity.WrapperBodies;

import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMeshLoader;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.Model;

public class WrapperModel {
	private Shader shader;
	private ModelData modelData;
	private CollisionMesh collisionMesh;
	
	public WrapperModel(String objPath, Shader shader) {
		this.shader = shader;
		
		modelData = ModelLoader.loadOBJ(objPath);
		collisionMesh = CollisionMeshLoader.loadObj(objPath);
	}
	
	public Model getModel() {
		Model model = new Model(modelData);
		model.setShader(shader);
		return model;
	}
	public CollisionMesh getCollisionMesh() { return collisionMesh; }
}
