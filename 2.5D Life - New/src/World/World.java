package World;

import java.util.ArrayList;

import com.Engine.PhysicsEngine.Render.Vector.VectorModel;
import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.Player;
import Entity.WorldObjects.Lot.Lot;
import Input.CameraMovement;
import Main.Assets;
import Main.Game;
import Main.Handler;

public class World {
	private Handler handler;

	private Camera camera;
	private CameraMovement cameraMovement;
	
	private ArrayList<Light> sun;
	
	private Lot testLot, anothaOne;
	
	private Player player;
	
	public World(Handler handler) {
		this.handler = handler;
		
		camera = new Camera(70, (float) handler.getWidth() / (float) handler.getHeight(), 0.3f, 1000);
		cameraMovement = new CameraMovement(handler, camera, new Vector3f(7, 20, 24), 10, 10, .15f);
		
		sun = new ArrayList<>();
	}
	
	public void init() {
		VectorModel.init(Game.physicsShader);
		
		testLot = new Lot(handler, new Vector2f(), 100, 100);
		anothaOne = new Lot(handler, new Vector2f(-25, 0), 5, 6);

		player = new Player(handler, Assets.playerModel, Assets.playerTexture);
		player.getBody().setPosition2D(2, 2);
		
		sun.add(new Light(new Vector3f(10, 35, 10), new Vector3f(1), new Vector3f(.8, 0, 0)));

		testLot.enableEdit();
	}
	
	public void update(float delta) {
		testLot.update(delta);
		anothaOne.update(delta);
		
		player.update(delta);
		
		Vector3f thing = new Vector3f(anothaOne.getPosition(), 0).rotate(new Vector3f(0, 0, .1));
		anothaOne.getPosition().x = thing.x;
		anothaOne.getPosition().y = thing.y;
		
		cameraMovement.update(delta);
	}
	
	public void render() {
		testLot.render();
		anothaOne.render();
		
		player.render();
		
		Assets.defaultShader.bind();
		Assets.defaultShader.loadLights(sun);
	}

	public Camera getCamera() { return camera; }
	public CameraMovement getCameraMovement() { return cameraMovement; }
	
	public Lot getTestLot() { return testLot; }
}
