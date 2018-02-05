package World;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.Engine.PhysicsEngine.Render.Vector.VectorModel;
import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.Human;
import Entity.FreeMoving.Player;
import Entity.FreeMoving.AI.Living.Viewer.Viewer;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Input.CameraMovement;
import Main.Assets;
import Main.Game;
import Main.Handler;
import World.Tiles.Tile;
import World.Tiles.Render.TileInstanceModel;

public class World {
	private Handler handler;

	private Camera camera;
	private CameraMovement cameraMovement;
	
	private ArrayList<Light> sun;
	private ArrayList<Lot> lots;
	
//	private Model model = Assets.wallModel.getModel();
	
	private Player player;
	private boolean testPlayer = true; // Test variable to quickly disable player from the game
	
	private Human human;
	private boolean testHuman = false; // Test variable to quickly disable test human from the game 
	
	private Viewer needViewer;
	private boolean viewViewer = false; // Test variable to quickly disable need viewer
	
	public World(Handler handler) {
		this.handler = handler;
		
		camera = new Camera(70, (float) handler.getWidth() / (float) handler.getHeight(), 0.3f, 1000);
		cameraMovement = new CameraMovement(handler, camera, new Vector3f(7, 20, 24), 10, 10, .15f);
		
		sun = new ArrayList<>();
		lots = new ArrayList<>();
	}
	
	public void init() {
		VectorModel.init(Game.physicsShader);
		
		lots.add(new Lot(handler, new Vector3f(), new Vector2f(10)));

		sun.add(new Light(new Vector3f(10, 35, 10), new Vector3f(1), new Vector3f(.8, 0, 0)));
		
		lots.get(0).enableEdit();
		

		if(testPlayer) {
			player = new Player(handler, Assets.playerModel, Assets.playerTexture, "Player");
			player.setPosition2D(0, 0);
		}

		if(testHuman) {
			human = new Human(handler, Assets.playerModel, Assets.playerTexture, "Bob");
			human.setPosition2D(8, 8);
		}
		
		if(viewViewer) {
			if(testHuman)
				needViewer = new Viewer(handler, human);
			if(testPlayer)
				needViewer = new Viewer(handler, player);
		}
		
//		System.out.println(player.getCornerPosition2D());
		
//		place(new Wall(handler, lots.get(0)), new Vector2f(.5f, .5f));
//		model.setTexture(Assets.wallTexture);
	}
	
	public boolean place(WorldObject object, Vector3f position, float angle) {
		object.setAngle(angle);
		object.setPosition3D(position);
		return object.addToTile(object.getLot().getTile(object.getPosition3D().subtract(object.getWidth() / 2, 0, object.getHeight() / 2)));
	}
	
	public boolean place(WorldObject object, Vector2f position) {
		object.setPosition2D(position);
		return object.addToTile(object.getLot().getTile(object.getPosition3D().subtract(object.getWidth() / 2, 0, object.getHeight() / 2)));
	}
	
	public boolean place(WorldObject object) {
		return object.addToTile(object.getLot().getTile(object.getPosition3D().subtract(object.getWidth() / 2, 0, object.getHeight() / 2)));
	}
	
	public Lot getLot(Vector2f position) {
		return getLot((int) position.x, (int) position.y);
	}
	
	public Lot getLot(int x, int y) {
		for(Lot lot : lots) {
			if(x >= lot.getPosition().x && x <= lot.getPosition().x + lot.getWidth()) {
				if(y >= lot.getPosition().y && y <= lot.getPosition().y + lot.getHeight()) {
					return lot;
				}
			}
		}
		return null;
	}
	
	public void update(float delta) {
		cameraMovement.update(delta);
		
		for(Lot lot : lots)
			lot.update(delta);
		
		if(testPlayer)
			player.update(delta);
		if(testHuman)
			human.update(delta);
		if(viewViewer)
			needViewer.update();
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_APOSTROPHE)) {
			System.out.println("Dim: " + player.getDimensions2D());
//			System.out.println("Pos: " + player.getCornerPosition2D());
		}
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_NUMPAD0)) {
			player.getBody().setAngle(player.getBody().getRenderProperties().getTransform().getRotation().y + 90);
			player.setPosition2D(new Vector2f());
		}
		
//		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_SPACE)) {
//			Tile[][] tiles = getTestLot().getFloorTiles(0);
//			
//			for(Tile[] temp : tiles) {
//				for(Tile tile : temp) {
//					if(tile.containsAnything())
//						System.out.print(1);
//					else
//						System.out.print(0);
//				}
//				System.out.println();
//			}
//		}
	}
	
	public void render() {
		for(Lot lot : lots)
			lot.render();
		
		if(viewViewer)
			needViewer.render();
		
		if(testPlayer)
			player.render();
		if(testHuman)
			human.render();
		
//		model.render(new DefaultRenderProperties(new Transform(new Vector3f(0, 6, 0), new Vector3f(), new Vector3f(1))));

		Assets.defaultShader.bind();
		Assets.defaultShader.loadLights(sun);
		TileInstanceModel.TILE_SHADER.bind();
		TileInstanceModel.TILE_SHADER.loadLights(sun);
	}

	public Lot getTestLot() {
		return lots.get(0);
	}
	
	public Camera getCamera() { return camera; }
	public CameraMovement getCameraMovement() { return cameraMovement; }
}
