package World;

import java.util.ArrayList;

import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.Entity;
import Entity.WorldObjects.Lot.Lot;
import Input.CameraMovement;
import Main.Handler;

public class World {
	//TODO Ignore not tiles when trying to pick location for object?
	
	private Handler handler;

	private Camera camera;
	private CameraMovement cameraMovement;
	
	private DefaultShader shader;
	private ArrayList<Light> sun;
	
	private Lot testLot, anothaOne;
	
	private ArrayList<Entity> entities;
	
	public World(Handler handler) {
		this.handler = handler;
		
		camera = new Camera(70, (float) handler.getWidth() / (float) handler.getHeight(), 0.3f, 1000);
		cameraMovement = new CameraMovement(handler, camera, new Vector3f(7, 20, 24), 10, 10, .15f);
		
		shader = new DefaultShader();
		shader.getRenderer().usingFrustumCulling(false);
		
		sun = new ArrayList<>();
		
		entities = new ArrayList<>();
	}
	
	public void init() {
		testLot = new Lot(handler, new Vector2f(), 20, 20);
		anothaOne = new Lot(handler, new Vector2f(-25, 0), 5, 6);
		
		
		
		sun.add(new Light(new Vector3f(10, 35, 10), new Vector3f(1), new Vector3f(.8, 0, 0)));
		
		testLot.enableEdit();
	}
	
	public void update(float delta) {
		testLot.update(delta);
		anothaOne.update(delta);

		Vector3f thing = new Vector3f(anothaOne.getPosition(), 0).rotate(new Vector3f(0, 0, .1));
		anothaOne.getPosition().x = thing.x;
		anothaOne.getPosition().y = thing.y;
		
		cameraMovement.update(delta);
	}
	
	public void render() {
		testLot.render(camera);
		anothaOne.render(camera);
		
		shader.bind();
		shader.loadLights(sun);
	}

	public Camera getCamera() { return camera; }
	public CameraMovement getCameraMovement() { return cameraMovement; }
	public DefaultShader getShader() { return shader; }
}
