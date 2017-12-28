package World;

import java.util.ArrayList;

import com.Engine.PhysicsEngine.Render.Vector.VectorModel;
import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.Human;
import Entity.FreeMoving.Player;
import Entity.FreeMoving.AI.PathFinding;
import Entity.FreeMoving.AI.Living.Viewer.Viewer;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Fridge;
import Entity.WorldObjects.Objects.TV;
import Entity.WorldObjects.Objects.Table;
import Input.CameraMovement;
import Main.Assets;
import Main.Game;
import Main.Handler;
import Utils.Util;
import Utils.Vector4I;
import World.Tiles.Render.TileInstanceModel;

public class World {
	private Handler handler;

	private Camera camera;
	private CameraMovement cameraMovement;
	
	private ArrayList<Light> sun;
	private ArrayList<Lot> lots;
	
	private Player player;
	private Human human;
	
//	private Viewer needViewer;
	
	public World(Handler handler) {
		this.handler = handler;
		
		camera = new Camera(70, (float) handler.getWidth() / (float) handler.getHeight(), 0.3f, 1000);
		cameraMovement = new CameraMovement(handler, camera, new Vector3f(7, 20, 24), 10, 10, .15f);
		
		sun = new ArrayList<>();
		lots = new ArrayList<>();
	}
	
	public void init() {
		VectorModel.init(Game.physicsShader);
		
		lots.add(new Lot(handler, new Vector2f(), new Vector2f(60)));

		player = new Player(handler, Assets.playerModel, Assets.playerTexture, "Player");
		player.setPosition2D(2, 2);

		human = new Human(handler, Assets.playerModel, Assets.playerTexture, "Bob");
		
//		needViewer = new Viewer(handler, human);
		
		sun.add(new Light(new Vector3f(10, 35, 10), new Vector3f(1), new Vector3f(.8, 0, 0)));
		Util.placeHouse(handler, lots.get(0), Assets.house, 5, 5);
		
//		place(new Fridge(handler), lots.get(0), new Vector2f(5, 5));
		
		lots.get(0).enableEdit();
		
		Fridge fridge = new Fridge(handler);
		place(fridge, lots.get(0), new Vector2f(8), 270);
		ArrayList<Vector2f> tiles = PathFinding.getEffectiveArea(lots.get(0), fridge, new Vector2f(7), false);
		
//		TV tv = new TV(handler);
//		place(tv, lots.get(0), new Vector2f(8));
//		ArrayList<Vector2f> tiles = PathFinding.getEffectiveArea(lots.get(0), tv, new Vector2f(2), false);
		
		for(Vector2f v : tiles) 
			place(new Table(handler), lots.get(0), v, 0);
	}
	
	public void update(float delta) {
		cameraMovement.update(delta);
		
		for(Lot lot : lots)
			lot.update(delta);
		
		player.update(delta);
		human.update(delta);
//		cameraMovement.centerOnEntity(player);
//		needViewer.update();
	}
	
	public void render() {
		for(Lot lot : lots)
			lot.render();
		
//		needViewer.render();
		player.render();
		human.render();
		Assets.defaultShader.bind();
		Assets.defaultShader.loadLights(sun);
		TileInstanceModel.TILE_SHADER.bind();
		TileInstanceModel.TILE_SHADER.loadLights(sun);
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
	
	public void place(WorldObject worldObject, Lot lot, Vector2f position, float angle) {
		worldObject.setPosition2D(position);
		worldObject.setAngle(angle);
		worldObject.addToTile(lot.getTiles()[(int) worldObject.getPosition2D().x][(int) worldObject.getPosition2D().y]);
	}
 	
	public Lot getTestLot() {
		return lots.get(0);
	}
	
//	public Player getPlayer() { return player; }
	public Camera getCamera() { return camera; }
	public CameraMovement getCameraMovement() { return cameraMovement; }
}
