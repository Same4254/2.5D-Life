package World;

import java.util.ArrayList;

import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMeshLoader;
import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector3f;

import Input.CameraMovement;
import Main.Handler;
import Utils.ImageLoader;
import World.Tiles.FloorTile;
import World.Tiles.Tile;

public class World {
	private Handler handler;

	private Camera camera;
	private CameraMovement cameraMovement;
	
	private DefaultShader shader;
	private ArrayList<Light> sun;
	
	private Tile[][] tiles;
	
	public World(Handler handler) {
		this.handler = handler;
		
		camera = new Camera(70, (float) handler.getWidth() / (float) handler.getHeight(), 0.3f, 1000);
		cameraMovement = new CameraMovement(handler, camera, new Vector3f(7, 20, 24), 10, 10, .15f);
		
		shader = new DefaultShader();
		shader.getRenderer().usingFrustumCulling(false);
		
		sun = new ArrayList<>();
		
		tiles = new Tile[20][20];
	}
	
	public void init() {
		sun.add(new Light(new Vector3f(10, 35, 10), new Vector3f(1), new Vector3f(.8, 0, 0)));
		
		Model goldModel = new Model(ModelLoader.loadOBJ("res/models/Tile.obj"));
		goldModel.setTexture(ImageLoader.loadTexture("/textures/Gold.png"));
		goldModel.setShader(shader);
		
		CollisionMesh tileCol = CollisionMeshLoader.loadObj("res/models/Tile.obj");
		
		for(int x = 0; x < tiles.length; x++)
			for(int y = 0; y < tiles[x].length; y++)
				tiles[x][y] = new FloorTile(handler, tileCol, goldModel, new Vector3f(x, 0, y));
	}
	
	public void update(float delta) {
		for(Tile[] t : tiles)
			for(Tile tile : t)
				tile.update();
		
		cameraMovement.update(delta);
	}
	
	public void render() {
		for(Tile[] t : tiles)
			for(Tile tile : t)
				tile.render(camera);
		
		shader.bind();
		shader.loadLights(sun);
	}

	public Camera getCamera() { return camera; }
	public CameraMovement getCameraMovement() { return cameraMovement; }
	public DefaultShader getShader() { return shader; }
}
