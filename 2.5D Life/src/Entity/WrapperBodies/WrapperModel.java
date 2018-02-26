package Entity.WrapperBodies;

import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMeshLoader;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.Model;

import Utils.AssetLoader;

public class WrapperModel {
	private Shader shader;
	private ModelData modelData;
	private CollisionMesh collisionMesh;
	
	public WrapperModel(String objPath, String collisionPath, Shader shader) {
		this.shader = shader;
		
		modelData = ModelLoader.loadOBJ(objPath);
		collisionMesh = CollisionMeshLoader.loadObj(collisionPath);
	}
	
	public WrapperModel(String name, Shader shader) {
		this(AssetLoader.MODEL_PATH + name + ".obj", AssetLoader.COLLISION_PATH + name + ".obj", shader);
	}
	
	public Model getModel() {
		Model model = new Model(modelData);
		model.setShader(shader);
		return model;
	}
	public CollisionMesh getCollisionMesh() { return collisionMesh; }
}
