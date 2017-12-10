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
import Utils.Util;
import World.Tiles.Render.TileInstanceModel;

public class World {
	private Handler handler;

	private Camera camera;
	private CameraMovement cameraMovement;
	
	private ArrayList<Light> sun;
	private ArrayList<Lot> lots;
	
	private Player player;
	
	public World(Handler handler) {
		this.handler = handler;
		
		camera = new Camera(70, (float) handler.getWidth() / (float) handler.getHeight(), 0.3f, 1000);
		cameraMovement = new CameraMovement(handler, camera, new Vector3f(7, 20, 24), 10, 10, .15f);
		
		sun = new ArrayList<>();
		lots = new ArrayList<>();
	}
	
	public void init() {
		VectorModel.init(Game.physicsShader);
		
		lots.add(new Lot(handler, new Vector2f(), 200, 200));

		player = new Player(handler, Assets.playerModel, Assets.playerTexture);
		player.getBody().setPosition2D(2, 2);
		
		sun.add(new Light(new Vector3f(10, 35, 10), new Vector3f(1), new Vector3f(.8, 0, 0)));
		Util.placeHouse(handler, lots.get(0), Assets.maze, 0, 0);
	}
	
	public void update(float delta) {
		for(Lot lot : lots)
			lot.update(delta);
		
		player.update(delta);
		cameraMovement.update(delta);
	}
	
	public void render() {
		for(Lot lot : lots)
			lot.render();
		
		player.render();
		
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
	
	public Camera getCamera() { return camera; }
	public CameraMovement getCameraMovement() { return cameraMovement; }
}
