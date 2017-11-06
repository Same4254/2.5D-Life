package World;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Shaders.Default.DefaultShader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Lot;
import Entity.WorldObjects.SubTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.FullObjects.Table;
import Entity.WorldObjects.SubObjects.Wall;
import Input.CameraMovement;
import Main.Handler;
import World.Tiles.Tile;

public class World {
	private Handler handler;

	private Camera camera;
	private CameraMovement cameraMovement;
	
	private DefaultShader shader;
	private ArrayList<Light> sun;
	
	private WorldObject testObject;
	private Lot testLot, anothaOne;
	
	public World(Handler handler) {
		this.handler = handler;
		
		camera = new Camera(70, (float) handler.getWidth() / (float) handler.getHeight(), 0.3f, 1000);
		cameraMovement = new CameraMovement(handler, camera, new Vector3f(7, 20, 24), 10, 10, .15f);
		
		shader = new DefaultShader();
		shader.getRenderer().usingFrustumCulling(false);
		
		sun = new ArrayList<>();
	}
	
	public void init() {
		testLot = new Lot(handler, new Vector2f(), 20, 20);
		anothaOne = new Lot(handler, new Vector2f(-25, 0), 5, 6);
		
		sun.add(new Light(new Vector3f(10, 35, 10), new Vector3f(1), new Vector3f(.8, 0, 0)));
	}
	
	public void update(float delta) {
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) {
			testObject = new Wall(0, 0,"Wall", shader);
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_2)) {
			testObject = new Table(new Vector2f(1), "Table", shader);
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) {
			testObject = null;
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_RETURN)) {
			testLot.getTiles()[(int) testObject.getBody().getX()][(int) testObject.getBody().getZ()].add(testObject);
			testObject = null;
		}
		
		if(testObject != null) {
			if(testObject instanceof SubTileObject) {
				SubTileObject temp = (SubTileObject) testObject;
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_I)) {
					if(temp.getSubY() - 1 >= 0)
						temp.setSubY(temp.getSubY() - 1);
				}
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_K)) {
					if(temp.getSubY() + 1 + temp.getSubHeight() <= Tile.TILE_RESOLUTION)
						temp.setSubY(temp.getSubY() + 1);
				}
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_J)) {
					if(temp.getSubX() - 1 >= 0)
						temp.setSubX(temp.getSubX() - 1);
				}
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_L)) {
					if(temp.getSubX() + 1 + temp.getSubWidth() <= Tile.TILE_RESOLUTION)
						temp.setSubX(temp.getSubX() + 1);
				}
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_PERIOD)) {
				testObject.rotateRight();
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_COMMA)) {
				testObject.rotateLeft();
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DOWN)) {
				if(testObject.getBody().getZ() + 1 < testLot.getHeight())
					testObject.getBody().setPosition2D(new Vector2f(testObject.getBody().getX(), testObject.getBody().getZ() + 1));
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_UP)) {
				if(testObject.getBody().getZ() - 1 >= 0)
					testObject.getBody().setPosition2D(new Vector2f(testObject.getBody().getX(), testObject.getBody().getZ() - 1));
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_LEFT)) {
				if(testObject.getBody().getX() - 1 >= 0)
					testObject.getBody().setPosition2D(new Vector2f(testObject.getBody().getX() - 1, testObject.getBody().getZ()));
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_RIGHT)) {
				if(testObject.getBody().getX() + 1 < testLot.getWidth())
					testObject.getBody().setPosition2D(new Vector2f(testObject.getBody().getX() + 1, testObject.getBody().getZ()));
			}
		}
		
		testLot.update();
		anothaOne.update();

		Vector3f thing = new Vector3f(anothaOne.getPosition(), 0).rotate(new Vector3f(0, 0, .1));
		anothaOne.getPosition().x = thing.x;
		anothaOne.getPosition().y = thing.y;
		
		cameraMovement.update(delta);
	}
	
	public void render() {
		testLot.render(camera);
		anothaOne.render(camera);
		
		if(testObject != null) {
			if(testObject instanceof SubTileObject) {
				SubTileObject temp = (SubTileObject) testObject;
				temp.getBody().getRenderProperties().getTransform().setTranslation(temp.getBody().getPosition3D().add((float) temp.getSubX() / Tile.TILE_RESOLUTION, 0, (float) temp.getSubY() / Tile.TILE_RESOLUTION));
			}
			testObject.render(camera);
		}
		
		shader.bind();
		shader.loadLights(sun);
	}

	public Camera getCamera() { return camera; }
	public CameraMovement getCameraMovement() { return cameraMovement; }
	public DefaultShader getShader() { return shader; }
}
